package com.example.android.quakereport.model;

import java.io.Serializable;

/**
 * Created by frensky on 06/02/2017.
 */

public class QBSetting implements Serializable {
    int port;
    int socket_timeout;
    boolean keep_alive;
    boolean use_tls;
    boolean auto_join;
    boolean auto_mark_delivered;
    boolean reconnection_allowed;
    boolean allow_listen_network;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getSocket_timeout() {
        return socket_timeout;
    }

    public void setSocket_timeout(int socket_timeout) {
        this.socket_timeout = socket_timeout;
    }

    public boolean isKeep_alive() {
        return keep_alive;
    }

    public void setKeep_alive(boolean keep_alive) {
        this.keep_alive = keep_alive;
    }

    public boolean isUse_tls() {
        return use_tls;
    }

    public void setUse_tls(boolean use_tls) {
        this.use_tls = use_tls;
    }

    public boolean isAuto_join() {
        return auto_join;
    }

    public void setAuto_join(boolean auto_join) {
        this.auto_join = auto_join;
    }

    public boolean isAuto_mark_delivered() {
        return auto_mark_delivered;
    }

    public void setAuto_mark_delivered(boolean auto_mark_delivered) {
        this.auto_mark_delivered = auto_mark_delivered;
    }

    public boolean isReconnection_allowed() {
        return reconnection_allowed;
    }

    public void setReconnection_allowed(boolean reconnection_allowed) {
        this.reconnection_allowed = reconnection_allowed;
    }

    public boolean isAllow_listen_network() {
        return allow_listen_network;
    }

    public void setAllow_listen_network(boolean allow_listen_network) {
        this.allow_listen_network = allow_listen_network;
    }
}
