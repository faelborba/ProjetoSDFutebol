package servidorHttp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ConexaoMysql.Response;

public class Erro {
	public int errorCode = 0;
	public String errorDescription = null;

	public Erro(int errorCode) {
		this.errorCode = errorCode;
		switch (errorCode) {
		case 1:
			errorDescription = "Servidor indisponivel";
			break;
		case 2:
			errorDescription = "Dados inexixtentes";
			break;

		case 3:
			errorDescription = "Invalid Request";
			break;

		default:
			errorDescription = "Erro geral";
			break;
		}
	}
	
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	@Override
	public String toString() {
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		Gson gson = builder.create();
		return gson.toJson(this);
	}
	
	public Erro toObjeto(String erroString) {
		Gson gson = new Gson();
		Erro erro = gson.fromJson(erroString, Erro.class);
		return erro;
	}
}
