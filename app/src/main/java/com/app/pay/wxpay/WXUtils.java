package com.app.pay.wxpay;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.util.InetAddressUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WXUtils {
	
	private static final String TAG = "WXUtils";
	private static final boolean DEBUG = true;
	
	public static String getIp(Context context) {   
	      WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);   
	      WifiInfo wifiInfo = wifiManager.getConnectionInfo();   
	      int ipAddress = wifiInfo.getIpAddress();   
	         
	      // 格式化IP address，例如：格式化前：1828825280，格式化后：192.168.1.109   
	      String ip = String.format("%d.%d.%d.%d",   
	              (ipAddress & 0xff),   
	              (ipAddress >> 8 & 0xff),   
	              (ipAddress >> 16 & 0xff),   
	              (ipAddress >> 24 & 0xff));   
	      
	      return ip;   
	  } 
    
    public static String getLocalIpAddress() {  
        try {  
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {  
                NetworkInterface intf = en.nextElement();  
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {  
                    InetAddress inetAddress = enumIpAddr.nextElement();  
                    if (!inetAddress.isLoopbackAddress()&& InetAddressUtils.isIPv4Address(inetAddress.getHostAddress())) {  
                        return inetAddress.getHostAddress().toString();  
                    }  
                }  
            }  
        } catch (SocketException ex) {  
            return "192.168.1.1";  
        }  
        return "192.168.1.1";  
    }  
    
    public static byte[] httpGet(final String url) {

		HttpClient httpClient = getNewHttpClient();
		HttpGet httpGet = new HttpGet(url);

		try {
			HttpResponse resp = httpClient.execute(httpGet);
			if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				if(DEBUG)
					Log.e(TAG, "httpGet fail, status code = " + resp.getStatusLine().getStatusCode());
				return null;
			}

			return EntityUtils.toByteArray(resp.getEntity());

		} catch (Exception e) {
			if(DEBUG)
				Log.e(TAG, "httpGet exception, e = " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
    
    
    public static byte[] httpPost(String url, String entity) {
		if (url == null || url.length() == 0) {
			if(DEBUG)
				Log.e(TAG, "httpPost, url is null");
			return null;
		}
		
		HttpClient httpClient = getNewHttpClient();
		
		HttpPost httpPost = new HttpPost(url);
		
		try {
			httpPost.setEntity(new StringEntity(entity));
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			
			HttpResponse resp = httpClient.execute(httpPost);
			if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				if(DEBUG)
					Log.e(TAG, "httpGet fail, status code = " + resp.getStatusLine().getStatusCode());
				return null;
			}

			return EntityUtils.toByteArray(resp.getEntity());
		} catch (Exception e) {
			if (DEBUG) {
				Log.e(TAG, "httpPost exception, e = " + e.getMessage());
			}
			e.printStackTrace();
			return null;
		}
	}
    
    private static HttpClient getNewHttpClient() { 
 	   try { 
 	       KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType()); 
 	       trustStore.load(null, null); 

 	       SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore); 
 	       sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER); 

 	       HttpParams params = new BasicHttpParams(); 
 	       HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1); 
 	       HttpProtocolParams.setContentCharset(params, HTTP.UTF_8); 

 	       SchemeRegistry registry = new SchemeRegistry(); 
 	       registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80)); 
 	       registry.register(new Scheme("https", sf, 443)); 

 	       ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry); 

 	       return new DefaultHttpClient(ccm, params); 
 	   } catch (Exception e) { 
 	       return new DefaultHttpClient(); 
 	   } 
 	}
    
    
    private static class SSLSocketFactoryEx extends SSLSocketFactory {      
	      
	    SSLContext sslContext = SSLContext.getInstance("TLS");      
	      
	    public SSLSocketFactoryEx(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {      
	        super(truststore);      
	      
	        TrustManager tm = new X509TrustManager() {      
	      
	            public X509Certificate[] getAcceptedIssuers() {      
	                return null;      
	            }      
	      
				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain,	String authType) throws java.security.cert.CertificateException {
				}  
	        };      
	      
	        sslContext.init(null, new TrustManager[] { tm }, null);      
	    }      
	      
		@Override
		public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host,	port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		} 
	}  
    
}
