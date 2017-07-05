package edu.csu.utils;

import java.util.ArrayList;
import java.util.List;

public class StoreCodeDivide {
	private List Divide(String StoreCode){
	    List storecodediv=new ArrayList();
		String str1=StoreCode.substring(0,2);
		String str2=StoreCode.substring(3, 5);
		String str3=StoreCode.substring(6,10);
		String str4=StoreCode.substring(10);
		storecodediv.add(str1);
		storecodediv.add(str2);
		storecodediv.add(str3);
		storecodediv.add(str4);
		return storecodediv;
	}
	 public static void main(String[] args) {
	    String code="04.02.10010001";
	    StoreCodeDivide storeCodeDivide=new StoreCodeDivide();
	    String str1=(String) storeCodeDivide.Divide(code).get(0);
	    String str2=(String) storeCodeDivide.Divide(code).get(1);
	    String str3=(String) storeCodeDivide.Divide(code).get(2);
	    String str4=(String) storeCodeDivide.Divide(code).get(3);
	    System.out.println(str1);
	    System.out.println(str2);
	    System.out.println(str3);
	    System.out.println(str4);
		}
}
