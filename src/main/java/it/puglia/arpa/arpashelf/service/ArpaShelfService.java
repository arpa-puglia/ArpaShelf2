/*
 * Decompiled with CFR 0.150.
 * 03.03.2023 ESEGUITE CORREZIONI CODICE MANUALE
 */
package it.puglia.arpa.arpashelf.service;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import it.puglia.arpa.arpashelf.hbm.Account;
import it.puglia.arpa.arpashelf.hbm.ApplicazioneExt;
import it.puglia.arpa.arpashelf.hbm.Cartella;
import it.puglia.arpa.arpashelf.util.ArpaShelfConfig;
import it.puglia.arpa.arpashelf.util.ArpaShelfUtil;
import it.puglia.arpa.arpashelf.util.HibernateUtil;
import it.puglia.arpa.service.common.Applicazione;
import redstone.xmlrpc.XmlRpcClient;

public class ArpaShelfService {
    private Logger logger = Logger.getLogger(ArpaShelfService.class);

    public void creaCartellaApplicazione(String idApplicazione, String htmlApplicazione, String testoLogin) throws Exception {
        Applicazione app = ArpaShelfUtil.getArpaCommonService().getApplicazione(Long.valueOf(Long.parseLong(idApplicazione)));
        if (app != null) {
        	Session hibSession = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = hibSession.beginTransaction();
            try {
                try {
                    ApplicazioneExt appExt = new ApplicazioneExt();
                    appExt.setIdApplicazione(Integer.valueOf(app.getIdApplicazione().intValue()));
                    appExt.setHtml(htmlApplicazione);
                    appExt.setTestoLogin(testoLogin);
                    hibSession.save((Object)appExt);
                    String codiceApplicazione = app.getCodice();
                    this.logger.info((Object)("Creazione cartella applicazione su ajaxplorer, codice applicazione: " + codiceApplicazione));
                    XmlRpcClient client = ArpaShelfUtil.getAjxpXmlRpcClient();
                    Object retVal = client.invoke("arpa.creaApplicazione", new Object[]{codiceApplicazione});
                    this.logger.info((Object)("Creazione cartella applicazione su ajaxplorer, retVal: " + retVal));
                    tr.commit();
                   
                }
                catch (Exception e) {
                    this.logger.error((Object)e, (Throwable)e);
                    tr.rollback();
                    throw e;
                }
            }
            finally {
                hibSession.close();
            }
        }else
        	throw new Exception("Applicazione con id: " + idApplicazione + " inesistente");
    }

    public void modificaCartellaApplicazione(String idApplicazione, String htmlApplicazione, String testoLogin) throws Exception {
        Applicazione app = ArpaShelfUtil.getArpaCommonService().getApplicazione(Long.valueOf(Long.parseLong(idApplicazione)));
        if (app != null) {
            Session hibSession = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = hibSession.beginTransaction();
            try {
                try {
                    ApplicazioneExt appExt = (ApplicazioneExt)hibSession.get(ApplicazioneExt.class, (Serializable)Integer.valueOf(app.getIdApplicazione().intValue()));
                    appExt.setHtml(htmlApplicazione);
                    appExt.setTestoLogin(testoLogin);
                    hibSession.save((Object)appExt);
                    tr.commit();
                }
                catch (Exception e) {
                    tr.rollback();
                    throw e;
                }
            }
            finally {
                hibSession.close();
            }
        }else
        	throw new Exception("Applicazione con id: " + idApplicazione + " inesistente");
       
    }

    public void eliminaCartellaApplicazione(String idApplicazione) throws Exception {
    	Session hibSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = hibSession.beginTransaction();
        try {
            try {
                ApplicazioneExt appExt = (ApplicazioneExt)hibSession.get(ApplicazioneExt.class, (Serializable)Integer.valueOf(Integer.parseInt(idApplicazione)));
                if (appExt == null) {
                    throw new Exception("Applicazione con id: " + idApplicazione + " inesistente");
                }
                hibSession.delete((Object)appExt);
                tr.commit();
            }
            catch (Exception e) {
                tr.rollback();
                throw e;
            }
        }
        finally {
            hibSession.close();
        }
    }

    public String creaCartellaAccountInApplicazione(String idApplicazione, String account_login, String account_nome, String account_password, String account_mail) throws Exception {
        this.logger.info((Object)("Creazione account applicazione: idApplicazione: " + idApplicazione + ", login: " + account_login + ", nome: " + account_nome + ", pass: " + (account_password == null ? "null" : "*******") + ", mail: " + account_mail));
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = hibSession.beginTransaction();
        String idRepository = null;
        try {
            try {
                Account account;
                ApplicazioneExt appExt = (ApplicazioneExt)hibSession.get(ApplicazioneExt.class, (Serializable)Integer.valueOf(Integer.parseInt(idApplicazione)));
                if (appExt != null) {
                    Applicazione app = ArpaShelfUtil.getArpaCommonService().getApplicazione(new Long(appExt.getIdApplicazione().intValue()));
                    String codiceApplicazione = app.getCodice();
                    this.logger.info((Object)("Creazione utente su ajaxplorer, codice applicazione: " + codiceApplicazione));
                    XmlRpcClient client = ArpaShelfUtil.getAjxpXmlRpcClient();
                    Object retVal = client.invoke("arpa.creaUtente", new Object[]{account_login, account_password, codiceApplicazione, account_nome});
                    this.logger.info((Object)("Creazione utente su ajaxplorer, retVal: " + retVal));
                    idRepository = (String)retVal;
                    account = (Account)hibSession.get(Account.class, (Serializable)((Object)account_login));
                    if (account == null) {
                        account = new Account();
                        account.setNome(account_nome);
                        account.setLogin(account_login);
                        account.setMail(account_mail);
                        hibSession.save((Object)account);
                    }
                } else {
                    throw new Exception("Non e' stata ancora creata la cartella relativa all'applicazione selezionata");
                }
                Cartella cart = new Cartella();
                cart.setApplicazioneExt(appExt);
                cart.setAccount(account);
                cart.setIdAjxpRepo(idRepository);
                cart.setPassword(account_password);
                cart.setNome(account_nome);
                hibSession.save((Object)cart);
                tr.commit();
            }
            catch (Exception e) {
                this.logger.error((Object)e, (Throwable)e);
                tr.rollback();
                throw e;
            }
        }
        finally {
            hibSession.close();
        }
        return idRepository;
    }

    public void eliminaCartellaAccountInApplicazione(String idApplicazione, String account_login) throws Exception {
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = hibSession.beginTransaction();
        try {
            try {
                Query qu = hibSession.createQuery("select c from Cartelle c  join c.applicazioneExt app  join c.account acc  where app.idApplicazione = :idApplicazione   and acc.login = :login ");
                qu.setParameter("idApplicazione", (Object)Integer.parseInt(idApplicazione));
                qu.setParameter("login", (Object)account_login);
                Cartella cartAccApp = (Cartella)qu.uniqueResult();
                if (cartAccApp == null) {
                    throw new Exception("Cartella non trovata o utente inesistente");
                }
                XmlRpcClient client = ArpaShelfUtil.getAjxpXmlRpcClient();
                Object retVal = client.invoke("arpa.eliminaUtente", new Object[]{account_login, cartAccApp.getIdAjxpRepo()});
                this.logger.info((Object)("Eliminazione utente su ajaxplorer, retVal: " + retVal));
                hibSession.delete((Object)cartAccApp);
                tr.commit();
            }
            catch (Exception e) {
                tr.rollback();
                throw e;
            }
        }
        finally {
            hibSession.close();
        }
    }
    
   
    public Cartella getCartellaAccountInApplicazione(String idApplicazione, String login) throws Exception {
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        Cartella cartAccApp = null;
        try {
            try {
                Query qu = hibSession.createQuery("select c from Cartelle c  join c.applicazioneExt app  join c.account acc  where app.idApplicazione = :idApplicazione   and acc.login = :login ");
                qu.setParameter("idApplicazione", (Object)Integer.parseInt(idApplicazione));
                qu.setParameter("login", (Object)login);
                cartAccApp = (Cartella)qu.uniqueResult();
                String idRepo = cartAccApp.getIdAjxpRepo();
                if (cartAccApp != null && idRepo != null) {
                    cartAccApp.setNome("___NOME_CARTELLA__");
                }
            }
            catch (Exception e) {
                this.logger.error((Object)e, (Throwable)e);
                throw e;
            }
        }
        finally {
            hibSession.close();
        }
        return cartAccApp;
    }

    public ApplicazioneExt getApplicazione(String idApplicazione) throws Exception {
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        ApplicazioneExt app = null;
        try {
            app = (ApplicazioneExt)hibSession.get(ApplicazioneExt.class, (Serializable)Integer.valueOf(Integer.parseInt(idApplicazione)));
            if (app == null) {
                throw new Exception("Applicazione non trovata");
            }
            Applicazione _app = ArpaShelfUtil.getArpaCommonService().getApplicazione(Long.valueOf(Long.parseLong(idApplicazione)));
            app.setApplicazione(_app);
        }
        finally {
            hibSession.close();
        }
        return app;
    }

    public String costruisciOptionSelectApplicazioni() throws Exception {
        StringBuffer sb = new StringBuffer("<option value=\"\" selected=\"selected\">-</option>\n");
        List<Applicazione> apps = ArpaShelfUtil.getArpaCommonService().getApplicazioni();
        for (Applicazione applicazione : apps) {
            if (applicazione.getCodice().startsWith(".")) continue;
            sb.append("<option value=\"");
            sb.append(applicazione.getIdApplicazione());
            sb.append("\">");
            sb.append(applicazione.getCodice());
            sb.append("</option>\n");
        }
        return sb.toString();
    }

    public List<String[]> getCartelleAccountByIdApplicazione(String idApplicazione, Map<String, String[]> parametri) throws Exception {
       Session hibSession = HibernateUtil.getSessionFactory().openSession();
        ArrayList<String[]> cartelleAsArray = new ArrayList<String[]>();
        this.logger.info((Object)("DISPLAY LENGTH: " + parametri.get("iDisplayLength")[0]));
        this.logger.info((Object)("DISPLAY START: " + parametri.get("iDisplayStart")[0]));
        int iDisplayLength = Integer.parseInt(parametri.get("iDisplayLength")[0]);
        int iDisplayStart = Integer.parseInt(parametri.get("iDisplayStart")[0]);
        String sSortDir_0 = parametri.get("sSortDir_0")[0];
        int iSortCol_0 = Integer.parseInt(parametri.get("iSortCol_0")[0]);
        String order = null;
        switch (iSortCol_0) {
            case 0: {
                order = "c.nome";
                break;
            }
            case 1: {
                order = "acc.login";
                break;
            }
            case 2: {
                order = "c.password";
                break;
            }
            case 3: {
                order = "acc.mail";
            }
        }
        try {
            try {
                String hql = "select c from Cartelle c  join c.applicazioneExt app  join c.account acc  where app.idApplicazione = :idApplicazione order by " + order + " " + sSortDir_0;
                Query qu = hibSession.createQuery(hql);
                qu.setParameter("idApplicazione", (Object)Integer.parseInt(idApplicazione));
                qu.setMaxResults(iDisplayLength);
                qu.setFirstResult(iDisplayStart);
                List<Cartella> cartelle = qu.list();
                for (Cartella cartella : cartelle) {
                    String[] cartellaAr = new String[]{cartella.getNome(), cartella.getAccount().getLogin(), cartella.getPassword(), cartella.getAccount().getMail()};
                    cartelleAsArray.add(cartellaAr);
                }
            }
            catch (Exception e) {
                this.logger.error((Object)e, (Throwable)e);
                throw e;
            }
        }
        finally {
            hibSession.close();
        }
        return cartelleAsArray;
    }

    public long getNumeroTotaleCartelleAccountByIdApplicazione(String idApplicazione) throws Exception {
        long numRighe;
        Session hibSession = null;
        try {
        	hibSession =  HibernateUtil.getSessionFactory().openSession();
            Query qu = hibSession.createQuery("select count(c) from Cartelle c  join c.applicazioneExt app  join c.account acc  where app.idApplicazione = :idApplicazione ");
            qu.setParameter("idApplicazione", (Object)Integer.parseInt(idApplicazione));
            numRighe = (Long)qu.uniqueResult();
        }
        catch (Exception e) {
            this.logger.error((Object)e, (Throwable)e);
            throw e;
        }
        finally {
        	if (hibSession != null) hibSession.close();
        }
        
        return numRighe;
    }

    public String getPasswordAccountInApplicazione(Integer idApplicazione, String idAccount) throws Exception {
    	String pw = null;
    	Session hibSession = null;
    	try {
    		hibSession = HibernateUtil.getSessionFactory().openSession();
            Criteria crit = hibSession.createCriteria(Cartella.class);
            crit.createAlias("applicazioneExt", "applicazioneExt");
            crit.createAlias("account", "account");
            crit.add((Criterion)Restrictions.eq((String)"applicazioneExt.idApplicazione", (Object)idApplicazione));
            crit.add((Criterion)Restrictions.eq((String)"account.login", (Object)idAccount));
            Cartella cartella = (Cartella)crit.uniqueResult();
            if (cartella != null) {
                pw= cartella.getPassword();
            }
		} catch (Exception e) {
			 this.logger.error((Object)e, (Throwable)e);
             throw e;
        }
		
        
        return pw;
    }

    public String getMailAccount(String idAccount) throws Exception {
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        Criteria crit = hibSession.createCriteria(Account.class);
        crit.add((Criterion)Restrictions.eq((String)"login", (Object)idAccount));
        Account _acc = (Account)crit.uniqueResult();
        if (_acc != null) {
            return _acc.getMail();
        }
        return null;
    }

    public void inviaMailRecuperoPassword(String idApplicazione, String idAccount) throws Exception {
        String accMail = this.getMailAccount(idAccount);
        String accPassword = this.getPasswordAccountInApplicazione(Integer.valueOf(Integer.parseInt(idApplicazione)), idAccount);
        if (accMail != null) {
        	javax.mail.Session session;
            String fromMail = ArpaShelfConfig.getInstance().getProperty("smtp.from.mail");
            String fromName = ArpaShelfConfig.getInstance().getProperty("smtp.from.name");
            String host = ArpaShelfConfig.getInstance().getProperty("smtp.host");
            String segnalazioneOggetto = "[ArpaShelf] Recupero Password";
            String segnalazioneTesto = "Gentile Utente,\ne' stato richiesto l'inoltro della password di accesso al Fascicolo Elettronico del servizio.\n\nLa tua password e': " + accPassword + "\n\n" + "Servizio ArpaMIP.";
            String _port = ArpaShelfConfig.getInstance().getProperty("smtp.port");
            String _auth = ArpaShelfConfig.getInstance().getProperty("smtp.auth");
            int port = Integer.parseInt(_port);
            boolean auth = Boolean.parseBoolean(_auth);
            Properties props = new Properties();
            if (auth) {
                props.put("mail.smtp.auth", "true");
            }
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", (Object)port);
            if (auth) {
                String user = ArpaShelfConfig.getInstance().getProperty("smtp.auth.user");
                String password = ArpaShelfConfig.getInstance().getProperty("smtp.auth.password");
                session = javax.mail.Session.getInstance(props,  new javax.mail.Authenticator() {
												                    protected PasswordAuthentication getPasswordAuthentication() {
												                        return new PasswordAuthentication(user, password);
												                    }});
            } else {
                session = javax.mail.Session.getInstance((Properties)props, null);
            }
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom((Address)new InternetAddress(fromMail, fromName));
            InternetAddress[] address = new InternetAddress[]{new InternetAddress(accMail)};
            msg.setRecipients(Message.RecipientType.TO, (Address[])address);
            msg.setSentDate(new Date());
            msg.setSubject(segnalazioneOggetto);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)("Oggetto: " + segnalazioneOggetto));
            }
            msg.setText(segnalazioneTesto);
            Transport.send((Message)msg);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)("Mail inviata a: " + accMail));
            }
        } else {
            this.logger.warn((Object)("Richiesta recupero mail per account inesistente: " + idAccount));
        }
    }

    public String costruisciHtmlPersonalizzatoApplicazione(String idApplicazione, Map<String, String[]> mappaParametri) throws Exception {
        ApplicazioneExt app = this.getApplicazione(idApplicazione);
        String appNameLc = app.getApplicazione().getCodice().toLowerCase();
        String customClassName = "it.puglia.arpa.arpashelf." + appNameLc + ".shelf.Shelf";
        try {
            this.logger.warn((Object)("Classe " + customClassName + " trovata, recupero contenuto custom per l'applicazione"));
            Class<?> customShelfClass = Class.forName(customClassName);
            Object customShelfClassInstance = customShelfClass.newInstance();
            Method customShelfRenderMethod = customShelfClass.getMethod("render", String.class, Map.class);
            Object renderReturnObj = customShelfRenderMethod.invoke(customShelfClassInstance, idApplicazione, mappaParametri);
            return "\n<!-- HTML PERSONALIZZATO APPLICAZIONE (" + customClassName + ") -->\n" + (String)renderReturnObj + "\n<!-- HTML PERSONALIZZATO APPLICAZIONE [FINE] -->\n";
        }
        catch (ClassNotFoundException cnfe) {
            this.logger.warn((Object)("Classe " + customClassName + " non trovata, nessun contenuto custom per l'applicazione"));
            return "\n<!-- HTML PERSONALIZZATO APPLICAZIONE NON PRESENTE -->\n";
        }
    }

    public String putFile(String url, Integer idApplicazione, String nomeUtente, String nomeFile, String htmlToPdf) {
        return this.putFileInternal(url, idApplicazione, nomeUtente, nomeFile, htmlToPdf, false);
    }

    public String putFileUpd(String url, Integer idApplicazione, String nomeUtente, String nomeFile, String htmlToPdf) {
        return this.putFileInternal(url, idApplicazione, nomeUtente, nomeFile, htmlToPdf, true);
    }

    private String putFileInternal(String url, Integer idApplicazione, String nomeUtente, String nomeFile, String htmlToPdf, boolean overwrite) {
        try {
            Object xmlRpcResult = null;
            this.logger.info((Object)"putFileInternal");
            Session hibSession = HibernateUtil.getSessionFactory().openSession();
            Query qu = hibSession.createQuery("select c from Cartelle c  join c.applicazioneExt app  join c.account acc  where app.idApplicazione = :idApplicazione   and acc.login = :login ");
            qu.setParameter("idApplicazione", (Object)idApplicazione);
            qu.setParameter("login", (Object)nomeUtente);
            Cartella cartAccApp = (Cartella)qu.uniqueResult();
            if (cartAccApp == null) {
                throw new Exception("Inserimento file: non esiste la cartella relativa all'account e all'applicazione: " + nomeUtente + ", " + idApplicazione);
            }
            XmlRpcClient client = ArpaShelfUtil.getAjxpXmlRpcClient();
            xmlRpcResult = client.invoke("arpa.putFile", new Object[]{url, cartAccApp.getIdAjxpRepo(), nomeFile, htmlToPdf, overwrite});
            this.logger.info((Object)"file caricato");
            return xmlRpcResult.toString();
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    public String putFileFromProtocol(String prUserName, String prPassword, String protocolId, Integer idApplicazione, String nomeUtente, String nomeFile) {
        Session hibSession = null;
        try {
            Object xmlRpcResult = null;
            this.logger.info((Object)"putFileFromProtocol");
            hibSession = HibernateUtil.getSessionFactory().openSession();
            Query qu = hibSession.createQuery("select c from Cartelle c  join c.applicazioneExt app  join c.account acc  where app.idApplicazione = :idApplicazione   and acc.login = :login ");
            qu.setParameter("idApplicazione", (Object)idApplicazione);
            qu.setParameter("login", (Object)nomeUtente);
            Cartella cartAccApp = (Cartella)qu.uniqueResult();
            if (cartAccApp == null) {
                throw new Exception("Inserimento file: non esiste la cartella relativa all'account e all'applicazione: " + nomeUtente + ", " + idApplicazione);
            }
            XmlRpcClient client = ArpaShelfUtil.getAjxpXmlRpcClient();
            xmlRpcResult = client.invoke("arpa.putFileFromProtocol", new Object[]{prUserName, prPassword, protocolId, cartAccApp.getIdAjxpRepo(), nomeFile});
            this.logger.info((Object)"putFileFromProtocol: file caricato");
            return xmlRpcResult.toString();
        }
        catch (Exception e) {
            return e.getMessage();
        }
        finally {
			if (hibSession!=null) hibSession.close();
		}
    }

    public String protocolFile(String[] url, String[] htmlToPdf, Map<String, Object> parametriProtocollo) {
        try {
            this.logger.info((Object)"protocolFile");
            Object xmlRpcResult = null;
            XmlRpcClient client = ArpaShelfUtil.getAjxpXmlRpcClient();
            xmlRpcResult = client.invoke("arpa.protocolFile", new Object[]{url, htmlToPdf, parametriProtocollo});
            this.logger.info((Object)"file protocollato");
            return xmlRpcResult.toString();
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    public String getFile(Integer idApplicazione, String nomeUtente, String nomeFile) {
        try {
            this.logger.info((Object)"getFile");
            Session hibSession = HibernateUtil.getSessionFactory().openSession();
            Query qu = hibSession.createQuery("select c from Cartelle c  join c.applicazioneExt app  join c.account acc  where app.idApplicazione = :idApplicazione   and acc.login = :login ");
            qu.setParameter("idApplicazione", (Object)idApplicazione);
            qu.setParameter("login", (Object)nomeUtente);
            Cartella cartAccApp = (Cartella)qu.uniqueResult();
            if (cartAccApp != null) {
                XmlRpcClient client = ArpaShelfUtil.getAjxpXmlRpcClient();
                Object retVal = client.invoke("arpa.getFile", new Object[]{cartAccApp.getIdAjxpRepo(), nomeFile});
                return "" + (Boolean)retVal;
            }
            throw new Exception("get file: non esiste la cartella relativa all'account e all'applicazione: " + nomeUtente + ", " + idApplicazione);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    public String verifyFile(Integer idApplicazione, String nomeUtente, String nomeFile) {
        try {
            this.logger.info((Object)"verifyFile");
           Session hibSession = HibernateUtil.getSessionFactory().openSession();
            Query qu = hibSession.createQuery("select c from Cartelle c  join c.applicazioneExt app  join c.account acc  where app.idApplicazione = :idApplicazione   and acc.login = :login ");
            qu.setParameter("idApplicazione", (Object)idApplicazione);
            qu.setParameter("login", (Object)nomeUtente);
            Cartella cartAccApp = (Cartella)qu.uniqueResult();
            if (cartAccApp != null) {
                XmlRpcClient client = ArpaShelfUtil.getAjxpXmlRpcClient();
                Object retVal = client.invoke("arpa.verifyFile", new Object[]{cartAccApp.getIdAjxpRepo(), nomeFile});
                return "" + (Boolean)retVal;
            }
            throw new Exception("verify file: non esiste la cartella relativa all'account e all'applicazione: " + nomeUtente + ", " + idApplicazione);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    public String googleUrlShortener(String url) {
        try {
            this.logger.info((Object)"googleUrlShortener");
            Object xmlRpcResult = null;
            XmlRpcClient client = ArpaShelfUtil.getAjxpXmlRpcClient();
            xmlRpcResult = client.invoke("arpa.googleUrlShortener", new Object[]{url});
            this.logger.info((Object)"fine googleUrlShortner xmlrpc");
            return xmlRpcResult.toString();
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    public String jotformPostUrlMailZoho(Map<String, String[]> parametri) throws Exception {
        this.logger.info((Object)"jotformPostUrlMailZoho");
        Object xmlRpcResult = null;
        XmlRpcClient client = ArpaShelfUtil.getAjxpXmlRpcClient();
        xmlRpcResult = client.invoke("arpa.jotformPostUrlMailZoho", new Object[]{parametri});
        this.logger.info((Object)"fine jotformPostUrlMailZoho xmlrpc");
        return xmlRpcResult.toString();
    }
}

