package com.kam

import static org.junit.Assert.*
import org.junit.*
import org.springframework.validation.Errors;

class UserIntegrationTests {

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
	
	void testUserSave(){
		def user=new User(firstName:'firstname',middleName:'middlename',lastName:'lastname',email:'email@email.com',username:'anyuser',password:'anyuser',accountLocked: false,enabled: true,accountExpired:false,passwordExpired:false);
		assertNotNull user.save()
		assertNotNull user.username
		assertNotNull user.password
	}
	
	void testSaveFailed(){
		def user=new User(firstName:'firstname',middleName:'middlename',lastName:'lastname',email:'email@email.com',username:'superuser',password:'superuser',accountLocked: false,enabled: true,accountExpired:false,passwordExpired:false);
		assertFalse user.validate()
		assertTrue user.hasErrors()
		def error=user.errors
		assertEquals 'unique',error.getFieldError("username").code
	}
	
	void testBlankFieldSave(){
		def user=new User(firstName:'firstname',middleName:'middlename',lastName:'lastname',email:'',username:'',password:'',accountLocked: false,enabled: true,accountExpired:false,passwordExpired:false);
		assertFalse user.validate()
		assertTrue user.hasErrors()
		def error=user.errors
		assertEquals 'blank',error.getFieldError("username").code
		assertEquals 'blank',error.getFieldError("password").code
		assertEquals 'blank',error.getFieldError("email").code
	}
	
	
	
	
}
