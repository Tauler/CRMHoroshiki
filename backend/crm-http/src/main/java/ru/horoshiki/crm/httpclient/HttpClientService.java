package ru.horoshiki.crm.httpclient;


import ru.horoshiki.crm.httpclient.exception.HttpErrorException;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by onyushkindv on 27.04.2016.
 */
public interface HttpClientService {
    String httpGet(String path, String params, HttpDataSource httpDataSource) throws IOException, URISyntaxException, HttpErrorException;
    String httpPost(String path, String param, HttpDataSource httpDataSource) throws IOException, URISyntaxException, HttpErrorException;
    String httpPut(String path, String param, HttpDataSource httpDataSource) throws IOException, URISyntaxException, HttpErrorException, HttpErrorException;
}
