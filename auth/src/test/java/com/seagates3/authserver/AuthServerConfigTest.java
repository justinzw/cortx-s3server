/*
 * COPYRIGHT 2017 SEAGATE LLC
 *
 * THIS DRAWING/DOCUMENT, ITS SPECIFICATIONS, AND THE DATA CONTAINED
 * HEREIN, ARE THE EXCLUSIVE PROPERTY OF SEAGATE TECHNOLOGY
 * LIMITED, ISSUED IN STRICT CONFIDENCE AND SHALL NOT, WITHOUT
 * THE PRIOR WRITTEN PERMISSION OF SEAGATE TECHNOLOGY LIMITED,
 * BE REPRODUCED, COPIED, OR DISCLOSED TO A THIRD PARTY, OR
 * USED FOR ANY PURPOSE WHATSOEVER, OR STORED IN A RETRIEVAL SYSTEM
 * EXCEPT AS ALLOWED BY THE TERMS OF SEAGATE LICENSES AND AGREEMENTS.
 *
 * YOU SHOULD HAVE RECEIVED A COPY OF SEAGATE'S LICENSE ALONG WITH
 * THIS RELEASE. IF NOT PLEASE CONTACT A SEAGATE REPRESENTATIVE
 * http://www.seagate.com/contact
 *
 * Original author: Sushant Mane <sushant.mane@seagate.com>
 * Original creation date: 01-Feb-2017
 */

package com.seagates3.authserver;

import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;

public class AuthServerConfigTest {

    @Test
    public void initTest() throws Exception {
        Properties authServerConfig = getAuthProperties();
        AuthServerConfig.init(authServerConfig);

        assertEquals("s3.seagate.com", AuthServerConfig.getDefaultEndpoint());

        assertEquals("resources/static/saml-metadata.xml",
                AuthServerConfig.getSAMLMetadataFilePath());

        assertEquals(8085, AuthServerConfig.getHttpPort());

        assertEquals(8086, AuthServerConfig.getHttpsPort());

        assertEquals("s3_auth.jks", AuthServerConfig.getKeyStoreName());

        assertEquals("seagate", AuthServerConfig.getKeyStorePassword());

        assertEquals("seagate", AuthServerConfig.getKeyPassword());

        assertTrue(AuthServerConfig.isHttpsEnabled());

        assertEquals("ldap", AuthServerConfig.getDataSource());

        assertEquals("127.0.0.1", AuthServerConfig.getLdapHost());

        assertEquals(389, AuthServerConfig.getLdapPort());

        assertEquals(5, AuthServerConfig.getLdapMaxConnections());

        assertEquals(1, AuthServerConfig.getLdapMaxSharedConnections());

        assertEquals("cn=admin,dc=seagate,dc=com", AuthServerConfig.getLdapLoginDN());

        assertEquals("seagate", AuthServerConfig.getLdapLoginPassword());

        assertEquals("https://console.s3.seagate.com:9292/sso", AuthServerConfig.getConsoleURL());

        assertNull(AuthServerConfig.getLogConfigFile());

        assertNull(AuthServerConfig.getLogLevel());

        assertEquals(1, AuthServerConfig.getBossGroupThreads());

        assertEquals(2, AuthServerConfig.getWorkerGroupThreads());

        assertFalse(AuthServerConfig.isPerfEnabled());

        assertEquals("/var/log/seagate/auth/perf.log", AuthServerConfig.getPerfLogFile());

        assertEquals(4, AuthServerConfig.getEventExecutorThreads());

        assertFalse(AuthServerConfig.isFaultInjectionEnabled());

        String[] expectedEndPoints = {"s3-us-west-2.seagate.com", "s3-us.seagate.com",
                "s3-europe.seagate.com", "s3-asia.seagate.com"};
        assertArrayEquals(expectedEndPoints, AuthServerConfig.getEndpoints());
    }

    private Properties getAuthProperties() throws Exception {
        Properties authServerConfig = new Properties();

        authServerConfig.setProperty("s3Endpoints", "s3-us-west-2.seagate.com," +
                "s3-us.seagate.com,s3-europe.seagate.com,s3-asia.seagate.com");
        authServerConfig.setProperty("defaultEndpoint", "s3.seagate.com");
        authServerConfig.setProperty("samlMetadataFileName", "saml-metadata.xml");
        authServerConfig.setProperty("nettyBossGroupThreads","1");
        authServerConfig.setProperty("nettyWorkerGroupThreads", "2");
        authServerConfig.setProperty("nettyEventExecutorThreads", "4");
        authServerConfig.setProperty("httpPort", "8085");
        authServerConfig.setProperty("httpsPort", "8086");
        authServerConfig.setProperty("logFilePath", "/var/log/seagate/auth/");
        authServerConfig.setProperty("dataSource", "ldap");
        authServerConfig.setProperty("ldapHost", "127.0.0.1");
        authServerConfig.setProperty("ldapPort", "389");
        authServerConfig.setProperty("ldapMaxCons", "5");
        authServerConfig.setProperty("ldapMaxSharedCons", "1");
        authServerConfig.setProperty("ldapLoginDN", "cn=admin,dc=seagate,dc=com");
        authServerConfig.setProperty("ldapLoginPW", "seagate");
        authServerConfig.setProperty("consoleURL", "https://console.s3.seagate.com:9292/sso");
        authServerConfig.setProperty("enable_https", "true");
        authServerConfig.setProperty("enable_http", "false");
        authServerConfig.setProperty("enableFaultInjection", "false");
        authServerConfig.setProperty("perfEnabled", "false");
        authServerConfig.setProperty("perfLogFile", "/var/log/seagate/auth/perf.log");
        authServerConfig.setProperty("s3KeystoreName", "s3_auth.jks");
        authServerConfig.setProperty("s3KeyStorePassword", "seagate");
        authServerConfig.setProperty("s3KeyPassword", "seagate");

        return authServerConfig;
    }
}
