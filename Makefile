CASK=cask
EMACS=emacs
CASKRUN=$(CASK) exec emacs --debug --script
WING=$(CASKRUN) script/build.el --


install:
	$(CASK) install


gen-src: install
	$(WING) gen-src


slides: gen-src adoc/2015_lisbon.adoc
	asciidoc adoc/2015_lisbon.adoc
