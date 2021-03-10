## DataSource Configuration
To get the datasource configured on the development tomcat server, create a file named `context.xml` in this same directory and paste/modify the following:

```xml
<Context>

    <!-- Database resource -->
    <Resource name="jdbc/main"
              auth="Container"
              type="javax.sql.DataSource"
              username="user"
              password="password"
              driverClassName=" com.mysql.jdbc.Driver"
              url="jdbc:sqlserver://192.168.0.101:1433;database=no_loot"/>
            
</Context>

```