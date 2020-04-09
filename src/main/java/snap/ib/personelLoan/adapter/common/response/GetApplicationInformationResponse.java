package snap.ib.personelLoan.adapter.common.response;
import org.json.simple.JSONArray;

public class GetApplicationInformationResponse {
	
	
	private String tenure_in_months;
	private boolean success;
	private String sub_occupation;
	private Double stamp_duty;
	private String stage;
	private String spouse_name;
	private String soft_approval_decision;
	private String service_charge;
	private String salutation;
	private Double revised_requested_loan_tenure;
	private Double revised_requested_loan_amount;
	private String residential_status;
	private Double rate_of_interest;
	private Double processing_fee;
	private Double pre_emi_amount;
	private String perfios_required;
	private boolean pan_validation_status;
	private String pan_number;
	private String occupation;
	private String mpin;
	private String mother_name;
	private Double monthly_income;
	private String mobile_number;
	private String middle_name;
	private Double max_eligible_loan_amount;
	private String marital_status;
	private Double loan_amount;
	private String lead_id;
	private String last_name;
	private String kyc_status;
	private String ifsc_code;
	private String gender;
	private String frequency;
	private String first_name;
	private String father_name;
	private String error_message;
	private String emi_due_day;
	private String emi_cycle;
	private Double emi_amount;
	private String emandate_flag;
	private String email_id;
	private String dob;
	private String customer_unique_number;
	private String current_state;
	private String current_pincode;
	private String current_city;
	private String current_address1;
	private boolean converted;
	private String cas_id;
	private String bank_unique_ref_number;
	private String bank_name;
	private String bank_mandate_details;
	private String account_number;
	private String aadhaar_state;
	private String aadhaar_pincode;
	private String aadhaar_phone_number;
	private String aadhaar_number;
	private String aadhaar_name;
	private String aadhaar_city;
	private String aadhaar_address1;
	private String is_alternate_data_valid;
	private String is_soft_approval_valid;
	private String is_hard_approval_valid;
	private String hard_approval_decision;
	private Double max_eligible_emi;
	private boolean Kyc_flag;
	private String disbursment_description_2;
	private String Eligible_For_Months;
	private String disbursment_description_1;
	private Double disbursement_percentage;
	private String device_id;
	private String debit_start_date;
	private String debit_end_date;
	private String current_employmet_in_month;
	private String current_address_same_as_aadhar;
	private String crm_id;
	private String corporate_code;
	private String additional_field_5;
	private String additional_field_4;
	private String additional_field_3;
	private String additional_field_2;
	private Double additional_field_1;
	private JSONArray application_tracker;
	private boolean upload_eligibilty;
	
	private String cvFirstName;
	private String cvMiddleName;
	private String cvLastName;
	private String companyName;
	private String officialEmailID;
	private String companyRegWithCV;
	private boolean isCVCheckDone;
	private String cvNameMatch;
	private String AgeCheck;
	private String locationCheck;
	private String filler1;
	private String filler2;
	private String filler3;
	private String pincode_only_stp;
	private boolean is_poi_uploaded;
	private boolean is_poa_uploaded;
	private boolean  is_mobile_number_valid;
	private String CIBIL_Completed;
	
	private Boolean is_photo_uploaded;
	private String poa_count;
	private String poa_type;
	
	private String doc_verification_status;
	private String instructions_to_cust_poi;
	private String instructions_to_cust_poa;
	private boolean is_insurance_opted;
	
	private String prepaid_card_status;
	private String prepaid_card_courier_status;
	private String prepaid_card_type;
	private String prepaid_card_category;
	private String salary_mode;
	private String cibil_score;
	private String prepaid_card_courier_tracking_id;
	
	
	
	public String getPrepaid_card_courier_tracking_id() {
		return prepaid_card_courier_tracking_id;
	}
	public void setPrepaid_card_courier_tracking_id(String prepaid_card_courier_tracking_id) {
		this.prepaid_card_courier_tracking_id = prepaid_card_courier_tracking_id;
	}
	public String getPrepaid_card_status() {
		return prepaid_card_status;
	}
	public void setPrepaid_card_status(String prepaid_card_status) {
		this.prepaid_card_status = prepaid_card_status;
	}
	public String getPrepaid_card_courier_status() {
		return prepaid_card_courier_status;
	}
	public void setPrepaid_card_courier_status(String prepaid_card_courier_status) {
		this.prepaid_card_courier_status = prepaid_card_courier_status;
	}
	public String getPrepaid_card_type() {
		return prepaid_card_type;
	}
	public void setPrepaid_card_type(String prepaid_card_type) {
		this.prepaid_card_type = prepaid_card_type;
	}
	public String getPrepaid_card_category() {
		return prepaid_card_category;
	}
	public void setPrepaid_card_category(String prepaid_card_category) {
		this.prepaid_card_category = prepaid_card_category;
	}
	public String getSalary_mode() {
		return salary_mode;
	}
	public void setSalary_mode(String salary_mode) {
		this.salary_mode = salary_mode;
	}
	public String getCibil_score() {
		return cibil_score;
	}
	public void setCibil_score(String cibil_score) {
		this.cibil_score = cibil_score;
	}
	public boolean isIs_insurance_opted() {
		return is_insurance_opted;
	}
	public void setIs_insurance_opted(boolean is_insurance_opted) {
		this.is_insurance_opted = is_insurance_opted;
	}
	public String getDoc_verification_status() {
		return doc_verification_status;
	}
	public void setDoc_verification_status(String doc_verification_status) {
		this.doc_verification_status = doc_verification_status;
	}
	public String getInstructions_to_cust_poi() {
		return instructions_to_cust_poi;
	}
	public void setInstructions_to_cust_poi(String instructions_to_cust_poi) {
		this.instructions_to_cust_poi = instructions_to_cust_poi;
	}
	public String getInstructions_to_cust_poa() {
		return instructions_to_cust_poa;
	}
	public void setInstructions_to_cust_poa(String instructions_to_cust_poa) {
		this.instructions_to_cust_poa = instructions_to_cust_poa;
	}
	
	public Boolean getIs_photo_uploaded() {
		return is_photo_uploaded;
	}
	public void setIs_photo_uploaded(Boolean is_photo_uploaded) {
		this.is_photo_uploaded = is_photo_uploaded;
	}
	public String getPoa_count() {
		return poa_count;
	}
	public void setPoa_count(String poa_count) {
		this.poa_count = poa_count;
	}
	public String getPoa_type() {
		return poa_type;
	}
	public void setPoa_type(String poa_type) {
		this.poa_type = poa_type;
	}
	public String getCIBIL_Completed() {
		return CIBIL_Completed;
	}
	public void setCIBIL_Completed(String cIBIL_Completed) {
		CIBIL_Completed = cIBIL_Completed;
	}
	public boolean isIs_mobile_number_valid() {
		return is_mobile_number_valid;
	}
	public void setIs_mobile_number_valid(boolean is_mobile_number_valid) {
		this.is_mobile_number_valid = is_mobile_number_valid;
	}
	public boolean isIs_poi_uploaded() {
		return is_poi_uploaded;
	}
	public void setIs_poi_uploaded(boolean is_poi_uploaded) {
		this.is_poi_uploaded = is_poi_uploaded;
	}
	public boolean isIs_poa_uploaded() {
		return is_poa_uploaded;
	}
	public void setIs_poa_uploaded(boolean is_poa_uploaded) {
		this.is_poa_uploaded = is_poa_uploaded;
	}
	public String getPincode_only_stp() {
		return pincode_only_stp;
	}
	public void setPincode_only_stp(String pincode_only_stp) {
		this.pincode_only_stp = pincode_only_stp;
	}
	public String getEligible_For_Months() {
		return Eligible_For_Months;
	}
	public void setEligible_For_Months(String eligible_For_Months) {
		Eligible_For_Months = eligible_For_Months;
	}
	public boolean isUpload_eligibilty() {
		return upload_eligibilty;
	}
	public void setUpload_eligibilty(boolean finalUpload_eligibilty) {
		upload_eligibilty = finalUpload_eligibilty;
	}
	public JSONArray getApplication_tracker() {
		return application_tracker;
	}
	public void setApplication_tracker(JSONArray application_tracker) {
		this.application_tracker = application_tracker;
	}
	public String getTenure_in_months() {
		return tenure_in_months;
	}
	public void setTenure_in_months(String tenure_in_months) {
		this.tenure_in_months = tenure_in_months;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getSub_occupation() {
		return sub_occupation;
	}
	public void setSub_occupation(String sub_occupation) {
		this.sub_occupation = sub_occupation;
	}
	public Double getStamp_duty() {
		return stamp_duty;
	}
	public void setStamp_duty(Double stamp_duty) {
		this.stamp_duty = stamp_duty;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getSpouse_name() {
		return spouse_name;
	}
	public void setSpouse_name(String spouse_name) {
		this.spouse_name = spouse_name;
	}
	public String getSoft_approval_decision() {
		return soft_approval_decision;
	}
	public void setSoft_approval_decision(String soft_approval_decision) {
		this.soft_approval_decision = soft_approval_decision;
	}
	public String getService_charge() {
		return service_charge;
	}
	public void setService_charge(String service_charge) {
		this.service_charge = service_charge;
	}
	public String getSalutation() {
		return salutation;
	}
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	public Double getRevised_requested_loan_tenure() {
		return revised_requested_loan_tenure;
	}
	public void setRevised_requested_loan_tenure(
			Double revised_requested_loan_tenure) {
		this.revised_requested_loan_tenure = revised_requested_loan_tenure;
	}
	public Double getRevised_requested_loan_amount() {
		return revised_requested_loan_amount;
	}
	public void setRevised_requested_loan_amount(
			Double revised_requested_loan_amount) {
		this.revised_requested_loan_amount = revised_requested_loan_amount;
	}
	public String getResidential_status() {
		return residential_status;
	}
	public void setResidential_status(String residential_status) {
		this.residential_status = residential_status;
	}
	public Double getRate_of_interest() {
		return rate_of_interest;
	}
	public String getCvFirstName() {
		return cvFirstName;
	}
	public void setCvFirstName(String cvFirstName) {
		this.cvFirstName = cvFirstName;
	}
	public String getCvMiddleName() {
		return cvMiddleName;
	}
	public void setCvMiddleName(String cvMiddleName) {
		this.cvMiddleName = cvMiddleName;
	}
	public String getCvLastName() {
		return cvLastName;
	}
	public void setCvLastName(String cvLastName) {
		this.cvLastName = cvLastName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getOfficialEmailID() {
		return officialEmailID;
	}
	public void setOfficialEmailID(String officialEmailID) {
		this.officialEmailID = officialEmailID;
	}
	public String getCompanyRegWithCV() {
		return companyRegWithCV;
	}
	public void setCompanyRegWithCV(String companyRegWithCV) {
		this.companyRegWithCV = companyRegWithCV;
	}
	public boolean isCVCheckDone() {
		return isCVCheckDone;
	}
	public void setCVCheckDone(boolean isCVCheckDone) {
		this.isCVCheckDone = isCVCheckDone;
	}
	public String getCvNameMatch() {
		return cvNameMatch;
	}
	public void setCvNameMatch(String cvNameMatch) {
		this.cvNameMatch = cvNameMatch;
	}
	public String getAgeCheck() {
		return AgeCheck;
	}
	public void setAgeCheck(String ageCheck) {
		AgeCheck = ageCheck;
	}
	public String getLocationCheck() {
		return locationCheck;
	}
	public void setLocationCheck(String locationCheck) {
		this.locationCheck = locationCheck;
	}
	public String getFiller1() {
		return filler1;
	}
	public void setFiller1(String filler1) {
		this.filler1 = filler1;
	}
	public String getFiller2() {
		return filler2;
	}
	public void setFiller2(String filler2) {
		this.filler2 = filler2;
	}
	public String getFiller3() {
		return filler3;
	}
	public void setFiller3(String filler3) {
		this.filler3 = filler3;
	}
	public void setRate_of_interest(Double rate_of_interest) {
		this.rate_of_interest = rate_of_interest;
	}
	public Double getProcessing_fee() {
		return processing_fee;
	}
	public void setProcessing_fee(Double processing_fee) {
		this.processing_fee = processing_fee;
	}
	public Double getPre_emi_amount() {
		return pre_emi_amount;
	}
	public void setPre_emi_amount(Double pre_emi_amount) {
		this.pre_emi_amount = pre_emi_amount;
	}
	public String getPerfios_required() {
		return perfios_required;
	}
	public void setPerfios_required(String perfios_required) {
		this.perfios_required = perfios_required;
	}
	public boolean isPan_validation_status() {
		return pan_validation_status;
	}
	public void setPan_validation_status(boolean pan_validation_status) {
		this.pan_validation_status = pan_validation_status;
	}
	public String getPan_number() {
		return pan_number;
	}
	public void setPan_number(String pan_number) {
		this.pan_number = pan_number;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getMpin() {
		return mpin;
	}
	public void setMpin(String mpin) {
		this.mpin = mpin;
	}
	public String getMother_name() {
		return mother_name;
	}
	public void setMother_name(String mother_name) {
		this.mother_name = mother_name;
	}
	public Double getMonthly_income() {
		return monthly_income;
	}
	public void setMonthly_income(Double monthly_income) {
		this.monthly_income = monthly_income;
	}
	public String getMobile_number() {
		return mobile_number;
	}
	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}
	public String getMiddle_name() {
		return middle_name;
	}
	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}
	public Double getMax_eligible_loan_amount() {
		return max_eligible_loan_amount;
	}
	public void setMax_eligible_loan_amount(Double max_eligible_loan_amount) {
		this.max_eligible_loan_amount = max_eligible_loan_amount;
	}
	public String getMarital_status() {
		return marital_status;
	}
	public void setMarital_status(String marital_status) {
		this.marital_status = marital_status;
	}
	public Double getLoan_amount() {
		return loan_amount;
	}
	public void setLoan_amount(Double loan_amount) {
		this.loan_amount = loan_amount;
	}
	public String getLead_id() {
		return lead_id;
	}
	public void setLead_id(String lead_id) {
		this.lead_id = lead_id;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getKyc_status() {
		return kyc_status;
	}
	public void setKyc_status(String kyc_status) {
		this.kyc_status = kyc_status;
	}
	public String getIfsc_code() {
		return ifsc_code;
	}
	public void setIfsc_code(String ifsc_code) {
		this.ifsc_code = ifsc_code;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getFather_name() {
		return father_name;
	}
	public void setFather_name(String father_name) {
		this.father_name = father_name;
	}
	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	public String getEmi_due_day() {
		return emi_due_day;
	}
	public void setEmi_due_day(String emi_due_day) {
		this.emi_due_day = emi_due_day;
	}
	public String getEmi_cycle() {
		return emi_cycle;
	}
	public void setEmi_cycle(String emi_cycle) {
		this.emi_cycle = emi_cycle;
	}
	public Double getEmi_amount() {
		return emi_amount;
	}
	public void setEmi_amount(Double emi_amount) {
		this.emi_amount = emi_amount;
	}
	public String getEmandate_flag() {
		return emandate_flag;
	}
	public void setEmandate_flag(String emandate_flag) {
		this.emandate_flag = emandate_flag;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getCustomer_unique_number() {
		return customer_unique_number;
	}
	public void setCustomer_unique_number(String customer_unique_number) {
		this.customer_unique_number = customer_unique_number;
	}
	public String getCurrent_state() {
		return current_state;
	}
	public void setCurrent_state(String current_state) {
		this.current_state = current_state;
	}
	public String getCurrent_pincode() {
		return current_pincode;
	}
	public void setCurrent_pincode(String current_pincode) {
		this.current_pincode = current_pincode;
	}
	public String getCurrent_city() {
		return current_city;
	}
	public void setCurrent_city(String current_city) {
		this.current_city = current_city;
	}
	public String getCurrent_address1() {
		return current_address1;
	}
	public void setCurrent_address1(String current_address1) {
		this.current_address1 = current_address1;
	}
	public boolean isConverted() {
		return converted;
	}
	public void setConverted(boolean converted) {
		this.converted = converted;
	}
	public String getCas_id() {
		return cas_id;
	}
	public void setCas_id(String cas_id) {
		this.cas_id = cas_id;
	}
	public String getBank_unique_ref_number() {
		return bank_unique_ref_number;
	}
	public void setBank_unique_ref_number(String bank_unique_ref_number) {
		this.bank_unique_ref_number = bank_unique_ref_number;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getBank_mandate_details() {
		return bank_mandate_details;
	}
	public void setBank_mandate_details(String bank_mandate_details) {
		this.bank_mandate_details = bank_mandate_details;
	}
	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	public String getAadhaar_state() {
		return aadhaar_state;
	}
	public void setAadhaar_state(String aadhaar_state) {
		this.aadhaar_state = aadhaar_state;
	}
	public String getAadhaar_pincode() {
		return aadhaar_pincode;
	}
	public void setAadhaar_pincode(String aadhaar_pincode) {
		this.aadhaar_pincode = aadhaar_pincode;
	}
	public String getAadhaar_phone_number() {
		return aadhaar_phone_number;
	}
	public void setAadhaar_phone_number(String aadhaar_phone_number) {
		this.aadhaar_phone_number = aadhaar_phone_number;
	}
	public String getAadhaar_number() {
		return aadhaar_number;
	}
	public void setAadhaar_number(String aadhaar_number) {
		this.aadhaar_number = aadhaar_number;
	}
	public String getAadhaar_name() {
		return aadhaar_name;
	}
	public void setAadhaar_name(String aadhaar_name) {
		this.aadhaar_name = aadhaar_name;
	}
	public String getAadhaar_city() {
		return aadhaar_city;
	}
	public void setAadhaar_city(String aadhaar_city) {
		this.aadhaar_city = aadhaar_city;
	}
	public String getAadhaar_address1() {
		return aadhaar_address1;
	}
	public void setAadhaar_address1(String aadhaar_address1) {
		this.aadhaar_address1 = aadhaar_address1;
	}
	public String getIs_alternate_data_valid() {
		return is_alternate_data_valid;
	}
	public void setIs_alternate_data_valid(String is_alternate_data_valid) {
		this.is_alternate_data_valid = is_alternate_data_valid;
	}
	public String getIs_soft_approval_valid() {
		return is_soft_approval_valid;
	}
	public void setIs_soft_approval_valid(String is_soft_approval_valid) {
		this.is_soft_approval_valid = is_soft_approval_valid;
	}
	public String getIs_hard_approval_valid() {
		return is_hard_approval_valid;
	}
	public void setIs_hard_approval_valid(String is_hard_approval_valid) {
		this.is_hard_approval_valid = is_hard_approval_valid;
	}
	public String getHard_approval_decision() {
		return hard_approval_decision;
	}
	public void setHard_approval_decision(String hard_approval_decision) {
		this.hard_approval_decision = hard_approval_decision;
	}
	public Double getMax_eligible_emi() {
		return max_eligible_emi;
	}
	public void setMax_eligible_emi(Double max_eligible_emi) {
		this.max_eligible_emi = max_eligible_emi;
	}
	public boolean getKyc_flag() {
		return Kyc_flag;
	}
	public void setKyc_flag(boolean kyc_flag) {
		Kyc_flag = kyc_flag;
	}
	public String getDisbursment_description_2() {
		return disbursment_description_2;
	}
	public void setDisbursment_description_2(String disbursment_description_2) {
		this.disbursment_description_2 = disbursment_description_2;
	}
	public String getDisbursment_description_1() {
		return disbursment_description_1;
	}
	public void setDisbursment_description_1(String disbursment_description_1) {
		this.disbursment_description_1 = disbursment_description_1;
	}
	public Double getDisbursement_percentage() {
		return disbursement_percentage;
	}
	public void setDisbursement_percentage(Double disbursement_percentage) {
		this.disbursement_percentage = disbursement_percentage;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getDebit_start_date() {
		return debit_start_date;
	}
	public void setDebit_start_date(String debit_start_date) {
		this.debit_start_date = debit_start_date;
	}
	public String getDebit_end_date() {
		return debit_end_date;
	}
	public void setDebit_end_date(String debit_end_date) {
		this.debit_end_date = debit_end_date;
	}
	public String getCurrent_employmet_in_month() {
		return current_employmet_in_month;
	}
	public void setCurrent_employmet_in_month(String current_employmet_in_month) {
		this.current_employmet_in_month = current_employmet_in_month;
	}
	public String getCurrent_address_same_as_aadhar() {
		return current_address_same_as_aadhar;
	}
	public void setCurrent_address_same_as_aadhar(
			String current_address_same_as_aadhar) {
		this.current_address_same_as_aadhar = current_address_same_as_aadhar;
	}
	public String getCrm_id() {
		return crm_id;
	}
	public void setCrm_id(String crm_id) {
		this.crm_id = crm_id;
	}
	public String getCorporate_code() {
		return corporate_code;
	}
	public void setCorporate_code(String corporate_code) {
		this.corporate_code = corporate_code;
	}
	public String getAdditional_field_5() {
		return additional_field_5;
	}
	public void setAdditional_field_5(String additional_field_5) {
		this.additional_field_5 = additional_field_5;
	}
	public String getAdditional_field_4() {
		return additional_field_4;
	}
	public void setAdditional_field_4(String additional_field_4) {
		this.additional_field_4 = additional_field_4;
	}
	public String getAdditional_field_3() {
		return additional_field_3;
	}
	public void setAdditional_field_3(String additional_field_3) {
		this.additional_field_3 = additional_field_3;
	}
	public String getAdditional_field_2() {
		return additional_field_2;
	}
	public void setAdditional_field_2(String additional_field_2) {
		this.additional_field_2 = additional_field_2;
	}
	public Double getAdditional_field_1() {
		return additional_field_1;
	}
	public void setAdditional_field_1(Double additional_field_1) {
		this.additional_field_1 = additional_field_1;
	}
	
}
