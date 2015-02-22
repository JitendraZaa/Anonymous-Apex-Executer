/**
 * @Website http://JitendraZaa.com
 * @GitHub https://github.com/JitendraZaa
 * @Date 02-22-2015
 *
 */
package com.jitendrazaa.ToolingAPI;

/**
 *
 * @author Jitendra
 */
public class CommandLineConfigPOJO {
    private String username;
    private String password;
    private boolean isSandbox ;
    private String proxyAddress;
    private int proxyPort ;
    private String proxyUserName;
    private String proxyPassword;
    private String apexFile;
    private int repeat;
    private int pause;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsSandbox() {
        return isSandbox;
    }

    public void setIsSandbox(boolean isSandbox) {
        this.isSandbox = isSandbox;
    }

    public String getProxyAddress() {
        return proxyAddress;
    }

    public void setProxyAddress(String proxyAddress) {
        this.proxyAddress = proxyAddress;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyUserName() {
        return proxyUserName;
    }

    public void setProxyUserName(String proxyUserName) {
        this.proxyUserName = proxyUserName;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    public String getApexFile() {
        return apexFile;
    }

    public void setApexFile(String apexFile) {
        this.apexFile = apexFile;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public int getPause() {
        return pause;
    }

    public void setPause(int pause) {
        this.pause = pause;
    }
    

    @Override
    public String toString() {
        return "CommandLineConfigPOJO{" + "username=" + username + ", isSandbox=" + isSandbox + ", proxyAddress=" + proxyAddress + ", proxyPort=" + proxyPort + ", proxyUserName=" + proxyUserName + ", proxyPassword=" + proxyPassword + ", apexFile=" + apexFile + '}';
    } 
}
