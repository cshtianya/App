package com.tnw.api;

public class Base64_Encode_PHP {
	//对应php里的 base64_decode 方法
	private static final String base64code = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
			+ "abcdefghijklmnopqrstuvwxyz" + "0123456789" + "+/[]{}:,.\"";
	private static final int splitLinesAt = 20480; //76 分割以多少个长度进行,这个对于特别长的参数

	/**
	 * 接口数据加密
	 * @param strings
	 * @return
	 */
	public static String encode(String strings) {
		String tedemo = encodeBase64(strings.trim());
		String teststr = tedemo.substring(0, 1).trim();
		String newssts = tedemo.substring(1, tedemo.length()).trim();
		String tempstrs = newssts.concat(teststr).toString().trim();
		// 再次进行编码
		String resultsss = encodeBase64(tempstrs).toString().trim();
		return resultsss;
	}
	
	private static byte[] zeroPad(int length, byte[] bytes) {
		// initialized to zero by JVM
		byte[] padded = new byte[length];
		System.arraycopy(bytes, 0, padded, 0, bytes.length);
		return padded;
	}

	private static String encodeBase64(String string) {
		String encoded = "";
		byte[] stringArray;
		try {
			// use appropriate encoding string!
			stringArray = string.getBytes("UTF-8");
		} catch (Exception ignored) {
			// use locale default rather than croak
			stringArray = string.getBytes();
		}
		// determine how many padding bytes to add to the output
		int paddingCount = (3 - (stringArray.length % 3)) % 3;
		// add any necessary padding to the input
		stringArray = zeroPad(stringArray.length + paddingCount, stringArray);
		// process 3 bytes at a time, churning out 4 output bytes
		// worry about CRLF insertions later
		for (int i = 0; i < stringArray.length; i += 3) {
			int j = ((stringArray[i] & 0xff) << 16)
					+ ((stringArray[i + 1] & 0xff) << 8)
					+ (stringArray[i + 2] & 0xff);
			encoded = encoded + base64code.charAt((j >> 18) & 0x3f)
					+ base64code.charAt((j >> 12) & 0x3f)
					+ base64code.charAt((j >> 6) & 0x3f)
					+ base64code.charAt(j & 0x3f);
		}
		// replace encoded padding nulls with "="
		return splitLines(encoded.substring(0, encoded.length() - paddingCount)
				+ "==".substring(0, paddingCount));
	}
	//分行
	private static String splitLines(String string) {
		String lines = "";
		for (int i = 0; i < string.length(); i += splitLinesAt) {
			lines += string.substring(i,
					Math.min(string.length(), i + splitLinesAt));
			lines += "\r\n";
		}
		return lines;
	}

}
