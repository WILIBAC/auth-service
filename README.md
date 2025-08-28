# Proyecto Base Implementando Clean Architecture

## Antes de Iniciar

Empezaremos por explicar los diferentes componentes del proyectos y partiremos de los componentes externos, continuando con los componentes core de negocio (dominio) y por último el inicio y configuración de la aplicación.

Lee el artículo [Clean Architecture — Aislando los detalles](https://medium.com/bancolombia-tech/clean-architecture-aislando-los-detalles-4f9530f35d7a)

# Arquitectura

![Clean Architecture](https://miro.medium.com/max/1400/1*ZdlHz8B0-qu9Y-QO3AXR_w.png)

## Domain

Es el módulo más interno de la arquitectura, pertenece a la capa del dominio y encapsula la lógica y reglas del negocio mediante modelos y entidades del dominio.

## Usecases

Este módulo gradle perteneciente a la capa del dominio, implementa los casos de uso del sistema, define lógica de aplicación y reacciona a las invocaciones desde el módulo de entry points, orquestando los flujos hacia el módulo de entities.

## Infrastructure

### Helpers

En el apartado de helpers tendremos utilidades generales para los Driven Adapters y Entry Points.

Estas utilidades no están arraigadas a objetos concretos, se realiza el uso de generics para modelar comportamientos
genéricos de los diferentes objetos de persistencia que puedan existir, este tipo de implementaciones se realizan
basadas en el patrón de diseño [Unit of Work y Repository](https://medium.com/@krzychukosobudzki/repository-design-pattern-bc490b256006)

Estas clases no puede existir solas y debe heredarse su compartimiento en los **Driven Adapters**

### Driven Adapters

Los driven adapter representan implementaciones externas a nuestro sistema, como lo son conexiones a servicios rest,
soap, bases de datos, lectura de archivos planos, y en concreto cualquier origen y fuente de datos con la que debamos
interactuar.

### Entry Points

Los entry points representan los puntos de entrada de la aplicación o el inicio de los flujos de negocio.

## Application

Este módulo es el más externo de la arquitectura, es el encargado de ensamblar los distintos módulos, resolver las dependencias y crear los beans de los casos de use (UseCases) de forma automática, inyectando en éstos instancias concretas de las dependencias declaradas. Además inicia la aplicación (es el único módulo del proyecto donde encontraremos la función “public static void main(String[] args)”.

**Los beans de los casos de uso se disponibilizan automaticamente gracias a un '@ComponentScan' ubicado en esta capa.**

# Historia de Usuario
<img width="1474" height="457" alt="image" src="https://github.com/user-attachments/assets/0148a5b4-3c5b-4db2-bcce-8ded1aa06027" />

# Flujo
<img width="1331" height="692" alt="image" src="https://github.com/user-attachments/assets/f7a627a0-9802-47b4-9acf-a9cf6ad86dc1" />

# BD Relacional
<img width="824" height="754" alt="image" src="https://github.com/user-attachments/assets/427cd35f-01db-4b28-a024-8543933ca236" />

<img width="823" height="439" alt="image" src="https://github.com/user-attachments/assets/cda9e6e0-e43f-4d72-aefa-637780237268" />

# build jar

```bash
# Option A (from repo root):
./gradlew clean :applications:app-service:bootJar

# Option B (from the module directory):
(cd applications/app-service && ../../gradlew clean bootJar)
```

# copy jar (version-agnostic)

```bash
# Simple glob (picks any SNAPSHOT jar under build/libs)
cp applications/app-service/build/libs/*-SNAPSHOT.jar deployment/crediya-authentication.jar

# Safer: pick the newest jar found under build/libs
JAR_PATH=$(ls -1t applications/app-service/build/libs/*.jar 2>/dev/null | head -n1) && \
  [ -n "$JAR_PATH" ] && cp "$JAR_PATH" deployment/crediya-authentication.jar || \
  (echo "No jar found in applications/app-service/build/libs. Did the build succeed?" && exit 1)
```

# build docker and compose

```bash
cd deployment
docker compose up --build -d
```

# shut down docker compose

```bash
docker compose down -v
```

# One-liner (Unix/macOS): build, package jar to deployment/, and start stack

```bash
./gradlew clean :applications:app-service:bootJar && \
  cp applications/app-service/build/libs/*-SNAPSHOT.jar deployment/crediya-authentication.jar && \
  cd deployment && docker compose up --build -d
```

Notes:
- If you changed the artifact version, the glob handles it automatically. If you use a release version (no -SNAPSHOT), the "Safer" variant will still pick it.
- To view logs after starting in detached mode: `cd deployment && docker compose logs -f app`
- To rebuild after code changes: re-run the gradle bootJar + copy, then `cd deployment && docker compose up --build -d`. 