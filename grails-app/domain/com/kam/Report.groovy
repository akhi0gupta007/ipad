package com.kam

class Report {

	String reportName
	String projectName
	String projectNumber
	String reviewedBy
	Date dateCreated
	Date lastUpdated
	
	static belongsTo = [building:Building]
	
	static hasMany = [reportValues:ReportValue]
	
	static mapping = {
		reportValues cascade: 'all-delete-orphan'
	}
	
    static constraints = {
		projectName(nullable:true)
		projectNumber(nullable:true)
		
    }
}
