package com.cobrand.ct.config;

import static com.cobrand.citi.constant.CobrandConstants.CON_MGR_TIMEOUT;
import static com.cobrand.citi.constant.CobrandConstants.CON_TIMEOUT;
import static com.cobrand.citi.constant.CobrandConstants.HOST_DEFAULT;
import static com.cobrand.citi.constant.CobrandConstants.HOST_PROP_NAME;
import static com.cobrand.citi.constant.CobrandConstants.IS_PROXY_REQUIRED;
import static com.cobrand.citi.constant.CobrandConstants.JWT_HOST_DEFAULT;
import static com.cobrand.citi.constant.CobrandConstants.JWT_HOST_PROP_NAME;
import static com.cobrand.citi.constant.CobrandConstants.PROXY_URL;
import static com.cobrand.citi.constant.CobrandConstants.SOCKET_TIMEOUT;
import static io.restassured.RestAssured.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.params.CoreConnectionPNames;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@SuppressWarnings("deprecation")
public class AARestAssuredConfiguration {

    public static RequestSpecification aaMerchSpec() {
        return getRequestSpecs(getMerchBaseURL());
    }

    public static RequestSpecification aaJWTSpec() {
        return getRequestSpecs(getJWTBaseURL());
    }

    public static RequestSpecification aaCobrandSpec() {
        return getRequestSpecs(getCobrandBaseURL());
    }

    private static RequestSpecification getRequestSpecs(String url) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(url)
                .setConfig(getHttpClientAAconfig());
        if (Boolean.parseBoolean(System.getProperty(IS_PROXY_REQUIRED))) {
            builder.setProxy(PROXY_URL);
        }
        return builder.build();
    }
    private static String getMerchBaseURL() {
        String urlProp = System.getProperty(HOST_PROP_NAME);
        String url = StringUtils.isEmpty(urlProp) ? HOST_DEFAULT : urlProp;
        return url;
    }

    private static String getJWTBaseURL() {
        String urlProp = System.getProperty(JWT_HOST_PROP_NAME);
        String url = StringUtils.isEmpty(urlProp) ? JWT_HOST_DEFAULT : urlProp;
        return url;
    }

    private static String getCobrandBaseURL() {
    	return "https://ct-loyalty-cobrand-citi-api-dev.us-east.mybluemix.net";
        //return "https://ct-loyalty-cobrand-citi-api-dev.mybluemix.net";
        
    }

    private static RestAssuredConfig getHttpClientAAconfig() {
        return config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam(ClientPNames.CONN_MANAGER_TIMEOUT, CON_MGR_TIMEOUT)
                        .setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, CON_TIMEOUT)
                        .setParam(CoreConnectionPNames.SO_TIMEOUT, SOCKET_TIMEOUT));
    }

}
