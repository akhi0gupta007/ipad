package com.kam
/**
 * Class to get information about ongoing projects
 */
class Project{
	
	String projectNumber
	String projectName
	String projectTitle
	String address
	String city
	String docname
	boolean isDeleted = false
	Date dateCreated=new Date()
	Date lastUpdated=new Date()
	static hasMany = [documents:Document,buildings:Building]
	static belongsTo = [customer:Customer]
	
    static constraints = {
		projectTitle(nullable:true)
		docname(nullable:true)
    }
	
	static mapping = {
		buildings cascade: 'all-delete-orphan'
		documents cascade: 'all-delete-orphan'
	}
	
}
