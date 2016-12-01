package ru.horoshiki.crm.httpclient;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import ru.horoshiki.crm.httpclient.exception.HttpErrorException;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

/**
 * Created by OnyushkinDV on 27.04.2016.
 */
public class ApacheHttpClientService implements HttpClientService {
    public static final Logger LOG = Logger.getLogger(HttpClientService.class);

    /**
     * Метод get-запрос
     * @param path           Адрес
     * @param httpDataSource Настройки запроса
     * @param params         Параметры запроса
     * @return Возвращает строку с ответом
     * @throws IOException
     * @throws URISyntaxException
     */
    @Override
    public String httpGet(String path, String params, HttpDataSource httpDataSource) throws URISyntaxException, IOException, HttpErrorException {
        String res = "";
        HttpResponse response;
        HttpClient httpClient = HttpClients.createDefault();

        HttpHost targetHost = new HttpHost(httpDataSource.getHost(), httpDataSource.getPort(), httpDataSource.getScheme());

        if (params == null)
            params = "";

        URI uri = new URIBuilder()
                .setScheme(httpDataSource.getScheme())
                .setHost(httpDataSource.getHost())
                .setPort(httpDataSource.getPort())
                .setPath(path)
                .setCustomQuery(params)
                .build();
        HttpGet httpget = new HttpGet(uri);

        HttpClientContext context = HttpClientContext.create();
        if (httpDataSource.getTimeout() != 0) {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(httpDataSource.getTimeout())
                    .setSocketTimeout(httpDataSource.getTimeout()).build();
            context.setRequestConfig(requestConfig);
        }

        //Авторизация
        setCredentials(context, httpDataSource, targetHost);

            response = httpClient.execute(targetHost, httpget, context);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                res = EntityUtils.toString(entity);
            } else {
                throw new HttpErrorException("On the request " + httpget.getURI().toString() + " server response to the error: '" + response.getStatusLine().toString() + "'");
            }

        return res;
    }

    /**
     * Метод post-запрос
     * @param path           Адрес
     * @param param          Параметры запроса
     * @param httpDataSource Настройки запроса
     * @return Возвращает строку с ответом
     * @throws IOException
     * @throws URISyntaxException
     */
    public String httpPost(String path, String param, HttpDataSource httpDataSource) throws IOException, URISyntaxException, HttpErrorException {
        String res = "";
        HttpResponse response;
        HttpClient httpClient = HttpClients.createDefault();

        HttpHost targetHost = new HttpHost(httpDataSource.getHost(), httpDataSource.getPort(), httpDataSource.getScheme());

        HttpClientContext context = HttpClientContext.create();


        if (httpDataSource.getTimeout() != 0) {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(httpDataSource.getTimeout())
                    .setSocketTimeout(httpDataSource.getTimeout()).build();
            context.setRequestConfig(requestConfig);
        }

        URI uri = new URIBuilder()
                .setScheme(httpDataSource.getScheme())
                .setHost(httpDataSource.getHost())
                .setPort(httpDataSource.getPort())
                .setPath(path)
                .build();

        HttpPost httpPost = new HttpPost(uri);

        StringEntity myEntity = new StringEntity(param,
                ContentType.create("application/x-www-form-urlencoded", (Charset) Consts.UTF_8));
        httpPost.setEntity(myEntity);


        //Авторизация
        setCredentials(context, httpDataSource, targetHost);

        response = httpClient.execute(targetHost, httpPost, context);

            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                res = EntityUtils.toString(entity);
            } else {
                throw new HttpErrorException("On the request " + httpPost.getURI().toString() + " server response to the error: '" + response.getStatusLine().toString() + "'");
            }

        return res;
    }

    /**
     * Метод put-запрос
     * @param path           Адрес
     * @param param          Параметры запроса
     * @param httpDataSource Настройки запроса
     * @return Возвращает строку с ответом
     * @throws IOException
     * @throws URISyntaxException
     */
    public String httpPut(String path, String param, HttpDataSource httpDataSource) throws IOException, URISyntaxException, HttpErrorException {
        String res = "";
        HttpResponse response;
        HttpClient httpClient = HttpClients.createDefault();

        HttpHost targetHost = new HttpHost(httpDataSource.getHost(), httpDataSource.getPort(), httpDataSource.getScheme());

        URI uri = new URIBuilder()
                .setScheme(httpDataSource.getScheme())
                .setHost(httpDataSource.getHost())
                .setPort(httpDataSource.getPort())
                .setPath(path)
                .build();

        HttpPut httpput = new HttpPut(uri);

        StringEntity myEntity = new StringEntity(param,
                ContentType.create("application/x-www-form-urlencoded"));
        httpput.setEntity(myEntity);

        HttpClientContext context = HttpClientContext.create();

        if (httpDataSource.getTimeout() != 0) {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(httpDataSource.getTimeout())
                    .setSocketTimeout(httpDataSource.getTimeout()).build();
            context.setRequestConfig(requestConfig);
        }

        //Авторизация
        setCredentials(context, httpDataSource, targetHost);

        response = httpClient.execute(targetHost, httpput, context);


            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                res = EntityUtils.toString(entity);
            } else {
                throw new HttpErrorException("On the request " + httpput.getURI().toString() + " server response to the error: '" + response.getStatusLine().toString() + "'");
            }

        return res;
    }

    private void setCredentials(HttpClientContext context, HttpDataSource httpDataSource, HttpHost targetHost){
        if (httpDataSource.getLogin() != null && httpDataSource.getPassword() != null) {
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(
                    new AuthScope(targetHost.getHostName(), targetHost.getPort()),
                    new UsernamePasswordCredentials(httpDataSource.getLogin(), httpDataSource.getPassword()));

            // Create AuthCache instance
            AuthCache authCache = new BasicAuthCache();
            // Generate BASIC scheme object and add it to the local auth cache
            BasicScheme basicAuth = new BasicScheme();
            authCache.put(targetHost, basicAuth);
            // Add AuthCache to the execution context
            context.setCredentialsProvider(credsProvider);
            context.setAuthCache(authCache);
        }
    }
}
