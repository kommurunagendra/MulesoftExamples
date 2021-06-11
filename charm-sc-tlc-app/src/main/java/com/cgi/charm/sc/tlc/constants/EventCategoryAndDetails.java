package com.cgi.charm.sc.tlc.constants;

import com.cgi.charm.sc.tlc.helper.TriggerEventDetails;

/**
 * @author pavan.chenna
 *
 */
public enum EventCategoryAndDetails {

	IO_EVENTS_1010("1010") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Lampfout");
			triggerEventDetails.getDetails().add(0, TlcConstants.Trigger_Event_Detail_SgRef);
			triggerEventDetails.getDetails().add(1, "Kleur");
			triggerEventDetails.getDetails().add(2, "Lref");
			triggerEventDetails.getDetails().add(3, "Cat");
			triggerEventDetails.getDetails().add(4, "ALF");
			triggerEventDetails.getDetails().add(5, "LRF");
			triggerEventDetails.getDetails().add(6, "STEND");
			triggerEventDetails.setEventCode(this.getValue());
			return triggerEventDetails;
		}
	},
	IO_EVENTS_1020("1020") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Detectiefout");
			triggerEventDetails.getDetails().add(0, "DetRef");
			triggerEventDetails.getDetails().add(1, "Bezet");
			triggerEventDetails.getDetails().add(2, "BOV");
			triggerEventDetails.getDetails().add(3, "OND");
			triggerEventDetails.getDetails().add(4, "LF");
			triggerEventDetails.getDetails().add(5, "SW");
			triggerEventDetails.getDetails().add(6, "STBOV");
			triggerEventDetails.getDetails().add(7, "STOND");
			triggerEventDetails.getDetails().add(8, "STLF");
			triggerEventDetails.getDetails().add(9, "FLT");
			triggerEventDetails.getDetails().add(10, "STFLT");
			triggerEventDetails.setEventCode(this.getValue());
			return triggerEventDetails;
		}
	},
	IO_EVENTS_1030("1030") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Akoestischefout");
			triggerEventDetails.getDetails().add(0, TlcConstants.Trigger_Event_Detail_BeginEinde);
			triggerEventDetails.getDetails().add(1, "AkRef");
			triggerEventDetails.setEventCode(this.getValue());
			return triggerEventDetails;
		}
	},
	PROGRAM_EVENTS_2000("2000") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Programma event");
			triggerEventDetails.getDetails().add(TlcConstants.Trigger_Event_Detail_IdNummer);
			triggerEventDetails.setEventCode(this.getValue());
			return triggerEventDetails;
		}
	},
	PROGRAM_EVENTS_2001("2001") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("VRI status wijziging");
			triggerEventDetails.getDetails().add(TlcConstants.Trigger_Event_Detail_IdNummer);
			triggerEventDetails.setEventCode(this.getValue());
			return triggerEventDetails;
		}
	},
	PROGRAM_EVENTS_2002("2002") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Programmaomschakeling");
			triggerEventDetails.getDetails().add(TlcConstants.Trigger_Event_Detail_IdNummer);
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}

	},
	PROGRAM_EVENTS_2003("2003") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Brugingreep");
			triggerEventDetails.setEventCode(getValue());
			triggerEventDetails.getDetails().add(0, TlcConstants.Trigger_Event_Detail_IdNummer);
			triggerEventDetails.getDetails().add(1, TlcConstants.Trigger_Event_Detail_BeginEinde);
			return triggerEventDetails;
		}
	},
	PROGRAM_EVENTS_2004("2004") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Brandweeringreep");
			triggerEventDetails.setEventCode(getValue());
			triggerEventDetails.getDetails().add(0, TlcConstants.Trigger_Event_Detail_IdNummer);
			triggerEventDetails.getDetails().add(1, TlcConstants.Trigger_Event_Detail_BeginEinde);
			return triggerEventDetails;
		}

	},
	PROGRAM_EVENTS_2005("2005") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("AHOB melding");
			triggerEventDetails.setEventCode(getValue());
			triggerEventDetails.getDetails().add(0, TlcConstants.Trigger_Event_Detail_IdNummer);
			triggerEventDetails.getDetails().add(1, TlcConstants.Trigger_Event_Detail_BeginEinde);
			return triggerEventDetails;
		}
	},
	PROGRAM_EVENTS_2500("2500") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Fasebewaking");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	PROGRAM_EVENTS_2501("2501") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails
					.setDescription("GUS-WUS fouten CVN C-interface");
			triggerEventDetails.setEventCode(getValue());
			triggerEventDetails.getDetails().add(TlcConstants.Trigger_Event_Detail_SgRef);
			return triggerEventDetails;
		}
	},
	PROGRAM_EVENTS_2502("2502") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Rekentijdproblemen");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	PROGRAM_EVENTS_2503("2503") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Garantietijdonderschrijding");
			triggerEventDetails.setEventCode(getValue());
			triggerEventDetails.getDetails().add(0, TlcConstants.Trigger_Event_Detail_SgRef);
			triggerEventDetails.getDetails().add(1, TlcConstants.Trigger_Event_Detail_ExternSGToestand);
			triggerEventDetails.getDetails().add(2, "Tijd");
			return triggerEventDetails;
		}
	},
	PROGRAM_EVENTS_2504("2504") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Maximumtijdoverschrijding");
			triggerEventDetails.setEventCode(getValue());
			triggerEventDetails.getDetails().add(0, TlcConstants.Trigger_Event_Detail_SgRef);
			triggerEventDetails.getDetails().add(1, TlcConstants.Trigger_Event_Detail_ExternSGToestand);
			triggerEventDetails.getDetails().add(2, "Tijd");
			return triggerEventDetails;
		}
	},
	PROGRAM_EVENTS_2505("2505") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails
					.setDescription("Start niet kunnen regelen door storing");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	PROGRAM_EVENTS_2506("2506") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails
					.setDescription("Einde niet kunnen regelen door storing");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	PROGRAM_EVENTS_2510("2510") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails
					.setDescription("Overig Logboek 90% vol grens bereikt");
			triggerEventDetails.setEventCode(getValue());
			triggerEventDetails.getDetails().add("Obj");
			return triggerEventDetails;
		}
	},
	PROGRAM_EVENTS_2511("2511") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails
					.setDescription("VRI.LA Logboek 90% vol grens bereikt");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	PROGRAM_EVENTS_2512("2512") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails
					.setDescription("PAR.LA Logboek 90% vol grens berekit");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	PROGRAM_EVENTS_2513("2513") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails
					.setDescription("OV.LA Logboek 90% vol grens berekit");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	PROGRAM_EVENTS_2600("2600") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails
					.setDescription("Seriele koppeling - ontbreken levensignaal");
			triggerEventDetails.setEventCode(getValue());
			triggerEventDetails.getDetails().add(TlcConstants.Trigger_Event_Detail_IdNummer);
			return triggerEventDetails;
		}
	},
	PROGRAM_EVENTS_2601("2601") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails
					.setDescription("Seriele koppeling - geen communicate");
			triggerEventDetails.setEventCode(getValue());
			triggerEventDetails.getDetails().add(TlcConstants.Trigger_Event_Detail_IdNummer);
			return triggerEventDetails;
		}
	},
	PROGRAM_EVENTS_2700("2700") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Onderspanningsmelding");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	PROGRAM_EVENTS_2701("2701") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Bovenspanningsmelding");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	PROGRAM_EVENTS_2702("2702") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Telefoonnummer centrale kwijt");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	MONITORING_EVENTS_3000("3000") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Algemeen bewakerevent");
			triggerEventDetails.setEventCode(getValue());
			triggerEventDetails.getDetails().add(TlcConstants.Trigger_Event_Detail_IdNummer);
			return triggerEventDetails;
		}
	},
	MONITORING_EVENTS_3001("3001") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Conflict");
			triggerEventDetails.setEventCode(getValue());
			triggerEventDetails.getDetails().add(0, TlcConstants.Trigger_Event_Detail_SgRef);
			triggerEventDetails.getDetails().add(1, TlcConstants.Trigger_Event_Detail_SgRef);
			triggerEventDetails.getDetails().add(2, "ConflictType");
			triggerEventDetails.getDetails().add(3, "Tijd");
			return triggerEventDetails;
		}
	},
	MONITORING_EVENTS_3002("3002") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Lampfout");
			triggerEventDetails.setEventCode(getValue());
			triggerEventDetails.getDetails().add(TlcConstants.Trigger_Event_Detail_IdNummer);
			return triggerEventDetails;
		}
	},
	MONITORING_EVENTS_3003("3003") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Meer dan 1 kleur");
			triggerEventDetails.setEventCode(getValue());
			triggerEventDetails.getDetails().add(TlcConstants.Trigger_Event_Detail_SgRef);
			return triggerEventDetails;
		}
	},
	MONITORING_EVENTS_3004("3004") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Geelknipperfout");
			triggerEventDetails.setEventCode(getValue());
			triggerEventDetails.getDetails().add(TlcConstants.Trigger_Event_Detail_SgRef);
			return triggerEventDetails;
		}
	},
	MONITORING_EVENTS_3005("3005") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Garantietijdonderschrijding");
			triggerEventDetails.setEventCode(getValue());
			triggerEventDetails.getDetails().add(0, TlcConstants.Trigger_Event_Detail_SgRef);
			triggerEventDetails.getDetails().add(1, TlcConstants.Trigger_Event_Detail_ExternSGToestand);
			triggerEventDetails.getDetails().add(2, "Tijd");
			return triggerEventDetails;
		}
	},
	MONITORING_EVENTS_3006("3006") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Maximumtijdoverschrijding");
			triggerEventDetails.setEventCode(getValue());
			triggerEventDetails.getDetails().add(0, TlcConstants.Trigger_Event_Detail_SgRef);
			triggerEventDetails.getDetails().add(1, TlcConstants.Trigger_Event_Detail_ExternSGToestand);
			triggerEventDetails.getDetails().add(2, "Tijd");
			return triggerEventDetails;
		}
	},
	MONITORING_EVENTS_3007("3007") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Fout in eindschakelaar");
			triggerEventDetails.setEventCode(getValue());
			triggerEventDetails.getDetails().add(TlcConstants.Trigger_Event_Detail_IdNummer);
			return triggerEventDetails;
		}
	},
	MONITORING_EVENTS_3008("3008") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Witknipperfout");
			triggerEventDetails.setEventCode(getValue());
			triggerEventDetails.getDetails().add(TlcConstants.Trigger_Event_Detail_SgRef);
			return triggerEventDetails;
		}
	},
	MONITORING_EVENTS_3009("3009") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Halfconflict OV");
			triggerEventDetails.setEventCode(getValue());
			triggerEventDetails.getDetails().add(0, TlcConstants.Trigger_Event_Detail_SgRef);
			triggerEventDetails.getDetails().add(1, TlcConstants.Trigger_Event_Detail_SgRef);
			return triggerEventDetails;
		}
	},
	MONITORING_EVENTS_3010("3010") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Volgordebewaking");
			triggerEventDetails.setEventCode(getValue());
			triggerEventDetails.getDetails().add(0, TlcConstants.Trigger_Event_Detail_SgRef);
			triggerEventDetails.getDetails().add(1, TlcConstants.Trigger_Event_Detail_ExternSGToestand);
			triggerEventDetails.getDetails().add(2, TlcConstants.Trigger_Event_Detail_ExternSGToestand);
			return triggerEventDetails;
		}
	},
	RESET_EVENTS_4000("4000") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Algemeen resetevent");
			triggerEventDetails.setEventCode(getValue());
			triggerEventDetails.getDetails().add(TlcConstants.Trigger_Event_Detail_IdNummer);
			return triggerEventDetails;
		}
	},
	RESET_EVENTS_4001("4001") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("REset van alle storingen");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	RESET_EVENTS_4002("4002") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Reset van detectiealarmen");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	RESET_EVENTS_4003("4003") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Reset van Iampfouten");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	RESET_EVENTS_4004("4004") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Reset van applicatiefouten");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	RESET_EVENTS_4005("4005") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Reset van tellers");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	RESET_EVENTS_4006("4006") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Reset teller applicatiefouten");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	RESET_EVENTS_4007("4007") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails
					.setDescription("Reset teller aantal GUS-WUS fouten");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	RESET_EVENTS_4008("4008") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails
					.setDescription("Reset teller fasebewakingsfouten");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	RESET_EVENTS_4009("4009") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails
					.setDescription("Reset teller executietijdoverschrijdingen");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	RESET_EVENTS_4010("4010") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Netspanning uitsterfbericht");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	RESET_EVENTS_4011("4011") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Opstartbericht");
			triggerEventDetails.setEventCode(getValue());
			triggerEventDetails.getDetails().add("Soort start");
			return triggerEventDetails;
		}
	},
	RESET_EVENTS_4012("4012") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Deur open politie paneel");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	RESET_EVENTS_4013("4013") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Deur open wegbeheerder");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	RESET_EVENTS_4014("4014") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails
					.setDescription("Deur open energie compartiment");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	RESET_EVENTS_4015("4015") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Testbericht noodkreetmelder");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	RESET_EVENTS_4016("4016") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Noodstroomvoedingberich");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	RESET_EVENTS_4022("4022") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails
					.setDescription("Aanvraag toestemming lokaal is gedaan door gebruiker bij VRI");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	RESET_EVENTS_4023("4023") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails
					.setDescription("Aanvraag toestemming lokaal is ingetrokken door gebruiker bij VRI");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	DATA_COMMUNICATION_EVENTS_6000("6000") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Testtrigger");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	DATA_COMMUNICATION_EVENTS_6001("6001") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Begin fysieke verfbinding");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	DATA_COMMUNICATION_EVENTS_6002("6002") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Einde fysieke verbinding");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	DATA_COMMUNICATION_EVENTS_6003("6003") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Poging tot inbreuk");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	DATA_COMMUNICATION_EVENTS_6004("6004") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Uitbellen naar centrale");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	},
	DATA_COMMUNICATION_EVENTS_6005("6005") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Login");
			triggerEventDetails.setEventCode(getValue());
			triggerEventDetails.getDetails().add("Inlogniveau");
			return triggerEventDetails;
		}
	},
	DATA_COMMUNICATION_EVENTS_6006("6006") {
		@Override
		public TriggerEventDetails getEventDetails() {
			TriggerEventDetails triggerEventDetails = new TriggerEventDetails();
			triggerEventDetails.setDescription("Logout");
			triggerEventDetails.setEventCode(getValue());
			return triggerEventDetails;
		}
	};

	private final String value;

	EventCategoryAndDetails(final String newValue) {
		value = newValue;
	}

	public abstract TriggerEventDetails getEventDetails();

	
	/**
	 * EventCategoryDetailsByEventCode
	 * @param eventCode is for prepare msg
	 * @return EventCategoryAndDetails
	 */
	public static EventCategoryAndDetails getEventCategoryAndDetailsByeventCode(
			String eventCode) {
		EventCategoryAndDetails categoryByCode = null;
		for (EventCategoryAndDetails category : EventCategoryAndDetails
				.values()) {
			if (category.getValue().equals(eventCode)) {
				categoryByCode = category;
				break;
			}
		}
		return categoryByCode;
	}

	public String getValue() {
		return value;
	}
}
