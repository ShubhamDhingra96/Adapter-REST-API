package snap.ib.personelLoan.adapter.common.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.ibm.json.java.JSONObject;


public class IOUtils {

	private static Logger logger = LoggerFactory.getLogger(IOUtils.class);

	public static String getPrintStackTrace(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}

	public String readFile(String fileName) {
		InputStream stream = null;
		StringBuilder builder = new StringBuilder();
		Reader reader=null;
		try {

			String filePath=ApplicationConstant.jsonFilePath + "/"+ fileName+ ".json";
			logger.info("filePath:::: "+filePath);
			stream = new FileInputStream(filePath);
			reader = new BufferedReader(new InputStreamReader(stream));
			char[] buffer = new char[8192];
			int read;
			while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
				builder.append(buffer, 0, read);
			}


		} catch (Exception e) {
			logger.error(ApplicationConstant.ERROR, e);

		} finally {
			try {
				if (stream != null)
					stream.close();
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				logger.error(ApplicationConstant.ERROR, e);
			}
		}
		return builder.toString();
	}
	
	public String readProductList(String fileName) {
		InputStream stream = null;
		StringBuilder builder = new StringBuilder();
		Reader reader=null;
		try {
			String filePath = ApplicationConstant.productList + "/" + fileName + ".json";
			stream = new FileInputStream(filePath);
			logger.info("filePath:" + filePath);
			reader = new BufferedReader(new InputStreamReader(stream));
			char[] buffer = new char[8192];
			int read;
			while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
				builder.append(buffer, 0, read);
			}

		} catch (Exception e) {
			logger.error(ApplicationConstant.ERROR, e);

		} finally {
			try {
				if (stream != null)
					stream.close();
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				logger.error(ApplicationConstant.ERROR, e);
			}
		}
		return builder.toString();
	}

	@SuppressWarnings("unchecked")
	public static <T> T getObject(final String fileName, final Class<T> classObj) {
		Gson gson = new Gson();
		try {
			
			String jsonString = new IOUtils().readFile(fileName);
			JSONObject jsonObject = JSONObject.parse(jsonString);
			Object t = gson.fromJson(jsonObject.toString(), classObj);
			return (T) t;
		} catch (IOException e) {
			logger.error(ApplicationConstant.ERROR, e);
			JSONObject object = new JSONObject();
			object.put("responseCode", "IBL005");
			object.put("responseMessage", "Seems some issue to parsing stub json due to" + e.getMessage() + "("
					+ ApplicationConstant.jsonFilePath + "/" + fileName + ".json)");
			Object t = gson.fromJson(object.toString(), classObj);
			return (T) t;
		} catch (Exception e) {
			logger.error(ApplicationConstant.ERROR, e);
			JSONObject object = new JSONObject();
			object.put("responseCode", "IBL007");
			object.put("responseMessage", e.getMessage() + "");
			Object t = gson.fromJson(object.toString(), classObj);
			return (T) t;
		}

	}

	public static String readTnCFile(String lang, String fileName) {

		InputStream stream = null;
		String result = "";
		try {

			logger.info("in readFile:" + ApplicationConstant.TnCFilePath);
			stream = new FileInputStream(ApplicationConstant.TnCFilePath + "/" + lang + "/" + fileName + ".html");
			result = getStringFromInputStream(stream);

		} catch (Exception e) {
			logger.error(ApplicationConstant.ERROR, e);

		} finally {
			try {
				if (stream != null)
					stream.close();
			} catch (IOException e) {
				logger.error(ApplicationConstant.ERROR, e);
			}
		}
		return result;
	}

	private static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

	public static <T> T fetchProductsList(String fileName, Class<T> classObj) {

		Gson gson = new Gson();
		try {
			String jsonString = new IOUtils().readProductList(fileName);
			JSONObject jsonObject = JSONObject.parse(jsonString);
			Object t = gson.fromJson(jsonObject.toString(),classObj);
			return (T) t;
		} catch (IOException e) {
			logger.error(ApplicationConstant.ERROR, e);
			JSONObject object = new JSONObject();
			object.put("responseCode", "IBL005");
			object.put("responseMessage", "Seems some issue to parsing stub json due to" + e.getMessage() + "("
					+ ApplicationConstant.productList + "/" + fileName + ".json)");
			Object t = gson.fromJson(object.toString(), classObj);
			return (T) t;
		} catch (Exception e) {
			
			logger.error(ApplicationConstant.ERROR, e);
			JSONObject object = new JSONObject();
			object.put("responseCode", "IBL007");
			object.put("responseMessage", e.getMessage() + "");
			Object t = gson.fromJson(object.toString(), classObj);
			return (T) t;
		}
	}
}
