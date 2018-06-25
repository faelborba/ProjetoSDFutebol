package ConexaoMysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

public class Queries {
	private String getAvailableYearsQuery;
	private String playerNameQuery;
	private String clubNameQuery;
	private String clubNameAndPlayerNameQuery;

	private String getPlayerNameQuery() {
		return playerNameQuery;
	}

	private void setPlayerNameQuery(String year, String playerName) {
		this.playerNameQuery = "SELECT (`Match`.`home_team_goal` - `Match`.`away_team_goal`) as resultado\n" + 
				"FROM `Player`\n" + 
				"INNER JOIN `Match`\n" + 
				"    on `Match`.`home_player_1` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`home_player_2` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`home_player_3` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`home_player_4` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`home_player_5` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`home_player_6` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`home_player_7` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`home_player_8` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`home_player_9` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`home_player_10` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`home_player_11` = `Player`.`player_api_id`\n" + 
				"where (`Player`.`player_name`) = ('" + playerName + "')\n" + 
				"    and year(`Match`.`date`) = '" + year + "'\n" + 
				"    and (`Match`.`home_team_goal` - `Match`.`away_team_goal`) <> 0\n" + 
				"union all\n" + 
				"SELECT  (`Match`.`away_team_goal` - `Match`.`home_team_goal`) as resultado\n" + 
				"FROM `Player`\n" + 
				"INNER JOIN `Match`\n" + 
				"    on `Match`.`away_player_1` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`away_player_2` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`away_player_3` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`away_player_4` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`away_player_5` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`away_player_6` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`away_player_7` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`away_player_8` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`away_player_9` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`away_player_10` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`away_player_11` = `Player`.`player_api_id`\n" + 
				"where (`Player`.`player_name`) = ('" + playerName + "')\n" + 
				"    and year(`Match`.`date`) = '" + year + "'\n" + 
				"    and (`Match`.`away_team_goal` - `Match`.`home_team_goal`) <> 0;";
	}

	private String getClubNameQuery() {
		return clubNameQuery;
	}

	private void setClubNameQuery(String year, String clubName) {
		this.clubNameQuery = "select (`Match`.`home_team_goal` - `Match`.`away_team_goal`) as resultado\n" + 
				"from `Team`\n" + 
				"inner join `Match`\n" + 
				"    on `Match`.`home_team_api_id` = `Team`.`team_api_id`\n" + 
				"where (`Team`.`team_long_name`) = ('" + clubName + "')\n" + 
				"    and year(`Match`.`date`) = '" + year + "'\n" + 
				"    and (`Match`.`home_team_goal` - `Match`.`away_team_goal`) <> 0\n" + 
				"union all\n" + 
				"select (`Match`.`away_team_goal` - `Match`.`home_team_goal`) as resultado\n" + 
				"from `Team`\n" + 
				"inner join `Match`\n" + 
				"    on `Match`.`away_team_api_id` = `Team`.`team_api_id`\n" + 
				"where (`Team`.`team_long_name`) = ('" + clubName + "')\n" + 
				"    and year(`Match`.`date`) = '" + year + "'\n" + 
				"    and (`Match`.`away_team_goal` - `Match`.`home_team_goal`) <> 0;";
	}

	private String getClubNameAndPlayerNameQuery() {
		return clubNameAndPlayerNameQuery;
	}

	private void setClubNameAndPlayerNameQuery(String year, String clubName, String playerName) {
		this.clubNameAndPlayerNameQuery = "SELECT (`Match`.`home_team_goal` - `Match`.`away_team_goal`) as resultado\n" + 
				"FROM `Player`\n" + 
				"INNER JOIN `Match`\n" + 
				"    on `Match`.`home_player_1` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`home_player_2` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`home_player_3` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`home_player_4` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`home_player_5` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`home_player_6` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`home_player_7` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`home_player_8` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`home_player_9` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`home_player_10` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`home_player_11` = `Player`.`player_api_id`\n" + 
				"INNER JOIN `Team`\n" + 
				"    on `Match`.`home_team_api_id` = `Team`.`team_api_id`\n" + 
				"where (`Player`.`player_name`) = ('" + playerName + "')\n" + 
				"    and (`Team`.`team_long_name`) = ('" + clubName + "')\n" + 
				"    and year(`Match`.`date`) = '" + year + "'\n" + 
				"    and (`Match`.`home_team_goal` - `Match`.`away_team_goal`) <> 0\n" + 
				"union all\n" + 
				"SELECT (`Match`.`away_team_goal` - `Match`.`home_team_goal`) as resultado\n" + 
				"FROM `Player`\n" + 
				"INNER JOIN `Match`\n" + 
				"    on `Match`.`away_player_1` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`away_player_2` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`away_player_3` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`away_player_4` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`away_player_5` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`away_player_6` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`away_player_7` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`away_player_8` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`away_player_9` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`away_player_10` = `Player`.`player_api_id`\n" + 
				"    or `Match`.`away_player_11` = `Player`.`player_api_id`\n" + 
				"INNER JOIN `Team`\n" + 
				"    on `Match`.`away_team_api_id` = `Team`.`team_api_id`\n" + 
				"where (`Player`.`player_name`) = ('" + playerName + "')\n" + 
				"    and (`Team`.`team_long_name`) = ('" + clubName + "')\n" + 
				"    and year(`Match`.`date`) = '" + year + "'\n" + 
				"    and (`Match`.`away_team_goal` - `Match`.`home_team_goal`) <> 0;";
	}

	private String getGetAvailableYearsQuery() {
		return getAvailableYearsQuery;
	}

	private void setGetAvailableYearsQuery(String year1, String year2) {
		this.getAvailableYearsQuery = "SELECT DISTINCT year(date) as resultado\n" + 
		"FROM `Match`\n" + 
		"where year(date) >= '" + year1 +"' and year(date) <='" + year2 + "'\n" + 
		"order by date;";
	}
	private ArrayList<Integer> executeQuery(String query) {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Integer> result = new ArrayList<>();
		
		try {
			stmt = ConexaoMySQL.getConexaoMySQL().createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				result.add(rs.getInt("resultado"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public String getPlayerNameQueryResult(String year, String playerName) {
		playerName = playerName.replace("+", " ");
		this.setPlayerNameQuery(year, playerName);
		ArrayList<Integer> resultSet = new ArrayList<>();
		resultSet = this.executeQuery(this.getPlayerNameQuery());
		System.out.println(resultSet);
		return(this.getWinsAndLosses(resultSet).toString());
	}
	
	public String getClubNameQueryResult(String year, String clubName) {
		clubName = clubName.replace("+", " ");
		this.setClubNameQuery(year, clubName);
		ArrayList<Integer> resultSet = new ArrayList<>();
		resultSet = this.executeQuery(this.getClubNameQuery());
		System.out.println(resultSet);
		return(this.getWinsAndLosses(resultSet).toString());
	}
	
	public String getClubNameAndPlayerNameQueryResult(String year, String clubName, String playerName) {
		clubName = clubName.replace("+", " ");
		playerName = playerName.replace("+", " ");
		this.setClubNameAndPlayerNameQuery(year, clubName, playerName);
		ArrayList<Integer> resultSet = new ArrayList<>();
		resultSet = this.executeQuery(this.getClubNameAndPlayerNameQuery());
		System.out.println(resultSet);
		return(this.getWinsAndLosses(resultSet).toString());
	}
	
	private String getWinsAndLosses(ArrayList<Integer> resultSet) {
		int wins = 0;
		int losses = 0;
		int current;
		Iterator<Integer> ite = resultSet.iterator();
		while(ite.hasNext()) {
			current = ite.next();
			if(current < 0) {
				losses ++;
			} else if(current > 0) {
				wins ++;
			}
		}
		return (wins == 0 && losses == 0) ? "" : new Response(wins, losses).toString();
	}
	
}
