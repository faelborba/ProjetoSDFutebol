# ProjetoSDFutebol

O projeto tem como objetivo criar um sistema de banco de dados distribuido entre os Alunos da turma de Sistemas Distribuídos da faculdade de Ciência da Computação da Ulbra, 2018/1.<br>
Os autores são:<br>
Bruno Cunha, Eric Quadros e Rafael Borba<br>

Foi usada as seguintes tecnologias para desenvolver o projeto:<br>
-Java com JDK 8 ou superior.<br>
-Banco de dados MySQL.<br>
-E serviço de Memcached.<br>
Abaixo Segue o Manual para configuração do ambiente de testes.<br>

<h1>Manual de configuração do ambiente de testes.</h1>
É necessário ter instalado em sua máquina a ferramenta de desenvolvimento Eclipse, para compilação e execução do servidor.<br>
Para começar é necessários importar o projeto inteiro em seu Eclipse, todas as bibliotecas java necessárias já estão inclusas nele.<br>
O arquivo de configuração do servidor está no caminho abaixo, arquivo em json:<br>
. ProjetoSDFutebol/config/config.json<br>
Nele você pode definir host, porta e outros detalhes pertinentes ao projeto, como o ano que cada servidor deve pesquisar.<br>
Segue a sintaxe:<br>
{<br>
  &emsp;"serverName" : "serverBER2",<br>
  &emsp;"serverIP" : "localhost",<br>
  &emsp;"portListen" : 1112,<br>
  &emsp;"memcachedServer" : "localhost",<br>
  &emsp;"memcachedPort" : 11211,<br>
  &emsp;"yearData" : [2014, 2010]<br>
}<br>

<h2> Tutorial de configuração do MySql</h2>

Abaixo os passos para instalar o servidor de banco de dados e importar a base. Pode ser mysql ou mariadb em sua máquina. Caso já tenha instalado Pule direto para a segunda parte.<br>
<b>1º)</b> Instale o servidor MySQL.<br>
Se estiver usando a distro ubuntu, em seu terminal rode o comando abaixo. <br>
sudo apt-get install mariadb-server<br>
<b>2º)</b> Após instalação você deve iniciar o servidor mysql.<br>
eu uso o comando abaixo no ubuntu.<br>
sudo service mysql start <br>
<b>3º)</b> Acesse seu SGDB mysql através do terminal use os comandos abaixo (os campos em aspas, troque por seus dados de acesso):<br>
mysql -u "usuário do banco" -h "endereço do server" -p "senha"<br>
Ficando com a sintaxe abaixo.<br>
mysql -u root -h localhost -p 123456<br>
<b>4º)</b> Agora você deve criar a base de dados no seu servidor mysql.<br>
Para isso dentro do mysql rode o comando abaixo.<br>
create database TrabalhoG2<br>
Agora saia do MySql.<br>
<b>5º)</b> Importe a base de dados do trabalho que encontra-se em uma rquivo sql.<br>
Baixe o arquivo sql que consta no link abaixo em sua máquina.<br>
https://github.com/faelborba/ProjetoSDFutebol/blob/master/ProjetoSDFutebol/db/TrabalhoG2.sql<br>
Efetuar a importação da base de dados para uma base nova criada em seu mysql.<br>
Para isso use o comando abaixo diretamente no terminal<br>
mysql -u root -h localhost -p 123456 TrabalhoG2 < <Caminho completo do arquivo/TrabalhoG2.sql>.<br>
<b>6º)</b> Agora você deve configurar o mysql no arquivo de configuração respectivo.<br>
Para isso você deve acessar a o arquivo "configMysql.json" que está dentro da pasta config da raiz do projeto.<br>
Caminho: "ProjetoSDFutebol/config/configMysql.json"<br>
Conteúdo do arquivo em json:<br>
{<br>
    &emsp;"serverName" : "localhost",<br>
    &emsp;"mydatabase" : "TrabalhoG2",<br>
    &emsp;"userName" : "root",<br>
    &emsp;"password" : ""<br>
}<br>
Onde serverName está definido como "localhost" e você deve definir com o seu endereço do mysql.<br>
E mydatabase está como "TrabalhoG2", caso tenha nomeado de outra forma no ítem 4 você deve alterar aqui também.<br>
O userName está "root" e você deve definir com o nome de usuário que usa para acessar o mysql.<br>
O Password está sem senha, mas caso utilize algum você deve preenxer conforme a senha definida para seu usuário Mysql.<br>

Após todos os procedimentos de configuração acima você pode Startar o server em seu Eclipse.<br>
Para isso abra o arquivo SimpleHttpServer.java e execute o projeto.<br>
Caminho para o arquivo:<br>
ProjetoSDFutebol/ProjetoSDFutebol/src/servidorHttp/SimpleHttpServer.java<br>

Finalmente você pode ir até seu navegador e digitar suas requisições conforme necessário.<br>

Fim!<br>