package snap.ib.personelLoan.adapter.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.security.Key;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Random;

import javax.crypto.Cipher;

import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import snap.ib.personelLoan.adapter.common.CommonAdapterResource;

public class NetbankingPayload {
	
	private static Logger logger = LoggerFactory.getLogger(CommonAdapterResource.class);
	static String email = "adityan@techepoch.com";
	static String server = "demo.perfios.com";
	static String axisserver = "uat-etendering.axisbank.co.in";
	public static String cid = "3343";
	public static String rid = "1651";
	public static String crn = "PL00000054";
	public static String amount = "1";
	public static String key = "axis";

	public static String vendor = "indiabullsVenture";
	public static String returnURL = "https://www.google.com";
	public static String checksum=cid+rid+crn+amount+key;

	
	
	static String privateKey = "-----BEGIN RSA PRIVATE KEY-----\r\n" + 
    		"MIIBOgIBAAJBAIJ6nvh1NYSm0I04NN3mr3Xq6Azn2RSyF48M/oL/VfDzl3xJqovW\r\n" + 
    		"aWxxvIKJbNHMOsH+eyijfR1RwJ/ZOw0m8xUCAwEAAQJAPQ8/B4xqMBqUbBHKg7Fp\r\n" + 
    		"mOgGEcwJxWqWJcZx8EGnKStZmap0CLvb2JlHmfFaB0fm9f4SlA0RIgODBAPA5LBN\r\n" + 
    		"wQIhAMd3hd/lm50uDMCQrAsbMr/fimZezibh8Xnqr7iloS2xAiEAp3WgcZmVpvP4\r\n" + 
    		"SQ3lIMzcsIhpebreqt8tDJtYoUssgKUCIGYdc2T3OB5Up+Sha+JXrzh5aXIVEPbo\r\n" + 
    		"7uWaVxFxgb1RAiAkLH+tKn0uZHNU7KB7VmWx6LXIKvrgtHcZyxD5PrDtVQIhAIau\r\n" + 
    		"bC52rMBbdthPaaJGmgZ2bU9Kfo16BcpBjhSoGcsA\r\n" + 
    		"-----END RSA PRIVATE KEY-----\r\n" + 
    		"";
	
	static final String DIGEST_ALGO = "SHA-1";	
	static final String privatekeys="axisbank12345678";
	static final String DIGEST_ALGO1 = "SHA-256";
	static final String ENCRYPTION_ALGO = "RSA/ECB/PKCS1Padding";
	static final String ENCRYP_ALGO = "AES/ECB/PKCS5Padding";
	static String applicationId = "dummyApplicationId";
	static String txnId = String.valueOf(generateRandom(12));
	static String perfiosTransactionId = "UPDATE ME PLEASE";
	static String format = "pdf";

	public static String payloadNetbanking = "<payload>\n" + "<vendorId>"
			+ vendor + "</vendorId>\n" + "<txnId>" + txnId + "</txnId>\n"
			+ "<emailId>#email#</emailId>\n"
			+ "<destination>netbankingFetch</destination>\n" + "<returnUrl>"
			+ returnURL + "</returnUrl>\n" + "</payload>";
	
	public static String axisPayloadNetbanking = "<payload>\n"
			
			+ "<CID>1162</CID>\n"
			+ "<RID>121</RID>\n" 
			+ "<CRN>" + crn + "</CRY>\n"
			+ "<AMT>" + amount + "</AMT>\n"
			+ "<VER>1.0</VER>\n"
			+ "<TYP>TEST</TYP>\n"
			+ "<CNY>INR</CNY>\n"
			+ "<RTU>" + "https://10.9.0.9/easypay/frontend/index.php/api/output" + "</RTU>\n"
			+ "<PPI>" + "test1|asd|test|29/04/2015|8097520469|rajas.vyas@tejora.com|1"+ "</PPI>\n"
			+ "<RE1></RE1>\n"
			+ "<RE2></RE2>\n"
			+ "<RE3></RE3>\n"
			+ "<RE4></RE4>\n"
			+ "<RE5></RE5>\n"
			+ "<CKS></CKS>\n"
			+ "</payload>";
	                                                                         															//Customer Name|Bank Name|EMI Amount|IFSC Code|Maximum Amount|FREQUENCY|ACCOUNT NUMBER|SCHEDULE DATE|EXPIRY DATE|AMOUNT
	//public static String axispayload="CID=3343&RID=1649&CRN=PL00000052&AMT=10&VER=1.0&TYP=TEST&CNY=INR&RTU=https://www.indiabulls.com&PPI=xyz|HDFC|10|HDFC0000501|10|Monthly|917020038533944|20-June-2017|20-May-2020|10&RE1=MN&RE2=&RE3=&RE4=&RE5=&CKS=";
	public static String axispayload="CID=3343&RID=1651&CRN=PL00000054&AMT=1&VER=1.0&TYP=TEST&CNY=INR&RTU=https://www.indiabulls.com&PPI=xyz|HDFC|1|HDFC0000501|1|Monthly|917020038533944|20-June-2017|20-May-2020|1&RE1=MN&RE2=&RE3=&RE4=&RE5=&CKS=";
	
	public static String payloadStatement = "<payload>\n"
			+ "<vendorId>"
			+ vendor
			+ "</vendorId>\n"
			+ "<txnId>"
			+ applicationId
			+ "</txnId>\n"
			+ "<emailId>#email#</emailId>\n<destination>statement</destination>\n"
			+ "<returnUrl>" + returnURL + "</returnUrl>\n" + "</payload>";

	public static String getTxnStatusPayload() {
		return "<payload>\n" + "<vendorId>" + vendor + "</vendorId>\n"
				+ "<txnId>" + applicationId + "</txnId>\n" + "</payload>";
	}

	public static String getRetrievePayload() {
		
		return "<payload>\n" + "<vendorId>" + vendor + "</vendorId>\n"
				+ "<txnId>" + applicationId + "</txnId>\n"
				+ "<perfiosTransactionId>" + perfiosTransactionId
				+ "</perfiosTransactionId>\n" + "<reportType>" + format
				+ "</reportType>\n" + "</payload>";
	}

	public static String getDeleteTransactionPayload() {
		return "<payload>\n" + "<vendorId>" + vendor + "</vendorId>\n"
				+ "<perfiosTransactionId>" + perfiosTransactionId
				+ "</perfiosTransactionId>\n" + "</payload>";
	}

	public static boolean getPayloadStatus() {
		boolean status=false;
		try {
			String myHTML = genericCreateHTML(NetbankingPayload.payloadNetbanking);
			createFile("netbanking", myHTML);
			status=true;
		} catch (Exception e) {
			status=false;
			e.printStackTrace();
		}
		return status;
	}

	public static void createFile1(String classification, String myHTML) {
		String filename = vendor + "/" + classification +".html";
		PrintWriter out=null;
		try {
			out = new PrintWriter(filename);
			out.print(myHTML);

			logger.info("Successfully created file " + filename);
		} catch (Exception e) {
			logger.error("Error while creating file " + filename);
			e.printStackTrace();
		}finally {
			out.close();
		}
	}
	
	public static void createFile(String classification, String myHTML) {
		String filename = vendor + "/" + classification + "_" + server
				+ ".html";
		PrintWriter out=null;
		try {
			out = new PrintWriter(filename);
			out.print(myHTML);

			logger.info("Successfully created file " + filename);
		} catch (Exception e) {
			logger.error("Error while creating file " + filename);
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	public static String genericCreateHTML(String payload) {
		return genericCreateHTML(payload, null);
	}

	public static String genericCreateAxisHTML(String payload) {
		logger.info("axis payload:\t"+payload);
		return genericCreateAxisHTML(payload, null);
	}
	private static String genericCreateHTML(String payload, String operation) {
		logger.info("genericCreateHTML method\t\t");
		String emailEncrypted = encrypt(email, ENCRYPTION_ALGO,
				buildPublicKey(privateKey));
		payload = payload.replaceAll("\n", "");
		payload = payload.replaceAll("#email#", emailEncrypted);

		String signature = getSignature(ENCRYPTION_ALGO, DIGEST_ALGO,
				buildPrivateKey(privateKey), payload);
		if (operation == null)

		operation = "start";
		String myHTML = "<html>\n"
				+ "	<body onload='document.autoform.submit();'>\n"
				+ "		<form name='autoform' method='post' action='https://"
				+ server + "/KuberaVault/insights/" + operation + "'>\n"
				+ "			<input type='hidden' name='payload' value='" + payload
				+ "'>\n" + "			<input type='hidden' name='signature' value='"
				+ signature + "'>\n" + "		</form>\n" + "	</body>\n"
				+ "</html>\n";
		return myHTML;
	}

	 public static String getSignature(String encryptAlgo, String digestAlgo, Key k, String xml) {
	        String dig = makeDigest(xml, digestAlgo);
	        return encrypt(dig, encryptAlgo, k);
	    }
	private static String genericCreateAxisHTML(String payload, String operation) {
				
		payload = payload.replaceAll("\n", "");
       
		String enc_data=null;
		try {
			enc_data = getAxisEncData(payload,checksum);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (operation == null)
			operation = "start";
		    String myHTML = "<html>\n"
				+ "<body>\n"
				+ "<form name='autoform' method='post' action='https://"
				+ axisserver + "/easypay2.0/frontend/api/payment'>\n"
				+ "<input type='hidden' name='i' value='" + enc_data + "'>\n"
				+"<input type='submit' value='submit' />\n"
				+ "</form>\n" + "</body>\n"
				+ "</html>\n";
		return myHTML;
	
	}
	public static String getAxisEncData(String payload,String checksum) throws NoSuchAlgorithmException {
		logger.info("checksum:\t"+checksum);
		checksum=AES_Encrption.checkSum(checksum);
		logger.info("hex checksum:\t"+checksum);
		payload=payload+checksum;
		logger.info(payload);
		String enc_data=AES_Encrption.encrypt(payload, privatekeys);
		logger.info("checksum:\t"+enc_data);
		return enc_data;
		
	}

	public static PrivateKey buildPrivateKey(String privateKeySerialized) {
		StringReader reader = new StringReader(privateKeySerialized);
		PrivateKey pKey = null;
		try {
			PEMReader pemReader = new PEMReader(reader);
			KeyPair keyPair = (KeyPair) pemReader.readObject();
			pKey = keyPair.getPrivate();
			pemReader.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
		return pKey;
	}

	private static PublicKey buildPublicKey(String privateKeySerialized) {
		StringReader reader = new StringReader(privateKeySerialized);
		PublicKey pKey = null;
		try {
			PEMReader pemReader = new PEMReader(reader);
			KeyPair keyPair = (KeyPair) pemReader.readObject();
			pKey = keyPair.getPublic();
			pemReader.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
		return pKey;
	}

	public static String makeDigest(String payload, String digestAlgo) {
		String strDigest = "";
		try {
			MessageDigest md = MessageDigest.getInstance(digestAlgo);
			md.update(payload.getBytes("UTF-8"));
			byte[] digest = md.digest();
			byte[] encoded = Hex.encode(digest);
			strDigest = new String(encoded);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return strDigest;
	}

	public static String encrypt(String raw, String encryptAlgo, Key k) {
		String strEncrypted = "";
		try {
			Cipher cipher = Cipher.getInstance(encryptAlgo);
			cipher.init(Cipher.ENCRYPT_MODE, k);
			byte[] encrypted = cipher.doFinal(raw.getBytes("UTF-8"));
			byte[] encoded = Hex.encode(encrypted);
			strEncrypted = new String(encoded);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return strEncrypted;
	}

	public static PrivateKey getprivateKey() {
		return NetbankingPayload.buildPrivateKey(privateKey);
	}

	public static String getPerfiosSignature(String payload) {
  	
		logger.info("Generated payload "+payload);
		String perfiosSignature=getUrlRedirectSignature(ENCRYPTION_ALGO, DIGEST_ALGO,
				buildPrivateKey(privateKey), payload);
		logger.info("perfiosSignature in getPerfiosSignature:\t"+perfiosSignature);
	    return perfiosSignature;
	}
	
	private static String getUrlRedirectSignature(String encryptionAlgo,
			String digestAlgo, PrivateKey buildPrivateKey,
			String payload) {
		String signature=getSignature(ENCRYPTION_ALGO, DIGEST_ALGO,
				buildPrivateKey(privateKey), payload);
		logger.info("signature in getUrlRedirectSignature:\t"+signature);
		return signature; 
	}

	public static String buildPerfiosPayload(String vendor,
			String txnId, String emailId, String destination,
			String returnUrl) {
		
		    logger.info("genericCreateHTML method\t\t");
		    String emailEncrypted = encrypt(emailId, ENCRYPTION_ALGO,
				buildPublicKey(privateKey));
		    
		    String perfiosURLRedirectPayload = "<payload>\n"
		            + "<vendorId>" + vendor + "</vendorId>\n" 
		    		+ "<txnId>"  + txnId + "</txnId>\n"
					+ "<emailId>#email#</emailId>\n"
					+ "<destination>"+ destination +"</destination>\n"
					+ "<returnUrl>"+ returnUrl + "</returnUrl>\n"
					+ "</payload>";
		    
		    logger.info("payload for netbanking : "+perfiosURLRedirectPayload);
		    perfiosURLRedirectPayload = perfiosURLRedirectPayload.replaceAll("\n", "");
		    perfiosURLRedirectPayload = perfiosURLRedirectPayload.replaceAll("#email#", emailEncrypted);
		    logger.info("perfiosURLRedirectPayload req "+perfiosURLRedirectPayload);
		    return perfiosURLRedirectPayload;
	}
	
	public static String generateRandom(int length) {

		Random random = new Random();
		char[] digits=null;
		try{
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
		return String.valueOf(Long.parseLong(new String(digits)));
	}
  
}
