package ru.horoshiki.crm.httpclient;

/**
 * Created by onyushkindv on 27.04.2016.
 */
public class HttpDataSource {

    private String host;
    private int port = 80;
    private String login;
    private String password;
    private String scheme = "http";
    private int timeout = 60000;

    public HttpDataSource() {
    }

    public HttpDataSource(String host, String scheme, int port, String login, String password, int timeout) {
        this.host = host;
        this.login = login;
        this.password = password;
        this.port = port;
        this.scheme = scheme;
        this.timeout = timeout;
    }

    public HttpDataSource(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getScheme() {
        return scheme;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
