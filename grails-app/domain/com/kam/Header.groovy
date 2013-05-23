package com.kam

import java.awt.List;

class Header {

	String name
	String checkpointType
	static belongsTo=[documentItem:DocumentItem]
    static constraints = {
    }
}
