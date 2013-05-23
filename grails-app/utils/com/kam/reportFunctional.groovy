package com.kam

public enum reportFunctional {

	functional1("De noodverlichting functioneerd in normat bednjt"),functional2('De noodverlichting functioneerd in nood bednjt'),functional3('De noodverlichting functioneerd in normat bednjt minimaal een 1uur')
	
	
	private final String value
	
	reportFunctional(String value) {
	 this.value = value
	 }
	public String toValue() {
	 return value
	  }
}
