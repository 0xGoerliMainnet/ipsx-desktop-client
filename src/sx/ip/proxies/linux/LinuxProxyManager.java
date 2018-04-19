package sx.ip.proxies.linux;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sx.ip.proxies.ProxyManager;
import sx.ip.proxies.ProxySettings;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.ExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;

/**
 * Proxy Manager to be used when the OS is Linux
 */
public class LinuxProxyManager extends ProxyManager {

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
     * Method responsible for set the Proxy settings.
     *
     * @param settings A ProxySettings instance that hold all necessary
     * configurations
     *
     * @return A boolean indicating the insertion status
     * 
     * @throws ProxySetupException The Proxy setup exception
     */
    @Override
    public boolean setProxySettings(ProxySettings settings) throws ProxySetupException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Method responsible for get the latest Proxy settings.
     *
     * @return The latest Proxy settings
     * 
     * @throws ProxySetupException The Proxy setup exception
     */
    @Override
    public ProxySettings getProxySettings() throws ProxySetupException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Main function.
     *
     * @param args the command line arguments
     *
     * @throws IOException The IO exception
     *
     * @throws InterruptedException The interrupted exception
     */
    public static void main(String[] args) throws IOException, InterruptedException, ProxySetupException {
        ProxySettings settings = new ProxySettings("127.0.0.2", 7001, ProxySettings.ProxyType.HTTPS, null, true, null, null);
        setInternetProxy(settings);
        //disableInternetProxy();

    }

    /**
     * Method resposible for start the Proxy connection.
     *
     * @param settings A ProxySettings instance that hold all necessary
     * configurations
     *
     * @throws IOException The IO exception
     *
     * @throws InterruptedException The interrupted exception
     */
    public static void setInternetProxy(ProxySettings settings) throws IOException, InterruptedException {
        //Just a test call for valid the command line execution
        List<String[]> commandList = new ArrayList<>(); 
        
        if (settings.getProxyHost() != null) {
            commandList.add(new String[] {"set", "org.gnome.system.proxy","mode","'manual'"});
            commandList.add(new String[] {"set", "org.gnome.system.proxy.http","enabled","true"});

            switch (settings.getType()) {
                case HTTP:
                    commandList.add(new String[] {"set", "org.gnome.system.proxy.http","host",settings.getProxyHost()});
                    commandList.add(new String[] {"set", "org.gnome.system.proxy.http","port", String.valueOf(settings.getProxyPort())});
                    break;
                case HTTPS:
                    commandList.add(new String[] {"set", "org.gnome.system.proxy.https","host",settings.getProxyHost()});
                    commandList.add(new String[] {"set", "org.gnome.system.proxy.https","port", String.valueOf(settings.getProxyPort())});
                    break;
                case SOCKS:
                    commandList.add(new String[] {"set", "org.gnome.system.proxy.socks","host",settings.getProxyHost()});
                    commandList.add(new String[] {"set", "org.gnome.system.proxy.socks","port", String.valueOf(settings.getProxyPort())});
                    break;
                case FTP:
                    commandList.add(new String[] {"set", "org.gnome.system.proxy.ftp","host",settings.getProxyHost()});
                    commandList.add(new String[] {"set", "org.gnome.system.proxy.ftp","port", String.valueOf(settings.getProxyPort())});
                    break;
                case HTTP_AND_HTTPS:
                    commandList.add(new String[] {"set", "org.gnome.system.proxy.http","host",settings.getProxyHost()});
                    commandList.add(new String[] {"set", "org.gnome.system.proxy.http","port", String.valueOf(settings.getProxyPort())});
                    commandList.add(new String[] {"set", "org.gnome.system.proxy.https","host",settings.getProxyHost()});
                    commandList.add(new String[] {"set", "org.gnome.system.proxy.https","port", String.valueOf(settings.getProxyPort())});
                    break;
            }
        } else {
            commandList.add(new String[] {"set", "org.gnome.system.proxy","mode","'none'"});
            commandList.add(new String[] {"set", "org.gnome.system.proxy.http","enabled","false"});
        }

        if ((settings.getAuthUser() != null) && (settings.getAuthUser().isEmpty())) {
            commandList.add(new String[] {"set", "org.gnome.system.proxy.http","authentication-user",settings.getAuthUser()});
            commandList.add(new String[] {"set", "org.gnome.system.proxy.http","authentication-password",settings.getAuthPass()});
        }

        if (settings.getBypassOnLocal()) {
            commandList.add(new String[] {"set", "org.gnome.system.proxy","ignore-hosts","\"['localhost',  '127.0.0.1', 'all', 'other', 'hosts']\""});
        }
        
        if((settings.getAcsUrl() != null) && (settings.getAcsUrl().isEmpty())){
            commandList.add(new String[] {"set", "org.gnome.system.proxy","mode","'auto'"});
            commandList.add(new String[] {"set", "org.gnome.system.proxy","autoconfig-url",settings.getAcsUrl()});
        }
        
        runCommandLine(commandList, null, null, 3000);
    }

    /**
     * Method resposible for disable the Proxy connection.
     *
     * @throws IOException The IO exception
     *
     */
    public static void disableInternetProxy() throws IOException {
        List<String[]> commandList = new ArrayList<>();       
                
        commandList.add(new String[] {"set", "org.gnome.system.proxy","mode","'none'"});
        commandList.add(new String[] {"set", "org.gnome.system.proxy.http","enabled","false"});
        commandList.add(new String[] {"set", "org.gnome.system.proxy","use-same-proxy","false"});
        
        runCommandLine(commandList, null, null, 3000);
    }

    /**
     * Method responsible for execute the command lines.
     *
     * @param commandList List of commands to be executed
     * @param pumpStreamHandle Handle with sub-processes output and errors
     * @param executeResultHandle Handle with the execution result
     * @param timeout The watchdog timeout
     * 
     * @throws IOException The IO exception
     *
     */
    private static void runCommandLine(List<String[]> commandList, PumpStreamHandler pumpStreamHandle,
            ExecuteResultHandler executeResultHandle, int timeout) throws IOException {
        System.out.println("Test commands starting....");
        for ( String[] args: commandList) {

            executor = new DefaultExecutor();

            if (executeResultHandle == null) {
                executeResultHandle = new DefaultExecuteResultHandler();
            }

            if (pumpStreamHandle == null) {
                pumpStreamHandle = new PumpStreamHandler(System.out);
            }

            CommandLine commandLine = new CommandLine("gsettings");

            executor.setStreamHandler(pumpStreamHandle);       

            watchdog = new ExecuteWatchdog(timeout);

            if (args != null && args.length > 0) {
                for (String arg : args) {
                    commandLine.addArgument(arg);
                }
            }

            executor.execute(commandLine, executeResultHandle);
            System.out.println("Executing command:" + commandLine.toString());
        }
    }
}
