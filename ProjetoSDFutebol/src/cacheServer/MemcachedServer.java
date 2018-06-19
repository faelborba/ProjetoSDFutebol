package cacheServer;

import java.util.ArrayList;

public class MemcachedServer {
	private String name = "";
	private String location = "";
	private ArrayList<Integer> year = null ;
	private boolean active = false;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public ArrayList<Integer> getYear() {
		return year;
	}

	public void setYear(ArrayList<Integer> year) {
		this.year = year;
	}

	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}			
}
