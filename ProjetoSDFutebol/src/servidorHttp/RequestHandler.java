package servidorHttp;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class RequestHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exg) throws IOException {
		String requestURI = exg.getRequestURI().toString();
		String requestQuery = exg.getRequestURI().getQuery();
		byte [] response = null;
		int responseCode = 200;
		int ano = 0, mes = 0, dia = 0;
		String playerName = "", clubName = "";
		//aqui se tiver um 3 parametro no request (a data no caso) ele ja assigna os valores
		if(exg.getRequestURI().getPath().split("/").length > 2) {
			String data = exg.getRequestURI().getPath().split("/")[2];
			if(data.length() == 4) {
				ano = Integer.parseInt(data);
			} else if (data.length() == 6) {
				ano = Integer.parseInt(data.substring(0, 4));
				mes = Integer.parseInt(data.substring(4, 6));
			} else if (data.length() == 8) {
				ano = Integer.parseInt(data.substring(0, 4));
				mes = Integer.parseInt(data.substring(4, 6));
				dia = Integer.parseInt(data.substring(6, 8));
			}
		}
		
		if(requestQuery != null) {
			//Ja estou assignando valor para o player name e club name aqui, se nao tiver no request, nao assigna valor
			playerName = this.queryToMap(exg.getRequestURI().getQuery()).get("playerName");
			clubName = this.queryToMap(exg.getRequestURI().getQuery()).get("clubName");
		}		
		//esse if encadiado grandao, valida todos os possiveis requests, se for invalido cai no else e o http code vira 417
		//por enquanto no response adicionei a descricao do que devera ser buscado no banco, futuramente tratamos e retornamos o JSON aqui
		if(requestURI.matches("\\/getAvailabeYears$")) {
			response = "Retornar Avaliable Years".getBytes();
		} else if(requestURI.matches("\\/getData\\/[0-9]{4}\\?playerName=[A-z]+\\+?[A-z]+$")) {
			response = ("Wins e Losses do jogador " + playerName + " na data " + ano).getBytes();
		} else if(requestURI.matches("\\/getData\\/[0-9]{4}(0[1-9]|1[0-2])\\?playerName=[A-z]+\\+?[A-z]+$")) {
			response = ("Wins e Losses do jogador " + playerName + " na data " + ano + mes).getBytes();
		} else if(requestURI.matches("\\/getData\\/[0-9]{4}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])\\?playerName=[A-z]+\\+?[A-z]+$")) {
			//O Regex desse if só nao controla se colocar 30 ou 31 de fevereiro os resto das datas ta ok
			response = ("Wins e Losses do jogador " + playerName + " na data " + ano + mes + dia).getBytes();
		} else if(requestURI.matches("\\/getData\\/[0-9]{4}\\?clubName=[A-z]+\\+?[A-z]+$")) {
			response = ("Wins e Losses do clube " + clubName + " na data " + ano).getBytes();
		} else if(requestURI.matches("\\/getData\\/[0-9]{4}(0[1-9]|1[0-2])\\?clubName=[A-z]+\\+?[A-z]+$")) {
			response = ("Wins e Losses do clube " + clubName + " na data " + ano + mes).getBytes();
		} else if(requestURI.matches("\\/getData\\/[0-9]{4}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])\\?clubName=[A-z]+\\+?[A-z]+$")) {
			//O Regex desse if só nao controla se colocar 30 ou 31 de fevereiro os resto das datas ta ok
			response = ("Wins e Losses do clube " + clubName + " na data " + ano + mes + dia).getBytes();
		} else if(requestURI.matches("\\/getData\\/[0-9]{4}\\?clubName=[A-z]+\\+?[A-z]+&playerName=[A-z]+\\+?[A-z]+$")) {
			response = ("Wins e Losses do jogador " + playerName + " pelo clube " + clubName + " na data " + ano).getBytes();
		} else if(requestURI.matches("\\/getData\\/[0-9]{4}(0[1-9]|1[0-2])\\?clubName=[A-z]+\\+?[A-z]+&playerName=[A-z]+\\+?[A-z]+$")) {
			response = ("Wins e Losses do jogador " + playerName + " pelo clube " + clubName + " na data " + ano + mes).getBytes();
		} else if(requestURI.matches("\\/getData\\/[0-9]{4}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])\\?clubName=[A-z]+\\+?[A-z]+&playerName=[A-z]+\\+?[A-z]+$")) {
			//O Regex desse if só nao controla se colocar 30 ou 31 de fevereiro os resto das datas ta ok
			response = ("Wins e Losses do jogador " + playerName + " pelo clube " + clubName + " na data " + ano + mes + dia).getBytes();
		} else {
			responseCode = 417;
			response = "Error 3 Invalid Request".getBytes();
		}
		
	      exg.sendResponseHeaders(responseCode, response.length);
	      OutputStream os = exg.getResponseBody();
	      os.write(response);
	      os.close();
	}
	
	/**
	   * returns the url parameters in a map
	   * @param query
	   * @return map
	   */
	  public Map<String, String> queryToMap(String query){
	    Map<String, String> result = new HashMap<String, String>();
	    for (String param : query.split("&")) {
	        String pair[] = param.split("=");
	        if (pair.length>1) {
	            result.put(pair[0], pair[1]);
	        }else{
	            result.put(pair[0], "");
	        }
	    }
	    return result;
	  }

}
