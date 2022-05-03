# Bothniapp

An image archive application written using Ktor and Freemarker.

## Configuring

Example `application.conf` for local development (try **not** to commit local changes to this file). This file is located at `src/main/resources/application.conf` (relative to the project root directory).

```conf
# application.conf
ktor {
    ...
    database {
        jdbcURL = jdbc:postgresql://<hostname>:<port>/<database>
        user = postgres # (default)
        password = <password>
    }
}
```

_... represents the remainder of the configuration, which is left as-is._

## Building

### Gradle (development)

```shell
# macOS & Linux
./gradlew build

# Windows
gradlew.bat build
```

### Docker (production)

```shell
docker build -t bothniapp:latest .
```

## Running

### Gradle (development)

With configuration file **(recommended for local development)**.

_See instructions for configuring application further up._

```shell
# macOS & Linux
./gradlew run

# Windows
gradlew.bat run
```

With environment variables (recommended for production).

```shell
# macOS & Linux
DATABASE_JDBC_URL='<JDBC URL>' \
DATABASE_USER='<USER>' \
DATABASE_PASSWORD='<PASSWORD>' \
SECRET_ENCRYPT_KEY='<28-CHAR HEX-STRING>' \
SECRET_SIGN_KEY='<28-CHAR HEX-STRING>' \
./gradlew run

# Windows
DATABASE_JDBC_URL='<JDBC URL>' \
DATABASE_USER='<USER>' \
DATABASE_PASSWORD='<PASSWORD>' \
SECRET_ENCRYPT_KEY='<28-CHAR HEX-STRING>' \
SECRET_SIGN_KEY='<28-CHAR HEX-STRING>' \
gradlew.bat run
```

### Docker (production)

```shell
docker run \
    -p 8080:8080 \
    -e DATABASE_JDBC_URL='<JDBC URL>' \
    -e DATABASE_USER='<USER>' \
    -e DATABASE_PASSWORD='<PASSWORD>' \
    -e SECRET_ENCRYPT_KEY='<28-CHAR HEX-STRING>' \
    -e SECRET_SIGN_KEY='<28-CHAR HEX-STRING>' \
    bothniapp:latest
```
