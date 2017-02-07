package com.example.android.quakereport.model;

import java.io.Serializable;

/**
 * Created by frensky on 06/02/2017.
 */

public class QBConfig implements Serializable {
    String app_id;
    String auth_key;
    String auth_secret;
    String account_key;
    String api_domain;
    String chat_domain;
    String gcm_sender_id;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getAuth_key() {
        return auth_key;
    }

    public void setAuth_key(String auth_key) {
        this.auth_key = auth_key;
    }

    public String getAuth_secret() {
        return auth_secret;
    }

    public void setAuth_secret(String auth_secret) {
        this.auth_secret = auth_secret;
    }

    public String getAccount_key() {
        return account_key;
    }

    public void setAccount_key(String account_key) {
        this.account_key = account_key;
    }

    public String getApi_domain() {
        return api_domain;
    }

    public void setApi_domain(String api_domain) {
        this.api_domain = api_domain;
    }

    public String getChat_domain() {
        return chat_domain;
    }

    public void setChat_domain(String chat_domain) {
        this.chat_domain = chat_domain;
    }

    public String getGcm_sender_id() {
        return gcm_sender_id;
    }

    public void setGcm_sender_id(String gcm_sender_id) {
        this.gcm_sender_id = gcm_sender_id;
    }
}
