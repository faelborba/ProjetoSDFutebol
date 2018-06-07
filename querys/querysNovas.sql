--Requisição /getAvailabeYears
SELECT distinct strftime('%Y', date) as year
FROM match
where strftime('%Y', date) >= '2009' and strftime('%Y', date) <= '2011'--onde tem os números dos anos substitua por váriavel
order by date;


--Requisição /getData/2010?clubName=Real+Madrid
--Tabela nova
CREATE TABLE "PartidasTimes" (
    PkPartidaTime integer  PRIMARY KEY AUTOINCREMENT,
    IDPartidaTime INTEGER,
    NomeTime TEXT,
    ResultadoTime INTEGER,
    DataJogoTime TEXT
);

--Primeiro usamos a query abaixo para popular  a tabela acima
insert into PartidasTimes (IDPartidaTime, NomeTime, ResultadoTime,DataJogoTime)
select ma.match_api_id as IDPartidaTime, t.team_long_name as NomeTime, (ma.home_team_goal - ma.away_team_goal) as ResultadoTime, strftime('%Y', ma.date) as DataJogoTime
from team t
inner join match ma
    on ma.home_team_api_id = t.team_api_id;
union all
insert into PartidasTimes (IDPartidaTime, NomeTime, ResultadoTime,DataJogoTime)
select ma.match_api_id as IDPartidaTime, t.team_long_name as NomeTime, (ma.away_team_goal - ma.home_team_goal) as ResultadoTime, strftime('%Y', ma.date) as DataJogoTime
from team t
inner join match ma
    on ma.home_team_api_id = t.team_api_id;


--QUERY nova
-- essa leva em torno de 75ms para ser executada enquanto a antiga levava 235ms
select * from PartidasTimes
where upper(NomeTime) like upper('%Real Madrid%')
    and DataJogoTime = '2010';



--Requisição /getData/2010?playerName=Lionel+Messi
--Tabela Nova
CREATE TABLE "PartidasJogadores" (
    PkPartidaJogador integer  PRIMARY KEY AUTOINCREMENT,
    IDPartidaJogador INTEGER,
    FkIdPartidaTimes integer,
    NomeJogador TEXT,
    ResultadoJogador INTEGER,
    DataJogoJogador TEXT
);

--Primeiro usamos a query abaixo para popular  a tabela acima
insert into PartidasJogadores(IDPartidaJogador,FkIdPartidaTimes,NomeJogador,ResultadoJogador,DataJogoJogador)
select pl.player_api_id as IdPartidaJogador, ma.match_api_id as FkIDPartidaTime, pl.player_name as Nomejogador, (ma.home_team_goal - ma.away_team_goal) as ResultadoJogador, strftime('%Y', ma.date) as DataJogoJogador
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
union all
select pl.player_api_id as IdPartidaJogador, ma.match_api_id as FkIDPartidaTime, pl.player_name as Nomejogador, (ma.away_team_goal - ma.home_team_goal) as ResultadoJogador, strftime('%Y', ma.date) as DataJogoJogador
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
    or ma.away_player_11 = pl.player_api_id;

--Query Nova
select * from PartidasJogadores
where upper(NomeJogador) like upper('%Lionel Messi%')--onde tem o nome do jogador substitua por váriavel
    and DataJogoJogador = '2010';



--Requisição /getData/2010?clubName=Barcelona&playerName=Neymar+Jr
select DISTINCT pj.NomeJogador, pt.NomeTime, pj.ResultadoJogador from PartidasJogadores pj
inner join PartidasTimes pt
    on pt.IDPartidaTime = pj.FkIdPartidaTimes
where upper(pj.NomeJogador) like upper('%Neymar%')--onde tem o nome do jogador substitua por váriavel
    and upper(pt.NomeTime) like upper('%Barcelona%')
    and pj.DataJogoJogador = '2015';
