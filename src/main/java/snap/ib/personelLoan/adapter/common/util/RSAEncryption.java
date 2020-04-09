package snap.ib.personelLoan.adapter.common.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.zip.CRC32;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class RSAEncryption {

	public PublicKey readKeyFromFile(String keyFileName) throws IOException {
		InputStream in = new FileInputStream(keyFileName);
		ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(
				in));
		try {
			BigInteger m = (BigInteger) oin.readObject();
			BigInteger e = (BigInteger) oin.readObject();
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
			KeyFactory fact = KeyFactory.getInstance("RSA");
			PublicKey pubKey = fact.generatePublic(keySpec);
			return pubKey;
		} catch (Exception e) {
			throw new RuntimeException("Spurious serialisation error", e);
		} finally {
			oin.close();	
	   }
	}

	public String rsaEncrypt(byte[] data, String publicKeyFile) {
		byte[] cipherData = null;
		String encData="";
		try {
			PublicKey pubKey = readKeyFromFile(publicKeyFile);
			System.out.println("pubKey>>>"+pubKey);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			cipherData = cipher.doFinal(data);
			encData=new String(Base64.encodeBase64(cipherData));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encData;
	}

	public String createCheckSum(String strForchecksum) {
		String checksum = "";
		CRC32 crc32 = new CRC32();
		try {
			strForchecksum = strForchecksum + "|" + "KMBANK";
			crc32.update(strForchecksum.getBytes());
			checksum = String.valueOf(crc32.getValue());
			strForchecksum = strForchecksum + "|" + checksum;
			strForchecksum = strForchecksum.replace("KMBANK|", "");

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return strForchecksum;
	}

	public boolean validateCheckSum(String strForchecksum,String chkSum) {
		String checksum = "";
		boolean isMatched=false;
		CRC32 crc32 = new CRC32();
		
		try {
			strForchecksum = strForchecksum + "|" + "KMBANK";
			crc32.update(strForchecksum.getBytes());
			checksum = String.valueOf(crc32.getValue());
			strForchecksum = strForchecksum + "|" + checksum;
			strForchecksum = strForchecksum.replace("KMBANK|", "");

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
		if (checksum.equals(chkSum)) {
			isMatched=true;
		}
		return isMatched;
	}
}
