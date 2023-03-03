/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvReader
 *  it.puglia.arpa.arpashelf.arpamip.hbm.Verifica
 *  it.puglia.arpa.arpashelf.arpamip.service.ArpaMipService
 *  it.puglia.arpa.arpashelf.util.HibernateUtil
 *  org.apache.log4j.Logger
 *  org.hibernate.Criteria
 *  org.hibernate.Query
 *  org.hibernate.Transaction
 *  org.hibernate.classic.Session
 *  org.hibernate.criterion.Criterion
 *  org.hibernate.criterion.Order
 *  org.hibernate.criterion.Projection
 *  org.hibernate.criterion.Projections
 *  org.hibernate.criterion.Restrictions
 */
package it.puglia.arpa.arpashelf.arpamip.service;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.csvreader.CsvReader;

import it.puglia.arpa.arpashelf.arpamip.hbm.Verifica;
import it.puglia.arpa.arpashelf.util.HibernateUtil;

public class ArpaMipService {
    private Logger logger = Logger.getLogger(ArpaMipService.class);
    public static final int ID_APPLICAZIONE = 5;

    public void elabolaVerificheZoho(InputStream csvIs) throws Exception {
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        Transaction transact = null;
        try {
            try {
                transact = hibSession.beginTransaction();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                CsvReader csvReader = new CsvReader(csvIs, ',', Charset.defaultCharset());
                csvReader.setRecordDelimiter('|');
                csvReader.readHeaders();
                while (csvReader.readRecord()) {
                    this.logger.info((Object)("INSERIMENTO VERIFICA, RIGA CSV: " + csvReader.getRawRecord()));
                    Verifica ver = new Verifica();
                    String _partitaIva = csvReader.get(0);
                    String _codiceVerifica = csvReader.get(1);
                    String _statoVerifica = csvReader.get(2);
                    String _tecnicoIncaricato = csvReader.get(3);
                    String _dataRichiesta = csvReader.get(4);
                    String _dataValidazione = csvReader.get(5);
                    String _dataRifiuto = csvReader.get(6);
                    String _dataAccettazione = csvReader.get(7);
                    String _dataPagamento = csvReader.get(8);
                    String _dataVerifica = csvReader.get(9);
                    String _dataChiusura = csvReader.get(10);
                    String _importoTotale = csvReader.get(11);
                    String _idRichiesta = csvReader.get(12);
                    this.logger.info((Object)("INSERIMENTO NUOVA VERIFICA: idrich: " + _idRichiesta + "piva: " + _partitaIva + ", codVer: " + _codiceVerifica + ", stato: " + _statoVerifica + ", dataRich: " + _dataRichiesta));
                    ver.setIdRichiesta(Long.valueOf(Long.parseLong(_idRichiesta)));
                    ver.setPartitaIva(_partitaIva);
                    ver.setCodiceVerifica(_codiceVerifica);
                    ver.setStatoVerifica(_statoVerifica);
                    ver.setTecnicoIncaricato(_tecnicoIncaricato);
                    ver.setDataRichiesta("null".equals(_dataRichiesta) ? null : dateFormat.parse(_dataRichiesta));
                    ver.setDataValidazione("null".equals(_dataValidazione) ? null : dateFormat.parse(_dataValidazione));
                    ver.setDataRifiuto("null".equals(_dataRifiuto) ? null : dateFormat.parse(_dataRifiuto));
                    ver.setDataAccettazione("null".equals(_dataAccettazione) ? null : dateFormat.parse(_dataAccettazione));
                    ver.setDataPagamento("null".equals(_dataPagamento) ? null : dateFormat.parse(_dataPagamento));
                    ver.setDataVerifica("null".equals(_dataVerifica) ? null : dateFormat.parse(_dataVerifica));
                    ver.setDataChiusura("null".equals(_dataChiusura) ? null : dateFormat.parse(_dataChiusura));
                    ver.setImportoTotale("null".equals(_importoTotale) ? null : Float.valueOf(Float.parseFloat(_importoTotale)));
                    hibSession.saveOrUpdate((Object)ver);
                }
                transact.commit();
            }
            catch (Exception e) {
                e.printStackTrace();
                this.logger.error((Object)e, (Throwable)e);
                transact.rollback();
                throw e;
            }
        }
        finally {
            hibSession.close();
        }
    }

    public List<String[]> getVerificheUtente(String login, String password, Map<String, String[]> parametri) throws Exception {
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "select count(c) from Cartelle c  join c.applicazioneExt app  join c.account acc  where app.idApplicazione = :idApplicazione and acc.login = :login and c.password = :password";
            Query qu = hibSession.createQuery(hql);
            qu.setParameter("idApplicazione", (Object)5);
            qu.setParameter("login", (Object)login);
            qu.setParameter("password", (Object)password);
            long numRighePass = (Long)qu.uniqueResult();
            if (numRighePass >= 0L) {
                ArrayList<String[]> verificheAsArray = new ArrayList<String[]>();
                this.logger.info((Object)("DISPLAY LENGTH: " + parametri.get("iDisplayLength")[0]));
                this.logger.info((Object)("DISPLAY START: " + parametri.get("iDisplayStart")[0]));
                int iDisplayLength = Integer.parseInt(parametri.get("iDisplayLength")[0]);
                int iDisplayStart = Integer.parseInt(parametri.get("iDisplayStart")[0]);
                String sSortDir_0 = parametri.get("sSortDir_0")[0];
                int iSortCol_0 = Integer.parseInt(parametri.get("iSortCol_0")[0]);
                String order = null;
                switch (iSortCol_0) {
                    case 0: {
                        order = "codiceVerifica";
                        break;
                    }
                    case 1: {
                        order = "statoVerifica";
                        break;
                    }
                    case 2: {
                        order = "tecnicoIncaricato";
                        break;
                    }
                    case 3: {
                        order = "dataRichiesta";
                        break;
                    }
                    case 4: {
                        order = "dataValidazione";
                        break;
                    }
                    case 5: {
                        order = "dataRifiuto";
                        break;
                    }
                    case 6: {
                        order = "dataAccettazione";
                        break;
                    }
                    case 7: {
                        order = "dataPagamento";
                        break;
                    }
                    case 8: {
                        order = "dataVerifica";
                        break;
                    }
                    case 9: {
                        order = "dataChiusura";
                        break;
                    }
                    case 10: {
                        order = "importoTotale";
                    }
                }
                this.logger.info((Object)("ORDER BY: " + order));
                Criteria crit = hibSession.createCriteria(Verifica.class);
                crit.add((Criterion)Restrictions.eq((String)"partitaIva", (Object)login));
                crit.addOrder("asc".equals(sSortDir_0) ? Order.asc((String)order) : Order.desc((String)order));
                crit.setMaxResults(iDisplayLength);
                crit.setFirstResult(iDisplayStart);
                List<Verifica> verifiche = crit.list();
                SimpleDateFormat dateFmt = new SimpleDateFormat("dd/MM/yyyy");
                DecimalFormat decimalFmt = new DecimalFormat();
                for (Verifica verifica : verifiche) {
                    String[] verificaAr = new String[]{verifica.getCodiceVerifica() != null ? verifica.getCodiceVerifica() : "-", verifica.getStatoVerifica() != null ? verifica.getStatoVerifica() : "-", verifica.getTecnicoIncaricato() != null ? verifica.getTecnicoIncaricato() : "-", verifica.getDataRichiesta() != null ? dateFmt.format(verifica.getDataRichiesta()) : "-", verifica.getDataValidazione() != null ? dateFmt.format(verifica.getDataValidazione()) : "-", verifica.getDataRifiuto() != null ? dateFmt.format(verifica.getDataRifiuto()) : "-", verifica.getDataAccettazione() != null ? dateFmt.format(verifica.getDataAccettazione()) : "-", verifica.getDataPagamento() != null ? dateFmt.format(verifica.getDataPagamento()) : "-", verifica.getDataVerifica() != null ? dateFmt.format(verifica.getDataVerifica()) : "-", verifica.getDataChiusura() != null ? dateFmt.format(verifica.getDataChiusura()) : "-", verifica.getImportoTotale() != null ? decimalFmt.format(verifica.getImportoTotale()) : "-"};
                    verificheAsArray.add(verificaAr);
                }
                return verificheAsArray;
            }
            return null;
        }
        catch (Exception e) {
            this.logger.error((Object)e, (Throwable)e);
            throw e;
        }
        finally {
            hibSession.close();
        }
    }

    public Long getNumeroTotaleVerificheUtente(String login, String password) throws Exception {
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        Long numVerifiche = null;
        try {
            String hql = "select count(c) from Cartelle c  join c.applicazioneExt app  join c.account acc  where app.idApplicazione = :idApplicazione and acc.login = :login and c.password = :password";
            Query qu = hibSession.createQuery(hql);
            qu.setParameter("idApplicazione", (Object)5);
            qu.setParameter("login", (Object)login);
            qu.setParameter("password", (Object)password);
            long numRighePass = (Long)qu.uniqueResult();
            if (numRighePass >= 0L) {
                Criteria crit = hibSession.createCriteria(Verifica.class);
                crit.add((Criterion)Restrictions.eq((String)"partitaIva", (Object)login));
                crit.setProjection((Projection)Projections.count((String)"partitaIva"));
                numVerifiche = (Long)crit.uniqueResult();
            }
            return numVerifiche;
        }
        catch (Exception e) {
            this.logger.error((Object)e, (Throwable)e);
            throw e;
        }
        finally {
            hibSession.close();
        }
    }
}

