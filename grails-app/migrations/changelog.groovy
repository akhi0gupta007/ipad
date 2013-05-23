databaseChangeLog = {

	changeSet(author: "Oodles (generated)", id: "1345631638703-1") {
		createTable(tableName: "action_user") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "action_group_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "parent_id", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "type", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-2") {
		createTable(tableName: "armatuur") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "armatuur_type", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "deleted", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-3") {
		createTable(tableName: "async_mail_attachment") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "attachment_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "content", type: "LONGBLOB") {
				constraints(nullable: "false")
			}

			column(name: "inline", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "message_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "mime_type", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "attachments_idx", type: "INT")
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-4") {
		createTable(tableName: "async_mail_bcc") {
			column(name: "message_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "bcc_string", type: "LONGTEXT")

			column(name: "bcc_idx", type: "INT")
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-5") {
		createTable(tableName: "async_mail_cc") {
			column(name: "message_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "cc_string", type: "LONGTEXT")

			column(name: "cc_idx", type: "INT")
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-6") {
		createTable(tableName: "async_mail_header") {
			column(name: "message_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "header_name", type: "VARCHAR(255)")

			column(name: "header_value", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-7") {
		createTable(tableName: "async_mail_mess") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "attempt_interval", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "attempts_count", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "begin_date", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "create_date", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "end_date", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "from_column", type: "LONGTEXT")

			column(name: "html", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "last_attempt_date", type: "DATETIME")

			column(name: "mark_delete", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "max_attempts_count", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "priority", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "reply_to", type: "LONGTEXT")

			column(name: "sent_date", type: "DATETIME")

			column(name: "status", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "subject", type: "LONGTEXT") {
				constraints(nullable: "false")
			}

			column(name: "text", type: "LONGTEXT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-8") {
		createTable(tableName: "async_mail_to") {
			column(name: "message_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "to_string", type: "LONGTEXT")

			column(name: "to_idx", type: "INT")
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-9") {
		createTable(tableName: "battery") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "battery_type", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "deleted", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-10") {
		createTable(tableName: "brand") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "brand_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "deleted", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-11") {
		createTable(tableName: "building") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "address", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "building_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "building_number", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "city", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "flow", type: "VARCHAR(255)")

			column(name: "is_deleted", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "project_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "zip_code", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-12") {
		createTable(tableName: "building_document") {
			column(name: "building_documents_id", type: "BIGINT")

			column(name: "document_id", type: "BIGINT")
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-13") {
		createTable(tableName: "checkpoints_equipment") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "category", type: "VARCHAR(255)")

			column(name: "customer_id", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "deleted", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "sno", type: "VARCHAR(255)")

			column(name: "value", type: "VARCHAR(6000)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-14") {
		createTable(tableName: "customer") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "activatedby", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "address", type: "VARCHAR(255)")

			column(name: "city", type: "VARCHAR(255)")

			column(name: "contact", type: "VARCHAR(255)")

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "enabled", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "flow", type: "VARCHAR(255)")

			column(name: "is_deleted", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "website", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-15") {
		createTable(tableName: "customize_settings") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "back_ground", type: "VARCHAR(255)")

			column(name: "customer_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "links", type: "VARCHAR(255)")

			column(name: "logo", type: "VARCHAR(255)")

			column(name: "navigation", type: "VARCHAR(255)")

			column(name: "theme", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-16") {
		createTable(tableName: "disciplines") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "discipline_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-17") {
		createTable(tableName: "document") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "customer_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "dead_line", type: "DATETIME")

			column(name: "discipline", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "document_number", type: "VARCHAR(255)")

			column(name: "document_template_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "is_deleted", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "project_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "status", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "updated_by", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "document_version", type: "INT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-18") {
		createTable(tableName: "document_item") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "alignment", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "checkpoint_type", type: "VARCHAR(255)")

			column(name: "document_template_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "form_element_name", type: "LONGTEXT") {
				constraints(nullable: "false")
			}

			column(name: "is_deleted", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "is_header", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "is_signature", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "position", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "type", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-19") {
		createTable(tableName: "document_item_value") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "alignment", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "document_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "document_item_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "form_element_value", type: "LONGTEXT") {
				constraints(nullable: "false")
			}

			column(name: "position", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "type", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-20") {
		createTable(tableName: "document_report") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "document_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-21") {
		createTable(tableName: "document_template") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "created_by", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "customer_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "discipline", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "is_deleted", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-22") {
		createTable(tableName: "email_group") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "activated_by", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "customer_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "group_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "group_type", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-23") {
		createTable(tableName: "email_group_member_emails") {
			column(name: "email_group_members_id", type: "BIGINT")

			column(name: "member_emails_id", type: "BIGINT")
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-24") {
		createTable(tableName: "emergency_unit_of_print") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "deleted", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "unit_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-25") {
		createTable(tableName: "equipment") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "armatuur_id", type: "BIGINT")

			column(name: "battery_id", type: "BIGINT")

			column(name: "brand_id", type: "BIGINT")

			column(name: "build_year_of_armature", type: "VARCHAR(255)")

			column(name: "build_year_of_battery", type: "VARCHAR(255)")

			column(name: "build_year_of_emergency_unit", type: "VARCHAR(255)")

			column(name: "created_by", type: "VARCHAR(255)")

			column(name: "customer", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "dead_line", type: "DATETIME")

			column(name: "description", type: "VARCHAR(255)")

			column(name: "emergency_unit_of_print_id", type: "BIGINT")

			column(name: "equipment_type", type: "VARCHAR(255)")

			column(name: "floor_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "group_no_id", type: "BIGINT")

			column(name: "is_deleted", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "kast_id", type: "BIGINT")

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "light_id", type: "BIGINT")

			column(name: "name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "picto_gram", type: "VARCHAR(255)")

			column(name: "picto_gram_afw", type: "VARCHAR(255)")

			column(name: "spec", type: "VARCHAR(255)")

			column(name: "status", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-26") {
		createTable(tableName: "equipment_checkpoint") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "checkpoint_description", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "checkpoint_type", type: "VARCHAR(255)")

			column(name: "comment", type: "VARCHAR(255)")

			column(name: "equipment_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "floor_id", type: "VARCHAR(255)")

			column(name: "priority", type: "VARCHAR(255)")

			column(name: "priority_date", type: "VARCHAR(255)")

			column(name: "report_id", type: "VARCHAR(255)")

			column(name: "status", type: "VARCHAR(255)")

			column(name: "value", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-27") {
		createTable(tableName: "equipment_image") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "equipment_checkpoint_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "image_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-28") {
		createTable(tableName: "excel") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "file", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "user", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-29") {
		createTable(tableName: "floor") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "building_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "floor_description", type: "LONGTEXT")

			column(name: "floor_map", type: "VARCHAR(255)")

			column(name: "floor_number", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "is_deleted", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "revision_date", type: "DATETIME")
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-30") {
		createTable(tableName: "floor_document") {
			column(name: "floor_documents_id", type: "BIGINT")

			column(name: "document_id", type: "BIGINT")
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-31") {
		createTable(tableName: "group_nr") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "deleted", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "group_number", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-32") {
		createTable(tableName: "header") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "checkpoint_type", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "document_item_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-33") {
		createTable(tableName: "image") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "document_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "ticket_id", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-34") {
		createTable(tableName: "invitation_code") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "admin_name", type: "VARCHAR(255)")

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "email", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "super_user", type: "VARCHAR(255)")

			column(name: "token", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-35") {
		createTable(tableName: "kast") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "deleted", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "kast_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-36") {
		createTable(tableName: "light") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "deleted", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-37") {
		createTable(tableName: "member_emails") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "email", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-38") {
		createTable(tableName: "notify_user") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "notify_group_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "parent_id", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "type", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-39") {
		createTable(tableName: "project") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "address", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "city", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "customer_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "is_deleted", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "project_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "project_number", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "project_title", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-40") {
		createTable(tableName: "question") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "document_item_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "is_header", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "question", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "serial_no", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-41") {
		createTable(tableName: "question_value") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "checkpoint_type", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "document_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "document_item_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "document_item_values_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "question_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "question_value", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-42") {
		createTable(tableName: "registration_code") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "token", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-43") {
		createTable(tableName: "report") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "building_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "project_name", type: "VARCHAR(255)")

			column(name: "project_number", type: "VARCHAR(255)")

			column(name: "report_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "reviewed_by", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-44") {
		createTable(tableName: "report_value") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "floor_id", type: "BIGINT")

			column(name: "report_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "type", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "value", type: "VARCHAR(6000)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-45") {
		createTable(tableName: "role") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "authority", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-46") {
		createTable(tableName: "room") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "floor_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "is_deleted", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "room_id", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-47") {
		createTable(tableName: "room_document") {
			column(name: "room_documents_id", type: "BIGINT")

			column(name: "document_id", type: "BIGINT")
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-48") {
		createTable(tableName: "saved_document") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "building_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "comments", type: "VARCHAR(255)")

			column(name: "document_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "document_report_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "document_version", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "is_nowa_ticket", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "priority", type: "VARCHAR(255)")

			column(name: "priority_date", type: "VARCHAR(255)")

			column(name: "project_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "room_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "status", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "title", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-49") {
		createTable(tableName: "saved_document_image") {
			column(name: "saved_document_attachments_id", type: "BIGINT")

			column(name: "image_id", type: "BIGINT")
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-50") {
		createTable(tableName: "ticket") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "document_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "floor_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "room_id", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-51") {
		createTable(tableName: "user") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "account_expired", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "account_locked", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "created_by", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "customer_id", type: "BIGINT")

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "email", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "enabled", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "first_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "is_deleted", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "last_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "middle_name", type: "VARCHAR(255)")

			column(name: "mobile", type: "VARCHAR(255)")

			column(name: "password", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "password_expired", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-52") {
		createTable(tableName: "user_document") {
			column(name: "user_document_id", type: "BIGINT")

			column(name: "document_id", type: "BIGINT")
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-53") {
		createTable(tableName: "user_role") {
			column(name: "role_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-54") {
		addPrimaryKey(columnNames: "role_id, user_id", tableName: "user_role")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-55") {
		addForeignKeyConstraint(baseColumnNames: "message_id", baseTableName: "async_mail_attachment", baseTableSchemaName: "KAM", constraintName: "FK1CACA0E817082B9", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "async_mail_mess", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-56") {
		addForeignKeyConstraint(baseColumnNames: "project_id", baseTableName: "building", baseTableSchemaName: "KAM", constraintName: "FKAABA12B4111B6CF6", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "project", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-57") {
		addForeignKeyConstraint(baseColumnNames: "building_documents_id", baseTableName: "building_document", baseTableSchemaName: "KAM", constraintName: "FKECDFBA46D7163005", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "building", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-58") {
		addForeignKeyConstraint(baseColumnNames: "document_id", baseTableName: "building_document", baseTableSchemaName: "KAM", constraintName: "FKECDFBA46D769557E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "document", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-59") {
		addForeignKeyConstraint(baseColumnNames: "customer_id", baseTableName: "customize_settings", baseTableSchemaName: "KAM", constraintName: "FK43AF7C9F405B3A9E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "customer", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-60") {
		addForeignKeyConstraint(baseColumnNames: "customer_id", baseTableName: "document", baseTableSchemaName: "KAM", constraintName: "FK335CD11B405B3A9E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "customer", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-61") {
		addForeignKeyConstraint(baseColumnNames: "document_template_id", baseTableName: "document", baseTableSchemaName: "KAM", constraintName: "FK335CD11BA3500A35", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "document_template", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-62") {
		addForeignKeyConstraint(baseColumnNames: "project_id", baseTableName: "document", baseTableSchemaName: "KAM", constraintName: "FK335CD11B111B6CF6", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "project", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-63") {
		addForeignKeyConstraint(baseColumnNames: "document_template_id", baseTableName: "document_item", baseTableSchemaName: "KAM", constraintName: "FK5E7CDBB7A3500A35", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "document_template", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-64") {
		addForeignKeyConstraint(baseColumnNames: "document_id", baseTableName: "document_item_value", baseTableSchemaName: "KAM", constraintName: "FKFF89EAE9D769557E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "document", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-65") {
		addForeignKeyConstraint(baseColumnNames: "document_item_id", baseTableName: "document_item_value", baseTableSchemaName: "KAM", constraintName: "FKFF89EAE9A20B6D15", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "document_item", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-66") {
		addForeignKeyConstraint(baseColumnNames: "document_id", baseTableName: "document_report", baseTableSchemaName: "KAM", constraintName: "FKC1421CD8D769557E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "document", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-67") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "document_report", baseTableSchemaName: "KAM", constraintName: "FKC1421CD85AB3897E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-68") {
		addForeignKeyConstraint(baseColumnNames: "customer_id", baseTableName: "document_template", baseTableSchemaName: "KAM", constraintName: "FK43DD3B1E405B3A9E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "customer", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-69") {
		addForeignKeyConstraint(baseColumnNames: "customer_id", baseTableName: "email_group", baseTableSchemaName: "KAM", constraintName: "FK83E7801C405B3A9E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "customer", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-70") {
		addForeignKeyConstraint(baseColumnNames: "email_group_members_id", baseTableName: "email_group_member_emails", baseTableSchemaName: "KAM", constraintName: "FK13AF4AB9329952EB", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "email_group", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-71") {
		addForeignKeyConstraint(baseColumnNames: "member_emails_id", baseTableName: "email_group_member_emails", baseTableSchemaName: "KAM", constraintName: "FK13AF4AB99B7BF1D3", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "member_emails", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-72") {
		addForeignKeyConstraint(baseColumnNames: "armatuur_id", baseTableName: "equipment", baseTableSchemaName: "KAM", constraintName: "FK4027E58EA7B0807E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "armatuur", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-73") {
		addForeignKeyConstraint(baseColumnNames: "battery_id", baseTableName: "equipment", baseTableSchemaName: "KAM", constraintName: "FK4027E58EF4771876", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "battery", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-74") {
		addForeignKeyConstraint(baseColumnNames: "brand_id", baseTableName: "equipment", baseTableSchemaName: "KAM", constraintName: "FK4027E58E90B3536", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "brand", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-75") {
		addForeignKeyConstraint(baseColumnNames: "emergency_unit_of_print_id", baseTableName: "equipment", baseTableSchemaName: "KAM", constraintName: "FK4027E58ED104264D", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "emergency_unit_of_print", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-76") {
		addForeignKeyConstraint(baseColumnNames: "floor_id", baseTableName: "equipment", baseTableSchemaName: "KAM", constraintName: "FK4027E58E8358AB16", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "floor", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-77") {
		addForeignKeyConstraint(baseColumnNames: "group_no_id", baseTableName: "equipment", baseTableSchemaName: "KAM", constraintName: "FK4027E58EEBDA1918", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "group_nr", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-78") {
		addForeignKeyConstraint(baseColumnNames: "kast_id", baseTableName: "equipment", baseTableSchemaName: "KAM", constraintName: "FK4027E58E2BBF61FE", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "kast", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-79") {
		addForeignKeyConstraint(baseColumnNames: "light_id", baseTableName: "equipment", baseTableSchemaName: "KAM", constraintName: "FK4027E58E463796D6", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "light", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-80") {
		addForeignKeyConstraint(baseColumnNames: "equipment_id", baseTableName: "equipment_checkpoint", baseTableSchemaName: "KAM", constraintName: "FK41D760D9584A55D6", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "equipment", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-81") {
		addForeignKeyConstraint(baseColumnNames: "equipment_checkpoint_id", baseTableName: "equipment_image", baseTableSchemaName: "KAM", constraintName: "FK39B40F6AD09047D3", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "equipment_checkpoint", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-82") {
		addForeignKeyConstraint(baseColumnNames: "building_id", baseTableName: "floor", baseTableSchemaName: "KAM", constraintName: "FK5D0240CDC1B42DE", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "building", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-83") {
		addForeignKeyConstraint(baseColumnNames: "document_id", baseTableName: "floor_document", baseTableSchemaName: "KAM", constraintName: "FK844BABEED769557E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "document", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-84") {
		addForeignKeyConstraint(baseColumnNames: "floor_documents_id", baseTableName: "floor_document", baseTableSchemaName: "KAM", constraintName: "FK844BABEEE892263D", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "floor", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-85") {
		addForeignKeyConstraint(baseColumnNames: "document_item_id", baseTableName: "header", baseTableSchemaName: "KAM", constraintName: "FKB734E28DA20B6D15", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "document_item", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-86") {
		addForeignKeyConstraint(baseColumnNames: "document_id", baseTableName: "image", baseTableSchemaName: "KAM", constraintName: "FK5FAA95BD769557E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "document", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-87") {
		addForeignKeyConstraint(baseColumnNames: "ticket_id", baseTableName: "image", baseTableSchemaName: "KAM", constraintName: "FK5FAA95B613F5CDE", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "ticket", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-88") {
		addForeignKeyConstraint(baseColumnNames: "customer_id", baseTableName: "project", baseTableSchemaName: "KAM", constraintName: "FKED904B19405B3A9E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "customer", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-89") {
		addForeignKeyConstraint(baseColumnNames: "document_item_id", baseTableName: "question", baseTableSchemaName: "KAM", constraintName: "FKBA823BE6A20B6D15", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "document_item", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-90") {
		addForeignKeyConstraint(baseColumnNames: "document_id", baseTableName: "question_value", baseTableSchemaName: "KAM", constraintName: "FK904C2BD8D769557E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "document", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-91") {
		addForeignKeyConstraint(baseColumnNames: "document_item_id", baseTableName: "question_value", baseTableSchemaName: "KAM", constraintName: "FK904C2BD8A20B6D15", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "document_item", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-92") {
		addForeignKeyConstraint(baseColumnNames: "document_item_values_id", baseTableName: "question_value", baseTableSchemaName: "KAM", constraintName: "FK904C2BD8835E58EF", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "document_item_value", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-93") {
		addForeignKeyConstraint(baseColumnNames: "question_id", baseTableName: "question_value", baseTableSchemaName: "KAM", constraintName: "FK904C2BD879D55D9E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "question", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-94") {
		addForeignKeyConstraint(baseColumnNames: "building_id", baseTableName: "report", baseTableSchemaName: "KAM", constraintName: "FKC84C5534DC1B42DE", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "building", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-95") {
		addForeignKeyConstraint(baseColumnNames: "floor_id", baseTableName: "report_value", baseTableSchemaName: "KAM", constraintName: "FK388592A68358AB16", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "floor", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-96") {
		addForeignKeyConstraint(baseColumnNames: "report_id", baseTableName: "report_value", baseTableSchemaName: "KAM", constraintName: "FK388592A64CD14BDE", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "report", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-97") {
		addForeignKeyConstraint(baseColumnNames: "floor_id", baseTableName: "room", baseTableSchemaName: "KAM", constraintName: "FK3580DB8358AB16", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "floor", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-98") {
		addForeignKeyConstraint(baseColumnNames: "document_id", baseTableName: "room_document", baseTableSchemaName: "KAM", constraintName: "FK579047FFD769557E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "document", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-99") {
		addForeignKeyConstraint(baseColumnNames: "room_documents_id", baseTableName: "room_document", baseTableSchemaName: "KAM", constraintName: "FK579047FF69CB1965", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "room", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-100") {
		addForeignKeyConstraint(baseColumnNames: "building_id", baseTableName: "saved_document", baseTableSchemaName: "KAM", constraintName: "FKC5D5FBD3DC1B42DE", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "building", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-101") {
		addForeignKeyConstraint(baseColumnNames: "document_id", baseTableName: "saved_document", baseTableSchemaName: "KAM", constraintName: "FKC5D5FBD3D769557E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "document", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-102") {
		addForeignKeyConstraint(baseColumnNames: "document_report_id", baseTableName: "saved_document", baseTableSchemaName: "KAM", constraintName: "FKC5D5FBD3C6070AB5", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "document_report", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-103") {
		addForeignKeyConstraint(baseColumnNames: "project_id", baseTableName: "saved_document", baseTableSchemaName: "KAM", constraintName: "FKC5D5FBD3111B6CF6", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "project", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-104") {
		addForeignKeyConstraint(baseColumnNames: "room_id", baseTableName: "saved_document", baseTableSchemaName: "KAM", constraintName: "FKC5D5FBD3B5B6AF7E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "room", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-105") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "saved_document", baseTableSchemaName: "KAM", constraintName: "FKC5D5FBD35AB3897E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-106") {
		addForeignKeyConstraint(baseColumnNames: "image_id", baseTableName: "saved_document_image", baseTableSchemaName: "KAM", constraintName: "FK50079DEFD7B274B6", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "image", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-107") {
		addForeignKeyConstraint(baseColumnNames: "saved_document_attachments_id", baseTableName: "saved_document_image", baseTableSchemaName: "KAM", constraintName: "FK50079DEF3A428F94", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "saved_document", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-108") {
		addForeignKeyConstraint(baseColumnNames: "document_id", baseTableName: "ticket", baseTableSchemaName: "KAM", constraintName: "FKCBE86B0CD769557E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "document", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-109") {
		addForeignKeyConstraint(baseColumnNames: "floor_id", baseTableName: "ticket", baseTableSchemaName: "KAM", constraintName: "FKCBE86B0C8358AB16", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "floor", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-110") {
		addForeignKeyConstraint(baseColumnNames: "room_id", baseTableName: "ticket", baseTableSchemaName: "KAM", constraintName: "FKCBE86B0CB5B6AF7E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "room", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-111") {
		addForeignKeyConstraint(baseColumnNames: "customer_id", baseTableName: "user", baseTableSchemaName: "KAM", constraintName: "FK36EBCB405B3A9E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "customer", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-112") {
		addForeignKeyConstraint(baseColumnNames: "document_id", baseTableName: "user_document", baseTableSchemaName: "KAM", constraintName: "FK36602B0FD769557E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "document", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-113") {
		addForeignKeyConstraint(baseColumnNames: "user_document_id", baseTableName: "user_document", baseTableSchemaName: "KAM", constraintName: "FK36602B0F24ACD3BA", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-114") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "user_role", baseTableSchemaName: "KAM", constraintName: "FK143BF46AB588C59E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "role", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-115") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_role", baseTableSchemaName: "KAM", constraintName: "FK143BF46A5AB3897E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user", referencedTableSchemaName: "KAM", referencesUniqueColumn: "false")
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-116") {
		createIndex(indexName: "authority", tableName: "role", unique: "true") {
			column(name: "authority")
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-117") {
		createIndex(indexName: "email", tableName: "user", unique: "true") {
			column(name: "email")
		}
	}

	changeSet(author: "Oodles (generated)", id: "1345631638703-118") {
		createIndex(indexName: "username", tableName: "user", unique: "true") {
			column(name: "username")
		}
	}
}
