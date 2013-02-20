#!/bin/csh -f

# set script dir to this script location

setenv WEBICE__SCRIPT_DIR `dirname $0`

# source modules

source /etc/profile.d/modules.csh
source /etc/profile.d/px.csh

module purge
module load null

module load ccp4/6.2.0
module load refmac/5.6.0119 
module load solve 
module load xds 
module load arp_warp/7.2 
module load usf
module load cns 
module load snb 
module load imosflm 
module load HKL2000 
module load utils 
module load elves 
# module load sharp 
# module load shelx/20060317
module load ipmosflm/7.0.7
module load phenix
module load raddose/20080103
module load best/3.1

env
