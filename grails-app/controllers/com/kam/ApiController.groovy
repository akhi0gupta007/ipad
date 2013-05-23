package com.kam
import org.apache.commons.lang.time.DateUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile
import grails.converters.*;
import groovy.xml.MarkupBuilder

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ScheduledExecutorService
class ApiController{
	def springSecurityService
	// dependency injection for DocumentService,userService,kamGenericService,MasterService
	def documentService
	def userService
	def pdfService
	def kamGenericService
	def ticketService
	def buildingService
	def masterService
	def projectService
	def postMasterService
	def postBuildingService
	def documentServiceNewService //New Service Akhilesh
	def buildingTicketService
	def asynchronousMailService
	def grailsApplication
	def postBuildingMailService
	//def floorTicketService
	def apprequest = {
		log.debug "params from apple api call "+params
		log.debug "index of <digikam> is=="+params.toString().indexOf("<digikam>")
		def initialIndex = params.toString().indexOf("<digikam>")
		def lastIndex = params.toString().indexOf("</digikam>")
		log.debug "index of </digikam> is=="+params.toString().indexOf("</digikam>")
		def breakedXml = params.toString().substring(initialIndex,lastIndex+10)
		log.debug "substring between digikam tags is==="+params.toString().substring(initialIndex,lastIndex+10)
		def urlValues = params.toString().split(',')
		log.debug "split xml is==="+urlValues
		//def requiredXML = urlValues.getAt(0).toString().replace('[','').replace(':','=')
		def object =  XML.parse(breakedXml)
		log.debug "xml converted is=" +object

		def requestTypeReceived = object.requesttype
		log.debug "request type is==" +requestTypeReceived
		response.setContentType("text/xml");
		log.debug "Response type is Set As XML............................................................................................."
		switch(requestTypeReceived)
		{
			case 'LOGINCHECK':
				def test = userService.loginCheck(object)
				log.debug "tes is =====" +test
				int index = test.lastIndexOf("/")
				String trimmedXml = test.substring(0,index-1).trim()
				log.debug "returning xml=="+trimmedXml
				render trimmedXml 
				break;
			case 'GETPROJECTS':
				log.debug "In Get Projects"
				try{
					def userToFind = User.findByUsername(object.loginvalidation.loginname.toString())
					log.debug "user found is=="+userToFind
					def customer = Customer.findById(User.get(userToFind.id).customer.id)
					log.debug "dbCustomer in kam service is =="+customer
					log.debug "In Get Projects"
					if(customer.flow =="equipment"){
						log.debug "called equipment flow"
						def getProjects = projectService.getProjects(object)
						log.debug getProjects
						int index = getProjects.lastIndexOf("/")
						String trimmedXml = getProjects.substring(0,index-1).trim()
						log.debug "returning xml=="+trimmedXml
						render trimmedXml
					}
					else if(customer.flow == "document"){
						log.debug "called document type"
						def getProjects = kamGenericService.getProjects(object)
						log.debug getProjects
						int index = getProjects.lastIndexOf("/")
						String trimmedXml = getProjects.substring(0,index-1).trim()
						log.debug "returning xml=="+trimmedXml
						render trimmedXml
					}
				}catch(Exception e){
					e.printStackTrace()
				}
				break;
			case 'GETDOCUMENTS':
				log.debug "In get documents"
				def getDocuments = documentServiceNewService.getDocuments(object)
				int index = getDocuments.lastIndexOf("/")
				String trimmedXml = getDocuments.substring(0,index-1).trim()
				log.debug "returning xml=="+trimmedXml
				render trimmedXml
				break;
			case 'GETTICKETS':
				log.debug "In get tickets"
				def getTickets = ticketService.getTickets(object)
				int index = getTickets.lastIndexOf("/")
				String trimmedXml = getTickets.substring(0,index-1).trim()
				log.debug "returning xml=="+trimmedXml
				render trimmedXml
				break;
			case 'POSTTICKETS':
				log.debug "In post tickets"
				def postTickets = ticketService.postTickets(object,breakedXml)
				int index = postTickets.lastIndexOf("/")
				String trimmedXml = postTickets.substring(0,index-1).trim()
				log.debug "returning xml=="+trimmedXml
				render trimmedXml
				break;
			case 'GETBUILDING':
				log.debug "in get buildings"
				def getBuildings = buildingService.getBuilding(object,request.contextPath,request.serverName)
				int index = getBuildings.lastIndexOf("/")
				String trimmedXml = getBuildings.substring(0,index-1).trim()
				log.debug "returning xml=="+trimmedXml
				render trimmedXml
				break;
			case 'GETMASTER':
				log.debug "in getMASTER"
				def getMaster = masterService.getMaster(object)
				int index = getMaster.lastIndexOf("/")
				String trimmedXml = getMaster.substring(0,index-1).trim()
				log.debug "returning xml=="+trimmedXml
				render trimmedXml
				break;
			case 'POSTMASTER':
				log.debug "in POSTMASTER"
				def postMaster = postMasterService.postMaster(object,breakedXml)
				int index = postMaster.lastIndexOf("/")
				String trimmedXml = postMaster.substring(0,index-1).trim()
				log.debug "returning xml=="+trimmedXml
				render trimmedXml
				break;
			case 'POSTBUILDING':
				log.debug "in POSTBUILDING"
				def postBuilding = postBuildingService.postBuilding(object,breakedXml)
				int index = postBuilding.lastIndexOf("/")
				String trimmedXml = postBuilding.substring(0,index-1).trim()
				log.debug "returning xml=="+trimmedXml
				render trimmedXml
				//postBuildingMailService.sendBuildingSynchronisationMail(object,breakedXml)
				break;
			case 'BUILDINGTICKETS':
				log.debug "in POSTBUILDING"
				def getBuildingTickets = buildingTicketService.getBuildingTickets(object)
				int index = getBuildingTickets.lastIndexOf("/")
				String trimmedXml = getBuildingTickets.substring(0,index-1).trim()
				log.debug "returning xml=="+trimmedXml
				render trimmedXml
				break;
				/*case 'FLOORTICKETS':
				log.debug "in FLOORTICKET"
				def getFloorTickets = floorTicketService.getFloorTickets(object)
				int index = getFloorTickets.lastIndexOf("/")
				String trimmedXml = getFloorTickets.substring(0,index-1).trim()
				log.debug "returning xml=="+trimmedXml
				render trimmedXml
				break;*/
			default:
				render userService.methodNotFound(object)
		}

	}
	def xmlRead = {
		log.debug "received "+params
		def postAttachment = ticketService.postAttachment(params.loginname,params.password,params.mode,params.id,params.ticketid)
		render postAttachment
	}
	def checkpointRead = {

		log.debug "received "+params
		def postBuildingAttachment = ticketService.postBuildingAttachment(params.loginname,params.password,params.mode,params.attachmentid)
		
		render postBuildingAttachment

	}
	
	def callDocumentPDF = {
		byte[] b
		def redirectUrl="/api/documentPdf?id="
		log.debug "log after redirect url"
		def pdfLink = grailsApplication.config.report.server.url+redirectUrl
		log.debug "pdfLink created is===="+pdfLink
		try{
		b = pdfService.buildPdf(pdfLink)
		log.debug "report link  is now====="+pdfLink
		log.debug "byte array returned is==="+b
		OutputStream out = new FileOutputStream("/home/oodles/documentReport/report.pdf");
		out.write(b);
		out.close();
		}catch(FileNotFoundException fnfe){
		fnfe.printStackTrace()
		}
		catch(NullPointerException npe){
			npe.printStackTrace()
			}
	}
	def documentPdf = {
		def user=springSecurityService.currentUser
			def  footerClass, customerListClass, docDetailsClass, showImage=true, blankRowValue=" "
			def isPdf
				isPdf=true
				customerListClass=''
				docDetailsClass=''
			def document = Document.get(params.id)
			//def document = Document.get(session.getAttribute("createdDocumentName"))
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
			
			[document:document,documentItemValueMap:documentItemValueMap,customerListClass:customerListClass,docDetailsClass:docDetailsClass,footerClass:footerClass,isPdf:isPdf,showImage:showImage,blankRowValue:blankRowValue,attachments:documentAttachments]
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