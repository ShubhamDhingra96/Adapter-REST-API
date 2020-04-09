package snap.ib.personelLoan.adapter.common.request;

public class DocumentListRequest {
	
	    private String lead_id;
	    private String additional_1;
	    private String additional_2;
	    private String request_type;
		private String device_id;
		private String session_key;
		
		
		public String getAdditional_1() {
			return additional_1;
		}
		public void setAdditional_1(String additional_1) {
			this.additional_1 = additional_1;
		}
		public String getAdditional_2() {
			return additional_2;
		}
		public void setAdditional_2(String additional_2) {
			this.additional_2 = additional_2;
		}
		public String getRequest_type() {
			return request_type;
		}
		public void setRequest_type(String request_type) {
			this.request_type = request_type;
		}
		
		public String getLead_id() {
			return lead_id;
		}
		public void setLead_id(String lead_id) {
			this.lead_id = lead_id;
		}
		
		public String getDevice_id() {
			return device_id;
		}
		public void setDevice_id(String device_id) {
			this.device_id = device_id;
		}
		public String getSession_key() {
			return session_key;
		}
		public void setSession_key(String session_key) {
			this.session_key = session_key;
		}
		
		

}
