--querys Para o trabalho
--Requisição /getAvailabeYears
SELECT DISTINCT year(date)
FROM `Match`
where year(date) >= '2009' and year(date) <='2011';


--Requisição /getData/2010?playerName=Lionel+Messi
--home
SELECT year(`Match`.`date`), `Player`.`player_name`, (`Match`.`home_team_goal` - `Match`.`away_team_goal`) as resultado
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
where `Player`.`player_name` LIKE ('%Lionel Messi%')
    and year(`Match`.`date`) = '2010'
--away
union all
SELECT year(`Match`.`date`), `Player`.`player_name`, (`Match`.`away_team_goal` - `Match`.`home_team_goal`) as resultado
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
where `Player`.`player_name` LIKE ('%Lionel Messi%')
    and year(`Match`.`date`) = '2010';


--Requisição /getData/2010?clubName=Real+Madrid
--home
select `Team`.`team_api_id`,`Team`.`team_long_name`, (`Match`.`home_team_goal` - `Match`.`away_team_goal`) as resultado
from `Team`
inner join `Match`
    on `Match`.`home_team_api_id` = `Team`.`team_api_id`
where upper(`Team`.`team_long_name`) like upper('%Real Madrid%')
    and year(`Match`.`date`) = '2010'
union all
select `Team`.`team_api_id`,`Team`.`team_long_name`, (`Match`.`away_team_goal` - `Match`.`home_team_goal`) as resultado
from `Team`
inner join `Match`
    on `Match`.`away_team_api_id` = `Team`.`team_api_id`
where upper(`Team`.`team_long_name`) like upper('%Real Madrid%')
    and year(`Match`.`date`) = '2010'


--Requisição /getData/2010?clubName=Barcelona&playerName=Neymar+Jr
--home
SELECT year(`Match`.`date`), `Player`.`player_name`, `Team`.`team_long_name`,(`Match`.`home_team_goal` - `Match`.`away_team_goal`) as resultado
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
where `Player`.`player_name` LIKE ('%Neymar%')
    and upper(`Team`.`team_long_name`) like upper('%Barcelona%')
    and year(`Match`.`date`) = '2015'
union all
SELECT year(`Match`.`date`), `Player`.`player_name`, `Team`.`team_long_name`,(`Match`.`away_team_goal` - `Match`.`home_team_goal`) as resultado
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
where `Player`.`player_name` LIKE ('%Neymar%')
    and upper(`Team`.`team_long_name`) like upper('%Barcelona%')
    and year(`Match`.`date`) = '2015';