/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.puglia.arpa.arpashelf.hbm.Account
 *  it.puglia.arpa.arpashelf.hbm.ApplicazioneExt
 *  it.puglia.arpa.arpashelf.hbm.Cartella
 *  javax.persistence.Entity
 *  javax.persistence.GeneratedValue
 *  javax.persistence.GenerationType
 *  javax.persistence.Id
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.Table
 *  javax.persistence.UniqueConstraint
 */
package it.puglia.arpa.arpashelf.hbm;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name="Cartelle")
@Table(name="Cartelle", uniqueConstraints={@UniqueConstraint(columnNames={"idAccount", "idApplicazione"})})
public class Cartella {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Integer idCartella;
    String idAjxpRepo;
    String password;
    String nome;
    @ManyToOne(targetEntity=Account.class)
    @JoinColumn(name="idAccount", nullable=false)
    Account account;
    @ManyToOne(targetEntity=ApplicazioneExt.class)
    @JoinColumn(name="idApplicazione", nullable=false)
    ApplicazioneExt applicazioneExt;

    public Integer getIdCartella() {
        return this.idCartella;
    }

    public void setIdCartella(Integer idCartella) {
        this.idCartella = idCartella;
    }

    public ApplicazioneExt getApplicazioneExt() {
        return this.applicazioneExt;
    }

    public void setApplicazioneExt(ApplicazioneExt applicazioneExt) {
        this.applicazioneExt = applicazioneExt;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdAjxpRepo() {
        return this.idAjxpRepo;
    }

    public void setIdAjxpRepo(String idAjxpRepo) {
        this.idAjxpRepo = idAjxpRepo;
    }
}

