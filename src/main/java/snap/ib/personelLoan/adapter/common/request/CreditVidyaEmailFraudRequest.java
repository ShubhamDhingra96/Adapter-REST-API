package snap.ib.personelLoan.adapter.common.request;

import snap.ib.personelLoan.adapter.common.beans.ClientReference;

public class CreditVidyaEmailFraudRequest {
	
	private String middleName;
	private String dateOfBirth;
	private String lastName;
	private String pfNumber;
	private String educationalQualification;
	private String designation;
	private String maritalStatus;
	private String companyName;
	private String city;
	private String residenceAddressLine1;
	private String uniqueId;
	private String residenceAddressLine3;
	private String residenceAddressLine2;
	private String officemobileNumber;
	private String residencemobileNumber;
	private String email;
	private String income;
	private ClientReference clientReference;
	private String monthsInCurrentJob;
	private String mobileNumber;
	private String firstName;

    
	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPfNumber() {
		return pfNumber;
	}

	public void setPfNumber(String pfNumber) {
		this.pfNumber = pfNumber;
	}

	public String getEducationalQualification() {
		return educationalQualification;
	}

	public void setEducationalQualification(String educationalQualification) {
		this.educationalQualification = educationalQualification;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getResidenceAddressLine1() {
		return residenceAddressLine1;
	}

	public void setResidenceAddressLine1(String residenceAddressLine1) {
		this.residenceAddressLine1 = residenceAddressLine1;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getResidenceAddressLine3() {
		return residenceAddressLine3;
	}

	public void setResidenceAddressLine3(String residenceAddressLine3) {
		this.residenceAddressLine3 = residenceAddressLine3;
	}

	public String getResidenceAddressLine2() {
		return residenceAddressLine2;
	}

	public void setResidenceAddressLine2(String residenceAddressLine2) {
		this.residenceAddressLine2 = residenceAddressLine2;
	}

	public String getOfficemobileNumber() {
		return officemobileNumber;
	}

	public void setOfficemobileNumber(String officemobileNumber) {
		this.officemobileNumber = officemobileNumber;
	}

	public String getResidencemobileNumber() {
		return residencemobileNumber;
	}

	public void setResidencemobileNumber(String residencemobileNumber) {
		this.residencemobileNumber = residencemobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public ClientReference getClientReference() {
		return clientReference;
	}

	public void setClientReference(ClientReference clientReference) {
		this.clientReference = clientReference;
	}

	public String getMonthsInCurrentJob() {
		return monthsInCurrentJob;
	}

	public void setMonthsInCurrentJob(String monthsInCurrentJob) {
		this.monthsInCurrentJob = monthsInCurrentJob;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String toString() {
		return "CreditVidyaEmailFraudRequest [middleName = " + middleName + ", dateOfBirth = " + dateOfBirth + ", lastName = " + lastName
				+ ", pfNumber = " + pfNumber + ", educationalQualification = " + educationalQualification
				+ ", designation = " + designation + ", maritalStatus = " + maritalStatus + ", companyName = "
				+ companyName + ", city = " + city + ", residenceAddressLine1 = " + residenceAddressLine1
				+ ", uniqueId = " + uniqueId + ", residenceAddressLine3 = " + residenceAddressLine3
				+ ", residenceAddressLine2 = " + residenceAddressLine2 + ", officemobileNumber = " + officemobileNumber
				+ ", residencemobileNumber = " + residencemobileNumber + ", email = " + email + ", income = " + income
				+ ", clientReference = " + clientReference + ", monthsInCurrentJob = " + monthsInCurrentJob
				+ ", mobileNumber = " + mobileNumber + ", firstName = " + firstName + "]";
	}
}