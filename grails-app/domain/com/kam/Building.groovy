package com.kam

class Building {
	String buildingName
	String buildingNumber
	String address
	String zipCode
	String city
	String flow
	boolean isDeleted = false
	Date dateCreated
	Date lastUpdated
	Project project
	static hasMany = [floors:Floor,documents:Document,reports:Report]
	
	static belongsTo = []
	
	static mapping = {
		floors cascade: 'all-delete-orphan'
		documents cascade: 'all-delete-orphan'
		reports cascade: 'all-delete-orphan'
	}
	
    static constraints = {
		flow(nullable:true)
    }
}
