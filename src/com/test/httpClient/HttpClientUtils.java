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
 * 分装一个http请求的工具类
 *
 * @author 顾炜【guwei】 on 14-4-22.下午3:17
 */
public class HttpClientUtils {
	  //private static final Log log = LogFactory.getLog(HttpClientUtils.class);
    /**
     * 初始化HttpClient
     */
    private static HttpClient httpClient = null;
 
    /**
     * 生产HttpClient实例
     * 公开，静态的工厂方法，需要使用时才去创建该单体
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
     * POST方式调用
     *
     * @param url
     * @param params 参数为NameValuePair键值对对象
     * @return 响应字符串
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
            System.out.println("HttpClient POST请求结果：" + responseJson);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            System.out.println("HttpClient POST请求异常：" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpclient.getConnectionManager().closeExpiredConnections();
            httpclient.getConnectionManager().closeIdleConnections(30, TimeUnit.SECONDS);
        }
        return responseJson;
    }
 
    /**
     * Get方式请求
     *
     * @param url    带参数占位符的URL，例：http://User/user/center.aspx?_action=GetSimpleUserInfo&codes={0}&email={1}
     * @param params 参数值数组，需要与url中占位符顺序对应
     * @return 响应字符串
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
            System.out.println("HttpClient GET请求结果：" + responseJson);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            System.out.println("HttpClient GET请求异常：" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("HttpClient GET请求异常：" + e.getMessage());
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
            System.out.println("HttpClient GET请求结果：" + responseJson);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            System.out.println("HttpClient GET请求异常：" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("HttpClient GET请求异常：" + e.getMessage());
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