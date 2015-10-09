/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 * 
 *  提示：如何获取安全校验码和合作身份者id
 *  1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *  2.点击“商家服务”(https://b.alipay.com/order/myorder.htm)
 *  3.点击“查询合作者身份(pid)”、“查询安全校验码(key)”
 */

package com.app.pay.alipay;

//
// 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
// 这里签名时，只需要使用生成的RSA私钥。
// Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。
public final class AliKeys {

	// 合作身份者id，以2088开头的16位纯数字
	public static final String DEFAULT_PARTNER = "2088021150296989";

	// 收款支付宝账号
	public static final String DEFAULT_SELLER = "zhifubao@tenongwang.com";

	// 商户私钥，自助生成  
	public static final String PRIVATE = "MIICXgIBAAKBgQDV4M5jHRGctFkdfpbrCvuZEj/cFU3YyH8oR3r+OsV38OOdQ5rq5YLIPPeTQKo9FpSkWVT8WM+alTr1ZygdFtK9bdypg6Bz2kEKcXo+zQN+HP5HuPVsY/uzIF/goGdAXm2BD2YFF82Ri1LNkDfjQOqDYwG8auD/emtOS6rVANy97QIDAQABAoGBAMd8eiQZ7d5ynJgVBaDPSqKYUfXt7QEg6PFh0ocjvCFGDpcVgn5LebNYoja3XtHBqPyt0cXY9DIjtK1F/TU8n2uZdzOE/wPlLQHENlMcWZjB3ROu8HYewJnJLcI7CKcABE69fBZpc5YbVwAXJNbQl83Iw+4cMaWUl6vRTFJWiYOhAkEA6nIruVv3+kGcBCVxneRr9JkkMDHvjX8eVB1na7/3d1Kp5swx6hhhWb31KKXd4ti8pIm1wu1HH1dwl0wjQcsTlQJBAOmKjxgXRl009m+KyaWFh6rBdgVju6CuLF3mOyLb76qLiEDZoj1O3mxZ6PNlQFubxgvioL+3POrntL3p7QyPavkCQCskb19gyTVINYmdylaf3i3YsIwd5FNJfS9Fae6uGL/gelt6YAUQD3oQNrkQLNC9yZkNrmD8gQYHdTSiUiqdUDECQQCiFf18XeMDG5gSEEQlcgiXtY+g6/gz6851GS5f4j9zoUXepG2amVt73eb0zogdTH7mjtK8XIOWEVx7FpjkR8GZAkEAyL7ExlLMkN4ffVzxh7ClZTCJNPPYArSBRHu2Ina9SrJdmwBBvrKhK0b2IQeDcz/FMSSqp5RUVoDltj3c2thhSA==";

	// 商户商户公钥，验证上传
	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDV4M5jHRGctFkdfpbrCvuZEj/cFU3YyH8oR3r+OsV38OOdQ5rq5YLIPPeTQKo9FpSkWVT8WM+alTr1ZygdFtK9bdypg6Bz2kEKcXo+zQN+HP5HuPVsY/uzIF/goGdAXm2BD2YFF82Ri1LNkDfjQOqDYwG8auD/emtOS6rVANy97QIDAQAB";
	
}
