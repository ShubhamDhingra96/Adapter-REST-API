package snap.ib.personelLoan.adapter.common.request;

public class EMIPaymentUpdateRequest {

	private String txn_status;
	private String txn_msg;
	private String txn_err_msg;
	private String clnt_txn_ref;
	private String tpsl_bank_cd;
	private String tpsl_txn_id;
	private String txn_amt;
	private String clnt_rqst_meta;
	private String tpsl_txn_time;
	private String transaction_id;
	private String device_id;
	private String session_key;

	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getSession_key() {
		return session_key;
	}
	public String getTxn_status() {
		return txn_status;
	}

	public void setTxn_status(String txn_status) {
		this.txn_status = txn_status;
	}

	public String getTxn_msg() {
		return txn_msg;
	}

	public void setTxn_msg(String txn_msg) {
		this.txn_msg = txn_msg;
	}

	public String getTxn_err_msg() {
		return txn_err_msg;
	}

	public void setTxn_err_msg(String txn_err_msg) {
		this.txn_err_msg = txn_err_msg;
	}

	public String getClnt_txn_ref() {
		return clnt_txn_ref;
	}

	public void setClnt_txn_ref(String clnt_txn_ref) {
		this.clnt_txn_ref = clnt_txn_ref;
	}

	public String getTpsl_bank_cd() {
		return tpsl_bank_cd;
	}

	public void setTpsl_bank_cd(String tpsl_bank_cd) {
		this.tpsl_bank_cd = tpsl_bank_cd;
	}

	public String getTpsl_txn_id() {
		return tpsl_txn_id;
	}

	public void setTpsl_txn_id(String tpsl_txn_id) {
		this.tpsl_txn_id = tpsl_txn_id;
	}

	public String getTxn_amt() {
		return txn_amt;
	}

	public void setTxn_amt(String txn_amt) {
		this.txn_amt = txn_amt;
	}

	public String getClnt_rqst_meta() {
		return clnt_rqst_meta;
	}

	public void setClnt_rqst_meta(String clnt_rqst_meta) {
		this.clnt_rqst_meta = clnt_rqst_meta;
	}

	public String getTpsl_txn_time() {
		return tpsl_txn_time;
	}

	public void setTpsl_txn_time(String tpsl_txn_time) {
		this.tpsl_txn_time = tpsl_txn_time;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
}
