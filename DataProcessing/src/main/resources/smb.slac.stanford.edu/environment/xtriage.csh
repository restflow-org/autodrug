#!/bin/csh -f

source /etc/profile.d/modules.csh
source /etc/profile.d/px.csh

module purge
module load null

module load phenix

env
