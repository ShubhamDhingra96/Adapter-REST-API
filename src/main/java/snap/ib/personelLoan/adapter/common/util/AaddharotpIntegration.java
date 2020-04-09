package snap.ib.personelLoan.adapter.common.util;

 

import java.io.IOException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;

 



public class AaddharotpIntegration {
	private static final ReadProperty readProperties=  ReadProperty.getInstance();
	

	private static final String ALGORITHM = "AES";
	
	private static final String secretKey = readProperties.getPropertyValue("OTP_ENC_KEY") == null?"India-bulls-0007":readProperties.getPropertyValue("OTP_ENC_KEY");
	
	public static String GetEncryptedString(String text1) throws IOException
	{
		
		byte[] SALT1 = secretKey.getBytes(); 
		
		  if (text1 == null ){
			  return "string or Key is blank";
          }
          Key key = new SecretKeySpec(SALT1, ALGORITHM);
          try {
              Cipher cipher = Cipher.getInstance(ALGORITHM);
              cipher.init(Cipher.ENCRYPT_MODE, key);
              byte[] encodedValue = cipher.doFinal(text1.getBytes());                  
              return  Base64.getEncoder().encodeToString(encodedValue);               
              }catch (Exception e){
            	  				  //System.out.println("Exception is: "+ e);
            	  				  return "fail";
            	  				  }            
      }
	

	public static String GetDecryptedString(String encString) throws IOException
	{	
		  if (encString == null){
            return "string or Key is blank";
        }
		  byte[] SALT1 = secretKey.getBytes();
        Key key = new SecretKeySpec(SALT1, ALGORITHM); 
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedValue = Base64.getDecoder().decode(encString);
            byte[] decValue = cipher.doFinal(decodedValue);
            return new String(decValue);
        } catch (Exception e) {
            //System.out.println("Exception is: "+ e);
            return "fail";
        }        
    }

}