/* 
 * Copyright 2018 IP Exchange : https://ip.sx/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sx.ip.proxies;

/**
 * Class that will storage all Proxy settings for the system.
 */
public class ProxySettings {

    /** The proxy host for the connection. */
    private final String proxyHost;
    
    /** The proxy port to be access. */
    private final Integer proxyPort;
    
    /** The proxy type witch can be HTTP, HTTPS ,SOCKS, FTP, HTTP and HTTPS. */
    private final ProxyType type;
    
    /** A url for get all proxy information at once. */
    private final String acsUrl;
    
    /** A bypass on local environment. */
    private final boolean bypassOnLocal;
    
    /** The root user for possible authorization requirements. */
    private final String authUser;
    
    /** The root password for possible authorization requirements. */
    private final String authPass;
    
    
    /**
    * Create a <code>ProxySettings</code> that will hold all Proxy configuration for the system.
    *
    * @param proxyHost
    *            The proxy host for the connection
    * @param proxyPort
    *            The proxy port to be access
    * @param type
    *            The proxy type witch can be HTTP, HTTPS ,Socks, FTP, HTTP and HTTPS.
    * @param acsUrl
    *            A url for get all proxy information at once
    * @param bypassOnLocal
    *            A bypass on local environment
    * @param authUser
    *            The root user for possible authorization requirements
    * @param authPass
    *            The root password for possible authorization requirements
    */
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
    
    /**
     * All Proxy types that can be used.
     *
     */
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
