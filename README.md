# Anonymous Apex Executer
This is Java based tool created using Tooling API to execute Anonymous code repeatedly after defined intervals

# How to run this application
This application can be executed in 2 ways :

1. GUI
2. Command-line 

## Run using GUI
To run this application using GUI, navigate to [dist](https://github.com/JitendraZaa/Anonymous-Apex-Executer/tree/master/Anonymous%20Apex%20Executer/dist) folder and double click on [Apex_Executer.jar](https://github.com/JitendraZaa/Anonymous-Apex-Executer/blob/master/Anonymous%20Apex%20Executer/dist/Apex_Executer.jar), below form will be opened

![Application Screen](https://github.com/JitendraZaa/Anonymous-Apex-Executer/blob/master/Screens/Application%20Screen.PNG "Landing Screen")

On this screen, we need to provide Username and Password of Salesforce organization. If its Sandbox then checkbox "IsSandbox" needs to be checked. If you are using proxy settings then that information also needs to be provided.

### Other parameters
**Loop** In this textbox we need to indicate that for how many times this script needs to be executed ?
**Pause** It may not be used frequently however if someone wants to wait for sometime before sending next request to execute code, that duration can be entered here in milliseconds

### Features
**Log Window**

Once user clicks on "Execute" button, below log window will appear with realtime status of request

![Log Window](https://github.com/JitendraZaa/Anonymous-Apex-Executer/blob/master/Screens/Log%20Window.PNG "Log Window")



**Log Files**

It also creates log file in background to see detailed informations like compiler errors

**Save and Browse**

You can sabve your apex code on local system and then reuse it.

## Running in Command-line mode

You may required to schedule your code to run after some interval. It can be done by creating shell script of batch file and use Operating system to schedule it.

###commandLine.config
You need to create "commandLine.config" file which will contain all settings required to run program.

**Example of commandLine.config**
```xml
<apexconfig>
	<username>salesforce username</username>
	<password>salesforce password+Security token</password>
	<isSandbox>false</isSandbox>
	<proxyAddress>ip address of proxy server if applicable</proxyAddress>
	<proxyPort>port of Proxy server</proxyPort>
	<proxyUserName>proxy server username</proxyUserName>
	<proxyPassword>proxy server password</proxyPassword>
	<apexFile>location of source code to be executed</apexFile>
	<repeat>total numbers of time to repeat</repeat>
	<pause>interval between execution in milliseconds if needed</pause>
</apexconfig>
```

**How to run from commandline**

To run from command-line, execute below statement in shell or command window
```shell
java -cp Apex_Executer.jar  com.jitendrazaa.ToolingAPI.CommandLine
```

**help command**

To get information about sample xml file needed, run below command
```shell
java -cp Apex_Executer.jar  com.jitendrazaa.ToolingAPI.CommandLine -help
```

# Video Demo

<a href="http://www.youtube.com/watch?feature=player_embedded&v=Ar47DJ3v9wQ" target="_blank"><img src="http://img.youtube.com/vi/Ar47DJ3v9wQ/0.jpg" 
alt="Tooling API based Java tool to execute Anonymous Code" width="420" height="315" border="10" /></a>
