package com.kam

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

import java.awt.Color;
import java.text.DateFormat
import java.text.SimpleDateFormat
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
/**
* @author Shridhar
*
*/
@Secured(['ROLE_ADMIN','ROLE_USER','IS_AUTHENTICATED_REMEMBERED'])
class ReportsController {
	
	def springSecurityService

    def index() { }
	
	def equipmentReport = {
		log.debug "reached reports controller"
		def user=springSecurityService.currentUser
		def isPdf=false, customerListClass, docDetailsClass, reportId
		if(params.id!=null){
			reportId=params.id
			session.setAttribute("reportId", params.id)
			customerListClass='customerList'
			docDetailsClass='docDetails'
			isPdf=false
		}
		else{
			reportId=session.getAttribute("reportId")
			customerListClass=''
			docDetailsClass=''
			isPdf=true
		}
		def report = Report.get(reportId)
		def aduiesComment = ReportValue.findWhere(report:report,type:'aduies')
		def conlusionComment = ReportValue.findWhere(report:report,type:'conclusion')
		log.debug "under reports controller=conlusionComment=="+conlusionComment
		def aduiesComments='',conlusionComments=''
		if(aduiesComment!=null && aduiesComment.value!='')
			aduiesComments=aduiesComment.value.toString().split('[.]')
		if(conlusionComment!=null  && conlusionComment.value!='')
			conlusionComments=conlusionComment.value.toString().split('[.]') 
			log.debug "conlusionComments has value==="+conlusionComments
		def reportCheckpoints,functionalCheckpoints,visualCheckpoints, signature1,signature2,customerSignature='', userSignature=''
		signature1 =  ReportValue.findWhere(report:report,type:'CustomerSignature')
		signature2 =  ReportValue.findWhere(report:report,type:'UserSignature')
		if(signature1!=null)
			customerSignature = signature1.value
		if(signature2!=null)
			userSignature = signature2.value
		reportCheckpoints=CheckpointsEquipment.findAllWhere(customerId:user.customer.id.toString())
		functionalCheckpoints=CheckpointsEquipment.findAllWhere(customerId:user.customer.id.toString(),category:'Functional')
		visualCheckpoints=CheckpointsEquipment.findAllWhere(customerId:user.customer.id.toString(),category:'Visual')
		def equipmentCheckpoints = EquipmentCheckpoint.findAllWhere(reportId:report.id.toString())
		def totalEquipments = EquipmentCheckpoint.executeQuery("select distinct(e.id) from EquipmentCheckpoint ec,Equipment e where ec.reportId='"+report.id+"' AND e.id = ec.equipment.id")
		def totalEquipmentsRejected = EquipmentCheckpoint.executeQuery("select distinct(e.id) from EquipmentCheckpoint ec,Equipment e where ec.reportId='"+report.id+"' AND e.id = ec.equipment.id AND ec.status='Afgekeurd'")
		def totalEquipmentsIndication = EquipmentCheckpoint.executeQuery("select distinct(e.id) from EquipmentCheckpoint ec,Equipment e where ec.reportId='"+report.id+"' AND e.id = ec.equipment.id AND e.equipmentType2='Indication' ")
		def totalEquipmentsLighting = EquipmentCheckpoint.executeQuery("select distinct(e.id) from EquipmentCheckpoint ec,Equipment e where ec.reportId='"+report.id+"' AND e.id = ec.equipment.id AND e.equipmentType2='Lighting'")
		def totalIndicationRejected = EquipmentCheckpoint.executeQuery("select distinct(e.id) from EquipmentCheckpoint ec,Equipment e where ec.reportId='"+report.id+"' AND e.id = ec.equipment.id AND e.equipmentType2='indication' AND ec.status='Afgekeurd'")
		def totalLightingRejected = EquipmentCheckpoint.executeQuery("select distinct(e.id) from EquipmentCheckpoint ec,Equipment e where ec.reportId='"+report.id+"' AND e.id = ec.equipment.id AND e.equipmentType2='Lighting' AND ec.status='Afgekeurd'")
		
		def floorComments = ReportValue.findAllByReportAndType(report,'FloorComment',[sort:'floor.floorNumber'])
		def formatter = new SimpleDateFormat('dd/MM/yyyy')
		def reviewDate = formatter.format(report.dateCreated)
		if(totalEquipmentsIndication.size()>0)
		createPieCharts("1", report.id,[totalIndicationRejected.size(),totalEquipmentsIndication.size()],"_")
		if(totalEquipmentsLighting.size()>0)
		createPieCharts("2", report.id,[totalLightingRejected.size(),totalEquipmentsLighting.size()],"_")
		def customerSettings = CustomizeSettings.findByCustomer(user.customer)
		log.debug "customer setting are==="+customerSettings
		def customerImage
		if(customerSettings==null){
			customerImage = "default"
		}
		else{
			customerImage = customerSettings.logo
		}
		[loggedinUser:user,role:UserRole.findWhere(user:user).role.authority,aduiesComments:aduiesComments,conlusionComments:conlusionComments,report:report,floorComments:floorComments,totalEquipments:totalEquipments,totalEquipmentsRejected:totalEquipmentsRejected,customerListClass:customerListClass,docDetailsClass:docDetailsClass,isPdf:isPdf,equipmentCheckpoints:equipmentCheckpoints,reportCheckpoints:reportCheckpoints,functionalCheckpoints:functionalCheckpoints,visualCheckpoints:visualCheckpoints,customerSignature:customerSignature,userSignature:userSignature,reviewDate:reviewDate,totalEquipmentsIndication:totalEquipmentsIndication,totalLightingRejected:totalLightingRejected,totalEquipmentsLighting:totalEquipmentsLighting,totalIndicationRejected:totalIndicationRejected,customerImage:customerImage]
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
	
	
	
	def reportList = {
		log.debug "reached reportsList"
		def user=springSecurityService.currentUser
		if(params.id!=null)
		session.setAttribute("SelectedCurrentBuilding", params.id)
		def building = Building.get(session.getAttribute("SelectedCurrentBuilding"))
		[role:UserRole.findWhere(user:user).role.authority,loggedinUser:user,building:building]
	}
	
	def editReport = {
		def user=springSecurityService.currentUser
		def report = Report.get(params.id)
		def aduiesComment = ReportValue.findWhere(report:report,type:'aduies')
		def conlusionComment = ReportValue.findWhere(report:report,type:'conlusion')
		def aduiesComments='',conlusionComments=''
		if(aduiesComment!=null)
		aduiesComments=aduiesComment.value.toString().split('[.]')
		if(conlusionComment!=null)
		conlusionComments=conlusionComment.value.toString().split('[.]') 
		def equipmentCheckpoints = EquipmentCheckpoint.findAllWhere(reportId:report.id.toString())
		def reportCheckpoints,functionalCheckpoints,visualCheckpoints,signature1,signature2,customerSignature='', userSignature=''
		signature1 =  ReportValue.findWhere(report:report,type:'CustomerSignature')
		signature2 =  ReportValue.findWhere(report:report,type:'UserSignature')
		if(signature1!=null)
			customerSignature = signature1.value
		if(signature2!=null)
			userSignature = signature2.value
		reportCheckpoints=CheckpointsEquipment.findAllWhere(customerId:user.customer.id.toString())
		functionalCheckpoints=CheckpointsEquipment.findAllWhere(customerId:user.customer.id.toString(),category:'Functional')
		visualCheckpoints=CheckpointsEquipment.findAllWhere(customerId:user.customer.id.toString(),category:'Visual')
		
		def totalEquipments = EquipmentCheckpoint.executeQuery("select distinct(e.id) from EquipmentCheckpoint ec,Equipment e where ec.reportId='"+report.id+"' AND e.id = ec.equipment.id")
		def totalEquipmentsRejected = EquipmentCheckpoint.executeQuery("select distinct(e.id) from EquipmentCheckpoint ec,Equipment e where ec.reportId='"+report.id+"' AND e.id = ec.equipment.id AND ec.status='Afgekeured'")
		def totalEquipmentsIndication = EquipmentCheckpoint.executeQuery("select distinct(e.id) from EquipmentCheckpoint ec,Equipment e where ec.reportId='"+report.id+"' AND e.id = ec.equipment.id AND e.equipmentType2='Indication' ")
		def totalEquipmentsLighting = EquipmentCheckpoint.executeQuery("select distinct(e.id) from EquipmentCheckpoint ec,Equipment e where ec.reportId='"+report.id+"' AND e.id = ec.equipment.id AND e.equipmentType2='Lighting'")
		def totalIndicationRejected = EquipmentCheckpoint.executeQuery("select distinct(e.id) from EquipmentCheckpoint ec,Equipment e where ec.reportId='"+report.id+"' AND e.id = ec.equipment.id AND e.equipmentType2='indication' AND ec.status='Afgekeurd'")
		def totalLightingRejected = EquipmentCheckpoint.executeQuery("select distinct(e.id) from EquipmentCheckpoint ec,Equipment e where ec.reportId='"+report.id+"' AND e.id = ec.equipment.id AND e.equipmentType2='Lighting' AND ec.status='Afgekeurd'")
		
		def floorComments = ReportValue.findAllWhere(report:report,type:'FloorComment')
		def formatter = new SimpleDateFormat('dd/MM/yyyy')  
		def reviewDate = formatter.format(report.dateCreated)
		if(totalEquipmentsIndication.size()>0)  
		createPieCharts("1", report.id,[totalIndicationRejected.size(),totalEquipmentsIndication.size()],"_")
		if(totalEquipmentsLighting.size()>0)
		createPieCharts("2", report.id,[totalLightingRejected.size(),totalEquipmentsLighting.size()],"_")
		[loggedinUser:user,role:UserRole.findWhere(user:user).role.authority,aduiesComments:aduiesComments,conlusionComments:conlusionComments,report:report,floorComments:floorComments,totalEquipments:totalEquipments,totalEquipmentsRejected:totalEquipmentsRejected,equipmentCheckpoints:equipmentCheckpoints,reportCheckpoints:reportCheckpoints,functionalCheckpoints:functionalCheckpoints,visualCheckpoints:visualCheckpoints,customerSignature:customerSignature,userSignature:userSignature,reviewDate:reviewDate,totalEquipmentsIndication:totalEquipmentsIndication,totalLightingRejected:totalLightingRejected,totalEquipmentsLighting:totalEquipmentsLighting,totalIndicationRejected:totalIndicationRejected]
	}
	
	
	
	def saveReportEdits = {
		def user=springSecurityService.currentUser
		def report = Report.get(params.reportId.toLong())
		def equipmentCheckpoints = EquipmentCheckpoint.findAllWhere(reportId:report.id.toString())
		for(def equipmentCheckpoint in equipmentCheckpoints){
			equipmentCheckpoint.comment = params['equipmentCheckpoint'+equipmentCheckpoint.id.toString()]	
		}
		
		def reportValues = ReportValue.findAllWhere(report:report)
		
		for(def reportValue in reportValues){
			def floorComment=''
			if(reportValue.type=='FloorComment'){
				String[] comments = params['floorComment'+reportValue.floor.id.toString()].toString().replace('[','').replace(']','').split(',')
				for(int i=0;i<comments.length;i++){
					floorComment=floorComment+comments[i].trim()+'.'
				}
				reportValue.value=floorComment.substring(0, floorComment.length()-1)
			}
			else{
				reportValue.value=params[reportValue.type+'Comment']
			}
		}
		report.lastUpdated=new Date()
		report.save()
		redirect action:'reportList'	
	}
	
	def deleteReport = {
		def returnObject = [:]
		def report = Report.get(params.id)
		report.delete()
		returnObject.sucess=true
		returnObject.id=report.id
		render returnObject as JSON
	}
}
