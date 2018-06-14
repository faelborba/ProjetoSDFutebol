package cacheServer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class Memcached {

	private XMemcachedClientBuilder builder;
	private MemcachedClient client;

	public Memcached(String memcachedAddress) {
		builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(memcachedAddress));
		try {
			client = builder.build();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setCacheData(String key, String value) {
		try {
			client.set(key, 0, value);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			e.printStackTrace();
		}
	}
	
	public String getCacheData(String key) {
		String value = null;
		try {
			value = client.get(key);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			e.printStackTrace();
		}
		return value;
	}
}