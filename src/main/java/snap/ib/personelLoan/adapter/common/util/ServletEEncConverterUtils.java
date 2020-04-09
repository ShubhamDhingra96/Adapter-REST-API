package snap.ib.personelLoan.adapter.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.gson.Gson;

import snap.ib.personelLoan.adapter.common.request.AESKeyRequest;
import snap.ib.personelLoan.adapter.common.request.CommonRequest;
import snap.ib.personelLoan.adapter.common.request.ServeletEEncRequest;


public class ServletEEncConverterUtils {

	private static Logger logger = LoggerFactory.getLogger(ServletEEncConverterUtils.class);
	
	public static AESKeyRequest convert(ServeletEEncRequest sDERequest, String isEnc) {
		AESKeyRequest aesKeyRequest = null;
		String decryptedStr = "";
		String salt = "servletdE";
		String osType=  sDERequest.getDeviceOs();
		logger.info("osType [" + osType + "]" );
		try {
			if(osType.equalsIgnoreCase("IOS") || osType.equalsIgnoreCase("android")) {
				logger.info("Inside the OS type of IOS/android");
				decryptedStr = SecureBuilderServletD.decrypt(sDERequest.getReqToken(),sDERequest.getReqData(),salt, sDERequest.getDeviceOs());
			}else {
				CommonRequest commonRequest =  new CommonRequest();
				commonRequest.setDeviceType(osType);
				commonRequest.setOperationParam(salt);
				commonRequest.setRequestToken(sDERequest.getReqToken());
				commonRequest.setInputString(sDERequest.getReqData());
				decryptedStr = UtilClass.getDecryptedInputString(commonRequest, osType);
			}
			Gson gson = new Gson();
			if(!Strings.isNullOrEmpty(decryptedStr)) {
				aesKeyRequest = gson.fromJson(decryptedStr, AESKeyRequest.class);
				logger.info("Device Id [ "+aesKeyRequest.getDevice_id()+" ]");
			}
			logger.info("decryptedStr= [ " + decryptedStr+" ]");

		} catch(Exception ex) {
			ex.printStackTrace();
			logger.info("Exception [ "+ex+" ]");
		}
		return aesKeyRequest;
	}

	public static AESKeyRequest convertE(CommonRequest commonRequest, String isEnc) {
		AESKeyRequest aesKeyRequest = null;
		String decryptedStr = "";
		String salt = "servletdE";
		String osType =  commonRequest.getDeviceType();

		try {
			decryptedStr = UtilClass.getDecryptedInputString(commonRequest, osType);
			Gson gson = new Gson();
		
			if(!Strings.isNullOrEmpty(decryptedStr)) {
				aesKeyRequest = gson.fromJson(decryptedStr, AESKeyRequest.class);
				logger.info("Device Id [ "+aesKeyRequest.getDevice_id()+" ]");
			}
			logger.info("decryptedStr= [ " + decryptedStr+" ]");

		} catch(Exception ex) {
			ex.printStackTrace();
			logger.info("Exception [ "+ex+" ]");
		}
		return aesKeyRequest;
	}
}
