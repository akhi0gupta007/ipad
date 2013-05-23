package com.kam

import java.util.Date;

class Document {
	String name
	String status
	String documentNumber
	String updatedBy
	String discipline
	boolean isDeleted
	Customer customer
	Project project
	Date deadLine
	Date dateCreated=new Date()
	Date lastUpdated=new Date()
	int documentVersion
	static belongsTo = [documentTemplate:DocumentTemplate]
	
	static hasMany = [documentItemValue:DocumentItemValue,images:Image]
	
	static constraints = {
		name(blank:false)
		documentNumber(nullable:true)
		deadLine(nullable:true)
		documentVersion(nullable:true)
	}
	
	static mapping = {
		documentItemValue cascade: 'all-delete-orphan'
	}
	
}