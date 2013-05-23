package com.kam

import static org.junit.Assert.*
import org.junit.*

class DocumentIntegrationTests{

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
	//Method below checks that templates are saved
	void testDocumentTemplateSave()
	{
		//Customer has a unique name. Please provide a different name.
		def customer=new Customer(name:'customer', address:'address', city:'city', website:'website', contact:'099999999999',enabled:true,activatedby:'Superuser')
		assertNotNull customer.save()
		assertEquals "customer",customer.name

		def documentTemplate= new DocumentTemplate(name:"Template_9",customer:customer,createdBy:'SuperUser')
		documentTemplate.save()
		assertNotNull new DocumentItem(formElementName:'TextElement',documentTemplate:documentTemplate,position:'Title',type:'Text',alignment:'Left').save()
		assertNotNull new DocumentItem(formElementName:'ImageElement',documentTemplate:documentTemplate,position:'Title',type:'Image',alignment:'Left').save()
		assertNotNull new DocumentItem(formElementName:'TableElement',documentTemplate:documentTemplate,position:'Title',type:'Table',alignment:'Left').save()
		assertNotNull new DocumentItem(formElementName:'CheckpointElement',documentTemplate:documentTemplate,position:'Title',type:'CheckPoint',alignment:'Left').save()
		def documentItem=DocumentItem.findWhere(formElementName:'TableElement')
		if(documentItem.type=="Table")
		assertNotNull new Header(name:'TableHeader',documentItem:documentItem).save()
		documentItem=DocumentItem.findWhere(formElementName:'CheckpointElement')
		if(documentItem.type=="CheckPoint")
		assertNotNull new Question(question:'Checkpoint1',documentItem:documentItem).save()
	}

	void testDocumentItemSave()
	{
	 def customer=new Customer(name:'customer1', address:'address', city:'city', website:'website', contact:'099999999999',enabled:true,activatedby:'Superuser')
	assertNotNull customer.save()
	 assertEquals "customer1",customer.name
   
	 def documentTemplate = new DocumentTemplate(name:"Template_11",customer:customer).save()
	 assertNotNull documentTemplate.save()
	 assertNotNull new DocumentItem(formElementName:'TextElement',documentTemplate:documentTemplate,position:'Title',type:'Text',alignment:'Left').save()
		assertNotNull new DocumentItem(formElementName:'ImageElement',documentTemplate:documentTemplate,position:'Title',type:'Image',alignment:'Left').save()
		assertNotNull new DocumentItem(formElementName:'TableElement',documentTemplate:documentTemplate,position:'Title',type:'Table',alignment:'Left').save()
		assertNotNull new DocumentItem(formElementName:'CheckpointElement',documentTemplate:documentTemplate,position:'Title',type:'CheckPoint',alignment:'Left').save()
		def documentItem=DocumentItem.findWhere(formElementName:'TableElement')
		if(documentItem.type=="Table")
		assertNotNull new Header(name:'TableHeader',documentItem:documentItem).save()
		documentItem=DocumentItem.findWhere(formElementName:'CheckpointElement')
		if(documentItem.type=="CheckPoint")
		assertNotNull new Question(question:'Checkpoint1',documentItem:documentItem).save()
	}
	
	
	void testDocumentItemValueSave()
	{
		def customer=new Customer(name:'customer2', address:'address', city:'city', website:'website', contact:'099999999999',enabled:true,activatedby:'Superuser')
	assertNotNull customer.save()
	 assertEquals "customer2",customer.name
	 def user=new User(firstName:'firstname',middleName:'middlename',lastName:'lastname',email:'email@email.com',username:'anyuser',password:'anyuser',accountLocked: false,enabled: true,accountExpired:false,passwordExpired:false);
	 assertNotNull user.save()
	 def documentTemplate = new DocumentTemplate(name:"Template_12",customer:customer).save()
	 assertNotNull documentTemplate.save()
	 assertNotNull new DocumentItem(formElementName:'TextElement',documentTemplate:documentTemplate,position:'Title',type:'Text',alignment:'Left').save()
		assertNotNull new DocumentItem(formElementName:'ImageElement',documentTemplate:documentTemplate,position:'Title',type:'Image',alignment:'Left').save()
		assertNotNull new DocumentItem(formElementName:'TableElement',documentTemplate:documentTemplate,position:'Title',type:'Table',alignment:'Left').save()
		assertNotNull new DocumentItem(formElementName:'CheckpointElement',documentTemplate:documentTemplate,position:'Title',type:'CheckPoint',alignment:'Left').save()
		def documentItem=DocumentItem.findWhere(formElementName:'TableElement')
		if(documentItem.type=="Table")
		assertNotNull new Header(name:'TableHeader',documentItem:documentItem).save()
		documentItem=DocumentItem.findWhere(formElementName:'CheckpointElement')
		if(documentItem.type=="CheckPoint")
		assertNotNull new Question(question:'Checkpoint1',documentItem:documentItem).save()
		
		
		def document = new Document(name:'Document1',documentTemplate:documentTemplate,user:user)
		assertNotNull document.save(flush:true)
		def documentItems=DocumentItem.findWhere(documentTemplate:documentTemplate)
		for(def item in documentItems)
		{
			
			if(item.type=="CheckPoint")
			{
				def questions=Question.findAllWhere(documentItem:item)
				def questionSaveValue=new QuestionValue(questionValue:'Value of question',question:questions,document:document,documentItem:item)
				assertNotNull questionSaveValue.save()
			}
			
			def documentItemValue = new DocumentItemValue(formElementValue:'Value of '+item.name,type:item.type,position:item.position,alignment:item.alignment,documentItem:item,document:document)
			assertNotNull documentItemValue.save()
		}
		
		
	}
	
   
	

}
