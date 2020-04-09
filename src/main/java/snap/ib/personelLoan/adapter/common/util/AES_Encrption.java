package snap.ib.personelLoan.adapter.common.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import snap.ib.personelLoan.adapter.common.CommonAdapterResource;

public class AES_Encrption 
{
	private static Logger logger = LoggerFactory.getLogger(CommonAdapterResource.class);
	static int keyLength=256;
	
	public static String encrypt(String input, String key)
	{
	  byte[] crypted = null;
	  try{
		  System.out.println(key.getBytes().toString());
	      SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
	      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	      cipher.init(Cipher.ENCRYPT_MODE, skey);
	      crypted = cipher.doFinal(input.getBytes());
	    }catch(Exception e){
	    	e.printStackTrace();
	    
	    }
	    return new String(Base64.encodeBase64(crypted));
	}
	
	public static String getRblencrypt(String input, String key)
	{
		
		logger.info("getRblencrypt  :::"+input+" Key::: "+key);
	  byte[] crypted = null;
	  
	  try{
		 // fixKeyLength();
	     // SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
		  byte[] randomByt = getKey(key, "AES");
		  SecretKeySpec skey = new SecretKeySpec(randomByt, "AES");
	      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	      
	      //cipher.init(Cipher.ENCRYPT_MODE, skey);
	      cipher.init(Cipher.ENCRYPT_MODE, skey);
	      crypted = cipher.doFinal(input.getBytes());
	    }catch(Exception e){
	    	e.printStackTrace();
	    	
	    	logger.info("getRblencrypt  :: Exception :::"+input+e.getMessage());
	    
	    }
	    return new String(Base64.encodeBase64(crypted));
	}
	public static String getRblDecrypt(String input, String key)
	{
		
		logger.info("getRblencrypt  :::"+input+" Key::: "+key);
	  byte[] crypted = null;
	  
	  try{
		 // fixKeyLength();
	     // SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
		  byte[] randomByt = getKey(key, "AES");
		  SecretKeySpec skey = new SecretKeySpec(randomByt, "AES");
	      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	      
	      //cipher.init(Cipher.ENCRYPT_MODE, skey);
	      cipher.init(Cipher.DECRYPT_MODE, skey);
	      crypted = cipher.doFinal(input.getBytes());
	    }catch(Exception e){
	    	e.printStackTrace();
	    	
	    	logger.info("getRblencrypt  :: Exception :::"+input+e.getMessage());
	    
	    }
	    return new String(Base64.encodeBase64(crypted));
	}
	public static void fixKeyLength() {
	    String errorString = "Failed manually overriding key-length permissions.";
	    int newMaxKeyLength;
	    try {
	        if ((newMaxKeyLength = Cipher.getMaxAllowedKeyLength("AES")) < 256) {
	            Class c = Class.forName("javax.crypto.CryptoAllPermissionCollection");
	            Constructor con = c.getDeclaredConstructor();
	            con.setAccessible(true);
	            Object allPermissionCollection = con.newInstance();
	            Field f = c.getDeclaredField("all_allowed");
	            f.setAccessible(true);
	            f.setBoolean(allPermissionCollection, true);

	            c = Class.forName("javax.crypto.CryptoPermissions");
	            con = c.getDeclaredConstructor();
	            con.setAccessible(true);
	            Object allPermissions = con.newInstance();
	            f = c.getDeclaredField("perms");
	            f.setAccessible(true);
	            ((Map)f.get(allPermissions)).put("*", allPermissionCollection);

	            c = Class.forName("javax.crypto.JceSecurityManager");
	            f = c.getDeclaredField("defaultPolicy");
	            f.setAccessible(true);
	            Field mf = Field.class.getDeclaredField("modifiers");
	            mf.setAccessible(true);
	            mf.setInt(f, f.getModifiers() & ~Modifier.FINAL);
	            f.set(null, allPermissions);

	            newMaxKeyLength = Cipher.getMaxAllowedKeyLength("AES");
	        }
	    } catch (Exception e) {
	        throw new RuntimeException(errorString, e);
	    }
	    if (newMaxKeyLength < 256)
	        throw new RuntimeException(errorString); // hack failed
	}
	
	
	private static byte[] getKey(String randomId, String cipherName)   throws Exception {

		
		logger.info("getKey  :  randomId:::"+randomId+"  ::cipherName"+cipherName);
      int keyBytes = 32;
      int len = randomId.length();

      String key = randomId;
      logger.info("getKey  :  len < keyBytes:::"+len +"::"+ keyBytes);
      if (len < keyBytes) {

            int repeatFactor = keyBytes / len;

            for (int i = 0; i < repeatFactor; i++) {

                  key = key + randomId;

            }
            logger.info("key :::"+key+"::key lenth::"+key.length());

      }
     key =  key.substring(0, keyBytes);
logger.info("key  ::"+key);

      return key.getBytes();



}

	
	public static String aesEncrypt(String input, String key)
	{
	  byte[] crypted = null;
	  StringBuffer sb = new StringBuffer();

	  try{
		  MessageDigest md = MessageDigest.getInstance("SHA-256");
	      md.update(input.getBytes());
	      byte byteData[] = md.digest();

	      for (int i = 0; i < byteData.length; i++) {
	      sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	      }
	      SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
	      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	      cipher.init(Cipher.ENCRYPT_MODE, skey);
	      crypted = cipher.doFinal(input.getBytes());
	    }catch(Exception e){
	    	e.printStackTrace();
	    
	    }
	    return new String(Base64.encodeBase64(crypted));
	}
	
	public static String getEncToken(String input, String key) {

		byte[] arr;
		String token = null;
		try {
			if (null != input && null != key) {
				
				arr = key.getBytes("UTF-8");
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				arr = md.digest(arr);
				byte[] arr1 = new byte[16];
				arr1=Arrays.copyOf(arr,16);
				SecretKeySpec skey = new SecretKeySpec(arr1, "AES");
				IvParameterSpec iv = new IvParameterSpec(arr1);
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				cipher.init(Cipher.ENCRYPT_MODE, skey, iv);
				byte[] byteArr = cipher.doFinal(input.getBytes("UTF-8"));
				token = new String(Base64.encodeBase64(byteArr, false));
			}

		} catch (Exception exception) {
			logger.info(exception.toString());
		}
		return token;
	}
	
	public static String getDecToken(String input, String key) {

		byte[] arr;
		String token = null;
		logger.info("input::: "+input+" Key::: "+key);
		try {
			if (null != input && null != key) {
				
				arr = key.getBytes("UTF-8");
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				arr = md.digest(arr);
				byte[] arr1 = new byte[16];
				arr1=Arrays.copyOf(arr,16);
				SecretKeySpec skey = new SecretKeySpec(arr1, "AES");
				IvParameterSpec iv = new IvParameterSpec(arr1);
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				cipher.init(Cipher.DECRYPT_MODE, skey, iv);
				token=new String(cipher.doFinal(Base64.decodeBase64(input)));
				logger.info("decrypted string ::: "+token);
				
			}

		} catch (Exception exception) {
			logger.info(exception.toString());
		}
		return token;
	}
	
	public static String decrypt(String input, String key){
	    
		byte[] output = null;
		byte[] arr;
		input=input.replaceAll("%2F", "/");
		input=input.replaceAll("%2B", "+");
		input=input.replaceAll("%3D", "=");
	    try{
			/*arr = key.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			arr = md.digest(arr);
			byte[] arr1 = new byte[16];
			arr1=Arrays.copyOf(arr,16);
			SecretKeySpec skey = new SecretKeySpec(arr1, "AES");*/
	      SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
	      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	      cipher.init(Cipher.DECRYPT_MODE, skey);
	      output = cipher.doFinal(Base64.decodeBase64(input));
	    }catch(Exception e){
	    	e.printStackTrace();
	    
	    }
	    return new String(output);
	}
	
	
	public static String checkSum(String input) throws NoSuchAlgorithmException
	{

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(input.getBytes());
 
        byte byteData[] = md.digest();
        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
    
    	return  hexString.toString();
	}
	
	public static long generateRandom(int length) {
		Random random = null;
		char[] digits = null;
		
		try{
			random = new Random();
			digits = new char[length];
			digits[0] = (char) (random.nextInt(9) + '1');
			for (int i = 1; i < length; i++) {
				digits[i] = (char) (random.nextInt(10) + '0');
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			random=null;
		}
		return Long.parseLong(new String(digits));
	}
	public static void main(String[] args) {
		String enc_data = AES_Encrption.encrypt("uyerwigryew", "R!@N2$Ih7U!AdC#L");
		System.out.println("enc_data:"+enc_data);
		String enc_data1 = AES_Encrption.decrypt(enc_data, "R!@N2$Ih7U!AdC#L");
	System.out.println(enc_data1);
	}
}