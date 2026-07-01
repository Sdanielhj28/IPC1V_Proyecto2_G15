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

- **`modelo/`** — Contiene las clases de datos del sistema: `Investigador`, `Muestra`, `Patron`, `Resultado` y `SistemaDatos`. Estas clases representan las entidades del negocio, implementan `Serializable` para la persistencia, y no tienen ninguna dependencia hacia la interfaz gráfica.

- **`vista/`** — Contiene todas las ventanas del sistema construidas con Java Swing y AWT: `LoginFrame`, `AdminFrame`, `InvestigadorFrame`, `AnalisisFrame`, `CrearInvestigadorFrame`, `CrearMuestraFrame` y `CrearPatronFrame`. Cada ventana es responsable únicamente de mostrar información al usuario y capturar sus acciones, sin contener lógica de negocio.

- **`util/`** — Actúa como capa de soporte entre modelo y vista: `CSVUtil` maneja la conversión de texto a matrices, y `PersistenciaUtil` gestiona la serialización y deserialización de los datos hacia archivos binarios, conectando el modelo con el almacenamiento sin que las vistas necesiten conocer estos detalles.

Esta separación permite que los cambios en la interfaz gráfica no afecten la lógica de datos y viceversa.

---

## Estructura del Proyecto

```
src/
├── modelo/
│   ├── Investigador.java   — Entidad investigador con código, nombre, género, contraseña y experimentos (Serializable)
│   ├── Muestra.java        — Entidad muestra con código, descripción, estado, matriz NxN e investigador asignado (Serializable)
│   ├── Patron.java         — Entidad patrón con código, nombre y matriz de 0s y 1s (Serializable)
│   ├── Resultado.java      — Entidad resultado de análisis con no., muestra, patrón, fecha, hora y resultado (Serializable)
│   └── SistemaDatos.java   — Almacén central con cuatro ArrayLists globales: investigadores, muestras, patrones y resultados
├── util/
│   ├── CSVUtil.java            — Conversión de texto separado por ";" a matriz NxN
│   └── PersistenciaUtil.java   — Serialización / deserialización a datos.bin
└── vista/
    ├── LoginFrame.java             — Ventana de inicio de sesión (admin e investigadores)
    ├── AdminFrame.java             — Panel principal del administrador con 4 pestañas
    ├── InvestigadorFrame.java      — Panel del investigador con pestañas Análisis y Resultados
    ├── AnalisisFrame.java          — Ventana emergente con animación de hilos en tiempo real
    ├── CrearInvestigadorFrame.java — Formulario crear/editar investigador
    ├── CrearMuestraFrame.java      — Formulario crear/editar muestra con carga de CSV
    └── CrearPatronFrame.java       — Formulario crear patrón con carga de CSV
```

---

## Lógica Principal — Fragmentos de Código

### Fragmento 1: Serialización de datos (`PersistenciaUtil.java`)

Este es uno de los fragmentos más importantes del proyecto. Se encarga de que todos los datos del sistema persistan entre ejecuciones. `guardarDatos()` serializa los cuatro `ArrayList` del sistema (investigadores, muestras, patrones y resultados) en un archivo binario `datos.bin` usando `ObjectOutputStream`. Al iniciar la aplicación desde `LoginFrame`, `cargarDatos()` usa `ObjectInputStream` para leer ese archivo y restaurar todos los objetos en memoria. Si el archivo no existe (primera ejecución), el bloque `catch` lo maneja silenciosamente y el sistema arranca vacío.

```java
public static void guardarDatos() {
    try {
        ObjectOutputStream out = new ObjectOutputStream(
            new FileOutputStream("datos.bin"));
        out.writeObject(SistemaDatos.investigadores);
        out.writeObject(SistemaDatos.muestras);
        out.writeObject(SistemaDatos.patrones);
        out.writeObject(SistemaDatos.resultados);
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
        SistemaDatos.resultados     = (java.util.ArrayList) in.readObject();
        in.close();
    } catch (Exception e) {
        System.out.println("No hay datos previos cargados");
    }
}
```

La serialización se activa automáticamente al cerrar `AdminFrame` gracias a un `WindowListener` registrado en su constructor. Esto garantiza que ningún dato se pierda al salir de la aplicación:

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

Este fragmento convierte la cadena de texto del patrón (valores separados por `;`) en una matriz cuadrada `int[][]` de tamaño N×N. Primero divide el texto en un arreglo de valores usando `split(";")`, luego calcula N como la raíz cuadrada de la cantidad de elementos (fórmula indicada en el spec: `len(filas o columnas) = √len(arreglo)`). Si el total de valores no forma un cuadrado perfecto, retorna una matriz vacía `int[0][0]` como señal de error. Si es válido, recorre el arreglo y llena la matriz fila por fila.

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

El sistema diferencia dos tipos de usuario. Primero verifica si las credenciales corresponden al administrador (`"admin"/"admin"`); si es así, abre directamente `AdminFrame`. De lo contrario, recorre el `ArrayList` de investigadores buscando una coincidencia de código y contraseña; si la encuentra, abre `InvestigadorFrame` pasándole el objeto `Investigador` encontrado. Si ninguna credencial coincide, muestra un mensaje de error con `JOptionPane`. El método `dispose()` cierra la ventana de login al redirigir para no dejarla abierta en segundo plano.

```java
private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {
    String codigo   = txtcodigo.getText();
    String password = new String(txtpassword.getPassword());

    if (codigo.equals("admin") && password.equals("admin")) {
        AdminFrame admin = new AdminFrame();
        admin.setVisible(true);
        admin.setLocationRelativeTo(null);
        this.dispose();
        return;
    }

    for (Investigador inv : SistemaDatos.investigadores) {
        if (inv.getCodigo().equals(codigo) && inv.getPassword().equals(password)) {
            InvestigadorFrame investigadorFrame = new InvestigadorFrame(inv);
            investigadorFrame.setVisible(true);
            investigadorFrame.setLocationRelativeTo(null);
            this.dispose();
            return;
        }
    }

    javax.swing.JOptionPane.showMessageDialog(this, "Credenciales incorrectas");
}
```

---

### Fragmento 4: Carga masiva CSV de patrones (`AdminFrame.java`)

Cuando el administrador presiona el botón **Cargar** en la pestaña Patrones, se abre un `JFileChooser` para seleccionar el archivo. El sistema lee cada línea con un `Scanner` y la divide en tres partes usando `split(",", 3)`: código, nombre y texto de la matriz. El tercer parámetro `3` asegura que aunque el texto del patrón contenga comas, no se parta incorrectamente. Luego delega la conversión del texto a `CSVUtil.convertirTextoAMatriz()`, crea el objeto `Patron` y lo agrega al `ArrayList` global.

```java
private void btnCargarPatronActionPerformed(java.awt.event.ActionEvent evt) {
    javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
    int resultado = fileChooser.showOpenDialog(this);

    if (resultado == javax.swing.JFileChooser.APPROVE_OPTION) {
        java.io.File archivo = fileChooser.getSelectedFile();
        try {
            java.util.Scanner scanner = new java.util.Scanner(archivo);
            while (scanner.hasNextLine()) {
                String linea    = scanner.nextLine();
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
                Patron patron  = new Patron(codigo, nombre, matriz);
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

La carga masiva de muestras funciona de forma similar, pero con `split(",", 4)` ya que tiene un campo adicional (estado), y construye un objeto `Muestra` en lugar de `Patron`.

---

### Fragmento 5: Asignación de muestra a investigador (`AdminFrame.java`)

Este fragmento maneja la pestaña **Asignación de Experimentos**. Primero valida que ambos combos tengan selección. Luego busca la muestra en el `ArrayList` y verifica que su campo `codigoInvestigadorAsignado` esté vacío: si ya tiene un valor significa que fue asignada anteriormente y muestra un aviso. Si está libre, asigna el código del investigador, cambia el estado de la muestra a `"En proceso"` y recorre el `ArrayList` de investigadores para encontrar al seleccionado e incrementar su contador de experimentos en 1. Finalmente refresca las tablas y combos para reflejar los cambios visualmente.

```java
private void btnAsignarActionPerformed(java.awt.event.ActionEvent evt) {
    if (comboInvestigador.getSelectedItem() == null || comboMuestra.getSelectedItem() == null) {
        javax.swing.JOptionPane.showMessageDialog(this, "Debe seleccionar investigador y muestra");
        return;
    }

    String codigoInvestigador = comboInvestigador.getSelectedItem().toString();
    String codigoMuestra      = comboMuestra.getSelectedItem().toString();

    for (Muestra m : SistemaDatos.muestras) {
        if (m.getCodigo().equals(codigoMuestra)) {

            if (!m.getCodigoInvestigadorAsignado().isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Esta muestra ya fue asignada");
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

### Fragmento 6: Animación del análisis con Hilos (`AnalisisFrame.java`)

Este es el fragmento más complejo del sistema. Al abrirse `AnalisisFrame`, lanza un `Thread` que recorre ambas matrices celda por celda simultáneamente. Para cada celda compara si los valores de `matrizResultado` y `matrizPatron` son iguales, luego usa `SwingUtilities.invokeLater()` para pintar las celdas desde el hilo secundario de forma segura (esto es obligatorio en Swing para no congelar la interfaz). Si coinciden se pinta de **verde**, si difieren se pinta de **rojo** y el hilo se detiene inmediatamente llamando a `finalizarAnalisis(false)`. Si recorre toda la matriz sin diferencias, llama a `finalizarAnalisis(true)`. Entre cada celda se aplica `Thread.sleep(500)` para que la animación sea visible.

```java
private void iniciarAnalisis() {
    Thread hilo = new Thread(() -> {
        boolean coincide = true;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {

                boolean celdaCoincide = matrizResultado[i][j] == matrizPatron[i][j];
                int fila = i, columna = j;

                SwingUtilities.invokeLater(() -> {
                    if (celdaCoincide) {
                        labelsResultado[fila][columna].setBackground(Color.GREEN);
                        labelsPatron[fila][columna].setBackground(Color.GREEN);
                    } else {
                        labelsResultado[fila][columna].setBackground(Color.RED);
                        labelsPatron[fila][columna].setBackground(Color.RED);
                    }
                });

                if (!celdaCoincide) {
                    coincide = false;
                    finalizarAnalisis(false);
                    return;
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
        finalizarAnalisis(coincide);
    });

    hilo.start();
}
```

Al finalizar, `finalizarAnalisis()` llama de vuelta a `InvestigadorFrame` mediante `SwingUtilities.invokeLater()` para actualizar el resultado en la vista principal:

```java
private void finalizarAnalisis(boolean coincide) {
    SwingUtilities.invokeLater(() -> {
        investigadorFrame.recibirResultadoAnalisis(coincide, nombrePatron);
    });
}
```

---

### Fragmento 7: Registro de resultado y actualización de estado (`InvestigadorFrame.java`)

Este método es llamado por `AnalisisFrame` al finalizar la animación. Primero actualiza el `JLabel` de resultado con el texto correspondiente según si coincidió o no. Luego busca la muestra analizada y cambia su estado a `"Procesada"` para que desaparezca del listado de muestras pendientes. Después captura la fecha y hora actuales con `LocalDate` y `LocalTime`, crea un objeto `Resultado` con todos los datos del análisis (número, muestra, patrón, fecha, hora, resultado e investigador) y lo agrega al `ArrayList` global. Finalmente refresca la tabla de resultados, recarga los combos, y llama a `guardarDatos()` para persistir todo inmediatamente.

```java
public void recibirResultadoAnalisis(boolean coincide, String nombrePatron) {
    String codigoMuestra = comboMuestra.getSelectedItem().toString();
    String codigoPatron  = comboPatron.getSelectedItem().toString();
    String textoResultado;

    if (coincide) {
        textoResultado = "Coincide";
        lblResultado.setText("La muestra coincide con " + nombrePatron);
    } else {
        textoResultado = "No coincide";
        lblResultado.setText("La muestra no coincide con " + nombrePatron);
    }

    for (Muestra m : SistemaDatos.muestras) {
        if (m.getCodigo().equals(codigoMuestra)) {
            m.setEstado("Procesada");
            break;
        }
    }

    java.time.LocalDate fecha = java.time.LocalDate.now();
    java.time.LocalTime hora  = java.time.LocalTime.now();
    int numero = SistemaDatos.resultados.size() + 1;

    modelo.Resultado nuevoResultado = new modelo.Resultado(
        numero, codigoMuestra, codigoPatron,
        fecha.toString(), hora.toString().substring(0, 8),
        textoResultado, investigador.getCodigo()
    );

    SistemaDatos.resultados.add(nuevoResultado);
    actualizarTablaResultados();
    cargarCombos();
    util.PersistenciaUtil.guardarDatos();
}
```

---

### Fragmento 8: Generación de HTML desde la matriz (`AdminFrame.java`)

Al hacer clic en la columna **"Acciones"** de la tabla de muestras o patrones, el evento `mouseClicked` detecta en qué fila y columna se hizo clic. Si es la columna 3 (Acciones), obtiene el objeto correspondiente del `ArrayList` por índice de fila y llama al método generador. Este método construye el HTML fila por fila recorriendo la matriz con dos ciclos `for` anidados, imprimiendo cada valor dentro de una celda `<td>`. El nombre del archivo sigue el formato del spec: `Muestra_<codigo>.html` o `Patron_<codigo>.html`.

```java
// Detección del clic en la tabla
private void tablaMuestrasMouseClicked(java.awt.event.MouseEvent evt) {
    int fila    = tablaMuestras.getSelectedRow();
    int columna = tablaMuestras.getSelectedColumn();
    if (fila == -1) return;
    if (columna == 3) {
        Muestra muestra = SistemaDatos.muestras.get(fila);
        generarHTMLMuestra(muestra);
    }
}

// Generación del archivo HTML
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

        writer.println("</table></body></html>");
        writer.close();

        javax.swing.JOptionPane.showMessageDialog(this, "HTML generado: " + nombreArchivo);
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
- **Persistencia:** Serialización de objetos en archivo binario (`datos.bin`), no se permite texto plano
- **Estructuras de datos:** `ArrayList` de Java para investigadores, muestras, patrones y resultados
- **Concurrencia:** `Thread` con `SwingUtilities.invokeLater()` para la animación del análisis
- **Manejo de excepciones:** Bloques `try-catch` en todas las operaciones de archivo y validación de entrada
