package com.kam

class Excel {
    String user
    String file
    Date dateCreated

    static constraints = {
        user(blank: false)
        file(blank: false)
    }
}
