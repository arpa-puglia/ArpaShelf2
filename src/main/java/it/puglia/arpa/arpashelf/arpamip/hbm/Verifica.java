/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.puglia.arpa.arpashelf.arpamip.hbm.Verifica
 *  javax.persistence.Entity
 *  javax.persistence.Id
 */
package it.puglia.arpa.arpashelf.arpamip.hbm;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="arpamip_Verifiche")
public class Verifica {
    @Id
    private Long idRichiesta;
    private String codiceVerifica;
    private String partitaIva;
    private String statoVerifica;
    private String tecnicoIncaricato;
    private Date dataRichiesta;
    private Date dataValidazione;
    private Date dataRifiuto;
    private Date dataAccettazione;
    private Date dataPagamento;
    private Date dataVerifica;
    private Date dataChiusura;
    private Float importoTotale;

    public String getPartitaIva() {
        return this.partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public String getCodiceVerifica() {
        return this.codiceVerifica;
    }

    public void setCodiceVerifica(String codiceVerifica) {
        this.codiceVerifica = codiceVerifica;
    }

    public String getStatoVerifica() {
        return this.statoVerifica;
    }

    public void setStatoVerifica(String statoVerifica) {
        this.statoVerifica = statoVerifica;
    }

    public String getTecnicoIncaricato() {
        return this.tecnicoIncaricato;
    }

    public void setTecnicoIncaricato(String tecnicoIncaricato) {
        this.tecnicoIncaricato = tecnicoIncaricato;
    }

    public Date getDataRichiesta() {
        return this.dataRichiesta;
    }

    public void setDataRichiesta(Date dataRichiesta) {
        this.dataRichiesta = dataRichiesta;
    }

    public Date getDataValidazione() {
        return this.dataValidazione;
    }

    public void setDataValidazione(Date dataValidazione) {
        this.dataValidazione = dataValidazione;
    }

    public Date getDataRifiuto() {
        return this.dataRifiuto;
    }

    public void setDataRifiuto(Date dataRifiuto) {
        this.dataRifiuto = dataRifiuto;
    }

    public Date getDataAccettazione() {
        return this.dataAccettazione;
    }

    public void setDataAccettazione(Date dataAccettazione) {
        this.dataAccettazione = dataAccettazione;
    }

    public Date getDataPagamento() {
        return this.dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Date getDataVerifica() {
        return this.dataVerifica;
    }

    public void setDataVerifica(Date dataVerifica) {
        this.dataVerifica = dataVerifica;
    }

    public Date getDataChiusura() {
        return this.dataChiusura;
    }

    public void setDataChiusura(Date dataChiusura) {
        this.dataChiusura = dataChiusura;
    }

    public Float getImportoTotale() {
        return this.importoTotale;
    }

    public void setImportoTotale(Float importoTotale) {
        this.importoTotale = importoTotale;
    }

    public Long getIdRichiesta() {
        return this.idRichiesta;
    }

    public void setIdRichiesta(Long idRichiesta) {
        this.idRichiesta = idRichiesta;
    }
}

