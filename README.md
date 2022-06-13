# RetoInventarioEmpleadoVacunacion

## Documentación del API Rest 
La documentación del Api rest se la puede encontrar en la siguiente url http://localhost:8080/swagger-ui/index.html.

Esta url estará habilitada después de que el aplicativo se haya deployado.

## Base de datos
Se debe crear una base de datos en postgress lamada *empleadoVacunaDB*.   Cuando esta base de datos se haya creado y se deploye el aplicativo se generará automáticamente las tablas y la data necesaria necesarias para que el aplicativo funcione correctamente.

> El nombre de la base de datos y el usuario y contraseña de conexión a la base de datos, se la puede modificar en el archivo `/kruger-empleado-vacuna/src/main/resources/application.properties`
> Los datos que están configurados en el archivo application.properties son: 
> database: empleadoVacunaDB
> usuario: postgres
> contraseña: Carp3D13m

### Diagrama de base de datos
![Diagrama entidad relación](/recursos/DerEmpleadoVacuna.png)

# Construcción y ejecución del aplicativo.
Primero se debe clonar este proyecto en una carpeta local y compilarlo, siguiendo los siguientes comandos

```bash
git clone git@github.com:xzabalam/RetoInventarioEmpleadoVacunacion.git
cd RetoInventarioEmpleadoVacunacion
mvn clean install
```

Para deployar el aplicativo se debe importar el código a algún IDE de desarrollo como Eclipse o STS y que tenga instalado el plugins para Spring.  Cuando ya se haya importado el código, Se tiene que seleccionar sobre el nombre del proyecto con el botón derecho y seleccionar la opción Run as > Spring Boot app.

![Deploy de app](recursos/Compilar.png)

El aplicativo automáticamente creará un servidor de aplicaciones en el que se deployará el aplicativo.

# Usuario Administrador
Por defecto el aplicativo crea un usuario administrador, por lo que para saber el nombre de usuario del administrador se deberá consultar a la base de datos con la siguiente consulta
``` sql
select 
  u.username 
from 
  empleado e 
inner join usuario u on e.user_id = u.id 
where 
  nombres ='Administrador' and apellidos = 'Administrador';
```

> Con este usuario se podrá registrar, editar, listar y eliminar empleados.
> La contraseña es el mismo username.  En la base de datos se almacenará encriptada.

# Filtrado de búsqueda
Para el filtrado de empleados se puede utilizar las siguientes queryParams:

- Obtiene de forma paginada todos los empleados registrados: 
  - localhost:8080/empleados
- Se puede personalizar el painado: 
  - localhost:8080/empleados?size=10&page=0
- Se puede filtrar por VACUNADOS: 
  - localhost:8080/empleados?estadoVacunacion=VACUNADO
- Se puede filtrar por NO_VACUNADOS: 
  - localhost:8080/empleados?estadoVacunacion=NO_VACUNADO
- Se puede filtrar por VACUNADOS y por el tipo de vacuna: 
  - localhost:8080/empleados?estadoVacunacion=VACUNADO&tipoVacuna=SPUTNIK
- Se puede filtrar por VACUNADOS y por el tipo de vacuna y obtener los registrs desde una fecha en específico hasta la fecha actual: 
  - localhost:8080/empleados?estadoVacunacion=VACUNADO&tipoVacuna=SPUTNIK&fechaDesde=1931-11-15
- Se puede filtrar por VACUNADOS y por el tipo de vacuna y obtener los registrs desde una fecha en específico hasta una fecha en específico: 
  - localhost:8080/empleados?estadoVacunacion=VACUNADO&tipoVacuna=SPUTNIK&fechaDesde=1931-11-15&fechaHasta=1959-01-21

# Colección para Postman
En la carpeta recursos, se encuentra un archivo llamado *kruger.postman_collection.json* en el que se encuentra los ejemplos de uso del api rest, este archivo se lo debe importar al aplicativo [Postman](https://www.postman.com/)

# Seguridades
Todos los endpoints del Api Rest tienen autenticacion, por lo que se deberá configurar cada api rest con una autenticación básica, en los headers se debe añadir *Authorization: Basic YWExMzUyMDIyOmFhMTM1MjAyMg==* como se muestra en el siguiente ejemplo:
```bash
curl --location --request GET 'localhost:8080/empleados?estadoVacunacion=VACUNADO&tipoVacuna=SPUTNIK&fechaDesde=1931-11-15&fechaHasta=1959-01-21' \
--header 'Authorization: Basic YWExMzUyMDIyOmFhMTM1MjAyMg==' \
--header 'Cookie: JSESSIONID=54EAC21F451C5EB0FB0DA1C752F02F5C'
```
