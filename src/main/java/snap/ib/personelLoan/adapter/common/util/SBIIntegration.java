package snap.ib.personelLoan.adapter.common.util;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import snap.ib.personelLoan.adapter.common.CommonAdapterResource;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SBIIntegration {
	
	private static Logger logger = LoggerFactory.getLogger(CommonAdapterResource.class);
	private static String secretKey = "Ë†ÃŽ+9Ã² iÃ³Â´Â¤dbÃŠUÃ ";
	private static final ReadProperty readProperties=  ReadProperty.getInstance();
	public SBIIntegration() {
		secretKey = null;
	}

	public static String decryptFile(String s) {
		String decdata = null;
		byte[] key = (byte[]) null;
		try {
			key = returnbyte(secretKey);
		} catch (IOException e) {
			e.printStackTrace();
		}catch (NullPointerException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "IBMJCE");
			byte[] keyBytes = new byte[16];

			int len = key.length;
			if (len > keyBytes.length)
				len = keyBytes.length;
			System.arraycopy(key, 0, keyBytes, 0, len);
			SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
			IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
			cipher.init(2, keySpec, ivSpec);
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] results = decoder.decodeBuffer(s);
			byte[] ciphertext = cipher.doFinal(results);
			decdata = new String(ciphertext, "UTF-8");

		} catch (NullPointerException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return decdata;
	}
	

	public static String encryptFile(String s) {
		
		byte[] key = (byte[]) null;
		try {
			key = returnbyte(secretKey);
		} catch (IOException e) {
			e.printStackTrace();
		}catch (NullPointerException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}

		String encData = null;
		try {

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "IBMJCE");
			byte[] keyBytes = new byte[16];

			int len = key.length;
			if (len > keyBytes.length)
				len = keyBytes.length;
			System.arraycopy(key, 0, keyBytes, 0, len);
			SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
			IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
			cipher.init(1, keySpec, ivSpec);
			byte[] results = cipher.doFinal(s.getBytes("UTF-8"));
			BASE64Encoder encoder = new BASE64Encoder();
			encData = encoder.encode(results);
		}catch (NullPointerException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}

		return encData;
	}

	public static byte[] returnbyte(String path) throws IOException {
		
		FileInputStream fileinputstream =null;
		byte[] abyte=null;
		try{
			
		String keyPath=readProperties.getPropertyValue("KEY_PATH").trim();
	    fileinputstream = new FileInputStream(keyPath);
		 abyte = new byte[fileinputstream.available()];
		 
		fileinputstream.read(abyte);
		
		}catch(NullPointerException e){
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			fileinputstream.close();
		}
		return abyte;
	}

	public void setSecretKey(String s) {
		secretKey = s;
	}

	 public static String createChecksum(String s) throws Exception
	  {
	    String result = "";
	    byte[] b = (byte[])null;
	    InputStream fis = new ByteArrayInputStream(s.getBytes());
	    byte[] buffer = new byte['Ѐ'];
	    MessageDigest complete = MessageDigest.getInstance("MD5");
	    int numRead;
	    do {
	      numRead = fis.read(buffer);
	      if (numRead > 0) {
	        complete.update(buffer, 0, numRead);
	      }
	    } while (numRead != -1);
	    fis.close();
	    b = complete.digest();
	    for (int i = 0; i < b.length; i++) {
	      result = result + Integer.toString((b[i] & 0xFF) + 256, 16).substring(1);
	    }
	    return result;
	  }
}