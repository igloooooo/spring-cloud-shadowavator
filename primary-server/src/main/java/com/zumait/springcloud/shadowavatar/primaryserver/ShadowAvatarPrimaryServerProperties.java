package com.zumait.springcloud.shadowavatar.primaryserver;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Nicholas Zhu
 */
@ConfigurationProperties("shadowavatar")
public class ShadowAvatarPrimaryServerProperties {
    private Boolean checkMirrorServer;
    private boolean acceptAllSslCertificates;
    private boolean securePortEnabled;

    public Boolean getCheckMirrorServer() {
        return checkMirrorServer;
    }

    public void setCheckMirrorServer(Boolean checkMirrorServer) {
        this.checkMirrorServer = checkMirrorServer;
    }

    public boolean getAcceptAllSslCertificates() {
        return acceptAllSslCertificates;
    }

    public void setAcceptAllSslCertificates(boolean acceptAllSslCertificates) {
        this.acceptAllSslCertificates = acceptAllSslCertificates;
    }

    public boolean getSecurePortEnabled() {
        return securePortEnabled;
    }

    public void setSecurePortEnabled(boolean securePortEnabled) {
        this.securePortEnabled = securePortEnabled;
    }
}