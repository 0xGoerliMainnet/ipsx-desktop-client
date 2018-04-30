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
        try {
            return getInternetProxy();
        } catch (IOException ex) {
            Logger.getLogger(WindowsProxyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * @param args the command line arguments
     * @throws IOException the IO exception
     */
    public static void main(String[] args) throws IOException {
        ProxySettings settings = new ProxySettings("127.0.0.1", 8080, ProxySettings.ProxyType.HTTP, "http://proxy:7892", true, "user", "pass");
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
        String powerShellScript = IOUtils.resourceToString("/sx/ip/proxies/windows/scripts/windows-set-internet-proxy.ps1", Charset.forName("UTF-8"));
        
        switch (settings.getType()) {
            case HTTP:
                result = runPowerShellMethod(powerShellScript, "http", settings, false, false, null).get("result");
                break;                
            case HTTPS:
                result = runPowerShellMethod(powerShellScript, "https", settings, false, false, null).get("result");
                break;
            case SOCKS:
                result = runPowerShellMethod(powerShellScript, "socks", settings, false, false, null).get("result");
                break;
            case FTP:
                result = runPowerShellMethod(powerShellScript, "ftp", settings, false, false, null).get("result");
                break;
            case HTTP_AND_HTTPS:
                result = runPowerShellMethod(powerShellScript, "http", settings, false, false, null).get("result");
                if("true".equals(result)){
                    result = runPowerShellMethod(powerShellScript, "https", settings, false, false, null).get("result");
                }
                break;
        }
        return "true".equals(result);
    }
    
    /**
     * Method resposible for get the Proxy connection.
     *
     * @return The latest ProxySettings
     *
     * @throws IOException The IO exception
     *
     */
    public static ProxySettings getInternetProxy() throws IOException {
        Map<String, String> results = new HashMap<>();
        String powerShellScript = IOUtils.resourceToString("/sx/ip/proxies/windows/scripts/windows-get-internet-proxy.ps1", Charset.forName("UTF-8"));
        String bypass = runPowerShellMethod(powerShellScript,null, null, true, false, "ProxyOverride").get("output");
        String server = runPowerShellMethod(powerShellScript,null, null, true, false, "ProxyServer").get("output");
        String acs = runPowerShellMethod(powerShellScript,null, null, true, false, "AutoConfigURL").get("output");
        if (results.size() > 0) {
            String byPassValue = results.get("ignore-hosts");
            boolean hasByPass = !bypass.isEmpty();
            
            ProxySettings settings = null; //new ProxySettings(results.get("host"), Integer.valueOf(results.get("port")), ProxySettings.ProxyType.valueOf(type),
//                                                       results.get("autoconfig-url"), hasByPass, results.get("authentication-user"), results.get("authentication-password"));
            return settings;
        }
        return null;
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
        String powerShellScript = IOUtils.resourceToString("/sx/ip/proxies/windows/scripts/windows-disable-internet-proxy.ps1", Charset.forName("UTF-8"));
        return "true".equals(runPowerShellMethod(powerShellScript,null, null, true, false, null).get("result"));
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
    private static Map<String, String> runPowerShellMethod(String powerShellScript, String proxyType, ProxySettings settings, boolean disable, boolean getScript, String name) throws IOException {
        System.out.println("Test commands starting....");
        Map<String, String> response = new HashMap<>();
        String psMethod;
        PowerShell powerShell = null;
        
        if(disable){
            psMethod = "Disable-InternetProxy";
        }else if(getScript){
            psMethod = "Get-InternetProxy -name "+name;
        }else{
            psMethod = "Set-InternetProxy -proxy \"" +proxyType +"=" + settings.getProxyHost() + ":" + settings.getProxyPort() + "\" " 
                       + (settings.getAcsUrl() != null && !settings.getAcsUrl().isEmpty() ? "-acs \"" + settings.getAcsUrl() + "\"" : "")
                       + " "
                       + (settings.getAuthUser()!= null && !settings.getAuthUser().isEmpty() ? "-authuser \""+settings.getAuthUser()+"\"" : "")
                       + " "
                       + (settings.getAuthPass()!= null && !settings.getAuthPass().isEmpty() ? "-authpass \""+settings.getAuthPass()+"\"" : "")
                       + " "
                       + (settings.getBypassOnLocal() ? "-bypass \"true\"" : "");
            
            powerShellScript = IOUtils.resourceToString("/sx/ip/proxies/windows/scripts/windows-set-internet-proxy.ps1", Charset.forName("UTF-8"));
        }        
        
        try {
             Map<String, String> myConfig = new HashMap<>();
            myConfig.put("maxWait", "80000");
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
