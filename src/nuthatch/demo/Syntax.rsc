module nuthatch::demo::Syntax

import String;


lexical INT = [0-9]+;

lexical VAR = [a-zA-Z_][a-zA-Z0-9_]*;

lexical LAYOUT = [\t-\n \r \ ];
layout LAYOUTLIST
	= LAYOUT* !>> [\t-\n \r \ ];

syntax Program = Expr;

syntax Expr
	= VAR
	| INT
	| "(" Expr ")"
	| VAR "(" Expr ")"
	> left Expr "*" Expr
	> left (
		Expr "+" Expr
	  | Expr "-" Expr
	)
	> "let" VAR "=" Expr "in" Expr
	| "let" VAR "(" VAR ")" "=" Expr "in" Expr
	| "if" Expr "then" Expr "else" Expr 
	;

data Exception = Error(str message, loc location);
