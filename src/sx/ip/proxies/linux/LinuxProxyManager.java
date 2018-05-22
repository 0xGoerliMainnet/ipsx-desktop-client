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
package sx.ip.proxies.linux;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import sx.ip.proxies.ProxyManager;
import sx.ip.proxies.ProxySettings;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.LoggerFactory;

/**
 * Proxy Manager to be used when the OS is Linux
 */
public class LinuxProxyManager extends ProxyManager {
    
    static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LinuxProxyManager.class);

    /**
     * Object responsible for manage the command line executions.
     */
    static Executor executor;

    /**
     * Object responsible for handle of the process.
     */
    static ExecuteWatchdog watchdog;

    /**
     * Object responsible for handle with the respective result.
     */
    static DefaultExecuteResultHandler resultHandler;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setProxySettings(ProxySettings settings) throws ProxySetupException {
        boolean commandResult = false;
        try {
            commandResult = setInternetProxy(settings);
        } catch (IOException | InterruptedException ex) {
            LOGGER.error(ex.getMessage(), ex);
            Logger.getLogger(LinuxProxyManager.class.getName()).log(Level.SEVERE, null, ex);
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
            LOGGER.error(ex.getMessage(), ex);
            Logger.getLogger(LinuxProxyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Main function.
     *
     * @param args the command line arguments
     *
     * @throws IOException The IO exception
     *
     * @throws InterruptedException The interrupted exception
     * @throws ProxySetupException The proxy setup exception
     */
    public static void main(String[] args) throws IOException, InterruptedException, ProxySetupException {
        ProxySettings settings = new ProxySettings("127.0.0.1", 8080, ProxySettings.ProxyType.HTTP, null, true, null, null);
        setInternetProxy(settings);
        getInternetProxy();
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
     *
     * @throws InterruptedException The interrupted exception
     */
    public static boolean setInternetProxy(ProxySettings settings) throws IOException, InterruptedException {
        //Just a test call for valid the command line execution
        List<String[]> commandList = new ArrayList<>();
        Map<String, String> response = new HashMap<>();

        if(settings.getProxyHost() != null && settings.getProxyHost().length() > 0){
            commandList.add(new String[]{"reset-recursively", "org.gnome.system.proxy"});
            commandList.add(new String[]{"set", "org.gnome.system.proxy", "mode", "'manual'"});
            commandList.add(new String[]{"set", "org.gnome.system.proxy.http", "enabled", "true"});

            switch (settings.getType()) {
                case HTTP:
                    commandList.add(new String[]{"set", "org.gnome.system.proxy.http", "host", settings.getProxyHost()});
                    commandList.add(new String[]{"set", "org.gnome.system.proxy.http", "port", String.valueOf(settings.getProxyPort())});
                    break;
                case HTTPS:
                    commandList.add(new String[]{"set", "org.gnome.system.proxy.https", "host", settings.getProxyHost()});
                    commandList.add(new String[]{"set", "org.gnome.system.proxy.https", "port", String.valueOf(settings.getProxyPort())});
                    break;
                case SOCKS:
                    commandList.add(new String[]{"set", "org.gnome.system.proxy.socks", "host", settings.getProxyHost()});
                    commandList.add(new String[]{"set", "org.gnome.system.proxy.socks", "port", String.valueOf(settings.getProxyPort())});
                    break;
                case FTP:
                    commandList.add(new String[]{"set", "org.gnome.system.proxy.ftp", "host", settings.getProxyHost()});
                    commandList.add(new String[]{"set", "org.gnome.system.proxy.ftp", "port", String.valueOf(settings.getProxyPort())});
                    break;
                case HTTP_AND_HTTPS:
                    commandList.add(new String[]{"set", "org.gnome.system.proxy.http", "host", settings.getProxyHost()});
                    commandList.add(new String[]{"set", "org.gnome.system.proxy.http", "port", String.valueOf(settings.getProxyPort())});
                    commandList.add(new String[]{"set", "org.gnome.system.proxy.https", "host", settings.getProxyHost()});
                    commandList.add(new String[]{"set", "org.gnome.system.proxy.https", "port", String.valueOf(settings.getProxyPort())});
                    break;
            }
            if ((settings.getAuthUser() != null) && (settings.getAuthUser().isEmpty())) {
                commandList.add(new String[]{"set", "org.gnome.system.proxy.http", "authentication-user", settings.getAuthUser()});
                commandList.add(new String[]{"set", "org.gnome.system.proxy.http", "authentication-password", settings.getAuthPass()});
            }

            if (settings.getBypassOnLocal()) {
                //commandList.add(new String[]{"set", "org.gnome.system.proxy", "ignore-hosts", "\"['localhost',  '127.0.0.1', 'all', 'other', 'hosts']\""});
            }
        }else if(settings.getAcsUrl() != null){
            commandList.add(new String[]{"set", "org.gnome.system.proxy", "mode", "'auto'"});
            commandList.add(new String[]{"set", "org.gnome.system.proxy", "autoconfig-url", settings.getAcsUrl()});
        } else {
            return disableInternetProxy();
        }

        for (String[] command : commandList) {
            response.putAll(runCommandLine(command, null, null, 3000));
            if (response.get("result").equals("false")) {
                return false;
            }
        }
        return true;
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
        List<String[]> commandList = new ArrayList<>();
        Map<String, String> response = new HashMap<>();
        commandList.add(new String[]{"reset-recursively", "org.gnome.system.proxy"});
        commandList.add(new String[]{"set", "org.gnome.system.proxy", "mode", "'none'"});
        commandList.add(new String[]{"set", "org.gnome.system.proxy.http", "enabled", "false"});

        for (String[] command : commandList) {
            response.putAll(runCommandLine(command, null, null, 3000));
            if (response.get("result").equals("false")) {
                return false;
            }
        }
        return true;

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
        List<String[]> othersCommands = new ArrayList<>();
        List<String[]> hostPortCommands = new ArrayList<>();
        Map<String, String> results = new HashMap<>();
        String type = "";
        
        othersCommands.add(new String[]{"get", "org.gnome.system.proxy", "autoconfig-url"});
        othersCommands.add(new String[]{"get", "org.gnome.system.proxy", "ignore-hosts"});
        othersCommands.add(new String[]{"get", "org.gnome.system.proxy.http", "authentication-user"});
        othersCommands.add(new String[]{"get", "org.gnome.system.proxy.http", "authentication-password"});

        hostPortCommands.add(new String[]{"get", "org.gnome.system.proxy.ftp", "host"});
        hostPortCommands.add(new String[]{"get", "org.gnome.system.proxy.ftp", "port"});
        hostPortCommands.add(new String[]{"get", "org.gnome.system.proxy.socks", "host"});
        hostPortCommands.add(new String[]{"get", "org.gnome.system.proxy.socks", "port"});
        hostPortCommands.add(new String[]{"get", "org.gnome.system.proxy.http", "host"});
        hostPortCommands.add(new String[]{"get", "org.gnome.system.proxy.http", "port"});
        hostPortCommands.add(new String[]{"get", "org.gnome.system.proxy.https", "host"});
        hostPortCommands.add(new String[]{"get", "org.gnome.system.proxy.https", "port"});

        for (String[] command : othersCommands) {
            Map<String, String> response = runCommandLine(command, null, null, 3000);

            if (response.get("result").equals("true")) {
                String output = response.get("output").trim().replaceAll("'", "").replaceAll("\\[", "").replaceAll("\\]", "");
                results.put(command[2], output);
                System.err.println(command[2] + ":" + output);
            }
        }

        for (String[] command : hostPortCommands) {
            Map<String, String> response = runCommandLine(command, null, null, 3000);

            if (response.get("result").equals("true")) {
                String output = response.get("output").trim().replaceAll("'", "");
                if (!output.isEmpty() && !"0".equals(output)) {
                    results.put(command[2], output);
                    System.err.println(command[2] + ":" + output);
                    type = command[1].split("\\.")[4].toUpperCase();
                }

            }
        }

        if (results.size() > 0) {
            String byPassValue = results.get("ignore-hosts");
            boolean hasByPass = (byPassValue.split(",").length > 3);
            
            ProxySettings settings = new ProxySettings(results.get("host"), Integer.valueOf(results.get("port")), ProxySettings.ProxyType.valueOf(type),
                                                       results.get("autoconfig-url"), hasByPass, results.get("authentication-user"), results.get("authentication-password"));
            return settings;
        }
        return null;
    }

    /**
     * Method responsible for execute the command lines.
     *
     * @param commandList List of commands to be executed
     * @param pumpStreamHandle Handle with sub-processes output and errors
     * @param executeResultHandle Handle with the execution result
     * @param timeout The watchdog timeout
     *
     * @return A boolean indicating if the command was executed or not
     */
    private static Map<String, String> runCommandLine(String[] args, PumpStreamHandler pumpStreamHandle,
            DefaultExecuteResultHandler executeResultHandle, int timeout) {
        System.out.println("Test commands starting....");
        Map<String, String> response = new HashMap<>();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        executor = new DefaultExecutor();

        if (executeResultHandle == null) {
            executeResultHandle = new DefaultExecuteResultHandler();
        }

        if (pumpStreamHandle == null) {
            pumpStreamHandle = new PumpStreamHandler(outputStream);
        }

        CommandLine commandLine = new CommandLine("gsettings");

        executor.setStreamHandler(pumpStreamHandle);

        watchdog = new ExecuteWatchdog(timeout);

        if (args != null && args.length > 0) {
            for (String arg : args) {
                commandLine.addArgument(arg);
            }
        }
        try {
            executor.execute(commandLine, executeResultHandle);
            executeResultHandle.waitFor();
            int exitValue = executeResultHandle.getExitValue();

            System.out.println("Executing command:" + commandLine.toString());

            if (exitValue != 0) {
                response.put("result", "false");
            } else {
                response.put("result", "true");
            }

            response.put("output", outputStream.toString());
        } catch (InterruptedException | IOException ee) {
            Logger.getLogger(LinuxProxyManager.class.getName()).log(Level.SEVERE, null, ee);
        }

        return response;
    }
}
