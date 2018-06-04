--querys Para o trabalho
--Requisição /getAvailabeYears
SELECT strftime('%Y', date) as year
FROM match
where strftime('%Y', date) >= '2009' and strftime('%Y', date) <= '2011'--onde tem os números dos anos substitua por váriavel
order by date;


--Requisição /getData/2010?playerName=Lionel+Messi
--home
select pl.player_api_id, pl.player_name, (ma.home_team_goal - ma.away_team_goal) as resultado
from player pl
inner join match ma
    on ma.home_player_1 = pl.player_api_id
    or ma.home_player_2 = pl.player_api_id
    or ma.home_player_3 = pl.player_api_id
    or ma.home_player_4 = pl.player_api_id
    or ma.home_player_5 = pl.player_api_id
    or ma.home_player_6 = pl.player_api_id
    or ma.home_player_7 = pl.player_api_id
    or ma.home_player_8 = pl.player_api_id
    or ma.home_player_9 = pl.player_api_id
    or ma.home_player_10 = pl.player_api_id
    or ma.home_player_11 = pl.player_api_id
where upper(pl.player_name) like upper('%Lionel Messi%')--onde tem o nome do jogador substitua por váriavel
    and strftime('%Y', ma.date) = '2010';--onde tem os números dos anos substitua por váriavel
--away
select pl.player_api_id, pl.player_name, (ma.away_team_goal - ma.home_team_goal) as resultado
from player pl
inner join match ma
    on ma.away_player_1 = pl.player_api_id
    or ma.away_player_2 = pl.player_api_id
    or ma.away_player_3 = pl.player_api_id
    or ma.away_player_4 = pl.player_api_id
    or ma.away_player_5 = pl.player_api_id
    or ma.away_player_6 = pl.player_api_id
    or ma.away_player_7 = pl.player_api_id
    or ma.away_player_8 = pl.player_api_id
    or ma.away_player_9 = pl.player_api_id
    or ma.away_player_10 = pl.player_api_id
    or ma.away_player_11 = pl.player_api_id
where upper(pl.player_name) like upper('%Lionel Messi%')--onde tem o nome do jogador substitua por váriavel
    and strftime('%Y', ma.date) = '2010';--onde tem os números dos anos substitua por váriavel


--Requisição /getData/2010?clubName=Real+Madrid
--home
select t.team_api_id,t.team_long_name, (ma.home_team_goal - ma.away_team_goal) as resultado
from team t
inner join match ma
    on ma.home_team_api_id = t.team_api_id
where upper(t.team_long_name) like upper('%Real Madrid%')--onde tem o nome do time substitua por váriavel
    and strftime('%Y', ma.date) = '2010';--onde tem os números dos anos substitua por váriavel
--away
select t.team_api_id,t.team_long_name, (ma.away_team_goal - ma.home_team_goal) as resultado
from team t
inner join match ma
    on ma.home_team_api_id = t.team_api_id
where upper(t.team_long_name) like upper('%Real Madrid%')--onde tem o nome do time substitua por váriavel
    and strftime('%Y', ma.date) = '2010';--onde tem os números dos anos substitua por váriavel


--Requisição /getData/2010?clubName=Barcelona&playerName=Neymar+Jr
--home
select pl.player_api_id, pl.player_name, (ma.home_team_goal - ma.away_team_goal) as resultado
from player pl
inner join match ma
    on ma.home_player_1 = pl.player_api_id
    or ma.home_player_2 = pl.player_api_id
    or ma.home_player_3 = pl.player_api_id
    or ma.home_player_4 = pl.player_api_id
    or ma.home_player_5 = pl.player_api_id
    or ma.home_player_6 = pl.player_api_id
    or ma.home_player_7 = pl.player_api_id
    or ma.home_player_8 = pl.player_api_id
    or ma.home_player_9 = pl.player_api_id
    or ma.home_player_10 = pl.player_api_id
    or ma.home_player_11 = pl.player_api_id
inner join team t
    on ma.home_team_api_id = t.team_api_id
where upper(pl.player_name) like upper('%Neymar%')--onde tem o nome do jogador substitua por váriavel
    and upper(t.team_long_name) like upper('%Barcelona%')--onde tem o nome do time substitua por váriavel
    and strftime('%Y', ma.date) = '2015';--onde tem os números dos anos substitua por váriavel
--away
select pl.player_api_id, pl.player_name, (ma.away_team_goal - ma.home_team_goal) as resultado
from player pl
inner join match ma
    on ma.away_player_1 = pl.player_api_id
    or ma.away_player_2 = pl.player_api_id
    or ma.away_player_3 = pl.player_api_id
    or ma.away_player_4 = pl.player_api_id
    or ma.away_player_5 = pl.player_api_id
    or ma.away_player_6 = pl.player_api_id
    or ma.away_player_7 = pl.player_api_id
    or ma.away_player_8 = pl.player_api_id
    or ma.away_player_9 = pl.player_api_id
    or ma.away_player_10 = pl.player_api_id
    or ma.away_player_11 = pl.player_api_id
inner join team t
    on ma.away_team_api_id = t.team_api_id
    where upper(pl.player_name) like upper('%Neymar%')--onde tem o nome do jogador substitua por váriavel
    and upper(t.team_long_name) like upper('%Barcelona%')--onde tem o nome do time substitua por váriavel
    and strftime('%Y', ma.date) = '2015';--onde tem os números dos anos substitua por váriavel