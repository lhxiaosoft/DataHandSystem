package edu.csu.model.vo;

import edu.csu.model.BaseModel;

public class PayInfoCalVo extends BaseModel{
	private String REC_ID;
	private String REC_NAME;
	private String REC_YEAR;
	private int REC_NUMBER;
	private String RetId;
	private String RetTimes;
	private long RetUnitPrice;
	private long HavRet;
	private long HAD_RET;
	private long TAL_BANLANCE;
	private String Remark;
	public String getREC_ID() {
		return REC_ID;
	}
	public void setREC_ID(String rEC_ID) {
		REC_ID = rEC_ID;
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
	public String getRetId() {
		return RetId;
	}
	public void setRetId(String retId) {
		RetId = retId;
	}
	public String getRetTimes() {
		return RetTimes;
	}
	public void setRetTimes(String retTimes) {
		RetTimes = retTimes;
	}
	public long getRetUnitPrice() {
		return RetUnitPrice;
	}
	public void setRetUnitPrice(long retUnitPrice) {
		RetUnitPrice = retUnitPrice;
	}
	public long getHavRet() {
		return HavRet;
	}
	public void setHavRet(long havRet) {
		HavRet = havRet;
	}
	
	public long getHAD_RET() {
		return HAD_RET;
	}
	public void setHAD_RET(long hAD_RET) {
		HAD_RET = hAD_RET;
	}
	public long getTAL_BANLANCE() {
		return TAL_BANLANCE;
	}
	public void setTAL_BANLANCE(long tAL_BANLANCE) {
		TAL_BANLANCE = tAL_BANLANCE;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	
	
	
}
