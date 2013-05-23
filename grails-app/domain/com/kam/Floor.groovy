package com.kam

class Floor {

	String floorNumber
	String floorDescription
	String floorMap
	String floorComment
	Date dateCreated
	Date lastUpdated
	Date revisionDate
	boolean isDeleted=false
	Building building
	static hasMany = [rooms:Room,documents:Document,equipments:Equipment]
	static belongsTo = []
    static constraints = {
		revisionDate(nullable:true)
		floorDescription(nullable:true)
		floorMap(nullable:true)
		floorComment(nullable:true)
    }
	
	static mapping={
		floorDescription type: "text"
		rooms cascade: 'all-delete-orphan'
		documents cascade: 'all-delete-orphan'
		equipments cascade: 'all-delete-orphan'
	}
}
