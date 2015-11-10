all:
	clear
	(cd po-uilib; make $(MFLAGS) all)
	(cd poof-support; make $(MFLAGS) all)
	(cd poof-core; make $(MFLAGS) all)
	(cd poof-textui; make $(MFLAGS) all)
	export CLASSPATH="./po-uilib/po-uilib.jar:./poof-support/poof-support.jar:./poof-core/poof-core.jar:./poof-textui/poof-textui.jar";
clean:
	(cd poof-core; make $(MFLAGS) clean)
	(cd poof-textui; make $(MFLAGS) clean)
	(cd po-uilib; make $(MFLAGS) clean)
	(cd poof-support; make $(MFLAGS) clean)

install:
	(cd poof-core; make $(MFLAGS) install)
	(cd poof-textui; make $(MFLAGS) install)
	(cd po-uilib; make $(MFLAGS) install)
	(cd poof-support; make $(MFLAGS) install)
