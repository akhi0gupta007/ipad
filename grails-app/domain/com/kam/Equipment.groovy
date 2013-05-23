package com.kam

class Equipment {
	String name
	String description
	String pictoGram
	String pictoGramAfw
	GroupNr groupNo
	Kast kast
	String buildYearOfBattery
	String buildYearOfArmature
	String buildYearOfEmergencyUnit
	boolean isDeleted = false
	boolean isMailSent = false
	Date deadLine
	Date dateCreated
	Date lastUpdated
	int customer
	String status
	String equipmentType
	String equipmentType2
	String createdBy
	String spec
	Light light
	Armatuur armatuur
	Battery battery
	Brand brand
	EmergencyUnitOfPrint emergencyUnitOfPrint
	Floor floor
    static constraints = {
		description(nullable:true)
		pictoGram(nullable:true)
		pictoGramAfw(nullable:true)
		groupNo(nullable:true)
		kast(nullable:true)
		buildYearOfBattery(nullable:true)
		buildYearOfArmature(nullable:true)
		buildYearOfEmergencyUnit(nullable:true)
		light(nullable:true)
		armatuur(nullable:true)
		battery(nullable:true)
		brand(nullable:true)
		emergencyUnitOfPrint(nullable:true)
		customer(nullable:true)
		status(nullable:true)
		equipmentType(nullable:true)
		equipmentType2(nullable:true)
		createdBy(nullable:true)
		groupNo(nullable:true)
		kast(nullable:true)
		spec(nullable:true)
		deadLine(nullable:true)
    }
	
	static hasMany = [checkpoints:EquipmentCheckpoint]
	static mapping = {
		spec type:"text"

	}
}