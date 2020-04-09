package snap.ib.personelLoan.adapter.common.util;

import org.apache.commons.codec.binary.Base64;

public class FileValidator {
	
	

	public boolean isValidBase64String(String base64String) {
		boolean isValidBase64Data = false;
		try {

			isValidBase64Data = Base64.isBase64(base64String);
//			logger.info("isValidBase64Data=" + isValidBase64Data);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return isValidBase64Data;
	}
	
	public static byte[] decodeImage(String imageDataString) {
		return Base64.decodeBase64(imageDataString);
		}

}
