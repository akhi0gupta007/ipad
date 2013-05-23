package com.kam

import static org.junit.Assert.*

import java.util.Date;

import org.junit.*

class ProjectIntegrationTests {

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
        //fail "Implement me"
		
    }
	void testProjectSave(){
		def customer = new Customer(name:"Samsung Asia",address:"8950 gurgaon",city:"gurgaon",website:"www.nokia.com",contact:"9990541157",activatedby:"superuser",enabled:true)
			assertNotNull customer.save()
			def project1 = new Project(projectId:"KAM2",customer:customer)
			assertNotNull project1.save()
			def building1 = new Building(buildingName:"ER",project:project1)
			assertNotNull building1.save()
			def floor1 = new Floor(floorName:"11",building:building1)
			assertNotNull floor1.save()
			def floor2 = new Floor(floorName:"12",building:building1)
			assertNotNull floor2.save()
			def room1 = new Room(roomId:"111",floor:floor1)
			assertNotNull room1.save()
			def room2 = new Room(roomId:"112",floor:floor1)
			assertNotNull room2.save()
			def room3 = new Room(roomId:"113",floor:floor2)
			assertNotNull room3.save()
			
			def project2 = new Project(projectId:"KAM3",customer:customer)
			assertNotNull project2.save()
			def building2 = new Building(buildingName:"RIT",project:project2)
			assertNotNull building2.save()
			def floor3 = new Floor(floorName:"13",building:building2)
			assertNotNull floor3.save()
			def floor4 = new Floor(floorName:"14",building:building2)
			assertNotNull floor4.save()
			def room4 = new Room(roomId:"114",floor:floor3)
			assertNotNull room4.save()
			def room5 = new Room(roomId:"115",floor:floor3)
			assertNotNull room5.save()
			def room6 = new Room(roomId:"116",floor:floor3)
			assertNotNull room6.save()
			
			def documentTemplate1 = new DocumentTemplate(name:"Nokia Template",createdBy:"arvind",customer:customer)
			assertNotNull documentTemplate1.save()
			def documentTemplate2 = new DocumentTemplate(name:"Sony Template",createdBy:"brandon",customer:customer)
			assertNotNull documentTemplate2.save()
			
			def documentItem1 = new DocumentItem(formElementName:'Project Number',position:'body',alignment:'left',type:'checkpoint',documentTemplate:documentTemplate1)
			assertNotNull documentItem1.save()
			def documentItem2 = new DocumentItem(formElementName:'Project Team',position:'head',alignment:'left',type:'text',documentTemplate:documentTemplate2)
			assertNotNull documentItem2.save()
			def documentItem3 = new DocumentItem(formElementName:'time estimated',position:'head',alignment:'left',type:'text',documentTemplate:documentTemplate2)
			assertNotNull documentItem3.save()
			
			def user = new User(firstName:"Brandon",middleName:"M",lastName:"Looky",username:'brandon',customer:customer,email:"brandon@lomt.tm",password:'brandon',mobile:"9990541107",accountLocked: false,enabled: true,accountExpired:false,passwordExpired:false,createdBy:'superuser').save()
			def role=new UserRole(user:user,role:Role.findWhere(authority:'ROLE_ADMIN')).save()
			
			def document1 = new Document(name:"KAMDOC6",documentTemplate:documentTemplate1,user:user,project:project1,status:"complete").save()
			assertNotNull document1.save()
			def document2 = new Document(name:"KAMDOC7",documentTemplate:documentTemplate2,user:user,project:project1,status:"complete").save()
			assertNotNull document2.save()
			
			
			def documentItemValue1 = new DocumentItemValue(formElementValue:'101ERUI',alignment:'left',type:'checkpoint',position:'body',document:document1,documentItem:documentItem1)
			assertNotNull documentItemValue1.save()
			def documentItemValue2 = new DocumentItemValue(formElementValue:'10',alignment:'left',type:'text',position:'head',document:document2,documentItem:documentItem2)
			assertNotNull documentItemValue2.save()
			def documentItemValue3 = new DocumentItemValue(formElementValue:'5days',alignment:'left',type:'text',position:'head',document:document2,documentItem:documentItem3)
			assertNotNull documentItemValue3.save()
			
			def question1 = new Question(question:'is it working?',documentItem:documentItem1)
			assertNotNull question1.save()
			def question2 = new Question(question:'AC has been corrected',documentItem:documentItem1)
			assertNotNull question2.save()
			
			def question3 = new Question(question:'is it working?',documentItem:documentItem2)
			assertNotNull question3.save()
			def question4 = new Question(question:'AC has been corrected',documentItem:documentItem2)
			assertNotNull question4.save()
			def question5 = new Question(question:'AC has been corrected',documentItem:documentItem3)
			assertNotNull question5.save()
			
			def questionValue1 = new QuestionValue(questionValue:'yes',document:document1,documentItem:documentItem1,question:question1,documentItemValue:documentItemValue1)
			assertNotNull questionValue1.save()
			def questionValue2 = new QuestionValue(questionValue:'yes',document:document1,documentItem:documentItem1,question:question2,documentItemValue:documentItemValue1)
			assertNotNull questionValue2.save()
			def questionValue3 = new QuestionValue(questionValue:'no',document:document2,documentItem:documentItem2,question:question3,documentItemValue:documentItemValue2)
			assertNotNull questionValue3.save()
			def questionValue4 = new QuestionValue(questionValue:'yes',document:document2,documentItem:documentItem2,question:question4,documentItemValue:documentItemValue2)
			assertNotNull questionValue4.save() 
			def questionValue5 = new QuestionValue(questionValue:'yes',document:document2,documentItem:documentItem3,question:question5,documentItemValue:documentItemValue3)
			assertNotNull questionValue5.save()
	
	}
	
	
	
}
