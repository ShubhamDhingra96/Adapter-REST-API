package snap.ib.personelLoan.adapter.common.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.RSAPrivateKeySpec;
import java.util.zip.CRC32;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import snap.ib.personelLoan.adapter.common.CommonAdapterResource;

public class RSADecryption {
	private static Logger logger = LoggerFactory.getLogger(CommonAdapterResource.class);

	public PrivateKey readKeyFromFile(String keyFileName) throws IOException {
		  InputStream in = new FileInputStream(keyFileName);
		  ObjectInputStream oin =  new ObjectInputStream(new BufferedInputStream(in));
		  try {
		    BigInteger m = (BigInteger) oin.readObject();
		    BigInteger e = (BigInteger) oin.readObject();
		    RSAPrivateKeySpec  keySpec = new RSAPrivateKeySpec(m, e);
		    KeyFactory fact = KeyFactory.getInstance("RSA");
		    PrivateKey privateKey = fact.generatePrivate(keySpec);
		    return privateKey;
		  } catch (Exception e) {
		    throw new RuntimeException("Spurious serialisation error", e);
		  } finally {
		    oin.close();
		  }
		}
	
	public String rsaDecrypt(byte[] data,String privateKeyFile1) throws UnsupportedEncodingException {
				
		String decryptedStr = "ERROR";
        try {
                PrivateKey privateKeyFile= readKeyFromFile(privateKeyFile1);
                Cipher dipher = Cipher.getInstance("RSA");
                dipher.init(Cipher.DECRYPT_MODE, privateKeyFile);
                decryptedStr= new String(dipher.doFinal(Base64.decodeBase64(data)));
        }catch(Exception e){
              e.printStackTrace();
        }
        return decryptedStr;
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
		logger.info("New checksum generated :- " + checksum);
		logger.info("Generated String with checksum:-" + strForchecksum);
		if (checksum.equals(chkSum)) {
			isMatched=true;
		}
		return isMatched;
	}
	
}