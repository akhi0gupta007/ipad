package appubuild

class DocumentReminderJob {
	
	def documentsReminderService
	def equipmentsReminderService
	
    static triggers = {
	  cron name: 'myTrigger', cronExpression: "0 0 0 * * ?"
    }

    def execute() {
        // execute job
		documentsReminderService.documentReminders()
		equipmentsReminderService.equipmentReminders()
    }
}
