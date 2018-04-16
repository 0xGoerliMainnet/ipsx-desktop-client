package sx.ip.proxies;

/**
 *
 */
public class ProxySettings {

    private final String proxyHost;
    private final Integer proxyPort;
    private final ProxyType type;
    private final String acsUrl;
    private final boolean bypassOnLocal;
    private final String authUser;
    private final String authPass;

    public ProxySettings(String proxyHost, Integer proxyPort, ProxyType type, String acsUrl, boolean bypassOnLocal, String authUser, String authPass) {
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.type = type;
        this.acsUrl = acsUrl;
        this.bypassOnLocal = bypassOnLocal;
        this.authUser = authUser;
        this.authPass = authPass;
    }

    /**
     * Quick way to get a basic Proxy settings object with direct connection
     * options.
     *
     * @return A new ProxySettings that does not use any proxy.
     */
    public static ProxySettings getDirectConnectionSetting() {
        return new ProxySettings(null, null, null, null, true, null, null);
    }

    public enum ProxyType {
        SOCKS, HTTP, HTTPS, FTP, HTTP_AND_HTTPS;
    }

    /**
     * @return the acsUrl
     */
    public String getAcsUrl() {
        return acsUrl;
    }

    /**
     * @return the proxyHost
     */
    public String getProxyHost() {
        return proxyHost;
    }

    /**
     * @return the proxyPort
     */
    public Integer getProxyPort() {
        return proxyPort;
    }

    /**
     * @return the bypassOnLocal
     */
    public Boolean getBypassOnLocal() {
        return bypassOnLocal;
    }

    /**
     * @return the type
     */
    public ProxyType getType() {
        return type;
    }

    /**
     * @return the authUser
     */
    public String getAuthUser() {
        return authUser;
    }

    /**
     * @return the authPass
     */
    public String getAuthPass() {
        return authPass;
    }

}
