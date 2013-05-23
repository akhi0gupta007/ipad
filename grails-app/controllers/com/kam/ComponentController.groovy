package com.kam

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import grails.converters.JSON;
import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
/**
* @author Shridhar
*
*/
@Secured(['ROLE_ADMIN','ROLE_USER','IS_AUTHENTICATED_REMEMBERED'])
class ComponentController {

	def springSecurityService
	def imageService
	
    def index() { }
	
	def saveProject={
		def returnObject = [:]
		def user=springSecurityService.currentUser
		def project,customer
		def startIndex=params['offset']
		if(params.oldProjectId==''){
			if(SpringSecurityUtils.ifAnyGranted("ROLE_SUPERUSER")){
				customer=Customer.get(params.systemCustomers)
			}
			else
			customer=user.customer
			project=new Project(projectNumber:params["projectNumber"],projectName:params['projectName'],projectTitle:params['projectTitle'],address:params['projectAddress'],city:params['projectCity'],customer:customer,docname:params['projectDocnaam'])
		}
		else
		{
			project=Project.get(params.oldProjectId.toLong())
			project.projectName=params.projectName
			project.projectNumber=params.projectNumber
			project.projectTitle=params.projectTitle
			project.address=params.projectAddress
			project.city=params.projectCity
			project.docname=params['projectDocnaam']
		}
		if(project.save())
			returnObject.success=true
		else
			returnObject.success=false
			returnObject.currentId=project
			returnObject.oldProjectId=params.oldProjectId
			render returnObject as JSON 
		
	}
	
	def saveBuilding={
		def returnObject = [:]
		def user=springSecurityService.currentUser
		def project=Project.get(params.parentProject)
		def building
		if(params.oldBuildingId=='')
			building=new Building(buildingName:params.buildingName,buildingNumber:params.buildingNumber,address:params.buildingAddress,city:params.buildingCity,zipCode:params.buildingZip,project:project,flow:user.customer.flow) 
		else{
			building=Building.get(params.oldBuildingId.toLong())
			building.buildingName=params.buildingName
			building.buildingNumber=params.buildingNumber
			building.address=params.buildingAddress
			building.city=params.buildingCity
			building.zipCode=params.buildingZip
			building.flow=user.customer.flow
		}
		if(building.save()){
			returnObject.success=true
			building.project.lastUpdated = new Date()
		}
		else
			returnObject.success=false
		
		returnObject.parentProject=params.parentProject
		returnObject.oldBuildingId=params.oldBuildingId
		returnObject.currentId=building
		returnObject.currentCustomer=user.customer
		
		render returnObject as JSON
	}
	
	def saveFloor={
		def returnObject = [:]
		def user=springSecurityService.currentUser
		def building=Building.get(params.parentBuilding)
		def floor  
		DateFormat getDate  
		Date revisionDate  
		getDate=new SimpleDateFormat('dd/MM/yyyy')  
		if(params['revisionDate']!='')  
		revisionDate=(Date)getDate.parse(params['revisionDate'])  
		if(params.oldFloorId=='')  
			floor=new Floor(floorNumber:params.floorNumber,floorDescription:params.floorDescription,floorMap:params.floorMap,building:building,revisionDate:revisionDate)
		else{        
			floor=Floor.get(params.oldFloorId.toLong())
			floor.floorNumber=params.floorNumber
			floor.floorDescription=params.floorDescription
			floor.floorMap=params.floorMap
			floor.revisionDate=revisionDate
		}
		if(floor.save())
		{
			imageService.changeImageLocation(params.floorMap,grailsApplication.config.upload.floorplan.image.path)
			returnObject.success=true
		}
		else{
			
	
		returnObject.success=false
		} 
		returnObject.parentBuilding=building
		returnObject.oldFloorId=params.oldFloorId
		returnObject.currentId=floor  
		
		render returnObject as JSON
	}
	
	def saveRoom={
		def returnObject = [:]
		def user=springSecurityService.currentUser
		def floor=Floor.get(params.parentFloor)
		def room
		if(params.oldRoomId=='')
			room=new Room(roomId:params.roomId,floor:floor)
		else{
			room=Room.get(params.oldRoomId.toLong())
			room.roomId=params.roomId
		}
		if(room.save())
			returnObject.success=true
		else
		returnObject.success=false
		returnObject.parentFloor=params.parentFloor
		returnObject.oldRoomId=params.oldRoomId 
		returnObject.currentId=room
		
		render returnObject as JSON
	}
	
	
	def saveEquipment={
		def returnObject = [:]
		def user=springSecurityService.currentUser
		def floor=Floor.get(params.parentEquipmentFloor)
		def equipment,buildYearOfArmature,buildYearOfBattery,buildYearOfEmergencyUnit
		def light,armatuur,battery,brand,emergencyUnitOfPrint,groupnr,kast
		DateFormat getDate
		getDate=new SimpleDateFormat('dd/MM/yyyy')
		if(params.oldEquipmentId==''){
			(light,armatuur,battery,brand,emergencyUnitOfPrint,groupnr,kast)=getComponentsForEquipments(params['oldBrandName'],params['oldBatteryName'],params['oldArmatureName'],params['oldLightName'],params['oldEmergencyUnitOfPrintName'],params['oldGroupName'],params['oldKastName'])
			if(params['buildYearOfBattery']==''){
			buildYearOfBattery = getDate.format(new Date())
			}else{
			buildYearOfBattery = getDate.format(convertToDate(params['buildYearOfBattery']))
			log.debug "converted date--------"+buildYearOfBattery
			}
			log.debug "after inserting build year of battery"
			if(params['buildYearOfArmature']==''){
			buildYearOfArmature =  getDate.format(new Date())
			[build:buildYearOfArmature]
			}else{
			buildYearOfArmature =  getDate.format(convertToDate(params['buildYearOfArmature']))
			}
			log.debug "after buildyear of armature"
			if(params['buildYearOfEmergencyUnit']==''){
			buildYearOfEmergencyUnit =  getDate.format(new Date())
			}
			else{
				buildYearOfEmergencyUnit = getDate.format(convertToDate(params['buildYearOfEmergencyUnit']))
			}
			log.debug "after buildyear of emergency unit"
			equipment=new Equipment(name:params.equipmentName,description:params.equipmentDescription,floor:floor,light:light,armatuur:armatuur,battery:battery,brand:brand,emergencyUnitOfPrint:emergencyUnitOfPrint,buildYearOfBattery:buildYearOfBattery,buildYearOfArmature:buildYearOfArmature,buildYearOfEmergencyUnit:buildYearOfEmergencyUnit,status:'Not Ready',customer:user.customer.id,equipmentType:params.equipmentType,createdBy:user.username,groupNo:groupnr,kast:kast,equipmentType2:params['equipmentType2'])
			if(equipment.hasErrors()){
				equipment.errors.each {
					println "equiment has errors=="+it
				}
			}
			log.debug "after creating equipment"
		}
		else{
			equipment=Equipment.get(params.oldEquipmentId.toLong())
			equipment.name=params.equipmentName
			equipment.description=params.equipmentDescription
			(light,armatuur,battery,brand,emergencyUnitOfPrint,groupnr,kast)=getComponentsForEquipments(params['oldBrandName'],params['oldBatteryName'],params['oldArmatureName'],params['oldLightName'],params['oldEmergencyUnitOfPrintName'],params['oldGroupName'],params['oldKastName'])
			equipment.light=light
			equipment.armatuur=armatuur
			equipment.brand=brand
			equipment.battery=battery
			equipment.emergencyUnitOfPrint=emergencyUnitOfPrint
			equipment.groupNo=groupnr
			equipment.kast=kast
			equipment.equipmentType=params['equipmentType']
			equipment.equipmentType2=params['equipmentType2']	
			equipment.buildYearOfBattery=params['buildYearOfBattery']
			equipment.buildYearOfArmature=params['buildYearOfArmature']
			equipment.buildYearOfEmergencyUnit=params['buildYearOfEmergencyUnit']
			log.debug "after editing equipment"
		}
		if(equipment.save()){
			returnObject.success=true
			equipment.floor.lastUpdated = new Date()
		}
		else
			returnObject.success=false
			log.debug "after else condition"
		returnObject.parentFloor=params.parentEquipmentFloor
		log.debug "after parent Floor"
		returnObject.oldEquipmentId=params.oldEquipmentId
		log.debug "after old equipment id"
		returnObject.currentId=equipment
		log.debug "after current id"+"user customer is==="+user.customer
		returnObject.currentCustomer=user.customer
		log.debug "after current user"+returnObject
		render returnObject as JSON
	}
	
	def projectList = { 
		def user=springSecurityService.currentUser
		def projectsList
		if(SpringSecurityUtils.ifAnyGranted('ROLE_SUPERUSER'))
			projectsList=Project.findAllByIsDeleted(false,[sort:'id',order:'desc'])
		else
			projectsList=Project.findAllByCustomerAndIsDeleted(user.customer,false,[sort:'id',order:'desc']) 
		
		[projectsList:projectsList,role:UserRole.findWhere(user:user).role.authority,loggedinUser:user,sno:0]
		
	}
	
	def editProject = {
		def returnObject = [:]
		def project=Project.get(params.id)
		returnObject.oldProject=project
		render returnObject as JSON
	}
	
	def editBuilding = {
		def returnObject = [:]
		def building=Building.get(params.id)
		returnObject.oldBuilding=building
		render returnObject as JSON
	}
	
	
	def editFloor={
		def returnObject = [:]
		def floor=Floor.get(params.id)
		def revisiondate=''
		if(floor.revisionDate!=null && floor.revisionDate!=''){
			DateFormat formatter=new SimpleDateFormat('yyyy-MM-dd') 
			revisiondate = formatter.format(floor.revisionDate)
		}
		
		returnObject.oldFloorRevisionDate = revisiondate
		returnObject.oldFloor=floor
		render returnObject as JSON
	}
	def editRoom={
		def returnObject = [:]
		def room=Room.get(params.id)
		returnObject.oldRoom=room
		render returnObject as JSON
	}
	
	def editEquipment={
		def returnObject = [:]
		log.debug "reached editEquipment action"
		def user=springSecurityService.currentUser
		def equipment=Equipment.get(params.id)
		returnObject.oldEquipment=equipment
		returnObject.equipmentFloor=equipment.floor
		log.debug "before editing light"
		returnObject.equipmentLight=equipment.light
		log.debug "after editing light"
		returnObject.equipmentBattery=equipment.battery
		returnObject.equipmentBrand=equipment.brand
		returnObject.equipmentArmatuur=equipment.armatuur
		returnObject.equipmentEmergencyUnitOfPrint=equipment.emergencyUnitOfPrint
		returnObject.groupNumber=equipment.groupNo
		returnObject.kast=equipment.kast
		equipment.lastUpdated = new Date()
		equipment.floor.lastUpdated = new Date()
		//log.debug "returning object "+returnObject as JSON
		render returnObject as JSON
	}
	
	
	def deleteComponent={
		def returnObject = [:]
		def type=params.type 
		def component
		switch(type){
			case 'Project':
			component=Project.get(params.id)
			for(def building in component.buildings){
				for(def floor in building.floors){
					for(def equipment in floor.equipments){
						equipment.isDeleted=true
					}
					floor.isDeleted=true
				}
				building.isDeleted=true
			}
			break
			case 'Building':
			component=Building.get(params.id)
			for(def floor in component.floors){
				for(def equipment in floor.equipments){
					equipment.isDeleted=true
				}
				floor.isDeleted=true
			}
			component.project.lastUpdated = new Date()
			
			break
			case 'Floor':
			component=Floor.get(params.id)
			for(def equipment in component.equipments){
				equipment.isDeleted=true
			}
			break
			case 'Room':
			component=Room.get(params.id)
			break
			case 'Equipment':
			component=Equipment.get(params.id)
			component.floor.lastUpdated = new Date()
			break
		}
		returnObject.deletedId=component
		returnObject.deletedType=type
		component.isDeleted=true
		if(component.save(flush:true)){
			returnObject.success=true
			if(type=='Project'){
				deleteProjectDocuments(component)
			}
			else if(type!='Equipment')
				component.documents.remove(component.documents)
		}
		else
			returnObject.success=false
			render returnObject as JSON
	}
	
	def getCustomerList={
		def returnObject = [:]
		def customers
		def listSize
		if(SpringSecurityUtils.ifAnyGranted("ROLE_SUPERUSER")){
			customers=Customer.findAllWhere(isDeleted:false)
			listSize=customers.size()
		}
		else if(SpringSecurityUtils.ifAnyGranted("ROLE_ADMIN") || SpringSecurityUtils.ifAnyGranted("ROLE_USER")){
			customers=springSecurityService.currentUser.customer
			listSize=1
		}
		returnObject.customersList=customers
		returnObject.listSize=listSize
		render returnObject as JSON  
	}
	
	
	def equipmentComponentList={
		def returnObject = [:]
		def brands=Brand.list()
		def batteries=Battery.list()
		def lights=Light.list()
		def armatuurs=Armatuur.list()
		def emergencyUnitsOfPrint = EmergencyUnitOfPrint.list()
		def groupNr = GroupNr.list()
		def kast = Kast.list()
		returnObject.batteries=batteries.batteryType
		returnObject.lights=lights.name
		returnObject.armatuurs=armatuurs.armatuurType
		returnObject.brands=brands.brandName 
		returnObject.emergencyUnitsOfPrint=emergencyUnitsOfPrint.unitName
		returnObject.groupNr = groupNr.groupNumber
		returnObject.kast = kast.kastName
		render returnObject as JSON
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
	
	def deleteProjectDocuments(def projectComponent){
		def documents=projectComponent.documents
		for(def document in documents){
			for (def building in component.buildings){
				for (def floor in building.floors){
					for (def room in floor.rooms){
						room.documents.remove(document)
					}
					floor.documents.remove(document)
				}
				building.documents.remove(document)
			}
			component.removeFromDocuments(document)
			document.delete(flush:true)
		}
	}
	def convertToDate(def stringDate) {
		log.debug "string date received is======"+stringDate
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy")
		def date = dateFormat.parse(stringDate)
		return date
	}
}