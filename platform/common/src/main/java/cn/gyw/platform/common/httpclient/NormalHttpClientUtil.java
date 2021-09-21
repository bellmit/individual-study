package cn.gyw.platform.common.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 普通的HttpClientUtil
 */
public class NormalHttpClientUtil {

    /**
     * GET 请求
     *
     * @param url 请求的URL
     * @return 字符串
     */
    public static String sendGetRequest(String url) {
        return sendGetRequest(url, null);
    }

    /**
     * GET 请求
     *
     * @param url   请求的URL
     * @param param map 请求参数
     * @return 字符串
     */
    public static String sendGetRequest(String url, Map<String, String> param) {
        // 创建HttpClient 对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 设置请求配置
        RequestConfig requestConfig = buildRequestConfig();
        // 服务器响应值
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建URI，并构建参数
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            // 创建Http GET 请求
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            } else {
                resultString = String.valueOf(response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * POST请求
     *
     * @param url 请求的URL
     * @return 字符串
     */
    public static String sendPostRequest(String url) {
        return sendPostRequest(url, null);
    }

    /**
     * POST请求
     * 说明：FORM 表单请求
     * @param url   请求的URL
     * @param param map 请求参数
     * @return 字符串
     */
    public static String sendPostRequest(String url, Map<String, String> param) {
        // 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 设置请求配置
        RequestConfig requestConfig = buildRequestConfig();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟Form表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * POST请求
     * 说明：JSON 字符串请求
     * @param url 请求的URL
     * @param json json字符串
     * @return 字符串
     */
    public static String sendPostRequestJson(String url, String json) {
        // 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = buildRequestConfig();

        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            // 创建JSON请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行Http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * POST请求
     * 说明：File 文件流
     * @param url 请求的URL
     * @param file 文件
     * @param fileName 文件名
     * @return 字符串
     */
    public static String sendPostRequestFile(String url, File file, String fileName) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 设置请求配置
        RequestConfig requestConfig = buildRequestConfig();

        CloseableHttpResponse httpResponse = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            // 创建文件流参数
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            multipartEntityBuilder.setCharset(Charset.forName("UTF-8"));
            multipartEntityBuilder.addBinaryBody("Filedata", file, ContentType.DEFAULT_BINARY, fileName);
            multipartEntityBuilder.addPart("Filename",
                    new StringBody(fileName, ContentType.create("text/plain", "utf-8")));
            HttpEntity httpEntity = multipartEntityBuilder.build();
            httpPost.setEntity(httpEntity);

            httpResponse = httpClient.execute(httpPost);
            HttpEntity responseEntity = httpResponse.getEntity();
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(responseEntity.getContent()));
                StringBuffer buffer = new StringBuffer();
                String line = null;
                while (((line = reader.readLine()) != null)) {
                    buffer.append(line);
                }
                return buffer.toString();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "failed";
    }

    private static RequestConfig buildRequestConfig() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)// 设置连接超时时间
                .setConnectionRequestTimeout(5000)// 设置请求超时时间
                .setSocketTimeout(50000).setRedirectsEnabled(true)// 默认允许自动重定向
                .build();
        return requestConfig;
    }

        //响应处理器
/*	CloseableHttpClient httpclient = HttpClients.createDefault();
	HttpGet httpget = new HttpGet("http://localhost/json");
	ResponseHandler<MyJsonObject> rh = new ResponseHandler<MyJsonObject>() {
	    @Override
	    public JsonObject handleResponse(final HttpResponse response) throws IOException {
	        StatusLine statusLine = response.getStatusLine();
	        HttpEntity entity = response.getEntity();
	        if (statusLine.getStatusCode() >= 300) {
	            throw new HttpResponseException(statusLine.getStatusCode(),
	                statusLine.getReasonPhrase());
	        }
	        if (entity == null) {
	             throw new ClientProtocolException("Response contains no content");
	        }
	        Gson gson = new GsonBuilder().create();
	        ContentType contentType = ContentType.getOrDefault(entity);
	        Charset charset = contentType.getCharset();
	        Reader reader = new InputStreamReader(entity.getContent(), charset);
	        return gson.fromJson(reader, MyJsonObject.class);
	    }
	};
	MyJsonObject myjson = client.execute(httpget, rh);*/
}
