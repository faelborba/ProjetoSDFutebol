package cacheServer;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MemcachedServerList {
	
	private ArrayList<MemcachedServer> servers = null;
	
	public MemcachedServerList() {
		this.servers = new ArrayList<MemcachedServer>();
	}

	public ArrayList<MemcachedServer> getServers() {
		return servers;
	}

	public void setServers(ArrayList<MemcachedServer> servers) {
		this.servers = servers;
	}
	
	public void addServer(MemcachedServer server) {
		this.servers.add(server);		
	}
	
	public boolean updateServer(MemcachedServer server) {
		for (MemcachedServer memcachedServer : this.servers) {
			if (memcachedServer.getName() == server.getName()) {
				memcachedServer = server;
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		Gson gson = builder.create();
		return gson.toJson(this);
	}
	
	public MemcachedServerList toObjeto(String listaString) {
		Gson gson = new Gson();
		MemcachedServerList lista = gson.fromJson(listaString, MemcachedServerList.class);
		return lista;
	}
}
