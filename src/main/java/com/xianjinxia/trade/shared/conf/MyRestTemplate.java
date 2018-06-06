package com.xianjinxia.trade.shared.conf;

import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.beanutils.BeanMap;
import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.xianjinxia.logcenter.http.CatHttpClientFactory;

/**
 * 更换默认httpfactory
 * 
 * @author hym 2017年9月14日
 */
@Component
public class MyRestTemplate {

    @Autowired
    private MyGsonHttpMessageConverter myGsonHttpMessageConverter;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("restTemplateWithAbsoluteUrl")
    private RestTemplate restTemplateWithAbsoluteUrl;

    private final static String HTTP = "http://";

    @PostConstruct
    protected void init() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // SSLContext sslContext = new
        // SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
        // public boolean isTrusted(X509Certificate[] arg0, String arg1) throws
        // CertificateException {
        // return true;
        // }
        // }).build();
        // httpClientBuilder.setSSLContext(sslContext);
        // HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
        // SSLConnectionSocketFactory sslConnectionSocketFactory = new
        // SSLConnectionSocketFactory(sslContext, hostnameVerifier);
        // RegistryBuilder.<ConnectionSocketFactory>create().register("https",
        // sslConnectionSocketFactory)
        Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory()).build();// 注册http
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager =
                new PoolingHttpClientConnectionManager(socketFactoryRegistry);// 开始设置连接池
        poolingHttpClientConnectionManager.setMaxTotal(1000); // 最大连接数
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(600); // 同路由并发数
        httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(2, true));// 重试次数
        HttpClient httpClient = CatHttpClientFactory.createHttpClient(httpClientBuilder);
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        clientHttpRequestFactory.setConnectTimeout(10000);// 连接超时
        clientHttpRequestFactory.setReadTimeout(10000);// 数据读取超时时间
        clientHttpRequestFactory.setConnectionRequestTimeout(10000);// 连接不够用的等待时间
        restTemplate.getMessageConverters().add(0, myGsonHttpMessageConverter);
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        restTemplateWithAbsoluteUrl.getMessageConverters().add(0, myGsonHttpMessageConverter);
        restTemplateWithAbsoluteUrl.setRequestFactory(clientHttpRequestFactory);
    }

    /**
     * http post 请求 ParameterizedTypeReference<List<MyBean>> myBean = new
     * ParameterizedTypeReference<List<MyBean>>() {}; ResponseEntity<List<MyBean>> response =
     * template.exchange("http://example.com",HttpMethod.POST, requestObject, myBean);
     *
     * @param url
     * @param requestObject
     * @param responseType
     * @return
     */
    public <T> T httpPost(String url, Object requestObject,
            ParameterizedTypeReference<T> classType) {
        HttpHeaders httpHeader = new HttpHeaders();
        httpHeader.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(requestObject, httpHeader);
        return restTemplate.exchange(HTTP + url, HttpMethod.POST, httpEntity, classType).getBody();
    }

    public <T> T httpPostWithAbsoluteUrl(String url, Object requestObject,
            ParameterizedTypeReference<T> classType) {
        HttpHeaders httpHeader = new HttpHeaders();
        httpHeader.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(requestObject, httpHeader);
        return restTemplateWithAbsoluteUrl
                .exchange(HTTP + url, HttpMethod.POST, httpEntity, classType).getBody();
    }

    /**
     * http get 请求
     * 
     * @param url
     * @param
     * @return
     * @throws URISyntaxException
     * @throws RestClientException
     */
    public <T> T httpGet(String url, Object requestObject,
            ParameterizedTypeReference<T> classType) {
        try {
            return restTemplate.exchange(buildUrlForHttpGet(HTTP + url, requestObject),
                    HttpMethod.GET, null, classType).getBody();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T httpGetWithAbsoluteUrl(String url, Object requestObject,
            ParameterizedTypeReference<T> classType) {
        try {
            return restTemplateWithAbsoluteUrl
                    .exchange(buildUrlForHttpGet(HTTP + url, requestObject), HttpMethod.GET, null,
                            classType)
                    .getBody();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * http get 无需请求参数
     * 
     * @param url
     * @param classType
     * @return
     */
    public <T> T httpGet(String url, ParameterizedTypeReference<T> classType) {
        return httpGet(url, null, classType);
    }

    public <T> T httpGetWithAbsoluteUrl(String url, ParameterizedTypeReference<T> classType) {
        return httpGetWithAbsoluteUrl(url, null, classType);
    }

    private String buildUrlForHttpGet(String requestUrl, Object requestObject)
            throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(requestUrl);
        Set<Map.Entry<Object, Object>> paramMap = new BeanMap(requestObject).entrySet();
        for (Map.Entry<Object, Object> entry : paramMap) {
            if (!"class".equals(entry.getKey()) && entry.getValue() != null)
                uriBuilder.addParameter((String) entry.getKey(), entry.getValue().toString());
        }
        return uriBuilder.toString();
    }
}
