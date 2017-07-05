package edu.csu.model.vo;

import edu.csu.model.BaseModel;

public class RetMoneyInfoVo extends BaseModel {
	private String REC_ID;
	private String REC_NAME;
	private int REC_NUMBER;
	private String RET_ID;
	private String RET_TIMES;
	private int SHO_UNIT_PRICE;
	private int RET_UNIT_PRICE;
	private long HAV_RET;
	private long HAD_RET;
	private long TAL_BANLANCE;
	private String REMARK;
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
	
	
	
	public int getREC_NUMBER() {
		return REC_NUMBER;
	}
	public void setREC_NUMBER(int rEC_NUMBER) {
		REC_NUMBER = rEC_NUMBER;
	}
	public String getRET_ID() {
		return RET_ID;
	}
	public void setRET_ID(String rET_ID) {
		RET_ID = rET_ID;
	}
	public String getRET_TIMES() {
		return RET_TIMES;
	}
	public void setRET_TIMES(String rET_TIMES) {
		RET_TIMES = rET_TIMES;
	}
//	public int getRET_UNIT_PRICE() {
//		return RET_UNIT_PRICE;
//	}
	
	
//	public int getSHO_UNIT_PRICE() {
//		return SHO_UNIT_PRICE;
//	}
//	public void setSHO_UNIT_PRICE(String sHO_UNIT_PRICE) {
//		if(sHO_UNIT_PRICE.equals("")){
//			SHO_UNIT_PRICE=0;
//		}else{
//			SHO_UNIT_PRICE = Integer.parseInt(sHO_UNIT_PRICE);
//		}
//	}
	
//	public void setRET_UNIT_PRICE(String rET_UNIT_PRICE) {
//		if(rET_UNIT_PRICE.equals("")){
//			RET_UNIT_PRICE=0;
//		}else{
//			RET_UNIT_PRICE = Integer.parseInt(rET_UNIT_PRICE);
//		}
//	}
	public int getSHO_UNIT_PRICE() {
		return SHO_UNIT_PRICE;
	}
	public void setSHO_UNIT_PRICE(int sHO_UNIT_PRICE) {
		SHO_UNIT_PRICE = sHO_UNIT_PRICE;
	}
	
	public int getRET_UNIT_PRICE() {
		return RET_UNIT_PRICE;
	}
	public void setRET_UNIT_PRICE(int rET_UNIT_PRICE) {
		RET_UNIT_PRICE = rET_UNIT_PRICE;
	}
	public long getHAV_RET() {
		return HAV_RET;
	}
	
	public void setHAV_RET(long hAV_RET) {
			HAV_RET = hAV_RET;
		}
	//	public void setHAV_RET(String hAV_RET) {
//		if(hAV_RET.equals("")){
//			HAV_RET=0;
//		}else{
//			HAV_RET = Long.parseLong(hAV_RET);
//		}
//	}
	public long getHAD_RET() {
		return HAD_RET;
	}
	
//	public void setHAD_RET(String hAD_RET) {
//		if(hAD_RET.equals("")){
//			HAD_RET=0;
//		}else{
//			HAD_RET = Long.parseLong(hAD_RET);
//		}
//	}
	
	public void setHAD_RET(long hAD_RET) {
		HAD_RET = hAD_RET;
	}
	public long getTAL_BANLANCE() {
		return TAL_BANLANCE;
	}
	
	public void setTAL_BANLANCE(long tAL_BANLANCE) {
			TAL_BANLANCE = tAL_BANLANCE;
		}
	//	public void setTAL_BANLANCE(String tAL_BANLANCE) {
//		if(tAL_BANLANCE.equals("")){
//			TAL_BANLANCE=0;
//		}else{
//			TAL_BANLANCE = Long.parseLong(tAL_BANLANCE);
//		}
//	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	
	

}
