# File Ingestion API

## Resumo

A aplicação foi criada para ingestão de arquivos com content-type text/plain e posterior busca do conteúdo.

Um swagger foi disponibilizado na raíz do projeto contendo uma relação dos endpoints criados e seus parâmetros/retornos.

## Design

O design da aplicação foi feito com base na arquitetura em camadas; temos 4 camadas "principais":

- Controller: lida com requests e suas respostas, fazendo validações básicas e passando os dados necessários para as services;
- Service: contém a lógica principal do negócio (como a ingestão/transformação do arquivo em si e lógica para buscas);
- Repository: faz a manipulação dos dados, como persistência e busca;
- Domain: representa as entidades principais para as regras (User, Order, Product, etc.).

## Funcionalidades

- Upload e processamento dos dados armazenados num arquivo plain/txt e persistência num banco relacional;
- Busca de orders por id;
- Busca de orders com base na data em que foram realizadas;
- Busca de usuários por id;

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Data JPA para interface com banco de dados
- Banco relacional MySQL devido aos relacionamentos existentes entre os dados (um user possui várias orders, que por sua vez possuem vários products atrelados)
- Docker

## Para build e utilização do projeto

### Clonagem do repositório

```sh
git clone https://github.com/brunomsouz/file-ingestion-api.git
cd file-ingestion-api
```

### Docker
Para facilitar, disponibilizei um Dockerfile aliado de um docker-compose.yaml, que automatiza o build do projeto e já faz o deploy da app junto de um container do MySQL dentro de uma network. Caso queira subir somente a app, atente-se às configurações de rede para permitir comunicação entre os containers.

```sh
docker-compose up -d
```

Caso prefira rodar sem utilização do docker-compose.yaml, utilizar o passo a passo abaixo:

```sh
docker volume create mysql
docker network create --driver bridge app-network
docker run -d -p 3306:3306 --name=mysql --restart=unless-stopped -v mysql:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=file_ingestion --network=app-network mysql:latest
docker build -t file-ingestion-api .
docker run -d -p 8080:8080 --name=file-ingestion-api --restart=unless-stopped -e SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/file_ingestion -e SPRING_DATASOURCE_USERNAME=root -e SPRING_DATASOURCE_PASSWORD=root --network=app-network file-ingestion-api
```