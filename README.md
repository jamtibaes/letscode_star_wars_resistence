# Letscode Star Wars Resistence

Swagger (Documentação e Acesso a API):
https://app-starwars-letscode.herokuapp.com/swagger-ui.html#/star-wars-controller

Arquivo: StarWars.postman_collection.json (Postman configurado para consumir a API)

### Cadastro Rebelde (POST /rebeldes)
```
http://localhost:8080/v1/rebeldes
{
        "nome": "Obi-Wan Kenobi",
        "idade": 57,
        "genero": "MASCULINO",
        "inventario": {
            "arma": 2,
            "municao": 0,
            "comida": 6,
            "agua": 1
        },
        "localizacao": {
            "nomeGalaxia": "Bororosca",
            "latitude": 2546.0,
            "longitude": 345.0
        }
}
```
### Consulta Rebeldes (GET /rebeldes)
```
http://localhost:8080/v1/rebeldes
```
### Consulta Rebelde por ID (GET /rebeldes/{id})
```
http://localhost:8080/v1/rebeldes/1
```
### Consulta Traidores (GET /traidores)
```
http://localhost:8080/v1/traidores
```
### Reporte Traidores (POST /traidores/{id})
```
http://localhost:8080/v1/traidores/1
```
### Alteração de Localização (PUT /localização/{id})
```
http://localhost:8080/v1/localizacao/1
{
    "nomeGalaxia": "Boro",
    "latitude": 1.0,
    "longitude": 1.0
}
```
### Transação de Inventário (POST /transacao)
```
http://localhost:8080/v1/transacao
{
    "idComprador" : 1,
    "armaComprador" : 1,
    "municaoComprador": 0,
    "comidaComprador": 0,
    "aguaComprador" : 0,
    
    "idVendedor" : 2,
    "armaVendedor" : 0,
    "municaoVendedor" : 0,
    "comidaVendedor" : 4,
    "aguaVendedor" : 0
}

```
### Relatorio de Rebeldes (GET /relatorios/rebeldes)
```
http://localhost:8080/v1/relatorio/rebeldes
```
### Relatorio de Traidores (GET /relatorios/traidores)
```
http://localhost:8080/v1/relatorio/traidores
```
### Relatorio de Media de Recursos (GET /relatorios/mediarecursos)
```
http://localhost:8080/v1/relatorio/mediarecursos
```
### Relatorio de Pontos Perdidos por Traição (GET /relatorios/pontosperdidos)
```
http://localhost:8080/v1/relatorio/pontosperdidos
```
