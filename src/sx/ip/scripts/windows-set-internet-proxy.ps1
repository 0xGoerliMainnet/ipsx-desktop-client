####
# SET-INTERNETPROXY
#
# DESCRIPTION
#   This function will set the proxy server and (optional) Automatic configuration script.
#
# SYNTAX
#   Set-InternetProxy [-Proxy] <string[]> [[-acs] <string[]>]  [<CommonParameters>]
#
# EXAMPLES 
#   Setting proxy information: 
#        Set-InternetProxy -proxy "127.0.0.1:8080" 
#   Setting a socks proxy: 
#        Set-InternetProxy -proxy "socks=127.0.0.1:8080" 
#   Setting proxy information and (optinal) Automatic Configuration Script:
#       Set-InternetProxy -proxy "proxy:7890" -acs "http://proxy:7892" 
#
# SOURCE
#   https://gallery.technet.microsoft.com/scriptcenter/PowerShell-function-Get-cba2abf5
####

Function Set-InternetProxy
{
    [CmdletBinding()]
    Param(        
        [Parameter(Mandatory=$True,ValueFromPipeline=$true,ValueFromPipelineByPropertyName=$true)]
        [String[]]$Proxy,

        [Parameter(Mandatory=$False,ValueFromPipeline=$true,ValueFromPipelineByPropertyName=$true)]
        [AllowEmptyString()]
        [String[]]$acs                
    )

    Begin
    {
            $regKey="HKCU:\Software\Microsoft\Windows\CurrentVersion\Internet Settings"        
    }
    
    Process
    {        
        Set-ItemProperty -path $regKey ProxyEnable -value 1
        Set-ItemProperty -path $regKey ProxyServer -value $proxy   
        Set-ItemProperty -path $regKey ProxyOverride -value "<local>"                    
                         
        if($acs) 
        {                        
            Set-ItemProperty -path $regKey AutoConfigURL -Value $acs          
        }
    } 
    
    End
    {
        Write-Output "Proxy is now enabled"
        Write-Output "Proxy Server : $proxy"
        if ($acs)
        {            
            Write-Output "Automatic Configuration Script : $acs"
        }
        else
        {            
            Write-Output "Automatic Configuration Script : Not Defined"
        }
    }
}

# Keep this line and make sure there is an empty line below this one

