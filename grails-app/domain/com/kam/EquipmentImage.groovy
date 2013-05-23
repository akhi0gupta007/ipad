package com.kam

class EquipmentImage {
	String imageName
    static constraints = {
    }
	static belongsTo = [equipmentCheckpoint:EquipmentCheckpoint]
}
