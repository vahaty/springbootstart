# Ejercicio - Incompleto

**Spring Boot 2.2.1.RELEASE**
## Esquema

![Esquema](esquema.png)


## Componentes
### Calculadora [README](calculadora/README.MD)
Microservicio que se encargará de devolver sumas aleatorias así como recibir los intentos de los usuarios.


### Control Partida [README](control-partida/README.MD)
Microservicio que gestionará el panel de líderes (usuarios con más puntos) y las estadísticas de cada usuario.

### Service Registry [README](service-registry/README.MD)
Servicio de registro, implementado con Netflix Eureka

### Gateway [README](gateway-curso/README.MD)
Gateway, implementado con Netflix Zuul

### RabbitMQ
Descargar e instalar RabbitMQ desde https://www.rabbitmq.com/

Una vez esté instalado, arrancar el servicio.

También es posible arrancar RabbitMQ usando docker:

`docker run -d -p 5672:5672 -p 5671:5671 -p 15672:15672 --name my-rabbit rabbitmq:3-management`
### Frontend
Para arrancar el frontal hay que ejecutar el siguiente comando dentro de la carpeta _frontal-jetty_

`java -jar start.jar`

