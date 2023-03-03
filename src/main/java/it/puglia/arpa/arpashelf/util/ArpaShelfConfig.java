/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.puglia.arpa.arpashelf.util.ArpaShelfConfig
 */
package it.puglia.arpa.arpashelf.util;

import java.util.ResourceBundle;

public class ArpaShelfConfig {
    public static final String PROP_KEY_ARPA_SERVICES_ARPA_COMMON_URL = "arpa.services.arpa.common.url";
    public static final String PROP_KEY_ARPA_SERVICES_ARPA_COMMON_NAMESPACE = "arpa.services.arpa.common.namespace";
    public static final String PROP_KEY_ARPA_SERVICES_ARPA_COMMON_LOCALPART = "arpa.services.arpa.common.localpart";
    public static final String PROP_KEY_AJAXPLORER_XMLRPC_URL = "ajaxplorer.xmlrpc.url";
    public static final String PROP_KEY_AJAXPLORER_URL = "ajaxplorer.url";
    public static final String PROP_KEY_PROXY_HOST = "proxy.host";
    public static final String PROP_KEY_PROXY_PORT = "proxy.port";
    public static final String PROP_KEY_SMTP_FROM_MAIL = "smtp.from.mail";
    public static final String PROP_KEY_SMTP_FROM_NAME = "smtp.from.name";
    public static final String PROP_KEY_SMTP_HOST = "smtp.host";
    public static final String PROP_KEY_SMTP_PORT = "smtp.port";
    public static final String PROP_KEY_SMTP_AUTH = "smtp.auth";
    public static final String PROP_KEY_SMTP_AUTH_USER = "smtp.auth.user";
    public static final String PROP_KEY_SMTP_AUTH_PASSWORD = "smtp.auth.password";
    public static final String PROP_KEY_ARPAMIP_RICHIESTA_PASSWORD_SINTESI_VERIFICHE = "arpamip.richiesta.password.sintesi.verifiche";
    public static final String PROP_KEY_AUTH_SECRET = "auth.secret";
    private static ArpaShelfConfig config = null;
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("arpashelf");

    public static ArpaShelfConfig getInstance() {
        if (config == null) {
            config = new ArpaShelfConfig();
        }
        return config;
    }

    public String getProperty(String propertyName) {
        return this.resourceBundle.getString(propertyName);
    }
}

