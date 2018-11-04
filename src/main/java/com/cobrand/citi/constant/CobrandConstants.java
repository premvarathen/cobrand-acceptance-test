package com.cobrand.citi.constant;

public class CobrandConstants {

    public static final String HOST_PROP_NAME = "host";
    public static final String JWT_HOST_PROP_NAME = "jwthost";
    public static final String HOST_DEFAULT = "https://aa-ct-fly-merchandising-bff.mybluemix.net/";
    public static final String JWT_HOST_DEFAULT = "https://aa-ct-fly-oidc-qa.mybluemix.net/";
    public static final String JWT_RESOURCE_ROOT = "/auth/realms/ct-fly-master/protocol/openid-connect/token";
    public static final String ELIG_RESOURCE_ROOT = "/viewRes/products";
    public static final String IS_PROXY_REQUIRED = "reqProxy";
    public static final String PROXY_URL = "http://inetgw.aa.com:9091";
    public static final long CON_MGR_TIMEOUT = 300000L;
    public static final int CON_TIMEOUT = 300000;
    public static final int SOCKET_TIMEOUT = 300000;
    public static final boolean DELETE_PNR_UPON_TEST_COMPLETION = true;



    public static final String SESSION_KEY_RECORD_LOCATOR = "recordLocator";
    public static final String JWT_PPB_CLIENT = "aa-ppb-aacom-client";
    public static final String JWT_SECRET_KEY = "4b952962-0dc4-40cd-a2b5-c4f4e2b594cc";
    public static final String CLIENT_CREDENTIALS = "client_credentials";
    public static final String SESSION_KEY_AUTHORIZATION = "Authorization";
    public static final String SESSION_KEY_RETRY_ELIG_COUNTER = "SESSION_KEY_RETRY_ELIG_COUNTER";
    public static final String SESSION_KEY_RETRY_PNR_COUNTER = "SESSION_KEY_RETRY_PNR_COUNTER";
    public static final boolean IS_RETRY_ENABLED = true;
    public static final int MAX_RETRY = 3;
    public static final String FEATURE_KEY_ORIGIN = "Origin";
    public static final String FEATURE_KEY_DESTINATION = "Destination";
    public static final String FEATURE_KEY_DEPARTING_NO_OF_DAYS_FROM_TODAY = "DepartingNoOfDaysFromToday";
    public static final String FEATURE_KEY_DEPART_TIME = "DepartTime";
    public static final String FEATURE_KEY_CARRIER = "Carrier";
    public static final String FEATURE_KEY_FIRST_NAME = "FirstName";
    public static final String FEATURE_KEY_TOTAL_NUMBER_OF_PASSENGERS = "NumberOfPassengers";
    public static final String FEATURE_KEY_LAST_NAME = "LastName";
    public static final String FEATURE_KEY_RETURN_PLUS_NUMBER_OF_DAYS = "ReturnPlusNumberOfDays";
    public static final String FEATURE_KEY_RETURN_TIME = "ReturnTime";
    public static final String FEATURE_KEY_CONNECT_CITY = "ConnectCity";
    public static final String FEATURE_KEY_CORPORATE = "Corporate";
    public static final String FEATURE_KEY_EXIT_ROW_SEAT = "ExitRowSeat";
    public static final String FEATURE_KEY_TEST_ID = "TestId";
    public static final String FEATURE_KEY_PURCHASED = "Purchased";
    public static final String FEATURE_KEY_AADV = "AdvantageNumber";

    public static final String ONE_WORLD_CARRIER = "OneWorldCarrier";
    public static final String FEATURE_KEY_COUNTRY_OF_RESIDENCE = "countryOfResidence";
    public static final String FEATURE_KEY_LOCALE = "locale";


    
}
