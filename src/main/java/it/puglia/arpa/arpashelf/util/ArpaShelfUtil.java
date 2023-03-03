/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.puglia.arpa.arpashelf.util.ArpaShelfConfig
 *  it.puglia.arpa.arpashelf.util.ArpaShelfUtil
 *  it.puglia.arpa.service.ArpaCommon
 *  javax.xml.ws.Service
 *  org.apache.log4j.Logger
 *  redstone.xmlrpc.XmlRpcClient
 */
package it.puglia.arpa.arpashelf.util;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.log4j.Logger;

import it.puglia.arpa.service.ArpaCommon;
import redstone.xmlrpc.XmlRpcClient;

public class ArpaShelfUtil {
    private static Logger log = Logger.getLogger(ArpaShelfUtil.class);

    public static ArpaCommon getArpaCommonService() throws Exception {
        URL wsdlURL = new URL(ArpaShelfConfig.getInstance().getProperty("arpa.services.arpa.common.url"));
        QName SERVICE_NAME = new QName(ArpaShelfConfig.getInstance().getProperty("arpa.services.arpa.common.namespace"), ArpaShelfConfig.getInstance().getProperty("arpa.services.arpa.common.localpart"));
        Service service = Service.create(wsdlURL, SERVICE_NAME);
        ArpaCommon arpaCommon = (ArpaCommon)service.getPort(ArpaCommon.class);
        return arpaCommon;
    }

    public static XmlRpcClient getAjxpXmlRpcClient() throws Exception {
        String ajxpXmlRpcUrl = ArpaShelfConfig.getInstance().getProperty("ajaxplorer.xmlrpc.url");
        if (ajxpXmlRpcUrl == null || ajxpXmlRpcUrl.equals("")) {
            Exception ex = new Exception("Xmlrpc URL Ajaxplorer non configurato nel file di properties");
            log.error((Object)ex, (Throwable)ex);
            throw ex;
        }
        return new XmlRpcClient(ajxpXmlRpcUrl, false);
    }

    public static void main(String[] args) throws Exception {
    }
}

