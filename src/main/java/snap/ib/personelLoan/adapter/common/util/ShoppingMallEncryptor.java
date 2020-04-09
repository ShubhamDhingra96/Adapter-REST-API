package snap.ib.personelLoan.adapter.common.util;
 
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

public class ShoppingMallEncryptor {

		public static String encrypt(String masterKey, String stringToEncrypt) {
		
		String encryptedString = "";

		try {
			EncryptDecryptUtil util = new EncryptDecryptUtil(masterKey);
			encryptedString = util.encrypt(stringToEncrypt);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedString;
	}

	/*private static String decrypt(String masterKey, String encryptedString) {
		String decryptedString = "";
		try {
			EncryptDecryptUtil util = new EncryptDecryptUtil(masterKey);
			decryptedString = util.decrypt(encryptedString);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedString;
	}*/

}