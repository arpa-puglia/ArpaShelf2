/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.puglia.arpa.arpashelf.service.ArpaShelfService
 *  it.puglia.arpa.arpashelf.ws.ArpaShelfWs
 *  it.puglia.arpa.arpashelf.ws.impl.ArpaShelfWsImpl
 */
package it.puglia.arpa.arpashelf.ws.impl;

import it.puglia.arpa.arpashelf.service.ArpaShelfService;
import it.puglia.arpa.arpashelf.ws.ArpaShelfWs;

public class ArpaShelfWsImpl
implements ArpaShelfWs {
    public String getPassword(Integer idApplicazione, String login) throws Exception {
        return new ArpaShelfService().getPasswordAccountInApplicazione(idApplicazione, login);
    }
}

