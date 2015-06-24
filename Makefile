CASK=cask
EMACS=emacs
CASKRUN=$(CASK) exec emacs --debug --script
WING=$(CASKRUN) script/build.el --


ADOC_FILES = $(wildcard adoc/*.adoc) $(wildcard src/lisbon/*.adoc)

all: slides book pdf pre-req

install:
	$(CASK) install

gen-src: install
	$(WING) gen-src

slides: gen-src slides-fast

slides-fast: adoc/2015_lisbon.html

adoc/2015_lisbon.html: $(ADOC_FILES)
	asciidoc adoc/2015_lisbon.adoc

book: gen-src book-fast

book-fast: adoc/2015_lisbon_book.html

adoc/2015_lisbon_book.html: $(ADOC_FILES)
	asciidoc --backend=html --out-file adoc/2015_lisbon_book.html adoc/2015_lisbon.adoc 

pdf: gen-src pdf-fast

pdf-fast: adoc/2015_lisbon.pdf

adoc/2015_lisbon.pdf: $(ADOC_FILES)
	a2x --dblatex-opts \
	"-P latex.output.revhistory=0 -P doc.toc.show=0 --texstyle=adoc/asciidoc-dblatex.sty" \
	--asciidoc-opts="-d book" \
	adoc/2015_lisbon_pdf_wrapper.adoc
	mv adoc/2015_lisbon_pdf_wrapper.pdf adoc/2015_lisbon.pdf

pre-req: adoc/2015_prerequisities.adoc
	asciidoc adoc/2015_prerequisities.adoc

clean:
	rm adoc/*html

-include Makefile-local
