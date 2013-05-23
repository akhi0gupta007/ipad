package com.kam

class ReportValue {
	
	String value
	String type
	
	Floor floor
	Report report

    static constraints = {
		floor(nullable:true)
    }
	
	static mapping = {
		value sqlType: "varchar(6000)"
	}
}
