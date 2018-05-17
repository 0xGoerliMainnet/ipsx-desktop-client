####
# DISABLE-INTERNETPROXY
#
# DESCRIPTION
#   This function will disable the proxy server settings
#
# SYNTAX
#   Disable-InternetProxy
#
# EXAMPLES 
#   Disable-InternetProxy
#
# SOURCE
#   https://gallery.technet.microsoft.com/scriptcenter/PowerShell-function-Get-cba2abf5/view/Discussions#content
####

Function Disable-InternetProxy
{
  Begin
    {
        $regKey="HKCU:\Software\Microsoft\Windows\CurrentVersion\Internet Settings"        
    }
    
    Process
    {        
        Set-ItemProperty -path $regKey ProxyEnable -value 0 -ErrorAction Stop
        Set-ItemProperty -path $regKey ProxyServer -value "" -ErrorAction Stop                            
        Set-ItemProperty -path $regKey AutoConfigURL -Value "" -ErrorAction Stop       
    } 
    
    End
    {
        Write-Output "Proxy is now Disabled"              
    }
}

# Keep this line and make sure there is an empty line below this one

