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

clean:
	rm -f *.def *.tbl

.PHONY: all clean
