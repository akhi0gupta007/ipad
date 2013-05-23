package com.kam

import grails.converters.XML
import groovy.xml.MarkupBuilder

class ProjectDataService {

	def projectData(def requestType,def projectList,def loginnameReceived,def test){
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		log.debug "In projectDataService"
		log.debug "project List is=="+projectList
		xml.digikam{
			requesttype(requestType)
			loginvalidation{
				loginname(loginnameReceived)
				password(test)
				response("OK")
				message("Login Validated")
			}
			projects{
				for(projectitem in projectList)
					project{
						id(projectitem.projectName)
						buildings{
							for(buildingitem in projectitem.buildings)
								building{
									id(buildingitem.id)
									floors{
										for(flooritem in buildingitem.floors)
											floor{
												id(flooritem.floorNumber)
												rooms{
													for(roomitem in flooritem.rooms)
														room{ id(roomitem.roomId) }
												}
											}
									}
								}
						}
					}
			}
		}
		return writer.toString()
	}

	def projectAddStatus(def projectadded,def formattedLastsynctime){
		for(projectitem in projectadded){
			if(projectitem.dateCreated > formattedLastsynctime){
				if(projectitem.list().size!=0){
					return true
				}
			}
		}
	}

	def projectRemoveStatus(def projectremoved,def formattedLastsynctime){
		for(projectitem in projectremoved){
			if(projectitem.lastUpdated > formattedLastsynctime){
				if(projectitem.list().size!=0){
					return true
				}
			}
		}
	}
	def buildingAddStatus(def buildingadded,def formattedLastsynctime){
		for(buildingitem in buildingadded){
			if(buildingitem.dateCreated > formattedLastsynctime){
				if(buildingitem.list().size!=0){
					return true
				}
			}
		}
	}
	def buildingRemoveStatus(def buildingremoved,def formattedLastsynctime){
		for(buildingitem in buildingremoved){
			if(buildingitem.lastUpdated > formattedLastsynctime){
				if(buildingitem.list().size!=0){
					log.debug "building item removed is=="+buildingitem
					return true
				}
			}
		}
	}
}
