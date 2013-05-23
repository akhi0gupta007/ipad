package com.kam

class Image {
	String name
	static belongsTo = [document:Document,ticket:Ticket]
    static constraints = {
    }
}
