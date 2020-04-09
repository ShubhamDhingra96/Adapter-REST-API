package snap.ib.personelLoan.adapter.common.util;

import com.snap.ib.personelLoan.common.i18n.PropertiesFileReader;

public class ApplicationConstant {

	//public static final String STUBBED_FILE_DIR = "D:/StubbedData";
	private static final ReadProperty readProperties=  ReadProperty.getInstance();
	public static final String jsonFilePath  = readProperties.getPropertyValue("JSON_FILE_PATH");
	public static final String productList  = readProperties.getPropertyValue("PRODUCT_LIST_PATH");
	public static final String IMAGE_LOCATION = "/mfp/IBM/AppConfig/images";
	public static final String MESSAGE_FILE = "/mfp/IBM/AppConfig/propertiesFiles/com-snapwork-ib.properties";
	public static final String IIB_SERVICE_END_POINT = getValueFromPropertyFile("IIB_SERVICE_END_POINT");
	public static final String WCM_SERVICE_END_POINT = getValueFromPropertyFile("WCM_SERVICE_END_POINT");
	public static final String WCM_SERVICE_END_POINT_PUBLIC = getValueFromPropertyFile("WCM_SERVICE_END_POINT_PUBLIC");
	public static final String CLOUDANT_SERVICE_END_POINT = getValueFromPropertyFile("CLOUDANT_SERVICE_END_POINT");
	public static final String JIRA_SERVICE_END_POINT = getValueFromPropertyFile("JIRA_SERVICE_END_POINT");
	
	public static final String TnCFilePath =readProperties.getPropertyValue("ADHAARTnC").trim();
	public static final String OMP_END_POINT = getValueFromPropertyFile("OMP_END_POINT");
	public static final String UNICA_END_POINT = getValueFromPropertyFile("UNICA_END_POINT");
	public static final String CONTENT_TYPE_HEADER = "Content-Type";
	public static final String ACCEPT_TYPE_HEADER = "Accept";
	public static final String SERVICE_HEADER_VALUE = "application/json";
	public static final String HTTP_CODE = "httpCode";
	public static final String ERROR = "Error due to";
	public static final String MOBILE = "MOBILE";
	public static final String KEY_FAILURE_REPONSE_MESSAGE = "FAILURE_REPONSE_MESSAGE";
	public static final String FDProprtiesFile = "properties/wcmLinks.properties";
	public static final String MYDREAMS_PROPERTIES_FILE = "properties/myDreamsTnC.properties";
	public static final boolean isFileStubbedEnabled = true;
	public static final String EXCEPTION_CODE = "";
	public static final String EXCEPTION_MESSAGE = "We are unable to proccess your request,please try later";
	public static final String SUCCESS_CODE = "0000";
	public static final boolean isStubbedEnabled = false;
	public static final boolean isSecurityEnabled = true;
	public static final String REQUEST_MAPPING_FILE = "/home/snap74/Desktop/Adapter/com-snapwork-ib/src/main/java/requestidmapping.properties";
	public static boolean isEncryption;
	
	/**
	 * @param key
	 * @return
	 */
	
	public static String getValueFromPropertyFile(String key) {

		final String commonFileName = "/mfp/IBM/AppConfig/propertiesFiles/com-snapwork-ib.properties";
		PropertiesFileReader propertyFile = new PropertiesFileReader().getInstance("adapter_mapping.properties");
		return propertyFile.getProperty(commonFileName, key);
	}

	// For PreHomeAdapter
	public static final String INB_SERVICE_END_POINT = getValueFromPropertyFile("INB_SERVICE_END_POINT");
	public static final String LOTUS_INB_SERVICE_END_POINT = getValueFromPropertyFile("LOTUS_INB_SERVICE_END_POINT");
}
