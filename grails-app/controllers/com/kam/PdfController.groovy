package com.kam

import java.text.SimpleDateFormat;

import grails.plugins.springsecurity.SpringSecurityService;
import org.jfree.chart.*
import org.jfree.chart.entity.*
import org.jfree.data.general.*
import pdf.PdfService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import java.awt.Color;

class PdfController {

  PdfService pdfService
  def springSecurityService
  def index = { redirect(action: demo) }

  def pdfLink = {
    try{
      byte[] b
      def baseUri = request.scheme + "://" + request.serverName + ":" + request.serverPort + grailsAttributes.getApplicationUri(request)
      // def baseUri = g.createLink(uri:"/", absolute:"true").toString()
      // TODO: get this working...
      //if(params.template){
        //println "Template: $params.template"
        //def content = g.render(template:params.template, model:[pdf:params])
        //b = pdfService.buildPdfFromString(content.readAsString(), baseUri)
      //}
      if(params.pdfController){
        //println "GSP - Controller: $params.pdfController , Action: $params.pdfAction, Id: $params.pdfId" 
        def content = g.include(controller:params.pdfController, action:params.pdfAction, id:params.pdfId)
        b = pdfService.buildPdfFromString(content.readAsString(), baseUri)
      }
      else{
        def url = baseUri + params.url
        b = pdfService.buildPdf(url)
      }
      response.setContentType("application/pdf")
      response.setHeader("Content-disposition", "attachment; filename=" + (params.filename ?: "document.pdf"))
      response.setContentLength(b.length)
      response.getOutputStream().write(b)
    }
    catch (Throwable e) {
      println "there was a problem with PDF generation ${e}"
      //if(params.template) render(template:params.template)
      if(params.pdfController) redirect(controller:params.pdfController, action:params.pdfAction, params:params)
      else redirect(uri:params.url + '?' + request.getQueryString())
    }
  }

  def pdfForm = {
    try{
      byte[] b
      def baseUri = request.scheme + "://" + request.serverName + ":" + request.serverPort + grailsAttributes.getApplicationUri(request)
      // def baseUri = g.createLink(uri:"/", absolute:"true").toString()
      if(request.method == "GET") {
        def url = baseUri + params.url + '?' + request.getQueryString()
        //println "BaseUri is $baseUri"
        //println "Fetching url $url"
        b = pdfService.buildPdf(url)
      }
      if(request.method == "POST"){
        def content
        if(params.template){
          //println "Template: $params.template"
          content = g.render(template:params.template, model:[pdf:params])
        }
        else{
          content = g.include(controller:params.pdfController, action:params.pdfAction, id:params.id, pdf:params)
        }
        b = pdfService.buildPdfFromString(content.readAsString(), baseUri)
      }
      response.setContentType("application/pdf")
      response.setHeader("Content-disposition", "attachment; filename=" + (params.filename ?: "document.pdf"))
      response.setContentLength(b.length)
      response.getOutputStream().write(b)
    }
    catch (Throwable e) {
      println "there was a problem with PDF generation ${e}"
      if(params.template) render(template:params.template)
      if(params.url) redirect(uri:params.url + '?' + request.getQueryString())
      else redirect(controller:params.pdfController, action:params.pdfAction, params:params)
    }
  }

  def demo = {
    def firstName = params.first ?: "Eric"
    def lastName = params.last ?: "Cartman"
    def age = params.age
    return [firstName:firstName, lastName:lastName, age:age]
  }

  def documentPdf = {
    def user=springSecurityService.currentUser
		def  footerClass, customerListClass, docDetailsClass, showImage=true, blankRowValue=" "
		def isPdf
			isPdf=true
			customerListClass=''
			docDetailsClass=''
		
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
		
		[document:document,documentItemValueMap:documentItemValueMap,customerListClass:customerListClass,docDetailsClass:docDetailsClass,footerClass:footerClass,isPdf:isPdf,showImage:showImage,blankRowValue:blankRowValue,attachments:documentAttachments]
		
  }
  
 
  def equipmentReportPdf = {
		def isPdf=false, customerListClass, docDetailsClass, reportId
			reportId=params.id
			log.debug "report Id received in pdf controller is====="+reportId
			customerListClass=''
			docDetailsClass=''
			isPdf=true
		def report = Report.get(reportId.toLong())
		log.debug "report in pdf controller is====="+report
		def aduiesComment = ReportValue.findWhere(report:report,type:'aduies')
		def conlusionComment = ReportValue.findWhere(report:report,type:'conlusion')
		def aduiesComments='',conlusionComments=''
		if(aduiesComment!=null && aduiesComment.value!='')
			aduiesComments=aduiesComment.value.toString().split('[.]')
		if(conlusionComment!=null  && conlusionComment.value!='')
			conlusionComments=conlusionComment.value.toString().split('[.]') 
		def reportCheckpoints,functionalCheckpoints,visualCheckpoints, signature1,signature2,customerSignature='', userSignature=''
		signature1 =  ReportValue.findWhere(report:report,type:'CustomerSignature')
		signature2 =  ReportValue.findWhere(report:report,type:'UserSignature')
		if(signature1!=null)
			customerSignature = signature1.value
		if(signature2!=null)
			userSignature = signature2.value
		def customerId = report.building.project.customer.id
		println customerId
		reportCheckpoints=CheckpointsEquipment.findAllWhere(customerId:customerId.toString())
		log.debug "report checkpoint in pdfContyroller>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+reportCheckpoints
		functionalCheckpoints=CheckpointsEquipment.findAllWhere(customerId:customerId.toString(),category:'Functional')
		log.debug "functional checkpoint in pdfContyroller>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+functionalCheckpoints
		visualCheckpoints=CheckpointsEquipment.findAllWhere(customerId:customerId.toString(),category:'Visual')
		log.debug "visual checkpoint in pdfContyroller>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+visualCheckpoints
		def equipmentCheckpoints = EquipmentCheckpoint.findAllWhere(reportId:report.id.toString())
		log.debug "Equipment checkpoints in pdf controller>>>>>>>>>>>>>>>>>>>>>>>>"+equipmentCheckpoints
		def totalEquipments = EquipmentCheckpoint.executeQuery("select distinct(e.id) from EquipmentCheckpoint ec,Equipment e where ec.reportId='"+report.id+"' AND e.id = ec.equipment.id")
		log.debug "total Equipments in pdf controller>>>>>>>>>>>>>>>>>>>>>>>>"+totalEquipments
		def totalEquipmentsRejected = EquipmentCheckpoint.executeQuery("select distinct(e.id) from EquipmentCheckpoint ec,Equipment e where ec.reportId='"+report.id+"' AND e.id = ec.equipment.id AND ec.status='Afgekeurd'")
		log.debug "total Equipments rejected in pdf controller>>>>>>>>>>>>>>>>>>>>>>>>"+totalEquipmentsRejected
		def totalEquipmentsIndication = EquipmentCheckpoint.executeQuery("select distinct(e.id) from EquipmentCheckpoint ec,Equipment e where ec.reportId='"+report.id+"' AND e.id = ec.equipment.id AND e.equipmentType2='Indication' ")
		log.debug "totalEquipmentsIndication in pdf controller>>>>>>>>>>>>>>>>>>>>>>>>"+totalEquipmentsIndication
		def totalEquipmentsLighting = EquipmentCheckpoint.executeQuery("select distinct(e.id) from EquipmentCheckpoint ec,Equipment e where ec.reportId='"+report.id+"' AND e.id = ec.equipment.id AND e.equipmentType2='Lighting'")
		log.debug "totalEquipmentsLighting in pdf controller>>>>>>>>>>>>>>>>>>>>>>>>"+totalEquipmentsLighting
		def totalIndicationRejected = EquipmentCheckpoint.executeQuery("select distinct(e.id) from EquipmentCheckpoint ec,Equipment e where ec.reportId='"+report.id+"' AND e.id = ec.equipment.id AND e.equipmentType2='indication' AND ec.status='Afgekeurd'")
		log.debug "total Equipments lighting in pdf controller>>>>>>>>>>>>>>>>>>>>>>>>"+totalIndicationRejected
		def totalLightingRejected = EquipmentCheckpoint.executeQuery("select distinct(e.id) from EquipmentCheckpoint ec,Equipment e where ec.reportId='"+report.id+"' AND e.id = ec.equipment.id AND e.equipmentType2='Lighting' AND ec.status='Afgekeurd'")
		log.debug "total lighting rejected in pdf controller>>>>>>>>>>>>>>>>>>>>>>>>"+totalLightingRejected
		
		def floorComments = ReportValue.findAllWhere(report:report,type:'FloorComment')
		def formatter = new SimpleDateFormat('dd/MM/yyyy')
		def reviewDate = formatter.format(report.dateCreated)
		if(totalEquipmentsIndication.size()>0)
		createPieCharts("1", report.id,[totalIndicationRejected.size(),totalEquipmentsIndication.size()],"Indication Armature status")
		if(totalEquipmentsLighting.size()>0)
		createPieCharts("2", report.id,[totalLightingRejected.size(),totalEquipmentsLighting.size()],"Lighting Armature status")
		[aduiesComments:aduiesComments,conlusionComments:conlusionComments,report:report,floorComments:floorComments,totalEquipments:totalEquipments,totalEquipmentsRejected:totalEquipmentsRejected,customerListClass:customerListClass,docDetailsClass:docDetailsClass,isPdf:isPdf,equipmentCheckpoints:equipmentCheckpoints,reportCheckpoints:reportCheckpoints,functionalCheckpoints:functionalCheckpoints,visualCheckpoints:visualCheckpoints,customerSignature:customerSignature,userSignature:userSignature,reviewDate:reviewDate,totalEquipmentsIndication:totalEquipmentsIndication,totalLightingRejected:totalLightingRejected,totalEquipmentsLighting:totalEquipmentsLighting,totalIndicationRejected:totalIndicationRejected]
	}
  
  
  def createPieCharts(def counter, def reportId,def chartValues, def title){
	  final DefaultPieDataset data = new DefaultPieDataset()
	  
	  def firstValue=(chartValues[0]*100)/chartValues[1]
	  def secondValue=((chartValues[1]-chartValues[0])*100)/chartValues[1]
	  
	  data.setValue("Afgekeurd", new Double(firstValue))
	  data.setValue("Goedgekeurd", new Double(secondValue))
	  
	  JFreeChart chart = ChartFactory.createPieChart(title, data, true, true, false)
	  
	  PiePlot plot = (PiePlot)chart.getPlot();
	  
	  // Specify the colors here
	  
	  Color[] color = [Color.red, Color.green]
	  List <Comparable> keys = data.getKeys();
	  int aInt;
	  
	  for (int i = 0; i < keys.size(); i++)
	  {
		  aInt = i % color.length;
		  plot.setSectionPaint(keys.get(i), color[aInt]);
	  }
	  try {
	   final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection())
	   final File file1 = new File(grailsApplication.config.report.chart.path+"piechart"+counter+"-"+reportId+".png")
	   ChartUtilities.saveChartAsPNG(file1, chart, 600, 400, info)
	  }catch (Exception e) {
	   println e.getMessage()
	  }
  }

  def demo3 = {
    def today = new Date()
    def tomorrow = today +1
	final DefaultPieDataset data = new DefaultPieDataset()
	data.setValue("One", new Double(43.2))
	data.setValue("Two", new Double(10.0))
	data.setValue("Three", new Double(27.5))
	data.setValue("Four", new Double(17.5))
	data.setValue("Five", new Double(11.0))
	data.setValue("Six", new Double(19.4))    
	JFreeChart chart = ChartFactory.createPieChart("Pie Chart ", data, true, true, false)
	try {          
		final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection())
		final File file1 = new File("C:\\Users\\Oodles\\Documents\\workspace-sts-2.9.1.RELEASE\\AppUBuild\\web-app\\PieChart\\piechart.png")
		ChartUtilities.saveChartAsPNG(file1, chart, 600, 400, info)
	}catch (Exception e) {
		println e.getMessage()
	}
    def content = g.include(controller:"pdf", action:"sampleInclude", params:['today':today, 'tomorrow':tomorrow])
    return ['content':content, 'pdf':params, 'id':params.id]
  }

  def sampleInclude = {
    def bar = 'foo'
    def today = params?.today
    def tomorrow = params?.tomorrow
    return ['bar':bar, 'today':today, 'tomorrow':tomorrow]
    //[today:today, tomorrow:tomorrow]
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

