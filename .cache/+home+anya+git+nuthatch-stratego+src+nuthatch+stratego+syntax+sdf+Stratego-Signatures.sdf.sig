module(unparameterized("Stratego-Signatures"),[imports([module(unparameterized("Stratego-Core-Constants")),module(unparameterized("Stratego-Core-Identifiers")),module(unparameterized("Stratego-Core-Layout")),module(unparameterized("Stratego-Core-Signatures"))])],[exports(conc-grammars(conc-grammars(conc-grammars(sorts([sort("Module")]),context-free-syntax([prod([lit("\"module\""),sort("ModName"),iter-star(sort("Decl"))],sort("Module"),attrs([term(default(appl(unquoted("cons"),[fun(quoted("\"Module\""))])))]))])),sorts([sort("Decl")])),context-free-syntax([prod([lit("\"signature\""),iter-star(sort("Sdecl"))],sort("Decl"),attrs([term(default(appl(unquoted("cons"),[fun(quoted("\"Signature\""))])))]))]))),hiddens(context-free-start-symbols([sort("Module")]))])