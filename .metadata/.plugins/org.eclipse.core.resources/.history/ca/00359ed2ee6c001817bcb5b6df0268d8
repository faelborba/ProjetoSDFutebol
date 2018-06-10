package Servidor;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.sql.Statement;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Conexoes.ConexaoSQLite;


public class TrataProtocolo extends Thread implements Serializable {
	// variáveis do server
	private Socket socket;
	private Scanner entrada;
	private PrintWriter saida;

	// preparando entrada e saída do server
	public TrataProtocolo(Socket socket) throws IOException {
		this.socket = socket;
		this.entrada = new Scanner(this.socket.getInputStream());
		this.saida = new PrintWriter(this.socket.getOutputStream());
	}

	@Override
	public void run() {
		String protocolo = null;
		ConexaoSQLite conexaoSQLite = new ConexaoSQLite();
		ResultSet resultSet = null;
		Statement statement = null;
		
		protocolo = entrada.nextLine();
		System.out.println("Protocolo recebido: " + protocolo);
		
		conexaoSQLite.conectar();
		System.out.println("Conectou ao banco SQLite.");
		
		String query = "SELECT strftime('%Y', date) as year\n" + 
				"FROM match\n" + 
				"where strftime('%Y', date) >= '2009' and strftime('%Y', date) <= '2011'" + 
				"order by date;";
		statement = conexaoSQLite.criarStatement();
		try {
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				System.out.println("Ano: ");
				System.out.println(resultSet.getInt("year"));
				System.out.println(".... ");
				//resultSet.getInt("id");
			}
		}catch(SQLException e) {
			System.out.println("Erro de database");
		} finally {
			try {
				resultSet.close();
				statement.close();
				conexaoSQLite.desconectar();
			}catch(SQLException e) {
				System.out.println("Erro de fechamento de database");
			}
		}
		
		this.entrada.close();
		this.saida.close();
	}
}