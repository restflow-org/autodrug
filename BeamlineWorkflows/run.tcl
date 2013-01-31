#!/bin/sh
# the next line restarts using -*-Tcl-*-sh \
         exec tclsh "$0" ${1+"$@"}
global env
global argv

set command [lindex $argv 0]

set project BeamlineWorkflows
set projectDir $env(WORKSPACE)/$project
set base /data/$env(USER)/$project
set lib $projectDir/lib
set classes $projectDir/classes
set classpath $lib/*:$classes
set src_base $env(WORKSPACE)/$project/
set runs_dir $base/test
#To get test data
#cd /data/username
#svn co https://smb.slac.stanford.edu/svn/resources/TestRestFlow/
set testData_dir /data/$env(USER)/TestRestFlow/

set testNames { PROJECT }
set workflowNames { PROJECT }

source $env(WORKSPACE)/BeamlineWorkflows/getLatestFile.tcl

proc testWorkflow {name} {
    global projectDir
    global src_base
    global classpath
    global base
    global runs_dir
    global testData_dir
    global env

    switch $name {
        AutoDrug-ValidateInput {
            cd $projectDir
            set infile $src_base/src/test/resources/org/restflow/addons/AutoDrug/inputs/1/infile.yaml
            catch {exec java -classpath $classpath ssrl.workflow.RestFlow -base $base/test -f classpath:org/restflow/addons/AutoDrug/workflows/TestAutoDrug-OptionList.yaml -w AutoDrug-ValidateInput.Test.Workflow -infile $infile -i strategy-file=$projectDir/src/test/resources/org/restflow/addons/AutoDrug/inputs/1/proteins.yaml  }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* AutoDrug*]
            puts "runDir: $rundir"
            puts [exec cat $rundir/_metadata/products.yaml]
        }
        AutoDrug {
            cd $projectDir
            set infile $src_base/src/test/resources/org/restflow/addons/AutoDrug/inputs/1/infile.yaml
            catch {exec java -classpath $classpath ssrl.workflow.RestFlow -base $base/test -f classpath:org/restflow/addons/AutoDrug/workflows/TestAutoDrug-OptionList.yaml -w AutoDrug.Test.Workflow -infile $infile -i strategy-file=$projectDir/src/test/resources/org/restflow/addons/AutoDrug/inputs/1/proteins.yaml -i testDataDir=$testData_dir -i testDataImageLookupMapPath=$projectDir/src/test/resources/org/restflow/addons/AutoDrug/inputs/1/testDataMap.yaml  }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* AutoDrug*]
            puts "runDir: $rundir"
            puts [exec cat $rundir/_metadata/products.yaml]
        }
        CollectSample {
            #TODO
            puts "CollectSample test not implemented"
        }
        ProcessSample {
            cd $projectDir
            set infile $src_base/src/test/resources/org/restflow/addons/ProcessSample/inputs/1/infile.yaml
            catch {exec java -classpath $classpath ssrl.workflow.RestFlow -base $base/test -f classpath:org/restflow/addons/ProcessSample/workflows/TestProcessSample-OptionList.yaml -w ProcessSample.Test.Workflow -infile $infile -i firstImagePath=${testData_dir}/TestCollect/cbf/B2/B2_1_00001.cbf  }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* AutoDrug*]
            puts "runDir: $rundir"
            puts [exec cat $rundir/_metadata/products.yaml]
        }
        CollectRun {
            cd $projectDir
            set infile $src_base/src/test/resources/org/restflow/addons/CollectRun/inputs/1/infile.yaml
            catch {exec java -classpath $classpath ssrl.workflow.RestFlow -base $base/test -f classpath:org/restflow/addons/CollectRun/workflows/TestCollectRun-OptionList.yaml -w CollectRun.Test.Workflow -infile $infile -i testDataDir=$testData_dir -i testDataImageLookupMapPath=$projectDir/src/test/resources/org/restflow/addons/CollectRun/inputs/1/testDataMap.yaml  }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* CollectRun*]
            puts "runDir: $rundir"
            puts [exec cat $rundir/_metadata/products.yaml]
        }
        CollectTwoImages {
            cd $projectDir
            set infile $src_base/src/test/resources/org/restflow/addons/CollectTwoImages/inputs/1/infile.yaml
            catch {exec java -classpath $classpath ssrl.workflow.RestFlow -base $base/test -f classpath:org/restflow/addons/CollectTwoImages/workflows/TestCollectTwoImages-OptionList.yaml -w CollectTwoImages.Test.Workflow -infile $infile -i screeningParamsPath=$projectDir/src/test/resources/org/restflow/addons/CollectTwoImages/inputs/1/screeningParams.yaml -i testDataDir=$testData_dir -i testDataImageLookupMapPath=$projectDir/src/test/resources/org/restflow/addons/CollectTwoImages/inputs/1/testDataMap.yaml  }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* Collect*]
            puts "runDir: $rundir"
            puts [exec cat $rundir/_metadata/products.yaml]
        }
        IndexSample {
            cd $projectDir
            set infile $src_base/src/test/resources/org/restflow/addons/IndexSample/inputs/1/infile.yaml
            catch {exec java -classpath $classpath ssrl.workflow.RestFlow -base $base/test -f classpath:org/restflow/addons/IndexSample/workflows/TestIndexSample-OptionList-Sim.yaml -w IndexSample.Test.Workflow -infile $infile -i testDataDir=$testData_dir -i testDataImageLookupMapPath=$projectDir/src/test/resources/org/restflow/addons/IndexSample/inputs/1/testDataMap.yaml  }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* IndexSample*]
            puts "runDir: $rundir"
            puts [exec cat $rundir/_metadata/products.yaml]
        }
        ScreenSample {
            cd $projectDir
            set infile $src_base/src/test/resources/org/restflow/addons/ScreenSample/inputs/1/infile.yaml
            catch {exec java -classpath $classpath ssrl.workflow.RestFlow -base $base/test -f classpath:org/restflow/addons/ScreenSample/workflows/TestScreenSample-OptionList-Sim.yaml -w ScreenSample.Test.Workflow -infile $infile -i testDataDir=$testData_dir -i testDataImageLookupMapPath=$projectDir/src/test/resources/org/restflow/addons/ScreenSample/inputs/1/testDataMap.yaml  }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* ScreenSample*]
            puts "runDir: $rundir"
            puts [exec cat $rundir/_metadata/products.yaml]
        }

        VisualizeWorkflows {
            cd $projectDir
            catch {exec java -classpath $classpath ssrl.workflow.RestFlow -base $base/test -f classpath:org/restflow/addons/util/workflows/visualizeBeamlineWorkflows.yaml -w BeamlineWorkflowsUtils.Workflow }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* Visualize*]
            puts $rundir
            puts [exec cat $rundir/_metadata/products.yaml]
        }



        default {puts "workflow not found"}
    }
}

proc visualizeWorkflow { name} {
    global projectDir
    global src_base
    global classpath
    global base
    global runs_dir
    switch $name {
        CollectTwoImages {
            catch {exec java -classpath $classpath ssrl.workflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/CollectTwoImages/workflows/TestCollectTwoImages-OptionList.yaml -i workflowName=CollectTwoImages.A.Workflow   } err
            puts $err
        }
        ProcessSample {
            catch {exec java -classpath $classpath ssrl.workflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/ProcessSample/workflows/TestProcessSample-OptionList.yaml -i workflowName=ProcessSample.A.Workflow} err
            puts $err
        }
        CollectSample {
            catch {exec java -classpath $classpath ssrl.workflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/CollectSample/workflows/TestCollectSample-OptionList.yaml -i workflowName=CollectSample.A.Workflow} err
            puts $err
        }
        CollectRun {
            catch {exec java -classpath $classpath ssrl.workflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/CollectRun/workflows/TestCollectRun-OptionList.yaml -i workflowName=CollectRun.A.Workflow   } err
            puts $err
        }
        ScreenSample {
            catch {exec java -classpath $classpath ssrl.workflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/ScreenSample/workflows/TestScreenSample-OptionList-Sim.yaml -i workflowName=ScreenSample.A.Workflow} err
            puts $err
        }
        IndexSample {
            catch {exec java -classpath $classpath ssrl.workflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/IndexSample/workflows/TestIndexSample-OptionList-Sim.yaml -i workflowName=IndexSample.A.Workflow   } err
            puts $err
        }
        BeamlineControl {
            catch {exec java -classpath $classpath ssrl.workflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/BeamlineControl/workflows/TestReadBeamlineValues-OptionList.yaml -i workflowName=ReadBeamlineValues.Workflow   } err
            puts $err
        }
        VisualizeWorkflows {
            catch {exec java -classpath $classpath ssrl.workflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/util/workflows/visualizeRoute.yaml -i workflowName=DataProcessingUtils.Workflow } err
            puts $err
        }
        AutoDrug {
            catch {exec java -classpath $classpath ssrl.workflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/AutoDrug/workflows/TestAutoDrug-OptionList.yaml -i workflowName=AutoDrug-ValidateInput.A.Workflow } err
            puts $err
            catch {exec java -classpath $classpath ssrl.workflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/AutoDrug/workflows/TestAutoDrug-OptionList.yaml -i workflowName=AutoDrug.A.Workflow } err
            puts $err
        }

    }
}


switch $command {
    build {
      puts [exec ant compile-test]
    }
    publish {
      puts [exec ant publish]
    }
    visualize {
        cd $projectDir
        #puts "ant"
        #exec ant compile-test
        puts "visualize"
        if { $argc < 2} {
            foreach workflowName $workflowNames {
                puts "visualize $workflowName"
                visualizeWorkflow $workflowName
            }
            return
        }
        set workflowName [lindex $argv 1]
        visualizeWorkflow $workflowName
    }
    test {
        #puts "ant"
        #exec ant compile-test
        if { $argc < 2} {
            foreach workflowName $testNames {
                puts "test $workflowName"
                testWorkflow $workflowName
            }
            return
        }
        set workflowName [lindex $argv 1]
        testWorkflow $workflowName
    }
    report {
        global runs_dir
        puts [exec java -classpath $classpath ssrl.workflow.RestFlow -base $runs_dir -report summary] 
    }
    module-create {

        if { $argc < 2} {
            error "module-create module-name"
        }

        set moduleName [lindex $argv 1]
        cd $projectDir
        file mkdir src/main/resources/edu/stanford/slac/smb/environment/$moduleName
        #mkdir src/main/resources/edu/stanford/slac/smb/environment/$moduleName/env.csh
        file mkdir src/main/workflows/org/restflow/addons/$moduleName
        file mkdir src/main/workflows/org/restflow/addons/$moduleName/actors
        file mkdir src/main/workflows/org/restflow/addons/$moduleName/workflows
        file mkdir src/test/resources/org/restflow/addons/$moduleName
        file mkdir src/test/resources/org/restflow/addons/$moduleName/inputs
        file mkdir src/test/resources/org/restflow/addons/$moduleName/inputs/1
        #mkdir src/test/resources/org/restflow/addons/$moduleName/inputs/1/infile.yaml
    }
    class-edit {
        cd $projectDir
        set groovyClasses [glob -nocomplain src/main/groovy/edu/stanford/slac/smb/samples/* *.groovy]
        lappend groovyClasses [glob -nocomplain src/main/groovy/org/restflow/addons/beamline/* *.groovy]
        set javaClasses [glob -nocomplain src/main/java/edu/stanford/slac/smb/samples/* *.java]

        eval exec gvim $groovyClasses $javaClasses
    }
    module-edit {

        if { $argc < 2} {
            error "module-edit module-name"
        }

        set moduleName [lindex $argv 1]
        cd $projectDir
        set actors [glob -nocomplain src/main/workflows/org/restflow/addons/$moduleName/actors/* *.yaml]
        set workflows [glob -nocomplain src/main/workflows/org/restflow/addons/$moduleName/workflows/* *.yaml]
        set reportBeans [glob -nocomplain src/main/workflows/org/restflow/addons/$moduleName/reports/* *.yaml]
        set reports [glob -nocomplain src/main/resources/org/restflow/addons/$moduleName/reports/* *.txt]
        set beans [glob -nocomplain src/main/workflows/org/restflow/addons/$moduleName/beans/* *.yaml]
        set workflowTests [glob -nocomplain src/test/workflows/org/restflow/addons/$moduleName/workflows/* *.yaml]
        set testData [glob -nocomplain src/test/resources/org/restflow/addons/$moduleName/inputs/1/* *.yaml]
        eval exec gvim $actors $workflows $beans $workflowTests $testData $reports $reportBeans
    }
    search {

        if { $argc < 2} {
            error "search text"
        }
        set text [lindex $argv 1]
        puts "searching for $text"
        set cmd [list find src -name "*" -exec grep -H $text \{\} \;]
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
        

}
