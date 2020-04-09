package snap.ib.personelLoan.adapter.common.service;
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

public interface IVLCommonServiceStub {
	
	/**
 	 * Function used to get FAQs status 
 	 * @param FAQRequest
 	 * @return FAQResponse
 	 */
     public  FAQResponse getFAQStubStatus(
     		 FAQRequest request) throws CommonException;
     
     public  MandateDropOffResponse getMandateDropOffStub(
    		 MandateDropOffRequest request) throws CommonException;
     
     public AdhaarTnCResponse  getAadhaarTnC(AdhaarTnCRequest adhaarTnCRequest) throws CommonException; 
     
     public ProductsListResponse  getProductsList(ProductsListRequest productsListRequest) throws CommonException;

	 public AddressProofResponse getAddressProofList(AddressProofRequest request) throws CommonException;
     
}
