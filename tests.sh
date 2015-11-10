#!/bin/bash
# --------------------------------------------------
#
#       Title: tests.sh
# Description: Script Testes
#      Author: Ricardo Sequeira (edited by...blah blah blah)
#
#       Usage: ./tests.sh
#
# --------------------------------------------------


# Directorio onde se encontram os testes da entrega intermedia
# disponiveis em https://fenix.ist.utl.pt/disciplinas/po6/2013-2014/1-semestre/projecto/desenvolvimento-do-projecto
TESTSDIR='tests'

# Directorio base do projecto
PROJDIR='.'

# Directorio onde se encontra o material de apoio (po-uilib, poof-textui-poof-support, poof-textui-poof-support)
SUPPORTDIR='.'

TEMPDIR='tmp'

SUCCESS=0
FAIL=0
STRRESULTS=""

exec > >(tee $TEMPDIR/tests.res)

echo "Compiling..."
cd $PROJDIR
$(make)

# 
if [ -f $PROJDIR/poof-core/poof-core.jar ]
  then
    echo "-- successfully generated 'poof-core.jar'".
  else
    echo "!! default makefile rule did not produce 'poof-core.jar'"
fi

if [ -f $PROJDIR/poof-textui/poof-textui.jar ]
  then
    echo "-- successfully generated 'poof-textui.jar'".
  else
    echo "!! default makefile rule did not produce 'poof-textui.jar'"
fi

for file in $TESTSDIR/*.in
do
    filename=$(basename $file)
    filename=${filename%%.*}

    if [ -f $TESTSDIR/$filename.import ]
    then
      java -Dimport=$TESTSDIR/$filename.import -Din=$TESTSDIR/$filename.in -Dout=$TEMPDIR/test.outhyp -cp $PROJDIR/poof-textui/poof-textui.jar:$PROJDIR/poof-textui/poof-textui.jar:$PROJDIR/poof-core/poof-core.jar:$SUPPORTDIR/po-uilib/po-uilib.jar:$SUPPORTDIR/poof-support/poof-support.jar:$SUPPORTDIR/poof-support.jar poof.textui.Shell
    else
      java -Din=$TESTSDIR/$filename.in -Dout=$TEMPDIR/test.outhyp -cp $PROJDIR/poof-textui/poof-textui.jar:$PROJDIR/poof-textui/poof-textui.jar:$PROJDIR/poof-core/poof-core.jar:$SUPPORTDIR/po-uilib/po-uilib.jar:$SUPPORTDIR/poof-support/poof-support.jar:$SUPPORTDIR/poof-support.jar poof.textui.Shell
    fi


    echo "====================$filename===================="
    if diff -w $TESTSDIR/expected/$filename.out $TEMPDIR/test.outhyp; then
      
      echo "TEST PASSED!"
      SUCCESS=$[SUCCESS+1]
      STRRESULTS="$STRRESULTS$filename - OK\n"
    else
      echo "output differs from expected"
      FAIL=$[FAIL+1]
      STRRESULTS="$STRRESULTS$filename - FAIL\n"
    fi
done
echo ""
echo "=====================TEST RESULTS====================="
echo -e $STRRESULTS
echo "SUCCESS: $SUCCESS"
echo "FAIL: $FAIL"

echo ""
echo "Generated '$TEMPDIR/tests.res'"
