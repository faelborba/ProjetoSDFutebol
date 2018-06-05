package servidorHttp;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;


public class SimpleHttpServer {

	public static void main(String[] args) throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/getAvailabeYears", new RequestHandler());
		server.createContext("/getData", new RequestHandler());
		server.setExecutor(null);
		server.start();
	}
}
