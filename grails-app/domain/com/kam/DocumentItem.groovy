package com.kam
/**  
 * This Class Receives value from DocumentTemplate and generates form
 */
class DocumentItem {
	String formElementName
	String position
	String type
	String alignment
	boolean isDeleted
	String checkpointType
	boolean isHeader
	boolean isSignature
	static hasMany=[headers:Header,questions:Question]
	static belongsTo = [documentTemplate:DocumentTemplate]     //A documentItem belongs to DocumentTemplate
	
	static constraints = {
		checkpointType(nullable:true)
		isSignature(nullable:true)
	}
	
	static mapping={
		formElementName type: "text"
		headers cascade: 'all-delete-orphan'
		questions cascade: 'all-delete-orphan'
	}
}
