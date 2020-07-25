package com.withSoul.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class WxUtils {
	/**
	 * ��֤��Ϣ�Ϸ���
	 * @param signature
	 * @param paraStr
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean checkSingature(String signature,String...paraStr) throws NoSuchAlgorithmException {
		
		// ���ֵ�˳������
		Arrays.sort(paraStr);
		// �ַ���ƴ��
		StringBuilder content = new StringBuilder();
		for (String string : paraStr) {
			content.append(string);
		}
		// sha1����
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] digest = md.digest(content.toString().getBytes());
		String testingStr = byteArrayToHexString(digest);
		// �ȽϷ���
		if (testingStr.equalsIgnoreCase(signature.toUpperCase())) {
			return true;
		}
		return false;
	}
 
	// ���ֽ�����ת��Ϊʮ�������ַ���
	private static String byteArrayToHexString(byte[] bytearray) {
		String strDigest = "";
		for (int i = 0; i < bytearray.length; i++) {
			strDigest += byteToHexString(bytearray[i]);
		}
		return strDigest;
	}
 
	// ���ֽ�ת��Ϊʮ�������ַ���
	private static String byteToHexString(byte ib) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] ob = new char[2];
		ob[0] = Digit[(ib >>> 4) & 0X0F];
		ob[1] = Digit[ib & 0X0F];
		String s = new String(ob);
		return s;
	}

}
