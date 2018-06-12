package ConexaoMysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TesteConexao {
	public static void main(String[] args) {
		System.out.println(ConexaoMySQL.statusConection());
		ConexaoMySQL.getConexaoMySQL();
		System.out.println((ConexaoMySQL.statusConection()));

		
		Queries q = new Queries();
		//q.setClubNameAndPlayerNameQuery("2015", "Barcelona", "Neymar");
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			String result = "";
			stmt = ConexaoMySQL.getConexaoMySQL().createStatement();
			//rs = stmt.executeQuery(q.getClubNameAndPlayerNameQuery());
			while(rs.next()) {
				result += rs.getString("resultado") + "\n";
			}
			System.out.println(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
