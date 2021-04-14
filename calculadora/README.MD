# Servicio Calculadora - Incompleto

* **Nombre del servicio**: calculadora
* **Puerto**: 8080

## Descripción
Este servicio se encarga de generar sumas aleatorias y recoger el resultado que el usuario introduce.


## Endpoints

### Suma Aleatoria
Devuelve una suma aleatoria en formato _JSON_

* **URL**

  /sumas/random

* **Method:**  **`GET`**

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
```json 
{
  "id": null,
  "factorA": 66,
  "factorB": 96
}
```
 
* **Error Response:**

  * **Code:** 500 Internal Error <br />
    **Content:** `null`
___
### Enviar resultado suma
Envía un resultado de una suma al sistema _JSON_

* **URL**

  /resultados-sumas

* **Method:**
 **`POST`**
  
* **Body Params** _JSON_
```json
{
  "user": {
    "alias": "UserName"
  },
  "suma": {
    "factorA": "66",
    "factorB": "96"
  },
  "resultAttempt": "162"
}
```
* **Success Response:**

  * **Code:** 200 <br />
    **Content:** _JSON_
```json
{
  "id": 68,
  "user": {
    "id": 70,
    "alias": "UsuarioTest"
  },
  "suma": {
    "id": 69,
    "factorA": 44,
    "factorB": 93
  },
  "resultAttempt": 162,
  "correct": true
}
```
 
* **Error Response:**

  * **Code:** 500 Internal Error <br />
    **Content:** `null`

___
### Obtener estadísticas de un jugador
Devuelve todas las sumas de un jugador

* **URL**

  /resultados-sumas?alias={userName}

* **Method:**  **`GET`**
  
* **Request Params**

   **Required:**
   
   `alias=[string]`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** _JSON_
```json
[
  {
    "id": 67,
    "user": {
      "id": 70,
      "alias": "UsuarioTest"
    },
    "suma": {
      "id": 69,
      "factorA": 44,
      "factorB": 93
    },
    "resultAttempt": 161,
    "correct": false
  },
  {
    "id": 68,
    "user": {
      "id": 70,
      "alias": "UsuarioTest"
    },
    "suma": {
      "id": 69,
      "factorA": 44,
      "factorB": 93
    },
    "resultAttempt": 162,
    "correct": true
  }
]
``` 
     
* **Error Response:**

  * **Code:** 500 Internal Error <br />
    **Content:** `null`

___
### Obtener los datos de un intento
Devuelve los datos de un intento realizado

* **URL**

  /resultados-sumas/{resultId}

* **Method:**  **`GET`**
  
* **Path Params**

   **Required:**
   
   `resultId`: Identificador del intento

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** _JSON_
```json
{
  "id": 68,
  "user": {
    "id": 70,
    "alias": "UsuarioTest"
  },
  "suma": {
    "id": 69,
    "factorA": 44,
    "factorB": 93
  },
  "resultAttempt": 162,
  "correct": true
}

``` 
     
* **Error Response:**

  * **Code:** 500 Internal Error <br />
    **Content:** `null`
## Tareas a completar
* Completar las clases `SumaController` y `SumaResultAttempController`, creando los endpoint descritos anteriormente.
* Completar la clase `EventDispatcher`, que enviará un evento al broker (RabbitMQ) con el intento realizado por el usuario.

## Consideraciones

Si usamos un RabbitMQ compartido y que no tenemos en local tendremos que añadir las siguientes properties
```properties
spring.rabbitmq.host=rabbitHostOrIp
spring.rabbitmq.port=8081
```
Y modificar las siguientes para evitar que todos usen la misma cola

```properties
#Deben coincidir con los valores del microservicio Control Partida
rabbit.suma.exchange=my_custom_exange
rabbit.suma.solvedKey=my_custom_key.suma
```
