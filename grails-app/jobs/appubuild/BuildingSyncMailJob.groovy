package appubuild



class BuildingSyncMailJob {
	def postBuildingMailService
    static triggers = {
      simple repeatInterval: 120000l // execute job once in 120 seconds
    }

    def execute() {
		log.debug "postBuilding mail service is===="+postBuildingMailService
		postBuildingMailService.sendMailWithSchedular()
        // execute job
    }
}
