package com.kam

import static org.junit.Assert.*
import org.junit.*

class UserRoleTests {

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
	
	void testSaveRole(){
		def user=new User(firstName:'firstname',middleName:'middlename',lastName:'lastname',email:'email@email.com',username:'anyuser',password:'anyuser',accountLocked: false,enabled: true,accountExpired:false,passwordExpired:false);
		assertNotNull user.save()
		def role=Role.findWhere(authority:'ROLE_USER')
		
		def userrole=new UserRole(user:user,role:role)
		assertNotNull userrole.save()
		
		
	}
}
