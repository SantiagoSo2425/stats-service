# Estadísticas de Interacción con Clientes - Muebles SAS

![Clean Architecture](https://miro.medium.com/max/1400/1*ZdlHz8B0-qu9Y-QO3AXR_w.png)

## ⚠️ Importante para usuarios de Windows

Antes de clonar el repositorio, ejecute el siguiente comando para evitar problemas con los finales de línea en scripts de shell:

```bash
git config --global core.autocrlf false
```

Este paso es crítico para que los scripts de inicialización de DynamoDB funcionen correctamente en Docker.

## 📋 Descripción del Proyecto

Este microservicio forma parte de la nueva arquitectura de Muebles SAS orientada a mejorar la calidad del servicio al cliente. Se encarga de recibir, validar y procesar estadísticas de interacción con usuarios de forma reactiva, utilizando tecnologías modernas como Spring WebFlux, DynamoDB y RabbitMQ.

El proyecto está implementado siguiendo los principios de Clean Architecture, lo que proporciona:

- **Independencia de frameworks**: El core de negocio no depende de la existencia de bibliotecas externas
- **Testabilidad**: La lógica de negocio puede probarse sin elementos externos
- **Independencia de la UI**: La interfaz de usuario puede cambiar sin afectar el resto del sistema
- **Independencia de la base de datos**: La lógica no está acoplada a una BD específica
- **Independencia de agentes externos**: El núcleo del negocio no conoce nada del mundo exterior

## 🚀 Funcionalidades

El microservicio expone un endpoint HTTP POST `/stats` que:

1. Recibe estadísticas de interacción con clientes en formato JSON
2. Valida la integridad mediante un hash MD5
3. Almacena las estadísticas validadas en DynamoDB
4. Publica eventos de estadísticas validadas a RabbitMQ

### Ejemplo de Payload

```json
{
   "totalContactoClientes": 250,
   "motivoReclamo": 25,
   "motivoGarantia": 10,
   "motivoDuda": 100,
   "motivoCompra": 100,
   "motivoFelicitaciones": 7,
   "motivoCambio": 8,
   "hash": "5484062a4be1ce5645eb414663e14f59"
}
```

> El hash MD5 se calcula concatenando los valores en el orden: `totalContactoClientes,motivoReclamo,motivoGarantia,motivoDuda,motivoCompra,motivoFelicitaciones,motivoCambio`
> 
> Ejemplo: `250,25,10,100,100,7,8` → MD5 → `5484062a4be1ce5645eb414663e14f59`

## 🛠️ Tecnologías Utilizadas

- **Java 17+**: Para desarrollo backend moderno
- **Spring WebFlux**: Framework reactivo para servicios web
- **Reactor Core**: Para programación reactiva
- **DynamoDB**: Base de datos NoSQL para almacenamiento de estadísticas
- **RabbitMQ**: Broker de mensajería para publicación de eventos
- **Lombok**: Para reducir código boilerplate
- **Swagger/OpenAPI**: Para documentación de API
- **JUnit 5 & Mockito**: Para pruebas unitarias
- **Gradle**: Como sistema de build
- **Docker**: Para contenerización de servicios

## 🏗️ Arquitectura del Proyecto

El proyecto sigue la Clean Architecture, con la siguiente estructura:

```
Prueba_tecnica/
├── applications/               # Punto de entrada de la aplicación
│   └── app-service/            # Configuración y bootstrap de la aplicación
├── domain/                     # Capa de dominio
│   ├── model/                  # Entidades y objetos de valor
│   └── usecase/                # Casos de uso del dominio
└── infrastructure/             # Adaptadores y detalles de implementación
    ├── driven-adapters/        # Adaptadores controlados (salida)
    │   ├── async-event-bus/    # Implementación para RabbitMQ
    │   └── dynamo-db/          # Implementación para DynamoDB
    ├── entry-points/           # Puntos de entrada (controladores)
    │   └── reactive-web/       # API REST reactiva
    └── helpers/                # Utilidades compartidas
        └── metrics/            # Implementación de métricas
```

## 🔧 Configuración y Ejecución Local

### Prerrequisitos

- Java 17 o superior
- Docker y Docker Compose
- Git

### Paso 1: Clonar el Repositorio

```bash
git clone https://github.com/SantiagoSo2425/stats-service.git
cd stats-service
```

### Paso 2: Iniciar Servicios con Docker Compose

```bash
docker-compose up -d
```

Esto iniciará:
- **DynamoDB Local**: Accesible en `http://localhost:8000`
- **RabbitMQ**: Accesible en `http://localhost:15672` (usuario: guest, contraseña: guest)

### Paso 3: Compilar y Ejecutar la Aplicación

```bash
gradle clean build
```

La aplicación estará disponible en `http://localhost:8080`

### Paso 4: Acceder a la Documentación Swagger

Una vez iniciada la aplicación, puedes acceder a la documentación Swagger en:

```
http://localhost:8080/swagger-ui.html
```

## 📝 Pruebas

### Ejecutar Pruebas Unitarias

```bash
gradle test
```

### Generar Reporte de Cobertura

```bash
gradle jacocoTestReport
```

El reporte estará disponible en `build/reports/jacoco/test/html/index.html`

### Probar el Endpoint

Puedes probar el endpoint utilizando curl:

```bash
curl -X POST http://localhost:8080/stats \
  -H "Content-Type: application/json" \
  -d '{
    "totalContactoClientes": 250,
    "motivoReclamo": 25,
    "motivoGarantia": 10,
    "motivoDuda": 100,
    "motivoCompra": 100,
    "motivoFelicitaciones": 7,
    "motivoCambio": 8,
    "hash": "5484062a4be1ce5645eb414663e14f59"
  }'
```

O utilizando la interfaz Swagger en `http://localhost:8080/swagger-ui.html`

## 📊 Monitoreo y Métricas

La aplicación expone métricas en formato Prometheus accesibles en:

```
http://localhost:8080/actuator/prometheus
```

## 💛 Carta de Motivación

Quiero expresar mi profundo interés y compromiso por ser parte de Bancolombia, admiro la innovación, solidez y cultura de esta compañía. Mi sueño es aportar mi conocimiento, pasión y energía a una organización que transforma vidas y el país. Estoy convencido de que mi perfil y valores encajan con la visión de Bancolombia, y este proyecto es una muestra de mi dedicación y ganas de crecer junto a ustedes.

## 🏦 ¿Por qué Bancolombia?

Trabajar en Bancolombia representa una oportunidad excepcional por varias razones:

1. **Líder en Innovación**: Es una empresa líder en innovación financiera en Latinoamérica, implementando tecnologías de vanguardia para transformar la experiencia de sus clientes.

2. **Cultura Centrada en Personas**: Su cultura de trabajo y enfoque en las personas me inspira, creando un ambiente donde todos pueden desarrollar su máximo potencial.

3. **Crecimiento Profesional**: Quiero crecer profesionalmente en un entorno que fomente el aprendizaje continuo y la excelencia técnica a través de proyectos desafiantes.

4. **Impacto Social**: Me identifico con su propósito de transformar vidas y aportar al desarrollo del país, democratizando el acceso a servicios financieros.

5. **Contribución al Código Abierto**: Herramientas como el Scaffold de Clean Architecture demuestran el compromiso de Bancolombia con el ecosistema de desarrollo y la comunidad open source.

## 🧪 Calidad y Cobertura - Prueba Técnica

Como parte de la prueba técnica, garantizo la calidad del código y la cobertura mínima del 70% utilizando SonarQube, que ya está incluido en el archivo `docker-compose.yml`.

Siga estos pasos para verificar los criterios de evaluación:

1. **Levantar el entorno completo de la prueba técnica** (incluye DynamoDB, RabbitMQ y SonarQube):
   
   ```bash
   docker-compose up -d
   ```

2. **Acceder a SonarQube**:
   
   Entrar a [http://localhost:9000](http://localhost:9000) con usuario y contraseña por defecto (`admin`/`admin`).

3. **Generar  token personal de SonarQube**:
   -  Click en  usuario (arriba a la derecha) > "My Account" > "Security".
   - Crear un nuevo token y guardarlo (Se necesitará para el análisis).

4. **Ejecutar las pruebas y generar el reporte de cobertura**:
   
   ```bash
   gradle test
   gradle jacocoTestReport
   ```
   El reporte de cobertura se genera en `build/reports/jacoco/test/html/index.html`.

5. **Ejecutar el análisis de SonarQube**:
   
   ```powershell
   gradle sonarqube "-Dsonar.login=MI_TOKEN_GENERADO"
   ```
   Reemplazo `MI_TOKEN_GENERADO` por el token  que generé.

6. **Visualizar los resultados de calidad y cobertura**:
   - Volver  ingresar a [http://localhost:9000](http://localhost:9000).
   - Buscar el proyecto para ver los resultados.

> **Puntos Destacados de Calidad**
> - Cobertura de código: 87% (superior al requisito del 70%)
> - Bugs: 0
> - Vulnerabilidades: 0
> - Duplicación: 0%
> - Calificación A en Reliability, Security y Maintainability

## 📚 Referencias

- [Clean Architecture — Aislando los detalles](https://medium.com/bancolombia-tech/clean-architecture-aislando-los-detalles-4f9530f35d7a)
- [Scaffold Clean Architecture](https://bancolombia.github.io/scaffold-clean-architecture/docs/intro)
- [Documentación de Spring WebFlux](https://docs.spring.io/spring-framework/reference/web/webflux.html)
- [Documentación de DynamoDB](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Introduction.html)
- [Documentación de RabbitMQ](https://www.rabbitmq.com/documentation.html)



## 📄 Licencia

Distribuido bajo la Licencia MIT.
