package snap.ib.personelLoan.adapter.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import snap.ib.personelLoan.adapter.common.CommonAdapterResource;

public class ReadProperty {

	private static Logger logger = LoggerFactory.getLogger(CommonAdapterResource.class);
	public static final String REQUEST_MAPPING = "/logs/PLA.properties";
	public static final String REQUEST_PINCODE = "/logs/pincode.json";

	private static Properties properties;
	private static Properties statecityproperties;
	private static HashMap<String, Object> hashMap;
	private static ReadProperty reader;
	private static Properties propertiesPinCode;

	public static void load() {

		try {
			properties = new Properties();
			statecityproperties = new Properties();
			try {

				// properties.load(ReadProperty.class.getClassLoader().getResourceAsStream("PLALoc.properties"));
			   //  properties.load(ReadProperty.class.getClassLoader().getResourceAsStream("PLA.properties"));
				//properties.load(ReadProperty.class.getClassLoader().getResourceAsStream("PLA_UAT.properties"));
				//propertiesPinCode.load(ReadProperty.class.getClassLoader().getResourceAsStream("pincode.json"));
				
				logger.info("FilePath:" + REQUEST_MAPPING);
				statecityproperties.load(ReadProperty.class.getClassLoader().getResourceAsStream("statecity.properties")); 
				
				InputStream in = new FileInputStream(REQUEST_MAPPING);
				properties.load(in);
				in.close();
				logger.info("All properties are loaded successfully");
			} catch (IOException e) {
				logger.info("loadPropertiesFileFromResource: Error in reading properties file:" + e.toString());
			}
		} catch (Exception se) {
			logger.error("loadPropertiesFileFromResource: Failed in ReadConfigFile constructor :" + se.toString());
		}
	}

	public static synchronized ReadProperty getInstance() {
		if (reader == null) {
			reader = new ReadProperty();
			load();
		}
		return reader;
	}

	@SuppressWarnings("rawtypes")
	private void populateMap() {
		try {
			String key = "";
			String Value = "";
			hashMap = new HashMap<String, Object>();
			Set keySet = properties.keySet();
			for (Object K : keySet) {
				key = (String) K;
				Value = properties.getProperty(key + "");
				hashMap.put(key, Value);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String getKeyValue(Object keyName) {

		String key = "";
		if (hashMap != null) {
			key = hashMap.get(keyName) == null ? "" : hashMap.get(keyName).toString();
		} else {
			populateMap();
		}
		if ("".equals(key) || key == null) {
			key = properties.getProperty(keyName + "");
		}
		return key;
	}

	public String getPropertyValue(String key) {
		return this.getKeyValue(key) == null ? "" : this.getKeyValue(key).trim();
	}

	public String getStateCityKeyValue(Object keyName) {

		String key = statecityproperties.getProperty(keyName + "");

		return key;
	}
	
}
