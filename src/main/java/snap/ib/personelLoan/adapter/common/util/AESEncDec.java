package snap.ib.personelLoan.adapter.common.util;

import java.net.URLDecoder;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import snap.ib.personelLoan.adapter.common.CommonAdapterResource;
import snap.ib.personelLoan.adapter.common.sessionutil.CacheServices;
import snap.ib.personelLoan.adapter.common.sessionutil.CustomSessionServiceImpl;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class AESEncDec {

	private static Logger logger = LoggerFactory.getLogger(CommonAdapterResource.class);
	static Cipher cipher = null;

	public static String generateRandomKey(String deviceId, String sessionkey, String isEnc) throws Exception {

		logger.info("inside generateRandomKey method");
		String encKey = "";
		try {
			if (isEnc.equals("Y")) {
				logger.info("encryption enabled");
				String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionkey, deviceId);
				org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);

				if (cachejsonObject != null) {
					encKey = cachejsonObject.get("enckey") == null ? "" : cachejsonObject.get("enckey").toString();
					return encKey;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encKey;
	}

	public String encrypt(String Key, String Value, String isEnc) {
		String base64 = "";
		logger.info("inside encrypt method");
		try {
			if (!isEnc.equals("Y")) {
				logger.info("encryption disabled");
				return Value;
			}
			String initVector = "";
			if (Key.indexOf("|") != -1) {
				String keysplit[] = Key.split("\\|");
				Key = keysplit[0];
				initVector = keysplit[1];
			}
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(Key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] abyte2 = cipher.doFinal(Value.getBytes());
			BASE64Encoder encoder = new BASE64Encoder();
			base64 = encoder.encode(abyte2);
			base64 = base64.replace("\n", "").replace("\r", "");
			// System.out.println("Encrypt data:" + base64);

			return base64;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public String decrypt(String Key, String encrypted, String isEnc) {
		String initVector = "";
		logger.info("inside decrypt method");
		try {
			if (!isEnc.equals("Y")) {
				logger.info("encryption disabled");
				return encrypted;
			}
			if (Key.indexOf("|") != -1) {
				String keysplit[] = Key.split("\\|");
				Key = keysplit[0];
				initVector = keysplit[1];
			}
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(Key.getBytes("UTF-8"), "AES");
			encrypted = URLDecoder.decode(encrypted, "UTF-8");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

			return new String(original);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static String createChecksum(String inputString, String isEnc) {
		logger.info("inside createChecksum method");
		StringBuffer sb = new StringBuffer();
		try {
			if (!isEnc.equals("Y")) {
				logger.info("encryption disabled");
				return null;
			}
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(inputString.getBytes());
			byte[] digest = md.digest();

			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public boolean validateChecksum(String inputString, String checksum, String isEnc) {

		logger.info("inside validateChecksum method");
		boolean flag = false;
		try {
			if (!isEnc.equals("Y")) {
				logger.info("encryption disabled");
				return true;
			}
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(inputString.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}

			if (sb.toString().equalsIgnoreCase(checksum)) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
