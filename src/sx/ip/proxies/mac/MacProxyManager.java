package sx.ip.proxies.mac;

import sx.ip.proxies.ProxyManager;
import sx.ip.proxies.ProxySettings;

/**
 * Proxy Manager to be used when the OS is Mac
 */
public class MacProxyManager extends ProxyManager {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setProxySettings(ProxySettings settings) throws ProxySetupException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProxySettings getProxySettings() throws ProxySetupException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
