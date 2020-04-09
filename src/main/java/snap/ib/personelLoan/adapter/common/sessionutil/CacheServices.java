package snap.ib.personelLoan.adapter.common.sessionutil;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import snap.ib.personelLoan.adapter.common.util.ReadProperty;

public class CacheServices {

	static Logger logger = LoggerFactory.getLogger(CacheServices.class.getName());
	
	public static String  processCacheService(String url,String sessionid) throws Exception{
			
		    String sUrl = "",inputLine = "";
		    final ReadProperty readProperties=  ReadProperty.getInstance();
		
		    sUrl=readProperties.getPropertyValue("DYNACACHEURL").trim();
			HttpURLConnection conn = null;
			DataOutputStream wr = null;
			BufferedReader in = null;
			StringBuffer response = new StringBuffer();
	
			try {
				sUrl = sUrl+url;
				URL oracle = new URL(sUrl);		      
				conn =   (HttpURLConnection) oracle.openConnection();
				conn.setDoOutput(true);
				conn.setAllowUserInteraction(false);
	            conn.setInstanceFollowRedirects(true);
	            conn.setRequestMethod("POST");
	            conn.setRequestProperty("Content-Type", "text/xml");
	            conn.setConnectTimeout(30000);
	            conn.setReadTimeout(30000);
	            conn.setRequestProperty("Connection", "Keep-Alive");
	            if(!sessionid.equals("")){
	            	conn.setRequestProperty("Cookie", "JSESSIONID="+sessionid);
	            }
            	wr = new DataOutputStream(conn.getOutputStream());
	            wr.flush();
	            wr.close();
	            int responseCode = conn.getResponseCode();
	            logger.info("response code :"+responseCode);
	            String jsonString = conn.getResponseMessage();
	            logger.info("response message :"+jsonString);
	             in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	             	response = new StringBuffer();
	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response.toString();
	}
	
	public static JSONObject parseString(String param) {
		JSONObject jsonObject = null;
		JSONParser jsonParser = null;
		try {
			jsonParser = new JSONParser();
			if (param != null) {
				jsonObject = (JSONObject) jsonParser.parse(param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			jsonParser = null;
		}
		return jsonObject;
	}
	
	public boolean isSessionValid(String sessionKey) throws Exception{
		
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	return false;
	}
}
