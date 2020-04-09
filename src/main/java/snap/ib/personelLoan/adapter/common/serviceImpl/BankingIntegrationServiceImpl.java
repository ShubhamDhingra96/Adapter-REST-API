package snap.ib.personelLoan.adapter.common.serviceImpl;

import java.io.FileReader;
import java.util.Base64;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import snap.ib.personelLoan.adapter.common.CommonAdapterResource;
import snap.ib.personelLoan.adapter.common.exception.CommonException;
import snap.ib.personelLoan.adapter.common.request.AndhraBankResDataRequest;
import snap.ib.personelLoan.adapter.common.request.AndhraTokenRequest;
import snap.ib.personelLoan.adapter.common.request.AxisURLRedirectDecRequest;
import snap.ib.personelLoan.adapter.common.request.CommonRequest;
import snap.ib.personelLoan.adapter.common.request.RblTokenRequest;
import snap.ib.personelLoan.adapter.common.request.YesBankDataRequest;
import snap.ib.personelLoan.adapter.common.request.YesBankRequest;
import snap.ib.personelLoan.adapter.common.request.YesBankResponse;
import snap.ib.personelLoan.adapter.common.response.AndhraResponse;
import snap.ib.personelLoan.adapter.common.response.AndhraTokenResponse;
import snap.ib.personelLoan.adapter.common.response.CommonResponse;
import snap.ib.personelLoan.adapter.common.service.BankingIntegrationService;
import snap.ib.personelLoan.adapter.common.sessionutil.CustomSessionServiceImpl;
import snap.ib.personelLoan.adapter.common.sessionutil.SessionData;
import snap.ib.personelLoan.adapter.common.util.AESEncDec;
import snap.ib.personelLoan.adapter.common.util.AES_Encrption;
import snap.ib.personelLoan.adapter.common.util.AndhraBankIntegration;
import snap.ib.personelLoan.adapter.common.util.CommonUtility;
import snap.ib.personelLoan.adapter.common.util.ReadProperty;

public class BankingIntegrationServiceImpl implements BankingIntegrationService {

	private static Logger logger = LoggerFactory.getLogger(CommonAdapterResource.class);
	private static BankingIntegrationService integrationService;
	private static final ReadProperty readProperties = ReadProperty.getInstance();
	private final String isEnc = readProperties.getPropertyValue("IS_ENC");
	AESEncDec aesEncDec = new AESEncDec();
	public static BankingIntegrationService getInstance() {

		if (integrationService == null) {
			integrationService = new BankingIntegrationServiceImpl();
		}
		return integrationService;
	}

	public YesBankResponse yesBankRedirection(CommonRequest request1) throws CommonException {

		logger.info("StartTime of yesBankRedirection Service:" + System.currentTimeMillis());
		logger.info("yesBankRedirection request::: "+request1.toString());

		String deviceId= null, sessionKey = null;
		YesBankResponse response=new YesBankResponse();
		try{


			deviceId = request1.getDevice_id();
			sessionKey = request1.getSession_key();
			logger.info(" request::: "+request1.toString());
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request1, aesEncDec);

			if(isValidRequest){

				logger.info("request1.getInputString()= " + request1.getInputString());
				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);

				String inputString = aesEncDec.decrypt(key, request1.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();

				YesBankRequest request = gson.fromJson(inputString.replace("\\/", "/"), YesBankRequest.class);


				String fldClientCode=request.getFldClientCode();
				String fldTxnCurr=request.getFldTxnCurr(); 
				String fldTxnAmt=request.getFldTxnAmt();
				String fldTxnScAmt=request.getFldTxnScAmt();
				long fldMerchRefNbr=AES_Encrption.generateRandom(10);
				String fldDatTimeTxn=request.getFldDatTimeTxn();
				String md=request.getMd();
				String fldClientAcctNo=request.getFldClientAcctNo();
				String ru=request.getRu();
				String fldCustAcctNo=request.getFldCustAcctNo();
				String fldTenure=request.getFldTenure();
				String fldStartDat=request.getFldStartDat();
				String fldExpDat=request.getFldExpDat();
				String fldSchDat=request.getFldSchDat();
				String fldFreq=request.getFldFreq();
				String cu=request.getCu();
				String fldIFSC=request.getFldIFSC();
				String fldBankName=request.getFldBankName();
				String fldCustNam=request.getFldCustNam();
				String fldCRN=request.getFldCRN();
				String pid=request.getPid();
				String fldFlgReversal=request.getFldFlgReversal();
				String ybkEncKey=readProperties.getPropertyValue("YBKENCDECKEY").trim();
				String bankRefNo=request.getBankRefNo();

				logger.info("bankRefNo::: "+request.getBankRefNo());
				logger.info("ru          ::: "+ru);
				String checkSumStr="PID="+pid+"|"+"fldClientCode="+fldClientCode+"|"+"fldTxnCurr="+fldTxnCurr+"|"+"fldTxnAmt="+fldTxnAmt+"|"
						+"fldTxnScAmt="+fldTxnScAmt+"|"+"fldMerchRefNbr="+fldMerchRefNbr+"|"+"fldDatTimeTxn="+fldDatTimeTxn+"|"+"MD="+md+"|"
						+"fldClientAcctNo="+fldClientAcctNo+"|"+"RU="+"http://115.110.129.132:9080/ibapp/YBKReturn.jsp"+"|"+"fldCustAcctNo="+fldCustAcctNo+"|"+"fldTenure="+fldTenure+"|"
						+"fldStartDat="+fldStartDat+"|"+"fldExpDat="+fldExpDat+"|"+"fldSchDat="+fldSchDat+"|"+"fldFreq="+fldFreq+"|"+"CU="+cu+"|"
						+"fldIFSC="+fldIFSC+"|"+"fldBankName="+fldBankName+"|"+"fldCustNam="+fldCustNam+"|"+"fldCRN="+fldCRN+"|"+"fldFlgReversal="+fldFlgReversal;

				logger.info("checkSumStr::: "+checkSumStr);
				String chkSum=AESEncDec.createChecksum(checkSumStr, "Y");
				logger.info("checkSum::: "+chkSum);

				String tokenString="fldClientCode="+fldClientCode+"|"+"fldTxnCurr="+fldTxnCurr+"|"+"fldTxnAmt="+fldTxnAmt+"|"
						+"fldTxnScAmt="+fldTxnScAmt+"|"+"fldMerchRefNbr="+fldMerchRefNbr+"|"+"fldDatTimeTxn="+fldDatTimeTxn+"|"+"MD="+md+"|"
						+"fldClientAcctNo="+fldClientAcctNo+"|"+"RU="+"http://115.110.129.132:9080/ibapp/YBKReturn.jsp"+"|"+"fldCustAcctNo="+fldCustAcctNo+"|"+"fldTenure="+fldTenure+"|"
						+"fldStartDat="+fldStartDat+"|"+"fldExpDat="+fldExpDat+"|"+"fldSchDat="+fldSchDat+"|"+"fldFreq="+fldFreq+"|"+"CU="+cu+"|"
						+"fldIFSC="+fldIFSC+"|"+"fldBankName="+fldBankName+"|"+"fldCustNam="+fldCustNam+"|"+"fldCRN="+fldCRN+"|"+"fldFlgReversal="+fldFlgReversal+"|"+"CHECKSUM="+chkSum;

				logger.info("tokenString::: "+tokenString);
				logger.info("ybkEncKey::: "+ybkEncKey);
				String token=AES_Encrption.getEncToken(tokenString,ybkEncKey);

				logger.info("token::: "+token);
				response.setError_message(null);
				response.setSuccess(true);
				response.setToken(token);

				logger.info("response::: "+response);
				logger.info("EndTime of yesBankRedirection Service:" + System.currentTimeMillis());
			}

		}catch(Exception e){
			logger.info("Exception [ " + e + " ]");
		}
		return response;
	}

	public YesBankResponse yesBankDualVarification(CommonRequest request) throws CommonException {

		//YesBankDataRequest
		logger.info("StartTime of yesBankDualVarification Service:" + System.currentTimeMillis());
		logger.info("yesBankDualVarification request::: "+request.toString());
		YesBankResponse response=new YesBankResponse();
		String deviceId = null, sessionKey = null;

		try{
			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
			logger.info("key= " + key);
			String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
			logger.info("decrypted inputString= " + inputString);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			YesBankDataRequest serviceRequest = gson.fromJson(inputString, YesBankDataRequest.class);

			String pid=serviceRequest.getPid();
			String yesBankData=serviceRequest.getYesBankData();
			yesBankData=AES_Encrption.getDecToken(yesBankData, "Indiabulls2018");
			//yesBankData="fldClientCode=4004322|fldTxnCurr=INR|fldTxnAmt=800.0|fldTxnScAmt=1.0|fldMerchRefNbr=3269089812|fldDatTimeTxn=09/17/2018 13:40:13|MD=IB|fldClientAcctNo=000284000001354|RU=http://115.110.129.132:9080/ibapp/YBKReturn.jsp|fldCustAcctNo=000193700000011|fldTenure=12|fldStartDat=02/11/2018|fldExpDat=02/10/2020|fldSchDat=27|fldFreq=7|CU=null|fldIFSC=YESB0000127|fldBankName=YESBank|fldCustNam=INDIABULLSVENTURES|fldCRN=698699|fldFlgReversal=Y|BankRefNo=78027|Message=SUCCESS|checkSum=5b0a8f92ab1872386dad8ee7cf2829b6";
			String []strArr=yesBankData.split("\\|Message", 2);
			for (int i = 0; i < strArr.length; i++) {
				logger.info(strArr[i]);
			}

			String yesBankSubString=strArr[0].replace("fldFlgReversal=Y|", "");
			String checkSumStr="PID="+pid+"|"+yesBankSubString+"|"+"flgVerify=Y";
			logger.info("checkSumStr::: "+checkSumStr);

			String chkSum=AESEncDec.createChecksum(checkSumStr, "Y");
			logger.info("checkSum::: "+chkSum);
			String tokenString=checkSumStr.split("\\|",2)[1]+"|"+"CHECKSUM="+chkSum;
			logger.info("tokenString::: "+tokenString);
			String token=AES_Encrption.getEncToken(tokenString, "Indiabulls2018");
			logger.info("token::: "+token);
			response.setError_message(null);
			response.setSuccess(true);
			response.setToken(token);
			logger.info("response::: "+response);
			logger.info("EndTime of yesBankDualVarification Service:" + System.currentTimeMillis());

		}catch(Exception e){

		}
		return response;
	}

	public AndhraTokenResponse generateTokenForAndhraBank(CommonRequest request1) {
		long startTime = System.currentTimeMillis();

		logger.info("StartTime in AndhraBankRedirectService:\t\t" + startTime);
		AndhraTokenResponse response = new AndhraTokenResponse();
		String message = "", CRN = "";
		boolean _isError = false;
		Random rand = new Random();
		String deviceId = null, sessionKey = null;
		//AndhraTokenRequest

		try {
			deviceId = request1.getDevice_id();
			sessionKey = request1.getSession_key();
			String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
			logger.info("key= " + key);
			String inputString = aesEncDec.decrypt(key, request1.getInputString(), isEnc);
			logger.info("decrypted inputString= " + inputString);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			AndhraTokenRequest request = gson.fromJson(inputString, AndhraTokenRequest.class);

			CRN = readProperties.getPropertyValue("CRNKEY").trim();
			if (Strings.isNullOrEmpty(CRN)) {
				_isError = true;
				message = readProperties.getPropertyValue("CRNNUMERROR").trim();
			}
			if (!_isError) {

				logger.info("TokenRequest: \t\t" + request.toString());

				String bankid = readProperties.getPropertyValue("BANKID");
				String userlangid = readProperties.getPropertyValue("USERLANGID");
				String apptype = readProperties.getPropertyValue("APPTYPE");
				String usertype = readProperties.getPropertyValue("USERTYPE");
				String cg = readProperties.getPropertyValue("CG");
				String md = readProperties.getPropertyValue("MD");

				String pid = request.getPid();   
				String dhaniId = request.getDhani_id();
				String dhaniRef = request.getDhani_ref()+String.format("%04d", rand.nextInt(10000));;
				String customerName = request.getCustomer_name();
				String bankName = request.getBank_name();
				String accountNo = request.getAccount_number();
				String loanAmount = request.getLoan_amount();
				String emiAmount = request.getEmi_amount();
				String debitFrequery = request.getDebit_frequency();
				String debitStartDate = request.getDebit_start_date();
				String debitenddate = request.getDebit_end_date();
				String webtoken = "DUMMYTOKEN";


				logger.info("TokenRequest: \t\t" + request.toString()); 
				/*cg="Y";
				md="p";
				String  userlangid="001";
				String  bankid = "011";
				String  apptype="corporate";
				String  usertype="1";
				String pid = "000001923948";//request.getPid();
				String dhaniId = "100000015";//request.getDhani_id();
				String dhaniRef = "TEST0028369835";//request.getDhani_ref();//reanedem
				String customerName = "TESTCLIENT";//request.getCustomer_name();
				String bankName = "ANDHRABANK";//request.getBank_name();
				String accountNo = "117910100036871";//request.getAccount_number();
				String loanAmount = "1000000.00";//request.getLoan_amount();
				String emiAmount = "10000.00";//request.getEmi_amount();
				String debitFrequery = "M";//request.getDebit_frequency();
				String debitStartDate = "01-12-2018 00:00:00";//request.getDebit_start_date();
				String debitenddate = "01-12-2040 00:00:00";//request.getDebit_end_date();
				String webtoken = "DUMMYTOKEN";//request.getDebit_end_date();
				CRN = "INR";//request.getDebit_end_date();
				 */				//String webtoken = "DUMMYTOKEN";//request.getDebit_end_date();

				/*String paymentReq = "Action.ShoppingMall.Login.Init=Y" + "&" + "BankId=" + bankid + "&"
						+ "USER_LANG_ID=" + userlangid + "&" + "AppType=" + apptype + "&" + "UserType=" + usertype + "&"
						+ "CG=" + cg + "&" + "MD=" + md + "&" + "PID=" + pid + "&" + "DHANIID=" + dhaniId + "&"
						+ "DHANIREF=" + dhaniRef + "&" + "CUSTOMERNAME=" + customerName + "&" + "BANKNAME=" + bankName + "&"
						+ "ACCOUNTNUMBER=" + accountNo + "&" + "LOANAMOUNT=" + loanAmount + "&" + "EMIAMOUNT="
						+ emiAmount + "&" + "DEBITFREQUENCY=" + debitFrequery + "&" + "DEBITSTARTDATE=" + debitStartDate
						+ "&" + "DEBITENDDATE=" + debitenddate + "&" + "CRN=" + crn + "&" + "RU="
						+ "https://imsva91-ctp.trendmicro.com:443/wis/clicktime/v1/query?url=https%3a%2f%2fAndhraBankReturn.jsp%26%238221%3b";

				 */	

				logger.info("bankid:\t\t" + bankid);
				String paymentReq = "Action.ShoppingMall.Login.Init=Y" + "&" + "BankId=" + bankid + "&"
						+ "USER_LANG_ID=" + userlangid + "&" + "AppType=" + apptype + "&" + "UserType=" + usertype + "&"
						+ "CG=" + cg + "&" + "MD=" + md + "&" + "PID=" + pid + "&" + "DHANIID=" + dhaniId + "&"
						+ "DHANIREF=" + dhaniRef + "&" + "CUSTOMERNAME=" + customerName + "&" + "BANKNAME=" + bankName + "&"
						+ "ACCOUNTNUMBER=" + accountNo + "&" + "LOANAMOUNT=" + loanAmount + "&" + "EMIAMOUNT="
						+ emiAmount + "&" + "DEBITFREQUENCY=" + debitFrequery + "&" + "DEBITSTARTDATE=" + debitStartDate
						+ "&" + "DEBITENDDATE=" + debitenddate + "&" + "CRN=" + CRN + "&" + "WEBTOKEN="+webtoken+"&RU="+request.getRu();
				//+ "https://imsva91-ctp.trendmicro.com:443/wis/clicktime/v1/query?url=https%3a%2f%2fwww.andhrabank.in%7ccheckSum%3dbeb300a00ad8cfd6ba23127e89b36edf&umid=9AD0947D-7BF0-8F05-AD2C-3C16408E5323&auth=5a117aac19969cda63d2bc396e0fc3050c7b84d3-1b10340aeaba13bdd0ffbad5610b26f596a54211";



				/*String paymentReq = "PID=000001923948" + "&" + "DHANIID=" + dhaniId + "&"+ "DHANIREF=" + dhaniRef + "&" + "CUSTOMERNAME=" + customerName 
						+ "&" + "BANKNAME=" + bankName + "&"+ "ACCOUNTNUMBER=" + accountNo + "&" + "LOANAMOUNT=" + loanAmount 
						+ "&" + "EMIAMOUNT=" + emiAmount + "&" + "DEBITFREQUENCY=" + debitFrequery + "&"+ "DEBITSTARTDATE=" + debitStartDate 
						+ "&" + "DEBITENDDATE=" + debitenddate + "&" + "CRN=" + crn + "&"+ "WEBTOKEN=" + webtoken 
						+ "&" + "RU=https://imsva91-ctp.trendmicro.com:443/wis/clicktime/v1/query?url=https%3a%2f%2fwww.IndiaBulls.in%26quot&umid=916288CC-7B16-ED05-BAC4-5AFD44C0296D" 
						+ "&" + "auth=5a117aac19969cda63d2bc396e0fc3050c7b84d3-fbc108e918f0261603b9ac4042cee18929a53511";
				 */
				logger.info("requestWithout checksum:\t\t" + paymentReq);

				String token = AndhraBankIntegration.encrypt(paymentReq);

				logger.info("request token:\t\t" + token);
				//String decrypt = AndhraBankIntegration.decrypt("f7VFCIf3LAdkewLqSf2AuUZlqgRiNN/RkUCxI81qIewtj+tvvx14YI9gcfHFcAmTv2MGtA0R9WzBI9KkmBIVHwerVC06ignrO7lHpxr2583tPSmdTpGjZmnXS3crPRrcZFmU+m8EjFWWCf/8D69GKSJlT5RuW7vPd+DX3iKLpF/EigadxYYqe2DPel6StWTqfz6BWNiQqhKxEPEGwO01Ocu2qcptWY7Z3SSOEw31ZQ4bZvDpTkBmTfbbkAk9YR2bkwWCQAHqU6PRQ+QcFrXi//wPZ9F6od9wLBNsg8nuBbOnjgFO073+VdYktm+Qio5/sFxCDnQmCt+f6aXnnpwRNMFsWlUAlyee2DPtJ27NLeKR7nCaVU5Zosel+L1on3uSkT5ymC3TW2Xjyw4FP5mPdVY+QeiggJeOhs0Sd1L30RCN8GGzkusvpzzlpv31pyQjsRpfaKsYzzwbS2CScX0clT1gmknMxyr+GpQHapU8DO27KQ0+DseYvQKiI5689emkLP9XY6aTH+IjjEPggWNQgQ==");

				//logger.info("decrypt token:\t\t" + decrypt);

				//logger.info(" Token for rediction :  \t\t" + decrypt);

				response.setError_message(null);
				response.setSuccess(true);
				response.setToken(token);

			} else {

				response.setError_message(message);
				response.setSuccess(false);
				response.setToken(null);
			}
			long endTime = System.currentTimeMillis();
			logger.info("EndTime in generateTokenForAndhraBank:\t" + endTime + "\t"
					+ "TotalTime of generateTokenForAndhraBank:\t" + (endTime - startTime));

		} catch (Exception e) {
			e.printStackTrace();

		}
		//logger.info("Final Response>>"+response.toString());

		return response;
	}

	public AndhraResponse getandhraBankresponseGen(CommonRequest request1) {

		logger.info("StartTime of getandhraBankresponseGen Service:" + System.currentTimeMillis());
		logger.info("getandhraBankresponseGen request::: "+request1.toString());

		AndhraResponse response=new AndhraResponse();

		String deviceId = null, sessionKey = null;
		try{

			deviceId = request1.getDevice_id();
			sessionKey = request1.getSession_key();
			logger.info(" request::: "+request1.toString());
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request1, aesEncDec);

			if(isValidRequest){

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String inputString = aesEncDec.decrypt(key, request1.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				AxisURLRedirectDecRequest request = gson.fromJson(inputString, AxisURLRedirectDecRequest.class);
				logger.info(" request::: "+request.toString());
				String rslt=AndhraBankIntegration.decrypt(request.getEncData());
				logger.info(" request::: "+rslt);
				String[] result=rslt.split("\\&");

				System.out.println("result.length::"+result.length);

				if(result.length>15){

					response.setSTATUS(result[1].replace("STATUS=", "").trim());
					response.setDHANIID(result[2].replace("DHANIID=", "").trim());
					response.setDHANIREF(result[3].replace("DHANIREF=", "").trim());
					response.setMANDATEID(result[4].replace("MANDATEID=", "").trim());
					response.setMANDATEDATE(result[5].replace("MANDATEDATE=", "").trim());
					response.setBANKREF(result[6].replace("BANKREF=", "").trim());

					response.setCUSTOMERNAME(result[7].replace("CUSTOMERNAME=", "").trim());
					response.setBANKNAME(result[8].replace("BANKNAME=", "").trim());
					response.setACCOUNTNUMBER(result[9].replace("ACCOUNTNUMBER=", "").trim());
					response.setLOANAMOUNT(result[10].replace("LOANAMOUNT=", "").trim().replace("INR|", ""));
					response.setEMIAMOUNT(result[11].replace("EMIAMOUNT=", "").trim());
					response.setDEBITFREQUENCY(result[12].replace("DEBITFREQUENCY=", "").trim());

					response.setDEBITSTARTDATE(result[13].replace("DEBITSTARTDATE=", "").trim());
					response.setDEBITENDDATE(result[14].replace("DEBITENDDATE=", "").trim());

					String[] rslt1=result[15].trim().split("\\|");
					response.setWEBTOKEN(rslt1[0].replace("WEBTOKEN=", "").trim());
					response.setCheckSum(rslt1[1].replace("checkSum=", "").trim());
					response.setSuccess(true);
				}else{
					response.setSuccess(false);
				}

				//System.out.println("result.length::"+response.getCheckSum());
				//System.out.println("result.length::"+result[1]);
				//System.out.println(response.getWEBTOKEN());
				//System.out.println(Double.parseDouble(response.getLOANAMOUNT()));

				logger.info("response  :"+response.toString());
				logger.info("EndTime of getandhraBankresponseGen Service:" + System.currentTimeMillis());

			}else {

				logger.info("response is empty :"+response);
				logger.info("EndTime of getandhraBankresponseGen Service:" + System.currentTimeMillis());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		// TODO: handle exception
		return response;
	}




	public CommonResponse generateTokenForRbl(CommonRequest request) {
		long startTime = System.currentTimeMillis();

		logger.info("StartTime in generateTokenForRbl:\t\t" + startTime);
		CommonResponse response = new CommonResponse();
		String message = "", CRN = "",randomNO;
		boolean _isError = false;
		Random random = new Random();
		try {	


			CRN = readProperties.getPropertyValue("CRNKEY").trim();
			if (Strings.isNullOrEmpty(CRN)) {
				_isError = true;
				message = readProperties.getPropertyValue("CRNNUMERROR").trim();
			}
			//String isEnc1="Y";

			logger.info("request inputString= " + request.toString());
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);
			String sessionKey = request.getSession_key();
			String deviceId = request.getDevice_id();
			String outputString = null;

			if(isValidRequest){

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				RblTokenRequest requestRbl = gson.fromJson(inputString, RblTokenRequest.class);

				randomNO=String.format("%09d", random.nextInt(1000000000));

				if (!_isError) {

					logger.info("TokenRequest: \t\t" + request.toString());



					/*		String paymentReqToken = "ShoppingMallTranFG.RID~SunilGICJT545123787OrderNumber"
						+"|ShoppingMallTranFG.CID~PL00001234" 
						+ "|ShoppingMallTranFG.TYP~UAT"
						+"|ShoppingMallTranFG.CRN~PL00001234"
						+"|ShoppingMallTranFG.CYN~INR"
						+"|ShoppingMallTranFG.CUST_NAME~Ramesh"
						+"|ShoppingMallTranFG.BANK_NAME~RBL%20Bank"
						+"|ShoppingMallTranFG.FREQUENCY~ADHO"
						+"|ShoppingMallTranFG.DEBIT_TYPE~MAX"
						+"|ShoppingMallTranFG.START_DATE~15/12/2018"
						+"|ShoppingMallTranFG.END_DATE~15/12/2020"
						+"|ShoppingMallTranFG.AMOUNT~67.90"
						+"|ShoppingMallTranFG.DEBIT_AC_NO~309005064285"
						+"|ShoppingMallTranFG.SESSION_ID~0001FLSUF3fkXqEWHUz4ZNyt3K54554554L:-1FDTTManagerBanks";*/

					String paymentReqToken = "ShoppingMallTranFG.RID~"+requestRbl.getCustName()+requestRbl.getCid()+randomNO
							+"|ShoppingMallTranFG.CID~" + requestRbl.getCid() + "|ShoppingMallTranFG.TYP~"+requestRbl.getType() 
							+"|ShoppingMallTranFG.CRN~"+ requestRbl.getCrn() +"|ShoppingMallTranFG.CYN~"+requestRbl.getCyn()
							+"|ShoppingMallTranFG.CUST_NAME~"+requestRbl.getCustName()+"|ShoppingMallTranFG.BANK_NAME~"+requestRbl.getBankName()
							+"|ShoppingMallTranFG.FREQUENCY~"+requestRbl.getFrequency()+"|ShoppingMallTranFG.DEBIT_TYPE~"+requestRbl.getDebitType()
							+"|ShoppingMallTranFG.START_DATE~"+requestRbl.getStartDate()+"|ShoppingMallTranFG.END_DATE~"+requestRbl.getEndDate()
							+"|ShoppingMallTranFG.AMOUNT~"+requestRbl.getAmount()+"|ShoppingMallTranFG.DEBIT_AC_NO~"+requestRbl.getDebitAcNO()
							+"|ShoppingMallTranFG.SESSION_ID~"+requestRbl.getSessionId();


					//String paymentReq="ShoppingMallTranFG.RID~SunilGICJT545123787OrderNumber|ShoppingMallTranFG.CID~281597|ShoppingMallTranFG.TYP~UAT|ShoppingMallTranFG.CRN~CustRef1234|ShoppingMallTranFG.CYN~INR|ShoppingMallTranFG.CUST_NAME~Sunil Gonsalves|ShoppingMallTranFG.BANK_NAME~RBL BANK|ShoppingMallTranFG.FREQUENCY~ADHO|ShoppingMallTranFG.DEBIT_TYPE~MAX|ShoppingMallTranFG.START_DATE~15/12/2018|ShoppingMallTranFG.END_DATE~15/12/2020|ShoppingMallTranFG.AMOUNT~67.90|ShoppingMallTranFG.DEBIT_AC_NO~309005064285|ShoppingMallTranFG.SESSION_ID~0001FLSUF3fkXqEWHUz4ZNyt3K54554554L:-1FDTTManagerBanks";
					//	String paymentReq1="Jamunkar281597251437841|ShoppingMallTranFG.CID~281597|ShoppingMallTranFG.TYP~UAT|ShoppingMallTranFG.CRN~PL00001930|ShoppingMallTranFG.CYN~INR|ShoppingMallTranFG.CUST_NAME~Ashwini Jamunkar|ShoppingMallTranFG.BANK_NAME~RBL Bank|ShoppingMallTranFG.FREQUENCY~ADHO|ShoppingMallTranFG.DEBIT_TYPE~MAX|ShoppingMallTranFG.START_DATE~02/02/2019|ShoppingMallTranFG.END_DATE~02/01/2021|ShoppingMallTranFG.AMOUNT~1311000|ShoppingMallTranFG.DEBIT_AC_NO~309005064285|ShoppingMallTranFG.SESSION_ID~PL00001930";

					logger.info("requestToken without  Encrption :\t\t" + paymentReqToken);
					//logger.info("requestWithout :\t\t" + paymentReq);



					String token = AES_Encrption.getRblencrypt(paymentReqToken,"29304E8758327892");


					logger.info("request Encrption token:\t\t" + token);

					//logger.info("request  encode token:\t\t" + URLEncoder.encode(token, "UTF-8"));
					//String deccrptionToken = aES_Encrption.getDecToken("AZjAAEqTKfmJVz9fJLkoQ7b9VNrV8HnL9ipBK%2FpXiuZ1GmbgM2cFADfc7iRzNrSIwh3m5zgbSGaVns68%2FZ4n8EJFhuaXrhyiG6l9vKgFkeNIv5zn3Z4d%2Fw9SNqFtQWhDpKgIsmaskh%2Fzn5QCmFMXYkB%2BcIfJ4vUfNWZH%2Fvte8vikC89G8cETkDVmqVUu8hQsJpcYcPt%2BcC0tFFgFh0QnMyxlRD8ScOL7jHo7uCExP%2F5I58Z8x6UoEteJWpsUdZgKnVJ4mPQaZboP38phNF5se79R3qB%2BtFtMGwD1lXLZnLq2t%2BWcdHoFpWXrK48c4w%2FHSJL%2FuctZlL4GKinat8kBc5qc6mcrhoABCSigotD0rjjekv29Gh%2BOxiJwiT2C%2B4xH1eDQdaaEZLHafva7FFlqgd0ClWpkukwN5Xp2mZIRHrtfSmUug%2FtUor4N%2FkqkuBOL2stYjZ8bYi2t9wm40lOXDJyDu3DO1xbL5Z6yEBu0%2B8mH43G5V0eMC%2Bnu2QAOCNr7oXBogwyxo2OrB6GG5YTt3IKddcB1cPSYRR2bKES4jqoaO%2BENPcyGndXES8KqI%2Fgr2CsfXQtkfDTvLXPg3VZOHxl92hBWs9znyR%2FS6nRC4XynypL9ceC0faELyxPe9P%2BQ2mwXpLo5JQT%2BQPGD35skRBmr8rBdzVAjGpP4YgHqF3B3ctrVqUeECxECcNKs2cWPRADTEmao%2FaywXszRjveb4cw9%2FKWh3duXNfpncHLuK1xNxiqiwh4ait%2Fgtth0dcvu","29304E8758327892");

					//logger.info("deccrptionToken token:\t\t" + deccrptionToken);

					outputString = "{\"token\":\""+ token +"\",\"success\":true,\"error_message\":\"\"}";
					response = CommonUtility.generateCommonResponse(key, outputString, isEnc, aesEncDec, readProperties);
					logger.info("request final Encrption token:\t\t" + response);
				} else {
					outputString = "{\"token\":\"\",\"success\":false,\"error_message\":\""+message+"\"}";
					response = CommonUtility.generateCommonResponse(key, outputString, isEnc, aesEncDec, readProperties);
				}
			}
			long endTime = System.currentTimeMillis();
			logger.info("EndTime in generateTokenForRbl:\t" + endTime + "\t"
					+ "TotalTime of generateTokenForRbl:\t" + (endTime - startTime));

		} catch (Exception e) {
			e.printStackTrace();

		}
		//logger.info("Final Response>>"+response.toString());

		return response;
	}
/*
		 public static void main(String[] args) 
		    { 
		        // parsing file "JSONExample.json" 
		        Object obj;
				try {
					obj = new JSONParser().parse("{\"phoneNumbers\": [ {\"type\": \"home\", \"number\": \"212 555-1234\" } ]}");
				
					 System.out.println("obj"+obj); 
		        JSONObject jo = (JSONObject) obj; 
		          
		        // getting firstName and lastName 
		        String firstName = (String) jo.get("phoneNumbers"); 
		       
		        
		        
		        Gson gson = new Gson();

		        //convert java object to JSON format
		        String json = gson.toJson(obj);
		        System.out.println(json); 
		       // String lastName = (String) json.get("number"); 
		          
		        //System.out.println(lastName); 
		          
		            
		        // getting address 
		        Map address = ((Map)jo.get("address")); 
		          
		        // iterating address Map 
		        Iterator<Map.Entry> itr1 = address.entrySet().iterator(); 
		        while (itr1.hasNext()) { 
		            Map.Entry pair = itr1.next(); 
		            System.out.println(pair.getKey() + " : " + pair.getValue()); 
		        } 
		          
		        // getting phoneNumbers 
		        JSONArray ja = (JSONArray) jo.get("phoneNumbers"); 
		          
		        // iterating phoneNumbers 
		        Iterator itr2 = ja.iterator(); 
		          
		        while (itr2.hasNext())  
		        { 
		            itr1 = ((Map) itr2.next()).entrySet().iterator(); 
		            while (itr1.hasNext()) { 
		                Map.Entry pair = itr1.next(); 
		                System.out.println(pair.getKey() + " : " + pair.getValue()); 
		            } 
		        }
		        } catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		        
		    } 
		 */
		 
		 public static void main(String[] args) {
				
				try {
					String s=" ShoppingMallTranFG.RID~Saurabh Kapoor3021485548618459|ShoppingMallTranFG.CID~3021485|ShoppingMallTranFG.TYP~PROD|ShoppingMallTranFG.CRN~TP00073259|ShoppingMallTranFG.CYN~INR|ShoppingMallTranFG.CUST_NAME~Saurabh Kapoor|ShoppingMallTranFG.BANK_NAME~RBL Bank, India|ShoppingMallTranFG.FREQUENCY~ADHO|ShoppingMallTranFG.DEBIT_TYPE~MAX|ShoppingMallTranFG.START_DATE~05/05/2019|ShoppingMallTranFG.END_DATE~05/04/2021|ShoppingMallTranFG.AMOUNT~4759|ShoppingMallTranFG.DEBIT_AC_NO~309006811222|ShoppingMallTranFG.SESSION_ID~5764931827135429";
					String token = AES_Encrption.getRblencrypt(s,"29304E8758327892");
					System.out.println("token>>>>" + token);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}



