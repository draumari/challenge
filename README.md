# challenge
Challenge

Se crea una arquitectura MVC 
En el directorio principal: com.ar.challenge se encuentra la Aplicación de inicio y una clase llamada Consumo.java que es la encargada de ejecutar la inserción a la BD de los registros consultados en la API https://cex.io/api/last_price/{symbol}/USD, cada 10seg. Para esto se utiliza la etiqueta @Scheduled

En el directorio com.ar.challenge.config, se define el swagger  para facilitar las pruebas a través de la URL http://localhost:8080/swagger-ui.html#/

Luego siguiendo la arquitectura MVC, contamos con 4 directorios 
•	com.ar.challenge.controllers, se define los controladores para cumplir con los requerimientos
o	ChallengeController.java -> Contiene la PARTE 1
o	ConversorController. java -> Contiene la PARTE 2
o	CotizacionController.java -> Guarda en la BD los registros consultados cada 10seg
•	com.ar.challenge.models, se define los modelos 
o	CalculosModel.java -> Es el modelo de respuesta para la opción Parte1/B
o	ConversorModel.java -> Es el modelo de respuesta para la opción Parte2
o	CotizacionModel.java -> Es el modelo de la estructura guardada en la BD.
•	com.ar.challenge.services, se definen los servicios
o	ConversorService.java -> Servicio encargado de traer el ultimo dato almacenado en la base.
o	CotizacionService.java -> Servicio encargado de cumplir con todos los requerimientos de la PARTE1
•	com.ar.challenge.repositories, se define los repositorios
o	CotizacionRepository.java -> Extiende del repositorio Mongo para consumir todas las funcionalidades necesarias.

En el directorio, src/main/resources/application.properties se encuentran las properties y datos para conectar a la bd.
Y por ultimo el directorio, src/test/java contiene los test.

***Descripcion de la Solución***
Cada 10seg se consume el WS https://cex.io/api/last_price/{symbol}/USD, tanto para ETH como BTC, y ambos datos son almacenados en BD, el id se define por <moneda>_fecha getTime. El tiempo es configurable en la properties tiempo.ejecucion=10000 /*Es el tiempo para ejecutar la inserción en milisegundos 10000=10seg*/

Para el manejo de la BD como mongo guarda las fechas en UTC, se define la properties zona.horaria=-3 donde dependiendo del país puedes definir el uso horario, para Argentina es -3. 

