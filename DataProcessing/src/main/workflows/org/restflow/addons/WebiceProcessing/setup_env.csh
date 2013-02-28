#!/bin/csh -f

# set script dir to this script location

setenv WEBICE__SCRIPT_DIR `dirname $0`

# source module and px

source /etc/profile.d/modules.csh
source /etc/profile.d/px.csh

module purge
module load null
module load PX
# module load xds/201005

env
