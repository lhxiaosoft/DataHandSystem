package edu.csu.model;


public class STU_INFO extends BaseModel{
	private String STU_INFO_ID;
	private String REC_ID;
	private String recName;   //招生点名称
	private String STU_CODE;
	private String STU_NAME;
	private String STU_SEX;
	private String ADM_TIME;
	private String MASTER;
	private String GRA_STATE;
	private long FIR_PAY;
	private long FIR_PAYBACK;
	private String FIR_PAYBACK_TIME;
	private long SEC_PAY;
	private long SEC_PAYBACK;
	private long THI_PAY;
	private String SEC_PAYBACK_TIME;
	private long ALL_PAY;
	private long ALL_LESSPAY;
	private String REMARK;
	private long SHOULD_PAY;
	private String  LESSPAY_STATE;
	public String getSTU_INFO_ID() {
		return STU_INFO_ID;
	}
	public void setSTU_INFO_ID(String sTU_INFO_ID) {
		STU_INFO_ID = sTU_INFO_ID;
	}
	public String getREC_ID() {
		return REC_ID;
	}
	public void setREC_ID(String rEC_ID) {
		REC_ID = rEC_ID;
	}
	
	public String getRecName() {
		return recName;
	}
	public void setRecName(String recName) {
		this.recName = recName;
	}
	public String getSTU_CODE() {
		return STU_CODE;
	}
	public void setSTU_CODE(String sTU_CODE) {
		STU_CODE = sTU_CODE;
	}
	public String getSTU_NAME() {
		return STU_NAME;
	}
	public void setSTU_NAME(String sTU_NAME) {
		STU_NAME = sTU_NAME;
	}
	public String getSTU_SEX() {
		return STU_SEX;
	}
	public void setSTU_SEX(String sTU_SEX) {
		STU_SEX = sTU_SEX;
	}
	public String getADM_TIME() {
		return ADM_TIME;
	}
	public void setADM_TIME(String aDM_TIME) {
		ADM_TIME = aDM_TIME;
	}
	public String getMASTER() {
		return MASTER;
	}
	public void setMASTER(String mASTER) {
		MASTER = mASTER;
	}
	public String getGRA_STATE() {
		return GRA_STATE;
	}
	public void setGRA_STATE(String gRA_STATE) {
		GRA_STATE = gRA_STATE;
	}
	public long getFIR_PAY() {
		return FIR_PAY;
	}
	public void setFIR_PAY(String fIR_PAY) {
		if( fIR_PAY==null|| fIR_PAY.equals("")){
			FIR_PAY=0;
		}else{
			FIR_PAY = Long.parseLong(fIR_PAY);
		}
		
	}
	public long getFIR_PAYBACK() {
		return FIR_PAYBACK;
	}
	public void setFIR_PAYBACK(String fIR_PAYBACK) {
		if(fIR_PAYBACK==null || fIR_PAYBACK.equals("")){
			FIR_PAYBACK=0;
		}else{
			FIR_PAYBACK = Long.parseLong(fIR_PAYBACK);
		}
		
	}
	
	public String getFIR_PAYBACK_TIME() {
		if(FIR_PAYBACK_TIME==null){
			return FIR_PAYBACK_TIME;
		}
		else{
			if(FIR_PAYBACK_TIME.split(" ")[0].equals("1900-01-01")){
				return null;
			}
			else{
				return FIR_PAYBACK_TIME.split(" ")[0];
			}
		}
		
	}
	public void setFIR_PAYBACK_TIME(String fIR_PAYBACK_TIME) {
		FIR_PAYBACK_TIME = fIR_PAYBACK_TIME;
	}
	
	public String getSEC_PAYBACK_TIME() {
		if(SEC_PAYBACK_TIME==null){
			return SEC_PAYBACK_TIME;
		}else{
			if(SEC_PAYBACK_TIME.split(" ")[0].equals("1900-01-01")){
				return null;
			}else{
				return SEC_PAYBACK_TIME.split(" ")[0];
			}
			
		}
	}
	public void setSEC_PAYBACK_TIME(String sEC_PAYBACK_TIME) {
		SEC_PAYBACK_TIME = sEC_PAYBACK_TIME;
	}
	public long getSEC_PAY() {
		return SEC_PAY;
	}
	public void setSEC_PAY(String sEC_PAY) {
		if(sEC_PAY==null || sEC_PAY.equals("")){
			SEC_PAY=0;
		}else{
			SEC_PAY = Long.parseLong(sEC_PAY);
		}
		
	}
	public long getSEC_PAYBACK() {
		return SEC_PAYBACK;
	}
	public void setSEC_PAYBACK(String sEC_PAYBACK) {
		if(sEC_PAYBACK==null || sEC_PAYBACK.equals("")){
			SEC_PAYBACK=0;
		}else{
			SEC_PAYBACK = Long.parseLong(sEC_PAYBACK);
		}
		
	}
	

	public long getTHI_PAY() {
		return THI_PAY;
	}
	public void setTHI_PAY(String tHI_PAY) {
		if(tHI_PAY==null || tHI_PAY.equals("")){
			THI_PAY=0;
		}else{
			THI_PAY = Long.parseLong(tHI_PAY);
		}
	}
	public long getALL_PAY() {
		return ALL_PAY;
	}
	public void setALL_PAY(String aLL_PAY) {
		ALL_PAY = Long.parseLong(aLL_PAY);
	}
	public long getALL_LESSPAY() {
		return ALL_LESSPAY;
	}
	public void setALL_LESSPAY(String aLL_LESSPAY) {
		ALL_LESSPAY = Long.parseLong(aLL_LESSPAY);
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public long getSHOULD_PAY() {
		return SHOULD_PAY;
	}
	public void setSHOULD_PAY(String sHOULD_PAY) {
		if(sHOULD_PAY==null || sHOULD_PAY.equals("") ){
			SHOULD_PAY=0;
		}else{
			SHOULD_PAY = Long.parseLong(sHOULD_PAY);
		}
		
	}
	public String getLESSPAY_STATE() {
		return LESSPAY_STATE;
	}
	public void setLESSPAY_STATE(String lESSPAY_STATE) {
		LESSPAY_STATE = lESSPAY_STATE;
	}
	

}
