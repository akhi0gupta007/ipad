package com.kam

import java.util.Date

class Customer {
	String name
	String address
	String city
	String website
	String contact
	Date dateCreated 
	String activatedby
	String flow
	boolean enabled
	boolean isDeleted
	static hasMany = [documentTemplates:DocumentTemplate,projects:Project,emailGroups:EmailGroup]
	
	static constraints = {
		name(blank:false)
		address(nullable:true)
		city(nullable:true)
		website(nullable:true)
		contact(nullable:true)
		flow(nullable:true)
	}
	
	static mapping={
		documentTemplates cascade: 'all-delete-orphan'
		projects cascade: 'all-delete-orphan'
		emailGroups cascade: 'all-delete-orphan'
	}
}
