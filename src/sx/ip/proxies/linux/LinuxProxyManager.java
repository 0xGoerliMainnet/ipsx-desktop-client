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
    public static void main(String[] args) throws IOException, InterruptedException, ProxySetupException {
        ProxySettings settings = new ProxySettings("127.0.0.1", 8080, ProxySettings.ProxyType.HTTP, null, true, null, null);
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

        StringBuilder stringCommand = new StringBuilder();
        if (settings.getProxyHost() != null) {
            stringCommand.append("gsettings set org.gnome.system.proxy mode 'manual'");
            stringCommand.append(" & ");
            stringCommand.append("gsettings set org.gnome.system.proxy.http enabled true");
            stringCommand.append(" & ");

            switch (settings.getType()) {
                case HTTP:
                    stringCommand.append("gsettings set org.gnome.system.proxy.http host 'proxyHost'");
                    stringCommand.append(" & ");
                    stringCommand.append("gsettings set org.gnome.system.proxy.http port 'proxyPort'");
                    break;
                case HTTPS:
                    stringCommand.append("gsettings set org.gnome.system.proxy.https host 'proxyHost'");
                    stringCommand.append(" & ");
                    stringCommand.append("gsettings set org.gnome.system.proxy.https port 'proxyPort'");
                    break;
                case SOCKS:
                    stringCommand.append("gsettings set org.gnome.system.proxy.socks host 'proxyHost'");
                    stringCommand.append(" & ");
                    stringCommand.append("gsettings set org.gnome.system.proxy.socks port 'proxyPort'");
                    break;
                case FTP:
                    stringCommand.append("gsettings set org.gnome.system.proxy.ftp host 'proxyHost'");
                    stringCommand.append(" & ");
                    stringCommand.append("gsettings set org.gnome.system.proxy.ftp port 'proxyPort'");
                    break;
                case HTTP_AND_HTTPS:
                    stringCommand.append("gsettings set org.gnome.system.proxy.http host 'proxyHost'");
                    stringCommand.append(" & ");
                    stringCommand.append("gsettings set org.gnome.system.proxy.http port 'proxyPort'");
                    stringCommand.append(" & ");
                    stringCommand.append("gsettings set org.gnome.system.proxy.https host 'proxyHost'");
                    stringCommand.append(" & ");
                    stringCommand.append("gsettings set org.gnome.system.proxy.https port 'proxyPort'");
                    break;
            }
        } else {
            stringCommand.append("gsettings set org.gnome.system.proxy mode 'none'");
            stringCommand.append(" & ");
            stringCommand.append("gsettings set org.gnome.system.proxy.http enabled false");
        }

        if ((settings.getAuthUser() != null) && (settings.getAuthUser().isEmpty())) {
            stringCommand.append(" & ");
            stringCommand.append("gsettings set org.gnome.system.proxy.http authentication-user 'userName'");
            stringCommand.append(" & ");
            stringCommand.append("gsettings set org.gnome.system.proxy.http authentication-password 'userPassword'");
        }

        if (settings.getBypassOnLocal()) {
            stringCommand.append(" & ");
            stringCommand.append("gsettings set org.gnome.system.proxy ignore-hosts \"['localhost',  '127.0.0.1', 'all', 'other', 'hosts']\"");
        }
        
        if((settings.getAcsUrl() != null) && (settings.getAcsUrl().isEmpty())){
            stringCommand.append(" & ");
            stringCommand.append("gsettings set org.gnome.system.proxy mode 'auto'");
            stringCommand.append(" & ");
            stringCommand.append("gsettings set org.gnome.system.proxy autoconfig-url 'acsUrl'");
        }

        String commandLine = stringCommand.toString()
                .replaceAll("proxyHost", settings.getProxyHost())
                .replaceAll("proxyPort", String.valueOf(settings.getProxyPort())
                .replaceAll("userName", settings.getAuthUser())
                .replaceAll("userPassword", settings.getAuthPass())
                .replaceAll("acsUrl", settings.getAcsUrl()));
        
        System.err.println(commandLine);
        runCommandLine(commandLine, null, null, null, 3000);
    }

    /**
     * Method resposible for disable the Proxy connection.
     *
     * @throws IOException The IO exception
     *
     */
    public static void disableInternetProxy() throws IOException {
        StringBuilder stringCommand = new StringBuilder();
        stringCommand.append("gsettings set org.gnome.system.proxy mode 'none'");
        stringCommand.append(" & ");
        stringCommand.append("gsettings set org.gnome.system.proxy.http enabled false");
        System.err.println(stringCommand.toString());
        //runCommandLine(stringCommand.toString(), null, null, null, 3000);
    }

    /**
     * Method responsible for execute the command lines.
     *
     * @param cmd The command line to be executed
     * @param args Arguments for the command line
     * @param pumpStreamHandle Handle with sub-processes output and errors
     * @param executeResultHandle Handle with the execution result
     * @param timeout The watchdog timeout
     * @throws IOException The IO exception
     *
     */
    private static void runCommandLine(String cmd, String[] args, PumpStreamHandler pumpStreamHandle,
            ExecuteResultHandler executeResultHandle, int timeout) throws IOException {
        System.out.println("Test commands starting....");

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
