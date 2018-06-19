--querys Para o trabalho
--Requisição /getAvailabeYears
SELECT DISTINCT year(date) as year
FROM `Match`
where year(date) >= '2009' and year(date) <='2011'
order by date;


--Requisição /getData/2010?playerName=Lionel+Messi
--home
SELECT (`Match`.`home_team_goal` - `Match`.`away_team_goal`) as resultado
FROM `Player`
INNER JOIN `Match`
    on `Match`.`home_player_1` = `Player`.`player_api_id`
    or `Match`.`home_player_2` = `Player`.`player_api_id`
    or `Match`.`home_player_3` = `Player`.`player_api_id`
    or `Match`.`home_player_4` = `Player`.`player_api_id`
    or `Match`.`home_player_5` = `Player`.`player_api_id`
    or `Match`.`home_player_6` = `Player`.`player_api_id`
    or `Match`.`home_player_7` = `Player`.`player_api_id`
    or `Match`.`home_player_8` = `Player`.`player_api_id`
    or `Match`.`home_player_9` = `Player`.`player_api_id`
    or `Match`.`home_player_10` = `Player`.`player_api_id`
    or `Match`.`home_player_11` = `Player`.`player_api_id`
where upper(`Player`.`player_name`) = upper('Lionel Messi')
    and year(`Match`.`date`) = '2010'
    and (`Match`.`home_team_goal` - `Match`.`away_team_goal`) <> 0
union all
SELECT  (`Match`.`away_team_goal` - `Match`.`home_team_goal`) as resultado
FROM `Player`
INNER JOIN `Match`
    on `Match`.`away_player_1` = `Player`.`player_api_id`
    or `Match`.`away_player_2` = `Player`.`player_api_id`
    or `Match`.`away_player_3` = `Player`.`player_api_id`
    or `Match`.`away_player_4` = `Player`.`player_api_id`
    or `Match`.`away_player_5` = `Player`.`player_api_id`
    or `Match`.`away_player_6` = `Player`.`player_api_id`
    or `Match`.`away_player_7` = `Player`.`player_api_id`
    or `Match`.`away_player_8` = `Player`.`player_api_id`
    or `Match`.`away_player_9` = `Player`.`player_api_id`
    or `Match`.`away_player_10` = `Player`.`player_api_id`
    or `Match`.`away_player_11` = `Player`.`player_api_id`
where upper(`Player`.`player_name`) = upper('Lionel Messi')
    and year(`Match`.`date`) = '2010'
    and (`Match`.`away_team_goal` - `Match`.`home_team_goal`) <> 0;


--Requisição /getData/2010?clubName=Real+Madrid
--home
select (`Match`.`home_team_goal` - `Match`.`away_team_goal`) as resultado
from `Team`
inner join `Match`
    on `Match`.`home_team_api_id` = `Team`.`team_api_id`
where upper(`Team`.`team_long_name`) = upper('Real Madrid CF')
    and year(`Match`.`date`) = '2010'
    and (`Match`.`home_team_goal` - `Match`.`away_team_goal`) <> 0
union all
select (`Match`.`away_team_goal` - `Match`.`home_team_goal`) as resultado
from `Team`
inner join `Match`
    on `Match`.`away_team_api_id` = `Team`.`team_api_id`
where upper(`Team`.`team_long_name`) = upper('Real Madrid CF')
    and year(`Match`.`date`) = '2010'
    and (`Match`.`away_team_goal` - `Match`.`home_team_goal`) <> 0;


--Requisição /getData/2010?clubName=Barcelona&playerName=Neymar+Jr
--home
SELECT (`Match`.`home_team_goal` - `Match`.`away_team_goal`) as resultado
FROM `Player`
INNER JOIN `Match`
    on `Match`.`home_player_1` = `Player`.`player_api_id`
    or `Match`.`home_player_2` = `Player`.`player_api_id`
    or `Match`.`home_player_3` = `Player`.`player_api_id`
    or `Match`.`home_player_4` = `Player`.`player_api_id`
    or `Match`.`home_player_5` = `Player`.`player_api_id`
    or `Match`.`home_player_6` = `Player`.`player_api_id`
    or `Match`.`home_player_7` = `Player`.`player_api_id`
    or `Match`.`home_player_8` = `Player`.`player_api_id`
    or `Match`.`home_player_9` = `Player`.`player_api_id`
    or `Match`.`home_player_10` = `Player`.`player_api_id`
    or `Match`.`home_player_11` = `Player`.`player_api_id`
INNER JOIN `Team`
    on `Match`.`home_team_api_id` = `Team`.`team_api_id`
where upper(`Player`.`player_name`) = upper('Neymar')
    and upper(`Team`.`team_long_name`) = upper('FC Barcelona')
    and year(`Match`.`date`) = '2015'
    and (`Match`.`home_team_goal` - `Match`.`away_team_goal`) <> 0
union all
SELECT (`Match`.`away_team_goal` - `Match`.`home_team_goal`) as resultado
FROM `Player`
INNER JOIN `Match`
    on `Match`.`away_player_1` = `Player`.`player_api_id`
    or `Match`.`away_player_2` = `Player`.`player_api_id`
    or `Match`.`away_player_3` = `Player`.`player_api_id`
    or `Match`.`away_player_4` = `Player`.`player_api_id`
    or `Match`.`away_player_5` = `Player`.`player_api_id`
    or `Match`.`away_player_6` = `Player`.`player_api_id`
    or `Match`.`away_player_7` = `Player`.`player_api_id`
    or `Match`.`away_player_8` = `Player`.`player_api_id`
    or `Match`.`away_player_9` = `Player`.`player_api_id`
    or `Match`.`away_player_10` = `Player`.`player_api_id`
    or `Match`.`away_player_11` = `Player`.`player_api_id`
INNER JOIN `Team`
    on `Match`.`away_team_api_id` = `Team`.`team_api_id`
where upper(`Player`.`player_name`) = upper('Neymar')
    and upper(`Team`.`team_long_name`) = upper('FC Barcelona')
    and year(`Match`.`date`) = '2015'
    and (`Match`.`away_team_goal` - `Match`.`home_team_goal`) <> 0;