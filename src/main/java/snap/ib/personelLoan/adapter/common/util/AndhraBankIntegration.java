package snap.ib.personelLoan.adapter.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import sun.misc.BASE64Decoder;

//import com.infosys.feba.utils.StringByteConverter;


public class AndhraBankIntegration  {
	
	public static void main(String[] args){
		//AndhraBankIntegration.encrypt("");
		//AndhraBankIntegration.decrypt("sdfds");
		/*https://uat.onlineandhrabank.net.in/corp/sgonHttpHandler.aspx?encData=pI+SlS6vekayaByKVdzWHF7rwYIK1ixeltxSzp8KzXwzWfy8LNtHB/Bx6bVM2GiB3pa+TBqMfwSD
V2OfOVTgDHzBNrkijgM8jFrOo0swwr6En3gdoPhpZQHEkAgu51OYWqFz8I8BPNLTCo5LRtQGotM4
H+fPEPlvlQd/AGW6nUO8MHH2swtstd/ydXdSPLwre2JOIdXWJteUpQJbIobh22t34Vg1d7U5MSMM
0czg8zT7F4sJwpNf58oDkr9Nyu1Kh3FK7vmEpCAY3DDVkdMVlXqObyZXSw0gw503iszi/NNuU+s2
ciWjZQTrP+6f5+GRl7YjHcu9k5UG/jTUv/Xo9hsSQqBWlbAoZj0BGPp/1bYy/W2w+UC7OW7OaxbX
H5348rgk95/82yWHk2Oox/eXZkDx4wd3Ku59PuUfODij3wX96FN8vKYJ09KspzPCjpy8k4IQEj2P
M/DzZInJbn+cDc3YByss8646SUXoQrpI68ge7U/7gKgMwkvINx5zZp/9fwn7hAzZ9FmWeqJZYTAg
ao1LVLK/nd8XiDfdaB1y0ZQj0DoTZXdvM7+akzNnjGVLxKzq/wTwweSS4MgS9Z2GSw==&PID=000001923948*/

	}
	public static String encrypt(String checkSumToBeGenerated){
		String key = "ANDB123";
		String sCheckSum = "";
		String finalString = "";
		String pipe="|";
		 //checkSumToBeGenerated = "Action.ShoppingMall.Login.Init=Y&BankId=011&USER_LANG_ID=001&AppType=corporate&UserType=1&CG=Y&MD=P&PID=000001923948&DHANIID=20000004&DHANIREF=TEST00270081801&CUSTOMERNAME=TESTCLIENT&BANKNAME=ANDHRABANK&ACCOUNTNUMBER=117910100036871&LOANAMOUNT=1000000.00&EMIAMOUNT=10000.00&DEBITFREQUENCY=M&DEBITSTARTDATE=20181201&DEBITENDDATE=20401201&CRN=INR&WEBTOKEN=DUMMYTOKEN&RU=https://www.andhrabank.in";
		//	String valueTobeEncoded="Action.ShoppingMall.Login.Init=Y&BankId=011&USER_LANG_ID=001&AppType=corporate&UserType=1&CG=Y&MD=P&PID=000000012062&ITC=20000004&PRN=TEST00270081801&AMT=2.00&CRN=INR&RU=https://uat.billdesk.com/pgidsk/pgijsp/bankCorpResponse.jsp&STATFLG=H";

		MessageDigest msgd = null;
		try {
			msgd = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			msgd.update(checkSumToBeGenerated.getBytes("ASCII"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte byteData[] = msgd.digest();

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));

		}
		sCheckSum = sb.toString();
		System.out.println("checkSumToBeGenerated::"+checkSumToBeGenerated);
		finalString = checkSumToBeGenerated + pipe + "checkSum=" + sCheckSum;
		//finalString = checkSumToBeGenerated;
		//System.out.println("sCheckSum::::"+finalString);


		byte[] pwdBytes = null;
		try {
			pwdBytes = key.getBytes("UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] keyBytes = new byte[0x10];
		int len = pwdBytes.length;
		if (len > keyBytes.length) {
			len = keyBytes.length;
		}
		System.arraycopy(pwdBytes, 0, keyBytes, 0, len);

		byte[] sessionKey = keyBytes;
		byte[] iv = keyBytes;
		byte[] plaintext = null;
		try {
			plaintext = finalString.getBytes("UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("Output plan text what we are encrypting  :  " + finalString);
		try {
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(sessionKey, "AES"), new IvParameterSpec(iv));
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       
		catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		byte[] ciphertext = null;
		try {
			ciphertext = cipher.doFinal(plaintext);
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		String encr_text = new sun.misc.BASE64Encoder().encode(ciphertext);
		//System.out.println("Output Encrypted data :  " + encr_text);
		return encr_text;

	}
	public static String decrypt(String encryptedString1){

		//String encryptedString2 = "wjIR1mJe8UzPHhQgmj4oDqrTkeYhP2cHpHbtUvQDeuuhfSmtELbw9q%2FSCl%2BUy289QKbnSjbe497xcVm%2B0IICdxvmcy0UwpgJAvCKnGRpTzvaBWAuvqHknOfUdxKAn8puHivqBxMzwZh3qnjbZoj5N4nu7zBXkpAZiFadR7XQ8%2F0%3D";
		//encryptedString1="";
		 //encryptedString1 = "wjIR1mJe8UzPHhQgmj4oDjet4YEnP9mncfoesooi UJokuPrsDBCDWQeefmn4fRfTFtvIBdAbmazErV45n2vVPh3U30yrVJucx00YGGU92T2US3NVWOJNixMTFCDok/m29lUzsJSdQ03oGSbtdaWtEkQ0o8Wfd4THqfexPX8dvPWkz7OYhFRyNlZKmtLx7sl";
		 //encryptedString1 = "26encData%3df7VFCIf3LAdkewLqSf2AuUZlqgRiNN%252FRkUCxI81qIewtj%252Btvvx14YI9gcfHFcAmTv2MGtA0R9WzBI9KkmBIVHwerVC06ignrO7lHpxr2583tPSmdTpGjZmnXS3crPRrcZFmU%252Bm8EjFWWCf%252F8D69GKSJlT5RuW7vPd%252BDX3iKLpF%252FEigadxYYqe2DPel6StWTqfz6BWNiQqhKxEPEGwO01Ocu2qcptWY7Z3SSOEw31ZQ4bZvDpTkBmTfbbkAk9YR2bkwWCQAHqU6PRQ%252BQcFrXi%252F%252FwPZ9F6od9wLBNsg8nuBbOnjgFO073%252BVdYktm%252BQio5%252FsFxCDnQmCt%252Bf6aXnnpwRNMFsWlUAlyee2DPtJ27NLeKR7nCaVU5Zosel%252BL1on3uSkT5ymC3TW2Xjyw4FP5mPdVY%252BQeiggJeOhs0Sd1L30RCN8GGzkusvpzzlpv31pyQjsRpfaKsYzzwbS2CScX0clT1gmknMxyr%252BGpQHapU8DO27KQ0%252BDseYvQKiI5689emkLP9XY6aTH%252BIjjEPggWNQgQ%253D%253D&umid=4061F8CA-7C5A-3C05-B800-7FE1D2B58E7A&auth=5a117aac19969cda63d2bc396e0fc3050c7b84d3-221918e87ed587b7b9fb23af54ed6f1a64a064d1";
		//	  "pI+SlS6vekayaByKVdzWHF7rwYIK1ixeltxSzp8KzXwzWfy8LNtHB/Bx6bVM2GiB3pa+TBqMfwSD\nV2OfOVTgDHzBNrkijgM8jFrOo0swwr6En3gdoPhpZQHEkAgu51OYUDO1JCKVGYP0dbUuDUqL3Nm0\nASHhBc+BfGjCDNjJ3DLRDVKI7hz+zRT3F0ZYgPnFoETCTPfhnDGg93EatFMm4kBH9rLPiQPT1Pvi\nAQ60AG/9xan6+jnpVs22QsKfXwimRN5rUVm1qWaEOT8kkrJpVQR5FMUrhWJ61PB1Wpflz1tVHrsD\nGD+khG1/Xhvw1mVHt6BeH6B4TjY1Ugi6fTJzovVjC5x+ZXvK/mIolC3ktuMSvgruR3vZuiCeu+0y\nxcmtVLQBEeFKWVUAq8tx8nGicQ==";
		//	encryptedString1 = encryptedString1.replace(' ', '+');
		//	System.out.println("encryptedString::::::::::" + encryptedString1);
		/*try {
			encryptedString1=	URLDecoder.decode(encryptedString1.toString(),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		String key = "ANDB123"; 						
		byte[] pwdBytes = null;
		Cipher cipher = null;
		byte[] original = null;
		String decyptedString = "";
		//System.out.println(key);
		String rslt = null;
		try {
			pwdBytes = key.getBytes("UTF8");

		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		//System.out.println(pwdBytes);
		byte[] keyBytes = new byte[0x10];
		int len = pwdBytes.length;
		//System.out.println(keyBytes);
		if (len > keyBytes.length) {
			len = keyBytes.length;
		}
		System.arraycopy(pwdBytes, 0, keyBytes, 0, len);
		byte[] sessionKey = keyBytes;
		byte[] iv = keyBytes;
		
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (NoSuchPaddingException e1) {
			e1.printStackTrace();
		}
		//System.out.println(cipher);
		try {
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(sessionKey,
					"AES"), new IvParameterSpec(iv));
		} catch (InvalidKeyException e1) {
			e1.printStackTrace();
		} catch (InvalidAlgorithmParameterException e1) {
			e1.printStackTrace();
		}

		try {
			original = cipher.doFinal(new BASE64Decoder().decodeBuffer(encryptedString1));
		} catch (IllegalBlockSizeException e1) {
			e1.printStackTrace();
		} catch (BadPaddingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			
			byte[] encryptedString = cipher.doFinal(new BASE64Decoder().decodeBuffer(encryptedString1));
            // Create a string from the byte array with "UTF-8" encoding
             rslt = new String(encryptedString, StandardCharsets.UTF_8);
			//decyptedString = StringByteConverter.convertByteArrayToString(original);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//System.out.println("decyptedString::::::::::" + rslt);
		return rslt;

	}

}
