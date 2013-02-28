#!/usr/bin/perl

# Yingssu Tsai
# April 30, 2010

# find image number given starting image, starting image angle, oscillation angle, and image angle

# find_img.pl -startimg a -startphi b -osc c -endphi d

if ($#ARGV eq -1) {
        print "find_img.pl -startimg a -startphi b -osc c -endphi d \n";
        exit;
}

# print "\$#ARGV " . $#ARGV . "\n";

$i=0;

while ($i <= $#ARGV) {

        $var = $ARGV[$i];

        # print "var " . $var . "\n";

        if ($var eq "-startimg") {
                $startimg = $ARGV[$i+1];
                $i += 2;
        }
        elsif ($var eq "-startphi") {
                $startphi = $ARGV[$i+1];
                $i +=2;
        }
        elsif ($var eq "-osc") {
                $osc = $ARGV[$i+1];
                $i += 2;
        }
        elsif ($var eq "-endphi") {
                $endphi = $ARGV[$i+1];
                $i += 2;
        }
        else {
                print "find_img.pl -startimg a -startphi b -osc c -endphi d \n";
                exit;
        }

}

# print "startimg " . $startimg . "\n";
# print "startphi " . $startphi . "\n";
# print "osc " . $osc . "\n";
# print "endphi " . $endphi . "\n";


$imgnum = int ( ($endphi - $startphi) / $osc); 

# print "imgnum " . $imgnum . "\n";

$endimg = $startimg + $imgnum;

print $endimg . "\n";
