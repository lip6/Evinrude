#!/bin/sh


# ------------ Lancement des benchs -------------
evinrude="target/$1-jar-with-dependencies.jar"
config="resources/config.properties.default"

java -Xms40m -Xmx3000m -jar $evinrude -config $config -benchs


# ----------- Génération des résultats ----------
head="
##teamcity"

function testSuiteStarted {
    name=$1
    echo "$head[testSuiteStarted name='$name']"
}

function testSuiteFinished {
    name=$1
    echo "$head[testSuiteFinished name='$name']"
}

function testStarted {
    name=$1
    echo "$head[testStarted name='$name']"
}

function testFinished {
    name=$1
    echo "$head[testFinished name='$name']"
}

function testFailed {
    name=$1
    message=$2
    details=$3
    echo "$head[testFailed name='$name' message='$message' details='$details']"
}


IFS="
"

current_app=""

results="outputs/benchs.result"

testSuiteStarted "All integration tests"
for line in `cat $results`
do
    app=`echo $line | cut -d/ -f1`
    exe=`echo $line | cut -d/ -f2 | cut -d" " -f1`
    compilation=`echo $line | sed -r 's/.*(OK|KO) (OK|KO)/\1/'`
    trace=`echo $line | sed -r 's/.*(OK|KO) (OK|KO)/\2/'`

    if [ "$current_app" == "" ] ; then
        current_app=$app
        testSuiteStarted "$app"
    elif [ "$current_app" != $app ] ; then
        testSuiteFinished "$current_app"
        current_app=$app
        testSuiteStarted "$app"
    fi

    testStarted "$app/$exe"
    if [ "$compilation" == "KO" ] ; then
        testFailed "$app/$exe" "Compilation failed" ""
    elif [ "$trace" == "KO" ] ; then
        testFailed "$app/$exe" "Trace replay failed" ""
    fi
    testFinished "$app/$exe"
done
if [ "$current_app" != "" ] ; then
    testSuiteFinished "$current_app"
fi
testSuiteFinished "All integration tests"


grep -q 'KO$' outputs/benchs.result
if [ $? -eq 0 ] ; then
    exit 1
else
    exit 0
fi
