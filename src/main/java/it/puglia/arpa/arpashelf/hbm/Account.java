/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.puglia.arpa.arpashelf.hbm.Account
 *  it.puglia.arpa.arpashelf.hbm.Cartella
 *  javax.persistence.Entity
 *  javax.persistence.Id
 *  javax.persistence.OneToMany
 */
package it.puglia.arpa.arpashelf.hbm;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="Account")
public class Account {
    @Id
    String login;
    String nome;
    String mail;
    @OneToMany(mappedBy="account")
    List<Cartella> cartelle;

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Cartella> getCartelle() {
        return this.cartelle;
    }

    public void setCartelle(List<Cartella> cartelle) {
        this.cartelle = cartelle;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}

