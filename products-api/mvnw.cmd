\
    @ECHO OFF
    SETLOCAL
    set WRAPPER_JAR=.mvn\wrapper\maven-wrapper.jar
    set LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain
    IF NOT EXIST "%WRAPPER_JAR%" (
      powershell -Command "try { (New-Object System.Net.WebClient).DownloadFile('https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar','%WRAPPER_JAR%') } catch { exit 1 }"
      IF ERRORLEVEL 1 (
        ECHO Error downloading maven-wrapper.jar
        EXIT /B 1
      )
    )
    "%JAVA_HOME%\bin\java" -cp "%WRAPPER_JAR%" %LAUNCHER% %*
    ENDLOCAL
