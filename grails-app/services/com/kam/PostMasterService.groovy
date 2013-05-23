package com.kam

import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource
import org.json.simple.JSONArray
import org.json.simple.JSONObject
//import org.json.JSONException


class PostMasterService {
	def springSecurityService
	def saltSource
	JSONArray jsonArray = new JSONArray()
	JSONArray jsonArrayArmatuur = new JSONArray()
	JSONArray jsonArrayNoodUnit = new JSONArray()
	JSONArray jsonArrayLamp = new JSONArray()
	JSONArray jsonArrayAccu = new JSONArray()
	JSONArray jsonArrayGroup = new JSONArray()
	JSONArray jsonArrayKast = new JSONArray()
	JSONArray jsonArrayCheckpoint = new JSONArray()
	JSONObject jsonObject
    def postMaster(def object,def requiredXml) {
		def reader = new StringReader(requiredXml)
		def xmlRead = new XmlParser().parse(reader)
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		def xmlContent =  new ArrayList()
		def ipadBrandList = new ArrayList()
		log.debug "xml in postmaster Service is"+object
		def requestType = object.requesttype
		log.debug "request type in postmaster Service is=="+requestType
		def test = object.loginvalidation.password
		log.debug "password in postmaster service service is==" +test
		def paramLoginName = object.loginvalidation.loginname
		log.debug "username in postmaster service is==" + paramLoginName
		String salt = saltSource instanceof NullSaltSource ? object.loginvalidation.loginname:null
		log.debug "salt in postmaster service is"+ salt
		String encodedPassword = springSecurityService.encodePassword(object.loginvalidation.password.toString())
		log.debug encodedPassword
		def dbUser = User.findByUsernameAndPassword(salt,encodedPassword)
		log.debug "DB user in postMaster is"+dbUser
		if(dbUser){
			if(dbUser.enabled==true){
				def customer = Customer.findById(User.get(dbUser.id).customer.id)
				log.debug "dbCustomer in kam service is =="+customer
				brandTable(xmlRead)
				armatureTable(xmlRead)
				noodUnitTable(xmlRead)
				lampTable(xmlRead)
				accuTable(xmlRead)
				groupTable(xmlRead)
				kastTable(xmlRead)
				checkpointTable(xmlRead,customer.id.toString())			
					xml.'?xml version="1.0" encoding="UTF-8"?'{
						digikam{
						requesttype(requestType)
						loginvalidation{
							loginname(paramLoginName)
							password(test)
							response("OK")
							message("Login Validated")
						}
						tables{
						brandTableResponse(xml,jsonArray)
						armatuurTableResponse(xml,jsonArrayArmatuur)
						noodUnitResponse(xml,jsonArrayNoodUnit)
						lampTableResponse(xml,jsonArrayLamp)
						accuTableResponse(xml,jsonArrayAccu)
						groupTableResponse(xml,jsonArrayGroup)
						kastTableResponse(xml,jsonArrayKast)
						checkpointTableResponse(xml,jsonArrayCheckpoint)
					}
					}
				}
				log.debug "postmaster respinse is==="+writer.toString()
				return writer.toString()
			}
			if(dbUser.enabled==false){
				xml.'?xml version="1.0" encoding="UTF-8"?'{
					digikam{
					requesttype(requestType)
					loginvalidation{
						loginname(paramLoginName)
						password(test)
						response("DENIED")
						message("User not allowed to use iPad App")
					}
				}
				}
				return writer.toString()
			}
		}
		else{
			xml.'?xml version="1.0" encoding="UTF-8"?'{
				digikam{
				requesttype(requestType)
				loginvalidation{
					loginname(paramLoginName)
					password(test)
					response("ERROR")
					message("Wrong Username/Password")
				}
			}
			}
			return writer.toString()
		}
    }
	def brandTable(def xmlRead){
		xmlRead.tables.brandtable.add.brands.brand.each{brand->
			def ipadBrand = new Brand(brandName:brand.value.text())
			ipadBrand.save()
			jsonObject = new JSONObject()
			if(ipadBrand.save()){
				jsonObject.put("id", ipadBrand.id)
				jsonObject.put("value", brand.value.text())
				jsonObject.put("oldid", brand.id.text())
			}
			jsonArray.add(jsonObject);
		}
		
	}
	def armatureTable(def xmlRead){
		xmlRead.tables.armatuurtable.add.armatuurs.armatuur.each{armatuur->
			log.debug "armatuur id is"+armatuur.id.text()
			log.debug "armatuur value is"+armatuur.value.text()
			def ipadArmatuur = new Armatuur(armatuurType:armatuur.value.text())
			ipadArmatuur.save()
			jsonObject = new JSONObject()
			if(ipadArmatuur.save()){
				jsonObject.put("id", ipadArmatuur.id)
				jsonObject.put("value",armatuur.value.text())
				jsonObject.put("oldid", armatuur.id.text())
				
			}
			jsonArrayArmatuur.add(jsonObject);
		}
	}
	def noodUnitTable(def xmlRead){
		xmlRead.tables.noodunitofprinttable.add.noodunitofprints.noodunitofprint.each{noodunitofprint->
			log.debug "ipadEmergencyUnitofPrint id is"+noodunitofprint.id.text()
			log.debug "ipadEmergencyUnitofPrint value is"+noodunitofprint.value.text()
			def ipadEmergencyUnitofPrint = new EmergencyUnitOfPrint(unitName:noodunitofprint.value.text())
			ipadEmergencyUnitofPrint.save()
			jsonObject = new JSONObject()
			if(ipadEmergencyUnitofPrint.save()){
				jsonObject.put("id", ipadEmergencyUnitofPrint.id)
				jsonObject.put("value",noodunitofprint.value.text())
				jsonObject.put("oldid", noodunitofprint.id.text())
				
			}
			jsonArrayNoodUnit.add(jsonObject);
		}
	}
	def lampTable(def xmlRead){
		xmlRead.tables.lamptable.add.lamps.lamp.each{lamp->
			log.debug "ipadLamp id is"+lamp.id.text()
			log.debug "ipadLamp value is"+lamp.value.text()
			def ipadLamp = new Light(name:lamp.value.text())
			ipadLamp.save()
			jsonObject = new JSONObject()
			if(ipadLamp.save()){
				jsonObject.put("id", ipadLamp.id)
				jsonObject.put("value",lamp.value.text())
				jsonObject.put("oldid", lamp.id.text())
				
			}
			jsonArrayLamp.add(jsonObject);
		}
	}
	def accuTable(def xmlRead){
		xmlRead.tables.accutable.add.accus.accu.each{accu->
			log.debug "ipadLamp id is"+accu.id.text()
			log.debug "ipadLamp value is"+accu.value.text()
			def ipadAccu = new Battery(batteryType:accu.value.text())
			ipadAccu.save()
			jsonObject = new JSONObject()
			if(ipadAccu.save()){
				jsonObject.put("id", ipadAccu.id)
				jsonObject.put("value",accu.value.text())
				jsonObject.put("oldid", accu.id.text())
				
			}
			jsonArrayAccu.add(jsonObject);
		}
	}
	def groupTable(def xmlRead){
		xmlRead.tables.grouptable.add.groups.group.each{group->
			log.debug "ipad group id is=="+group.id.text()
			def ipadGroup = new GroupNr(groupNumber:group.value.text())
			ipadGroup.save()
			jsonObject = new JSONObject()
			if(ipadGroup.save()){
				jsonObject.put("id",ipadGroup.id)
				jsonObject.put("value",group.value.text())
				jsonObject.put("oldid",group.id.text())
			}
			jsonArrayGroup.add(jsonObject);
		}
	}
	def kastTable(def xmlRead){
		xmlRead.tables.kasttable.add.kasts.kast.each{kast->
			log.debug "ipad kast id is=="+kast.id.text()
			def ipadKast = new Kast(kastName:kast.value.text())
			ipadKast.save()
			jsonObject = new JSONObject()
			if(ipadKast.save()){
				jsonObject.put("id",ipadKast.id)
				jsonObject.put("value",kast.value.text())
				jsonObject.put("oldid",kast.id.text())
			}
			jsonArrayKast.add(jsonObject);
		}
	}
	def checkpointTable(def xmlRead,def customerId){
		xmlRead.tables.checkpointtable.add.checkpoints.checkpoint.each{checkpoint->
			log.debug "ipad checkpoint id is=="+checkpoint.id.text()
			log.debug "ipad checkpint description is==="+checkpoint.description.text()
			def checkpointEquipment = new CheckpointsEquipment(value:checkpoint.description.text(),customerId:customerId)
			checkpointEquipment.save()
			if(checkpointEquipment.hasErrors()){
				checkpointEquipment.errors.each {
					println "checpoint saving error is=="+it
					}
			}
			jsonObject = new JSONObject()
			if(checkpointEquipment.save())
			{
				jsonObject.put("id", checkpointEquipment.id)
				jsonObject.put("value",checkpoint.description.text())
				jsonObject.put("oldid",checkpoint.id.text())
			}
			jsonArrayCheckpoint.add(jsonObject)
		}
	}
	def brandTableResponse(xml,jsonArray){
		xml.brandtable{
			add{
				brands{
					for (int i = 0; i <jsonArray.size(); i++){
						brand{
							id(jsonArray[i].get("id"))
							oldid(jsonArray[i].get("oldid"))
							value(jsonArray[i].get("value"))
						}
					}
				}
			}
		}
	}
	def armatuurTableResponse(xml,jsonArrayArmatuur){
		xml.armatuurtable{
			add{
				armatuurs{
					for(int i=0;i<jsonArrayArmatuur.size();i++){
						armatuur{
							id(jsonArrayArmatuur[i].get("id"))
							oldid(jsonArrayArmatuur[i].get("oldid"))
							value(jsonArrayArmatuur[i].get("value"))
						}
					}
				}
			}
		}
	}
	def noodUnitResponse(xml,jsonArrayNoodUnit){
		xml.noodunitofprinttable{
			add{
				noodunitofprints{
					for(int i=0;i<jsonArrayNoodUnit.size();i++){
						noodunitofprint{
							id(jsonArrayNoodUnit[i].get("id"))
							oldid(jsonArrayNoodUnit[i].get("oldid"))
							value(jsonArrayNoodUnit[i].get("value"))
						}
					}
				}
			}
		}
	}
	def lampTableResponse(xml,jsonArrayLamp){
		xml.lamptable{
			add{
				lamps{
					for(int i=0;i<jsonArrayLamp.size();i++){
						lamp{
							id(jsonArrayLamp[i].get("id"))
							oldid(jsonArrayLamp[i].get("oldid"))
							value(jsonArrayLamp[i].get("value"))
						}
					}
					
				}
			}
		}
	}
	def accuTableResponse(xml,jsonArrayAccu){
		xml.accutable{
			add{
				accus{
					for(int i=0;i<jsonArrayAccu.size();i++){
						accu{
							id(jsonArrayAccu[i].get("id"))
							oldid(jsonArrayAccu[i].get("oldid"))
							value(jsonArrayAccu[i].get("value"))
						}
					}
				}
			}
		}
	}
	def groupTableResponse(xml,jsonArrayGroup){
		xml.grouptable{
			add{
				groups{
					for(int i=0;i<jsonArrayGroup.size();i++){
						group{
							id(jsonArrayGroup[i].get("id"))
							oldid(jsonArrayGroup[i].get("oldid"))
							value(jsonArrayGroup[i].get("value"))
						}
					}
				}
			}
		}
	}
	def kastTableResponse(xml,jsonArrayKast){
		xml.kasttable{
			add{
				kasts{
					for(int i=0;i<jsonArrayKast.size();i++){
						kast{
							id(jsonArrayKast[i].get("id"))
							oldid(jsonArrayKast[i].get("oldid"))
							value(jsonArrayKast[i].get("value"))
						}
					}
				}
			}
		}
	}
	def checkpointTableResponse(xml,jsonArrayCheckpoint){
		xml.checkpointtable{
			add{
				checkpoints{
					for(int i=0;i<jsonArrayCheckpoint.size();i++){
						checkpoint{
							id(jsonArrayCheckpoint[i].get("id"))
							oldid(jsonArrayCheckpoint[i].get("oldid"))
							value(jsonArrayCheckpoint[i].get("value"))
						}
					}
				}
			}
		}
	}
}
