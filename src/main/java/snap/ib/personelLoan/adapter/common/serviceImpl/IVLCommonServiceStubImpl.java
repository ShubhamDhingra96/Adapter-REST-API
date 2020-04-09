package snap.ib.personelLoan.adapter.common.serviceImpl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snap.ib.personelLoan.common.ApplicationConstant;
import com.snap.ib.personelLoan.common.BaseResource;

import snap.ib.personelLoan.adapter.common.CommonAdapterResource;
import snap.ib.personelLoan.adapter.common.exception.CommonException;
import snap.ib.personelLoan.adapter.common.request.AddressProofRequest;
import snap.ib.personelLoan.adapter.common.request.AdhaarTnCRequest;
import snap.ib.personelLoan.adapter.common.request.FAQRequest;
import snap.ib.personelLoan.adapter.common.request.MandateDropOffRequest;
import snap.ib.personelLoan.adapter.common.request.ProductsListRequest;
import snap.ib.personelLoan.adapter.common.response.AddressProofResponse;
import snap.ib.personelLoan.adapter.common.response.AdhaarTnCResponse;
import snap.ib.personelLoan.adapter.common.response.FAQResponse;
import snap.ib.personelLoan.adapter.common.response.MandateDropOffResponse;
import snap.ib.personelLoan.adapter.common.response.ProductsListResponse;
import snap.ib.personelLoan.adapter.common.service.IVLCommonServiceStub;
import snap.ib.personelLoan.adapter.common.util.IOUtils;
import snap.ib.personelLoan.adapter.common.util.ReadProperty;

public class IVLCommonServiceStubImpl extends BaseResource implements IVLCommonServiceStub {

	private static Logger logger = LoggerFactory.getLogger(CommonAdapterResource.class);
	private static IVLCommonServiceStub ivlStubSevice;
	private static final ReadProperty readProperties = ReadProperty.getInstance();
	/**
	 * Function used to get data from service or file Utils
	 * 
	 * @return Service Implementation
	 */

	public static IVLCommonServiceStub getInstance() {
		if (ivlStubSevice == null) {
				ivlStubSevice = new IVLCommonServiceStubImpl();
		}
		return ivlStubSevice;
	}

	public FAQResponse getFAQStubStatus(FAQRequest request) throws CommonException {

		String fileName = "";
		if (request != null) {
			fileName = request.getLang().trim();
			logger.info("json fileName" + fileName);
		} else {
			fileName = "FAQ_eng";
			logger.info("Default json fileName is FAQ_eng");

		}

		FAQResponse faqResponse = IOUtils.getObject(fileName, FAQResponse.class);

		if (faqResponse != null) {
			faqResponse.setError_message(null);
			faqResponse.setSuccess(true);
		}
		return faqResponse;
	}

	public AdhaarTnCResponse getAadhaarTnC(AdhaarTnCRequest request) throws CommonException {

		String fileName = "";
		String lang = "";
		String tncString = "";
		String finalString = "";
		AdhaarTnCResponse adhaarTnCResponse = null;

		if (request != null) {
			lang = request.getLang().trim();
			fileName = request.getFileName().trim();
			logger.info("fileName" + fileName);
		} else {
			fileName = "FAQ_eng";
			logger.info("Default fileName is FAQ_eng");

		}

		tncString = IOUtils.readTnCFile(lang, fileName);
		finalString = tncString.toString().trim();
		try {
			finalString = URLEncoder.encode(finalString, "UTF-8").replace("+", "%20");
			logger.info("Inside try::finalString=" + finalString);
		} catch (UnsupportedEncodingException e) {
			logger.info("Inside catch:" + e.getMessage());
			e.printStackTrace();
		}
		if (tncString != null) {
			adhaarTnCResponse = new AdhaarTnCResponse();
			adhaarTnCResponse.setText(finalString);
			adhaarTnCResponse.setError_message(null);
			adhaarTnCResponse.setSuccess(true);
			logger.info("adhaarTnCResponse=" + adhaarTnCResponse.toString());
		}
		return adhaarTnCResponse;
	}

	public MandateDropOffResponse getMandateDropOffStub(MandateDropOffRequest request) throws CommonException {
		String fileName = "", revised_req_loan_amt = "";
		if (request != null) {
			fileName = request.getFileName();
			revised_req_loan_amt = request.getRevised_req_loan_amt();
			logger.info("json fileName" + fileName);
		} else {
			fileName = "FAQ_eng";
			logger.info("Default json fileName is FAQ_eng");
		}
		MandateDropOffResponse myJsonResponse = IOUtils.getObject(fileName, MandateDropOffResponse.class);
		if (myJsonResponse != null) {
			ArrayList<String> list = myJsonResponse.getListPoints();
			String str = list.get(0);
			logger.info("json str" + str);
			str = str.replace("XXXX", revised_req_loan_amt);
			logger.info("after append str" + str);
			list.set(0, str);
			myJsonResponse.setListPoints(list);
			myJsonResponse.setError_message(null);
			myJsonResponse.setSuccess(true);
		}
		return myJsonResponse;
	}

	public ProductsListResponse getProductsList(ProductsListRequest productsListRequest) throws CommonException {

		String fileName = "";
		if (productsListRequest != null) {
			fileName = productsListRequest.getFileName();
		} 
		ProductsListResponse productsListResponse = IOUtils.fetchProductsList(fileName, ProductsListResponse.class);
		if (productsListResponse != null) {
			productsListResponse.setError_message(null);
			productsListResponse.setSuccess(true);
		}
		return productsListResponse;
	}

	public AddressProofResponse getAddressProofList(AddressProofRequest addressProofRequest) throws CommonException {
		
		String addressProofList = readProperties.getPropertyValue("ADDRESSPROOFTEXT");
		AddressProofResponse addressProofResponse=new AddressProofResponse();
		logger.info("addressProofList::: " + addressProofList);
		if (null!=addressProofList) {
			
			addressProofResponse.setSuccess(true);
			addressProofResponse.setError_message(null);
			addressProofResponse.setIdProofList(addressProofList);	
		}
		
		return addressProofResponse;
	}
}
