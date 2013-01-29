SDFFILES=Stratego-Core-Constants.sdf \
	Stratego-Core-Identifiers.sdf \
	Stratego-Core-Layout.sdf \
	Stratego-Core-Signatures.sdf \
	Stratego-Signatures.sdf \

all: Stratego-Signatures.tbl

Stratego-Signatures.def: $(SDFFILES)
	pack-sdf -i Stratego-Signatures.sdf -o $@


%.tbl: %.def
	sdf2table -i $< -m $* -o $@ && echo OK

%.rtg: %.def
	sdf2rtg -i $< -m $* -o $@

%.str: %.rtg
	rtg2sig -i $< --module $* -o $@

StrategoPatterns.java: Stratego-Signatures.str
	CLASSPATH=.:$$HOME/git/nuthatch-stratego/bin:$$HOME/git/nuthatch/bin:$$HOME/magnolia/workspace/org.spoofax.terms/bin/:$$HOME/magnolia/workspace/org.spoofax.jsglr/bin java nuthatch.util.Sig2Java -i $< -o $@ -p nuthatch.stratego.syntax

clean:
	rm -f *.def *.tbl

.PHONY: all clean
