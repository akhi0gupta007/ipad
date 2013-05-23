package com.kam

public enum reportVisual {

	visual1("Geen mechanische gebreken die de veilgheid in gevaar kunnen brengen"),visual2('De installatietekeningen zijn aanwezig en overenkomstig met de werkelijke sitatie'),visual3('De juiste pictogrammen zijn toegepast'),visual4('Er is voldoende noodverlichting aanwezig om de vluchtweg op een juiste wije te verlichten')
	
	private final String value
	
	reportVisual(String value) {
	 this.value = value
	 }
	public String toValue() {
	 return value
	  }
}
