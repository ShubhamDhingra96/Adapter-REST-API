package snap.ib.personelLoan.adapter.common.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class OTPGeneration {

	public static String generateOTP(String mobileNumber) {
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
