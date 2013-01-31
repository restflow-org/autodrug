package org.restflow.addons.reporting;
import java.net.URI;

import org.springframework.web.util.UriTemplate;

class ReportHelper {
	
	public static TreeMap returnProductsMatchingUriTemplate (Map products_, String uriTemplate) {
		
		UriTemplate template = new UriTemplate(uriTemplate);
		
		def productMatches = [:];
		products_.each() { productUri, productValue ->
			if ( ! template.matches(productUri) ) {return};

			def uriVars = template.match (productUri);
			
			def matches = true;
			uriVars.each { key,val -> 
				if (val.contains("/")) {
					matches = false;
				}
			}
			if (!matches) return;
			
			//URI expanded = template.expand(uriVars);
			//if (productUri.equals( expanded ) ) {
			productMatches[productUri]=[:];
			
			productMatches[productUri]["value"]=productValue;
			productMatches[productUri]["uriVariables"]=uriVars;
				
			//}
		}
		return productMatches;
	}
	
	
	public static Map returnValueMapForMatchingUriTemplate (Map products_, String uriTemplate) {
		
		UriTemplate template = new UriTemplate(uriTemplate);
		
		def productMatches = [:];
		products_.each() { productUri, productValue ->
			if ( ! template.matches(productUri) ) {return};

			def uriVars = template.match (productUri);
			
			def matches = true;
			uriVars.each { key,val ->
				if (val.contains("/")) {
					matches = false;
				}
			}
			if (!matches) return;
			
			//URI expanded = template.expand(uriVars);
			//if (productUri.equals( expanded ) ) {
			productMatches[productUri]=[:];
			productMatches[productUri]['_uri_']=productUri;
			productMatches[productUri]['_value_']=productValue;
			productMatches[productUri].putAll (uriVars);
			//}
		}
		return productMatches;
	}
	
	public static Map returnTrimmedValueMapForMatchingUriTemplate (Map products_, String uriTemplate, String trim) {
		
		UriTemplate template = new UriTemplate(uriTemplate);
		
		def productMatches = [:];
		products_.each() { productUri, productValue ->
			if ( ! template.matches(productUri) ) {return};

			def uriVars = template.match (productUri);
			
			def matches = true;
			uriVars.each { key,val ->
				if (val.contains("/")) {
					matches = false;
				}
			}
			if (!matches) return;
			
			def trimmedUri = productUri.replace(trim,'');
			//URI expanded = template.expand(uriVars);
			//if (productUri.equals( expanded ) ) {
			//productMatches[trimmedUri]=[:];
			//productMatches[trimmedUri]['_uri_']=productUri;
			productMatches[trimmedUri]=productValue;
			//productMatches[trimmedUri].putAll (uriVars);
			//}
		}
		return productMatches;
	}
	
}
