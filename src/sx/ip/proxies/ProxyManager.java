package sx.ip.proxies;

import com.sun.javafx.PlatformUtil;
import sx.ip.proxies.linux.LinuxProxyManager;
import sx.ip.proxies.mac.MacProxyManager;
import sx.ip.proxies.windows.WindowsProxyManager;

/**
 *
 *
 */
public abstract class ProxyManager {

    private static ProxyManager instance = null;

    protected ProxyManager() {
        // Prevents the creation of new instances
    }

    /**
     * Set the proxy options into the operation system settings for the current
     * user.
     *
     * @param settings The settings list
     * @return True of the operation was successful. False if something couldn't
     * be set.
     * @throws ProxySetupException If something goes really bad.
     */
    public abstract boolean setProxySettings(ProxySettings settings) throws ProxySetupException;

    /**
     * Reads the proxy settings from the OS for the current user
     *
     * @return The current proxy settings that are set on the OS for the current
     * user.
     * @throws ProxySetupException If something goes really bad.
     */
    public abstract ProxySettings getProxySettings() throws ProxySetupException;

    public static ProxyManager getInstance() throws UnsupportedOperationException {

        if (instance == null) {

            if (PlatformUtil.isWindows()) {
                instance = new WindowsProxyManager();
            } else {
                if (PlatformUtil.isMac()) {
                    instance = new MacProxyManager();
                } else {
                    if (PlatformUtil.isLinux()) {
                        instance = new LinuxProxyManager();
                    } else {
                        throw new UnsupportedOperationException("The current OS is not supported by the application.");
                    }
                }
            }
        }
        return instance;
    }

    /**
     * Exception that happens when something goes bad when setting a proxy on
     * the OS level
     */
    public class ProxySetupException extends Exception {

        /**
         * Create a new exception with a custom message
         *
         * @param message The string message
         */
        public ProxySetupException(String message) {
            super(message);
        }

        /**
         * Create a new exception with a custom message
         *
         * @param message The string message
         * @param ex The original Exception
         */
        public ProxySetupException(String message, Throwable ex) {
            super(message, ex);
        }
    }

}
