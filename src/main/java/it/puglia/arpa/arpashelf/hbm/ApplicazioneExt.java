/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.puglia.arpa.arpashelf.hbm.ApplicazioneExt
 *  it.puglia.arpa.arpashelf.hbm.Cartella
 *  it.puglia.arpa.service.common.Applicazione
 *  javax.persistence.CascadeType
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.Id
 *  javax.persistence.OneToMany
 *  javax.persistence.Transient
 */
package it.puglia.arpa.arpashelf.hbm;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import it.puglia.arpa.service.common.Applicazione;

@Entity(name="ApplicazioniExt")
public class ApplicazioneExt {
    @Id
    Integer idApplicazione;
    String testoLogin;
    String testoPassword;
    @Column(length=4096)
    String html;
    @OneToMany(mappedBy="applicazioneExt", cascade={CascadeType.ALL})
    List<Cartella> cartelle;
    @Transient
    Applicazione applicazione;

    public Integer getIdApplicazione() {
        return this.idApplicazione;
    }

    public void setIdApplicazione(Integer idApplicazione) {
        this.idApplicazione = idApplicazione;
    }

    public String getHtml() {
        return this.html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public List<Cartella> getCartelle() {
        return this.cartelle;
    }

    public void setCartelle(List<Cartella> cartelle) {
        this.cartelle = cartelle;
    }

    public String getTestoLogin() {
        return this.testoLogin;
    }

    public void setTestoLogin(String testoLogin) {
        this.testoLogin = testoLogin;
    }

    public String getTestoPassword() {
        return this.testoPassword;
    }

    public void setTestoPassword(String testoPassword) {
        this.testoPassword = testoPassword;
    }

    public Applicazione getApplicazione() {
        return this.applicazione;
    }

    public void setApplicazione(Applicazione applicazione) {
        this.applicazione = applicazione;
    }
}

