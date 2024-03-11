package com.manning.sbip.ch04.model;


import jakarta.persistence.*;

@Entity
@Table(name = "CT_TOTP_DETAILS")
public class TotpDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String secret;

    public TotpDetails() {}

    public TotpDetails(String username, String secret) {
        this.username = username;
        this.secret = secret;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
