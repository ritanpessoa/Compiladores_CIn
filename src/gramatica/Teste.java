package gramatica;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import ast.Program;
import visitor.BuildSymbolTableVisitor;
import visitor.PrettyPrintVisitor;
import visitor.TypeCheckVisitor;

public class Teste {
	public static void main(String[] args) throws IOException {
		File in = new File("codigos/QuickSort.txt");
		FileInputStream input = new FileInputStream(in);
		ANTLRInputStream antlr = new ANTLRInputStream(input);
		gramaticaLexer lexer = new gramaticaLexer(antlr);
		CommonTokenStream token = new CommonTokenStream(lexer);
		gramaticaParser parser = new gramaticaParser(token);
		ConstrutorAST ast = new ConstrutorAST();
		Program prog = ast.visitGoal(parser.goal());
		BuildSymbolTableVisitor stVis = new BuildSymbolTableVisitor();
		prog.accept(stVis);
		System.out.println("asd");
		prog.accept(new TypeCheckVisitor(stVis.getSymbolTable()));
		//PrettyPrintVisitor print = new PrettyPrintVisitor();
		//prog.accept(print);
	}
}
