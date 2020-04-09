package snap.ib.personelLoan.adapter.common.request;

public class LoginAttemptsRequest {

	private String mobile_number;
	private String wrong_otp;
	private String wrong_mpin;
	private String otp_count;
	private String mpin_count;
	private String additional_fld1;
	private String additional_fld2;
	private String additional_fld3;
	private String lead_id;
	private String opp_id;
	private String con_id;
	
	
	public String getLead_id() {
		return lead_id;
	}
	public void setLead_id(String lead_id) {
		this.lead_id = lead_id;
	}
	public String getOpp_id() {
		return opp_id;
	}
	public void setOpp_id(String opp_id) {
		this.opp_id = opp_id;
	}
	public String getCon_id() {
		return con_id;
	}
	public void setCon_id(String con_id) {
		this.con_id = con_id;
	}
	public String getMobile_number() {
		return mobile_number;
	}
	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}
	public String getWrong_otp() {
		return wrong_otp;
	}
	public void setWrong_otp(String wrong_otp) {
		this.wrong_otp = wrong_otp;
	}
	public String getWrong_mpin() {
		return wrong_mpin;
	}
	public void setWrong_mpin(String wrong_mpin) {
		this.wrong_mpin = wrong_mpin;
	}
	public String getOtp_count() {
		return otp_count;
	}
	public void setOtp_count(String otp_count) {
		this.otp_count = otp_count;
	}
	public String getMpin_count() {
		return mpin_count;
	}
	public void setMpin_count(String mpin_count) {
		this.mpin_count = mpin_count;
	}
	public String getAdditional_fld1() {
		return additional_fld1;
	}
	public void setAdditional_fld1(String additional_fld1) {
		this.additional_fld1 = additional_fld1;
	}
	public String getAdditional_fld2() {
		return additional_fld2;
	}
	public void setAdditional_fld2(String additional_fld2) {
		this.additional_fld2 = additional_fld2;
	}
	public String getAdditional_fld3() {
		return additional_fld3;
	}
	public void setAdditional_fld3(String additional_fld3) {
		this.additional_fld3 = additional_fld3;
	}
	@Override
	public String toString() {
		return "LoginAttemptsRequest [mobile_number=" + mobile_number + ", wrong_otp=" + wrong_otp + ", wrong_mpin="
				+ wrong_mpin + ", otp_count=" + otp_count + ", mpin_count=" + mpin_count + ", additional_fld1="
				+ additional_fld1 + ", additional_fld2=" + additional_fld2 + ", additional_fld3=" + additional_fld3
				+ "]";
	}
}
