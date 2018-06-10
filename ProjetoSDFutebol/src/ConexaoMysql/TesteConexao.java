package ConexaoMysql;

public class TesteConexao {
	public static void main(String[] args) {
		System.out.println(ConexaoMySQL.statusConection());
		ConexaoMySQL.getConexaoMySQL();
		System.out.println((ConexaoMySQL.statusConection()));
	}
}
