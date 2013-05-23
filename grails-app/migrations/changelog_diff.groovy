databaseChangeLog = {

	changeSet(author: "oodles (generated)", id: "1348841513433-1") {
		modifyDataType(columnName: "value", newDataType: "varchar(255)", tableName: "checkpoints_equipment")
	}

	changeSet(author: "oodles (generated)", id: "1348841513433-2") {
		modifyDataType(columnName: "value", newDataType: "varchar(255)", tableName: "report_value")
	}
}
