Proyecto: saucedemo-serenity

1) Tecnologías y versiones usadas
- Framework: Serenity BDD (Screenplay)
- Serenity: 4.2.34
- Cucumber: 7.14.0
- Selenium WebDriver (a través de Serenity)
- Gradle Wrapper: 7.6.1
- JDK: 17  (válido 11–17; el proyecto fuerza toolchain 17)
- IDE: IntelliJ IDEA
- Logging SLF4J: slf4j-simple:2.0.13

2) Requisitos previos
1. Tener Java 17 instalado (o configurado como toolchain en IntelliJ).
2. Usar el Gradle wrapper incluido (gradlew/gradlew.bat).
3. Tener Chrome instalado.

Nota: La advertencia “Unable to find an exact match for CDP version …” es solo warning y no afecta la ejecución.

3) Estructura relevante
src
 └── test
     ├── java
     │   ├── runners/ShopRunner.java
     │   ├── stepdefinitions/ShopStepDefinitions.java
     │   ├── tasks/*.java
     │   ├── questions/*.java
     │   └── models/OrderData.java
     └── resources
         ├── features/shop.feature
         ├── serenity.conf
         └── data/orders.json

4) Cómo ejecutar

Desde IntelliJ
1) Abrir el proyecto.
2) Verificar Project SDK = 17.
3) Run en runners/ShopRunner.java.

Desde terminal (en la raíz)
- gradlew clean test aggregate
- gradlew clean test -Dcucumber.filter.tags=@json aggregate     (solo escenarios con JSON)
- gradlew clean test -Dcucumber.filter.tags="not @json" aggregate (solo Outline sin JSON)

5) Dónde están los reportes

Serenity BDD
- target/site/serenity/index.html

Cucumber HTML
- target/site/cucumber-html/cucumber-html-reports/overview-features.html
(ahí también: overview-tags.html, overview-steps.html, etc.)

6) Escenarios implementados

A) Outline con Examples (2 casos)
Esquema del escenario: Compra exitosa de dos productos
  Dado que el usuario "<usuario>" se autentica en SauceDemo con la clave "<password>"
  Cuando agrega dos productos al carrito
  Y visualiza el carrito y completa el formulario de compra con "<nombre>", "<apellido>" y "<codigoPostal>"
  Entonces debería ver el mensaje "Thank you for your order!"

Ejemplos:
  | usuario       | password     | nombre | apellido | codigoPostal |
  | standard_user | secret_sauce | Jorge  | Escobar  | 170801       |
  | standard_user | secret_sauce | Carlos | Armijos  | 123456       |

B) Con datos desde JSON (2 casos)
@json
Esquema del escenario: Compra exitosa con datos desde JSON
  Dado cargo datos de compra de "orders.json" en la fila <idx>
  Y que el usuario se autentica en SauceDemo
  Cuando agrega dos productos al carrito
  Y visualiza el carrito y completa el formulario de compra
  Entonces debería ver el mensaje "Thank you for your order!"

Ejemplos:
  | idx |
  | 0   |
  | 1   |

Archivo data/orders.json:
[
  {
    "user": "standard_user",
    "password": "secret_sauce",
    "firstName": "Jorge",
    "lastName": "Escobar",
    "zip": "170801"
  },
  {
    "user": "standard_user",
    "password": "secret_sauce",
    "firstName": "Carlos",
    "lastName": "Armijos",
    "zip": "123456"
  }
]

La columna idx indica el índice (0-based) del arreglo usado en cada corrida.

7) Cómo añadir más datos de prueba
- Outline: agregar filas en la tabla Examples del feature.
- JSON: agregar objetos en data/orders.json y nuevas filas idx en el Examples @json.

8) Problemas comunes y soluciones aplicadas
- Reporte Serenity “plano” (sin CSS/JS): se agregó dependencia
  net.serenity-bdd:serenity-report-resources:4.2.34 y se ejecuta clean test aggregate.
- Warning SLF4J: se agregó backend testRuntimeOnly 'org.slf4j:slf4j-simple:2.0.13'.
- Unsupported class file major version (ByteBuddy/Jackson con JDK 21+):
  se forzaron versiones compatibles en build.gradle:
    net.bytebuddy:byte-buddy:1.14.10
    com.fasterxml.jackson.core:jackson-*:2.14.2
- Advertencia CDP (Selenium/Chrome): solo warning; ejecución OK.

9) Limpiar reportes
Windows:  rmdir /s /q target
macOS/Linux: rm -rf target

10) Comandos útiles
gradlew clean test aggregate
gradlew clean test -Dcucumber.filter.tags=@json aggregate
gradlew clean test -Dcucumber.filter.tags="not @json" aggregate
