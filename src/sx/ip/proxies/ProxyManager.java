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

import com.sun.javafx.PlatformUtil;
import sx.ip.proxies.linux.LinuxProxyManager;
import sx.ip.proxies.mac.MacProxyManager;
import sx.ip.proxies.windows.WindowsProxyManager;

/**
 *Proxy Manager Super class.
 */
public abstract class ProxyManager {
    
    /** The ProxyManager instance. */
    private static ProxyManager instance = null;

    /**
     * Create a instance of ProxyManager, but, as this is a singleton, can be only called by the class itself.
     */
    protected ProxyManager() {
        // Prevents the creation of new instances
    }

    /**
     * Set the proxy options into the operation system settings for the current
     * user.
     *
     * @param settings 
     *          The settings list
     * @throws ProxySetupException If something goes really bad.
     * 
     * @return True of the operation was successful. False if something couldn't
     * be set.
     */
    public abstract boolean setProxySettings(ProxySettings settings) throws ProxySetupException;

    /**
     * Reads the proxy settings from the OS for the current user
     * 
     * @throws ProxySetupException If something goes really bad.
     * 
     * @return The current proxy settings that are set on the OS for the current
     * user.
     */
    public abstract ProxySettings getProxySettings() throws ProxySetupException;
    
    /**
     * Return the ProxyManager for the current user OS
     * 
     * @throws UnsupportedOperationException If something goes really bad.
     * 
     * @return The ProxyManager for the current OS
     * user.
     */
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
     * the OS level.
     */
    public class ProxySetupException extends Exception {

        /**
         * Create a new exception with a custom message
         *
         * @param message 
         *          The string message
         */
        public ProxySetupException(String message) {
            super(message);
        }

        /**
         * Create a new exception with a custom message
         *
         * @param message 
         *          The string message
         * @param ex 
         *          The original Exception
         */
        public ProxySetupException(String message, Throwable ex) {
            super(message, ex);
        }
    }

}
