package appubuild



class EquipmentStatusMailJob {
	def postBuildingMailService
	def concurrent = false
    static triggers = {
      simple repeatInterval: 864000000l // execute job once in 120 seconds
    }

    def execute() {
		log.debug "equipment mail service is===="+postBuildingMailService
		postBuildingMailService.sendEquipmentMailWithSchedular()
        // execute job
    }
}
