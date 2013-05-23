package com.kam
import java.awt.List;
import java.util.Date;
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
/**
 * @author Shridhar
 *
 */
@Secured(['ROLE_ADMIN','ROLE_USER','IS_AUTHENTICATED_REMEMBERED'])
class DocumentController {
	def springSecurityService
	def imageService
		
	def showTemplate = {
		def user=springSecurityService.currentUser
		[userId:user.id,customerName:user.customer.name,loggedInUser:springSecurityService.currentUser,role:UserRole.findWhere(user:user).role.authority]
	}
	def createTemplate = {
		def user=springSecurityService.currentUser
		def totalElements = Integer.parseInt(params.totalElements)
		def customer = Customer.findById(User.get(user.id).customer.id)
		def userName=user.firstName
		if(user.middleName!='' && user.middleName!=null)
		userName=userName+' '+user.middleName
		userName=userName+' '+user.lastName
		def documentTemplate = new DocumentTemplate(name:params['name'],customer:customer,createdBy:userName,discipline:params['templateDiscipline'])
		documentTemplate.save(flush:true)
		if(documentTemplate.hasErrors()){
			redirect action:'showTemplate'
			return
		}
		else{
			addElementsToTemplate(totalElements,documentTemplate)
			redirect action:"templateList"
		}
	}

	def templateList = {
		def user=springSecurityService.currentUser
		def startIndex=params["offset"]
		def documentItemList = new ArrayList()
		def customer = Customer.get(user.customer.id)
		def documentTemplateList =  DocumentTemplate.findAllByCustomerAndIsDeleted(customer,false,[sort:'id',order:'desc',offset:startIndex?:0,max:10])
		def totalSize=DocumentTemplate.findAllByCustomerAndIsDeleted(customer,false,).size()
		if(params.id=="paginate")
		render template:'templates',model:[documentTemplateList:documentTemplateList,totalSize:totalSize,loggedInUserRole:UserRole.findWhere(user:user).role.authority,loggedinUser:user,sno:params['offset']?params['offset'].toString().toLong()+1:0]
		else
		[userId:user.id,documentTemplateList:documentTemplateList,role:UserRole.findWhere(user:user).role.authority,loggedinUser:user,totalSize:totalSize,sno:1]
	}


	def documentInput = {
		def user=springSecurityService.currentUser
		def customer = Customer.get(User.get(user.id).customer.id)
		if(params.status=='false')
		flash.error=message(code:'document.exists')
		if(params.id!=null)
		session.setAttribute("templateId", params.id)
		def projectList=Project.findAllWhere(customer:user.customer,isDeleted:false)
		def documentTemplate = DocumentTemplate.get(session.getAttribute("templateId"))
		def documentItemList =  DocumentItem.findAllWhere(documentTemplate:documentTemplate,isDeleted:false,isHeader:false,[sort:'id',order:'asc'])
		[documentItemList:documentItemList,projectList:projectList,loggedinUser:user,status:true,templateId:session.getAttribute("templateId"),role:UserRole.findWhere(user:user).role.authority]
	}
	
	def documentSave = {
		def user=springSecurityService.currentUser
		def documentProject,building,floor,room
		def customer = Customer.findById(User.get(user.id).customer.id)
		def documentTemplate = DocumentTemplate.get(params.templateId)
		if(params.documentProject!='select')
			documentProject=Project.get(params.documentProject)
		if(documentProject==null){
		redirect action:'documentInput',params:[status:false]
		return
		} 
		def document = new Document(name:params.DocumentName,documentTemplate:documentTemplate,customer:user.customer,status:'Not Ready',documentNumber:params.DocumentNumber,updatedBy:user.username,project:documentProject,discipline:documentTemplate.discipline)
		if(!document.save(flush:true)){
			redirect action:'documentInput',params:[status:false]
			return
		}
		
		if(params.buildingsDropdown!='select' && params.buildingsDropdown!=null){
			building=Building.get(params.buildingsDropdown)
			building.documents.add(document)
		}
		if(params.floorsDropdown!='select'&&params.floorsDropdown!=null){
			floor=Floor.get(params.floorsDropdown)
			floor.documents.add(document)
		}
		if(params.roomsDropdown!='select'&&params.roomsDropdown!=null){
			room=Room.get(params.roomsDropdown)
			room.documents.add(document)
		}
		createNewDocument(document,documentTemplate)
	}
	
	def changeDocument = {
		if(params.id!=null)
		session.setAttribute("documentId",params.id)
		session.setAttribute("createdDocumentName",params.id)
		def document=Document.get(session.getAttribute("documentId").toString().toLong())
		def user=springSecurityService.currentUser
		def documentItemList =  DocumentItemValue.findAllWhere(document:document,[sort:'id',order:'asc'])
		[documentItemList:documentItemList,document:document,loggedinUser:user,status:true,role:UserRole.findWhere(user:user).role.authority]
	
	}
	
	def documentChangesSave = {
		def user=springSecurityService.currentUser
		def document=Document.get(params.id)
		editDocument(document)
		document.version=document.version+1
		document.updatedBy=user.username
		document.lastUpdated=new Date()
		document.save()
		redirect action:session.getAttribute("nextPageAfterEdit")
		
	}
	def backAfterEdit = {
		def docId=session.getAttribute("nextPageAfterEditDocument")
		redirect action:session.getAttribute("nextPageAfterEdit"),params:[id:docId]
	}
	
	def previewTemplate = {
		def user=springSecurityService.currentUser
		def isPdf,footerClass,showImage=true, customerListClass, docDetailsClass
		if(params.id!=null){
			session.setAttribute('templateId',params.id)
			isPdf=false
			customerListClass='customerList'
			docDetailsClass='docDetails'
		}
		else{
			isPdf=true
			customerListClass=''
			docDetailsClass=''
		}
		def documentTemplate=DocumentTemplate.get(session.getAttribute('templateId'))
		def titleList=DocumentItem.findAllByPositionAndDocumentTemplateAndIsDeleted('Title',documentTemplate,false,[sort:'id',order:'asc'])
		def titleLeftList=DocumentItem.findAllByPositionAndAlignmentAndDocumentTemplateAndIsDeleted('Title','Left',documentTemplate,false,[sort:'id',order:'asc'])
		def titleCenterList=DocumentItem.findAllByPositionAndAlignmentAndDocumentTemplateAndIsDeleted('Title',"Center",documentTemplate,false,[sort:'id',order:'asc'])
		def titleRightList=DocumentItem.findAllByPositionAndAlignmentAndDocumentTemplateAndIsDeleted('Title','Right',documentTemplate,false,[sort:'id',order:'asc'])
		def logoList=DocumentItem.findAllByPositionAndDocumentTemplateAndIsDeleted('Logo',documentTemplate,false,[sort:'id',order:'asc'])
		def details1List=DocumentItem.findAllByPositionAndDocumentTemplateAndIsDeleted('Details1',documentTemplate,false,[sort:'id',order:'asc'])
		def otherList=DocumentItem.findAllByPositionAndDocumentTemplateAndIsDeleted('Other',documentTemplate,false,[sort:'id',order:'asc'])
		def remarksList=DocumentItem.findAllByPositionAndDocumentTemplateAndIsDeleted('Remark',documentTemplate,false,[sort:'id',order:'asc'])
		def details2List=DocumentItem.findAllByPositionAndDocumentTemplateAndIsDeleted('Details2',documentTemplate,false,[sort:'id',order:'asc'])
		def bodyList=DocumentItem.findAllByPositionAndDocumentTemplateAndIsDeleted('Body',documentTemplate,false,[sort:'id',order:'asc'])
		def footList=DocumentItem.findAllByPositionAndDocumentTemplateAndIsDeleted('Footer',documentTemplate,false,[sort:'id',order:'asc'])
		def notesList=DocumentItem.findAllByPositionAndDocumentTemplateAndIsDeleted('Notes',documentTemplate,false,[sort:'id',order:'asc'])
		
		def footerCenterList=DocumentItem.findAllByPositionAndDocumentTemplateAndIsDeleted('Footer',documentTemplate,false,[sort:'id',order:'asc'])
		
		def (details11BorderClass,details12BorderClass,details21BorderClass,details22BorderClass,other1BorderClass,other2BorderClass,remarks1BorderClass,remarks2BorderClass,body1BorderClass,body2BorderClass)=getBorderClass(details1List,details2List,otherList,remarksList,bodyList)
		
		def documentItemMap=new HashMap()
		documentItemMap.put("titleList", titleList)
		documentItemMap.put("titleLeftList", titleLeftList)
		documentItemMap.put("titleCenterList", titleCenterList)
		documentItemMap.put("titleRightList", titleRightList)
		documentItemMap.put("logoList", logoList)
		documentItemMap.put("details1List", details1List)
		documentItemMap.put("otherList", otherList)
		documentItemMap.put("remarksList", remarksList)
		documentItemMap.put("details2List", details2List)
		documentItemMap.put("bodyList", bodyList)
		documentItemMap.put("footList", footList)
		documentItemMap.put("notesList", notesList)
		
		documentItemMap.put("footerCenterList", footerCenterList)
		
		documentItemMap.put('details11BorderClass', details11BorderClass)
		documentItemMap.put( 'details12BorderClass', details12BorderClass)
		documentItemMap.put( 'details21BorderClass', details21BorderClass)
		documentItemMap.put( 'details22BorderClass', details22BorderClass)
		documentItemMap.put( 'other1BorderClass', other1BorderClass)
		documentItemMap.put( 'other2BorderClass', other2BorderClass)
		documentItemMap.put( 'remarks1BorderClass', remarks1BorderClass)
		documentItemMap.put( 'remarks2BorderClass', remarks2BorderClass)
		documentItemMap.put( 'body1BorderClass', body1BorderClass)
		documentItemMap.put( 'body2BorderClass', body2BorderClass)
		
		if(footerCenterList!=null)
		footerClass='width33Percent'
		else
		footerClass='widthHalf'
		
		[documentItemValueMap:documentItemMap,template:documentTemplate,loggedinUser:user,role:UserRole.findWhere(user:user).role.authority,isPdf:isPdf,customerListClass:customerListClass,docDetailsClass:docDetailsClass,footerClass:footerClass,showImage:showImage]
	}
	
	def showDocument={
		def user=springSecurityService.currentUser
		def  footerClass, customerListClass, docDetailsClass, showImage=true, blankRowValue=" "
		if(session.getAttribute("createdDocumentName")==null){
			redirect controller:'site'
			return
		}
		def isPdf
		session.setAttribute("nextPageAfterEdit",'showDocument')
		session.setAttribute("nextPageAfterEditDocument",'')
		if(params.id!=null){
			isPdf=true
			customerListClass=''
			docDetailsClass=''
		}
		else{
			isPdf=false
			customerListClass='customerList'
			docDetailsClass='docDetails'
		}

		def document = Document.get(session.getAttribute("createdDocumentName"))
		def titleList=DocumentItemValue.findAllByPositionAndDocument('Title',document,[sort:'id',order:'asc'])
		def titleLeftList=DocumentItemValue.findAllByPositionAndDocumentAndAlignment('Title',document,'Left',[sort:'id',order:'asc'])
		def titleCenterList=DocumentItemValue.findAllByPositionAndDocumentAndAlignment('Title',document,"Center",[sort:'id',order:'asc'])
		def titleRightList=DocumentItemValue.findAllByPositionAndDocumentAndAlignment('Title',document,'Right',[sort:'id',order:'asc'])
		def logoList=DocumentItemValue.findAllByPositionAndDocument('Logo',document,[sort:'id',order:'asc'])
		def details1List=DocumentItemValue.findAllByPositionAndDocument('Details1',document,[sort:'id',order:'asc'])
		def otherList=DocumentItemValue.findAllByPositionAndDocument('Other',document,[sort:'id',order:'asc'])
		def remarksList=DocumentItemValue.findAllByPositionAndDocument('Remark',document,[sort:'id',order:'asc'])
		def details2List=DocumentItemValue.findAllByPositionAndDocument('Details2',document,[sort:'id',order:'asc'])
		def bodyList=DocumentItemValue.findAllByPositionAndDocument('Body',document,[sort:'id',order:'asc'])
		def footList=DocumentItemValue.findAllByPositionAndDocument('Footer',document,[sort:'id',order:'asc'])
		def notesList=DocumentItemValue.findAllByPositionAndDocument('Notes',document,[sort:'id',order:'asc'])
		def footerCenterList=DocumentItemValue.findAllByPositionAndDocumentAndAlignment('Footer',document,"Center",[sort:'id',order:'asc'])
		def logoHeaderList=DocumentItem.findAllByPositionAndDocumentTemplateAndIsHeader('Logo',document.documentTemplate,true,[sort:'id',order:'asc'])

		def latestTicket = Ticket.findByDocument(document,[sort:'id',order:'desc'])
		def documentAttachments = Image.findAllWhere(ticket:latestTicket,document:document)
		def (details11BorderClass,details12BorderClass,details21BorderClass,details22BorderClass,other1BorderClass,other2BorderClass,remarks1BorderClass,remarks2BorderClass,body1BorderClass,body2BorderClass)=getBorderClass(details1List,details2List,otherList,remarksList,bodyList)
		def documentItemValueMap=new HashMap()
		documentItemValueMap.put('details11BorderClass', details11BorderClass)
		documentItemValueMap.put( 'details12BorderClass', details12BorderClass)
		documentItemValueMap.put( 'details21BorderClass', details21BorderClass)
		documentItemValueMap.put( 'details22BorderClass', details22BorderClass)
		documentItemValueMap.put( 'other1BorderClass', other1BorderClass)
		documentItemValueMap.put( 'other2BorderClass', other2BorderClass)
		documentItemValueMap.put( 'remarks1BorderClass', remarks1BorderClass)
		documentItemValueMap.put( 'remarks2BorderClass', remarks2BorderClass)
		documentItemValueMap.put( 'body1BorderClass', body1BorderClass)
		documentItemValueMap.put( 'body2BorderClass', body2BorderClass)
		
		documentItemValueMap.put("titleList", titleList)
		documentItemValueMap.put("titleLeftList", titleLeftList)
		documentItemValueMap.put("titleCenterList", titleCenterList)
		documentItemValueMap.put("titleRightList", titleRightList)
		documentItemValueMap.put("logoList", logoList)
		documentItemValueMap.put("details1List", details1List)
		documentItemValueMap.put("otherList", otherList)
		documentItemValueMap.put("remarksList", remarksList)
		documentItemValueMap.put("details2List", details2List)
		documentItemValueMap.put("bodyList", bodyList)
		documentItemValueMap.put("footList", footList)
		documentItemValueMap.put("notesList", notesList)
		documentItemValueMap.put("footerCenterList", footerCenterList)
		documentItemValueMap.put("logoHeaderList", logoHeaderList)
		
		if(footerCenterList!=null)
		footerClass='width33Percent'
		else
		footerClass='widthHalf'
		
		if(params.id=='pdfImageNo')
			showImage=false
		
		[document:document,documentItemValueMap:documentItemValueMap,customerListClass:customerListClass,docDetailsClass:docDetailsClass,footerClass:footerClass,isPdf:isPdf,loggedinUser:user,role:UserRole.findWhere(user:user).role.authority,showImage:showImage,blankRowValue:blankRowValue,attachments:documentAttachments]
	}
	
	def deleteTemplate = {
		def returnObject = [ : ]
		def template = DocumentTemplate.get(params.id)
			for(def item in DocumentItem.findAllByDocumentTemplate(template)){
				item.isDeleted =true
				item.save()
			}
			template.name='#DELETED786758675'+template.name
			template.isDeleted=true
			if(template.save()){
			returnObject.sucess=true
			render returnObject as JSON
			}
			else{
				returnObject.sucess=false
				render returnObject as JSON
			}
	}
	
	def editTemplate = {
		def user=springSecurityService.currentUser
		def customer = Customer.findById(User.get(user.id).customer.id)
		def template = DocumentTemplate.get(params.id)
		def documentItemList =  DocumentItem.findAllWhere(documentTemplate:template,isDeleted:false,[sort:'id',order:'asc'])
		[documentItemList:documentItemList,template:template,role:UserRole.findWhere(user:user).role.authority,loggedinUser:user]
	}
	
	def updateTemplate = {
		def user=springSecurityService.currentUser
		def documentOldTemplate =  DocumentTemplate.get(params.templateId.toLong())
		def oldDocumentItems = DocumentItem.findAllWhere(documentTemplate:documentOldTemplate,isDeleted:false,[sort:'id',order:'asc'])
		for(def docItem:oldDocumentItems){
			if(params[docItem.id.toString()]!=null){
				docItem.position = params['positionOld'+docItem.id.toString()]
				docItem.alignment=params['alignOld'+docItem.id.toString()]
				docItem.type=params['typeOld'+docItem.id.toString()]
				String isHeader
				if(params['isHeaderOld'+docItem.id.toString()]!=null)
					docItem.isHeader=true
				else
					docItem.isHeader=false
				if(docItem.type=='Table')
					editTemplateTableHeaders(docItem)
				if(docItem.type=='CheckPoint' || docItem.type=='Table')
				{
					editTemplateCheckpoints(docItem)
					if(docItem.type=='CheckPoint')
						docItem.checkpointType=params['checkpointTypeOld'+docItem.id.toString()]
				}
				if(docItem.type=='Image'){
					docItem.formElementName = ''
					if(params['isSignatureOld'+docItem.id.toString()]==null){
						imageService.changeImageLocation(params['imageOld'+docItem.id.toString()],grailsApplication.config.upload.document.image.path)
						docItem.isSignature=false
						docItem.formElementName = params['imageOld'+docItem.id.toString()]
					}
					else{
						docItem.isSignature=true
						docItem.formElementName = params[docItem.id.toString()]
					}
				}
				else
				docItem.formElementName = params[docItem.id.toString()]
				docItem.save()
			}
			else
			{
				docItem.isDeleted=true
				docItem.save()
			}
		}
		addElementsToTemplate(params.totalElements,documentOldTemplate)
		documentOldTemplate.lastUpdated=new Date()
		documentOldTemplate.name =  params.name
		documentOldTemplate.save()
		redirect action:'templateList'
	}
		
	def imageUploader={
		render template:'uploadImage',model:[elementName:params.elementName]
	}
	
	def templateDetails={
		def id=params.docId.toLong()
		def template=DocumentTemplate.get(id)
		def templateHeaders=DocumentItem.findAllWhere(documentTemplate:template)
		render template:'templateOverlay',model:[template:template,templateHeaders:templateHeaders]
	}
	
	def checkIfDocumentCreated={
		def returnObject = [ : ]
		def document
		if(params.documentName!=null)
		document=Document.findWhere(name:params.documentName,isDeleted:false)
		else
		document=Document.findWhere(documentNumber:params.documentNumber,isDeleted:false)
		
		if(document!=null)
		{
			returnObject.sucess=false
			if(params.documentName!=null)
			returnObject.message=message(code:'document.exists')
			else
			returnObject.message=message(code:'document.number.exists')
			render returnObject as JSON
		}
		else
		{
			returnObject.sucess=true
			returnObject.message=""
			render returnObject as JSON
		}
	}
	
	def addElementsToTemplate(def totalElements,def documentTemplate){
		for(int i=1;i<=totalElements;i++) {
			if(params['formElementName'+i] != null){
				String[] items
				String isHeader,checkPointSerialNo
				if(params['isHeader'+i]!=null)
				isHeader=true
				else
				isHeader=false
				def documentItem = new DocumentItem(formElementName:params['formElementName'+i],documentTemplate:documentTemplate,position:params['position'+i],type:params['type'+i],alignment:params['align'+i],isDeleted:false,isHeader:isHeader)
				documentItem.save()
				addNewTemplateElements(documentItem,i)
				if(params['type'+i]=='Image'){
					if(params['isSignature'+i]==null){
						imageService.changeImageLocation(params['image'+i],grailsApplication.config.upload.document.image.path)
						documentItem.isSignature=false
						documentItem.formElementName=params['image'+i]
					}
					else{
						documentItem.isSignature=true
						documentItem.formElementName=params['formElementName'+i]
					}
					documentItem.save()
				}
			}
		}
	}
	
	def addNewTemplateElements(def documentItem,int i){
		String[] items
		String isHeader,checkPointSerialNo
		if(params['type'+i]=='Table')
		{
			items=params['tableHeader'+i].toString().replace('[','').replace(']','').split(',')
			String[] checkpointType=params['checkpointType'+i].toString().replace('[','').replace(']','').split(',')
			for(int j=0;j<items.length;j++)
			{
				def headerField=new Header(name:items[j].trim(),documentItem:documentItem,checkpointType:checkpointType[j].trim())
				headerField.save()
			}
		}
		if(params['type'+i]=='CheckPoint' || params['type'+i]=='Table')
		{
			items=params['checkpoint'+i].toString().replace('[','').replace(']','').split(',')
			String[] checkpointSerialNo=params['checkpointSno'+i].toString().replace('[','').replace(']','').split(',')
			for(int j=0;j<items.length;j++)
			{
					if(params[i+'isHeader'+j]!=null)
						isHeader=true
					else
						isHeader=false
					if(checkpointSerialNo!=null && checkpointSerialNo[0]!='null')
						checkPointSerialNo=checkpointSerialNo[j]
				def questionField=new Question(question:items[j].trim(),documentItem:documentItem,isHeader:isHeader,serialNo:checkPointSerialNo)
				questionField.save()
			}
			if(params['type'+i]=='CheckPoint')
			documentItem.checkpointType=params['checkpointType'+i]
			documentItem.save()
		}
	}
	
	def createNewDocument(def document,def documentTemplate){
		def documentItemList =  DocumentItem.findAllWhere(documentTemplate:documentTemplate,isDeleted:false,[sort:'id',order:'asc'])
		for(int i=0;i<documentItemList.size();i++) {
			String formElementValue=''
			if(!documentItemList[i].isHeader){
				if(documentItemList[i].type=="CheckPoint" || documentItemList[i].type=="Table" || (documentItemList[i].type=="Image" && documentItemList[i].isSignature==false))
					formElementValue=documentItemList[i].formElementName
				else
					formElementValue=params[documentItemList[i].id.toString()]
			}
			else
				formElementValue=documentItemList[i].formElementName
				
			def documentItemValue = new DocumentItemValue(formElementValue:formElementValue,type:documentItemList[i].type,position:documentItemList[i].position,alignment:documentItemList[i].alignment,documentItem:documentItemList[i],document:document)
			documentItemValue.save(flush:true)
			if(documentItemList[i].type=="Table")
				addDocumentTables(documentItemList[i], document, documentItemValue)
			if(documentItemList[i].type=="CheckPoint")
				addDocumentCheckpoint(documentItemList[i], document, documentItemValue)
			if(documentItemList[i].isSignature==true)
				imageService.changeImageLocation(params[documentItemList[i].id.toString()],grailsApplication.config.upload.document.image.path)
		}
		session.setAttribute("createdDocumentName", document.id)
		redirect action:'showDocument'
	}
	
	
	def addDocumentCheckpoint(def documentItem, def document, def documentItemValue){
		def questions=Question.findAllWhere(documentItem:documentItem,[sort:'id',order:'asc'])
		String[] questionSaveValues
		if(params[documentItem.id.toString()] instanceof String)
			questionSaveValues=params[documentItem.id.toString()].toString().replace('[','').replace(']','').split(',')
		else
			questionSaveValues=params[documentItem.id.toString()]
		String questionValue
		for(int j=0;j<questions.size();j++)
		{
			questionValue=''
			if(!documentItem.isHeader){
				if(questions[j].documentItem.checkpointType=='TextBox')
					questionValue=questionSaveValues[j]
				else if(questions[j].documentItem.checkpointType=='CheckBox')
						questionValue = setCheckpointValues(questionSaveValues, questions[j])
				else if(questions[j].documentItem.checkpointType=='RadioButton')
					questionValue = setCheckpointValues(questionSaveValues, questions[j])
			}
			def questionSaveValue=new QuestionValue(questionValue:questionValue,question:questions[j],document:document,documentItem:documentItem,documentItemValues:documentItemValue,checkpointType:questions[j].documentItem.checkpointType)
			questionSaveValue.save()
		}
	}
	
	
	
	def addDocumentTables(def documentItem, def document, def documentItemValue){
		def questions=Question.findAllWhere(documentItem:documentItem,isHeader:false,[sort:'id',order:'asc'])
		def headers=Header.findAllWhere(documentItem:documentItem,[sort:'id',order:'asc'])
		String questionValue=''
		for(int j=0;j<questions.size();j++)
		{
			for(int k=2;k<headers.size();k++){
				questionValue=''
				if(!documentItem.isHeader){
					if(headers[k].checkpointType=="RadioButton"){
						if(params[documentItem.id+'doc'+questions[j].id+'TableCheckpoint']==headers[k].name)
							questionValue=headers[k].id
					}else{
						if(params[documentItem.id+'doc'+questions[j].id+'TableCheckpoint'+headers[k].id]!=null)
							questionValue=params[documentItem.id+'doc'+questions[j].id+'TableCheckpoint'+headers[k].id]
					}
				}
				def questionSaveValue=new QuestionValue(questionValue:questionValue,question:questions[j],document:document,documentItem:documentItem,documentItemValues:documentItemValue,checkpointType:headers[k].checkpointType)
				questionSaveValue.save()
			}
		}
	}
	
	def editDocument(def document){
		def documentItemList =  DocumentItemValue.findAllWhere(document:document,[sort:'id',order:'asc'])
		for(int i=0;i<documentItemList.size();i++) {
			String formElementValueSaved
			if(!documentItemList[i].documentItem.isHeader){
				if(documentItemList[i].type=="Table")
					formElementValueSaved = editDocumentTables(documentItemList[i])
				else if(documentItemList[i].type=="CheckPoint")
					formElementValueSaved = editDocumentQuestions(documentItemList[i])
				else if(documentItemList[i].type=="Image"){
					if(documentItemList[i].documentItem.isSignature==false)
						formElementValueSaved=documentItemList[i].formElementValue
					else{
						imageService.changeImageLocation(params[documentItemList[i].id.toString()],grailsApplication.config.upload.document.image.path)
						formElementValueSaved=params[documentItemList[i].id.toString()]
					}
				}
				else{
					formElementValueSaved=params[documentItemList[i].id.toString()]
				} 
				documentItemList[i].formElementValue=formElementValueSaved
				documentItemList[i].save(flush:true)
			}
		}
		session.setAttribute("createdDocumentName", document.id)
	}
	
	def editDocumentQuestions(def documentItem){
		String formElementValueSaved
		def questions=QuestionValue.findAllWhere(documentItemValues:documentItem,[sort:'id',order:'asc'])
		String[] questionSaveValues
		if(params[documentItem.id.toString()] instanceof String)
			questionSaveValues=params[documentItem.id.toString()].toString().replace('[','').replace(']','').split(',')
		else
			questionSaveValues=params[documentItem.id.toString()]
		String questionValue
		for(int j=0;j<questions.size();j++)
		{
			questionValue=''
			if(questions[j].documentItem.checkpointType=='TextBox')
				questionValue=questionSaveValues[j]
			else if(questions[j].documentItem.checkpointType=='CheckBox')
				questionValue = setCheckpointValues(questionSaveValues, questions[j].question)
			else if(questions[j].documentItem.checkpointType=='RadioButton')
				questionValue = setCheckpointValues(questionSaveValues, questions[j].question)
			
			questions[j].questionValue=questionValue
			questions[j].save(flush:true)			
		}
	}
	
	
	def editDocumentTables(def documentItemValue){
		String formElementValueSaved
		def questions=QuestionValue.findAllWhere(documentItemValues:documentItemValue,[sort:'id',order:'asc'])
		def headers=Header.findAllWhere(documentItem:documentItemValue.documentItem,[sort:'id',order:'asc'])
		String questionValue=''
	
		for(int j=0;j<questions.size();j++)
		{
			questionValue=''
			if(questions[j].checkpointType=="RadioButton"){
				if(params[documentItemValue.id+'TableCheckpoint'+questions[j].question.id].toString().indexOf(questions[j].id.toString())!=-1)
					questionValue=questions[j].id
			}else{
				if(params[documentItemValue.id+'TableCheckpoint'+questions[j].id]!=null)
					questionValue=params[documentItemValue.id+'TableCheckpoint'+questions[j].id]
			}
			questions[j].questionValue=questionValue
			questions[j].save()
		}
		formElementValueSaved=documentItemValue.documentItem.formElementName
		return formElementValueSaved
	}
	
	def editTemplateCheckpoints(def docItem){
		boolean isHeader
		String[] headers=params['checkpointOld'+docItem.id.toString()].toString().replace('[','').replace(']','').split(',')
		String[] checkPointSerialNo=params['checkpointSnoOld'+docItem.id.toString()].toString().replace('[','').replace(']','').split(',')
		int i=0
		for(def item :Question.findAllWhere(documentItem:docItem,[sort:'id',order:'asc']))
		{
				item.question=headers[i].trim()
				if(docItem.type=='Table')
				item.serialNo=checkPointSerialNo[i].trim()
				
				if(params[docItem.id.toString()+'isHeader'+i]!=null)
				item.isHeader=true
				else
				item.isHeader=false
				item.save()
				i++
		}
		if(i<headers.length){
			for(;i<headers.length;)
			{
				String sno=''
				if(docItem.type=='Table')
				sno = checkPointSerialNo[i].trim()
					if(params['Old'+docItem.id.toString()+'isHeader'+i]!=null)
					isHeader=true
					else
					isHeader=false
				def item=new Question(question:headers[i].trim(),documentItem:docItem,serialNo:sno,isHeader:isHeader)
				item.save()
				i++
			}
		}
	}
	
	def editTemplateTableHeaders(def docItem){
		String[] headers=params['tableHeaderOld'+docItem.id.toString()].toString().replace('[','').replace(']','').split(',')
		int i=0
		String[] checkpointType=params['checkpointTypeOld'+docItem.id.toString()].toString().replace('[','').replace(']','').split(',')
		for(def item :Header.findAllWhere(documentItem:docItem,[sort:'id',order:'asc']))
		{
				item.name=headers[i].trim()
				item.checkpointType=checkpointType[i].trim()
				item.save()
			i++
		}
		if(i<headers.length){
			for(;i<headers.length;)
			{
				def item=new Header(name:headers[i].trim(),documentItem:docItem,checkpointType:checkpointType[i].trim())
				item.save()
				i++
			}
		}
	}
	
	def setCheckpointValues(def questionSaveValues, def question){
		String questionValue=''
		if(questionSaveValues!=null){
			if(questionSaveValues.contains(question.question))
				questionValue=question.question
		}
		else
			questionValue=''
		return questionValue
	}
	
	def documentList={
		def user=springSecurityService.currentUser
		if(user.customer.flow=='document'){
			def docList=Document.findAllByCustomerAndIsDeleted(user.customer,false,[sort:'lastUpdated',order:'desc'])
			def projectList=Project.findAllByCustomerAndIsDeleted(user.customer,false,[sort:'projectName',order:'asc']) 
			session.setAttribute("objectType", "")
			session.setAttribute("objectValue", "")
			session.setAttribute("project", "")
			session.setAttribute("building", "")
			session.setAttribute("floor", "")
			session.setAttribute("room", "")
		
			session.setAttribute("actionPerformed","")
			render view:'documentList',model:[role:UserRole.findWhere(user:user).role.authority,loggedinUser:user,docList:docList,projectList:projectList]
		}
		else{
			redirect controller:'site'
		}
	}
	
	def documentListPdf={
		def user=springSecurityService.currentUser
		def (getCurrentList,newList,update,nextObject)=filterObjects(session.getAttribute("objectType"),session.getAttribute("objectValue"))
		def docList,customerLogo=''
		def defaultAction = false
		if(session.getAttribute("actionPerformed")=='filter'){
			if(session.getAttribute("objectType")=="")
				docList=Document.findAllWhere(customer:user.customer,isDeleted:false)
			else
				docList=getCurrentList.documents
		}
		else if(session.getAttribute("actionPerformed")=='search'){
			docList=Document.executeQuery("select p.projectName,d.name,d.documentNumber,d.status,d.lastUpdated,d.dateCreated,d.updatedBy,d.id,d.discipline,d.isDeleted from Document d, Project p where ( p.projectName like '%"+session.getAttribute("searchQuery")+"%' OR d.name like '%"+session.getAttribute("searchQuery")+"%' OR d.documentNumber like '%"+session.getAttribute("searchQuery")+"%' OR d.lastUpdated like '%"+session.getAttribute("searchQuery")+"%' OR d.status like '%"+session.getAttribute("searchQuery")+"%' OR d.discipline like '%"+session.getAttribute("searchQuery")+"%' ) AND d.project.id=p.id AND d.customer=:customer",[customer:user.customer,])
			defaultAction = true
		}
		else if(session.getAttribute("actionPerformed")==''){
			docList=Document.findAllByCustomerAndIsDeleted(user.customer,false)
		}
		def date=new java.util.Date()
		def customerSettings = CustomizeSettings.findWhere(customer:user.customer)
		if(customerSettings!=null)
			customerLogo = customerSettings.logo
		[docList:docList,date:date,project:session.getAttribute('project'),building:session.getAttribute('building'),floor:session.getAttribute('floor'),room:session.getAttribute('room'),defaultAction:defaultAction,customerLogo:customerLogo]
	}
	
	def documentSearch={
		def returnObject = [:]
		def user=springSecurityService.currentUser
		def docList
		session.setAttribute("actionPerformed","search")
		session.setAttribute("searchQuery",params['query'])
		//TODO: Ideally this should be done using GORM , need to fix
		docList=Document.executeQuery("select p.projectName,d.name,d.documentNumber,d.status,d.lastUpdated,d.dateCreated,d.updatedBy,d.id,d.discipline,d.isDeleted,d.deadLine from Document d, Project p where ( p.projectName like '%"+params['query']+"%' OR d.name like '%"+params['query']+"%' OR d.documentNumber like '%"+params['query']+"%' OR d.lastUpdated like '%"+params['query']+"%' OR d.status like '%"+params['query']+"%' OR d.discipline like '%"+params['query']+"%' ) AND d.project.id=p.id AND d.customer=:customer",[customer:user.customer,])
		returnObject.newList=docList 
		render returnObject as JSON
	}
	
	def viewDocument={
		session.setAttribute("nextPageAfterEdit",'viewDocument')
		def user=springSecurityService.currentUser
		def documentBuilding,documentFloor,documentRoom, documentId
		if(params.id!='null' && params.id!=null){
			documentId=params.id
			session.setAttribute("nextPageAfterEditDocument",params.id) 
		}
		else
			documentId=session.getAttribute("nextPageAfterEditDocument")
		def document=Document.get(documentId) 
		for(def building in document.project.buildings){
			for (def floor in building.floors){
				for (def room in floor.rooms){
					if(room.documents.name.contains(document.name))
						documentRoom=room.roomId
				}
				if(floor.documents.name.contains(document.name))
					documentFloor=floor.floorNumber
			}
			if(building.documents.name.contains(document.name))
				documentBuilding=building.buildingName
		}
		session.setAttribute("createdDocumentName",document.id)
		def documentImagesList=DocumentItemValue.findAllByDocumentAndType(document,'Image') 
		def projectList=Project.findAllByCustomer(user.customer,[sort:'projectName',order:'asc'])
		def latestTicket = Ticket.findByDocument(document,[sort:'id',order:'desc'])
		def documentAttachments = Image.findAllWhere(ticket:latestTicket,document:document)
		
		[role:UserRole.findWhere(user:user).role.authority,loggedinUser:user,document:document,documentImagesList:documentImagesList,projectList:projectList,,building:documentBuilding,floor:documentFloor,room:documentRoom,documentAttachments:documentAttachments]
	}
	
	def sortDocumentList={
		def returnObject = [:]
		def user=springSecurityService.currentUser
		def docList
		if(session.getAttribute("actionPerformed")=='')
			docList=Document.findAllByCustomerAndIsDeleted(user.customer,false,[sort:params.name,order:params.order])
		else if(session.getAttribute("actionPerformed")=='search'){
			def orderField='d.'+params.name
			docList=Document.executeQuery("select p.projectName,d.name,d.documentNumber,d.status,d.lastUpdated,d.dateCreated,d.updatedBy,d.id,d.discipline,d.isDeleted,d.deadLine from Document d, Project p where ( p.projectName like '%"+params['query']+"%' OR d.name like '%"+params['query']+"%' OR d.documentNumber like '%"+params['query']+"%' OR d.lastUpdated like '%"+params['query']+"%' OR d.status like '%"+params['query']+"%' OR d.discipline like '%"+params['query']+"%' ) AND d.project.id=p.id AND d.customer=:customer ORDER BY "+orderField+" "+params.order+" ",[customer:user.customer,])
		}
		else if(session.getAttribute("actionPerformed")=='filter'){
			def orderField='it.id'
			def (getCurrentList,newList,update,nextObject)=filterObjects(session.getAttribute("objectType"),session.getAttribute("objectValue"))
			if(session.getAttribute("objectType")=="")
				docList=Document.findAllWhere(customer:user.customer,isDeleted:false)
			else
				docList=getCurrentList.documents
			if(docList!=null)
				docList = sortList(docList,params.name,params.order)
		}
		if(session.getAttribute("actionPerformed")=='' || session.getAttribute("actionPerformed")=='filter')
			render template:'documentsListing',model:[docList:docList,actionToSend:'sortDocumentList']
		else if(session.getAttribute("actionPerformed")=='search'){
			returnObject.newList=docList
			render returnObject as JSON
		}
	}
	
	def sortList(def documentsList,def sortField,def order) {
		def docList
		switch(sortField){
			case 'project.projectName':
				if(order=='asc')
					docList = documentsList.sort({it.project.projectName})
				else
					docList = documentsList.sort({it.project.projectName}).reverse()
				break
			case 'name':
				if(order=='asc')
					docList = documentsList.sort({it.name})
				else
				docList = documentsList.sort({it.name}).reverse()
				break
			case 'documentNumber':
				if(order=='asc')
					docList = documentsList.sort({it.documentNumber})
				else
					docList = documentsList.sort({it.documentNumber}).reverse()
				break
			case 'discipline':
				if(order=='asc')
					docList = documentsList.sort({it.discipline})
				else
					docList = documentsList.sort({it.discipline}).reverse()
				break
			case 'status':
				if(order=='asc')
					docList = documentsList.sort({it.status})
				else
					docList = documentsList.sort({it.status}).reverse()
				break
			case 'lastUpdated':
				if(order=='asc')
					docList = documentsList.sort({it.lastUpdated})
				else
					docList = documentsList.sort({it.lastUpdated}).reverse()
				break
		}
		return docList
	}
	
	def deleteDocument={
		def returnObject = [:]
		def document=Document.get(params.id)
		document.isDeleted = true
		if(document.save())
			returnObject.success=true
		else
			returnObject.success=false
		render returnObject as JSON
	}
	
	def populateSelect={
		def returnObject = [:]
		def (getCurrentList,newList,update,nextObject)=filterObjects(params.type,params.value)
		def documents
		if(params.type!='discipline')
			documents=getCurrentList.documents
		else{
			documents=new ArrayList()
			int counter=0
			for(def document in getCurrentList.documents){
				if(document.documentTemplate.discipline==params.value){
					documents[counter]=document
					counter++
				}
			}
		}
		session.setAttribute("actionPerformed","filter")
		returnObject.put('list', newList)
		returnObject.put('update', update)
		returnObject.put('next', nextObject)
		
		returnObject.put('current', params.type)
		returnObject.put('newList', documents)
		render returnObject as JSON
	}
	
	def filterObjects(type,value){
		def currentList,getNewList,update,nextObject
		switch(type){
			case 'project':
				session.setAttribute("objectType", "project")
				session.setAttribute("objectValue", value)
				currentList =Project.get(value)
				session.setAttribute("project", currentList.projectName)
				getNewList = currentList.buildings
				update='buildingsDropdown'
				nextObject='building'
				break
			case 'building':
				session.setAttribute("objectType", "building")
				session.setAttribute("objectValue", value)
				currentList =Building.get(value)
				session.setAttribute("building", currentList.buildingName)
				getNewList = currentList.floors
				update='floorsDropdown'
				nextObject='floor'
			break
			case 'floor':
				session.setAttribute("objectType", "floor")
				session.setAttribute("objectValue", value)
				currentList =Floor.get(value)
				session.setAttribute("floor", currentList.floorNumber)
				getNewList = currentList.rooms
				update='roomsDropdown'
				nextObject='room'
				break
			case 'room':
				session.setAttribute("objectType", "room")
				session.setAttribute("objectValue", value)
				currentList =Room.get(value) 
				session.setAttribute("room", currentList.roomId)
				getNewList = Disciplines.list().disciplineName
				update='disciplinesDropdown'
				nextObject='discipline'
				break
			case 'discipline':
				currentList =Room.get(session.getAttribute("objectValue").toString().toLong())
				getNewList=[]
				update=''
				nextObject=''
				break
		}
		return [currentList,getNewList,update,nextObject]
	}
	
	def getBorderClass(def details1List,def details2List,def otherList,def remarksList,def bodyList){
		def details11BorderClass='',details12BorderClass='',details21BorderClass='',details22BorderClass='',other1BorderClass='',other2BorderClass='',remarks1BorderClass='',remarks2BorderClass='',body1BorderClass='',body2BorderClass=''
		for(def element1 in details1List){
			if(element1.type=='Image' || element1.type=='Text')
				details11BorderClass='fourSideBorder'
			if(element1.type=='Table' || element1.type=='Checkpoint')
				details12BorderClass='fourSideBorder'
		}
		for(def element1 in details2List){
			if(element1.type=='Image' || element1.type=='Text')
				details21BorderClass='fourSideBorder'
			if(element1.type=='Table' || element1.type=='Checkpoint')
				details22BorderClass='fourSideBorder'
		}
		for(def element1 in otherList){
			if(element1.type=='Image' || element1.type=='Text')
				other1BorderClass='fourSideBorder'
			if(element1.type=='Table' || element1.type=='Checkpoint')
				other2BorderClass='fourSideBorder'
		}
		for(def element1 in remarksList){
			if(element1.type=='Image' || element1.type=='Text')
				remarks1BorderClass='fourSideBorder'
			if(element1.type=='Table' || element1.type=='Checkpoint')
				remarks2BorderClass='fourSideBorder'
		}
		for(def element1 in bodyList){
			if(element1.type=='Image' || element1.type=='Text')
				body1BorderClass='fourSideBorder'
			if(element1.type=='Table' || element1.type=='Checkpoint')
				body2BorderClass='fourSideBorder'
		}
		
		return [details11BorderClass,details12BorderClass,details21BorderClass,details22BorderClass,other1BorderClass,other2BorderClass,remarks1BorderClass,remarks2BorderClass,body1BorderClass,body2BorderClass]
	}
}