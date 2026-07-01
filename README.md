# Sistema de Gestión de Laboratorio Químico — IPC Quimik

## Datos Generales

| Campo | Detalle |
|---|---|
| **Proyecto** | Proyecto 2 — Sistema de Gestión de Laboratorio Químico para Recepción de Muestras |
| **Curso** | Introducción a la Programación y Computación 1 (IPC1) |
| **Sección** | A |
| **Grupo** | 15 |
| **Integrantes** | José Roberto Orozco Orozco (202200030) — Sergio Daniel Hernández Juárez (202100246) |
| **Universidad** | Universidad San Carlos de Guatemala — Facultad de Ingeniería |

---

## Explicación del MVC

El proyecto está organizado siguiendo el patrón **Modelo-Vista-Controlador (MVC)** mediante tres paquetes principales dentro de `src/`:

- **`modelo/`** — Contiene las clases de datos del sistema: `Investigador`, `Muestra`, `Patron` y `SistemaDatos`. Estas clases representan las entidades del negocio, implementan `Serializable` para la persistencia, y no tienen ninguna dependencia hacia la interfaz gráfica.

- **`vista/`** — Contiene todas las ventanas del sistema construidas con Java Swing y AWT: `LoginFrame`, `AdminFrame`, `CrearInvestigadorFrame`, `CrearMuestraFrame` y `CrearPatronFrame`. Cada ventana es responsable únicamente de mostrar información al usuario y capturar sus acciones, sin contener lógica de negocio.

- **`util/`** — Actúa como capa de soporte entre modelo y vista: `CSVUtil` maneja la conversión de texto a matrices, y `PersistenciaUtil` gestiona la serialización y deserialización de los datos hacia archivos binarios.

Esta separación permite que los cambios en la interfaz gráfica no afecten la lógica de datos y viceversa.

---

## Estructura del Proyecto

```
src/
├── modelo/
│   ├── Investigador.java       — Entidad investigador (Serializable)
│   ├── Muestra.java            — Entidad muestra con matriz NxN (Serializable)
│   ├── Patron.java             — Entidad patrón con matriz de 0s y 1s (Serializable)
│   └── SistemaDatos.java       — Almacén central con ArrayLists globales
├── util/
│   ├── CSVUtil.java            — Conversión de texto separado por ";" a matriz NxN
│   └── PersistenciaUtil.java   — Serialización / deserialización a datos.bin
└── vista/
    ├── LoginFrame.java             — Ventana de inicio de sesión
    ├── AdminFrame.java             — Panel principal del administrador (4 pestañas)
    ├── CrearInvestigadorFrame.java — Formulario crear/editar investigador
    ├── CrearMuestraFrame.java      — Formulario crear muestra
    └── CrearPatronFrame.java       — Formulario crear patrón con carga CSV
```

---

## Lógica Principal — Fragmentos de Código

### Fragmento 1: Serialización de datos (`PersistenciaUtil.java`)

Este es uno de los fragmentos más importantes del proyecto. Se encarga de que todos los datos del sistema persistan entre ejecuciones. Al cerrar `AdminFrame`, se llama a `guardarDatos()` que serializa los tres `ArrayList` del sistema en un archivo binario `datos.bin`. Al iniciar la aplicación desde `LoginFrame`, se llama a `cargarDatos()` que restaura esos objetos en memoria. Si el archivo no existe (primera ejecución), el `catch` lo maneja silenciosamente y el sistema arranca vacío.

```java
public static void guardarDatos() {
    try {
        ObjectOutputStream out = new ObjectOutputStream(
            new FileOutputStream("datos.bin"));
        out.writeObject(SistemaDatos.investigadores);
        out.writeObject(SistemaDatos.muestras);
        out.writeObject(SistemaDatos.patrones);
        out.close();
    } catch (Exception e) {
        System.out.println("Error al guardar datos: " + e.getMessage());
    }
}

public static void cargarDatos() {
    try {
        ObjectInputStream in = new ObjectInputStream(
            new FileInputStream("datos.bin"));
        SistemaDatos.investigadores = (java.util.ArrayList) in.readObject();
        SistemaDatos.muestras       = (java.util.ArrayList) in.readObject();
        SistemaDatos.patrones       = (java.util.ArrayList) in.readObject();
        in.close();
    } catch (Exception e) {
        System.out.println("No hay datos previos cargados");
    }
}
```

La serialización se activa automáticamente al cerrar la ventana del administrador gracias a un `WindowListener`:

```java
addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosing(java.awt.event.WindowEvent e) {
        util.PersistenciaUtil.guardarDatos();
        System.exit(0);
    }
});
```

---

### Fragmento 2: Conversión de patrón CSV a matriz (`CSVUtil.java`)

Este fragmento convierte la cadena de texto del patrón (valores separados por `;`) en una matriz cuadrada `int[][]` de tamaño N×N. Primero divide el texto, calcula N como la raíz cuadrada de la cantidad de elementos (fórmula indicada en el spec), y luego llena la matriz fila por fila. Si la cantidad de valores no forma un cuadrado perfecto, retorna una matriz vacía como señal de error.

```java
public static int[][] convertirTextoAMatriz(String texto) {
    String[] valores = texto.split(";");
    int total = valores.length;
    int n = (int) Math.sqrt(total);

    if (n * n != total) {
        return new int[0][0]; // no es cuadrado perfecto
    }

    int[][] matriz = new int[n][n];
    int index = 0;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            matriz[i][j] = Integer.parseInt(valores[index].trim());
            index++;
        }
    }
    return matriz;
}
```

---

### Fragmento 3: Autenticación de usuarios (`LoginFrame.java`)

El sistema valida el ingreso comparando el código y contraseña ingresados. Si coinciden con `"admin"/"admin"`, abre `AdminFrame`. De lo contrario, busca entre los investigadores registrados en `SistemaDatos`. Si ninguna credencial coincide, muestra un mensaje de error. Este diseño de sistema cerrado significa que solo el administrador puede crear cuentas de investigadores.

```java
private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {
    String codigo   = txtcodigo.getText();
    String password = new String(txtpassword.getPassword());

    if (codigo.equals("admin") && password.equals("admin")) {
        AdminFrame admin = new AdminFrame();
        admin.setVisible(true);
        admin.setLocationRelativeTo(null);
        this.dispose();
    } else {
        javax.swing.JOptionPane.showMessageDialog(this, "Credenciales incorrectas");
    }
}
```

---

### Fragmento 4: Carga masiva CSV de patrones (`AdminFrame.java`)

Cuando el administrador presiona el botón **Cargar** en la pestaña Patrones, se abre un `JFileChooser` para seleccionar un archivo CSV. El sistema lee cada línea, la divide en tres partes (código, nombre, patrón) usando `split(",", 3)`, y llama a `CSVUtil.convertirTextoAMatriz()` para transformar el texto del patrón en una matriz. Luego crea el objeto `Patron` y lo agrega al `ArrayList` global.

```java
private void btnCargarPatronActionPerformed(java.awt.event.ActionEvent evt) {
    javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
    int resultado = fileChooser.showOpenDialog(this);

    if (resultado == javax.swing.JFileChooser.APPROVE_OPTION) {
        java.io.File archivo = fileChooser.getSelectedFile();
        try {
            java.util.Scanner scanner = new java.util.Scanner(archivo);
            while (scanner.hasNextLine()) {
                String linea   = scanner.nextLine();
                String[] partes = linea.split(",", 3);
                if (partes.length < 3) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Formato CSV incorrecto");
                    scanner.close();
                    return;
                }
                String codigo      = partes[0].trim();
                String nombre      = partes[1].trim();
                String textoMatriz = partes[2].trim();
                int[][] matriz = util.CSVUtil.convertirTextoAMatriz(textoMatriz);
                Patron patron = new Patron(codigo, nombre, matriz);
                SistemaDatos.patrones.add(patron);
            }
            actualizarTablaPatrones();
            javax.swing.JOptionPane.showMessageDialog(this, "Patrón cargado correctamente");
            scanner.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
```

---

### Fragmento 5: Asignación de muestra a investigador (`AdminFrame.java`)

Este fragmento maneja la pestaña **Asignación de Experimentos**. Primero verifica que la muestra seleccionada no haya sido asignada previamente (campo `codigoInvestigadorAsignado` vacío). Si está libre, asigna el código del investigador a la muestra, cambia su estado a `"En proceso"`, e incrementa el contador de experimentos del investigador. Si ya estaba asignada, muestra un aviso y no realiza ningún cambio.

```java
private void btnAsignarActionPerformed(java.awt.event.ActionEvent evt) {
    String codigoInvestigador = comboInvestigador.getSelectedItem().toString();
    String codigoMuestra      = comboMuestra.getSelectedItem().toString();

    for (Muestra m : SistemaDatos.muestras) {
        if (m.getCodigo().equals(codigoMuestra)) {

            if (!m.getCodigoInvestigadorAsignado().isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Esta muestra ya fue asignada");
                return;
            }

            m.setCodigoInvestigadorAsignado(codigoInvestigador);
            m.setEstado("En proceso");

            for (Investigador inv : SistemaDatos.investigadores) {
                if (inv.getCodigo().equals(codigoInvestigador)) {
                    inv.setExperimentos(inv.getExperimentos() + 1);
                    break;
                }
            }

            actualizarTablaMuestras();
            actualizarTablaInvestigadores();
            cargarCombosAsignacion();
            javax.swing.JOptionPane.showMessageDialog(this, "Muestra asignada correctamente");
            return;
        }
    }
}
```

---

### Fragmento 6: Generación de HTML desde la matriz (`AdminFrame.java`)

Al hacer clic en el botón **Ver** de cualquier fila de la tabla de muestras o patrones, el sistema genera un archivo `.html` con la matriz representada como una tabla HTML. El nombre del archivo sigue el formato requerido por el spec: `Muestra_<codigo>.html` o `Patron_<codigo>.html`. Recorre la matriz fila por fila e imprime cada valor dentro de una celda `<td>`.

```java
public void generarHTMLMuestra(Muestra muestra) {
    try {
        String nombreArchivo = "Muestra_" + muestra.getCodigo() + ".html";
        java.io.PrintWriter writer = new java.io.PrintWriter(nombreArchivo);

        writer.println("<html><body>");
        writer.println("<h1>Muestra " + muestra.getCodigo() + "</h1>");
        writer.println("<table border='1'>");

        int[][] matriz = muestra.getMatriz();
        for (int i = 0; i < matriz.length; i++) {
            writer.println("<tr>");
            for (int j = 0; j < matriz[i].length; j++) {
                writer.println("<td>" + matriz[i][j] + "</td>");
            }
            writer.println("</tr>");
        }

        writer.println("</table>");
        writer.println("</body></html>");
        writer.close();

        javax.swing.JOptionPane.showMessageDialog(this,
            "HTML generado: " + nombreArchivo);
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error al generar HTML");
    }
}
```

---

## Requisitos Técnicos

- **Lenguaje:** Java
- **IDE recomendado:** NetBeans
- **GUI:** Java Swing y AWT
- **Persistencia:** Serialización de objetos en archivo binario (`datos.bin`)
- **Estructuras de datos:** `ArrayList` de Java para investigadores, muestras y patrones
- **Manejo de excepciones:** Bloques `try-catch` en todas las operaciones de archivo y validación de entrada
