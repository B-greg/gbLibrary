package com.smartsoftasia.module.gblibrary.helper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class MD5 {
	
	static SecureRandom sr = null;
	
	public static String random() {
		if (sr == null) {
			sr = new SecureRandom();
		}
		
		return encrypt(new BigInteger(130, sr).toString(32));
	}
	
	public static String encrypt(String string){
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			digest.reset();			
			digest.update(string.getBytes());
			byte[] a = digest.digest();
			int len = a.length;
			StringBuilder sb = new StringBuilder(len << 1);
			for(int i =0; i<len; i++){
				sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16 ));
				sb.append(Character.forDigit(a[i] & 0x0f, 16 ));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
