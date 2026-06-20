package proyecto_1;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

/**
 *
 * @author daniel
 */
public class Main {

    public static Vehiculo[] vehiculos = new Vehiculo[25];
    public static Orden[] ordenes = new Orden[50];

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int opcion;

        do {
            System.out.println("PONCHITRUCK CORE");
            System.out.println("1. CRUD de Vehiculos");
            System.out.println("2. Ordenes de Envio");
            System.out.println("3. Simulacion en el Mapa");
            System.out.println("4. Consultas y Reportes");
            System.out.println("5. Reportes HTML");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {

                    case 1:
                        menuVehiculos();
                        break;

                    case 2:
                        menuOrdenes();
                        break;

                    case 3:
                        menuSimulacion();
                        break;

                    case 4:
                        menuConsultas();
                        break;

                    case 5:
                        menuReportesHTML();
                        break;

                    case 0:
                        System.out.println("Saliendo...");
                        break;

                    default:
                        System.out.println("Opcion invalida.");
                }

            } catch (Exception e) {
                System.out.println("Debe ingresar un numero.");
                opcion = -1;
            }

        } while (opcion != 0);
    }

    //                  MENÚ CRUD VEHÍCULOS
    public static void menuVehiculos() {
        int opcion;

        do {
            System.out.println("CRUD DE VEHICULOS");
            System.out.println("1. Carga masiva (vehiculos.csv)");
            System.out.println("2. Ingreso manual");
            System.out.println("3. Modificar vehiculo");
            System.out.println("4. Eliminar vehiculo");
            System.out.println("5. Mostrar vehiculos");
            System.out.println("0. Regresar");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {
                    case 1:
                        cargaMasivaCSV();
                        break;
                    case 2:
                        ingresarVehiculoManual();
                        break;
                    case 3:
                        modificarVehiculo();
                        break;
                    case 4:
                        eliminarVehiculo();
                        break;
                    case 5:
                        mostrarVehiculos();
                        break;
                    case 0:
                        System.out.println("Regresando al menu principal...");
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                }

            } catch (Exception e) {
                System.out.println("Debe ingresar un numero.");
                opcion = -1;
            }

        } while (opcion != 0);
    }

    // =========================================================
    //              1. CARGA MASIVA DESDE CSV
    // =========================================================
    public static void cargaMasivaCSV() {
        System.out.println("CARGA MASIVA DESDE vehiculos.csv");
        System.out.print("Ingrese la ruta del archivo vehiculos.csv: ");
        String ruta = sc.nextLine().trim();

        int registrados = 0;
        int omitidos = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(ruta));
            String linea;
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {
                // Saltar encabezado
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] datos = linea.split(",");

                if (datos.length < 6) {
                    System.out.println("Linea con formato incorrecto, omitida: " + linea);
                    omitidos++;
                    continue;
                }

                String id           = datos[0].trim();
                String marca        = datos[1].trim();
                String modelo       = datos[2].trim();
                double capacidadMax = Double.parseDouble(datos[3].trim());
                String estado       = datos[4].trim();
                String imagen       = datos[5].trim();

                // Verificar ID duplicado
                if (buscarVehiculoPorId(id) != -1) {
                    System.out.println("ID duplicado, omitido: " + id);
                    omitidos++;
                    continue;
                }

                // Buscar espacio en el arreglo
                boolean insertado = false;
                for (int i = 0; i < vehiculos.length; i++) {
                    if (vehiculos[i] == null) {
                        vehiculos[i] = new Vehiculo(id, marca, modelo, capacidadMax, estado, imagen);
                        insertado = true;
                        registrados++;
                        break;
                    }
                }

                if (!insertado) {
                    System.out.println("Arreglo lleno. No se pudieron registrar mas vehiculos.");
                    omitidos++;
                    break;
                }
            }

            br.close();
            System.out.println("Carga finalizada.");
            System.out.println("Vehiculos registrados : " + registrados);
            System.out.println("Registros omitidos    : " + omitidos);

        } catch (java.io.FileNotFoundException e) {
            System.out.println("Error: no se encontro el archivo en la ruta indicada.");
        } catch (NumberFormatException e) {
            System.out.println("Error: la capacidad maxima debe ser un numero valido.");
        } catch (Exception e) {
            System.out.println("Error inesperado al leer el archivo: " + e.getMessage());
        }
    }

    //              2. INGRESO MANUAL (ya existía)
    public static void ingresarVehiculoManual() {
        try {
            System.out.println("INGRESO MANUAL DE VEHICULO");

            System.out.print("ID: ");
            String id = sc.nextLine();

            if (buscarVehiculoPorId(id) != -1) {
                System.out.println("Error: ya existe un vehiculo con ese ID.");
                return;
            }

            System.out.print("Marca: ");
            String marca = sc.nextLine();

            System.out.print("Modelo: ");
            String modelo = sc.nextLine();

            System.out.print("Capacidad maxima en kg: ");
            double capacidad = Double.parseDouble(sc.nextLine());

            System.out.print("Estado (Disponible / En Ruta / Mantenimiento): ");
            String estado = sc.nextLine();

            System.out.print("Ruta de imagen: ");
            String imagen = sc.nextLine();

            for (int i = 0; i < vehiculos.length; i++) {
                if (vehiculos[i] == null) {
                    vehiculos[i] = new Vehiculo(id, marca, modelo, capacidad, estado, imagen);
                    System.out.println("Vehiculo registrado correctamente.");
                    return;
                }
            }

            System.out.println("No hay espacio disponible para mas vehiculos.");

        } catch (Exception e) {
            System.out.println("Error: dato invalido.");
        }
    }
    
    //              3. MODIFICAR VEHÍCULO
    public static void modificarVehiculo() {
        try {
            System.out.println("MODIFICAR VEHICULO");
            System.out.print("Ingrese el ID del vehiculo a modificar: ");
            String id = sc.nextLine().trim();

            int indice = buscarVehiculoPorId(id);

            if (indice == -1) {
                System.out.println("Error: no se encontro ningun vehiculo con ese ID.");
                return;
            }

            Vehiculo v = vehiculos[indice];

            // Bloqueo si está En Ruta
            if (v.getEstado().equalsIgnoreCase("En Ruta")) {
                System.out.println("Operacion denegada: El vehiculo y su carga estan en transito operativo.");
                return;
            }

            System.out.println("Vehiculo encontrado: " + v.getMarca() + " " + v.getModelo());
            System.out.println("Deje en blanco los campos que NO desea modificar.");

            System.out.print("Nueva marca [" + v.getMarca() + "]: ");
            String marca = sc.nextLine().trim();
            if (!marca.isEmpty()) v.setMarca(marca);

            System.out.print("Nuevo modelo [" + v.getModelo() + "]: ");
            String modelo = sc.nextLine().trim();
            if (!modelo.isEmpty()) v.setModelo(modelo);

            System.out.print("Nueva capacidad maxima kg [" + v.getCapacidadMax() + "]: ");
            String capStr = sc.nextLine().trim();
            if (!capStr.isEmpty()) {
                double nuevaCap = Double.parseDouble(capStr);
                v.setCapacidadMax(nuevaCap);
            }

            System.out.print("Nuevo estado (Disponible / En Ruta / Mantenimiento) [" + v.getEstado() + "]: ");
            String estado = sc.nextLine().trim();
            if (!estado.isEmpty()) v.setEstado(estado);

            System.out.println("Vehiculo modificado correctamente.");

        } catch (NumberFormatException e) {
            System.out.println("Error: la capacidad debe ser un numero valido.");
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    //              4. ELIMINAR VEHÍCULO (con desplazamiento)
    public static void eliminarVehiculo() {
        try {
            System.out.println("ELIMINAR VEHICULO");
            System.out.print("Ingrese el ID del vehiculo a eliminar: ");
            String id = sc.nextLine().trim();

            int indice = buscarVehiculoPorId(id);

            if (indice == -1) {
                System.out.println("Error: no se encontro ningun vehiculo con ese ID.");
                return;
            }

            // Bloqueo si está En Ruta
            if (vehiculos[indice].getEstado().equalsIgnoreCase("En Ruta")) {
                System.out.println("Operacion denegada: El vehiculo y su carga estan en transito operativo.");
                return;
            }

            String nombreVehiculo = vehiculos[indice].getMarca() + " " + vehiculos[indice].getModelo();

            // Desplazar elementos hacia la izquierda para no dejar huecos
            for (int i = indice; i < vehiculos.length - 1; i++) {
                vehiculos[i] = vehiculos[i + 1];
            }
            vehiculos[vehiculos.length - 1] = null; // Última posición queda libre

            System.out.println("Vehiculo '" + nombreVehiculo + "' eliminado correctamente.");

        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    //              UTILIDADES
    public static int buscarVehiculoPorId(String id) {
        for (int i = 0; i < vehiculos.length; i++) {
            if (vehiculos[i] != null && vehiculos[i].getId().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }

    public static void mostrarVehiculos() {
        System.out.println("LISTADO DE VEHICULOS");
        boolean hayVehiculos = false;

        for (int i = 0; i < vehiculos.length; i++) {
            if (vehiculos[i] != null) {
                hayVehiculos = true;
                System.out.println("ID       : " + vehiculos[i].getId());
                System.out.println("Marca    : " + vehiculos[i].getMarca());
                System.out.println("Modelo   : " + vehiculos[i].getModelo());
                System.out.println("Capacidad: " + vehiculos[i].getCapacidadMax() + " kg");
                System.out.println("Estado   : " + vehiculos[i].getEstado());
                System.out.println("Imagen   : " + vehiculos[i].getImagen());
            }
        }

        if (!hayVehiculos) {
            System.out.println("No hay vehiculos registrados.");
        }
    }

    //         MÓDULOS PENDIENTES (mañana)

    public static void menuOrdenes() {
    int opcion;

    do {
        System.out.println("\n===== ORDENES DE ENVIO =====");
        System.out.println("1. Crear orden");
        System.out.println("2. Mostrar ordenes");
        System.out.println("3. Asignar pedido a vehiculo");
        System.out.println("4. Modificar orden");
        System.out.println("5. Desasignar pedido");
        System.out.println("0. Regresar");
        System.out.print("Seleccione una opcion: ");

        try {
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    crearOrden();
                    break;
                case 2:
                    mostrarOrdenes();
                    break;
                case 3:
                    asignarPedido();
                    break;
                case 4:
                    modificarOrden();
                    break;
                case 5:
                    desasignarPedido();
                    break;
                case 0:
                    System.out.println("Regresando al menu principal...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }

        } catch (Exception e) {
            System.out.println("Debe ingresar un numero.");
            opcion = -1;
        }

    } while (opcion != 0);
}
    public static void crearOrden() {
    try {
        System.out.println("\n===== CREAR ORDEN =====");

        System.out.print("ID de orden: ");
        String idOrden = sc.nextLine();

        if (buscarOrdenPorId(idOrden) != -1) {
            System.out.println("Error: ya existe una orden con ese ID.");
            return;
        }

        System.out.print("Descripcion: ");
        String descripcion = sc.nextLine();

        System.out.print("Peso del paquete en kg: ");
        double peso = Double.parseDouble(sc.nextLine());

        System.out.print("Destino X (0 a 9): ");
        int destinoX = Integer.parseInt(sc.nextLine());

        System.out.print("Destino Y (0 a 9): ");
        int destinoY = Integer.parseInt(sc.nextLine());

        if (destinoX < 0 || destinoX > 9 || destinoY < 0 || destinoY > 9) {
            System.out.println("Error: las coordenadas deben estar entre 0 y 9.");
            return;
        }

        for (int i = 0; i < ordenes.length; i++) {
            if (ordenes[i] == null) {
                ordenes[i] = new Orden(idOrden, descripcion, peso, destinoX, destinoY);
                System.out.println("Orden registrada correctamente.");
                return;
            }
        }

        System.out.println("No hay espacio disponible para mas ordenes.");

    } catch (NumberFormatException e) {
        System.out.println("Error: debe ingresar numeros validos.");
    } catch (Exception e) {
        System.out.println("Error inesperado: " + e.getMessage());
    }
}
    public static int buscarOrdenPorId(String idOrden) {
    for (int i = 0; i < ordenes.length; i++) {
        if (ordenes[i] != null && ordenes[i].getIdOrden().equalsIgnoreCase(idOrden)) {
            return i;
        }
    }
    return -1;
}
    public static void mostrarOrdenes() {
    System.out.println("LISTADO DE ORDENES");

    boolean hayOrdenes = false;

    for (int i = 0; i < ordenes.length; i++) {
        if (ordenes[i] != null) {
            hayOrdenes = true;

            System.out.println("--------------------------------");
            System.out.println("ID Orden   : " + ordenes[i].getIdOrden());
            System.out.println("Descripcion: " + ordenes[i].getDescripcion());
            System.out.println("Peso       : " + ordenes[i].getPesoPaquete() + " kg");
            System.out.println("Destino    : (" + ordenes[i].getDestinoX() + ", " + ordenes[i].getDestinoY() + ")");
            System.out.println("Estado     : " + ordenes[i].getEstado());
            System.out.println("Vehiculo   : " + ordenes[i].getIdVehiculoAsignado());
        }
    }

    if (!hayOrdenes) {
        System.out.println("No hay ordenes registradas.");
    }
}

    //         ASIGNACION DE PEDIDOS (Regla 1: control de peso)
    public static void asignarPedido() {
    try {
        System.out.println("ASIGNAR PEDIDO A VEHICULO");
        System.out.print("Ingrese el ID de la orden (debe estar Pendiente): ");
        String idOrden = sc.nextLine().trim();

        int idxOrden = buscarOrdenPorId(idOrden);

        if (idxOrden == -1) {
            System.out.println("Error: no se encontro ninguna orden con ese ID.");
            return;
        }

        Orden orden = ordenes[idxOrden];

        if (!orden.getEstado().equalsIgnoreCase("Pendiente")) {
            System.out.println("Error: la orden debe estar en estado 'Pendiente' para poder asignarla.");
            return;
        }

        System.out.print("Ingrese el ID del vehiculo (debe estar Disponible): ");
        String idVehiculo = sc.nextLine().trim();

        int idxVehiculo = buscarVehiculoPorId(idVehiculo);

        if (idxVehiculo == -1) {
            System.out.println("Error: no se encontro ningun vehiculo con ese ID.");
            return;
        }

        Vehiculo vehiculo = vehiculos[idxVehiculo];

        if (!vehiculo.getEstado().equalsIgnoreCase("Disponible")) {
            System.out.println("Error: el vehiculo debe estar 'Disponible' para poder asignarle un pedido.");
            return;
        }

        // Regla 1: peso del nuevo pedido + suma de pesos ya acumulados en ese camion < capacidad maxima
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
            System.out.println("Carga acumulada actual : " + cargaAcumulada + " kg");
            System.out.println("Peso del nuevo pedido   : " + orden.getPesoPaquete() + " kg");
            System.out.println("Capacidad maxima        : " + vehiculo.getCapacidadMax() + " kg");
            System.out.println("Total resultante        : " + pesoTotal + " kg (supera la capacidad)");
            return;
        }

        // Validacion correcta -> se asigna
        orden.setIdVehiculoAsignado(vehiculo.getId());
        orden.setEstado("Asignada");

        System.out.println("Pedido asignado correctamente.");
        System.out.println("Orden " + orden.getIdOrden() + " -> Vehiculo " + vehiculo.getId());
        System.out.println("Carga total del vehiculo ahora: " + pesoTotal + " kg / " + vehiculo.getCapacidadMax() + " kg");

    } catch (Exception e) {
        System.out.println("Error inesperado: " + e.getMessage());
    }
}

    //         MODIFICACION DE ORDENES (Regla 2)
    public static void modificarOrden() {
    try {
        System.out.println("MODIFICAR ORDEN");
        System.out.print("Ingrese el ID de la orden a modificar: ");
        String idOrden = sc.nextLine().trim();

        int idx = buscarOrdenPorId(idOrden);

        if (idx == -1) {
            System.out.println("Error: no se encontro ninguna orden con ese ID.");
            return;
        }

        Orden orden = ordenes[idx];

        // Regla 2: solo se puede modificar libremente si esta "Pendiente"
        if (!orden.getEstado().equalsIgnoreCase("Pendiente")) {
            System.out.println("Operacion denegada: la orden esta en estado '" + orden.getEstado() + "'.");
            System.out.println("Solo se pueden modificar ordenes en estado 'Pendiente'.");
            System.out.println("Si la orden esta 'Asignada', primero debe Desasignar el pedido.");
            return;
        }

        System.out.println("Orden encontrada: " + orden.getDescripcion());
        System.out.println("Deje en blanco los campos que NO desea modificar.");

        System.out.print("Nueva descripcion [" + orden.getDescripcion() + "]: ");
        String descripcion = sc.nextLine().trim();
        if (!descripcion.isEmpty()) orden.setDescripcion(descripcion);

        System.out.print("Nuevo peso en kg [" + orden.getPesoPaquete() + "]: ");
        String pesoStr = sc.nextLine().trim();
        if (!pesoStr.isEmpty()) {
            double nuevoPeso = Double.parseDouble(pesoStr);
            orden.setPesoPaquete(nuevoPeso);
        }

        System.out.print("Nuevo destino X [" + orden.getDestinoX() + "]: ");
        String xStr = sc.nextLine().trim();
        if (!xStr.isEmpty()) {
            int nuevoX = Integer.parseInt(xStr);
            if (nuevoX < 0 || nuevoX > 9) {
                System.out.println("Error: destino X debe estar entre 0 y 9. No se modifico.");
            } else {
                orden.setDestinoX(nuevoX);
            }
        }

        System.out.print("Nuevo destino Y [" + orden.getDestinoY() + "]: ");
        String yStr = sc.nextLine().trim();
        if (!yStr.isEmpty()) {
            int nuevoY = Integer.parseInt(yStr);
            if (nuevoY < 0 || nuevoY > 9) {
                System.out.println("Error: destino Y debe estar entre 0 y 9. No se modifico.");
            } else {
                orden.setDestinoY(nuevoY);
            }
        }

        System.out.println("Orden modificada correctamente.");

    } catch (NumberFormatException e) {
        System.out.println("Error: debe ingresar valores numericos validos.");
    } catch (Exception e) {
        System.out.println("Error inesperado: " + e.getMessage());
    }
}

    //         DESASIGNACION DE PEDIDOS (Regla 2)
    public static void desasignarPedido() {
    try {
        System.out.println("\n===== DESASIGNAR PEDIDO =====");
        System.out.print("Ingrese el ID de la orden a desasignar: ");
        String idOrden = sc.nextLine().trim();

        int idx = buscarOrdenPorId(idOrden);

        if (idx == -1) {
            System.out.println("Error: no se encontro ninguna orden con ese ID.");
            return;
        }

        Orden orden = ordenes[idx];

        // Solo se puede desasignar si esta "Asignada" (no si ya esta "En Ruta")
        if (!orden.getEstado().equalsIgnoreCase("Asignada")) {
            if (orden.getEstado().equalsIgnoreCase("En Ruta")) {
                System.out.println("Operacion denegada: el vehiculo y su carga estan en transito operativo.");
            } else {
                System.out.println("Error: solo se pueden desasignar ordenes en estado 'Asignada'.");
                System.out.println("Estado actual de la orden: " + orden.getEstado());
            }
            return;
        }

        String vehiculoPrevio = orden.getIdVehiculoAsignado();

        // Liberar el vehiculo si ya no tiene mas ordenes asignadas, lo dejamos disponible
        orden.setIdVehiculoAsignado(null);
        orden.setEstado("Pendiente");

        // Si el vehiculo no tiene mas ordenes "Asignada"/"En Ruta" pendientes de entrega, lo regresamos a Disponible
        int idxVehiculo = buscarVehiculoPorId(vehiculoPrevio);
        if (idxVehiculo != -1) {
            boolean tieneOtrasOrdenes = false;
            for (int i = 0; i < ordenes.length; i++) {
                if (ordenes[i] != null
                        && ordenes[i].getIdVehiculoAsignado() != null
                        && ordenes[i].getIdVehiculoAsignado().equalsIgnoreCase(vehiculoPrevio)) {
                    tieneOtrasOrdenes = true;
                    break;
                }
            }
            if (!tieneOtrasOrdenes && vehiculos[idxVehiculo].getEstado().equalsIgnoreCase("Asignada")) {
                vehiculos[idxVehiculo].setEstado("Disponible");
            }
        }

        System.out.println("Pedido desasignado correctamente. La orden volvio a estado 'Pendiente'.");

    } catch (Exception e) {
        System.out.println("Error inesperado: " + e.getMessage());
    }
}

    public static void menuSimulacion() {
    System.out.println("SIMULACION EN EL MAPA");

    Orden ordenSeleccionada = null;

    for (int i = 0; i < ordenes.length; i++) {
        if (ordenes[i] != null && ordenes[i].getEstado().equalsIgnoreCase("Pendiente")) {
            ordenSeleccionada = ordenes[i];
            break;
        }
    }

    if (ordenSeleccionada == null) {
        System.out.println("No hay ordenes pendientes para simular.");
        return;
    }

    Vehiculo vehiculoSeleccionado = null;

    for (int i = 0; i < vehiculos.length; i++) {
        if (vehiculos[i] != null
                && vehiculos[i].getEstado().equalsIgnoreCase("Disponible")
                && vehiculos[i].getCapacidadMax() >= ordenSeleccionada.getPesoPaquete()) {

            vehiculoSeleccionado = vehiculos[i];
            break;
        }
    }

    if (vehiculoSeleccionado == null) {
        System.out.println("No hay vehiculo disponible con capacidad suficiente.");
        return;
    }

    ordenSeleccionada.setIdVehiculoAsignado(vehiculoSeleccionado.getId());
    ordenSeleccionada.setEstado("En Ruta");
    vehiculoSeleccionado.setEstado("En Ruta");

    int x = 0;
    int y = 0;

    System.out.println("Orden asignada: " + ordenSeleccionada.getIdOrden());
    System.out.println("Vehiculo asignado: " + vehiculoSeleccionado.getId());
    System.out.println("Destino: (" + ordenSeleccionada.getDestinoX() + ", " + ordenSeleccionada.getDestinoY() + ")");

    while (x != ordenSeleccionada.getDestinoX() || y != ordenSeleccionada.getDestinoY()) {

        if (x < ordenSeleccionada.getDestinoX()) {
            x++;
        } else if (x > ordenSeleccionada.getDestinoX()) {
            x--;
        }

        if (y < ordenSeleccionada.getDestinoY()) {
            y++;
        } else if (y > ordenSeleccionada.getDestinoY()) {
            y--;
        }

        mostrarMapa(x, y, ordenSeleccionada.getDestinoX(), ordenSeleccionada.getDestinoY());
    }

    System.out.println("Entrega completada correctamente.");

    ordenSeleccionada.setEstado("Entregada");
    vehiculoSeleccionado.setEstado("Disponible");
}
    
    public static void mostrarMapa(int vehiculoX, int vehiculoY, int destinoX, int destinoY) {
    int filas = 10;
    int columnas = 10;

    System.out.println();

    for (int y = 0; y < filas; y++) {
        for (int x = 0; x < columnas; x++) {

            if (x == vehiculoX && y == vehiculoY) {
                System.out.print(" V ");
            } else if (x == destinoX && y == destinoY) {
                System.out.print(" D ");
            } else {
                System.out.print(" . ");
            }
        }
        System.out.println();
    }

}

    public static void menuConsultas() {
    int opcion;

    do {
        System.out.println("CONSULTAS Y REPORTES");
        System.out.println("1. Vehiculos disponibles");
        System.out.println("2. Vehiculos en ruta");
        System.out.println("3. Ordenes pendientes");
        System.out.println("4. Ordenes entregadas");
        System.out.println("0. Regresar");
        System.out.print("Seleccione una opcion: ");

        try {
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    mostrarVehiculosPorEstado("Disponible");
                    break;
                case 2:
                    mostrarVehiculosPorEstado("En Ruta");
                    break;
                case 3:
                    mostrarOrdenesPorEstado("Pendiente");
                    break;
                case 4:
                    mostrarOrdenesPorEstado("Entregada");
                    break;
                case 0:
                    System.out.println("Regresando al menu principal...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }

        } catch (Exception e) {
            System.out.println("Debe ingresar un numero.");
            opcion = -1;
        }

    } while (opcion != 0);
}

    public static void mostrarVehiculosPorEstado(String estadoBuscado) {
    System.out.println("VEHICULOS " + estadoBuscado.toUpperCase() + "");

    boolean encontrado = false;

    for (int i = 0; i < vehiculos.length; i++) {
        if (vehiculos[i] != null && vehiculos[i].getEstado().equalsIgnoreCase(estadoBuscado)) {
            encontrado = true;
            System.out.println("ID       : " + vehiculos[i].getId());
            System.out.println("Marca    : " + vehiculos[i].getMarca());
            System.out.println("Modelo   : " + vehiculos[i].getModelo());
            System.out.println("Capacidad: " + vehiculos[i].getCapacidadMax() + " kg");
            System.out.println("Estado   : " + vehiculos[i].getEstado());
        }
    }

    if (!encontrado) {
        System.out.println("No hay vehiculos con estado: " + estadoBuscado);
    }
}

    public static void mostrarOrdenesPorEstado(String estadoBuscado) {
    System.out.println("ORDENES " + estadoBuscado.toUpperCase() + "");

    boolean encontrado = false;

    for (int i = 0; i < ordenes.length; i++) {
        if (ordenes[i] != null && ordenes[i].getEstado().equalsIgnoreCase(estadoBuscado)) {
            encontrado = true;
            System.out.println("ID Orden   : " + ordenes[i].getIdOrden());
            System.out.println("Descripcion: " + ordenes[i].getDescripcion());
            System.out.println("Peso       : " + ordenes[i].getPesoPaquete() + " kg");
            System.out.println("Destino    : (" + ordenes[i].getDestinoX() + ", " + ordenes[i].getDestinoY() + ")");
            System.out.println("Estado     : " + ordenes[i].getEstado());
            System.out.println("Vehiculo   : " + ordenes[i].getIdVehiculoAsignado());
        }
    }

    if (!encontrado) {
        System.out.println("No hay ordenes con estado: " + estadoBuscado);
    }
}

    public static void menuReportesHTML() {
    int opcion;

    do {
        System.out.println("REPORTES HTML");
        System.out.println("1. Catalogo de flota operativa");
        System.out.println("2. Manifiesto de carga y envios");
        System.out.println("0. Regresar");
        System.out.print("Seleccione una opcion: ");

        try {
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    generarReporteFlotaHTML();
                    break;
                case 2:
                    generarReporteManifiestoHTML();
                    break;
                case 0:
                    System.out.println("Regresando al menu principal...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }

        } catch (Exception e) {
            System.out.println("Debe ingresar un numero.");
            opcion = -1;
        }

    } while (opcion != 0);
  }
    
    public static String obtenerColorEstado(String estado) {
    if (estado.equalsIgnoreCase("Disponible")) {
        return "bg-signal";
    } else if (estado.equalsIgnoreCase("En Ruta")) {
        return "bg-route";
    } else {
        return "bg-rust";
    }
}
    
    public static void generarReporteFlotaHTML() {
    try {
        int total = 0, disponibles = 0, enRuta = 0, mantenimiento = 0;

        for (int i = 0; i < vehiculos.length; i++) {
            if (vehiculos[i] != null) {
                total++;
                if (vehiculos[i].getEstado().equalsIgnoreCase("Disponible")) disponibles++;
                else if (vehiculos[i].getEstado().equalsIgnoreCase("En Ruta")) enRuta++;
                else if (vehiculos[i].getEstado().equalsIgnoreCase("Mantenimiento")) mantenimiento++;
            }
        }

        FileWriter archivo = new FileWriter("reporte_flota.html");

        archivo.write("<!DOCTYPE html><html lang='es'><head>");
        archivo.write("<meta charset='UTF-8'>");
        archivo.write("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        archivo.write("<title>PonchiTruck - Catalogo de Flota Operativa</title>");
        archivo.write("<script src='https://cdn.tailwindcss.com'></script>");
        archivo.write("<script>tailwind.config={theme:{extend:{colors:{coal:'#1C1B19',steel:'#2E3138',concrete:'#ECE8DE',caution:'#F5B700',rust:'#C1440E',signal:'#2F9E44',route:'#1D6FB8',paperline:'#D9D4C5'}}}}</script>");
        archivo.write("</head><body class='bg-concrete text-coal'>");

        archivo.write("<div class='h-2 w-full' style='background:repeating-linear-gradient(45deg,#1C1B19 0 10px,#F5B700 10px 20px)'></div>");
        archivo.write("<header class='bg-coal text-concrete p-8'>");
        archivo.write("<h1 class='text-3xl font-bold uppercase'>Ponchi<span class='text-caution'>Truck</span></h1>");
        archivo.write("<p>Catalogo de Flota Operativa</p>");

        archivo.write("<div class='mt-4 flex gap-3 flex-wrap'>");
        archivo.write("<div class='bg-steel p-3 rounded'>Total flota: <b>" + total + "</b></div>");
        archivo.write("<div class='bg-steel p-3 rounded'>Disponibles: <b>" + disponibles + "</b></div>");
        archivo.write("<div class='bg-steel p-3 rounded'>En ruta: <b>" + enRuta + "</b></div>");
        archivo.write("<div class='bg-steel p-3 rounded'>Mantenimiento: <b>" + mantenimiento + "</b></div>");
        archivo.write("</div>");

        archivo.write("</header>");

        archivo.write("<main class='p-8 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-7'>");

        for (int i = 0; i < vehiculos.length; i++) {
            if (vehiculos[i] != null) {
                String color = obtenerColorEstado(vehiculos[i].getEstado());

                archivo.write("<article class='rounded-lg bg-white ring-1 ring-paperline overflow-hidden shadow'>");
                archivo.write("<div class='relative aspect-[4/3] bg-steel'>");
                archivo.write("<img src='" + vehiculos[i].getImagen() + "' class='w-full h-full object-cover'>");
                archivo.write("<span class='absolute top-3 right-3 rounded-full " + color + " px-3 py-1 text-xs font-bold text-white'>" + vehiculos[i].getEstado() + "</span>");
                archivo.write("<div class='absolute left-4 bottom-3 bg-coal text-caution px-3 py-1 font-bold'>" + vehiculos[i].getId() + "</div>");
                archivo.write("</div>");
                archivo.write("<div class='p-5'>");
                archivo.write("<h3 class='text-xl font-bold uppercase'>" + vehiculos[i].getMarca() + "</h3>");
                archivo.write("<p>" + vehiculos[i].getModelo() + "</p>");
                archivo.write("<p class='mt-4 border-t pt-3'>Capacidad max: <b>" + vehiculos[i].getCapacidadMax() + " kg</b></p>");
                archivo.write("</div>");
                archivo.write("</article>");
            }
        }

        archivo.write("</main></body></html>");
        archivo.close();

        System.out.println("Reporte reporte_flota.html generado correctamente.");

    } catch (Exception e) {
        System.out.println("Error al generar reporte de flota: " + e.getMessage());
    }
}
  public static void generarReporteManifiestoHTML() {
    try {
        FileWriter archivo = new FileWriter("reporte_manifiesto_envios.html");

        archivo.write("<!DOCTYPE html><html lang='es'><head>");
        archivo.write("<meta charset='UTF-8'>");
        archivo.write("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        archivo.write("<title>PonchiTruck - Manifiesto de Carga y Envios</title>");
        archivo.write("<script src='https://cdn.tailwindcss.com'></script>");
        archivo.write("<script>tailwind.config={theme:{extend:{colors:{coal:'#1C1B19',steel:'#2E3138',concrete:'#ECE8DE',caution:'#F5B700',rust:'#C1440E',signal:'#2F9E44',route:'#1D6FB8',paperline:'#D9D4C5'}}}}</script>");
        archivo.write("</head><body class='bg-concrete text-coal'>");

        archivo.write("<div class='h-2 w-full' style='background:repeating-linear-gradient(45deg,#1C1B19 0 10px,#F5B700 10px 20px)'></div>");
        archivo.write("<header class='bg-coal text-concrete p-8'>");
        archivo.write("<h1 class='text-3xl font-bold uppercase'>Ponchi<span class='text-caution'>Truck</span></h1>");
        archivo.write("<p>Manifiesto de Carga y Envios Combinados</p>");
        archivo.write("</header>");

        archivo.write("<main class='max-w-5xl mx-auto p-8 space-y-6'>");

        for (int i = 0; i < vehiculos.length; i++) {
            if (vehiculos[i] != null && !vehiculos[i].getEstado().equalsIgnoreCase("Mantenimiento")) {

                double cargaActual = 0;
                int cantidadOrdenes = 0;

                for (int j = 0; j < ordenes.length; j++) {
                    if (ordenes[j] != null
                            && ordenes[j].getIdVehiculoAsignado() != null
                            && ordenes[j].getIdVehiculoAsignado().equalsIgnoreCase(vehiculos[i].getId())) {
                        cargaActual += ordenes[j].getPesoPaquete();
                        cantidadOrdenes++;
                    }
                }

                double disponible = vehiculos[i].getCapacidadMax() - cargaActual;
                double porcentaje = 0;

                if (vehiculos[i].getCapacidadMax() > 0) {
                    porcentaje = (cargaActual / vehiculos[i].getCapacidadMax()) * 100;
                }

                String color = obtenerColorEstado(vehiculos[i].getEstado());

                archivo.write("<section class='rounded-lg bg-white ring-1 ring-paperline overflow-hidden'>");

                archivo.write("<div class='flex flex-wrap items-center justify-between gap-5 p-5'>");
                archivo.write("<div class='flex items-center gap-3'>");
                archivo.write("<span class='font-mono text-xs font-bold bg-coal text-caution px-2 py-1 rounded-sm'>" + vehiculos[i].getId() + "</span>");
                archivo.write("<div>");
                archivo.write("<h3 class='font-bold uppercase'>" + vehiculos[i].getMarca() + "</h3>");
                archivo.write("<p class='text-sm'>" + vehiculos[i].getModelo() + "</p>");
                archivo.write("</div>");
                archivo.write("<span class='rounded-full " + color + " px-3 py-1 text-xs font-bold uppercase text-white'>" + vehiculos[i].getEstado() + "</span>");
                archivo.write("</div>");
                archivo.write("</div>");

                if (cantidadOrdenes == 0) {
                    archivo.write("<p class='border-t border-paperline px-5 py-4 text-sm italic'>Sin pedidos asignados.</p>");
                } else {
                    archivo.write("<table class='w-full text-sm border-t border-paperline'>");
                    archivo.write("<thead><tr>");
                    archivo.write("<th class='px-5 py-2 text-left'>ID Orden</th>");
                    archivo.write("<th class='px-5 py-2 text-left'>Descripcion</th>");
                    archivo.write("<th class='px-5 py-2 text-left'>Peso</th>");
                    archivo.write("<th class='px-5 py-2 text-left'>Destino (X,Y)</th>");
                    archivo.write("</tr></thead><tbody>");

                    for (int j = 0; j < ordenes.length; j++) {
                        if (ordenes[j] != null
                                && ordenes[j].getIdVehiculoAsignado() != null
                                && ordenes[j].getIdVehiculoAsignado().equalsIgnoreCase(vehiculos[i].getId())) {

                            archivo.write("<tr class='border-t border-paperline'>");
                            archivo.write("<td class='px-5 py-2'>" + ordenes[j].getIdOrden() + "</td>");
                            archivo.write("<td class='px-5 py-2'>" + ordenes[j].getDescripcion() + "</td>");
                            archivo.write("<td class='px-5 py-2'>" + ordenes[j].getPesoPaquete() + " kg</td>");
                            archivo.write("<td class='px-5 py-2'>(" + ordenes[j].getDestinoX() + ", " + ordenes[j].getDestinoY() + ")</td>");
                            archivo.write("</tr>");
                        }
                    }

                    archivo.write("</tbody></table>");
                }

                archivo.write("<div class='flex flex-wrap gap-6 p-5 border-t border-paperline bg-concrete/20 justify-end'>");
                archivo.write("<div><p>Capacidad max.</p><b>" + vehiculos[i].getCapacidadMax() + " kg</b></div>");
                archivo.write("<div><p>Carga actual</p><b>" + cargaActual + " kg</b></div>");
                archivo.write("<div><p>Disponible</p><b>" + disponible + " kg</b></div>");
                archivo.write("<div><p>Ocupacion</p><b>" + String.format("%.1f", porcentaje) + "%</b>");
                archivo.write("<div class='w-24 h-2 bg-paperline rounded'><div class='h-2 bg-caution rounded' style='width:" + porcentaje + "%'></div></div>");
                archivo.write("</div>");
                archivo.write("</div>");

                archivo.write("</section>");
            }
        }

        archivo.write("</main></body></html>");
        archivo.close();

        System.out.println("Reporte reporte_manifiesto_envios.html generado correctamente.");

    } catch (Exception e) {
        System.out.println("Error al generar manifiesto: " + e.getMessage());
    }
  } 
}