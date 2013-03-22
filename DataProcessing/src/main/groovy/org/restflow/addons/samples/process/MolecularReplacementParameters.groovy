package org.restflow.addons.samples.process;

class MolecularReplacementParameters implements Serializable {
	Float intensity_cutoff_sigma;
	Float r_measure_cutoff_percent;
	String mr_model;
	String alt_mr_model;
	String cif_file;
	String space_group_number;
	String unit_cell_angstroms_degrees;
	Float resolution_angstroms;
	
	public void validate () {
		[ 'mr_model', 'alt_mr_model'].each { it->
			if ( "this.$it" == null ) {
				throw new Exception("$it must be defined in molecular_replacement");
			}
		}
			
		File f_mr_model = new File(mr_model);
		if (! f_mr_model.canRead() ) throw new Exception("file $mr_model is not readable");

		File f_alt_mr_model = new File(mr_model);
		if (! f_alt_mr_model.canRead() ) throw new Exception("file $alt_mr_model is not readable") ;

		if ( cif_file != null ) {
			File f_cif_file = new File(cif_file);
			if (! f_cif_file.canRead() ) throw new Exception("file $cif_file is not readable") ;
		}





		
	}
	
}
