#!/usr/bin/perl

# for use with workflow

$imageDir = $ARGV[0];
$prefix = $ARGV[1];
$imageType = $ARGV[2];
$imgNumDigits = $ARGV[3];
$startNum = $ARGV[4];
$endNum = $ARGV[5];

# the first and last image numbers cannot be identical
#if ($startNum == $endNum) {
#   print "There is only one image. A comparison cannot be made.\n"; 
#   exit;
#} 

# the last image cannot have a smaller image number than the first image number
#elsif ($endNum < $startNum) {
#   print "The number for the last image is less than the number for the first image.\n";
#   exit;
#}


# set up initial values
$prevstart = -1;
$prevrange = -1;

# go through each of the images, from the first image to the last image, incrementing by 1
for ($numfile = $startNum; $numfile < $endNum+1; $numfile = $numfile+1) {

#### set the string to make the image number
   $currnum = "";

#### the number of "?" will be substituted by numbers to create the entire file name
   $filelength = length($numfile);

# debugging info
# print "filelength " . $filelength . "\n";

#### find the difference between the raw image number and the number needed for the file name 
   $difflength = $imgNumDigits - $filelength;

# debugging info
# print "difflength " . $difflength . "\n"; 

#### error if the difference is less than 0
   if ($difflength < 0) {
     print "The image number is greater than allowed in the file name.\n";
     exit;
   }

#### variable for changing difference in characters for the image number 
   $tracklength = $difflength;

#### leading "0" for the image number 
   while ($tracklength != 0) {
      $currnum = $currnum . "0";
      $tracklength  = $tracklength - 1;
   }

# debugging info
# print "currnum " . $currnum . "\n";

#### set the file image number 
   $currnum = $currnum . $numfile;
#### set the current file name
   $currentfile = $imageDir . $prefix . "_" . $currnum . "." . $imageType; 

# debugging info
# print "currentfile " . $currentfile . "\n";

#### getImgHeader <file> | grep OSC_START | awk '{print $2}'
   $startline = "getImgHeader " . $currentfile . " | grep OSC_START | awk '{print \$2}'";
   $startout = `$startline`;
   chomp($startout);

# debugging info
# print "startout " . $startout . "\n";

#### check if the file contains header information
   if ($startout eq "") {
      print "The file " . $currentfile . " does not contain image header information.\n";
      exit;
   }

#### getImgHeader <file> | grep OSC_RANGE | awk '{print $2}'
   $rangeline = "getImgHeader " . $currentfile . " | grep OSC_RANGE | awk '{print \$2}'";
   $rangeout = `$rangeline`;
   chomp($rangeout);

# debugging info
# print "rangeout " . $rangeout . "\n";

# debugging info
# print "prevstart " . $prevstart . "\n";
# print "prevrange " . $prevrange . "\n";

#### calculated the expected oscillation start for this image
#### get values from the previous start and range of oscillation
   $prevnum = $prevstart + $prevrange;

# debugging info
# print "prevnum " . $prevnum . "\n";

#### check for consecutive angles
#### instead of checking if $startout is equal to $prevnum
#### need to check if difference is greater than 0.01
#### otherwise the PILATUS data will always be notequal
   if (($startout - $prevnum > 0.01) && ($prevrange != -1) && ($prevstart != -1)) {
   	  $numimg = $numfile - 1;
      print $numimg . "\n";
      exit;
   }

#### make the current values the previous values for the next cycle
   $prevstart = $startout;
   $prevrange = $rangeout;

}

print "0\n";