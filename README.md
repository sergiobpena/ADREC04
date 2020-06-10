# Recuperación unidade 4
## Parte obrigatoria
Debese realizar unha aplicación que cumpra os seguintes requisitos para acadar 5 puntos utilizando Hibernate e MySQL:

Os datos de conexión da base de datos e otros datos de configuración de Hibernate terá que obterse do ficherio config.json. Este ten o seguinte formato:
~~~
{
    "dbConnection":{
        "address":"192.168.56.101",
        "port":"3306",
        "name":"hibernate",
        "user":"userhibernate",
        "password":"abc123."    
    },
    "hibernate":{
        "driver":"com.mysql.cj.jdbc.Driver",
        "dialect":"org.hibernate.dialect.MySQL5Dialect",
        "HBM2DDL_AUTO":"create",
        "SHOW_SQL": true
    }
}
~~~

- A partir do arquivo “coronavirus.xml” procesa a información para obter os datos por pais e continente do número de casos e número de falecementos para cada día.
- Gardará a información anterior na base de datos na base de datos MySQL utilizando Hibernate.
- Un menú permitiranos realizar as seguintes consultas:
   - Obter aqueles paises con un número determinado de casos totais maior ao número proporcionado.
   - Obter para cada pais o maior número de mortes nun día. Ademais deberase indicar cal foi ese día.

*NOTAS: Débese utilizar SAX para parsear o arquivo XML.*

## Parte opcional
Engadiranse 3 puntos máis se:

- Descargarase a última fonte de datos posible dos eguinte enlace https://opendata.ecdc.europa.eu/covid19/casedistribution/xml e a partir deste actualizarnse todos os datos da base de datos.

*NOTAS: Non se pode borrar todos os datos da base de datos e cargalos de novo*

- Engadiranse 2 puntos máis se:

    - Se realizan as seguintes consultas:
        - O pais con maior número de casos para cada día do que se teña información.
        - Os paises ordenador de maior porcentaxe de crecemento a menor entre o dato de casos do día e o anterior.