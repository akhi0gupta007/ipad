package com.kam

import java.util.Date;

class CheckpointsEquipment {

	String sno
	String value
	String category
	String customerId
	
	Date dateCreated
	Date lastUpdated
	boolean deleted = false
	
    static constraints = {
		sno(nullable:true)
		category(nullable:true)
    }
	
	static mapping = {
		value sqlType: "varchar(6000)"
	}
}
