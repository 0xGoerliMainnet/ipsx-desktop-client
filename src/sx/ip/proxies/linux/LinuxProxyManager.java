package sx.ip.proxies.linux;

import java.io.IOException;
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
     * @throws ProxySetupException The Proxy setup exception
     */
    @Override
    public boolean setProxySettings(ProxySettings settings) throws ProxySetupException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Method responsible for get the latest Proxy settings.
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
    public static void main(String[] args) throws IOException, InterruptedException {

        setInternetProxy("127.0.0.1", "8080");
        disableInternetProxy();

    }
    
    /**
     * Method resposible for start the Proxy connection.
     *
     * @param ip the ip for do the proxy connection
     * 
     * @param port the port for the proxy connection
     * 
     * @throws IOException The IO exception
     * 
     * @throws InterruptedException The interrupted exception
     */
    public static void setInternetProxy(String ip, String port) throws IOException, InterruptedException {
        //Just a test call for valid the command line execution
        
        StringBuilder stringCommand = new StringBuilder();
        stringCommand.append("export http_proxy=\"http://");
        stringCommand.append(ip);
        stringCommand.append(":");
        stringCommand.append(port);
        stringCommand.append("\"");
        stringCommand.append(" & ");
        
        stringCommand.append("export https_proxy=\"http://");
        stringCommand.append(ip);
        stringCommand.append(":");
        stringCommand.append(port);
        stringCommand.append("\"");
        stringCommand.append(" & ");
        
        stringCommand.append("export ftp_proxy=\"http://");
        stringCommand.append(ip);
        stringCommand.append(":");
        stringCommand.append(port);
        stringCommand.append("\"");
        stringCommand.append(" & ");
        
        stringCommand.append("env | grep -i proxy");
        stringCommand.append(" & ");
        stringCommand.append("wget -q -O - checkip.dyndns.org | sed -e 's/.*Current IP Address: //' -e 's/<.*$//'");        
        stringCommand.append(" & ");
        stringCommand.append("time wget -q -O - checkip.dyndns.org | sed -e 's/.*Current IP Address: //' -e 's/<.*$//'");
        
        runCommandLine(stringCommand.toString(), null, null, null, 3000);
    }    
    
    /**
     * Method resposible for disable the Proxy connection.
     * 
     * @throws IOException The IO exception
     * 
     */
    public static void disableInternetProxy() throws IOException {
        StringBuilder stringCommand = new StringBuilder();
        stringCommand.append("unset http_proxy");
        stringCommand.append(" & ");
        
        stringCommand.append("unset https_proxy");
        stringCommand.append(" & ");
        
        stringCommand.append("unset ftp_proxy");
        stringCommand.append(" & ");
        
        stringCommand.append("env | grep -i proxy");
        stringCommand.append(" & ");
        stringCommand.append("wget -q -O - checkip.dyndns.org | sed -e 's/.*Current IP Address: //' -e 's/<.*$//'");        
        stringCommand.append(" & ");
        stringCommand.append("time wget -q -O - checkip.dyndns.org | sed -e 's/.*Current IP Address: //' -e 's/<.*$//'");
        
        runCommandLine(stringCommand.toString(), null, null, null, 3000);
    }    
    
    /**
     * Method responsible for execute the command lines.
     *
     * @param cmd 
     *           The command line to be executed 
     * @param args 
     *           Arguments for the command line 
     * @param pumpStreamHandle 
     *           Handle with sub-processes output and errors
     * @param executeResultHandle 
     *           Handle with the execution result
     * @param timeout 
     *           The watchdog timeout
     * @throws IOException 
     *           The IO exception
     * 
     */
    private static void runCommandLine(String cmd, String[] args, PumpStreamHandler pumpStreamHandle,
            ExecuteResultHandler executeResultHandle, int timeout) throws IOException {
        System.out.println("Test sequence starting....");

        executor = new DefaultExecutor();

        CommandLine commandLine = new CommandLine(cmd);

        if (pumpStreamHandle == null) {
            executor.setStreamHandler(new PumpStreamHandler(System.out));
        } else {
            executor.setStreamHandler(pumpStreamHandle);
        }

        watchdog = new ExecuteWatchdog(timeout);

        if (args != null && args.length > 0) {
            for (String arg : args) {
                commandLine.addArgument(arg);
            }
        }

        if (executeResultHandle != null) {
            executor.execute(commandLine, executeResultHandle);
        } else {
            executor.execute(commandLine, new DefaultExecuteResultHandler());
        }
    }
}
