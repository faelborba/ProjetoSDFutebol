package servidorHttp;

import java.util.ArrayList;

public class ConfigClass {
	private String serverName = null;
	private String serverIP = null;
	private int portListen = 0;
	private String memcachedServer = null;
	private int memcachedPort = 0;
	private ArrayList<Integer> yearData = null;

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public int getPortListen() {
		return portListen;
	}

	public void setPortListen(int portListen) {
		this.portListen = portListen;
	}

	public String getMemcachedServer() {
		return memcachedServer;
	}

	public void setMemcachedServer(String memcachedServer) {
		this.memcachedServer = memcachedServer;
	}

	public int getMemcachedPort() {
		return memcachedPort;
	}

	public void setMemcachedPort(int memcachedPort) {
		this.memcachedPort = memcachedPort;
	}

	public ArrayList<Integer> getYearData() {
		return yearData;
	}

	public void setYearData(ArrayList<Integer> yearData) {
		this.yearData = yearData;
	}

}