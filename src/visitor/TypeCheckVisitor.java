package visitor;

import symboltable.Class;
import symboltable.Method;
import symboltable.SymbolTable;
import symboltable.Variable;
import ast.And;
import ast.ArrayAssign;
import ast.ArrayLength;
import ast.ArrayLookup;
import ast.Assign;
import ast.Block;
import ast.BooleanType;
import ast.Call;
import ast.ClassDeclExtends;
import ast.ClassDeclSimple;
import ast.False;
import ast.Formal;
import ast.Identifier;
import ast.IdentifierExp;
import ast.IdentifierType;
import ast.If;
import ast.IntArrayType;
import ast.IntegerLiteral;
import ast.IntegerType;
import ast.LessThan;
import ast.MainClass;
import ast.MethodDecl;
import ast.Minus;
import ast.NewArray;
import ast.NewObject;
import ast.Not;
import ast.Plus;
import ast.Print;
import ast.Program;
import ast.This;
import ast.Times;
import ast.True;
import ast.Type;
import ast.VarDecl;
import ast.While;

public class TypeCheckVisitor implements TypeVisitor {

	private SymbolTable symbolTable;

	public TypeCheckVisitor(SymbolTable st) {
		symbolTable = st;
	}
	
	private Class currClass;
	private Method currMethod;

	// MainClass m;
	// ClassDeclList cl;
	public Type visit(Program n) {
		n.m.accept(this);
		for (int i = 0; i < n.cl.size(); i++) {
			n.cl.elementAt(i).accept(this);
		}
		return null;
	}

	// Identifier i1,i2;
	// Statement s;
	public Type visit(MainClass n) {
		//n.i1.accept(this);
		//n.i2.accept(this);
		//n.s.accept(this);
		return null;
	}

	// Identifier i;
	// VarDeclList vl;
	// MethodDeclList ml;
	public Type visit(ClassDeclSimple n) {
		currClass = symbolTable.getClass(n.i.s);
		currMethod = null;
		//n.i.accept(this);
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.ml.size(); i++) {
			n.ml.elementAt(i).accept(this);
		}
		return null;
	}

	// Identifier i;
	// Identifier j;
	// VarDeclList vl;
	// MethodDeclList ml;
	public Type visit(ClassDeclExtends n) {
		currClass = symbolTable.getClass(n.i.s);
		currMethod = null;
		//n.i.accept(this);
		//n.j.accept(this);
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.ml.size(); i++) {
			n.ml.elementAt(i).accept(this);
		}
		return null;
	}

	// Type t;
	// Identifier i;
	public Type visit(VarDecl n) {
		n.t.accept(this);
		n.i.accept(this);
		return null;
	}

	// Type t;
	// Identifier i;
	// FormalList fl;
	// VarDeclList vl;
	// StatementList sl;
	// Exp e;
	public Type visit(MethodDecl n) {
		currMethod = currClass.getMethod(n.i.s);
		n.t.accept(this);
		//n.i.accept(this);
		for (int i = 0; i < n.fl.size(); i++) {
			n.fl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.sl.size(); i++) {
			n.sl.elementAt(i).accept(this);
		}
		n.e.accept(this);
		return null;
	}

	// Type t;
	// Identifier i;
	public Type visit(Formal n) {
		//n.t.accept(this);
		//n.i.accept(this);
		return null;
	}

	public Type visit(IntArrayType n) {
		return n;
	}

	public Type visit(BooleanType n) {
		return n;
	}

	public Type visit(IntegerType n) {
		return n;
	}

	// String s;
	public Type visit(IdentifierType n) {
		return n;
	}

	// StatementList sl;
	public Type visit(Block n) {
		for (int i = 0; i < n.sl.size(); i++) {
			n.sl.elementAt(i).accept(this);
		}
		return null;
	}

	// Exp e;
	// Statement s1,s2;
	public Type visit(If n) {
		Type t1 = n.e.accept(this);
		n.s1.accept(this);
		n.s2.accept(this);
		if (t1 instanceof BooleanType) return new BooleanType();
		System.out.println("If com argumento sem ser booleano");
		System.out.println("Tipo: " + t1.toString() + " Expressao: " + n.e.toString());
		System.exit(0);
		return null;
	}

	// Exp e;
	// Statement s;
	public Type visit(While n) {
		Type t1 = n.e.accept(this);
		n.s.accept(this);
		if (t1 instanceof BooleanType) return new BooleanType();
		System.out.println("While com argumento sem ser booleano");
		System.exit(0);
		return null;
	}

	// Exp e;
	public Type visit(Print n) {
		n.e.accept(this);
		return null;
	}

	// Identifier i;
	// Exp e;
	public Type visit(Assign n) {
		Type t1 = n.i.accept(this);
		Type t2 = n.e.accept(this);
		if(symbolTable.compareTypes(t1, t2)) return t1;
		System.out.println("Assign de tipos diferentes");
		System.out.println("Tipo: " + t1.toString() + " Identificador: " + n.i.s);
		System.out.println("Tipo: " + t2.toString() + " Expressao: " + n.e.toString());
		System.exit(0);
		return null;
	}

	// Identifier i;
	// Exp e1,e2;
	public Type visit(ArrayAssign n) {
		n.i.accept(this);
		n.e1.accept(this);
		n.e2.accept(this);
		return null;
	}

	// Exp e1,e2;
	public Type visit(And n) {
		Type t1 = n.e1.accept(this);
		Type t2 = n.e2.accept(this);
		if (t1 instanceof BooleanType && t2 instanceof BooleanType) return new BooleanType();
		System.out.println("And com argumentos sem ser booleano");
		System.exit(0);
		return null;
	}

	// Exp e1,e2;
	public Type visit(LessThan n) {
		Type t1 = n.e1.accept(this);
		Type t2 = n.e2.accept(this);
		if (t1 instanceof IntegerType && t2 instanceof IntegerType) return new BooleanType();
		System.out.println("LessThan com argumentos sem ser booleano");
		System.out.println("Tipo: " + t1.toString() + " Expressao: " + n.e1.toString());
		System.out.println("Tipo: " + t2.toString() + " Expressao: " + n.e2.toString());
		System.exit(0);
		return null;
	}

	// Exp e1,e2;
	public Type visit(Plus n) {
		Type t1 = n.e1.accept(this);
		Type t2 = n.e2.accept(this);
		if (t1 instanceof IntegerType && t2 instanceof IntegerType) return new IntegerType();
		System.out.println("Plus com argumentos sem ser inteiros");
		System.exit(0);
		return null;
	}

	// Exp e1,e2;
	public Type visit(Minus n) {
		Type t1 = n.e1.accept(this);
		Type t2 = n.e2.accept(this);
		if (t1 instanceof IntegerType && t2 instanceof IntegerType) return new IntegerType();
		System.out.println("Minus com argumentos sem ser inteiros");
		System.exit(0);
		return null;
	}

	// Exp e1,e2;
	public Type visit(Times n) {
		Type t1 = n.e1.accept(this);
		Type t2 = n.e2.accept(this);
		if (t1 instanceof IntegerType && t2 instanceof IntegerType) return new IntegerType();
		System.out.println("Times com argumentos sem ser inteiros");
		System.exit(0);
		return null;
	}

	// Exp e1,e2;
	public Type visit(ArrayLookup n) {
		n.e1.accept(this);
		n.e2.accept(this);
		return new IntegerType();
	}

	// Exp e;
	public Type visit(ArrayLength n) {
		n.e.accept(this);
		return new IntegerType();
	}

	// Exp e;
	// Identifier i;
	// ExpList el;
	public Type visit(Call n) {
		Class save = currClass;
		IdentifierType t1 = (IdentifierType) n.e.accept(this);
		currClass = symbolTable.getClass(t1.s);
		//System.out.println(n.i.s);
		Type t2 = n.i.accept(this);
		currClass = save;
		for (int i = 0; i < n.el.size(); i++) {
			n.el.elementAt(i).accept(this);
		}
		return t2;
	}

	// int i;
	public Type visit(IntegerLiteral n) {
		return new IntegerType();
	}

	public Type visit(True n) {
		return new BooleanType();
	}

	public Type visit(False n) {
		return new BooleanType();
	}

	// String s;
	public Type visit(IdentifierExp n) {
		return symbolTable.getVarType(currMethod, currClass, n.s);
	}

	public Type visit(This n) {
		return new IdentifierType(currClass.getId());
	}

	// Exp e;
	public Type visit(NewArray n) {
		n.e.accept(this);
		return new IntArrayType();
	}

	// Identifier i;
	public Type visit(NewObject n) {
		//botar a classe
		return new IdentifierType(n.i.s);
	}

	// Exp e;
	public Type visit(Not n) {
		Type t1 = n.e.accept(this);
		if (t1 instanceof BooleanType) return new BooleanType();
		System.out.println("Not com argumento sem ser booleano");
		System.exit(0);
		return null;
	}

	// String s;
	public Type visit(Identifier n) {
		//return null;
		Variable v1 = currClass.getVar(n.s);
		Variable v2 = null, v3 = null;
		if(currMethod != null) v2 = currMethod.getVar(n.s);
		if(currMethod != null && v2 == null) v3 = currMethod.getParam(n.s);
		if(v1 != null || v2 != null || v3 != null) return symbolTable.getVarType(currMethod, currClass, n.s);
		else{
			return symbolTable.getMethodType(n.s, currClass.getId());
		}
		//return null;
	}
}
