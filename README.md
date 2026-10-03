![Duoc UC](https://www.duoc.cl/wp-content/uploads/2022/09/logo-0.png)

# 🧠 Semana 8 - Actividad Sumativa 3 - Desarrollo Orientado a Objetos II

## 👤 Autor del proyecto

* **Nombre completo:** Mauricio Francisco Valenzuela Fuentes
* **Carrera:** Analista Programador Computacional
* **Sede:** Online

---

## 📘 Descripción general del sistema

Este proyecto corresponde a la **Actividad Sumativa 3** de la asignatura **Desarrollo Orientado a Objetos II**.

Se trata de la octava etapa de **SistemaSpeedFast**, una aplicación desarrollada en Java para representar la gestión de pedidos de la empresa de reparto a domicilio **Speed Fast**.

En esta versión se completa la gestión persistente del sistema mediante la implementación de operaciones **CRUD (Create, Read, Update y Delete)** para las entidades principales:

* Repartidores.
* Pedidos.
* Entregas.

El sistema mantiene la interfaz gráfica construida con **Java Swing** y la persistencia mediante **JDBC y MySQL** incorporada durante la semana anterior.

Además, se aplica el patrón **DAO (Data Access Object)** mediante interfaces DAO y clases de implementación `DAOImpl`, separando las responsabilidades entre la interfaz gráfica, los controladores y el acceso directo a la base de datos.

El sistema permite:

* Registrar, consultar, editar y eliminar repartidores.
* Registrar, consultar, editar y eliminar pedidos.
* Registrar, consultar, editar y eliminar entregas.
* Seleccionar el tipo y estado de los pedidos.
* Visualizar los datos mediante tablas `JTable`.
* Seleccionar pedidos y repartidores mediante `JComboBox`.
* Cargar información directamente desde MySQL.
* Actualizar las tablas después de las operaciones CRUD.
* Validar campos obligatorios antes de ejecutar operaciones.
* Validar el formato de fecha y hora en las entregas.
* Mostrar mensajes de éxito, validación y error mediante `JOptionPane`.
* Cambiar el estado de un pedido de `PENDIENTE` a `EN_REPARTO` al registrar una entrega.
* Ejecutar el registro de una entrega y el cambio de estado del pedido mediante una transacción.
* Confirmar ambas operaciones mediante `COMMIT`.
* Deshacer las operaciones mediante `ROLLBACK` si ocurre un error.
* Manejar errores de conexión y operaciones SQL mediante excepciones.

---

## 🧱 Estructura general del proyecto

```text
📁 SistemaSpeedFast_v8/
│
├── 📁 database/
│   └── speedfast_db.sql
│
├── 📁 lib/
│   └── mysql-connector-j-26.7.0.jar
│
├── 📁 src/
│   ├── 📁 main/
│   │   └── Main.java
│   │
│   ├── 📁 modelo/
│   │   ├── EstadoPedido.java
│   │   ├── Pedido.java
│   │   ├── Repartidor.java
│   │   └── Entrega.java
│   │
│   ├── 📁 util/
│   │   └── ConexionBD.java
│   │
│   ├── 📁 dao/
│   │   ├── PedidoDAO.java
│   │   ├── RepartidorDAO.java
│   │   ├── EntregaDAO.java
│   │   │
│   │   └── 📁 impl/
│   │       ├── PedidoDAOImpl.java
│   │       ├── RepartidorDAOImpl.java
│   │       └── EntregaDAOImpl.java
│   │
│   ├── 📁 controlador/
│   │   ├── ControladorPedidos.java
│   │   ├── ControladorRepartidores.java
│   │   └── ControladorEntregas.java
│   │
│   └── 📁 vista/
│       ├── VentanaPrincipal.java
│       ├── VentanaGestionPedidos.java
│       ├── VentanaGestionRepartidores.java
│       └── VentanaGestionEntregas.java
│
├── 📄 .gitignore
├── 📄 SistemaSpeedFast_v8.iml
└── 📄 README.md
```

---

## 🧩 Organización por paquetes

El proyecto se encuentra organizado en seis paquetes principales:

### 1. `main`

Contiene la clase encargada de iniciar la aplicación.

#### `Main.java`

Es el punto de entrada del sistema.

Crea una instancia de:

```java
VentanaPrincipal
```

y muestra la interfaz principal al usuario.

---

### 2. `modelo`

Contiene las clases que representan los datos utilizados por el sistema.

#### `EstadoPedido.java`

Enum que representa los posibles estados de un pedido:

```text
PENDIENTE
EN_REPARTO
ENTREGADO
```

Cuando se registra una entrega, el pedido relacionado cambia a:

```text
EN_REPARTO
```

---

#### `Pedido.java`

Representa un pedido registrado en SpeedFast.

Contiene los atributos:

```text
id
direccion
tipo
estado
```

Se utilizan dos constructores:

* Uno para crear pedidos nuevos.
* Otro para reconstruir objetos `Pedido` a partir de los registros recuperados desde MySQL.

El identificador del pedido es generado automáticamente por la base de datos mediante:

```sql
AUTO_INCREMENT
```

Los tipos disponibles son:

```text
COMIDA
ENCOMIENDA
EXPRESS
```

---

#### `Repartidor.java`

Representa un repartidor registrado en el sistema.

Contiene:

```text
id
nombre
```

Incluye un método `toString()` que permite mostrar el identificador y el nombre del repartidor en los componentes gráficos.

---

#### `Entrega.java`

Representa la relación entre un pedido y un repartidor.

Contiene:

```text
id
idPedido
idRepartidor
fecha
hora
```

Cada entrega registra qué pedido fue asignado, qué repartidor se encuentra relacionado y la fecha y hora correspondientes.

---

### 3. `util`

Contiene elementos reutilizables para la conexión con la base de datos.

#### `ConexionBD.java`

Centraliza la configuración necesaria para establecer la conexión con MySQL.

Utiliza:

```java
DriverManager.getConnection()
```

La conexión apunta a:

```text
jdbc:mysql://127.0.0.1:3306/speedfast_db
```

Las credenciales deben configurarse localmente antes de ejecutar el proyecto.

La contraseña real utilizada durante el desarrollo no se incluye en el repositorio.

---

### 4. `dao`

Contiene las interfaces que definen las operaciones de acceso a datos.

DAO corresponde a:

```text
Data Access Object
```

Cada entidad principal posee una interfaz DAO con las operaciones CRUD:

```text
create()
readAll()
update()
delete()
```

Las interfaces implementadas son:

```text
PedidoDAO
RepartidorDAO
EntregaDAO
```

Estas interfaces permiten definir las operaciones disponibles sin incluir directamente las sentencias SQL.

---

### `dao.impl`

Contiene las implementaciones concretas de las interfaces DAO.

#### `PedidoDAOImpl.java`

Gestiona las operaciones relacionadas con la tabla:

```text
pedidos
```

Implementa:

```text
create()
readAll()
update()
delete()
```

También incluye un método para actualizar el estado de un pedido utilizando una conexión existente durante una transacción.

Las operaciones utilizan:

```text
INSERT
SELECT
UPDATE
DELETE
```

mediante `PreparedStatement`.

---

#### `RepartidorDAOImpl.java`

Gestiona las operaciones relacionadas con la tabla:

```text
repartidores
```

Implementa:

```text
create()
readAll()
update()
delete()
```

La consulta de repartidores utiliza:

```java
ResultSet
```

para recuperar las filas almacenadas y convertirlas nuevamente en objetos `Repartidor`.

---

#### `EntregaDAOImpl.java`

Gestiona las operaciones relacionadas con la tabla:

```text
entregas
```

Implementa:

```text
create()
readAll()
update()
delete()
```

También permite registrar una entrega utilizando una conexión existente para participar en una transacción compartida con la actualización del pedido.

---

### 5. `controlador`

Contiene las clases que coordinan las operaciones entre la interfaz gráfica y los DAO.

Esto permite evitar que las ventanas sean responsables directamente de las consultas SQL o del acceso a la base de datos.

El flujo utilizado es:

```text
Vista
  ↓
Controlador
  ↓
DAO
  ↓
DAOImpl
  ↓
MySQL
```

---

#### `ControladorPedidos.java`

Coordina las operaciones relacionadas con los pedidos.

Permite:

```text
Registrar pedidos
Listar pedidos
Actualizar pedidos
Eliminar pedidos
```

También valida que la dirección, el tipo y el estado sean válidos antes de solicitar una operación al DAO.

---

#### `ControladorRepartidores.java`

Coordina las operaciones relacionadas con los repartidores.

Permite:

```text
Registrar repartidores
Listar repartidores
Actualizar repartidores
Eliminar repartidores
```

Valida que el nombre del repartidor no se encuentre vacío antes de ejecutar una operación.

---

#### `ControladorEntregas.java`

Coordina las operaciones relacionadas con las entregas.

Permite:

```text
Registrar entregas
Listar entregas
Actualizar entregas
Eliminar entregas
Listar pedidos
Listar repartidores
```

Los pedidos y repartidores obtenidos desde la base de datos se utilizan para cargar los `JComboBox` de la interfaz gráfica.

Cuando se registra una nueva entrega:

```text
Se inicia una transacción
        ↓
Se registra la Entrega
        ↓
PedidoDAO cambia el estado
        ↓
PENDIENTE → EN_REPARTO
        ↓
COMMIT
```

Si cualquiera de estas operaciones falla:

```text
ROLLBACK
```

deshace los cambios realizados durante la transacción.

De esta manera se evita que una entrega quede registrada sin que el estado del pedido sea actualizado correctamente.

---

### 6. `vista`

Contiene las ventanas gráficas desarrolladas mediante Java Swing.

---

#### `VentanaPrincipal.java`

Es la ventana principal del sistema.

Contiene tres opciones:

```text
Gestión de Repartidores
Gestión de Pedidos
Gestión de Entregas
```

Su función principal es permitir la navegación entre los distintos módulos del sistema.

---

#### `VentanaGestionRepartidores.java`

Permite gestionar completamente los repartidores.

El usuario puede:

```text
Registrar
Consultar
Editar
Eliminar
Limpiar formulario
```

Los repartidores almacenados se muestran mediante:

```java
JTable
```

junto con:

```java
DefaultTableModel
```

Antes de registrar o modificar un repartidor se valida que el nombre no esté vacío.

---

#### `VentanaGestionPedidos.java`

Permite gestionar completamente los pedidos.

El usuario ingresa:

```text
Dirección
Tipo
Estado
```

El tipo se selecciona mediante:

```java
JComboBox
```

con las opciones:

```text
COMIDA
ENCOMIENDA
EXPRESS
```

El estado también se selecciona mediante `JComboBox`:

```text
PENDIENTE
EN_REPARTO
ENTREGADO
```

La ventana permite:

```text
Registrar
Consultar
Editar
Eliminar
Limpiar formulario
```

Los pedidos almacenados se muestran mediante `JTable`.

---

#### `VentanaGestionEntregas.java`

Permite gestionar completamente las entregas.

La ventana carga desde MySQL:

```text
Pedidos
Repartidores
```

y los muestra mediante:

```java
JComboBox
```

El usuario selecciona un pedido y un repartidor e ingresa:

```text
Fecha
Hora
```

La ventana permite:

```text
Registrar
Consultar
Editar
Eliminar
Actualizar información
Limpiar formulario
```

Antes de ejecutar las operaciones se valida:

* Que exista un pedido seleccionado.
* Que exista un repartidor seleccionado.
* Que la fecha tenga un formato válido.
* Que la hora tenga un formato válido.

Las entregas almacenadas se muestran mediante `JTable`.

---

## 💾 Base de datos

El sistema utiliza:

```text
speedfast_db
```

La base de datos contiene las tablas:

```text
repartidores
pedidos
entregas
```

### Tabla `repartidores`

Contiene:

```text
id
nombre
```

### Tabla `pedidos`

Contiene:

```text
id
direccion
tipo
estado
```

Los campos `tipo` y `estado` utilizan valores predefinidos mediante `ENUM`.

### Tabla `entregas`

Contiene:

```text
id
id_pedido
id_repartidor
fecha
hora
```

Las columnas:

```text
id_pedido
id_repartidor
```

son claves foráneas relacionadas con las tablas `pedidos` y `repartidores`.

El script necesario para crear la estructura se encuentra en:

```text
database/speedfast_db.sql
```

---

## ⚙️ Instrucciones para ejecutar el proyecto

### 1. Clonar el repositorio

```bash
git clone https://github.com/mauvalenzuelaf-oss/SistemaSpeedFast_v8.git
```

---

### 2. Abrir el proyecto

Abre **IntelliJ IDEA** y selecciona:

```text
Open
```

Luego abre:

```text
SistemaSpeedFast_v8
```

---

### 3. Crear la base de datos

Abre **MySQL Workbench** y ejecuta el archivo:

```text
database/speedfast_db.sql
```

Este script crea:

```text
speedfast_db
```

y las tablas:

```text
repartidores
pedidos
entregas
```

---

### 4. Configurar MySQL Connector/J

El proyecto incluye el conector JDBC en:

```text
lib/mysql-connector-j-26.7.0.jar
```

En IntelliJ debe encontrarse agregado mediante:

```text
File
→ Project Structure
→ Modules
→ Dependencies
```

con:

```text
Scope: Compile
```

---

### 5. Configurar la conexión

Abre:

```text
src/util/ConexionBD.java
```

y configura las credenciales correspondientes a tu instalación local de MySQL:

```java
private static final String USUARIO =
        "root";

private static final String CONTRASENA =
        "TU_CONTRASENA";
```

La contraseña real utilizada durante el desarrollo no se incluye en el repositorio.

---

### 6. Ejecutar la aplicación

Abre:

```text
src/main/Main.java
```

y ejecuta:

```java
main()
```

Se abrirá la ventana principal de SpeedFast.

---

### 7. Utilizar el sistema

Desde la ventana principal es posible acceder a:

```text
Gestión de Repartidores
Gestión de Pedidos
Gestión de Entregas
```

Cada módulo permite realizar operaciones CRUD sobre la información almacenada en MySQL.

Los datos permanecen almacenados después de cerrar la aplicación.

---

## 🖥️ Flujo de funcionamiento

```text
Inicio de aplicación
        ↓
VentanaPrincipal
        │
        ├─────────────────┬─────────────────┐
        │                 │                 │
        ↓                 ↓                 ↓
   Repartidores        Pedidos          Entregas
        │                 │                 │
        ↓                 ↓                 ↓
 Validar datos       Validar datos      Validar datos
        │                 │                 │
        ↓                 ↓                 ↓
Controlador         Controlador         Controlador
Repartidores        Pedidos             Entregas
        │                 │                 │
        ↓                 ↓                 ↓
RepartidorDAO       PedidoDAO           EntregaDAO
        │                 │                 │
        ↓                 ↓                 ↓
RepartidorDAOImpl   PedidoDAOImpl       EntregaDAOImpl
        │                 │                 │
        └─────────────────┴─────────────────┘
                          ↓
                         JDBC
                          ↓
                        MySQL
                          ↓
                    speedfast_db
```

---

## 🔄 Flujo de registro de una entrega

```text
VentanaGestionEntregas
        ↓
Seleccionar Pedido
        +
Seleccionar Repartidor
        +
Ingresar fecha y hora
        ↓
ControladorEntregas
        ↓
Iniciar transacción
        ↓
INSERT entrega
        +
UPDATE pedido
        ↓
¿Ambas operaciones fueron correctas?
        │
    ┌───┴───┐
    │       │
    ↓       ↓
   Sí       No
    │       │
    ↓       ↓
 COMMIT   ROLLBACK
```

Si la transacción se completa correctamente:

```text
PENDIENTE → EN_REPARTO
```

---

## ✅ Validaciones implementadas

El sistema valida los datos ingresados antes de ejecutar operaciones sobre la base de datos.

Entre las validaciones implementadas se encuentran:

```text
Nombre obligatorio para repartidores
Dirección obligatoria para pedidos
Pedido seleccionado para registrar entregas
Repartidor seleccionado para registrar entregas
Fecha válida
Hora válida
Registro seleccionado antes de editar
Registro seleccionado antes de eliminar
```

Los mensajes de validación, éxito o error se muestran mediante:

```java
JOptionPane
```

---

## 🔐 Acceso seguro a datos

Las operaciones SQL utilizan:

```java
PreparedStatement
```

evitando concatenar directamente los valores ingresados por el usuario en las consultas.

Las consultas utilizan:

```java
ResultSet
```

para recuperar información desde MySQL.

Los recursos JDBC se cierran mediante:

```java
try-with-resources
```

Las operaciones relacionadas con el registro de una entrega utilizan:

```text
COMMIT
ROLLBACK
```

para mantener la consistencia de los datos.

---

## 💾 Persistencia de datos

La información se almacena de manera persistente en:

```text
MySQL
```

mediante:

```text
Java Swing
    ↓
Controladores
    ↓
DAO
    ↓
DAOImpl
    ↓
JDBC
    ↓
speedfast_db
```

Esto permite cerrar y volver a ejecutar la aplicación sin perder los pedidos, repartidores y entregas registrados.

---

## 🔄 Evolución respecto de la Semana 7

Durante la Semana 7 se incorporó la conexión con MySQL mediante JDBC y se implementaron operaciones básicas de persistencia.

En esta Semana 8 se completa el ciclo de gestión mediante:

```text
CRUD completo para Repartidores
CRUD completo para Pedidos
CRUD completo para Entregas
Interfaces DAO
Implementaciones DAOImpl
JTable actualizados
JComboBox cargados desde MySQL
Validaciones
Mensajes mediante JOptionPane
Transacciones
COMMIT
ROLLBACK
```

La aplicación mantiene una estructura modular que separa la interfaz gráfica, la lógica de negocio y el acceso a los datos.

---

**Repositorio GitHub:** https://github.com/mauvalenzuelaf-oss/SistemaSpeedFast_v8

**Fecha de Entrega:** 05/10/2026
