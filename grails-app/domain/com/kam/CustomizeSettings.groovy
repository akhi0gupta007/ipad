package com.kam

class CustomizeSettings {

	Customer customer
	String logo
	String backGround
	String navigation
	String links
	String theme
    static constraints = {
		logo(nullable:true)
		backGround(nullable:true)
		navigation(nullable:true)
		links(nullable:true)
		theme(nullable:true)
    }
}
