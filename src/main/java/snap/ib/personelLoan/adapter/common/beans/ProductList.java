package snap.ib.personelLoan.adapter.common.beans;

import java.util.ArrayList;

public class ProductList {

	private String productName;
	private String productType;
	private Integer minAmount;
	private Integer maxAmount;
	private ArrayList<String> tenure;
	private ArrayList<String> selfEmpTenure;
	
	public ArrayList<String> getSelfEmpTenure() {
		return selfEmpTenure;
	}
	public void setSelfEmpTenure(ArrayList<String> selfEmpTenure) {
		this.selfEmpTenure = selfEmpTenure;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public Integer getMinAmount() {
		return minAmount;
	}
	public void setMinAmount(Integer minAmount) {
		this.minAmount = minAmount;
	}
	public Integer getMaxAmount() {
		return maxAmount;
	}
	public void setMaxAmount(Integer maxAmount) {
		this.maxAmount = maxAmount;
	}
	public ArrayList<String> getTenure() {
		return tenure;
	}
	public void setTenure(ArrayList<String> tenure) {
		this.tenure = tenure;
	}
	
}
