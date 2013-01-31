
proc getLatestFile { dir pattern } {
    #set dir /data/scottm/Molrep/test/Molrep*
    set files [glob $dir pattern]

    set unsorted ""
    foreach file $files {
        lappend unsorted [list $file [file mtime $file]]
    }
    set sorted [lsort -integer -index 1 -decreasing $unsorted]

    set latest [lindex [lindex $sorted 0] 0]
    return $latest
}
