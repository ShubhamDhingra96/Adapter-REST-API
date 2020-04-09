package snap.ib.personelLoan.adapter.common.util;

import java.security.DigestException;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import snap.ib.personelLoan.adapter.common.CommonAdapterResource;
import snap.ib.personelLoan.adapter.common.sessionutil.CacheServices;
import snap.ib.personelLoan.adapter.common.sessionutil.CustomSessionServiceImpl;

public class SecureBuilder {

	private static Logger logger = LoggerFactory.getLogger(CommonAdapterResource.class);
	static Cipher cipher = null;

	public static String generateRandomKey(String deviceId, String sessionkey,
			String isEnc) throws Exception {

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

	public static String encrypt(String encrandomKey, String Value,
			String salt, String ostype,String isEnc) throws Exception {
		
		try {
			 if(!isEnc.equals("Y")){
		            logger.info("encryption disabled");
		           	return Value;
		           }
			
			String strKey = generateEncKey(salt, encrandomKey, ostype);
			SecretKey key = new SecretKeySpec(Base64.decodeBase64(strKey), "AES");
			AlgorithmParameterSpec iv = new IvParameterSpec(Base64.decodeBase64(strKey));
			byte[] decodeBase64 = Value.getBytes();
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
//	        BASE64Encoder encoder = new BASE64Encoder();
//			result = encoder.encode(cipher.doFinal(decodeBase64));
	        Value = Base64.encodeBase64String(cipher.doFinal(decodeBase64));
			 
			return Value;
		} catch (Exception e) {
			e.printStackTrace();
		}
	return Value;
	}

	public static String decrypt(String encrandomKey, String Value,
			String salt, String ostype,String isEnc) throws Exception {
	    String decString="";
        try {
        	if(!isEnc.equals("Y")){
				logger.info("encryption disabled");
				decString=Value;
				return decString;
			}
        	
        	String Key = generateEncKey(salt, encrandomKey, ostype);
    		SecretKey key = new SecretKeySpec(Base64.decodeBase64(Key), "AES");
    		AlgorithmParameterSpec iv = new IvParameterSpec(Base64.decodeBase64(Key));
            byte[] decodeBase64 = Base64.decodeBase64(Value);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            decString= new String(cipher.doFinal(decodeBase64), "UTF-8");
           // logger.info("encryption enabled: "+decString);
            
		} catch (Exception e) {
			e.printStackTrace();
		}
        return decString;
	}
	
	public static String generateEncKey(String input, String randomsaltkey,
			String OSType) {
		String enckey = "";
		try {
			randomsaltkey = decryptRamdomKey(randomsaltkey);
			System.out.println("rankey----" + randomsaltkey);
			String keypartcert = getKeyGenerationString(randomsaltkey);
			input = keypartcert + "|" + input;
			System.out.println("input  -- "+input);
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(
					randomsaltkey.getBytes(), "HmacSHA256");
			sha256_HMAC.init(secret_key);
			enckey = Base64.encodeBase64String(sha256_HMAC.doFinal(input
					.getBytes()));
			enckey = enckey.substring(0, 4) + enckey.substring(8, 12)
					+ enckey.substring(16, 20) + enckey.substring(24, 28);
//			BASE64Encoder encoder = new BASE64Encoder();
//   		enckey = encoder.encode(enckey.getBytes());
			
			enckey = Base64.encodeBase64String(enckey.getBytes());
		    System.out.println("encrptyion key :"+enckey);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return enckey;
	}
	
	public static String getKeyGenerationString(String randomsaltkey) {

		String certthumb = "7e2f8b054a76b935c33d980023b5ffc6616e0b0b";
		String keystr = "";
		try {
			int firstdigit = Integer.parseInt(randomsaltkey.substring(0, 1));
			int seconfdigit = Integer.parseInt(randomsaltkey.substring(1, 2));
			keystr = certthumb.substring(firstdigit, firstdigit + seconfdigit);
			System.out.println("getKeyGenerationString Key " + keystr);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return keystr;
	}
	
	public static String decryptRamdomKey(String Value) throws Exception {
		String res = "";
		try
		{
           
		String Key = "NWJiMDQxMWIxOGI0YTkwZQ==";
		
		SecretKey key = new SecretKeySpec(Base64.decodeBase64(Key), "AES");
        
		AlgorithmParameterSpec iv = new IvParameterSpec(Base64.decodeBase64(Key));
        
        byte[] decodeBase64 = Base64.decodeBase64(Value);
        
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        res = new String(cipher.doFinal(decodeBase64), "UTF-8");
         
		}catch(Exception e){
			e.printStackTrace();
		}finally {
		 
		}
		return res;
	}
	
public static String encryptRamdomKey(String Value) throws Exception {
		
		String Key = "NWJiMDQxMWIxOGI0YTkwZQ==";
        SecretKey key = new SecretKeySpec(Base64.decodeBase64(Key), "AES");
		AlgorithmParameterSpec iv = new IvParameterSpec(Base64.decodeBase64(Key));
		byte[] decodeBase64 = Value.getBytes();
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
//        BASE64Encoder encoder = new BASE64Encoder();
//		String result = encoder.encode(cipher.doFinal(decodeBase64));
		String result = Base64.encodeBase64String(cipher.doFinal(decodeBase64));
		return result;
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

	public boolean validateChecksum(String inputString, String checksum,
			String isEnc) {
		
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
			logger.info("CheckSum created: "+sb.toString());
			if (sb.toString().equalsIgnoreCase(checksum)) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static byte[][] GenerateKeyAndIV(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password, MessageDigest md) {

	    int digestLength = md.getDigestLength();
	    int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;
	    byte[] generatedData = new byte[requiredLength];
	    int generatedLength = 0;

	    try {
	        md.reset();

	        while (generatedLength < keyLength + ivLength) {

	            if (generatedLength > 0)
	                md.update(generatedData, generatedLength - digestLength, digestLength);
	            md.update(password);
	            if (salt != null)
	                md.update(salt, 0, 8);
	            md.digest(generatedData, generatedLength, digestLength);

	            for (int i = 1; i < iterations; i++) {
	                md.update(generatedData, generatedLength, digestLength);
	                md.digest(generatedData, generatedLength, digestLength);
	            }

	            generatedLength += digestLength;
	        }

	        byte[][] result = new byte[2][];
	        result[0] = Arrays.copyOfRange(generatedData, 0, keyLength);
	        if (ivLength > 0)
	            result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);

	        return result;

	    } catch (DigestException e) {
	        throw new RuntimeException(e);

	    } finally {
	        Arrays.fill(generatedData, (byte)0);
	    }
	}

	public static String getEncrpytRandomKey(int len) throws Exception{
		try {
			
			String randomKey =  SecureBuilder.getRandomKey(len);
			return encryptRamdomKey(randomKey);
		} catch (Exception e) {
          e.printStackTrace();
		}
		
		return "";
	}
	
	public static String getRandomKey(int len) {

		StringBuffer AB = new StringBuffer("123456789");

		if (len > AB.length()) {
			return null;
		}

		StringBuilder sb = new StringBuilder(len);
		Random rnd = new Random();
		for (int i = 0; i < len; i++) {
			int char1 = rnd.nextInt(AB.length());
			sb.append(AB.charAt(char1));
			AB.deleteCharAt(char1);
		}

		return sb.toString();
	}
	public static void main(String[] args) throws Exception {
		
		//String encrandomkey = AESEncDec.encryptRamdomKey("2805818570c64023|8752cdafde9a2bc2");
		String encrandomkey = SecureBuilder.encryptRamdomKey("1a1c191a767a47ae|b8d23e3566ae4111");
		
		logger.info("encrandomkey= " + encrandomkey);
		//String getAppInfoRequestJson = AESEncDec.decrypt(encrandomkey,"FXHF3tUHdUIZ0IIqLcfi0Tty6HBGUVhSkJf8KJE/rnrQVsTL7KASnYRtGWGy6DCgB/X1kd/Zeou1meTJQqahbA==", "IDBILGN01","ios","Y");
		String getAppInfoRequestJson = SecureBuilder.decrypt(encrandomkey,"Nz4pMRPVjDLdYe6/H8Qufbk5CTI6/vDIgF/DouJw3qZfKYteTZQo/Lz8A8zSwTIFUCH9N5IP7eP5YJJ+1j6/YsVLePClbJi69G33VdxPGVN/5yRRARbbvpTJG+w8Teu4", "IDBILGN01",
				"ios","Y");
		
		System.out.println(getAppInfoRequestJson);
		
		String key="JKam8bLHZAHjV0DcotD2VQ==";
		String str="VikFXhRCCZcVwzn3jaNcjM8d5k7Jz1ApNyfezwJee8TMeAtkSXRTF+Oo8fhCkV/kSN07oJ8mYxpzEnETsi9GDsMrY11Bi3QToKaZs/Ppqmm0jK+jQNeVX2pYqlIEqVcFebGuhLlouXYu0anpiBYexb3CtLoW2Jl8wyxWgBPqEgE=";
		System.out.println(SecureBuilder.decrypt(encrandomkey,str, "IDBILGN01","ios","Y"));
	}
}
