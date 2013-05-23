package com.kam

class EquipmentCheckpoint {
	String checkpointDescription
	String status
	String value
	String priority
	String priorityDate
	String comment
	String floorId
	String checkpointType
	String reportId
	Equipment equipment
	
    static constraints = {
		status(nullable:true)
		value(nullable:true)
		priority(nullable:true)
		comment(nullable:true)
		priorityDate(nullable:true)
		floorId(nullable:true)
		checkpointType(nullable:true)
		reportId(nullable:true)
    } 
	static belongsTo = [equipment:Equipment]
	static hasMany = [equipmentImages:EquipmentImage]
	
}
