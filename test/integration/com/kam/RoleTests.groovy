package com.kam

import static org.junit.Assert.*
import org.junit.*
import org.springframework.validation.Errors;


class RoleTests {

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testSomething() {
       
    }
	
	void testRoleSave(){
		def role=new Role(authority:"NEW_ROLE")
		assertNotNull role.save()
	}
}
