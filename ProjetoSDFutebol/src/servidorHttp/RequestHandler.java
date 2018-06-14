package servidorHttp;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import ConexaoMysql.Queries;
import cacheServer.Memcached;

public class RequestHandler implements HttpHandler {

	private Memcached cache = new Memcached("localhost:11211");
	private String cacheKeyClubAndPlayer;
	private String cacheKeyClub;
	private String cacheKeyPlayer;
	private Queries q = new Queries();
	private int ano = 0;
	private String playerName = "", clubName = "";
	private int responseCode = 200;

	@Override
	public void handle(HttpExchange exg) throws IOException {
		String requestURI = exg.getRequestURI().toString();
		String requestQuery = exg.getRequestURI().getQuery();
		byte[] response = null;

		// aqui se tiver um 3 parametro no request (a data no caso) ele ja assigna os
		// valores
		if (exg.getRequestURI().getPath().split("/").length > 2) {
			String data = exg.getRequestURI().getPath().split("/")[2];
			this.ano = Integer.parseInt(data);
		}

		if (requestQuery != null) {
			// Ja estou assignando valor para o player name e club name aqui, se nao tiver
			// no request, nao assigna valor
			playerName = this.queryToMap(exg.getRequestURI().getQuery()).get("playerName");
			clubName = this.queryToMap(exg.getRequestURI().getQuery()).get("clubName");
		}
		this.cacheKeyClubAndPlayer = "SD_Data_" + ano + "_" + clubName + "_" + playerName;
		this.cacheKeyClub = "SD_Data_" + ano + "_" + clubName;
		this.cacheKeyPlayer = "SD_Data_" + ano + "_" + playerName;
		// esse if encadiado grandao, valida todos os possiveis requests, se for
		// invalido cai no else e o http code vira 417
		// por enquanto no response adicionei a descricao do que devera ser buscado no
		// banco, futuramente tratamos e retornamos o JSON aqui
		if (requestURI.matches("\\/getAvailabeYears$")) {
			response = "Retornar Avaliable Years".getBytes();
		} else if (requestURI.matches("\\/getData\\/[0-9]{4}\\?playerName=[A-z]+\\+?[A-z]+$")) {
			response = this.getResponse(this.cacheKeyPlayer).getBytes();
		} else if (requestURI.matches("\\/getData\\/[0-9]{4}\\?clubName=[A-z]+\\+?[A-z]+$")) {
			response = this.getResponse(this.cacheKeyClub).getBytes();
		} else if (requestURI
				.matches("\\/getData\\/[0-9]{4}\\?clubName=[A-z]+\\+?[A-z]+&playerName=[A-z]+\\+?[A-z]+$")) {
			response = this.getResponse(this.cacheKeyClubAndPlayer).getBytes();
		} else {
			responseCode = 417;
			response = new Erro(3).toString().getBytes();
		}

		exg.sendResponseHeaders(responseCode, response.length);
		OutputStream os = exg.getResponseBody();
		os.write(response);
		os.close();
	}

	/**
	 * returns the url parameters in a map
	 * 
	 * @param query
	 * @return map
	 */
	public Map<String, String> queryToMap(String query) {
		Map<String, String> result = new HashMap<String, String>();
		for (String param : query.split("&")) {
			String pair[] = param.split("=");
			if (pair.length > 1) {
				result.put(pair[0], pair[1]);
			} else {
				result.put(pair[0], "");
			}
		}
		return result;
	}

	private String getResponse(String cacheKey) {
		String response = "";
		this.responseCode = 200;
		String cacheData = cache.getCacheData(cacheKey);
		System.out.println(cacheData);
		if (cacheData == null) {
			System.out.println("Nao ta no cache");
			if (cacheKey == this.cacheKeyClubAndPlayer) {
				cacheData = q.getClubNameAndPlayerNameQueryResult(ano + "", clubName, playerName);
			} else if (cacheKey == this.cacheKeyClub) {
				cacheData = q.getClubNameQueryResult(ano + "", clubName);
			} else if (cacheKey == this.cacheKeyPlayer) {
				cacheData = q.getPlayerNameQueryResult(ano + "", playerName);
			}

			if (cacheData == "") {
				System.out.println("Dado inexistente nao adicionado no cache");
				this.responseCode = 417;
				response = new Erro(2).toString();
			} else {
				System.out.println("Adicionando no cache");
				cache.setCacheData(cacheKey, cacheData);
				response = cacheData;
			}
		} else {
			System.out.println("Ja tava no cache");
			response = cacheData;
		}
		return response;
	}

}
