package com.kam

class Room {
	String roomId
	boolean isDeleted = false
	static hasMany = [documents:Document]
	static belongsTo = [floor:Floor]
    static constraints = {
    }
}
