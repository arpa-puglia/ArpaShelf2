/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.puglia.arpa.arpashelf.ws.ArpaShelfWs
 *  javax.jws.WebParam
 *  javax.jws.WebService
 */
package it.puglia.arpa.arpashelf.ws;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName="ArpaShelfWs")
public interface ArpaShelfWs {
    public String getPassword(@WebParam(name="idApplicazione") Integer var1, @WebParam(name="login") String var2) throws Exception;
}

