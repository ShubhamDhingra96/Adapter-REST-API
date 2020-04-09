package snap.ib.personelLoan.adapter.common.service;

import snap.ib.personelLoan.adapter.common.exception.CommonException;
import snap.ib.personelLoan.adapter.common.request.AndhraBankResDataRequest;
import snap.ib.personelLoan.adapter.common.request.AndhraTokenRequest;
import snap.ib.personelLoan.adapter.common.request.CommonRequest;
import snap.ib.personelLoan.adapter.common.request.RblTokenRequest;
import snap.ib.personelLoan.adapter.common.request.YesBankDataRequest;
import snap.ib.personelLoan.adapter.common.request.YesBankRequest;
import snap.ib.personelLoan.adapter.common.request.YesBankResponse;
import snap.ib.personelLoan.adapter.common.response.AndhraResponse;
import snap.ib.personelLoan.adapter.common.response.AndhraTokenResponse;
import snap.ib.personelLoan.adapter.common.response.CommonResponse;

public interface BankingIntegrationService {

	public YesBankResponse yesBankRedirection(CommonRequest request) throws CommonException;

	public YesBankResponse yesBankDualVarification(CommonRequest request)throws CommonException;

	public AndhraTokenResponse generateTokenForAndhraBank(CommonRequest request);
	public AndhraResponse getandhraBankresponseGen(CommonRequest request) throws CommonException;

	public CommonResponse generateTokenForRbl(CommonRequest request);
}
