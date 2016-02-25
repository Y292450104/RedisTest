package com.test.httpClient;

import java.beans.Encoder;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * ��װһ��http����Ĺ�����
 *
 * @author ��쿡�guwei�� on 14-4-22.����3:17
 */
public class HttpClientUtils {
	  //private static final Log log = LogFactory.getLog(HttpClientUtils.class);
    /**
     * ��ʼ��HttpClient
     */
    private static HttpClient httpClient = null;
 
    /**
     * ����HttpClientʵ��
     * ��������̬�Ĺ�����������Ҫʹ��ʱ��ȥ�����õ���
     *
     * @return
     */
    public static HttpClient getHttpClient() {
        if (httpClient == null) {
            //httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager());
        	//httpClient = HttpClientBuilder.create().build();
            httpClient = HttpClients.createDefault();
        }
        return httpClient;
    }
 
    /**
     * POST��ʽ����
     *
     * @param url
     * @param params ����ΪNameValuePair��ֵ�Զ���
     * @return ��Ӧ�ַ���
     * @throws java.io.UnsupportedEncodingException
     */
    public static String executeByPost(String url, List<NameValuePair> params) {
        HttpClient httpclient = getHttpClient();
 
        HttpPost post = new HttpPost(url);
 
 
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseJson = null;
        try {
            if (params != null) {
                post.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
            }
            responseJson = httpclient.execute(post, responseHandler);
            System.out.println("HttpClient POST��������" + responseJson);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            System.out.println("HttpClient POST�����쳣��" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpclient.getConnectionManager().closeExpiredConnections();
            httpclient.getConnectionManager().closeIdleConnections(30, TimeUnit.SECONDS);
        }
        return responseJson;
    }
 
    /**
     * Get��ʽ����
     *
     * @param url    ������ռλ����URL������http://User/user/center.aspx?_action=GetSimpleUserInfo&codes={0}&email={1}
     * @param params ����ֵ���飬��Ҫ��url��ռλ��˳���Ӧ
     * @return ��Ӧ�ַ���
     * @throws java.io.UnsupportedEncodingException
     */
    public static String executeByGet(String url, Object[] params) {
        HttpClient httpclient = getHttpClient();
 
        String messages = MessageFormat.format(url, params);
 
        HttpGet get = new HttpGet(messages);
 
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseJson = null;
        try {
            responseJson = httpclient.execute(get, responseHandler);
            System.out.println("HttpClient GET��������" + responseJson);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            System.out.println("HttpClient GET�����쳣��" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("HttpClient GET�����쳣��" + e.getMessage());
        } finally {
            httpclient.getConnectionManager().closeExpiredConnections();
            httpclient.getConnectionManager().closeIdleConnections(30, TimeUnit.SECONDS);
        }
        return responseJson;
    }
 
    /**
     * @param url
     * @return
     */
    public static String executeByGet(String url) {
        HttpClient httpclient = getHttpClient();
 
        HttpGet get = new HttpGet(url);
 
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseJson = null;
        try {
            responseJson = httpclient.execute(get, responseHandler);
            System.out.println("HttpClient GET��������" + responseJson);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            System.out.println("HttpClient GET�����쳣��" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("HttpClient GET�����쳣��" + e.getMessage());
        } finally {
            httpclient.getConnectionManager().closeExpiredConnections();
            httpclient.getConnectionManager().closeIdleConnections(30, TimeUnit.SECONDS);
        }
        return responseJson;
    }
    
    public static String get(String url) {
    	HttpClient httpclient = getHttpClient();
    	HttpGet get = new HttpGet(url);
    	String responseJson = null;
    	HttpResponse response = null;
    	try {
    		response = httpclient.execute(get);
    		System.out.println(response.getStatusLine());
    		if (response.getStatusLine().getStatusCode() == 200) {
    			responseJson = EntityUtils.toString(response.getEntity());
    		}
    		
    	} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (response instanceof CloseableHttpResponse) {
				try {
					((CloseableHttpResponse) response).close();
					System.out.println("Close Response");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    	
    	return responseJson;
    }
    
    public static void main(String[] args) {
		String str1 = HttpClientUtils.get("http://192.168.110.245/");
		System.out.println(str1);
		String str2 = HttpClientUtils.executeByGet("http://192.168.110.245/");
		System.out.println(str2);
		String str3 = HttpClientUtils.get("http://192.168.110.245/");
		System.out.println(str3);
		String str4 = HttpClientUtils.get("http://www.baidu.com");
		System.out.println(str4);
	}
  
}