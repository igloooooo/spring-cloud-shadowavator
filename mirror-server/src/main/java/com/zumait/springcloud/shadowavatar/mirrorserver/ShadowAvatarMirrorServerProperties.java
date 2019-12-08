package com.zumait.springcloud.shadowavatar.mirrorserver;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("shadowavatar.mirrorserver")
public class ShadowAvatarMirrorServerProperties {
    private String primaryServer;
    private boolean acceptAllSslCertificates;
    private boolean securePortEnabled;

    public String getPrimaryServer() {
        return primaryServer;
    }

    public void setPrimaryServer(String primaryServer) {
        this.primaryServer = primaryServer;
    }

    public boolean isAcceptAllSslCertificates() {
        return acceptAllSslCertificates;
    }

    public void setAcceptAllSslCertificates(boolean acceptAllSslCertificates) {
        this.acceptAllSslCertificates = acceptAllSslCertificates;
    }

    public boolean isSecurePortEnabled() {
        return securePortEnabled;
    }

    public void setSecurePortEnabled(boolean securePortEnabled) {
        this.securePortEnabled = securePortEnabled;
    }
}
