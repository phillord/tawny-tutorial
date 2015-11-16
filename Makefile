CASK=cask
EMACS=emacs
CASKRUN=$(CASK) exec emacs --debug --script
WING=$(CASKRUN) script/build.el --


ADOC_FILES = $(wildcard adoc/*.adoc) $(wildcard src/tawny/tutorial/*.adoc)

all: slides book pre-req

test: all
	## lein run is part of the practical!
	lein run

install:
	$(CASK) install

gen-src: install
	$(WING) gen-src

slides: gen-src slides-fast

slides-fast: adoc/2015_swat4ls.html

adoc/2015_swat4ls.html: $(ADOC_FILES)
	asciidoc adoc/2015_swat4ls.adoc

book: gen-src book-fast

book-fast: adoc/2015_swat4ls_book.html

adoc/2015_swat4ls_book.html: $(ADOC_FILES)
	asciidoc --backend=html --out-file adoc/2015_swat4ls_book.html adoc/2015_swat4ls.adoc 

# pdf: gen-src pdf-fast

# pdf-fast: adoc/2015_swat4ls.pdf

adoc/2015_swat4ls.pdf: $(ADOC_FILES)
	a2x --dblatex-opts \
	"-P latex.output.revhistory=0 -P doc.toc.show=0 --texstyle=adoc/asciidoc-dblatex.sty" \
	--asciidoc-opts="-d book" \
	adoc/2015_swat4ls_pdf_wrapper.adoc
	mv adoc/2015_swat4ls_pdf_wrapper.pdf adoc/2015_swat4ls.pdf

pre-req: adoc/2015_prerequisities.adoc
	asciidoc adoc/2015_prerequisities.adoc

clean:
	rm adoc/*html
	rm src/tawny/tutorial/*adoc

-include Makefile-local
