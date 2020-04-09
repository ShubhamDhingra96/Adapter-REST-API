package snap.ib.personelLoan.adapter.common.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonToList {
	

	String responseStr = "";
	JSONObject jsonObj = new JSONObject();

	public String JsonResponseToList(String responseStr) {
		Object object = null;
		JSONParser jsonParser = new JSONParser();
		JSONArray arrayObj = null;
		String res  = "";

		try {

			jsonObj = parseString(responseStr);
			String schme_type=null;

			try {
				if(null !=jsonObj){
					schme_type = jsonObj.get("document_master") == null ? "0" : jsonObj.get("document_master").toString();
				}
				object = jsonParser.parse(schme_type);
			} catch (ParseException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}

			JSONObject json = null;
			arrayObj = (JSONArray) object;
			if(arrayObj !=null)
				for (int i = 0; i < arrayObj.size(); i++) {
					json = (JSONObject) arrayObj.get(i);
					String docname = json.get("document_add_1") == null ? "0" : json.get("document_add_1").toString();
					res+= docname+"##"; 
				}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;

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
			jsonObject = null;
		} finally {
			jsonParser = null;
		}
		return jsonObject;
	}
	

}
