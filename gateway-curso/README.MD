# Gateway (Netflix Zuul) - Completo 🚀

* **Nombre del servicio**: gateway
* **Puerto**: 8000

## Descripción 🎲
Gateway 

## Rutas

```yaml
zuul:
  ignoredServices: '*'
  prefix: /api
  routes:
    sumas:
      path: /sumas/**
      serviceId: calculadora
      strip-prefix: false
    resultados:
      path: /resultados-suma/**
      serviceId: calculadora
      strip-prefix: false
    clasificacion:
      path: /lideres/**
      serviceId: control
      strip-prefix: false
    estadisticas:
      path: /estadisticas/**
      serviceId: control
      strip-prefix: false
```

## Tareas a completar
* Explicar como se generan las rutas usando eureka.
* Explicar como se realiza el balanceo de carga entre las diferentes instancias de los microservicios con el uso de Ribbon.


## Consideraciones

El servicio usa la librería **Ribbon** para realizar el balanceo de peticiones entre las diferentes instancias de un microservicio. Para comprobar si cada instancia está OK, se realiza un healthCheck contra cada una de ellas, y si este check es KO, la instancia no se usa.

En el caso de que no podamos tener **RabbitMQ** levantado a la hora de probar el gateway, debemos modificar este comportamiento, ya que el microservicio nos dará un KO si no puede conectarse RabbitMQ. Para ello comentamos las siguientes lineas de la clase:

`com.viewnext.gateway.configuration.RibbonConfiguration`
```java
    @Bean
    public IPing ribbonPing(final IClientConfig config){

        return new PingUrl(false, "/health");
    }
```
