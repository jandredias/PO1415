JAVADIR=../..
COREDIR=../../poof-core
JARNAME=poof-textui

all:
	(cd src; javac -encoding UTF-8 -cp $(JAVADIR)/po-uilib/po-uilib.jar:$(JAVADIR)/poof-support/poof-support.jar:$(COREDIR)/poof-core.jar `find . -name \*.java`)
	(cd src; jar cvf ../$(JARNAME).jar `find . -name \*.class -o -name \*.java`)
clean:
	$(RM) $(JARNAME).jar `find . -name \*.class`

install:
	/bin/cp $(JARNAME).jar $(JAVADIR)
