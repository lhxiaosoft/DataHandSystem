package edu.csu.model;

public class REC_POINT extends BaseModel{
	private String REC_ID;
	private String REC_CODE;
	private String REC_NAME;
	private String REC_YEAR;
	private int REC_NUMBER;
	private int REC_SHOUNIT_PRICE;
	private String REMARK;
	public String getREC_ID() {
		return REC_ID;
	}
	public void setREC_ID(String rEC_ID) {
		REC_ID = rEC_ID;
	}
	public String getREC_CODE() {
		return REC_CODE;
	}
	public void setREC_CODE(String rEC_CODE) {
		REC_CODE = rEC_CODE;
	}
	public String getREC_NAME() {
		return REC_NAME;
	}
	public void setREC_NAME(String rEC_NAME) {
		REC_NAME = rEC_NAME;
	}
	public String getREC_YEAR() {
		return REC_YEAR;
	}
	public void setREC_YEAR(String rEC_YEAR) {
		REC_YEAR = rEC_YEAR;
	}
	public int getREC_NUMBER() {
		return REC_NUMBER;
	}
	public void setREC_NUMBER(int rEC_NUMBER) {
		REC_NUMBER = rEC_NUMBER;
	}
	
	public int getREC_SHOUNIT_PRICE() {
		return REC_SHOUNIT_PRICE;
	}
	public void setREC_SHOUNIT_PRICE(int rEC_SHOUNIT_PRICE) {
		REC_SHOUNIT_PRICE = rEC_SHOUNIT_PRICE;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	
}
