####
# GET-INTERNETPROXY
#
# DESCRIPTION
#   This function will get the proxy server settings.
#
# SYNTAX
#   Get-InternetProxy [-name] <string>
#
# EXAMPLES 
#   Getting proxy information: 
#        Get-InternetProxy -name "ProxyOverride" 
#   
# SOURCE
#   https://gallery.technet.microsoft.com/scriptcenter/PowerShell-function-Get-cba2abf5
####

Function Get-InternetProxy
{
    [CmdletBinding()]
    Param(        
        [Parameter(ValueFromPipeline=$true,ValueFromPipelineByPropertyName=$true)]
        [String]$name
    )

    Begin
    {
            $regKey="HKCU:\Software\Microsoft\Windows\CurrentVersion\Internet Settings"        
    }
    
    Process
    {        
        if($name){
            $output = Get-ItemProperty -path $regKey -name $name
            return $output.$name           
        }Else{
            $c = Get-Credential -m "Please enter with the proxy authentication if exists or cancel"
            $user = $c.GetNetworkCredential().UserName
            $pass = $c.GetNetworkCredential().password
            return $user+":"+$pass
        }
        
    } 
    
    End
    {
        
    }
}

# Keep this line and make sure there is an empty line below this one

