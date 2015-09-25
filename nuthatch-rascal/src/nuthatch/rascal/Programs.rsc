module nuthatch::rascal::Programs

import String;


lexical INT = [0-9]+;

lexical VAR = [a-zA-Z_][a-zA-Z0-9_]*;

lexical LAYOUT = [\t-\n \r \ ];
layout LAYOUTLIST
	= LAYOUT* !>> [\t-\n \r \ ];

syntax Program = Expr;

syntax Branch
	= Parent: "parent"
	| First: "first"
	| Last: "last"
	| From: "from"
	| Int: INT
	| Next: "next"
	| Prev: "prev"
	;

syntax Path
	= Path: {Branch "."}+
	;
	
syntax Stat
	= If: "if" "(" Expr ")" "{" Stat* "}" "else" "{" Stat* "}"
	| Assign: Expr "=" Expr ";"
	| Go: "go" Path ";"
	| Goto: "goto" Path ";"
	| Replace: "replace" Expr ";"
	| Proceed: "proceed" ";"
	| FromSwitch: "from" "{" FromCase* "}"
	;
	
syntax FromCase
	= Case: Branch ":" Stat
	;
	
syntax Expr
	= Var: VAR
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

data Exception = Error(str message, loc location);
