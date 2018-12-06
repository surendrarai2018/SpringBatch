package com.diaspark.csvtomongo.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.diaspark.csvtomongo.constant.SystemConstants;

public class CriptoUtil {

	public static String encrypt(String value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(SystemConstants.INIT_VECTOR.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(SystemConstants.KEY.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance(SystemConstants.CIPHER_KEY_CODE);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());
			
			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static String decrypt(String encrypted) {
		try {
			IvParameterSpec iv = new IvParameterSpec(SystemConstants.INIT_VECTOR.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(SystemConstants.KEY.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance(SystemConstants.CIPHER_KEY_CODE);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
			return new String(original);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
	
}
