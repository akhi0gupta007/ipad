package com.kam

import java.util.Date;

/**
 * This class is a default template for generating form and is used by DocumentItem as a template
 */
class DocumentTemplate {
	String name
	String createdBy
	String discipline
	Date dateCreated
	Date lastUpdated
	boolean isDeleted
/**
 * Relationships below show , a DocumentTemplate can have many DocumentItems and belongs to a Customer
 */
	static hasMany = [documentItems:DocumentItem]
	static belongsTo = [customer:Customer]
	static constraints = {
		name(blank:false)
	}
	
	static mapping = {
		documentItems cascade: 'all-delete-orphan'
	}
	
}
