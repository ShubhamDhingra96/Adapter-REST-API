package snap.ib.personelLoan.adapter.common.util;

import java.security.Key;
import java.security.MessageDigest;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.SimpleByteSource;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

public class SecureBuilderServletD {

	private static Logger logger = LoggerFactory.getLogger(SecureBuilderServletD.class);

	public static String encrypt(String encrandomKey, String Value,
			String salt, String ostype) throws Exception {
		byte[] abyte2 = null;
		String plainText = Value;
		byte s1[] = plainText.getBytes();
		String Key = generateEncKey(salt, encrandomKey, ostype);
		byte[] abyte1 = Key.getBytes();
		SecretKeySpec secretkeyspec = new SecretKeySpec(abyte1, "AES");
		SecretKeySpec secretkeyspec1 = secretkeyspec;
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(1, secretkeyspec1);
		abyte2 = cipher.doFinal(s1);
		String base64 = Base64.encodeBase64String(abyte2);
		// System.out.println("Encrypt data:"+base64);
		return base64;
	}

	public static String decrypt(String encrandomKey, String Value,
			String salt, String ostype) throws Exception {
		String result = "";
		String strKey = generateEncKey(salt, encrandomKey, ostype);
		SecretKeySpec skeySpec = new SecretKeySpec(strKey.getBytes(), "AES");
		Key secretKey = skeySpec;
		Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		aesCipher.init(2, secretKey);
		byte[] plaintext = (byte[]) null;
		//logger.info("in decrypt "+Value);
		byte[] base64 = Base64.decodeBase64(Value);
		plaintext = aesCipher.doFinal(base64);
		result = new String(plaintext);
		return result;
	}

	public static String decryptRamdomKey(String Value) throws Exception {
		String strKey = "5bb0411b18b4a90e";
		String result = "";
		SecretKeySpec skeySpec = new SecretKeySpec(strKey.getBytes(), "AES");
		Key secretKey = skeySpec;
		Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		aesCipher.init(2, secretKey);
		byte[] plaintext = (byte[]) null;
		byte[] base64 = Base64.decodeBase64(Value);
		plaintext = aesCipher.doFinal(base64);
		result = new String(plaintext);
		return result;
	}

	public static String encryptRamdomKey(String Value) throws Exception {
		String Key = "5bb0411b18b4a90e";
		byte[] abyte2 = null;
		String plainText = Value;
		byte s1[] = plainText.getBytes();
		byte[] abyte1 = Key.getBytes();
		SecretKeySpec secretkeyspec = new SecretKeySpec(abyte1, "AES");

		SecretKeySpec secretkeyspec1 = secretkeyspec;

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

		cipher.init(1, secretkeyspec1);

		abyte2 = cipher.doFinal(s1);

		String base64 =   Base64.encodeBase64String(abyte2);
		// System.out.println("Encrypt data:"+base64);
		return base64;
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

	public static String generateEncKey(String input, String randomsaltkey,
			String OSType) {
		String enckey = "";
		try {
			randomsaltkey = decryptRamdomKey(randomsaltkey);
			logger.info("rankey----" + randomsaltkey);
			String keypartcert = getKeyGenerationString(randomsaltkey);
			input = keypartcert + "|" + input;
			logger.info("input  -- "+input);
			if ("IOS".equalsIgnoreCase(OSType)) {
				Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
				SecretKeySpec secret_key = new SecretKeySpec(
						randomsaltkey.getBytes(), "HmacSHA256");
				sha256_HMAC.init(secret_key);
				enckey = Base64.encodeBase64String(sha256_HMAC.doFinal(input
						.getBytes()));
				logger.info("ios gnke----" + enckey);
				enckey = enckey.substring(0, 4) + enckey.substring(8, 12)
				+ enckey.substring(16, 20) + enckey.substring(24, 28);
				logger.info("ios gnke 2----" + enckey);
			} else {
				Sha256Hash sha256Hash = new Sha256Hash((String) input,
						(new SimpleByteSource(randomsaltkey)).getBytes());
				enckey = sha256Hash.toHex();
				logger.info("gnke----" + enckey);
				enckey = enckey.substring(0, 4) + enckey.substring(16, 20)
				+ enckey.substring(32, 36) + enckey.substring(48, 52);
				logger.info("gnke 2----" + enckey);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return enckey;
	}

	public static String getKeyGenerationString(String randomsaltkey) {
		// Prod:
		// String certthumb = "d73d5601c94ffebd673d8551d5a156c720111ee9";
		// UAT:
		String certthumb = "7e2f8b054a76b935c33d980023b5ffc6616e0b0b";
		String keystr = "";
		try {
			int firstdigit = Integer.parseInt(randomsaltkey.substring(0, 1));
			int seconfdigit = Integer.parseInt(randomsaltkey.substring(1, 2));
			keystr = certthumb.substring(firstdigit, firstdigit + seconfdigit);
			logger.info("getKeyGenerationString Key " + keystr);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return keystr;

	}

	/**
	 * Returns a hexadecimal encoded SHA-256 hash for the input String.
	 * 
	 * @param data
	 * @return
	 */
	private static String getSHA256Hash(String data) {
		String hash = "";

		try {
			String secret = "89";
			String message = data;

			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(),
					"HmacSHA256");
			sha256_HMAC.init(secret_key);

			hash = Base64.encodeBase64String(sha256_HMAC.doFinal(message
					.getBytes()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return hash;

	}

	public static String char2hex(byte x) {
		char arr[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };

		char c[] = { arr[(x & 0xF0) >> 4], arr[x & 0x0F] };
		return (new String(c));
	}

	public static String checkSumSHA256(String plaintext) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256"); // step 2
			md.update(plaintext.getBytes("UTF-8")); // step 3
		} catch (Exception e) {
			md = null;
		}

		StringBuffer ls_sb = new StringBuffer();
		byte raw[] = md.digest(); // step 4
		for (int i = 0; i < raw.length; i++)
			ls_sb.append(char2hex(raw[i]));
		return ls_sb.toString(); // step 6
	}

	/**
	 * Use javax.xml.bind.DatatypeConverter class in JDK to convert byte array
	 * to a hexadecimal string. Note that this generates hexadecimal in upper
	 * case.
	 * 
	 * @param hash
	 * @return
	 */
	private static String bytesToHex(byte[] hash) {

		return DatatypeConverter.printHexBinary(hash);
	}

	public static String encryptServletEResponse(String encrandomKey, String Value,
			String salt, String ostype, String isEnc) throws Exception {

		/*if(!isEnc.equals("Y")){
			logger.info("encryption disabled");
			return Value;
		}*/

		byte[] abyte2 = null;
		String plainText = Value;
		byte s1[] = plainText.getBytes();
		String Key = generateEncKey(salt, encrandomKey, ostype);
		byte[] abyte1 = Key.getBytes();
		SecretKeySpec secretkeyspec = new SecretKeySpec(abyte1, "AES");

		SecretKeySpec secretkeyspec1 = secretkeyspec;

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

		cipher.init(1, secretkeyspec1);

		abyte2 = cipher.doFinal(s1);

		@SuppressWarnings("restriction")
		BASE64Encoder encoder = new BASE64Encoder();

		@SuppressWarnings("restriction")
		String base64 = encoder.encode(abyte2);
		// System.out.println("Encrypt data:"+base64);
		return base64;
	}
	public static void main(String[] args) {
		try {/*
			SecureBuilder sbuilder = new SecureBuilder();
			//String randomkey = SecureBuilder.getRandomKey(2);
			//System.out.println("Random Token " + randomkey);
			String encrandomkey = SecureBuilder.encryptRamdomKey("95");
			System.out.println("Enc Random Token " + encrandomkey);


			String encryption3 = sbuilder.encrypt(encrandomkey,"{\"mobileNumber\" :\"9032705440\"}", "TCLOTPGEN", "Android");
			System.out.println("Encryption String3 " + encryption3);

			String encryption4 = sbuilder.encrypt(encrandomkey,"{\"otp\" :\"987559\"}", "TCLVERIFYOTP|ebc2aeb7-740a-4ece-9933-5a90d4e2cad6", "Android");
			System.out.println("Encryption String4 " + encryption4);

			String encryption5 = sbuilder.encrypt(encrandomkey,"{\"firstName\":\"Vikash\",\"middleName\":\"Kumar\",\"lastName\":\"Singh\",\"email\":\"vikash.kumar@jocata.com\",\"leadId\":\"12345\"}", "TCLSAVCUSTDET|f933c479-fb1d-46df-82eb-8ecceabb6c67", "Android");
			System.out.println("Encryption String5 " + encryption5);

			String encryption6 = sbuilder.encrypt(encrandomkey,"{}", "TCLGETFINREQ|f933c479-fb1d-46df-82eb-8ecceabb6c67", "Android");
			System.out.println("Encryption String6 " + encryption6);

			String encryption7 = sbuilder.encrypt(encrandomkey,"{\"leadId\" : \"541212\",\"companyName\" : \"ICROSOF LOBAL\"}", "TCLGETCOMP|f933c479-fb1d-46df-82eb-8ecceabb6c67", "Android");
			System.out.println("Encryption String7 " + encryption7);

			String encryption8 = sbuilder.encrypt(encrandomkey,"{	\"leadId\" : \"465454\",	\"loanAmount\" : 50000,	\"tenure\" : 12,	\"employment\" : 1,	\"companyName\" : \"Jocata Financial Advisory & Technology Services Pvt. Ltd\",	\"officialEmail\" : \"saidulu.yerpula@jocata.com\",	\"monthlyNetSalary\" : 10000,	\"modeOfPayment\" : 1}", "TCLSAVEPROFDET|a48eb231-e001-433f-975f-fdddd3ca7543", "Android");

			System.out.println("Encryption String8 " + encryption8);

			String encryption9 = sbuilder.encrypt(encrandomkey,"{\"leadId\" : \"541212\",\"aadhaarNumber\" : \"238695158754\"}", "TCLKYCGENOTP|a48eb231-e001-433f-975f-fdddd3ca7543", "Android");
			System.out.println("Encryption String9 " + encryption9);


			String encryption10 = sbuilder.encrypt(encrandomkey,"{\"leadId\" : \"541212\",\"aadhaarOtp\" : \"845154\"}", "TCLKYCVEROTP|a48eb231-e001-433f-975f-fdddd3ca7543", "Android");
			System.out.println("Encryption String10 " + encryption10);

			String encryption11 = sbuilder.encrypt(encrandomkey,"{\"leadId\" : \"541212\"}", "TCLSERAREA|a48eb231-e001-433f-975f-fdddd3ca7543", "Android");
			System.out.println("Encryption String11 " + encryption11);

			String encryption12 = sbuilder.encrypt(encrandomkey,"{	\"leadId\" : \"45154\",	\"firstName\" : \"Saidulu\",	\"middleName\" : \"\",	\"lastName\" : \"Yerpula\",	\"gender\" : \"M\",	\"maritalStatus\" : \"M\",	\"address\" : \"10-4-38/31, Humayun Nager, Masab Tank, Hyderabad, Telangana 500028\",	\"currentAddressFlag\" : false,	\"currentAddress\" : \"5-54-854/24, Banjana Hills\",	\"state\" : \"Telangana\",	\"city\" : \"Hyderabad\",	\"pincode\" : \"500032\",	\"propertyStatus\" : \"2\"}", "TCLSAVEPERDET|084a4d6e-ef70-4fe7-aa7a-ad22c234ffc6", "Android");
			System.out.println("Encryption String12 " + encryption12);

			String encryption13 = sbuilder.encrypt(encrandomkey,"{\"leadId\" : \"45154\",\"panNumber\" : \"DSCPS0571K\"}", "TCLVERIFYPAN|a48eb231-e001-433f-975f-fdddd3ca7543", "Android");
			System.out.println("Encryption String13 " + encryption13);

			String encryption14 = sbuilder.encrypt(encrandomkey,"{\"leadId\" : \"45154\",\"panNumber\" : \"DSCPS0571K\"}", "TCLSAVEPAN|a48eb231-e001-433f-975f-fdddd3ca7543", "Android");
			System.out.println("Encryption String14 " + encryption14);


			//String decryptRandomKey = sbuilder.decryptRamdomKey(encryption);
			//System.out.println("decryptRandomKey====" + decryptRandomKey);
			//String decryption = sbuilder.decrypt("gWn2fKLTI5ICXR9pp2Wz0g==", "UOOf/J6d7KyYuZi81y3bXJT3jOQVVxl54p+tJAVmrTvxI1EvNw/fRFyvld0F9d5JD79FKK02WCWv\r\nQwH6oOdgsNNDSM0fHdHFIW9zhxL/b2Y=","SBFCOTPGEN|3c95b5f7-9dc4-4802-b376-79913831d8da", "Android");
			//System.out.println("Decryption String " + decryption);
			String decryption = sbuilder.decrypt("pRmOLRDHqsPN6SG88q4kDQ==", "t9eJvvBG7aAIej6DAbNwW0CiFrSjhhC7h5MisG+BFw3kYRrVXLw8tkv8z3WJWjlnBRosfaqJRvAR1GEO99sbBhP+483u5P5xxwNpb/UAeCqC8omfxWRC/+Nr19ieLZbi","TCLSAVCUSTDET|f933c479-fb1d-46df-82eb-8ecceabb6c67", "Android");
			System.out.println("Decryption String " + decryption);

			//System.out.println("--"
			//		+ sbuilder.generateEncKey("Amit","SSOLOGIN", "ios"));
			// System.out.println("sha256 ios "+SecureBuilder.getSHA256Hash("NISHIT PATEL"));
		 */} catch (Exception ex) {
			 ex.printStackTrace();
		 }
	}

}
