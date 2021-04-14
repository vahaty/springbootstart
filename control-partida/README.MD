# Servicio Control Partida - Incompleto

* **Nombre del servicio**: control
* **Puerto**: 8081

## Descripción
Este servicio se encarga de guardar las estadísticas de los jugadores

## Endpoints

### Obtener Líderes
Devuelve una lista de jugadores ordenadas por puntuación en formato _JSON_

* **URL**

  /lideres

* **Method:**  **`GET`**

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
```json
[
  {
    "userId": 73,
    "totalScore": 60
  },
  {
    "userId": 82,
    "totalScore": 10
  }
]
```
 
* **Error Response:**

  * **Code:** 500 Internal Error <br />
    **Content:** `null`
___

### Obtener Estadísticas
Devuelve las estadísticas de un jugador en formato _JSON_
* **URL**

  /estadisticas?userId={userId}

* **Method:**
 **`GET`**

* **Request Params**

   **Required:**
   
   `userId=[integer]`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** _JSON_
```json
{
  "userId": 73,
  "score": 50,
  "badges": [
    "LUCKY_NUMBER",
    "FIRST_WON"
  ]
}
``` 
     
* **Error Response:**

  * **Code:** 500 Internal Error <br />
    **Content:** `null`

## Tareas a completar
* Completar las clases `LeaderBoardController` y `UserStatsController`, creando los endpoint descritos anteriormente.


## Consideraciones

Si no tenemos bien configurado RabbitMQ o no tenemos levantado el servicio, no recibiremos eventos con intentos de suma, por lo tanto no se generarán estadísticas de jugador ni la tabla de líderes
___
Si usamos un RabbitMQ compartido y que no tenemos en local tendremos que añadir las siguientes properties
```properties
spring.rabbitmq.host=rabbitHostOrIp
spring.rabbitmq.port=8081
```
Y modificar las siguientes para evitar que todos usen la misma cola

```properties
#Deben coincidir con los valores del microservicio Calculadora
suma.exchange=my_custom_exange
suma.solved.key=my_custom_key.suma

suma.queue=my_custom_queue
suma.anything.routing-key=my_custom_key.*
```
