package ConexaoMysql;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cacheServer.MemcachedServerList;


public class Response {
	private int wins;
	private int losses;
	
	public Response(int wins, int losses) {
		super();
		this.wins = wins;
		this.losses = losses;
	}
	
	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}
	
	public int getLosses() {
		return losses;
	}
	
	public void setLosses(int losses) {
		this.losses = losses;
	}
	
	@Override
	public String toString() {
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		Gson gson = builder.create();
		return gson.toJson(this);
	}
	
	public Response toObjeto(String responseString) {
		Gson gson = new Gson();
		Response response = gson.fromJson(responseString, Response.class);
		return response;
	}
}
