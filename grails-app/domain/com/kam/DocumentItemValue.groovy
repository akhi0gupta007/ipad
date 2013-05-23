package com.kam
/**
 * This class is used to store values for DocumentItem and these values belong to its corresponding documentItem and Document
 */
class DocumentItemValue {
	String formElementValue
	String position
	String type
	String alignment
	static belongsTo = [documentItem:DocumentItem,document:Document]
	static hasMany=[questionValues:QuestionValue]
	static constraints = {
	}
	
	static mapping={
		formElementValue type: "text"
		questionValues cascade: 'all-delete-orphan'
	}
}
