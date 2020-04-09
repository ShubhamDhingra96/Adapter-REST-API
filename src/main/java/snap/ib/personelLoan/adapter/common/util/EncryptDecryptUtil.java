package snap.ib.personelLoan.adapter.common.util;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
@SuppressWarnings("restriction")
public class EncryptDecryptUtil {

	  private static Cipher c;
	  @SuppressWarnings("unused")
	  private static String sAlgorithm;
	  private static Key encryptDecryptKey;
	  
	  public EncryptDecryptUtil(String sMasterKey)throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException
	  {
	    String sKey = "";
	    String sAlgorithm = "AES";
	    sKey = sMasterKey.trim();
	    if (sKey.length() != 16) {
	      throw new InvalidKeyException("Master Key should be of 16 Characters");
	    }
	    byte[] sKeyBytes = sKey.getBytes();
	    encryptDecryptKey = new SecretKeySpec(sKeyBytes, sAlgorithm);
	    c = Cipher.getInstance(sAlgorithm);
	  }
	  
	public String encrypt(String stringToEncrypt)
	    throws InvalidKeyException, IllegalStateException, IllegalBlockSizeException, BadPaddingException
	  {
	    c.init(1, encryptDecryptKey);
	    BASE64Encoder base64encoder = new BASE64Encoder();
	    String sEncString = base64encoder.encode(c.doFinal(stringToEncrypt.getBytes()));
	    sEncString = sEncString.replaceAll("\n", "");
	    sEncString = sEncString.replaceAll("\r", "");
	    return sEncString;
	  }
	  
	public String decrypt(String stringToDecrypt)
	    throws InvalidKeyException, IOException, IllegalStateException, IllegalBlockSizeException, BadPaddingException
	  {
	    c.init(2, encryptDecryptKey);
	    BASE64Decoder base64decoder = new BASE64Decoder();
	    byte[] ciphertext = (byte[])null;
	    
	    byte[] cleartext = base64decoder.decodeBuffer(replaceSpecialChars(stringToDecrypt));
	    ciphertext = c.doFinal(cleartext);
	    return bytes2String(ciphertext);
	  }
	  
	  private String bytes2String(byte[] bytes)
	  {
	    StringBuffer stringBuffer = new StringBuffer();
	    for (int i = 0; i < bytes.length; i++) {
	      stringBuffer.append((char)bytes[i]);
	    }
	    return stringBuffer.toString();
	  }
	  
	  private String replaceSpecialChars(String str)
	  {
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < str.length(); i++) {
	      if (str.charAt(i) == ' ') {
	        sb.append('+');
	      } else {
	        sb.append(str.charAt(i));
	      }
	    }
	    return sb.toString();
	  }

}
