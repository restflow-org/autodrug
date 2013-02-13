#!/bin/sh
# the next line restarts using -*-Tcl-*-sh \
         exec tclsh "$0" ${1+"$@"}
global env
global argv

set command [lindex $argv 0]

set dpProject autodrug/DataProcessing
set dpProjectDir $env(WORKSPACE)/$dpProject
set base /data/$env(USER)/autodrug
set dpLib $dpProjectDir/target/dependency
set dpClasses $dpProjectDir/target/classes
set test_classes $dpProjectDir/target/test-classes
set dpClassPath $dpLib/*:$dpClasses:$test_classes
set dpSrcBase $env(WORKSPACE)/$dpProject/
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

switch $command {
    build {
        cd $dpProjectDir
        puts "ant"
        puts [exec mvn package -DskiptTests]
    }
    publish {
        cd $dpProjectDir
        puts "publish"
        puts [exec mvn install -DskiptTests]
    }

    visualize {
        cd $dpProjectDir
        #puts "ant"
        #exec ant compile-test
        puts "visualize"
        if { $argc < 2} {
            foreach workflowName $workflowNames {
                puts "visualize $workflowName"
                visualizeDpWorkflow $workflowName
            }
            return
        }
        set workflowName [lindex $argv 1]
        visualizeWorkflow $workflowName
    }
    test {
        #puts "ant"
        #exec ant compile-test
        puts "build done"
        if { $argc < 2} {
            foreach workflowName $testNames {
                puts "test $workflowName"
                testDpWorkflow $workflowName
            }
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
        searchDir $dpSrcBase $text
    }
}
    




