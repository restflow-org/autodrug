#!/bin/sh
# the next line restarts using -*-Tcl-*-sh \
         exec tclsh "$0" ${1+"$@"}
global env
global argv

set command [lindex $argv 0]

set dpProjectDir DataProcessing
set base /data/$env(USER)/autodrug
set dpLib $dpProjectDir/target/dependency
set dpClasses $dpProjectDir/target/classes
set test_classes $dpProjectDir/target/test-classes
set dpClassPath $dpLib/*:$dpClasses:$test_classes
set dpSrcBase $dpProjectDir/
set runs_dir $base/test

puts $dpClassPath

set testNames { DataProcessing }
set workflowNames { DataProcessing }

#source $dpProjectDir/getLatestFile.tcl
#source $dpProjectDir/workflowTests.tcl


proc searchDir {dir text} {
        puts "searching for $text"
        #puts [exec find . -name DataProcessing-P.yaml]
        #set cmd [list find . -name "*" -exec grep -H $text '\{\}' \;]
        set cmd [list find $dir -name "*" -exec grep -H $text \{\} \;]
        set result [eval exec $cmd]
        foreach line [split $result "\n"] {
            foreach file [string range $line 0 [string first ":" $line]] {
                set exclude false
                foreach element [file split $file] {
                    if {$element == ".svn"} {
                        set exclude true
                        break;
                    }
                }
                if {$exclude} continue;
                puts $line
            }
        }
}

proc testWorkflow {name} {
    switch $name {
        freeR {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestFreeR]
        }
        fftMap {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestFftMap]
        }
        getImgHeader {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestGetImgHeader]
        }
        labelit {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestLabelit]
        }
        MolecularReplacement {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestMolecularReplacement]
        }
        molrep {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestMolrep]
        }
        mosflm {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestMosflm]
        }
        peakMax {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestPeakMax]
        }
        pointless {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestPointless]
        }
        ProcessImages {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestProcessImages]
        }
        raddose {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestRaddose]
        }
        refmac {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestRefmac]
        }
        scala {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestScala]
        }
        ScoreSample {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestScoreSample]
        }
        Strategy {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestStrategy]
        }
        truncate {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestTruncate]
        }
        xds {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestXds]
        }
        xtriage {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestXtriage]
        }
        CollectTwoImages {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestCollectTwoImages]
        }
        ProcessSample {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestProcessSample]
        }
        CollectSample {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestCollectSample]
        }
        CollectRun {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestCollectRun]
        }
        ScreenSample {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestScreenSample] 
        }
        IndexSample {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestIndexSample]
        }
        VisualizeWorkflows {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestVisualizeWorkflows] 
        }
        AutoDrug {
            puts [exec mvn test -DfailIfNoTests=false -Dtest=TestAutoDrug]
        }
    }
}

switch $command {
    package {
        puts [exec mvn package -DskipTests]
    }
    install {
        puts "publish"
        puts [exec mvn install -DskipTests]
    }

    visualize {
        puts "visualize"
        if {! [info exists env(HOST_DATA)]  } {
            error "must set HOST_DATA environment variable"
        }
        puts [exec mvn -Dtest=TestVisualizeWorkflows test -DfailIfNoTests=false]
    }
    test {
        if {! [info exists env(TEST_DATA)]  } {
            error "must set TEST_DATA environment variable"
        }
        if { $argc < 2} {
            puts [exec mvn test]
            return
        }
        set workflowName [lindex $argv 1]
        testWorkflow $workflowName
    }
    module-create {

        if { $argc < 2} {
            error "module-create module-name"
        }

        set moduleName [lindex $argv 1]
        cd $dpProjectDir
        file mkdir src/main/resources/edu/stanford/slac/smb/environment/$moduleName
        #mkdir src/main/resources/edu/stanford/slac/smb/environment/$moduleName/env.csh
        file mkdir src/main/workflows/org/restflow/addons/$moduleName
        file mkdir src/main/workflows/org/restflow/addons/$moduleName/actors
        file mkdir src/main/workflows/org/restflow/addons/$moduleName/workflows
        file mkdir src/test/resources/org/restflow/addons/$moduleName
        file mkdir src/test/resources/org/restflow/addons/$moduleName/inputs
        file mkdir src/test/resources/org/restflow/addons/$moduleName/inputs/1
        file mkdir src/test/resources/org/restflow/addons/$moduleName/workflows
        #mkdir src/test/resources/org/restflow/addons/$moduleName/inputs/1/infile.yaml
    }
    class-edit {
        cd $dpProjectDir
        set groovyClasses [glob -nocomplain src/main/groovy/org/restflow/addons/samples/dpProject/* *.groovy]
        eval lappend groovyClasses [glob -nocomplain src/main/groovy/org/restflow/addons/samples/collect/* *.groovy]
        eval lappend groovyClasses [glob -nocomplain src/main/groovy/org/restflow/addons/samples/process/* *.groovy]
        eval lappend groovyClasses [glob -nocomplain src/main/groovy/org/restflow/addons/samples/screen/* *.groovy]
        eval lappend groovyClasses [glob -nocomplain src/main/groovy/org/restflow/addons/samples/raster/* *.groovy]

        puts "$groovyClasses"
        eval exec gvim $groovyClasses
    }
    module-edit {

        if { $argc < 2} {
            error "module-create module-name"
        }

        set moduleName [lindex $argv 1]
        #cd $dpProjectDir
        set actors [glob -nocomplain */src/main/workflows/org/restflow/addons/$moduleName/actors/* *.yaml]
        set workflows [glob -nocomplain */src/main/workflows/org/restflow/addons/$moduleName/workflows/* *.yaml]
        set java [glob -nocomplain */src/*/java/org/restflow/addons/$moduleName/* *.java]
        set workflowTests [glob -nocomplain */src/test/workflows/org/restflow/addons/$moduleName/workflows/* *.yaml]
        set beans [glob -nocomplain */src/main/workflows/org/restflow/addons/$moduleName/beans/* *.yaml]
        set testData [glob -nocomplain */src/test/resources/org/restflow/addons/$moduleName/*/*/* *.yaml]
        eval exec gvim $actors $workflows $beans $workflowTests $testData $java
    }
    search {

        if { $argc < 2} {
            error "search text"
        }
        set text [lindex $argv 1]
        searchDir . $text
    }
}





