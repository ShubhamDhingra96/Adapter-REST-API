package snap.ib.personelLoan.adapter.common.response;

import java.math.BigInteger;
import java.security.MessageDigest;

public class GenerateOTPResponse {
	
	private String OTPToken;
	private boolean success;
	private String error_message;

	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	public String getOTPToken() {
		return OTPToken;
	}
	public void setOTPToken(String oTPToken) {
		OTPToken = oTPToken;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String generateOTP(String mobileNumber) {
		String otp="";

		try {

				MessageDigest md;
				String hashToken;
				String token = System.currentTimeMillis() + mobileNumber + java.util.UUID.randomUUID();

					md = MessageDigest.getInstance("SHA-512");
					md.update(token.getBytes());
					byte[] mdbytes = md.digest();
					
					StringBuffer hexString = new StringBuffer();
			    	for (int i=0;i<mdbytes.length;i++) {
			    	  hexString.append(Integer.toHexString(0xFF & mdbytes[i]));
			    	}
			    	hashToken = hexString.toString();
				
				String strHashTokenHex = (new BigInteger(hashToken, 16)).toString();
				int begingIndex = (int)(Math.random() * 100);
				otp = strHashTokenHex.substring(begingIndex,begingIndex+6);
				
				
		} catch (Exception e) {
			e.printStackTrace();
		}

		return otp;
	}
	
}
