package com.kam

import java.lang.ProcessEnvironment.Value
import java.util.Map;
import java.util.HashMap.Entry

import grails.converters.JSON;
import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.xhtmlrenderer.pdf.ITextRenderer;


import com.opensymphony.module.sitemesh.HTMLPage;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key
/**
* @author Shridhar
*
*/

@Secured(['ROLE_ADMIN','ROLE_USER','IS_AUTHENTICATED_REMEMBERED'])
class EquipmentController {

	def springSecurityService
	def pdfService

    def index() { }

	def equipmentList={
		def user=springSecurityService.currentUser
		if(user.customer.flow=='equipment'){
		def equipmentsList=Equipment.findAllByCustomerAndIsDeleted(user.customer.id,false,[sort:'lastUpdated',order:'desc'])
		def projectList=Project.findAllByCustomerAndIsDeleted(user.customer,false,[sort:'projectName',order:'asc'])
		session.setAttribute("objectType", "")
		session.setAttribute("objectValue", "")
		session.setAttribute("project", "")
		session.setAttribute("building", "")
		session.setAttribute("floor", "")
		session.setAttribute("actionPerformed","")
		Arrays.sort(equipmentsList)
		[role:UserRole.findWhere(user:user).role.authority,loggedinUser:user,equipmentsList:equipmentsList,projectList:projectList]
		}
		else
		redirect controller:'site'
	}

	def equipmentListPdf={
		def user=springSecurityService.currentUser
		def defaultAction = false,customerLogo=''
		def (getCurrentList,newList,update,nextObject)=filterObjects(session.getAttribute("objectType"),session.getAttribute("objectValue"))
		def equipmentsList = new ArrayList()
		log.debug "get current list is==="+getCurrentList
		log.debug "newlist is==="+newList
		log.debug "update list is==="+update
		log.debug "nextobject list is==="+nextObject
		log.debug "action performed is==="+session.getAttribute("actionPerformed")
		log.debug " Object type is==="+session.getAttribute("objectType")
		log.debug "equipmentList is===" +equipmentsList
		
		if(session.getAttribute("actionPerformed")=='filter'){
			if(session.getAttribute("objectType")=="")
				equipmentsList=Equipment.findAllByCustomerAndIsDeleted(user.customer.id,false,[sort:params.name,order:params.order])
			else{
				if(nextObject=='floor'||nextObject=='building'){
				equipmentsList = getAllEquipmentsList(getCurrentList,nextObject)
				
				def equipmentListNames = []
				for(equip in equipmentsList){
					equipmentListNames.add(equip.name)
				}
				log.debug "equipments name list is===="+equipmentListNames
				Collections.sort(equipmentListNames,new StringArraySort<String>())
				equipmentsList.clear()
				equipmentsList = sortEquipmentsList(equipmentListNames,user.customer.id)
				//
			}
				else{
					def equipmentListNames=[]
					log.debug "get current list is==="+getCurrentList
					log.debug "newlist is==="+newList
					log.debug "update list is==="+update
					log.debug "nextobject list is==="+nextObject
				 equipmentsList = getAllEquipmentsListNew(getCurrentList,nextObject,newList)
				log.debug "domain equipment list is==="+equipmentsList
				for(equip in equipmentsList){
					equipmentListNames.add(equip.name)
				}
				Collections.sort(equipmentListNames,new StringArraySort<String>())
				equipmentsList.clear()
				equipmentsList = sortEquipmentsListEquipment(equipmentListNames,user.customer.id)
				log.debug "now equipments list is==="+equipmentsList
				}	
		}
		}
			//
		else if(session.getAttribute("actionPerformed")=='search'){
			equipmentsList=Equipment.executeQuery("select e.name,p.projectName,b.buildingName,f.floorNumber,e.status,e.lastUpdated,e.dateCreated,e.isDeleted,e.id from Equipment e, Project p, Building b, Floor f where ( p.projectName like '%"+session.getAttribute("searchQuery")+"%' Or e.name like '%"+session.getAttribute("searchQuery")+"%' Or b.buildingName like '%"+session.getAttribute("searchQuery")+"%' Or f.floorNumber like '%"+session.getAttribute("searchQuery")+"%' Or e.lastUpdated like '%"+session.getAttribute("searchQuery")+"%' Or e.status like '%"+session.getAttribute("searchQuery")+"%') AND e.floor.id = f.id AND f.building.id = b.id AND b.project.id = p.id AND  e.customer=:customer",[customer:user.customer.id.toInteger()])			
			defaultAction = true
		}
		else if(session.getAttribute("actionPerformed")==''){
			def equipmentListNames = []
			equipmentsList=Equipment.findAllByCustomerAndIsDeleted(user.customer.id,false)
			
			log.debug "domain equipment list is==="+equipmentsList
			for(equip in equipmentsList){
				equipmentListNames.add(equip.name)
			}
			log.debug "equipments name list is===="+equipmentListNames
			Collections.sort(equipmentListNames,new StringArraySort<String>())
			equipmentsList.clear()
			equipmentsList = sortEquipmentsList(equipmentListNames,user.customer.id)
			log.debug "now equipments list is==="+equipmentsList
		}
		def customerSettings = CustomizeSettings.findWhere(customer:user.customer)
		if(customerSettings!=null)
			customerLogo = customerSettings.logo
		def date=new java.util.Date()
		[equipmentsList:equipmentsList,date:date,project:session.getAttribute('project'),building:session.getAttribute('building'),floor:session.getAttribute('floor'),room:session.getAttribute('room'),defaultAction:defaultAction,customerLogo:customerLogo]
	}

	def viewEquipment= {
		def user=springSecurityService.currentUser
		def equipmentId
		if(params.id!='null' && params.id!=null){
			equipmentId=params.id
			session.setAttribute("equipmentId",params.id)
		}
		else
			equipmentId=session.getAttribute("equipmentId")
		def equipment=Equipment.get(equipmentId)
		def projectList=Project.findAllByCustomerAndIsDeleted(user.customer,false,[sort:'projectName',order:'asc'])
		def report,equipmentCheckpoints=null,reportCheckpoints
		report=getLatestReport(user.username,equipment)
		log.debug "report is===="+report
		if(report!=null){
			equipmentCheckpoints = EquipmentCheckpoint.findAllWhere(reportId:report,equipment:equipment)
			equipmentCheckpoints = equipmentCheckpoints.sort({it.id})
		}
		else{
		equipmentCheckpoints = EquipmentCheckpoint.findAllWhere(equipment:equipment)
		log.debug "equipmets checkpoint size is===="+equipmentCheckpoints.size()
		}
		reportCheckpoints = CheckpointsEquipment.findAllWhere(customerId:user.customer.id.toString())

		[role:UserRole.findWhere(user:user).role.authority,loggedinUser:user,equipment:equipment,projectList:projectList,equipmentCheckpoints:equipmentCheckpoints,reportCheckpoints:reportCheckpoints]
	}

	def equipmentOverview = {
		def user=springSecurityService.currentUser
		def equipmentId
		if(params.id!='null' && params.id!=null){
			equipmentId=params.id
			session.setAttribute("equipmentId",params.id)
		}
		else
			equipmentId=session.getAttribute("equipmentId")
		def equipment=Equipment.get(equipmentId)
		def projectList=Project.findAllByCustomerAndIsDeleted(user.customer,false,[sort:'projectName',order:'asc'])
		def report,equipmentCheckpoints,reportCheckpoints
		report=getLatestReport(user.username,equipment)
		if(report!=null) 
		equipmentCheckpoints = EquipmentCheckpoint.findAllWhere(reportId:report,equipment:equipment)
		else
		equipmentCheckpoints =  EquipmentCheckpoint.findAllWhere(equipment:equipment)
		reportCheckpoints = CheckpointsEquipment.findAllWhere(customerId:user.customer.id.toString())

		def brands = Brand.list()
		def battery = Battery.list()
		def light = Light.list()
		def armatures = Armatuur.list()
		def emergencyUnit = EmergencyUnitOfPrint.list()

		[role:UserRole.findWhere(user:user).role.authority,loggedinUser:user,equipment:equipment,projectList:projectList,equipmentCheckpoints:equipmentCheckpoints,brands:brands,battery:battery,light:light,armatures:armatures,emergencyUnit:emergencyUnit,reportCheckpoints:reportCheckpoints]

	}

	def editEquipment = {
		def user=springSecurityService.currentUser
		def equipmentId
		if(params.id!='null' && params.id!=null){
			equipmentId=params.id
			session.setAttribute("equipmentId",params.id)
		}
		else
			equipmentId=session.getAttribute("equipmentId")
		def equipment=Equipment.get(equipmentId)
		def projectList=Project.findAllByCustomerAndIsDeleted(user.customer,false,[sort:'projectName',order:'asc'])
		def report,equipmentCheckpoints,reportCheckpoints
		report=getLatestReport(user.username,equipment)
		if(report!=null) 
		equipmentCheckpoints = EquipmentCheckpoint.findAllWhere(reportId:report,equipment:equipment)
		else
		equipmentCheckpoints =  EquipmentCheckpoint.findAllWhere(equipment:equipment)

		reportCheckpoints = CheckpointsEquipment.findAllWhere(customerId:user.customer.id.toString())
		def brands = Brand.list()
		def battery = Battery.list()
		def light = Light.list()
		def armatures = Armatuur.list()
		def emergencyUnit = EmergencyUnitOfPrint.list()
		def groupNos = GroupNr.list()
		def kasts = Kast.list()

		[role:UserRole.findWhere(user:user).role.authority,loggedinUser:user,equipment:equipment,projectList:projectList,equipmentCheckpoints:equipmentCheckpoints,brands:brands,battery:battery,light:light,armatures:armatures,emergencyUnit:emergencyUnit,reportCheckpoints:reportCheckpoints,groupNos:groupNos,kasts:kasts]

	}

	def saveEquipmentChanges = {
		def user=springSecurityService.currentUser
		def equipment
		equipment=Equipment.get(params.currentEquipmentId.toLong())
		equipment.name=params.equipmentName
		equipment.description=params.equipmentDescription
		def light,armatuur,battery,brand,emergencyUnitOfPrint,groupnr,kast
		(light,armatuur,battery,brand,emergencyUnitOfPrint,groupnr,kast)=getComponentsForEquipments(params['oldBrandName'],params['oldBatteryName'],params['oldArmatureName'],params['oldLightName'],params['oldEmergencyUnitOfPrintName'],params['oldGroupName'],params['oldKastName'])
		equipment.light=light
		equipment.armatuur=armatuur
		equipment.brand=brand
		equipment.battery=battery
		equipment.emergencyUnitOfPrint=emergencyUnitOfPrint
		equipment.groupNo=groupnr
		equipment.kast=kast
		equipment.equipmentType=params['equipmentType']
		equipment.buildYearOfBattery=params['buildYearOfBattery']
		equipment.buildYearOfArmature=params['buildYearOfArmature']
		equipment.buildYearOfEmergencyUnit=params['buildYearOfEmergencyUnit']

		def report,equipmentCheckpoints,reportCheckpoints
		report=getLatestReport(user.username,equipment)
		if(report!=null)
			equipmentCheckpoints = EquipmentCheckpoint.findAllWhere(reportId:report.id.toString(),equipment:equipment)
		else
			equipmentCheckpoints = EquipmentCheckpoint.findAllWhere(equipment:equipment)
		reportCheckpoints = CheckpointsEquipment.findAllWhere(customerId:user.customer.id.toString())
		def equipmentStatuses = new ArrayList()
		int i=0
		if(equipmentCheckpoints.size()>0){
			for(def checkpoint in equipmentCheckpoints){
				if(params['equipment'+equipment.id.toString()+'checkpoint'+checkpoint.id.toString()]!='null' && params['equipment'+equipment.id.toString()+'checkpoint'+checkpoint.id.toString()]!=null){
					checkpoint.status=params['equipment'+equipment.id.toString()+'checkpoint'+checkpoint.id.toString()]
					equipmentStatuses[i]=params['equipment'+equipment.id.toString()+'checkpoint'+checkpoint.id.toString()]
					i++
				}
			}
		}
		else{
			for(def checkpoint in reportCheckpoints){
				if(params['equipment'+equipment.id.toString()+'checkpoint'+checkpoint.id.toString()]!='null' && params['equipment'+equipment.id.toString()+'checkpoint'+checkpoint.id.toString()]!=null){
					def equipmentCheckpoint=new EquipmentCheckpoint(checkpointDescription:checkpoint.value,status:params['equipment'+equipment.id.toString()+'checkpoint'+checkpoint.id.toString()],equipment:equipment)
					equipmentCheckpoint.save()
					equipmentStatuses[i]=params['equipment'+equipment.id.toString()+'checkpoint'+checkpoint.id.toString()]
					i++
				}
			}
		}
		//def newStatus = getEquipmentStatus(equipmentStatuses)
		//equipment.status=newStatus
		redirect controller:'equipment', action:'equipmentList'
	}

	def equipmentSearch={
		def returnObject = [:]
		def user=springSecurityService.currentUser
		def equipmentsList
		session.setAttribute("actionPerformed","search")
		session.setAttribute("searchQuery",params['query'])
		//TODO: Ideally this should be done using GORM , need to fix
		equipmentsList=Equipment.executeQuery("select e.name,p.projectName,b.buildingName,f.floorNumber,e.status,e.lastUpdated,e.dateCreated,e.isDeleted,e.id,e.deadLine from Equipment e, Project p, Building b, Floor f where ( p.projectName like '%"+params['query']+"%' Or e.name like '%"+params['query']+"%' Or b.buildingName like '%"+params['query']+"%' Or f.floorNumber like '%"+params['query']+"%' Or e.lastUpdated like '%"+params['query']+"%' Or e.status like '%"+params['query']+"%') AND e.floor.id = f.id AND f.building.id = b.id AND b.project.id = p.id AND  e.customer=:customer",[customer:user.customer.id.toInteger(),])
		returnObject.newList=equipmentsList
		render returnObject as JSON
	}


	def createCheckpoints = {
		def user=springSecurityService.currentUser           
		def checkpoints = CheckpointsEquipment.findAllWhere(customerId:user.customer.id.toString())
		[userId:user.id,customerName:user.customer.name,loggedInUser:springSecurityService.currentUser,role:UserRole.findWhere(user:user).role.authority,checkpoints:checkpoints]
	}

	def saveEquipmentCheckpoints = {
		def user=springSecurityService.currentUser
		def checkpoints = CheckpointsEquipment.findAllWhere(customerId:user.customer.id.toString())
		for(def checkpoint in checkpoints){
			if(params['checkpoint'+checkpoint.id.toString()]!=null){
				EquipmentCheckpoint.executeUpdate("update EquipmentCheckpoint set checkpointDescription='"+params['checkpoint'+checkpoint.id.toString()]+"'  where checkpointDescription='"+checkpoint.value+"' ")
				checkpoint.value=params['checkpoint'+checkpoint.id.toString()]
				checkpoint.sno=params['checkpointSno'+checkpoint.id.toString()]
				checkpoint.category=params['checkpointType'+checkpoint.id.toString()]
			}
		}

		int elements = params['totalElements'].toString().toLong()
		for(int i=1;i<=elements;i++){          
			if(params['checkpointNew'+i]!=null && params['checkpointNew'+i]!='' ){
				def equipmentCheckpoint = new CheckpointsEquipment(value:params['checkpointNew'+i],customerId:user.customer.id,category:params['checkpointTypeNew'+i],sno:params['checkpointSnoNew'+i]).save()
			}
		}
		redirect controller:'site'
	}

	def sortEquipmentList={
		def returnObject = [:]
		def user=springSecurityService.currentUser
		def equipmentsList
		def equipmentListNames = []
		def newEquipmentsList = new ArrayList()
		if(session.getAttribute("actionPerformed")==''){
			equipmentsList=Equipment.findAllByCustomerAndIsDeleted(user.customer.id,false,[sort:params.name,order:params.order])
			log.debug "domain equipment list is==="+equipmentsList
			for(equip in equipmentsList){
				equipmentListNames.add(equip.name)
			}
			log.debug "equipments name list is===="+equipmentListNames
			Collections.sort(equipmentListNames,new StringArraySort<String>())
			equipmentsList.clear()
			equipmentsList = sortEquipmentsList(equipmentListNames,user.customer.id)
			log.debug "now equipments list is==="+equipmentsList
		}
		else if(session.getAttribute("actionPerformed")=='search'){
			def orderField='e.'+params.name
			equipmentsList=Document.executeQuery("select e.name,p.projectName,b.buildingName,f.floorNumber,e.status,e.lastUpdated,e.dateCreated,e.isDeleted,e.id,e.deadLine from Equipment e, Project p, Building b, Floor f where ( p.projectName like '%"+params['query']+"%' Or e.name like '%"+params['query']+"%' Or b.buildingName like '%"+params['query']+"%' Or f.floorNumber like '%"+params['query']+"%' Or e.lastUpdated like '%"+params['query']+"%' Or e.status like '%"+params['query']+"%') AND e.floor.id = f.id AND f.building.id = b.id AND b.project.id = p.id AND  e.customer=:customer ORDER BY "+orderField+" "+params.order+" ",[customer:user.customer.id.toInteger(),])
		}
		else if(session.getAttribute("actionPerformed")=='filter'){
			def orderField='it.id'
			def (getCurrentList,newList,update,nextObject)=filterObjects(session.getAttribute("objectType"),session.getAttribute("objectValue"))
			if(session.getAttribute("objectType")=="")
				equipmentsList=Equipment.findAllByCustomerAndIsDeleted(user.customer.id,false,[sort:params.name,order:params.order])
			else{
				if(nextObject=='floor'||nextObject=='building'){
				log.debug "get current list is==="+getCurrentList
				log.debug "newlist is==="+newList
				log.debug "update list is==="+update
				log.debug "nextobject list is==="+nextObject
				equipmentsList = getAllEquipmentsListNew(getCurrentList,nextObject,newList)
				log.debug "domain equipment list is==="+equipmentsList
				for(equip in equipmentsList){
					equipmentListNames.add(equip.name)
				}
				log.debug "equipments name list is===="+equipmentListNames
				Collections.sort(equipmentListNames,new StringArraySort<String>())
				equipmentsList.clear()
				equipmentsList = sortEquipmentsList(equipmentListNames,user.customer.id)
				log.debug "now equipments list is==="+equipmentsList
			}
				else{
					log.debug "get current list is==="+getCurrentList
					log.debug "newlist is==="+newList
					log.debug "update list is==="+update
					log.debug "nextobject list is==="+nextObject
				 equipmentsList = getAllEquipmentsListNew(getCurrentList,nextObject,newList)
				log.debug "domain equipment list is==="+equipmentsList
				for(equip in equipmentsList){
					equipmentListNames.add(equip.name)
				}
				log.debug "equipments name list is===="+equipmentListNames
				Collections.sort(equipmentListNames,new StringArraySort<String>())
				equipmentsList.clear()
				equipmentsList = sortEquipmentsListEquipment(equipmentListNames,user.customer.id)
				log.debug "now equipments list is==="+equipmentsList
				}
			}
			/*if(equipmentsList!=null)
				equipmentsList = sortList(equipmentsList,params.name,params.order)
*/		}
		if(session.getAttribute("actionPerformed")=='' || session.getAttribute("actionPerformed")=='filter')
			render template:'equipments',model:[equipmentsList:equipmentsList,actionToSend:'sortDocumentList']
		else if(session.getAttribute("actionPerformed")=='search'){
			returnObject.newList=equipmentsList
			render returnObject as JSON
		}
	}

	def sortList(def documentsList,def sortField,def order) {
		def docList
		switch(sortField){
			case 'name':
				if(order=='asc')
					docList = documentsList.sort({it.name})
				else
					docList = documentsList.sort({it.name}).reverse()
				break
			case 'floor.building.project.projectName':
				if(order=='asc')
					docList = documentsList.sort({it.floor.building.project.projectName})
				else
				docList = documentsList.sort({it.floor.building.project.projectName}).reverse()
				break
			case 'floor.building.buildingName':
				if(order=='asc')
					docList = documentsList.sort({it.floor.building.buildingName})
				else
					docList = documentsList.sort({it.floor.building.buildingName}).reverse()
				break
			case 'floor.floorNumber':
				if(order=='asc')
					docList = documentsList.sort({it.floor.floorNumber})
				else
					docList = documentsList.sort({it.floor.floorNumber}).reverse()
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

	def populateSelect={
		def returnObject = [:]        
		def (getCurrentList,newList,update,nextObject)=filterObjects(params.type,params.value)
		def equipmentsList=new ArrayList()
		def myProject = new HashMap(),myBuilding  = new HashMap(),myFloor  = new HashMap()

		equipmentsList = getAllEquipmentsList(getCurrentList,nextObject)

		for(equipment in equipmentsList){
			myProject.put(equipment.id, equipment.floor.building.project.projectName)
			myBuilding.put(equipment.id, equipment.floor.building.buildingName)
			myFloor.put(equipment.id, equipment.floor.floorNumber)
		}
		session.setAttribute("actionPerformed","filter")
		returnObject.put('list', newList)
		returnObject.put('update', update)
		returnObject.put('next', nextObject)

		returnObject.put('current', params.type)
		returnObject.put('newList', equipmentsList)
		returnObject.put('projectList', myProject)
		returnObject.put('buildingList', myBuilding)
		returnObject.put('floorList', myFloor)
		render returnObject as JSON
	}

	def getLatestReport(user,equipment) {
		def reportId
		def latestCheckpoint = EquipmentCheckpoint.findAllByEquipment(equipment)
		log.debug "latest checkpoints are==="+latestCheckpoint
		Collections.reverse(latestCheckpoint)
		//
		println "checkpoints is==="+latestCheckpoint
		def latestReport=null
		if(latestCheckpoint.size()!=0){
			log.debug "latest checkpoint size not zero"
		reportId=latestCheckpoint.getAt(0).reportId
		log.debug "report is is====="+reportId
		}
		if(reportId!=null){
			log.debug "under reportId tag null"
		return reportId
		}
		else{
			return null
		}
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
				update=''
				nextObject='equipment'
				break

		}
		return [currentList,getNewList,update,nextObject]
	}

	def getAllEquipmentsList(def currentSelectedObject, def nextObject){
		def equipmentsNewList=new ArrayList()
		if(nextObject=='building'){
			for(def building in currentSelectedObject.buildings){
				def floorsList = Floor.findAllWhere(building:building,isDeleted:false)
				log.debug "floorList in allequipments list===="+floorsList
				for(def floor in floorsList){
					if(floor.equipments!=null)
					{
						def equipmentList = Equipment.findAllWhere(floor:floor,isDeleted:false)
						log.debug "equipmentList is====="+equipmentList
						log.debug "equipments for this floor are====="+floor.equipments
						for(equip in equipmentList){
					equipmentsNewList.addAll(equip)
						}
					}
				}
			}
		}
		else if(nextObject=='floor'){
			for(def floor in currentSelectedObject.floors){
				if(floor.equipments!=null)
				equipmentsNewList.addAll(floor.equipments)
			}
		}
		else if(nextObject=='equipment'){
			if(currentSelectedObject.equipments!=null)
				equipmentsNewList.addAll(currentSelectedObject.equipments)
		}
		log.debug "equipments new list being returned is===="+equipmentsNewList
		return equipmentsNewList
	}
	def getComponentsForEquipments(def brandName,def batteryType,def armatuurType,def name,def unitName,def groupNumber,def kastName){
		def light,armatuur,battery,brand,emergencyUnitOfPrint,groupnr,kast
		brand=Brand.findWhere(brandName:brandName)
		if(brand==null && params['brandName']!='')
			brand=new Brand(brandName:brandName).save()
			log.debug "brandName is======="+brandName
			log.debug "params[brandname is=====]"+params['brandName']
		if(!brandName.equals(params['brandName']) && params['brandName']!=''){
			def existingBrand = Brand.findWhere(brandName:params['brandName'])
			if(existingBrand!=null){
				log.debug "in component method before delete==="
			existingBrand.delete()
			}
			brand.brandName=params['brandName']
		}

		battery=Battery.findWhere(batteryType:batteryType)
		if(battery==null && params['batteryName']!='')
			battery=new Battery(batteryType:batteryType).save()
		if(!batteryType.equals(params['batteryName']) && params['batteryName']!=''){
			def existingBattery = Battery.findWhere(batteryType:params['batteryName'])
			if(existingBattery!=null)
			{
				log.debug "before deleting brand"
			existingBattery.delete()
			}
			battery.batteryType=params['batteryName']
		}

		armatuur=Armatuur.findWhere(armatuurType:armatuurType)
		if(armatuur==null && params['armatureName']!='')
			armatuur=new Armatuur(armatuurType:armatuurType).save()
		if(!armatuurType.equals(params['armatureName']) && params['armatureName']!=''){
			def existingArmatur = Armatuur.findWhere(armatuurType:params['armatureName'])
			if(existingArmatur!=null)
			{
				log.debug "before deleting armatuur"
			existingArmatur.delete()
			}
			armatuur.armatuurType=params['armatureName']
		}

		light=Light.findWhere(name:name)
		if(light==null && params['lightName']!='')
			light=new Light(name:name).save()
		if(!name.equals(params['lightName']) && params['lightName']!=''){
			def existingLight = Light.findWhere(name:params['lightName'])
			if(existingLight!=null)
			{
				log.debug "before deleting light"
			existingLight.delete()
			}
			light.name=params['lightName']
		}

		emergencyUnitOfPrint=EmergencyUnitOfPrint.findWhere(unitName:unitName)
		if(emergencyUnitOfPrint==null && params['emergencyUnitOfPrintName']!='')
			emergencyUnitOfPrint=new EmergencyUnitOfPrint(unitName:unitName).save()
		if(!unitName.equals(params['emergencyUnitOfPrintName']) && params['emergencyUnitOfPrintName']!=''){
			def existingUnit = EmergencyUnitOfPrint.findWhere(unitName:params['emergencyUnitOfPrintName'])
			if(existingUnit!=null)
			{
			log.debug "before deleting emergency unit"
			existingUnit.delete()
		}
			emergencyUnitOfPrint.unitName=params['emergencyUnitOfPrintName']
		}

		groupnr=GroupNr.findWhere(groupNumber:groupNumber)
		if(groupnr==null && params['groupName']!='')
			groupnr=new GroupNr(groupNumber:groupNumber).save()
		if(!groupNumber.equals(params['groupName']) && params['groupName']!=''){
			def existingGroup = GroupNr.findWhere(groupNumber:params['groupName'])
			if(existingGroup!=null)
			{
				log.debug "before deleting group number"
			existingGroup.delete()
			}
			groupnr.groupNumber=params['groupName']
		}

		kast=Kast.findWhere(kastName:kastName)
		if(kast==null && params['kastName']!='')
			kast=new Kast(kastName:kastName).save()
		if(!kastName.equals(params['kastName']) && params['kastName']!=''){
			def existingKast = Kast.findWhere(kastName:params['kastName'])
			if(existingKast!=null)
			{
				log.debug "before deleting kast"
			existingKast.delete()
			}
			kast.kastName=params['kastName']
		}

		return [light,armatuur,battery,brand,emergencyUnitOfPrint,groupnr,kast]
	}
	def getEquipmentStatus(def checkpointsList) {
		def overAllStatus
		if(checkpointsList.contains('Afgekeurd'))
			overAllStatus='Afgekeurd'
		else if(checkpointsList.contains('Goedgekeurd'))
			overAllStatus='Goedgekeurd'
		else if(checkpointsList.contains('Akkoord na herkeuring'))
			overAllStatus='Akkoord na herkeuring'
		else if(checkpointsList.contains('NVT'))
			overAllStatus='NVT'

		return overAllStatus
	}

	def checking = {
		byte[] b
		b = pdfService.buildPdf("http://localhost:8080/AppUBuild/pdf/documentPdf")
		OutputStream out = new FileOutputStream("C:\\Users\\Oodles\\Desktop\\kamTest\\testPdf.pdf");
		out.write(b);
		out.close();

	}
	def sortEquipmentsList(equipmentListNames,def customerId){
		String matchName = ""
		Map mp = new LinkedHashMap();
		def sortedList = []
		log.debug "equipment name list received is==="+equipmentListNames
		for(int i=0;i<equipmentListNames.size();i++){
			println "run=="+i
			def equipments = Equipment.findAllByCustomerAndNameAndIsDeleted(customerId,equipmentListNames[i],false)
			log.debug "equipments found is==="+equipments
			if(equipments.size() > 0){
				for(int j=0;j<equipments.size();j++){
			mp.put(equipments[j], equipmentListNames[i])
			equipments.remove(equipmentListNames[j])
			
				}
			}/*else{
			mp.put(equipments[0], equipmentListNames[i])
			}*/
		}
		for (Map.Entry<Key, Value> entry : mp.entrySet()) {
			sortedList.add(entry.getKey());
		}
		log.debug "now sorted list is===="+sortedList
		return sortedList
	}
	
	def getAllEquipmentsListNew(def currentSelectedObject, def nextObject,def newList){
		def equipmentsNewList=new ArrayList()
		if(nextObject=='building'){
			for(def building in currentSelectedObject.buildings){
				def floorsList = Floor.findAllWhere(building:building,isDeleted:false)
				log.debug "floorList in allequipments list===="+floorsList
				for(def floor in floorsList){
					if(floor.equipments!=null)
					{
						def equipmentList = Equipment.findAllWhere(floor:floor,isDeleted:false)
						log.debug "equipmentList is====="+equipmentList
						log.debug "equipments for this floor are====="+floor.equipments
						for(equip in equipmentList){
					equipmentsNewList.addAll(equip)
						}
					}
				}
			}
		}
		else if(nextObject=='floor'){
			for(def floor in newList){
				
				if(floor.equipments!=null)
				{
					def equipmentList = Equipment.findAllWhere(floor:floor,isDeleted:false)
					log.debug "equipmentList is====="+equipmentList
					log.debug "equipments for this floor are====="+floor.equipments
					for(equip in equipmentList){
				equipmentsNewList.addAll(equip)
				}
				}
			}
		}
		else if(nextObject=='equipment'){
			if(currentSelectedObject.equipments!=null)
			{
				def equipmentList = Equipment.findAllWhere(floor:currentSelectedObject,isDeleted:false)
				log.debug "equipmentList is====="+equipmentList
					for(equip in equipmentList){
				equipmentsNewList.addAll(equip)
				}
			}
		}
		log.debug "equipments new list being returned is===="+equipmentsNewList
		return equipmentsNewList
	}
	
	def sortEquipmentsListEquipment(equipmentListNames,def customerId){
		def sortedList = []
		log.debug "equipment name list received is==="+equipmentListNames
		for(int i=0;i<equipmentListNames.size();i++){
			println "run=="+i
			def equipments = Equipment.findByCustomerAndNameAndIsDeleted(customerId,equipmentListNames[i],false)
			log.debug "equipments found is==="+equipments
			sortedList.add(equipments)
				}
		return sortedList
	}
	
}

