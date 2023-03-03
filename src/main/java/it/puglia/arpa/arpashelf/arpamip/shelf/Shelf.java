/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.puglia.arpa.arpashelf.arpamip.shelf.Shelf
 *  it.puglia.arpa.arpashelf.service.ArpaShelfService
 *  it.puglia.arpa.arpashelf.shelf.CustomShelf
 *  it.puglia.arpa.arpashelf.util.ArpaShelfConfig
 *  org.apache.log4j.Logger
 */
package it.puglia.arpa.arpashelf.arpamip.shelf;

import java.util.Map;

import org.apache.log4j.Logger;

import it.puglia.arpa.arpashelf.service.ArpaShelfService;
import it.puglia.arpa.arpashelf.shelf.CustomShelf;
import it.puglia.arpa.arpashelf.util.ArpaShelfConfig;

public class Shelf
implements CustomShelf {
    private Logger log = Logger.getLogger(Shelf.class);

    public String render(String idApplicazione, Map<String, String[]> mappaParametri) {
        if (mappaParametri.get("login") != null) {
            String msgPwd = ArpaShelfConfig.getInstance().getProperty("arpamip.richiesta.password.sintesi.verifiche");
            String testoLogin = "utente..";
            try {
                testoLogin = new ArpaShelfService().getApplicazione(idApplicazione).getTestoLogin();
            }
            catch (Exception e) {
                this.log.error((Object)e, (Throwable)e);
            }
            return "<script type=\"text/javascript\" src=\"arpamip/js/shelf.js\"></script>\n<div style=\"margin-top:25px;\"> <iframe frameborder=\"0\" width=\"100%\" id=\"iframeVisualizzaVerificheUtente\"></iframe></div>";
        }
        return "";
    }
}

