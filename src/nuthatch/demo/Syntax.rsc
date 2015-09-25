module nuthatch::demo::Syntax

import String;


lexical INT = [0-9]+;

lexical VAR = [a-zA-Z_][a-zA-Z0-9_]*;

lexical LAYOUT = [\t-\n \r \ ];
layout LAYOUTLIST
	= LAYOUT* !>> [\t-\n \r \ ];

syntax Program = Definition*;

syntax Definition
	= "def" Name "=" "{" Action* "}"
	| "def" Name "=" Expr ";"
	;

syntax Action
	= Go: "go" Expr ";"
	| Replace: "replace" Expr ";"
	| Guarded: Expr "=\>" Action ";"
	| Assign: Expr "=" Expr ";"
	| Append: Expr "+=" Expr ";"
	| Oneof: "oneof" "{" Action* "}"
	;
	
syntax Expr
	= Var: Name
	| Int: INT
	| "(" Expr ")"
	| App: VAR "(" Expr ")"
	> left Mul: Expr "*" Expr
	> left (
		Add: Expr "+" Expr
	  | Sub: Expr "-" Expr
	)
	> Let: "let" VAR "=" Expr "in" Expr
	| LetFun: "let" VAR "(" VAR ")" "=" Expr "in" Expr
	| If: "if" Expr "then" Expr "else" Expr 
	;

syntax Name
	= Name: VAR
	;
	
data Exception = Error(str message, loc location);
