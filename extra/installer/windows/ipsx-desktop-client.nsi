; Script generated by the HM NIS Edit Script Wizard.

; HM NIS Edit Wizard helper defines
!define PRODUCT_NAME "IPSX Desktop Client"
!define PRODUCT_VERSION "1.0.0.0"
!define PRODUCT_PUBLISHER "IPSX"
!define PRODUCT_WEB_SITE "https://ip.sx"
!define PRODUCT_DIR_REGKEY "Software\Microsoft\Windows\CurrentVersion\App Paths\ipsx-desktop-client.exe"
!define PRODUCT_UNINST_KEY "Software\Microsoft\Windows\CurrentVersion\Uninstall\${PRODUCT_NAME}"
!define PRODUCT_UNINST_ROOT_KEY "HKLM"

; CUSTOM ------
VIProductVersion  "${PRODUCT_VERSION}"
VIAddVersionKey "ProductName"  "${PRODUCT_NAME}"
VIAddVersionKey "CompanyName"  "${PRODUCT_PUBLISHER}"
VIAddVersionKey "LegalCopyright"  "${PRODUCT_PUBLISHER}"
VIAddVersionKey "FileDescription"  "${PRODUCT_NAME}"
VIAddVersionKey "FileVersion"  "${PRODUCT_VERSION}"

BrandingText "${PRODUCT_NAME} ${PRODUCT_VERSION}"

; MUI 1.67 compatible ------
!include "MUI.nsh"

; MUI Settings
!define MUI_ABORTWARNING
!define MUI_ICON "..\..\..\dist\ipsx-desktop-client.ico"
!define MUI_UNICON "..\..\..\dist\ipsx-desktop-client.ico"

; Welcome page
!define MUI_WELCOMEFINISHPAGE_BITMAP ".\welcome-bg.bmp"
!insertmacro MUI_PAGE_WELCOME
; License page
!insertmacro MUI_PAGE_LICENSE "..\..\..\LICENSE.txt"
; Directory page
!insertmacro MUI_PAGE_DIRECTORY
; Instfiles page
!insertmacro MUI_PAGE_INSTFILES
; Finish page
!define MUI_FINISHPAGE_RUN "$INSTDIR\ipsx-desktop-client.exe"
!insertmacro MUI_PAGE_FINISH

; Uninstaller pages
!insertmacro MUI_UNPAGE_INSTFILES

; Language files
!insertmacro MUI_LANGUAGE "English"

; MUI end ------

Name "${PRODUCT_NAME}"
OutFile "..\..\..\versions\ipsx-desktop-client-setup-latest.exe"
InstallDir "$PROGRAMFILES\IPSX Desktop Client"
InstallDirRegKey HKLM "${PRODUCT_DIR_REGKEY}" ""
ShowInstDetails show
ShowUnInstDetails show

Section "Main Application Files" SEC01
  SetOutPath "$INSTDIR"
  SetOverwrite try
  File "..\..\..\dist\ipsx-desktop-client.exe"
  CreateDirectory "$SMPROGRAMS\IPSX Desktop Client"
  CreateShortCut "$SMPROGRAMS\IPSX Desktop Client\IPSX Desktop Client.lnk" "$INSTDIR\ipsx-desktop-client.exe"
  CreateShortCut "$DESKTOP\IPSX Desktop Client.lnk" "$INSTDIR\ipsx-desktop-client.exe"
  File "..\..\..\dist\ipsx-desktop-client.ico"
  File "..\..\..\dist\IPSX-Desktop-Client.jar"
  SetOutPath "$INSTDIR\jre\bin"
  File "..\..\..\dist\jre\bin\api-ms-win-core-console-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-core-datetime-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-core-debug-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-core-errorhandling-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-core-file-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-core-file-l1-2-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-core-file-l2-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-core-handle-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-core-heap-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-core-interlocked-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-core-libraryloader-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-core-localization-l1-2-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-core-memory-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-core-namedpipe-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-core-processenvironment-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-core-processthreads-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-core-processthreads-l1-1-1.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-core-profile-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-core-rtlsupport-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-core-string-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-core-synch-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-core-synch-l1-2-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-core-sysinfo-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-core-timezone-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-core-util-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-crt-conio-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-crt-convert-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-crt-environment-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-crt-filesystem-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-crt-heap-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-crt-locale-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-crt-math-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-crt-multibyte-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-crt-private-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-crt-process-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-crt-runtime-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-crt-stdio-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-crt-string-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-crt-time-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\api-ms-win-crt-utility-l1-1-0.dll"
  File "..\..\..\dist\jre\bin\awt.dll"
  File "..\..\..\dist\jre\bin\bci.dll"
  SetOutPath "$INSTDIR\jre\bin\client"
  File "..\..\..\dist\jre\bin\client\jvm.dll"
  File "..\..\..\dist\jre\bin\client\Xusage.txt"
  SetOutPath "$INSTDIR\jre\bin"
  File "..\..\..\dist\jre\bin\concrt140.dll"
  File "..\..\..\dist\jre\bin\dcpr.dll"
  File "..\..\..\dist\jre\bin\decora_sse.dll"
  File "..\..\..\dist\jre\bin\deploy.dll"
  SetOutPath "$INSTDIR\jre\bin\dtplugin"
  File "..\..\..\dist\jre\bin\dtplugin\deployJava1.dll"
  File "..\..\..\dist\jre\bin\dtplugin\npdeployJava1.dll"
  SetOutPath "$INSTDIR\jre\bin"
  File "..\..\..\dist\jre\bin\dt_shmem.dll"
  File "..\..\..\dist\jre\bin\dt_socket.dll"
  File "..\..\..\dist\jre\bin\eula.dll"
  File "..\..\..\dist\jre\bin\fontmanager.dll"
  File "..\..\..\dist\jre\bin\fxplugins.dll"
  File "..\..\..\dist\jre\bin\glass.dll"
  File "..\..\..\dist\jre\bin\glib-lite.dll"
  File "..\..\..\dist\jre\bin\gstreamer-lite.dll"
  File "..\..\..\dist\jre\bin\hprof.dll"
  File "..\..\..\dist\jre\bin\instrument.dll"
  File "..\..\..\dist\jre\bin\j2pcsc.dll"
  File "..\..\..\dist\jre\bin\j2pkcs11.dll"
  File "..\..\..\dist\jre\bin\jaas_nt.dll"
  File "..\..\..\dist\jre\bin\jabswitch.exe"
  File "..\..\..\dist\jre\bin\java-rmi.exe"
  File "..\..\..\dist\jre\bin\java.dll"
  File "..\..\..\dist\jre\bin\java.exe"
  File "..\..\..\dist\jre\bin\JavaAccessBridge-32.dll"
  File "..\..\..\dist\jre\bin\JavaAccessBridge.dll"
  File "..\..\..\dist\jre\bin\javacpl.cpl"
  File "..\..\..\dist\jre\bin\javacpl.exe"
  File "..\..\..\dist\jre\bin\javafx_font.dll"
  File "..\..\..\dist\jre\bin\javafx_font_t2k.dll"
  File "..\..\..\dist\jre\bin\javafx_iio.dll"
  File "..\..\..\dist\jre\bin\javaw.exe"
  File "..\..\..\dist\jre\bin\javaws.exe"
  File "..\..\..\dist\jre\bin\java_crw_demo.dll"
  File "..\..\..\dist\jre\bin\jawt.dll"
  File "..\..\..\dist\jre\bin\JAWTAccessBridge-32.dll"
  File "..\..\..\dist\jre\bin\JAWTAccessBridge.dll"
  File "..\..\..\dist\jre\bin\jdwp.dll"
  File "..\..\..\dist\jre\bin\jfr.dll"
  File "..\..\..\dist\jre\bin\jfxmedia.dll"
  File "..\..\..\dist\jre\bin\jfxwebkit.dll"
  File "..\..\..\dist\jre\bin\jjs.exe"
  File "..\..\..\dist\jre\bin\jli.dll"
  File "..\..\..\dist\jre\bin\jp2iexp.dll"
  File "..\..\..\dist\jre\bin\jp2launcher.exe"
  File "..\..\..\dist\jre\bin\jp2native.dll"
  File "..\..\..\dist\jre\bin\jp2ssv.dll"
  File "..\..\..\dist\jre\bin\jpeg.dll"
  File "..\..\..\dist\jre\bin\jsdt.dll"
  File "..\..\..\dist\jre\bin\jsound.dll"
  File "..\..\..\dist\jre\bin\jsoundds.dll"
  File "..\..\..\dist\jre\bin\kcms.dll"
  File "..\..\..\dist\jre\bin\keytool.exe"
  File "..\..\..\dist\jre\bin\kinit.exe"
  File "..\..\..\dist\jre\bin\klist.exe"
  File "..\..\..\dist\jre\bin\ktab.exe"
  File "..\..\..\dist\jre\bin\lcms.dll"
  File "..\..\..\dist\jre\bin\management.dll"
  File "..\..\..\dist\jre\bin\mlib_image.dll"
  File "..\..\..\dist\jre\bin\msvcp140.dll"
  File "..\..\..\dist\jre\bin\msvcr100.dll"
  File "..\..\..\dist\jre\bin\net.dll"
  File "..\..\..\dist\jre\bin\nio.dll"
  File "..\..\..\dist\jre\bin\npt.dll"
  File "..\..\..\dist\jre\bin\orbd.exe"
  File "..\..\..\dist\jre\bin\pack200.exe"
  SetOutPath "$INSTDIR\jre\bin\plugin2"
  File "..\..\..\dist\jre\bin\plugin2\msvcr100.dll"
  File "..\..\..\dist\jre\bin\plugin2\npjp2.dll"
  SetOutPath "$INSTDIR\jre\bin"
  File "..\..\..\dist\jre\bin\policytool.exe"
  File "..\..\..\dist\jre\bin\prism_common.dll"
  File "..\..\..\dist\jre\bin\prism_d3d.dll"
  File "..\..\..\dist\jre\bin\prism_sw.dll"
  File "..\..\..\dist\jre\bin\resource.dll"
  File "..\..\..\dist\jre\bin\rmid.exe"
  File "..\..\..\dist\jre\bin\rmiregistry.exe"
  File "..\..\..\dist\jre\bin\servertool.exe"
  File "..\..\..\dist\jre\bin\splashscreen.dll"
  File "..\..\..\dist\jre\bin\ssv.dll"
  File "..\..\..\dist\jre\bin\ssvagent.exe"
  File "..\..\..\dist\jre\bin\sunec.dll"
  File "..\..\..\dist\jre\bin\sunmscapi.dll"
  File "..\..\..\dist\jre\bin\t2k.dll"
  File "..\..\..\dist\jre\bin\tnameserv.exe"
  File "..\..\..\dist\jre\bin\ucrtbase.dll"
  File "..\..\..\dist\jre\bin\unpack.dll"
  File "..\..\..\dist\jre\bin\unpack200.exe"
  File "..\..\..\dist\jre\bin\vcruntime140.dll"
  File "..\..\..\dist\jre\bin\verify.dll"
  File "..\..\..\dist\jre\bin\w2k_lsa_auth.dll"
  File "..\..\..\dist\jre\bin\WindowsAccessBridge-32.dll"
  File "..\..\..\dist\jre\bin\WindowsAccessBridge.dll"
  File "..\..\..\dist\jre\bin\wsdetect.dll"
  File "..\..\..\dist\jre\bin\zip.dll"
  SetOutPath "$INSTDIR\jre"
  File "..\..\..\dist\jre\COPYRIGHT"
  SetOutPath "$INSTDIR\jre\lib"
  File "..\..\..\dist\jre\lib\accessibility.properties"
  File "..\..\..\dist\jre\lib\calendars.properties"
  File "..\..\..\dist\jre\lib\charsets.jar"
  File "..\..\..\dist\jre\lib\classlist"
  SetOutPath "$INSTDIR\jre\lib\cmm"
  File "..\..\..\dist\jre\lib\cmm\CIEXYZ.pf"
  File "..\..\..\dist\jre\lib\cmm\GRAY.pf"
  File "..\..\..\dist\jre\lib\cmm\LINEAR_RGB.pf"
  File "..\..\..\dist\jre\lib\cmm\PYCC.pf"
  File "..\..\..\dist\jre\lib\cmm\sRGB.pf"
  SetOutPath "$INSTDIR\jre\lib"
  File "..\..\..\dist\jre\lib\content-types.properties"
  File "..\..\..\dist\jre\lib\currency.data"
  SetOutPath "$INSTDIR\jre\lib\deploy"
  File "..\..\..\dist\jre\lib\deploy\ffjcext.zip"
  File "..\..\..\dist\jre\lib\deploy\messages.properties"
  File "..\..\..\dist\jre\lib\deploy\messages_de.properties"
  File "..\..\..\dist\jre\lib\deploy\messages_es.properties"
  File "..\..\..\dist\jre\lib\deploy\messages_fr.properties"
  File "..\..\..\dist\jre\lib\deploy\messages_it.properties"
  File "..\..\..\dist\jre\lib\deploy\messages_ja.properties"
  File "..\..\..\dist\jre\lib\deploy\messages_ko.properties"
  File "..\..\..\dist\jre\lib\deploy\messages_pt_BR.properties"
  File "..\..\..\dist\jre\lib\deploy\messages_sv.properties"
  File "..\..\..\dist\jre\lib\deploy\messages_zh_CN.properties"
  File "..\..\..\dist\jre\lib\deploy\messages_zh_HK.properties"
  File "..\..\..\dist\jre\lib\deploy\messages_zh_TW.properties"
  File "..\..\..\dist\jre\lib\deploy\splash.gif"
  File "..\..\..\dist\jre\lib\deploy\splash@2x.gif"
  File "..\..\..\dist\jre\lib\deploy\splash_11-lic.gif"
  File "..\..\..\dist\jre\lib\deploy\splash_11@2x-lic.gif"
  SetOutPath "$INSTDIR\jre\lib"
  File "..\..\..\dist\jre\lib\deploy.jar"
  SetOutPath "$INSTDIR\jre\lib\ext"
  File "..\..\..\dist\jre\lib\ext\access-bridge-32.jar"
  File "..\..\..\dist\jre\lib\ext\access-bridge.jar"
  File "..\..\..\dist\jre\lib\ext\cldrdata.jar"
  File "..\..\..\dist\jre\lib\ext\dnsns.jar"
  File "..\..\..\dist\jre\lib\ext\jaccess.jar"
  File "..\..\..\dist\jre\lib\ext\jfxrt.jar"
  File "..\..\..\dist\jre\lib\ext\localedata.jar"
  File "..\..\..\dist\jre\lib\ext\meta-index"
  File "..\..\..\dist\jre\lib\ext\nashorn.jar"
  File "..\..\..\dist\jre\lib\ext\sunec.jar"
  File "..\..\..\dist\jre\lib\ext\sunjce_provider.jar"
  File "..\..\..\dist\jre\lib\ext\sunmscapi.jar"
  File "..\..\..\dist\jre\lib\ext\sunpkcs11.jar"
  File "..\..\..\dist\jre\lib\ext\zipfs.jar"
  SetOutPath "$INSTDIR\jre\lib"
  File "..\..\..\dist\jre\lib\flavormap.properties"
  File "..\..\..\dist\jre\lib\fontconfig.bfc"
  File "..\..\..\dist\jre\lib\fontconfig.properties.src"
  SetOutPath "$INSTDIR\jre\lib\fonts"
  File "..\..\..\dist\jre\lib\fonts\LucidaBrightDemiBold.ttf"
  File "..\..\..\dist\jre\lib\fonts\LucidaBrightDemiItalic.ttf"
  File "..\..\..\dist\jre\lib\fonts\LucidaBrightItalic.ttf"
  File "..\..\..\dist\jre\lib\fonts\LucidaBrightRegular.ttf"
  File "..\..\..\dist\jre\lib\fonts\LucidaSansDemiBold.ttf"
  File "..\..\..\dist\jre\lib\fonts\LucidaSansRegular.ttf"
  File "..\..\..\dist\jre\lib\fonts\LucidaTypewriterBold.ttf"
  File "..\..\..\dist\jre\lib\fonts\LucidaTypewriterRegular.ttf"
  SetOutPath "$INSTDIR\jre\lib"
  File "..\..\..\dist\jre\lib\hijrah-config-umalqura.properties"
  SetOutPath "$INSTDIR\jre\lib\i386"
  File "..\..\..\dist\jre\lib\i386\jvm.cfg"
  SetOutPath "$INSTDIR\jre\lib\images\cursors"
  File "..\..\..\dist\jre\lib\images\cursors\cursors.properties"
  File "..\..\..\dist\jre\lib\images\cursors\invalid32x32.gif"
  File "..\..\..\dist\jre\lib\images\cursors\win32_CopyDrop32x32.gif"
  File "..\..\..\dist\jre\lib\images\cursors\win32_CopyNoDrop32x32.gif"
  File "..\..\..\dist\jre\lib\images\cursors\win32_LinkDrop32x32.gif"
  File "..\..\..\dist\jre\lib\images\cursors\win32_LinkNoDrop32x32.gif"
  File "..\..\..\dist\jre\lib\images\cursors\win32_MoveDrop32x32.gif"
  File "..\..\..\dist\jre\lib\images\cursors\win32_MoveNoDrop32x32.gif"
  SetOutPath "$INSTDIR\jre\lib"
  File "..\..\..\dist\jre\lib\javafx.properties"
  File "..\..\..\dist\jre\lib\javaws.jar"
  File "..\..\..\dist\jre\lib\jce.jar"
  SetOutPath "$INSTDIR\jre\lib\jfr"
  File "..\..\..\dist\jre\lib\jfr\default.jfc"
  File "..\..\..\dist\jre\lib\jfr\profile.jfc"
  SetOutPath "$INSTDIR\jre\lib"
  File "..\..\..\dist\jre\lib\jfr.jar"
  File "..\..\..\dist\jre\lib\jfxswt.jar"
  File "..\..\..\dist\jre\lib\jsse.jar"
  File "..\..\..\dist\jre\lib\jvm.hprof.txt"
  File "..\..\..\dist\jre\lib\logging.properties"
  SetOutPath "$INSTDIR\jre\lib\management"
  File "..\..\..\dist\jre\lib\management\jmxremote.access"
  File "..\..\..\dist\jre\lib\management\jmxremote.password.template"
  File "..\..\..\dist\jre\lib\management\management.properties"
  File "..\..\..\dist\jre\lib\management\snmp.acl.template"
  SetOutPath "$INSTDIR\jre\lib"
  File "..\..\..\dist\jre\lib\management-agent.jar"
  File "..\..\..\dist\jre\lib\meta-index"
  File "..\..\..\dist\jre\lib\net.properties"
  File "..\..\..\dist\jre\lib\plugin.jar"
  File "..\..\..\dist\jre\lib\psfont.properties.ja"
  File "..\..\..\dist\jre\lib\psfontj2d.properties"
  File "..\..\..\dist\jre\lib\resources.jar"
  File "..\..\..\dist\jre\lib\rt.jar"
  SetOutPath "$INSTDIR\jre\lib\security"
  File "..\..\..\dist\jre\lib\security\blacklist"
  File "..\..\..\dist\jre\lib\security\blacklisted.certs"
  File "..\..\..\dist\jre\lib\security\cacerts"
  File "..\..\..\dist\jre\lib\security\java.policy"
  File "..\..\..\dist\jre\lib\security\java.security"
  File "..\..\..\dist\jre\lib\security\javaws.policy"
  SetOutPath "$INSTDIR\jre\lib\security\policy\limited"
  File "..\..\..\dist\jre\lib\security\policy\limited\local_policy.jar"
  File "..\..\..\dist\jre\lib\security\policy\limited\US_export_policy.jar"
  SetOutPath "$INSTDIR\jre\lib\security\policy\unlimited"
  File "..\..\..\dist\jre\lib\security\policy\unlimited\local_policy.jar"
  File "..\..\..\dist\jre\lib\security\policy\unlimited\US_export_policy.jar"
  SetOutPath "$INSTDIR\jre\lib\security"
  File "..\..\..\dist\jre\lib\security\trusted.libraries"
  SetOutPath "$INSTDIR\jre\lib"
  File "..\..\..\dist\jre\lib\sound.properties"
  File "..\..\..\dist\jre\lib\tzdb.dat"
  File "..\..\..\dist\jre\lib\tzmappings"
  SetOutPath "$INSTDIR\jre"
  File "..\..\..\dist\jre\LICENSE"
  File "..\..\..\dist\jre\README.txt"
  File "..\..\..\dist\jre\release"
  File "..\..\..\dist\jre\THIRDPARTYLICENSEREADME-JAVAFX.txt"
  File "..\..\..\dist\jre\THIRDPARTYLICENSEREADME.txt"
  File "..\..\..\dist\jre\Welcome.html"
  SetOutPath "$INSTDIR\lib"
  File "..\..\..\dist\lib\fontawesomefx-commons-8.15.jar"
  File "..\..\..\dist\lib\fontawesomefx-materialicons-2.2.0-5.jar"
  File "..\..\..\dist\lib\jfoenix-8.0.1.jar"
  File "..\..\..\dist\lib\jPowerShell-2.0.jar"
  File "..\..\..\dist\lib\commons-io-2.6.jar"
  File "..\..\..\dist\lib\commons-exec-1.3.jar"  
SectionEnd

Section -AdditionalIcons
  SetOutPath $INSTDIR
  WriteIniStr "$INSTDIR\${PRODUCT_NAME}.url" "InternetShortcut" "URL" "${PRODUCT_WEB_SITE}"
  CreateShortCut "$SMPROGRAMS\IPSX Desktop Client\Website.lnk" "$INSTDIR\${PRODUCT_NAME}.url"
  CreateShortCut "$SMPROGRAMS\IPSX Desktop Client\Uninstall.lnk" "$INSTDIR\uninst.exe"
SectionEnd

Section -Post
  WriteUninstaller "$INSTDIR\uninst.exe"
  WriteRegStr HKLM "${PRODUCT_DIR_REGKEY}" "" "$INSTDIR\ipsx-desktop-client.exe"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "DisplayName" "$(^Name)"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "UninstallString" "$INSTDIR\uninst.exe"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "DisplayIcon" "$INSTDIR\ipsx-desktop-client.exe"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "DisplayVersion" "${PRODUCT_VERSION}"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "URLInfoAbout" "${PRODUCT_WEB_SITE}"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "Publisher" "${PRODUCT_PUBLISHER}"
SectionEnd


Function un.onUninstSuccess
  HideWindow
  MessageBox MB_ICONINFORMATION|MB_OK "$(^Name) has been successfully removed from your computer."
FunctionEnd

Function un.onInit
  MessageBox MB_ICONQUESTION|MB_YESNO|MB_DEFBUTTON2 "Are you sure you want to completely remove the $(^Name) and all its components?" IDYES +2
  Abort
FunctionEnd

Section Uninstall
  Delete "$INSTDIR\${PRODUCT_NAME}.url"
  Delete "$INSTDIR\uninst.exe" 
  Delete "$INSTDIR\lib\commons-exec-1.3.jar"
  Delete "$INSTDIR\lib\commons-io-2.6.jar"
  Delete "$INSTDIR\lib\jPowerShell-2.0.jar"
  Delete "$INSTDIR\lib\jfoenix-8.0.1.jar"
  Delete "$INSTDIR\lib\fontawesomefx-materialicons-2.2.0-5.jar"
  Delete "$INSTDIR\lib\fontawesomefx-commons-8.15.jar"
  Delete "$INSTDIR\jre\Welcome.html"
  Delete "$INSTDIR\jre\THIRDPARTYLICENSEREADME.txt"
  Delete "$INSTDIR\jre\THIRDPARTYLICENSEREADME-JAVAFX.txt"
  Delete "$INSTDIR\jre\release"
  Delete "$INSTDIR\jre\README.txt"
  Delete "$INSTDIR\jre\LICENSE"
  Delete "$INSTDIR\jre\lib\tzmappings"
  Delete "$INSTDIR\jre\lib\tzdb.dat"
  Delete "$INSTDIR\jre\lib\sound.properties"
  Delete "$INSTDIR\jre\lib\security\trusted.libraries"
  Delete "$INSTDIR\jre\lib\security\policy\unlimited\US_export_policy.jar"
  Delete "$INSTDIR\jre\lib\security\policy\unlimited\local_policy.jar"
  Delete "$INSTDIR\jre\lib\security\policy\limited\US_export_policy.jar"
  Delete "$INSTDIR\jre\lib\security\policy\limited\local_policy.jar"
  Delete "$INSTDIR\jre\lib\security\javaws.policy"
  Delete "$INSTDIR\jre\lib\security\java.security"
  Delete "$INSTDIR\jre\lib\security\java.policy"
  Delete "$INSTDIR\jre\lib\security\cacerts"
  Delete "$INSTDIR\jre\lib\security\blacklisted.certs"
  Delete "$INSTDIR\jre\lib\security\blacklist"
  Delete "$INSTDIR\jre\lib\rt.jar"
  Delete "$INSTDIR\jre\lib\resources.jar"
  Delete "$INSTDIR\jre\lib\psfontj2d.properties"
  Delete "$INSTDIR\jre\lib\psfont.properties.ja"
  Delete "$INSTDIR\jre\lib\plugin.jar"
  Delete "$INSTDIR\jre\lib\net.properties"
  Delete "$INSTDIR\jre\lib\meta-index"
  Delete "$INSTDIR\jre\lib\management-agent.jar"
  Delete "$INSTDIR\jre\lib\management\snmp.acl.template"
  Delete "$INSTDIR\jre\lib\management\management.properties"
  Delete "$INSTDIR\jre\lib\management\jmxremote.password.template"
  Delete "$INSTDIR\jre\lib\management\jmxremote.access"
  Delete "$INSTDIR\jre\lib\logging.properties"
  Delete "$INSTDIR\jre\lib\jvm.hprof.txt"
  Delete "$INSTDIR\jre\lib\jsse.jar"
  Delete "$INSTDIR\jre\lib\jfxswt.jar"
  Delete "$INSTDIR\jre\lib\jfr.jar"
  Delete "$INSTDIR\jre\lib\jfr\profile.jfc"
  Delete "$INSTDIR\jre\lib\jfr\default.jfc"
  Delete "$INSTDIR\jre\lib\jce.jar"
  Delete "$INSTDIR\jre\lib\javaws.jar"
  Delete "$INSTDIR\jre\lib\javafx.properties"
  Delete "$INSTDIR\jre\lib\images\cursors\win32_MoveNoDrop32x32.gif"
  Delete "$INSTDIR\jre\lib\images\cursors\win32_MoveDrop32x32.gif"
  Delete "$INSTDIR\jre\lib\images\cursors\win32_LinkNoDrop32x32.gif"
  Delete "$INSTDIR\jre\lib\images\cursors\win32_LinkDrop32x32.gif"
  Delete "$INSTDIR\jre\lib\images\cursors\win32_CopyNoDrop32x32.gif"
  Delete "$INSTDIR\jre\lib\images\cursors\win32_CopyDrop32x32.gif"
  Delete "$INSTDIR\jre\lib\images\cursors\invalid32x32.gif"
  Delete "$INSTDIR\jre\lib\images\cursors\cursors.properties"
  Delete "$INSTDIR\jre\lib\i386\jvm.cfg"
  Delete "$INSTDIR\jre\lib\hijrah-config-umalqura.properties"
  Delete "$INSTDIR\jre\lib\fonts\LucidaTypewriterRegular.ttf"
  Delete "$INSTDIR\jre\lib\fonts\LucidaTypewriterBold.ttf"
  Delete "$INSTDIR\jre\lib\fonts\LucidaSansRegular.ttf"
  Delete "$INSTDIR\jre\lib\fonts\LucidaSansDemiBold.ttf"
  Delete "$INSTDIR\jre\lib\fonts\LucidaBrightRegular.ttf"
  Delete "$INSTDIR\jre\lib\fonts\LucidaBrightItalic.ttf"
  Delete "$INSTDIR\jre\lib\fonts\LucidaBrightDemiItalic.ttf"
  Delete "$INSTDIR\jre\lib\fonts\LucidaBrightDemiBold.ttf"
  Delete "$INSTDIR\jre\lib\fontconfig.properties.src"
  Delete "$INSTDIR\jre\lib\fontconfig.bfc"
  Delete "$INSTDIR\jre\lib\flavormap.properties"
  Delete "$INSTDIR\jre\lib\ext\zipfs.jar"
  Delete "$INSTDIR\jre\lib\ext\sunpkcs11.jar"
  Delete "$INSTDIR\jre\lib\ext\sunmscapi.jar"
  Delete "$INSTDIR\jre\lib\ext\sunjce_provider.jar"
  Delete "$INSTDIR\jre\lib\ext\sunec.jar"
  Delete "$INSTDIR\jre\lib\ext\nashorn.jar"
  Delete "$INSTDIR\jre\lib\ext\meta-index"
  Delete "$INSTDIR\jre\lib\ext\localedata.jar"
  Delete "$INSTDIR\jre\lib\ext\jfxrt.jar"
  Delete "$INSTDIR\jre\lib\ext\jaccess.jar"
  Delete "$INSTDIR\jre\lib\ext\dnsns.jar"
  Delete "$INSTDIR\jre\lib\ext\cldrdata.jar"
  Delete "$INSTDIR\jre\lib\ext\access-bridge.jar"
  Delete "$INSTDIR\jre\lib\ext\access-bridge-32.jar"
  Delete "$INSTDIR\jre\lib\deploy.jar"
  Delete "$INSTDIR\jre\lib\deploy\splash_11@2x-lic.gif"
  Delete "$INSTDIR\jre\lib\deploy\splash_11-lic.gif"
  Delete "$INSTDIR\jre\lib\deploy\splash@2x.gif"
  Delete "$INSTDIR\jre\lib\deploy\splash.gif"
  Delete "$INSTDIR\jre\lib\deploy\messages_zh_TW.properties"
  Delete "$INSTDIR\jre\lib\deploy\messages_zh_HK.properties"
  Delete "$INSTDIR\jre\lib\deploy\messages_zh_CN.properties"
  Delete "$INSTDIR\jre\lib\deploy\messages_sv.properties"
  Delete "$INSTDIR\jre\lib\deploy\messages_pt_BR.properties"
  Delete "$INSTDIR\jre\lib\deploy\messages_ko.properties"
  Delete "$INSTDIR\jre\lib\deploy\messages_ja.properties"
  Delete "$INSTDIR\jre\lib\deploy\messages_it.properties"
  Delete "$INSTDIR\jre\lib\deploy\messages_fr.properties"
  Delete "$INSTDIR\jre\lib\deploy\messages_es.properties"
  Delete "$INSTDIR\jre\lib\deploy\messages_de.properties"
  Delete "$INSTDIR\jre\lib\deploy\messages.properties"
  Delete "$INSTDIR\jre\lib\deploy\ffjcext.zip"
  Delete "$INSTDIR\jre\lib\currency.data"
  Delete "$INSTDIR\jre\lib\content-types.properties"
  Delete "$INSTDIR\jre\lib\cmm\sRGB.pf"
  Delete "$INSTDIR\jre\lib\cmm\PYCC.pf"
  Delete "$INSTDIR\jre\lib\cmm\LINEAR_RGB.pf"
  Delete "$INSTDIR\jre\lib\cmm\GRAY.pf"
  Delete "$INSTDIR\jre\lib\cmm\CIEXYZ.pf"
  Delete "$INSTDIR\jre\lib\classlist"
  Delete "$INSTDIR\jre\lib\charsets.jar"
  Delete "$INSTDIR\jre\lib\calendars.properties"
  Delete "$INSTDIR\jre\lib\accessibility.properties"
  Delete "$INSTDIR\jre\COPYRIGHT"
  Delete "$INSTDIR\jre\bin\zip.dll"
  Delete "$INSTDIR\jre\bin\wsdetect.dll"
  Delete "$INSTDIR\jre\bin\WindowsAccessBridge.dll"
  Delete "$INSTDIR\jre\bin\WindowsAccessBridge-32.dll"
  Delete "$INSTDIR\jre\bin\w2k_lsa_auth.dll"
  Delete "$INSTDIR\jre\bin\verify.dll"
  Delete "$INSTDIR\jre\bin\vcruntime140.dll"
  Delete "$INSTDIR\jre\bin\unpack200.exe"
  Delete "$INSTDIR\jre\bin\unpack.dll"
  Delete "$INSTDIR\jre\bin\ucrtbase.dll"
  Delete "$INSTDIR\jre\bin\tnameserv.exe"
  Delete "$INSTDIR\jre\bin\t2k.dll"
  Delete "$INSTDIR\jre\bin\sunmscapi.dll"
  Delete "$INSTDIR\jre\bin\sunec.dll"
  Delete "$INSTDIR\jre\bin\ssvagent.exe"
  Delete "$INSTDIR\jre\bin\ssv.dll"
  Delete "$INSTDIR\jre\bin\splashscreen.dll"
  Delete "$INSTDIR\jre\bin\servertool.exe"
  Delete "$INSTDIR\jre\bin\rmiregistry.exe"
  Delete "$INSTDIR\jre\bin\rmid.exe"
  Delete "$INSTDIR\jre\bin\resource.dll"
  Delete "$INSTDIR\jre\bin\prism_sw.dll"
  Delete "$INSTDIR\jre\bin\prism_d3d.dll"
  Delete "$INSTDIR\jre\bin\prism_common.dll"
  Delete "$INSTDIR\jre\bin\policytool.exe"
  Delete "$INSTDIR\jre\bin\plugin2\npjp2.dll"
  Delete "$INSTDIR\jre\bin\plugin2\msvcr100.dll"
  Delete "$INSTDIR\jre\bin\pack200.exe"
  Delete "$INSTDIR\jre\bin\orbd.exe"
  Delete "$INSTDIR\jre\bin\npt.dll"
  Delete "$INSTDIR\jre\bin\nio.dll"
  Delete "$INSTDIR\jre\bin\net.dll"
  Delete "$INSTDIR\jre\bin\msvcr100.dll"
  Delete "$INSTDIR\jre\bin\msvcp140.dll"
  Delete "$INSTDIR\jre\bin\mlib_image.dll"
  Delete "$INSTDIR\jre\bin\management.dll"
  Delete "$INSTDIR\jre\bin\lcms.dll"
  Delete "$INSTDIR\jre\bin\ktab.exe"
  Delete "$INSTDIR\jre\bin\klist.exe"
  Delete "$INSTDIR\jre\bin\kinit.exe"
  Delete "$INSTDIR\jre\bin\keytool.exe"
  Delete "$INSTDIR\jre\bin\kcms.dll"
  Delete "$INSTDIR\jre\bin\jsoundds.dll"
  Delete "$INSTDIR\jre\bin\jsound.dll"
  Delete "$INSTDIR\jre\bin\jsdt.dll"
  Delete "$INSTDIR\jre\bin\jpeg.dll"
  Delete "$INSTDIR\jre\bin\jp2ssv.dll"
  Delete "$INSTDIR\jre\bin\jp2native.dll"
  Delete "$INSTDIR\jre\bin\jp2launcher.exe"
  Delete "$INSTDIR\jre\bin\jp2iexp.dll"
  Delete "$INSTDIR\jre\bin\jli.dll"
  Delete "$INSTDIR\jre\bin\jjs.exe"
  Delete "$INSTDIR\jre\bin\jfxwebkit.dll"
  Delete "$INSTDIR\jre\bin\jfxmedia.dll"
  Delete "$INSTDIR\jre\bin\jfr.dll"
  Delete "$INSTDIR\jre\bin\jdwp.dll"
  Delete "$INSTDIR\jre\bin\JAWTAccessBridge.dll"
  Delete "$INSTDIR\jre\bin\JAWTAccessBridge-32.dll"
  Delete "$INSTDIR\jre\bin\jawt.dll"
  Delete "$INSTDIR\jre\bin\java_crw_demo.dll"
  Delete "$INSTDIR\jre\bin\javaws.exe"
  Delete "$INSTDIR\jre\bin\javaw.exe"
  Delete "$INSTDIR\jre\bin\javafx_iio.dll"
  Delete "$INSTDIR\jre\bin\javafx_font_t2k.dll"
  Delete "$INSTDIR\jre\bin\javafx_font.dll"
  Delete "$INSTDIR\jre\bin\javacpl.exe"
  Delete "$INSTDIR\jre\bin\javacpl.cpl"
  Delete "$INSTDIR\jre\bin\JavaAccessBridge.dll"
  Delete "$INSTDIR\jre\bin\JavaAccessBridge-32.dll"
  Delete "$INSTDIR\jre\bin\java.exe"
  Delete "$INSTDIR\jre\bin\java.dll"
  Delete "$INSTDIR\jre\bin\java-rmi.exe"
  Delete "$INSTDIR\jre\bin\jabswitch.exe"
  Delete "$INSTDIR\jre\bin\jaas_nt.dll"
  Delete "$INSTDIR\jre\bin\j2pkcs11.dll"
  Delete "$INSTDIR\jre\bin\j2pcsc.dll"
  Delete "$INSTDIR\jre\bin\instrument.dll"
  Delete "$INSTDIR\jre\bin\hprof.dll"
  Delete "$INSTDIR\jre\bin\gstreamer-lite.dll"
  Delete "$INSTDIR\jre\bin\glib-lite.dll"
  Delete "$INSTDIR\jre\bin\glass.dll"
  Delete "$INSTDIR\jre\bin\fxplugins.dll"
  Delete "$INSTDIR\jre\bin\fontmanager.dll"
  Delete "$INSTDIR\jre\bin\eula.dll"
  Delete "$INSTDIR\jre\bin\dt_socket.dll"
  Delete "$INSTDIR\jre\bin\dt_shmem.dll"
  Delete "$INSTDIR\jre\bin\dtplugin\npdeployJava1.dll"
  Delete "$INSTDIR\jre\bin\dtplugin\deployJava1.dll"
  Delete "$INSTDIR\jre\bin\deploy.dll"
  Delete "$INSTDIR\jre\bin\decora_sse.dll"
  Delete "$INSTDIR\jre\bin\dcpr.dll"
  Delete "$INSTDIR\jre\bin\concrt140.dll"
  Delete "$INSTDIR\jre\bin\client\Xusage.txt"
  Delete "$INSTDIR\jre\bin\client\jvm.dll"
  Delete "$INSTDIR\jre\bin\bci.dll"
  Delete "$INSTDIR\jre\bin\awt.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-crt-utility-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-crt-time-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-crt-string-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-crt-stdio-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-crt-runtime-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-crt-process-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-crt-private-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-crt-multibyte-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-crt-math-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-crt-locale-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-crt-heap-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-crt-filesystem-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-crt-environment-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-crt-convert-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-crt-conio-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-core-util-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-core-timezone-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-core-sysinfo-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-core-synch-l1-2-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-core-synch-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-core-string-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-core-rtlsupport-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-core-profile-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-core-processthreads-l1-1-1.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-core-processthreads-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-core-processenvironment-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-core-namedpipe-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-core-memory-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-core-localization-l1-2-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-core-libraryloader-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-core-interlocked-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-core-heap-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-core-handle-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-core-file-l2-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-core-file-l1-2-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-core-file-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-core-errorhandling-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-core-debug-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-core-datetime-l1-1-0.dll"
  Delete "$INSTDIR\jre\bin\api-ms-win-core-console-l1-1-0.dll"
  Delete "$INSTDIR\IPSX-Desktop-Client.jar"
  Delete "$INSTDIR\ipsx-desktop-client.ico"
  Delete "$INSTDIR\ipsx-desktop-client.exe"

  Delete "$SMPROGRAMS\IPSX Desktop Client\Uninstall.lnk"
  Delete "$SMPROGRAMS\IPSX Desktop Client\Website.lnk"
  Delete "$DESKTOP\IPSX Desktop Client.lnk"
  Delete "$SMPROGRAMS\IPSX Desktop Client\IPSX Desktop Client.lnk"

  RMDir "$SMPROGRAMS\IPSX Desktop Client"
  RMDir "$INSTDIR\lib"
  RMDir "$INSTDIR\jre\lib\security\policy\unlimited"
  RMDir "$INSTDIR\jre\lib\security\policy\limited"
  RMDir "$INSTDIR\jre\lib\security"
  RMDir "$INSTDIR\jre\lib\management"
  RMDir "$INSTDIR\jre\lib\jfr"
  RMDir "$INSTDIR\jre\lib\images\cursors"
  RMDir "$INSTDIR\jre\lib\i386"
  RMDir "$INSTDIR\jre\lib\fonts"
  RMDir "$INSTDIR\jre\lib\ext"
  RMDir "$INSTDIR\jre\lib\deploy"
  RMDir "$INSTDIR\jre\lib\cmm"
  RMDir "$INSTDIR\jre\lib"
  RMDir "$INSTDIR\jre\bin\plugin2"
  RMDir "$INSTDIR\jre\bin\dtplugin"
  RMDir "$INSTDIR\jre\bin\client"
  RMDir "$INSTDIR\jre\bin"
  RMDir "$INSTDIR\jre"
  RMDir "$INSTDIR"

  DeleteRegKey ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}"
  DeleteRegKey HKLM "${PRODUCT_DIR_REGKEY}"
  SetAutoClose true
SectionEnd