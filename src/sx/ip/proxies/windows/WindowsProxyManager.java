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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
       boolean commandResult = false;
        try {
            commandResult = setInternetProxy(settings);
        } catch (IOException ex) {
            Logger.getLogger(WindowsProxyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return commandResult;
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
     * @throws IOException the IO exception
     */
    public static void main(String[] args) throws IOException {
        ProxySettings settings = new ProxySettings("127.0.0.1", 8080, ProxySettings.ProxyType.HTTP, null, true, null, null);
        setInternetProxy(settings);
        disableInternetProxy();

    }

    /**
     * Method resposible for start the Proxy connection.
     *
     * @param settings A ProxySettings instance that hold all necessary
     * configurations
     *
     * @return If the command was executed or not
     *
     * @throws IOException The IO exception
     */
    public static boolean setInternetProxy(ProxySettings settings) throws IOException {
        String result = "false";
        switch (settings.getType()) {
            case HTTP:
                result = runPowerShellMethod("http", settings, false).get("result");
                break;                
            case HTTPS:
                result = runPowerShellMethod("https", settings, false).get("result");
                break;
            case SOCKS:
                result = runPowerShellMethod("socks", settings, false).get("result");
                break;
            case FTP:
                result = runPowerShellMethod("ftp", settings, false).get("result");
                break;
            case HTTP_AND_HTTPS:
                result = runPowerShellMethod("http", settings, false).get("result");
                if("true".equals(result)){
                    result = runPowerShellMethod("https", settings, false).get("result");
                }
                break;
        }
        return "true".equals(result);
    }
    
    /**
     * Method resposible for disable the Proxy connection.
     *
     * @return If the command was executed or not
     *
     * @throws IOException The IO exception
     *
     */
    public static boolean disableInternetProxy() throws IOException {        
        
        return "true".equals(runPowerShellMethod(null, null, true).get("result"));
    }
    
    /**
     * Function responsible for execute the calls to the PowerShell Script Methods.
     *
     * @param proxyType Type of proxy, what can be (http, https, socks and ftp)
     * 
     * @param settings ProxySettings instance containing all proxy configurations
     * 
     * @param disable Flag to indicate which script to use, what can be the set or disable scripts
     *
     * @return A map result and output message
     */
    private static Map<String, String> runPowerShellMethod( String proxyType, ProxySettings settings, boolean disable) throws IOException {
        System.out.println("Test commands starting....");
        Map<String, String> response = new HashMap<>();
        String powerShellScript;
        String psMethod;
        PowerShell powerShell = null;
        
        if(disable){
            psMethod = "Disable-InternetProxy";
            powerShellScript = IOUtils.resourceToString("/sx/ip/proxies/windows/scripts/windows-disable-internet-proxy.ps1", Charset.forName("UTF-8"));
        }else{
            psMethod = "Set-InternetProxy -proxy \"" +proxyType +"=" + settings.getProxyHost() + ":" + settings.getProxyPort() + "\" " 
                       + (settings.getAcsUrl() != null && !settings.getAcsUrl().isEmpty() ? "-acs \"" + settings.getAcsUrl() + "\"" : "");
            
            powerShellScript = IOUtils.resourceToString("/sx/ip/proxies/windows/scripts/windows-set-internet-proxy.ps1", Charset.forName("UTF-8"));
        }        
        
        try {
            //Creates PowerShell session (we can execute several commands in the same session)
            powerShell = PowerShell.openSession();

            //Execute a command in PowerShell session
            powerShell.executeCommand(powerShellScript);
            
            //Execute another command in the same PowerShell session
            PowerShellResponse psResponse = powerShell.executeCommand(psMethod);
            
            if(psResponse.isError()){
                 response.put("result", "false");
            } else {
                response.put("result", "true");
            }

            //Print results
            System.out.println("Output:" + psResponse.getCommandOutput());
            response.put("output", psResponse.getCommandOutput());
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

        return response;
    }

}
