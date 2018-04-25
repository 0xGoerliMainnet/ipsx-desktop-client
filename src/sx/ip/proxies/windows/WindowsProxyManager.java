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
package sx.ip.proxies.windows;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellNotAvailableException;
import com.profesorfalken.jpowershell.PowerShellResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.io.IOUtils;
import sx.ip.proxies.ProxyManager;
import sx.ip.proxies.ProxySettings;

/**
 * Proxy Manager to be used when the OS is Windows
 */
public class WindowsProxyManager extends ProxyManager {

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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        setInternetProxy("127.0.0.1", "8080", "");
        disableInternetProxy();

    }

    public static void setInternetProxy(String ip, String port, String acs) throws IOException {
        String cmd = IOUtils.resourceToString("/sx/ip/ipsxclient/scripts/windows-set-internet-proxy.ps1", Charset.forName("UTF-8"));

        PowerShell powerShell = null;
        try {
            //Creates PowerShell session (we can execute several commands in the same session)
            powerShell = PowerShell.openSession();

            //Execute a command in PowerShell session
            powerShell.executeCommand(cmd);

            //Execute another command in the same PowerShell session
            PowerShellResponse response = powerShell.executeCommand("Set-InternetProxy -proxy \"socks=" + ip + ":" + port + "\" " + (acs == null || acs.isEmpty() ? "-acs \"" + acs + "\"" : ""));

            //Print results
            System.out.println("Output 2:" + response.getCommandOutput());
        } catch (PowerShellNotAvailableException ex) {
            //Handle error when PowerShell is not available in the system
            //Maybe try in another way?
            ex.printStackTrace();
        } finally {
            //Always close PowerShell session to free resources.
            if (powerShell != null) {
                powerShell.close();
            }
        }
    }

    public static void disableInternetProxy() throws IOException {
        String cmd = IOUtils.resourceToString("/sx/ip/ipsxclient/scripts/windows-disable-internet-proxy.ps1", Charset.forName("UTF-8"));

        PowerShell powerShell = null;
        try {
            //Creates PowerShell session (we can execute several commands in the same session)
            powerShell = PowerShell.openSession();

            //Execute a command in PowerShell session
            powerShell.executeCommand(cmd);

            //Execute another command in the same PowerShell session
            PowerShellResponse response = powerShell.executeCommand("Disable-InternetProxy");

            //Print results
            System.out.println("Output 2:" + response.getCommandOutput());
        } catch (PowerShellNotAvailableException ex) {
            //Handle error when PowerShell is not available in the system
            //Maybe try in another way?
            ex.printStackTrace();
        } finally {
            //Always close PowerShell session to free resources.
            if (powerShell != null) {
                powerShell.close();
            }
        }
    }

}
