package edu.csu.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;


/**
 * 编码工具类
 * 1.将byte[]转为各种进制的字符串a
 * 2.base 64 encode
 * 3.base 64 decode
 * 4.获取byte[]的md5值
 * 5.获取字符串md5值
 * 6.结合base64实现md5加密
 * 7.AES加密
 * 8.AES加密为base 64 code
 * 9.AES解密
 * 10.将base 64 code AES解密
 * @author uikoo9
 * @version 0.0.7.20140601
 */
public class QEncodeUtil {
	
	private static final String KEY = "CSUEDU";
	public static void main(String[] args) throws Exception {
		String content = "admin";
		System.out.println("加密前：" + content);

		String key = "abcdfef";
		System.out.println("加密密钥和解密密钥：" + key);
		
	/*	String encrypt = aesEncrypt(content, key);
		System.out.println("加密后：" + encrypt);
		
		String decrypt = aesDecrypt(encrypt, key);
		System.out.println("解密后：" + decrypt);*/
		
		String str = md5Encrypt(content);
		System.out.println("MD5:"+str);
	}
	
	/**
	 * 将byte[]转为各种进制的字符串
	 * @param bytes byte[]
	 * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
	 * @return 转换后的字符串
	 */
	public static String binary(byte[] bytes, int radix){
		return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
	}
	
	/**
	 * base 64 encode
	 * @param bytes 待编码的byte[]
	 * @return 编码后的base 64 code
	 */
	public static String base64Encode(byte[] bytes){
		return new BASE64Encoder().encode(bytes);
	}
	
	/**
	 * base 64 decode
	 * @param base64Code 待解码的base 64 code
	 * @return 解码后的byte[]
	 * @throws Exception
	 */
	public static byte[] base64Decode(String base64Code) throws Exception{
		return CommonTools.isNullString(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
	}
	
	/**
	 * 获取byte[]的md5值
	 * @param bytes byte[]
	 * @return md5
	 * @throws Exception
	 */
	public static byte[] md5(byte[] bytes) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(bytes);
			return md.digest();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 获取字符串md5值
	 * @param msg 
	 * @return md5
	 * @throws Exception
	 */
	public static byte[] md5(String msg)  {
		return CommonTools.isNullString(msg) ? null : md5(msg.getBytes());
	}
	
	/**
	 * 结合base64实现md5加密
	 * @param msg 待加密字符串
	 * @return 获取md5后转为base64
	 * @throws Exception
	 */
	public static String md5Encrypt(String msg){
		//return CommonTools.isNullString(msg) ? null : base64Encode(md5(msg));
		//与PDA加密保持一致
		 char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',  
		            'a', 'b', 'c', 'd', 'e', 'f' };  
		    try {  
		        byte[] strTemp = msg.getBytes();  
		        MessageDigest mdTemp = MessageDigest.getInstance("MD5");  
		        mdTemp.update(strTemp);  
		        byte[] md = mdTemp.digest();  
		        int j = md.length;  
		        char str[] = new char[j * 2];  
		        int k = 0;  
		        for (int i = 0; i < j; i++) {  
		            byte byte0 = md[i];  
		            str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
		            str[k++] = hexDigits[byte0 & 0xf];  
		        }  
		        return new String(str);  
		    } catch (Exception e) {  
		        e.printStackTrace();  
		        return null;  
		    }  
	}
	
	/**
	 * AES加密
	 * @param content 待加密的内容
	 * @param encryptKey 加密密钥
	 * @return 加密后的byte[]
	 * @throws Exception
	 */
	public static byte[] aesEncryptToBytes(String content) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128, new SecureRandom(KEY.getBytes()));

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
		
		return cipher.doFinal(content.getBytes("utf-8"));
	}
	
	/**
	 * AES加密为base 64 code
	 * @param content 待加密的内容
	 * @param encryptKey 加密密钥
	 * @return 加密后的base 64 code
	 * @throws Exception
	 */
	public static String aesEncrypt(String content) throws Exception {
		return base64Encode(aesEncryptToBytes(content));
	}
	
	/**
	 * AES解密
	 * @param encryptBytes 待解密的byte[]
	 * @param decryptKey 解密密钥
	 * @return 解密后的String
	 * @throws Exception
	 */
	public static String aesDecryptByBytes(byte[] encryptBytes) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128, new SecureRandom(KEY.getBytes()));
		
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
		byte[] decryptBytes = cipher.doFinal(encryptBytes);
		
		return new String(decryptBytes);
	}
	
	/**
	 * 将base 64 code AES解密
	 * @param encryptStr 待解密的base 64 code
	 * @param decryptKey 解密密钥
	 * @return 解密后的string
	 * @throws Exception
	 */
	public static String aesDecrypt(String encryptStr) throws Exception {
		return CommonTools.isNullString(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr));
	}
	
}