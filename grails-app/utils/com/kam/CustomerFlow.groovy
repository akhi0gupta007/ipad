package com.kam

public enum CustomerFlow {

	PROJECTS("Projects"),BUILDINGS("Buildings"),FLOORS("Floors"),ROOMS("Rooms"),DISCIPLINES("Disciplines"),EMERGENCYlIGHTS("Emergency Lights"),TICKETS("Tickets"),
	
	private final String value
	
	CustomerFlow(String value) {
	 this.value = value
	 }
	public String toValue() {
	 return value
	  }
}
