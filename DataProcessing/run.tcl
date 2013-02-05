#!/bin/sh
# the next line restarts using -*-Tcl-*-sh \
         exec tclsh "$0" ${1+"$@"}
global env
global argv

set command [lindex $argv 0]

set project DataProcessing
set projectDir $env(WORKSPACE)/$project
set base /data/$env(USER)/$project
set lib $projectDir/target/dependency
set classes $projectDir/target/classes
set classpath $lib/*:$classes
set src_base $env(WORKSPACE)/$project/
set runs_dir $base/test

puts $classpath

set testNames { DataProcessing }
set workflowNames { DataProcessing }

source $projectDir/getLatestFile.tcl

proc testWorkflow {name} {
    global env
    global projectDir
    global src_base
    global classpath
    global base
    global runs_dir

    switch $name {
        ProcessImages {
            cd $projectDir
            set infile $src_base/src/test/resources/org/restflow/addons/ProcessImages/inputs/1/infile.yaml
            catch {exec java -classpath $classpath org.restflow.RestFlow -base $base/test -f classpath:org/restflow/addons/ProcessImages/workflows/TestProcessImages-A.yaml -w ProcessImages.Test.Workflow -infile $infile -i firstImagePath=$env(WORKSPACE)/TestDataProcessing/1/B1_1_00001.cbf -i mr_model=src/test/resources/org/restflow/addons/ProcessImages/inputs/1/2MBW.pdb  }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* ProcessImages*]
            puts $rundir
            puts [exec cat $rundir/_metadata/products.yaml]
        }


        ProcessImages2 {
            cd $projectDir
            set infile $src_base/src/test/resources/org/restflow/addons/ProcessImages/inputs/2/infile.yaml
            catch {exec java -classpath $classpath org.restflow.RestFlow -base $base/test -f classpath:org/restflow/addons/ProcessImages/workflows/ProcessImagesTest-R.yaml -w ProcessImages.Test.Workflow -infile $infile -i firstImagePath=$env(WORKSPACE)/TestDataProcessing/1/B1_1_00001.cbf -i mr_model=src/test/resources/org/restflow/addons/ProcessImages/inputs/2/2MBW.pdb  }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* ProcessImages*]
            puts $rundir
            puts [exec cat $rundir/_metadata/products.yaml]
        }

        DataProcessing {
            cd $projectDir
            set infile $src_base/src/test/resources/org/restflow/addons/DataProcessing/inputs/1/infile.yaml
            catch {exec java -classpath $classpath org.restflow.RestFlow -base $base/test -f classpath:org/restflow/addons/DataProcessing/workflows/DataProcessingTest-P.yaml -w DataProcessing.Test.Workflow -infile src/test/resources/org/restflow/addons/DataProcessing/inputs/1/DataProcessing.in -i firstImagePath=$env(WORKSPACE)/TestDataProcessing/1/B1_1_00001.cbf  }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* DataProcessing*]
            puts $rundir
            puts [exec cat $rundir/_metadata/products.yaml]
        }

        freeR {
            cd $projectDir
            catch {exec java -classpath $classpath org.restflow.RestFlow -base $base/test -f classpath:org/restflow/addons/freeR/workflows/freeRCmd.yaml -w freeRCmd.Workflow -i mtz=src/test/resources/org/restflow/addons/freeR/inputs/1/trunc1.mtz }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* freeR*]
            puts $rundir
            puts [exec cat $rundir/_metadata/products.yaml]
        }
        truncate {
            cd $projectDir
            catch {exec java -classpath $classpath org.restflow.RestFlow -base $base/test -f classpath:org/restflow/addons/truncate/workflows/truncateCmd.yaml -w truncateCmd.Workflow -i resolution=0.0 -i mtz=src/test/resources/org/restflow/addons/truncate/inputs/1/scala1.mtz }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* truncate*]
            puts $rundir
            puts [exec cat $rundir/_metadata/products.yaml]
        }
        truncate2 {
            cd $projectDir
            catch {exec java -classpath $classpath org.restflow.RestFlow -base $base/test -f classpath:org/restflow/addons/truncate/workflows/truncateCmd.yaml -w truncateCmd.Workflow -i resolution=1.0 -i mtz=src/test/resources/org/restflow/addons/truncate/inputs/1/scala1.mtz }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* truncate*]
            puts $rundir
            puts [exec cat $rundir/_metadata/products.yaml]
        }
        scala {
            cd $projectDir
            catch {exec java -classpath $classpath org.restflow.RestFlow -base $base/test -f classpath:org/restflow/addons/scala/workflows/scalaCmd.yaml -w scalaCmd.Workflow -i isigmaCutoff=1.0 -i mtz=src/test/resources/org/restflow/addons/scala/inputs/1/point1.mtz }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* scalaCmd*]
            puts $rundir
            puts [exec cat $rundir/_metadata/products.yaml]
        }
        xtriage {
            cd $projectDir
            catch {exec java -classpath $classpath org.restflow.RestFlow -base $base/test -f classpath:org/restflow/addons/xtriage/workflows/xtriageCmd.yaml -w xtriage.Test.Workflow -i mtz=src/test/resources/org/restflow/addons/xtriage/inputs/1/point1.mtz }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* xtriageCmd*]
            puts $rundir
            puts [exec cat $rundir/_metadata/products.yaml]
        }
        pointless {
            cd $projectDir
            catch {exec java -classpath $classpath org.restflow.RestFlow -base $base/test -f classpath:org/restflow/addons/pointless/workflows/testPointless.yaml -w pointless.Test.Workflow -i xdsascii=src/test/resources/org/restflow/addons/pointless/inputs/1/XDS_ASCII.HKL  -i validatePath=${projectDir}/src/test/resources/org/restflow/addons/pointless/outputs/1/out.yaml  }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* pointlessCmd*]
            puts $rundir
            puts [exec cat $rundir/_metadata/products.yaml]

            catch {exec java -classpath $classpath org.restflow.RestFlow -base $base/test -f classpath:org/restflow/addons/pointless/workflows/testPointless.yaml -w pointless.Test.Workflow -i xdsascii=src/test/resources/org/restflow/addons/pointless/inputs/2/XDS_ASCII.HKL  -i validatePath=${projectDir}/src/test/resources/org/restflow/addons/pointless/outputs/2/out.yaml  }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* pointlessCmd*]
            puts $rundir
        }
        xds {
            cd $projectDir
            catch {exec java -classpath $classpath org.restflow.RestFlow -base $base/test -f classpath:org/restflow/addons/xds/workflows/testXds.yaml -w xds.Test.Workflow -infile src/test/resources/org/restflow/addons/xds/inputs/1/in.yaml -i firstImagePath=$env(WORKSPACE)/TestDataProcessing/1/B1_1_00001.cbf }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* xdsCmd*]
            puts $rundir
            puts [exec cat $rundir/_metadata/products.yaml]
        }

        xds-parser {
            cd $projectDir
            catch {exec java -classpath $classpath org.restflow.RestFlow -base $base/test -f classpath:org/restflow/addons/xds/workflows/testXdsParser.yaml -w xds.TestParser.Workflow -i xdsStdoutPath=src/test/resources/org/restflow/addons/xds/parser/1/stdout.txt -i validatePath=${projectDir}/src/test/resources/org/restflow/addons/xds/parser/1/parser-out.yaml }   err
            puts $err
            set rundir [getLatestFile $runs_dir/* xds*]
            puts $rundir
            puts [exec cat $rundir/_metadata/products.yaml]
        }


        getImgHeader {
            cd $projectDir
            catch {exec java -classpath $classpath org.restflow.RestFlow -base $base/test -f classpath:org/restflow/addons/getImgHeader/workflows/getImgHeaderCmd.yaml -w getImgHeader.Test.Workflow -i imagePath=${projectDir}/src/test/resources/org/restflow/addons/getImgHeader/inputs/1/B1_1_00001.cbf  -i validatePath=${projectDir}/src/test/resources/org/restflow/addons/getImgHeader/inputs/1/header.yaml }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* getImgHeaderCmd*]
            puts $rundir
            puts [exec cat $rundir/_metadata/products.yaml]
        }
        labelit {
            cd $projectDir
            catch {exec java -classpath $classpath org.restflow.RestFlow -base $base/test -f classpath:org/restflow/addons/labelit/workflows/labelitTest.yaml -w labelit.Test.Workflow -i image1=${projectDir}/src/test/resources/org/restflow/addons/labelit/inputs/1/B2_0001.cbf -i image2=${projectDir}/src/test/resources/org/restflow/addons/labelit/inputs/1/B2_0002.cbf -i validatePath=${projectDir}/src/test/resources/org/restflow/addons/labelit/inputs/1/output.yaml   }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* labelit*]
            puts $rundir
            puts [exec cat $rundir/_metadata/products.yaml]
        }

        ScoreSample {
            cd $projectDir
            catch {exec java -classpath $classpath org.restflow.RestFlow -base $base/test -f classpath:org/restflow/addons/ScoreSample/workflows/ScoreSampleTest.yaml -w ScoreSample.Test.Workflow -i image1=${projectDir}/src/test/resources/org/restflow/addons/ScoreSample/inputs/1/B2_0001.cbf -i image2=${projectDir}/src/test/resources/org/restflow/addons/ScoreSample/inputs/1/B2_0002.cbf -i validatePath=${projectDir}/src/test/resources/org/restflow/addons/ScoreSample/inputs/1/output.yaml -infile ${projectDir}/src/test/resources/org/restflow/addons/ScoreSample/inputs/1/infile.yaml }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* ScoreSample*]
            puts $rundir
            puts [exec cat $rundir/_metadata/products.yaml]
        }

        mosflm {
            cd $projectDir
            catch {exec java -classpath $classpath org.restflow.RestFlow -base $base/test -f classpath:org/restflow/addons/mosflm/workflows/mosflmTest.yaml -w mosflm.Test.Workflow -i image1=${projectDir}/src/test/resources/org/restflow/addons/mosflm/inputs/1/B2_0001.cbf -i image2=${projectDir}/src/test/resources/org/restflow/addons/mosflm/inputs/1/B2_0002.cbf -i validatePath=${projectDir}/src/test/resources/org/restflow/addons/mosflm/inputs/1/output.yaml -infile ${projectDir}/src/test/resources/org/restflow/addons/mosflm/inputs/1/infile.yaml -i gen=${projectDir}/src/test/resources/org/restflow/addons/mosflm/inputs/1/index.gen -i matrix=${projectDir}/src/test/resources/org/restflow/addons/mosflm/inputs/1/index.mat  }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* mosflm*]
            puts $rundir
            puts [exec cat $rundir/_metadata/products.yaml]
        }
        raddose {
            cd $projectDir
            catch {exec java -classpath $classpath org.restflow.RestFlow -base $base/test -f classpath:org/restflow/addons/raddose/workflows/raddoseTest.yaml -w raddose.Test.Workflow -i validatePath=${projectDir}/src/test/resources/org/restflow/addons/raddose/inputs/1/output.yaml  -infile ${projectDir}/src/test/resources/org/restflow/addons/raddose/inputs/1/infile.yaml   }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* raddose*]
            puts $rundir
            puts [exec cat $rundir/_metadata/products.yaml]
        }

        Strategy {
            cd $projectDir
            catch {exec java -classpath $classpath org.restflow.RestFlow -base $base/test -f classpath:org/restflow/addons/Strategy/workflows/StrategyTest.yaml -w Strategy.Test.Workflow -i validatePath=${projectDir}/src/test/resources/org/restflow/addons/Strategy/inputs/1/output.yaml  -infile ${projectDir}/src/test/resources/org/restflow/addons/Strategy/inputs/1/infile.yaml -i image1=${projectDir}/src/test/resources/org/restflow/addons/Strategy/inputs/1/B2_0001.cbf -i image2=${projectDir}/src/test/resources/org/restflow/addons/Strategy/inputs/1/B2_0002.cbf  }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* Strategy*]
            puts $rundir
            puts [exec cat $rundir/_metadata/products.yaml]
        }

        Molrep {
            cd $projectDir
            set infile $src_base/src/test/resources/org/restflow/addons/Molrep/inputs/1/infile.yaml
            exec java -classpath $classpath org.restflow.RestFlow -base $runs_dir -f classpath:org/restflow/addons/Molrep/workflows/Molrep.yaml -w Molrep.Workflow -infile $infile
            set rundir [getLatestFile $runs_dir/* Molrep*]
            puts $rundir
            puts [exec cat $rundir/_metadata/products.yaml]
        }
        MolecularReplacement {
            cd $projectDir
            set infile $src_base/src/test/resources/org/restflow/addons/MolecularReplacement/inputs/1/infile.yaml
            catch {exec java -classpath $classpath org.restflow.RestFlow -base $base/test -f classpath:org/restflow/addons/MolecularReplacement/workflows/MolecularReplacementCmd.yaml -w MolecularReplacementCmd.Workflow -i mtz=src/test/resources/org/restflow/addons/MolecularReplacement/inputs/1/mr.mtz -i pdb=src/test/resources/org/restflow/addons/MolecularReplacement/inputs/1/mr.pdb -i refiType=REST -i numCycles=10 -i rcutoff=0.4} err
            puts $err
            set rundir [getLatestFile $runs_dir/* MolecularReplacement*]
            puts $rundir
            puts [exec cat $rundir/_metadata/products.yaml]
        }
        refmac-nocif {
            cd $projectDir
            exec java -classpath $classpath org.restflow.RestFlow -base $runs_dir -f classpath:org/restflow/addons/refmac/workflows/refmacCmd.yaml -w refmacCmd.Workflow -i mtz=src/test/resources/org/restflow/addons/refmac/inputs/1/mr.mtz -i pdb=src/test/resources/org/restflow/addons/refmac/inputs/1/mr.pdb -i refiType=REST -i numCycles=10 -i rcutoff=0.4
            set rundir [getLatestFile $runs_dir/* refmac*]
            puts $rundir
            puts [exec cat $rundir/_metadata/products.yaml]

        }
        refmac-cif {
            cd $projectDir
            exec java -classpath $classpath org.restflow.RestFlow -base $runs_dir -f classpath:org/restflow/addons/refmac/workflows/refmacCmd.yaml -w refmacCmd.Workflow -i mtz=src/test/resources/org/restflow/addons/refmac/inputs/2/mr.mtz -i pdb=src/test/resources/org/restflow/addons/refmac/inputs/2/mr.pdb -i refiType=REST -i numCycles=10 -i rcutoff=0.4 -i cif=src/test/resources/org/restflow/addons/refmac/inputs/2/TLK.cif
            set rundir [getLatestFile $runs_dir/* refmac*]
            puts $rundir
            puts [exec cat $rundir/_metadata/products.yaml]

        }
        fftMap {
            cd $projectDir
            catch {exec java -classpath $classpath org.restflow.RestFlow -base $base/test -f classpath:org/restflow/addons/fftMap/workflows/fftMapCmd.yaml -w fftMapCmd.Workflow -i mtz=src/test/resources/org/restflow/addons/fftMap/inputs/1/in.mtz} err
            puts $err
            set rundir [getLatestFile $runs_dir/* fftMap*]
            puts $rundir
            puts [exec cat $rundir/_metadata/products.yaml]
        }
        peakMax {
            cd $projectDir
            catch {exec java -classpath $classpath org.restflow.RestFlow -base $base/test -f classpath:org/restflow/addons/peakMax/workflows/peakMaxCmd.yaml -w peakMaxCmd.Workflow -i sigma=3.0 -i map=src/test/resources/org/restflow/addons/peakMax/inputs/1/in.map} err
            puts $err
            set rundir [getLatestFile $runs_dir/* peakMax*]
            puts $rundir
            puts [exec cat $rundir/_metadata/products.yaml]
        }


        VisualizeWorkflows {
            cd $projectDir
            catch {exec java -classpath $classpath org.restflow.RestFlow -base $base/test -f classpath:org/restflow/addons/util/workflows/visualizeRoute.yaml -w DataProcessingUtils.Workflow }  err
            puts $err
            set rundir [getLatestFile $runs_dir/* Visualize*]
            puts $rundir
            puts [exec cat $rundir/_metadata/products.yaml]
        }



    }
}

proc visualizeWorkflow { name} {
    global projectDir
    global src_base
    global classpath
    global base
    global runs_dir
    switch $name {
        DataProcessing {
             catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/DataProcessing/workflows/DataProcessing-P.yaml -i workflowName=DataProcessing.Workflow} err
            puts $err
            catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/DataProcessing/workflows/DataProcessingCmd.yaml -i workflowName=DataProcessingCmd.Workflow}
        }
        freeR {
            catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/freeR/workflows/freeRCmd.yaml -i workflowName=freeRCmd.Workflow} err
            puts $err
        }
        truncate {
            catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/truncate/workflows/truncateCmd.yaml -i workflowName=truncateCmd.Workflow} err
            puts $err
        }
        scalaCmd {
            catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/scala/workflows/scalaCmd.yaml -i workflowName=scalaCmd.Workflow} err
            puts $err
        }
        scala {
            catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/scala/workflows/scala.yaml -i workflowName=scala.Workflow} err
            puts $err
        }
        xtriage {
            catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/xtriage/workflows/xtriage.yaml -i workflowName=xtriage.Workflow} err
            puts $err
        }
        pointless {
            catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/pointless/workflows/pointless.yaml -i workflowName=pointless.Workflow} err
            puts $err
        }
        xds {
            catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/xds/workflows/xds.yaml -i workflowName=xds.Workflow} err
            puts $err
        }
        xdsParams {
            catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/xds/workflows/xdsParams.yaml -i workflowName=xdsParams.Workflow} err
            puts $err
        }
        getImgHeader {
            catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/getImgHeader/workflows/getImgHeaderCmd.yaml -i workflowName=getImgHeader.Test.Workflow} err
            puts $err
            catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/getImgHeader/workflows/getImgHeader.yaml -i workflowName=getImgHeader.Workflow} err
            puts $err
        }
        ProcessImages {
            catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/ProcessImages/workflows/ProcessImages-A.yaml -i workflowName=ProcessImages.A.Workflow} err
            puts $err
        }
        Strategy {
            catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/Strategy/workflows/StrategyLookup.yaml -i workflowName=Strategy.Lookup.Workflow} err
            puts $err
            catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/Strategy/workflows/StrategyTest.yaml -i workflowName=Strategy.Test.Workflow} err
            puts $err
        }
        ScoreSample {
            catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/ScoreSample/workflows/ScoreSample.yaml -i workflowName=ScoreSample.A.Workflow} err
            puts $err
        }
        labelit {
            catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/labelit/workflows/labelit.yaml -i workflowName=labelit.A.Workflow} err
            puts $err
            catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/labelit/workflows/labelitMosflm.yaml -i workflowName=labelit.B.Workflow} err
            puts $err
        }
        labelitTest {
            catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/labelit/workflows/labelitTest.yaml -i workflowName=labelit.Test.Workflow} err
            puts $err
        }
        mosflm {
            catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/mosflm/workflows/mosflm.yaml -i workflowName=mosflm.A.Workflow} err
            puts $err
            catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/mosflm/workflows/mosflmTest.yaml -i workflowName=mosflm.Test.Workflow} err
            puts $err
        }
        raddose {
            catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/raddose/workflows/raddoseTest.yaml -i workflowName=raddose.Test.Workflow} err
            puts $err

            catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/raddose/workflows/raddose.yaml -i workflowName=raddose.A.Workflow   } err
            puts $err
        }

        Molrep {
            exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/Molrep/workflows/Molrep.yaml -i workflowName=Molrep.Workflow
        }
        refmac {
            exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/refmac/workflows/refmac.yaml -i workflowName=refmac.Workflow
            exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/refmac/workflows/refmacCmd.yaml -i workflowName=refmacCmd.Workflow
        }
        MolecularReplacement {
             catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/MolecularReplacement/workflows/MolecularReplacement.yaml -i workflowName=MolecularReplacement.Workflow} err
            puts $err
            catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/MolecularReplacement/workflows/MolecularReplacementCmd.yaml -i workflowName=MolecularReplacementCmd.Workflow}
        }
        fftMap {
            exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/fftMap/workflows/fftMapCmd.yaml -i workflowName=fftMapCmd.Workflow
        }
        peakMax {
            exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/peakMax/workflows/peakMaxCmd.yaml -i workflowName=peakMaxCmd.Workflow
        }
        VisualizeWorkflows {
            catch {exec java -classpath $classpath org.restflow.RestFlow -f classpath:tools/visualize.yaml -base $base/visualize -i restflowFile=classpath:org/restflow/addons/util/workflows/visualizeRoute.yaml -i workflowName=DataProcessingUtils.Workflow } err
            puts $err
        }
        default {
           puts "workflow not recognized"
        }
    }
}


switch $command {
    build {
        cd $projectDir
        puts "ant"
        puts [exec ant compile-test]
    }
    publish {
        cd $projectDir
        puts "publish"
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
        puts "build done"
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
        file mkdir src/test/resources/org/restflow/addons/$moduleName/workflows
        #mkdir src/test/resources/org/restflow/addons/$moduleName/inputs/1/infile.yaml
    }
    class-edit {
        cd $projectDir
        set groovyClasses [glob -nocomplain src/main/groovy/org/restflow/addons/samples/project/* *.groovy]
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
        cd $projectDir
        set actors [glob -nocomplain src/main/workflows/org/restflow/addons/$moduleName/actors/* *.yaml]
        set workflows [glob -nocomplain src/main/workflows/org/restflow/addons/$moduleName/workflows/* *.yaml]
        set workflowTests [glob -nocomplain src/test/workflows/org/restflow/addons/$moduleName/workflows/* *.yaml]
        set beans [glob -nocomplain src/main/workflows/org/restflow/addons/$moduleName/beans/* *.yaml]
        set testData [glob -nocomplain src/test/resources/org/restflow/addons/$moduleName/inputs/1/* *.yaml]
        set testOutData [glob -nocomplain src/test/resources/org/restflow/addons/$moduleName/outputs/1/* *.yaml]
        eval exec gvim $actors $workflows $beans $workflowTests $testData $testOutData
    }
    search {

        if { $argc < 2} {
            error "search text"
        }
        set text [lindex $argv 1]
        puts "searching for $text"
        #puts [exec find . -name DataProcessing-P.yaml]
        #set cmd [list find . -name "*" -exec grep -H $text '\{\}' \;]
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
