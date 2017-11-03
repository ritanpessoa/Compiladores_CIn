grammar gramatica;

goal : mainClass ( classDeclaration )* EOF;

mainClass : 'class' IDENTIFIER '{' 'public' 'static' 'void' 'main' '(' 'String' '[' ']' IDENTIFIER ')' '{' statement '}' '}';

classDeclaration : 'class' IDENTIFIER ( 'extends' IDENTIFIER )? '{' ( varDeclaration )* ( methodDeclaration )* '}';

varDeclaration : type IDENTIFIER;

methodDeclaration : 'public' type IDENTIFIER '(' ( type IDENTIFIER ( ',' type IDENTIFIER )* )? ')' '{' ( varDeclaration )* ( statement )* 'return' expression ';' '}';

type : 'int' '[' ']' | 'boolean' | 'int' | IDENTIFIER;

statement : '{' ( statement )* '}'
		  | 'if' '(' expression ')' statement 'else' statement
		  | 'while' '(' expression ')' statement
		  | 'System.out.println' '(' expression ')' ';'
		  | IDENTIFIER '=' expression ';'
		  | IDENTIFIER '[' expression ']' '=' expression ';';
		  
expression : expression OPERAND expression
		   | expression '[' expression ']'
		   | expression '.' 'length'
		   | expression '.' IDENTIFIER '(' ( expression ( ',' expression )* )? ')'
		   | INTEGER_LITERAL
		   | 'true'
		   | 'false'
		   | IDENTIFIER
		   | 'this'
		   | 'new' 'int' '[' expression ']'
		   | 'new' IDENTIFIER '(' ')'
		   | '!' expression
		   | '(' expression ')';
		   
IDENTIFIER : ([a-zA-Z]|'_')([a-zA-Z]|'_'|[0-9])* ;

OPERAND : ('&&'|'<'|'+'|'-'|'*');

INTEGER_LITERAL : ('-')?[0-9]+;

WS : [' \r\n\t']+ -> skip;
		   

		   