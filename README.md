# PonchiTruck Core – Sistema de Control de Distribución y Simulación de Rutas

## 1. Datos Generales

- **Proyecto:** Proyecto 1 – PonchiTruck Core
- **Universidad:** San Carlos de Guatemala
- **Curso:** Introducción a la Programación y Computación 1
- **Grupo:** 15
- **Integrantes:**
  - Sergio Daniel Hernández Juárez – Carné 202100246
  - Jose Roberto Orozco Orozco – Carné 202200030

---

## 2. Descripción de la Solución

PonchiTruck Core es un sistema de consola desarrollado en Java que centraliza el control logístico de la ferretería "PonchiTruck". El programa aplica el paradigma de Programación Orientada a Objetos mediante dos clases de entidad, `Vehiculo` y `Orden`, que encapsulan toda la información de la flota y de los pedidos respectivamente. Cada clase expone únicamente atributos privados con sus métodos getters y setters, de forma que cualquier cambio de estado (por ejemplo, el paso de una orden de "Pendiente" a "Asignada") ocurre de forma controlada a través de métodos y no por acceso directo a los atributos.

A partir de estos objetos, el sistema resuelve los tres problemas planteados por la gerencia: evita la sobrecarga de los camiones validando el peso acumulado antes de asignar cualquier pedido, brinda trazabilidad en tiempo real porque el estado de cada vehículo y cada orden se actualiza en memoria en el instante en que ocurre la acción (asignación, despacho, pausa o entrega), y resuelve la falta de planificación de rutas por medio de un módulo de simulación que mueve un vehículo paso a paso sobre una matriz de 10x10, comparando su posición actual contra el destino del pedido en cada turno. Toda la información se maneja con arreglos estáticos de tamaño fijo, sin colecciones dinámicas de Java, conforme lo exigido en los requerimientos técnicos.

---

## 3. Estructuras de Datos Utilizadas

| Estructura | Tipo | Tamaño fijo | Uso |
|---|---|---|---|
| `vehiculos` | `Vehiculo[]` | 25 posiciones | Almacena la flota completa de camiones. Las posiciones vacías se marcan con `null`. |
| `ordenes` | `Orden[]` | 50 posiciones | Almacena el libro de pedidos/envíos. Las posiciones vacías se marcan con `null`. |
| Matriz del mapa | `char` calculado en tiempo de ejecución (10 x 10) | 10 x 10 | Representa el tablero de entrega. No se guarda como una matriz física en memoria; se recorre con dos ciclos `for` anidados y se decide el carácter (`H`, `X`, `V` o `.`) comparando la posición de cada celda contra la posición actual del vehículo (`actualX`, `actualY`) y el destino de la orden (`destinoX`, `destinoY`). |

No se utilizó ninguna colección dinámica de Java (`ArrayList`, `LinkedList`, `HashMap`, etc.). Todo el manejo de datos se realiza con arreglos tradicionales de objetos.

---

## 4. Modelo de Clases

### Clase `Vehiculo`

Atributos privados:
- `id` (String) – Código único e inmutable del camión.
- `marca` (String)
- `modelo` (String)
- `capacidadMax` (double) – Peso máximo soportado en kg.
- `estado` (String) – "Disponible", "En Ruta" o "Mantenimiento".
- `imagen` (String) – Ruta de la imagen usada en los reportes HTML.
- `actualX`, `actualY` (int) – Coordenadas actuales del vehículo dentro del mapa de 10x10. Se inicializan en `(0,0)` y se usan para llevar el control de la posición durante la simulación, incluso si el viaje se pausa con "SALIR".

### Clase `Orden`

Atributos privados:
- `idOrden` (String) – Código único e inmutable del pedido.
- `descripcion` (String)
- `pesoPaquete` (double) – Peso del pedido en kg.
- `destinoX`, `destinoY` (int) – Coordenadas de destino en el mapa.
- `estado` (String) – "Pendiente", "Asignada", "En Ruta" o "Entregada".
- `idVehiculoAsignado` (String) – Es el campo que conecta la orden con un vehículo. Nace en `null` cuando se crea la orden (estado "Pendiente"). Cuando el usuario asigna el pedido a un camión disponible, este atributo guarda el `id` de ese `Vehiculo`, y es precisamente comparando este campo contra el `id` de cada vehículo que el sistema sabe, en los reportes y en la simulación, qué pedidos le corresponden a cada camión. Al desasignar un pedido, este campo se limpia (`null`) y la orden regresa a "Pendiente".

---

## 5. Lógica y Algoritmos Principales

### 5.1 Algoritmo de Ordenamiento (sin desordenar el arreglo original)

Para las vistas "por Capacidad" y "por Marca" se trabaja siempre sobre una copia del arreglo de vehículos, nunca sobre `vehiculos` directamente. Primero se duplican las referencias:

```java
public static Vehiculo[] copiarVehiculos() {
    Vehiculo[] copia = new Vehiculo[vehiculos.length];
    for (int i = 0; i < vehiculos.length; i++) {
        copia[i] = vehiculos[i];
    }
    return copia;
}
```

Y luego se aplica un Bubble Sort manual sobre esa copia, por ejemplo para capacidad descendente:

```java
public static Vehiculo[] ordenarPorCapacidad() {
    Vehiculo[] copia = copiarVehiculos();

    for (int i = 0; i < copia.length - 1; i++) {
        for (int j = i + 1; j < copia.length; j++) {
            if (copia[i] != null && copia[j] != null
                    && copia[i].getCapacidadMax() < copia[j].getCapacidadMax()) {
                Vehiculo temp = copia[i];
                copia[i] = copia[j];
                copia[j] = temp;
            }
        }
    }
    return copia;
}
```

Como el ordenamiento se ejecuta sobre `copia` y el arreglo `vehiculos` jamás se reasigna dentro del método, el orden original (índice 0 al 24) se mantiene intacto para la "Vista por Defecto". El mismo patrón se reutiliza para `ordenarPorMarca()`, cambiando la condición de comparación por `compareToIgnoreCase`.

### 5.2 Algoritmo de Eliminación (desplazamiento hacia la izquierda)

Al eliminar un vehículo, en vez de simplemente poner `null` en su posición (lo que dejaría un hueco), se recorre el arreglo desde el índice encontrado y se desplaza cada elemento siguiente una posición hacia la izquierda:

```java
for (int i = indice; i < vehiculos.length - 1; i++) {
    vehiculos[i] = vehiculos[i + 1];
}
vehiculos[vehiculos.length - 1] = null; // Última posición queda libre
```

De esta forma todos los vehículos posteriores "se recorren" un lugar, y el `null` siempre termina al final del arreglo, nunca en medio.

### 5.3 Validación de Carga Acumulada (Regla de control de peso)

Antes de asignar un pedido a un camión, se recorre el arreglo de órdenes para sumar el peso de todas las que ya tienen guardado el `id` de ese vehículo en `idVehiculoAsignado`, y se compara ese total más el peso del nuevo pedido contra la capacidad máxima:

```java
double cargaAcumulada = 0;
for (int i = 0; i < ordenes.length; i++) {
    if (ordenes[i] != null
            && ordenes[i].getIdVehiculoAsignado() != null
            && ordenes[i].getIdVehiculoAsignado().equalsIgnoreCase(vehiculo.getId())) {
        cargaAcumulada += ordenes[i].getPesoPaquete();
    }
}

double pesoTotal = cargaAcumulada + orden.getPesoPaquete();

if (pesoTotal > vehiculo.getCapacidadMax()) {
    System.out.println("ALERTA DE SOBRECARGA: No se puede asignar el pedido.");
    return;
}

orden.setIdVehiculoAsignado(vehiculo.getId());
orden.setEstado("Asignada");
```

Esta misma lógica de recorrer y sumar por `idVehiculoAsignado` se reutiliza en el reporte de Manifiesto de Carga, para calcular la carga actual, la capacidad disponible y el porcentaje de ocupación de cada camión.

### 5.4 Lógica de Movimiento en la Matriz

En cada turno (cada vez que el usuario presiona ENTER), la nueva posición del camión se calcula con condicionales independientes para cada eje, comparando la posición actual contra el destino de la orden:

```java
int nuevoX = vehiculoEnSimulacion.getActualX();
int nuevoY = vehiculoEnSimulacion.getActualY();

if (nuevoX < destinoX) {
    nuevoX = nuevoX + 1;
} else if (nuevoX > destinoX) {
    nuevoX = nuevoX - 1;
}

if (nuevoY < destinoY) {
    nuevoY = nuevoY + 1;
} else if (nuevoY > destinoY) {
    nuevoY = nuevoY - 1;
}

vehiculoEnSimulacion.setActualX(nuevoX);
vehiculoEnSimulacion.setActualY(nuevoY);
```

Al usar dos bloques `if / else if` separados (uno para filas y otro para columnas), el camión puede moverse en diagonal cuando ambos ejes todavía no coinciden con el destino, y deja de moverse en un eje en el momento en que ese eje ya alcanzó su valor de destino, sin nunca pasarse de la celda objetivo ni salirse de la matriz de 10x10.

---

## 6. Archivos del Repositorio

- `Proyecto_1/` – Proyecto de NetBeans con el código fuente (`Vehiculo.java`, `Orden.java`, `Main.java`, `Proyecto_1.java`).
- `vehiculos.csv` – Archivo de carga masiva inicial de la flota.
- `README.md` – Este documento.
- `Manual_Usuario.pdf` – Manual de usuario para el encargado de despacho.
