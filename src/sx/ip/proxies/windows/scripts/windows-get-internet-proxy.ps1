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
#   Setting proxy information, (optinal) Automatic Configuration and (optinal) Authentication Script:
#       Set-InternetProxy -proxy "proxy:7890" -acs "http://proxy:7892" -authuser "username" -authpass "password" -bypass
#
# SOURCE
#   https://gallery.technet.microsoft.com/scriptcenter/PowerShell-function-Get-cba2abf5
####

Function Get-InternetProxy
{
    [CmdletBinding()]
    Param(        
        [Parameter(Mandatory=$True,ValueFromPipeline=$true,ValueFromPipelineByPropertyName=$true)]
        [String]$name
    )

    Begin
    {
            $regKey="HKCU:\Software\Microsoft\Windows\CurrentVersion\Internet Settings"        
    }
    
    Process
    {        
        $output = Get-ItemProperty -path $regKey -name $name
        return $output
        
    } 
    
    End
    {
        Write-Output "$name field obtained"
        Write-Output "$name : $output"
        
    }
}

# Keep this line and make sure there is an empty line below this one

