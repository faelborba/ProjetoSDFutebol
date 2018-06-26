package servidorHttp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import ConexaoMysql.Queries;
import ConexaoMysql.Response;
import cacheServer.Memcached;
import cacheServer.MemcachedServer;
import cacheServer.MemcachedServerList;

public class RequestHandler implements HttpHandler {

	private Memcached cache;
	private String cacheKeyClubAndPlayer;
	private String cacheKeyClub;
	private String cacheKeyPlayer;
	private String cacheKeyListServers;
	private Queries q = new Queries();
	private int ano = 0;
	private String playerName = "", clubName = "";
	private int responseCode = 200;
	private ConfigClass config = null;

	private String requestQuery = "";
	// private boolean isAtualizarListaServers = false;

	public RequestHandler(ConfigClass config) {
		this.config = config;
		this.cache = new Memcached(this.config.getMemcachedServer() + ":" + this.config.getMemcachedPort());
	}

	@Override
	public void handle(HttpExchange exg) throws IOException {
		String requestURI = exg.getRequestURI().toString();
		String requestQuery = exg.getRequestURI().getQuery();
		byte[] response = null;
		
		/*System.out.println("Testando URI SEM URLdecoder: " + requestURI);*/		
//		requestURI = URLDecoder.decode(requestURI, "UTF-8").replace(" ", "+");		
		/*System.out.println("Testando URI COM URLdecoder: " + requestURI);*/
		
		/*System.out.println("Testando Query SEM URLdecoder: " + requestQuery);*/		
		requestQuery = URLDecoder.decode(requestQuery, "UTF-8").replace(" ", "+");		
		/*System.out.println("Testando Query COM URLdecoder: " + requestQuery);*/

		this.requestQuery = requestQuery;

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
		this.cacheKeyListServers = "SD_ListServers";

		// pegando a lista de servidores no cache
		String cacheData = cache.getCacheData(this.cacheKeyListServers);
		String saveListResponse = this.saveListServersCacheJson(cacheData);

		if (cacheData == null || cacheData == "") { // adicionar o servidor na lista de servidores do cache
			System.out.println("Lista de servidores não disponível cache, adicionando...");
			System.out.println(saveListResponse);
			cache.setCacheData(this.cacheKeyListServers, saveListResponse);
		} else {
			System.out.println("Lista de servidores disponível cache - atualizando..."); // atualizar o servidor na
																							// lista de servidores do
																							// cache
			cache.setCacheData(this.cacheKeyListServers, saveListResponse);
			System.out.println(saveListResponse);
		}

		// esse if encadiado grandao, valida todos os possiveis requests, se for
		// invalido cai no else e o http code vira 417
		// por enquanto no response adicionei a descricao do que devera ser buscado no
		// banco, futuramente tratamos e retornamos o JSON aqui
		if (requestURI.matches("\\/getAvailabeYears$")) {
			response = this.config.yearsToString().getBytes();

		} else if (requestURI.matches("\\/getData\\/[0-9]{4}\\?playerName=[[A-záàâãéèêíïóôõöúüçñÁÀÂÃÉÈÊÍÏÓÔÕÖÚÜÇÑ[\\-]]+\\+?[A-záàâãéèêíïóôõöúüçñÁÀÂÃÉÈÊÍÏÓÔÕÖÚÜÇÑ]]+$")) {
			response = this.getResponse(this.cacheKeyPlayer).getBytes();

		} else if (requestURI.matches("\\/getData\\/[0-9]{4}\\?clubName=[[A-záàâãéèêíïóôõöúüçñÁÀÂÃÉÈÊÍÏÓÔÕÖÚÜÇÑ[\\-]]+\\+?[A-záàâãéèêíïóôõöúüçñÁÀÂÃÉÈÊÍÏÓÔÕÖÚÜÇÑ]]+$")) {
			response = this.getResponse(this.cacheKeyClub).getBytes();

		} else if (requestURI.matches(
				"\\/getData\\/[0-9]{4}\\?clubName=[[A-záàâãéèêíïóôõöúüçñÁÀÂÃÉÈÊÍÏÓÔÕÖÚÜÇÑ[\\-]]+\\+?[A-záàâãéèêíïóôõöúüçñÁÀÂÃÉÈÊÍÏÓÔÕÖÚÜÇÑ]]+&playerName=[[A-záàâãéèêíïóôõöúüçñÁÀÂÃÉÈÊÍÏÓÔÕÖÚÜÇÑ[\\-]]+\\+?[A-záàâãéèêíïóôõöúüçñÁÀÂÃÉÈÊÍÏÓÔÕÖÚÜÇÑ]]+$")) {
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

			if (this.verificaIsServerContemAno(ano)) { // verifica se servidor e responsavel pelo ano da consulta

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
				System.out.println("A requisição solicitada do ano " + ano
						+ " não se encontra neste servidor, estamos buscando o server...");

				cacheData = cache.getCacheData(this.cacheKeyListServers);
				MemcachedServer serverAno = this.verificaServerAno(ano, cacheData);
				if (serverAno != null && serverAno.isActive()) {
					System.out.println("Servidor encontrado. Buscando...");

					System.out.println("\nO servidor encontrado: ");
					System.out.printf("\nName: %s", serverAno.getName());
					System.out.printf("\nLocation: %s", serverAno.getLocation());
					System.out.printf("\nYear's: %s", serverAno.getYear());

					// serverAno.getLocation() // fazer o request neste servidor
					String responseReqOtherServer = this.sendGetOtherServer(serverAno.getLocation(), this.ano,
							this.requestQuery);

					if (responseReqOtherServer.equals("-1")) { // se ocorreu qualquer execption retorna como erro
						System.out.println(
								"Ocorreu uma falha ao realizar a requisição para o outro servidor. Servidor indisponível...");

						// se ocorreu falha ao buscar naquele servidor, ele eh desativado na lista de
						// servers-
						String saveListResponse = this.disableServerListServersCacheJson(cacheData,
								serverAno.getName());
						cache.setCacheData(this.cacheKeyListServers, saveListResponse);

						this.responseCode = 417;
						response = new Erro(1).toString();
					} else if (responseReqOtherServer.equals("417")) {
						this.responseCode = 417;
						response = new Erro(2).toString();
					} else {
						if (responseReqOtherServer.contains("wins") || responseReqOtherServer.contains("losses")) {
							// converter o resultado como o padrão
							Response res = new Response(0, 0);
							res = res.toObjeto(responseReqOtherServer);
							return res.toString();

						} else if (responseReqOtherServer.contains("errorCode")) {
							// converter o resultado para o erro
							Erro erro = new Erro(2);
							erro = erro.toObjeto(responseReqOtherServer);
							return erro.toString();

						} else {
							this.responseCode = 417;
							response = new Erro(3).toString();
						}
					}

				} else {
					System.out.println("Nenhum servidor encontrado para este ano. Não foi encontrado o resultado...");
					this.responseCode = 417;
					response = new Erro(1).toString();
				}
			}
		} else {
			System.out.println("Ja tava no cache");
			response = cacheData;
		}
		return response;
	}

	private String saveListServersCacheJson(String listaServersCache) {
		if (listaServersCache == null) { // adicionar
			MemcachedServerList lista = new MemcachedServerList();

			MemcachedServer thisServer = new MemcachedServer();
			thisServer.setName(this.config.getServerName());
			thisServer.setLocation(this.config.getServerIP() + ":" + this.config.getPortListen());
			thisServer.setYear(this.config.getYearData());
			thisServer.setActive(true);

			lista.addServer(thisServer);

			return lista.toString();

		} else { // alterar
			MemcachedServerList lista = new MemcachedServerList();
			lista = lista.toObjeto(listaServersCache);

			boolean isServerList = false;

			if (lista.getServers().size() > 0) {
				for (MemcachedServer itemMemcached : lista.getServers()) {
					if (itemMemcached.getName().equals(this.config.getServerName())) {
						isServerList = true;
					}
				}
			}

			MemcachedServer thisServer = new MemcachedServer();
			thisServer.setName(this.config.getServerName());
			thisServer.setLocation(this.config.getServerIP() + ":" + this.config.getPortListen());
			thisServer.setYear(this.config.getYearData());
			thisServer.setActive(true);

			if (isServerList) {
				lista.updateServer(thisServer);
			} else {
				lista.addServer(thisServer);
			}

			return lista.toString();
		}
	}

	private String disableServerListServersCacheJson(String listaServersCache, String serverName) {
		MemcachedServerList lista = new MemcachedServerList();
		lista = lista.toObjeto(listaServersCache);

		if (lista.getServers().size() > 0) {
			for (MemcachedServer itemMemcached : lista.getServers()) {
				if (itemMemcached.getName().equals(serverName)) {
					itemMemcached.setActive(false);
				}
			}
		}

		return lista.toString();
	}

	private boolean verificaIsServerContemAno(int ano) {
		if (this.config.getYearData().size() > 0) {
			for (int itemAno : this.config.getYearData()) {
				if (itemAno == ano) {
					return true;
				}
			}
		}
		return false;
	}

	private MemcachedServer verificaServerAno(int ano, String listaServersCache) {
		MemcachedServerList lista = new MemcachedServerList();
		lista = lista.toObjeto(listaServersCache);

		if (lista.getServers().size() > 0) {
			for (MemcachedServer itemMemcached : lista.getServers()) {
				if (itemMemcached.getYear().size() > 0) { // testar se ativo?
					for (int itemAno : itemMemcached.getYear()) {
						if (itemAno == ano) {
							return (MemcachedServer) itemMemcached;
						}
					}
				}
			}
		}
		return (MemcachedServer) null;
	}

	public void buscarAtualizarListaServersMemcached() {
		this.cacheKeyListServers = "SD_ListServers";

		// pegando a lista de servidores no cache
		String cacheData = cache.getCacheData(this.cacheKeyListServers);
		String saveListResponse = this.saveListServersCacheJson(cacheData);

		if (cacheData == null || cacheData == "") { // adicionar o servidor na lista de servidores do cache
			System.out.println("Lista de servidores não disponível cache, adicionando...");
			System.out.println(saveListResponse);
			cache.setCacheData(this.cacheKeyListServers, saveListResponse);
		} else {
			System.out.println("Lista de servidores disponível cache - atualizando..."); // atualizar o servidor na
																							// lista de servidores do
																							// cache
			cache.setCacheData(this.cacheKeyListServers, saveListResponse);
			System.out.println(saveListResponse);
		}
	}

	private String sendGetOtherServer(String serverLocation, int ano, String requestQuery) { // HTTP GET request outros
																								// servidores
		String retResponse = "";

		try {
			String uri = String.format("http://%s/getData/%d?%s", serverLocation, ano, requestQuery); // ex:
																										// http://192.168.0.135/getData/2011?playerName=Lionel+Messi
			System.out.println("sendGet uri => " + uri);

			URL obj = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET"); // optional default is GET
			con.setConnectTimeout(7 * 1000); // 7 segundos timeout

			int responseCode = con.getResponseCode();

			if (responseCode == 417) {
				con.disconnect();
				retResponse = "417";
				return retResponse;
			}
			System.out.println("\nSending 'GET' request to URL : " + uri);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			con.disconnect();
			retResponse = response.toString();

			// print result
			System.out.println(response.toString());

		} catch (Exception e) {
			System.out.println("Ocorreu uma falha ao tentar se conectar com o servidor... Erro: " + e.getMessage());
			retResponse = "-1";
		}

		return retResponse;
	}
}
