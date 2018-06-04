package Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Servidor {
	public static void main(String[] args) {
		try {
			System.out.println("Server: Iniciando...");
			ServerSocket server = new ServerSocket(1235);
			System.out.println("Server: online...");
			while (true) {
				System.out.println("Server: Aguardando Conexão...");
				Socket cliente = server.accept();
				System.out.println("Server: Conectado com cliente!");
				TrataProtocolo protocolo = new TrataProtocolo(cliente);
				protocolo.start();
			}
		} catch (UnknownHostException ex) {
			System.out.println("GerenciadorServer: Host desconhecido");
		} catch (IOException ex) {
			System.out.println("GerenciadorServer: Erro na conexao: " + ex.getMessage());
		}
	}
}
