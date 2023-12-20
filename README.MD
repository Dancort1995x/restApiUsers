# API RESTFUL USERS
***

_Creacion de api rest para el registro de clientes_

### Pre-Requisitos 📋
***

_Requisitos para inicializar el proyecto:_

* Java 11 (openjdk)
* Gradle
* IDE a elección (de prefrencia Intellij)
* Base de datos de memoria H2

## Paso para probar 👨🏻‍💻:
***

1. Descargar proyecto e importarlo al IDE
2. configurar version java
3. inicializar proyecto
4. una vez inicializado podra acceder via postman o por swagger desde localhost (http://localhost:[PUERTO]/swagger-ui/index.html)

## Endpoints 👨🏻‍💻
***
Dentro del proyecto se encontrara los siguientes endpoints para probar:
```
{HOST} = localhost:[PUERTO]/api/v1
```
_Obtener usuarios por uuid_

* GET : {HOST}/user/{uuid}

_Listar todos los usuarios registrados_

* GET: {HOST}/user/listAll

_Registrar nuevo usuario_

* POST: {HOST}/user/register

_actualizar usuario por uuid_

* PUT: {HOST}/user/{uuid}

_eliminar usuario por uuid_

* DELETE: {HOST}/user/{uuid}


Para registrar un nuevo usuario debera añadir el siguiente body en json:
```
{
    "name": "NOMBRE",
    "email": "CORREO",
    "password": "CONTRASEÑA",
    "phones": [
        {
            "number": "NUMERO",
            "citycode": "CODIGO DE LA CIUDAD",
            "contrycode": "CODIGO PAIS"
        }
    ]
}

```

## Base de datos
***
Base de datos utilizada es H2, si desea ingresar a la interfaz de la base de datos debera ingresar a la siguiente url:

* http://localhost:[PUERTO]/h2-console

al ingresar debera modificar e ingresar lo siuiente : 


```
JDBC URL: jdbc:h2:mem:testdb
USER NAME: sa
Password: password
```

Al momento de inicializar el proyecto se cargara un script de data por defecto el cual la obtendra de la carpeta resources llamada **data.sql**
