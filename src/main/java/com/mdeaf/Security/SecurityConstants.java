package com.mdeaf.Security;

public class SecurityConstants {
    public static  final String SECRET="amelbouguerra2010@gmail.com";
    /**
     * la date d'expiration du token
     * 10*24*3600=864000
     */
    public static final long EXPIRATION_TIME=864_000_000; //10 days
    public static final String TOKEN_PREFFIX="Bearer ";
    /**
     * le HEADER_STRING pour stoker le token
     */
    public static final String HEADER_STRING="Authorisation";
}
