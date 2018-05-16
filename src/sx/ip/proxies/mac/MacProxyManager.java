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
package sx.ip.proxies.mac;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.LoggerFactory;
import sx.ip.proxies.ProxyManager;
import sx.ip.proxies.ProxySettings;

/**
 * Proxy Manager to be used when the OS is Mac.
 */
public class MacProxyManager extends ProxyManager {
    
    /** The logger Object.  */
    static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MacProxyManager.class);

    /** Object responsible for manage the command line executions. */
    static Executor executor;

    /** Object responsible for handle of the process. */
    static ExecuteWatchdog watchdog;

    /** Object responsible for handle with the respective result. */
    static DefaultExecuteResultHandler resultHandler;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setProxySettings(ProxySettings settings) throws ProxySetupException {
        boolean commandResult = false;
        try {
            commandResult = setInternetProxy(settings);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new ProxySetupException("Internal error: " + ex.getMessage(), ex);
        } catch (InterruptedException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new ProxySetupException("Internal error: " + ex.getMessage(), ex);
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
            throw new ProxySetupException("Internal error: " + ex.getMessage(), ex);
        } catch (ParseException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new ProxySetupException("Internal error: " + ex.getMessage(), ex);
        } catch (InterruptedException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new ProxySetupException("Internal error: " + ex.getMessage(), ex);
        }
    }

    /**
     * Main function for tests.
     *
     * @param args 
     *          The command line arguments
     *
     * @throws IOException The IO exception.
     *
     * @throws InterruptedException The interrupted exception.
     *
     * @throws ProxySetupException The proxy setup exception.
     *
     * @throws ParseException The parse exception.
     */
    public static void main(String[] args) throws IOException, InterruptedException, ProxySetupException, ParseException {
        ProxySettings settings = new ProxySettings(null, 8080, ProxySettings.ProxyType.HTTP_AND_HTTPS, null, true, null, null);
        setInternetProxy(settings);
        getInternetProxy();
    }

    /**
     * Method resposible for start the Proxy connection.
     *
     * @param settings 
     *          A ProxySettings instance that hold all necessary configurations
     *
     * @throws IOException The IO exception.
     *
     * @throws InterruptedException The interrupted exception.
     * 
     *  @return If the command was executed or not.
     */
    public static boolean setInternetProxy(ProxySettings settings) throws IOException, InterruptedException {
        //Just a test call for valid the command line execution
        List<String[]> commandList = new ArrayList<>();
        Map<String, String> response = new HashMap<>();

        if (settings.getProxyHost() != null && settings.getProxyHost().length() > 0) {
            switch (settings.getType()) {
                case HTTP:
                    if ((settings.getAuthUser() != null) && (settings.getAuthUser().isEmpty())) {
                        commandList.add(new String[]{"-setwebproxy", "\"Ethernet\"", settings.getProxyHost(), String.valueOf(settings.getProxyPort()), "on", settings.getAuthUser(), settings.getAuthPass()});
                    } else {
                        commandList.add(new String[]{"-setwebproxy", "\"Ethernet\"", settings.getProxyHost(), String.valueOf(settings.getProxyPort())});
                    }
                    break;
                case HTTPS:
                    if ((settings.getAuthUser() != null) && (settings.getAuthUser().isEmpty())) {
                        commandList.add(new String[]{"-setsecurewebproxy", "\"Ethernet\"", settings.getProxyHost(), String.valueOf(settings.getProxyPort()), "on", settings.getAuthUser(), settings.getAuthPass()});
                    } else {
                        commandList.add(new String[]{"-setsecurewebproxy", "\"Ethernet\"", settings.getProxyHost(), String.valueOf(settings.getProxyPort())});
                    }
                    break;
                case SOCKS:
                    if ((settings.getAuthUser() != null) && (settings.getAuthUser().isEmpty())) {
                        commandList.add(new String[]{"-setsocksfirewallproxy", "\"Ethernet\"", settings.getProxyHost(), String.valueOf(settings.getProxyPort())});
                    } else {
                        commandList.add(new String[]{"-setsocksfirewallproxy", "\"Ethernet\"", settings.getProxyHost(), String.valueOf(settings.getProxyPort())});
                    }
                    break;
                case FTP:
                    if ((settings.getAuthUser() != null) && (settings.getAuthUser().isEmpty())) {
                        commandList.add(new String[]{"-setftpproxy", "\"Ethernet\"", settings.getProxyHost(), String.valueOf(settings.getProxyPort())});
                    } else {
                        commandList.add(new String[]{"-setftpproxy", "\"Ethernet\"", settings.getProxyHost(), String.valueOf(settings.getProxyPort())});
                    }
                    break;
                case HTTP_AND_HTTPS:
                    if ((settings.getAuthUser() != null) && (settings.getAuthUser().isEmpty())) {
                        commandList.add(new String[]{"-setwebproxy", "\"Ethernet\"", settings.getProxyHost(), String.valueOf(settings.getProxyPort()), "on", settings.getAuthUser(), settings.getAuthPass()});
                        commandList.add(new String[]{"-setsecurewebproxy", "\"Ethernet\"", settings.getProxyHost(), String.valueOf(settings.getProxyPort()), "on", settings.getAuthUser(), settings.getAuthPass()});
                    } else {
                        commandList.add(new String[]{"-setwebproxy", "\"Ethernet\"", settings.getProxyHost(), String.valueOf(settings.getProxyPort())});
                        commandList.add(new String[]{"-setsecurewebproxy", "\"Ethernet\"", settings.getProxyHost(), String.valueOf(settings.getProxyPort())});
                    }

                    break;
            }
            if (settings.getBypassOnLocal()) {
                commandList.add(new String[]{"-setproxybypassdomains", "\"Ethernet\""});
            }

        } else if ((settings.getAcsUrl() != null) && (settings.getAcsUrl().isEmpty())) {
            commandList.add(new String[]{"-setautoproxyurl", "\"Ethernet\"", settings.getAcsUrl()});

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
     * @throws IOException The IO exception.
     *
     * @throws InterruptedException The interrupted exception.
     * 
     * @return If the command was executed or not.
     *
     */
    public static boolean disableInternetProxy() throws IOException, InterruptedException {
        List<String[]> commandList = new ArrayList<>();
        Map<String, String> response = new HashMap<>();
        commandList.add(new String[]{"-setftpproxystate", "\"Ethernet\"", "off"});
        commandList.add(new String[]{"-setwebproxystate", "\"Ethernet\"", "off"});
        commandList.add(new String[]{"-setsecurewebproxystate", "\"Ethernet\"", "off"});
        commandList.add(new String[]{"-setsocksfirewallproxystate", "\"Ethernet\"", "off"});
        commandList.add(new String[]{"-setautoproxyurl", "\"Ethernet\"", ""});

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
     * @throws IOException The IO exception.
     *
     * @throws ParseException The Parse exception.
     *
     * @throws InterruptedException The interrupted exception.
     * 
     * @return The latest ProxySettings
     *
     */
    public static ProxySettings getInternetProxy() throws IOException, ParseException, InterruptedException {
        List<String[]> commandList = new ArrayList<>();
        JSONParser parser = new JSONParser();
        ProxySettings settings;
        String type = null;
        String proxyServer = null;
        String proxyPort = null;
        String proxyAuth;
        String url = null;
        Boolean byPass = false;

        commandList.add(new String[]{"-getftpproxy", "\"Ethernet\""});
        commandList.add(new String[]{"-getwebproxy", "\"Ethernet\""});
        commandList.add(new String[]{"-getsecurewebproxy", "\"Ethernet\""});
        commandList.add(new String[]{"-getsocksfirewallproxy", "\"Ethernet\""});
        commandList.add(new String[]{"-getautoproxyurl", "\"Ethernet\""});

        String[] byPassCommand = new String[]{"-getproxybypassdomains", "\"Ethernet\""};

        for (String[] command : commandList) {
            Map<String, String> response = runCommandLine(command, null, null, 3000);
            String proxyEnable;
            if (response.get("result").equals("true")) {
                String output = response.get("output");
                Object obj = parser.parse(output);
                JSONObject jsonObject = (JSONObject) obj;
                if (jsonObject.containsKey("Enabled")) {
                    proxyEnable = (String) jsonObject.get("Enabled");
                    if (proxyEnable.equals("Yes")) {
                        switch (command[0]) {
                            case "-getftpproxy":
                                type = "FTP";
                                break;
                            case "-getwebproxy":
                                type = "HTTP";
                                break;
                            case "-getsecurewebproxy":
                                type = "HTTPS";
                                break;
                            case "-getsocksfirewallproxy":
                                type = "SOCKS";
                                break;
                        }
                        proxyServer = (String) jsonObject.get("Server");
                        proxyPort = (String) jsonObject.get("Port");
                        proxyAuth = (String) jsonObject.get("Authenticated Proxy Enabled");
                        url = (String) jsonObject.get("URL");
                    }
                }
                System.err.println(command[0] + ":" + output);
            }
        }

        Map<String, String> response = runCommandLine(byPassCommand, null, null, 3000);

        if (response.get("result").equals("true")) {
            String output = response.get("output");
            if (!output.trim().isEmpty()) {
                byPass = true;
            }
            System.err.println(byPassCommand[0] + ":" + output);
        }

        settings = new ProxySettings(proxyServer, Integer.valueOf(proxyPort), ProxySettings.ProxyType.valueOf(type),
                url, byPass, "", "");
        return settings;
    }

    /**
     * Method responsible for execute the command lines.
     *
     * @param commandList 
     *              List of commands to be executed
     * @param pumpStreamHandle 
     *              Handle with sub-processes output and errors
     * @param executeResultHandle 
     *              Handle with the execution result
     * @param timeout 
     *              The watchdog timeout
     *
     * @return A boolean indicating if the command was executed or not.
     */
    private static Map<String, String> runCommandLine(String[] args, PumpStreamHandler pumpStreamHandle,
            DefaultExecuteResultHandler executeResultHandle, int timeout) throws IOException, InterruptedException {
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

        CommandLine commandLine = new CommandLine("networksetup");

        executor.setStreamHandler(pumpStreamHandle);

        watchdog = new ExecuteWatchdog(timeout);

        if (args != null && args.length > 0) {
            for (String arg : args) {
                commandLine.addArgument(arg);
            }
        }
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

        return response;
    }

}
