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
    public static Orden ordenSeleccionadaSimulacion = null;

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int opcion;

        do {
            System.out.println("\n=================================");
            System.out.println("      PONCHITRUCK CORE");
            System.out.println("=================================");
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

    // =========================================================
    //                  MENÚ CRUD VEHÍCULOS
    // =========================================================
    public static void menuVehiculos() {
        int opcion;

        do {
            System.out.println("\n===== CRUD DE VEHICULOS =====");
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
            } finally{
                System.out.println("Operacion de menu finalizada.");
            }

        } while (opcion != 0);
    }

    // =========================================================
    //              1. CARGA MASIVA DESDE CSV
    // =========================================================
    public static void cargaMasivaCSV() {
        System.out.println("\n===== CARGA MASIVA DESDE vehiculos.csv =====");
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
            System.out.println("----------------------------------------");
            System.out.println("Carga finalizada.");
            System.out.println("Vehiculos registrados : " + registrados);
            System.out.println("Registros omitidos    : " + omitidos);
            System.out.println("----------------------------------------");

        } catch (java.io.FileNotFoundException e) {
            System.out.println("Error: no se encontro el archivo en la ruta indicada.");
        } catch (NumberFormatException e) {
            System.out.println("Error: la capacidad maxima debe ser un numero valido.");
        } catch (Exception e) {
            System.out.println("Error inesperado al leer el archivo: " + e.getMessage());
        } finally{
            System.out.println("Operacion finalizada.");
        }
    }

    // =========================================================
    //              2. INGRESO MANUAL (ya existía)
    // =========================================================
    public static void ingresarVehiculoManual() {
        try {
            System.out.println("\n===== INGRESO MANUAL DE VEHICULO =====");

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
        } finally {
            System.out.println("Operacion finalizada.");
        }
    }

    // =========================================================
    //              3. MODIFICAR VEHÍCULO
    // =========================================================
    public static void modificarVehiculo() {
        try {
            System.out.println("\n===== MODIFICAR VEHICULO =====");
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
        } finally{
            System.out.println("Operacion finalizada.");
        }
    }

    // =========================================================
    //              4. ELIMINAR VEHÍCULO (con desplazamiento)
    // =========================================================
    public static void eliminarVehiculo() {
        try {
            System.out.println("\n===== ELIMINAR VEHICULO =====");
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
        } finally{
            System.out.println("Operacion finalizada.");
        }
    }

    // =========================================================
    //              UTILIDADES
    // =========================================================
    public static int buscarVehiculoPorId(String id) {
        for (int i = 0; i < vehiculos.length; i++) {
            if (vehiculos[i] != null && vehiculos[i].getId().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }

    public static void mostrarVehiculos() {
        System.out.println("\n===== LISTADO DE VEHICULOS =====");
        boolean hayVehiculos = false;

        for (int i = 0; i < vehiculos.length; i++) {
            if (vehiculos[i] != null) {
                hayVehiculos = true;
                System.out.println("--------------------------------");
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

    // =========================================================
    //         MÓDULOS
    // =========================================================

    public static void menuOrdenes() {
    int opcion;

    do {
        System.out.println("\n===== ORDENES DE ENVIO =====");
        System.out.println("1. Crear orden");
        System.out.println("2. Mostrar ordenes");
        System.out.println("3. Asignar orden a vehiculo");
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
                    asignarOrdenAVehiculo();
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
        } finally{
            System.out.println("Operacion de menu finalizada.");
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
    } finally {
        System.out.println("Operacion finalizada.");
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
    System.out.println("\n===== LISTADO DE ORDENES =====");

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
            String vehiculo = ordenes[i].getIdVehiculoAsignado();

        if (vehiculo == null) {
            vehiculo = "Sin asignar";
    }

System.out.println("Vehiculo   : " + vehiculo);
        }
    }

    if (!hayOrdenes) {
        System.out.println("No hay ordenes registradas.");
    }
}

    public static void asignarOrdenAVehiculo() {
    try {
        System.out.println("\n===== ASIGNAR ORDEN A VEHICULO =====");

        System.out.print("Ingrese ID de la orden: ");
        String idOrden = sc.nextLine();

        int indiceOrden = buscarOrdenPorId(idOrden);

        if (indiceOrden == -1) {
            System.out.println("No existe una orden con ese ID.");
            return;
        }

        Orden orden = ordenes[indiceOrden];

        if (!orden.getEstado().equalsIgnoreCase("Pendiente")) {
            System.out.println("Solo se pueden asignar ordenes pendientes.");
            return;
        }

        System.out.print("Ingrese ID del vehiculo: ");
        String idVehiculo = sc.nextLine();

        int indiceVehiculo = buscarVehiculoPorId(idVehiculo);

        if (indiceVehiculo == -1) {
            System.out.println("No existe un vehiculo con ese ID.");
            return;
        }

        Vehiculo vehiculo = vehiculos[indiceVehiculo];

        if (!vehiculo.getEstado().equalsIgnoreCase("Disponible")) {
            System.out.println("El vehiculo no esta disponible.");
            return;
        }

        if (vehiculo.getCapacidadMax() < orden.getPesoPaquete()) {
            System.out.println("El vehiculo no tiene capacidad suficiente.");
            return;
        }

        orden.setIdVehiculoAsignado(vehiculo.getId());
        orden.setEstado("Asignada");

        System.out.println("Orden asignada correctamente.");

    } catch (Exception e) {
        System.out.println("Error al asignar orden: " + e.getMessage());
    } finally {
        System.out.println("Operacion finalizada.");
    }
}
    
    public static void menuSimulacion() {
    int opcion;

    do {
        System.out.println("\n===== SIMULACION EN EL MAPA =====");
        System.out.println("1. Seleccionar orden");
        System.out.println("2. Iniciar / Reanudar simulacion");
        System.out.println("3. Cancelar seleccion");
        System.out.println("0. Regresar");
        System.out.print("Seleccione una opcion: ");

        try {
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    seleccionarOrdenSimulacion();
                    break;
                case 2:
                    iniciarSimulacionMapa();
                    break;
                case 3:
                    ordenSeleccionadaSimulacion = null;
                    System.out.println("Seleccion cancelada.");
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
        } finally{
            System.out.println("Operacion de menu finalizada.");
        }

    } while (opcion != 0);
}
    
    public static void seleccionarOrdenSimulacion() {
    System.out.println("\n===== SELECCIONAR ORDEN =====");

    boolean hayOrdenes = false;

    for (int i = 0; i < ordenes.length; i++) {
        if (ordenes[i] != null &&
            (ordenes[i].getEstado().equalsIgnoreCase("Asignada") ||
             ordenes[i].getEstado().equalsIgnoreCase("En Ruta"))) {

            hayOrdenes = true;
            System.out.println("--------------------------------");
            System.out.println("ID Orden : " + ordenes[i].getIdOrden());
            System.out.println("Estado   : " + ordenes[i].getEstado());
            System.out.println("Vehiculo : " + ordenes[i].getIdVehiculoAsignado());
            System.out.println("Destino  : (" + ordenes[i].getDestinoX() + ", " + ordenes[i].getDestinoY() + ")");
        }
    }

    if (!hayOrdenes) {
        System.out.println("No hay ordenes Asignadas o En Ruta.");
        return;
    }

    System.out.print("Ingrese el ID de la orden: ");
    String idOrden = sc.nextLine();

    int indiceOrden = buscarOrdenPorId(idOrden);

    if (indiceOrden == -1) {
        System.out.println("No existe una orden con ese ID.");
        return;
    }

    Orden orden = ordenes[indiceOrden];

    if (!orden.getEstado().equalsIgnoreCase("Asignada") &&
        !orden.getEstado().equalsIgnoreCase("En Ruta")) {
        System.out.println("Solo puede seleccionar ordenes en estado Asignada o En Ruta.");
        return;
    }

    if (orden.getIdVehiculoAsignado() == null) {
        System.out.println("La orden no tiene vehiculo asignado.");
        return;
    }

    int indiceVehiculo = buscarVehiculoPorId(orden.getIdVehiculoAsignado());

    if (indiceVehiculo == -1) {
        System.out.println("El vehiculo asignado no existe.");
        return;
    }

    Vehiculo vehiculo = vehiculos[indiceVehiculo];

    for (int i = 0; i < ordenes.length; i++) {
        if (ordenes[i] != null &&
            ordenes[i] != orden &&
            ordenes[i].getIdVehiculoAsignado() != null &&
            ordenes[i].getIdVehiculoAsignado().equalsIgnoreCase(vehiculo.getId()) &&
            ordenes[i].getEstado().equalsIgnoreCase("En Ruta")) {

            System.out.println("Este vehiculo ya tiene otra orden en ruta.");
            return;
        }
    }

    ordenSeleccionadaSimulacion = orden;
    System.out.println("Orden seleccionada correctamente.");
}
    
    public static void iniciarSimulacionMapa() {
    if (ordenSeleccionadaSimulacion == null) {
        System.out.println("Primero debe seleccionar una orden.");
        return;
    }

    Orden orden = ordenSeleccionadaSimulacion;
    int indiceVehiculo = buscarVehiculoPorId(orden.getIdVehiculoAsignado());

    if (indiceVehiculo == -1) {
        System.out.println("No se encontro el vehiculo asignado.");
        return;
    }

    Vehiculo vehiculo = vehiculos[indiceVehiculo];

    orden.setEstado("En Ruta");
    vehiculo.setEstado("En Ruta");

    System.out.println("\nSimulacion iniciada.");
    System.out.println("Presione ENTER para avanzar un paso.");
    System.out.println("Escriba SALIR para pausar la simulacion.");

    while (vehiculo.getActualX() != orden.getDestinoX() ||
           vehiculo.getActualY() != orden.getDestinoY()) {

        mostrarMapa(vehiculo.getActualX(), vehiculo.getActualY(), orden.getDestinoX(), orden.getDestinoY());

        System.out.print("Comando: ");
        String comando = sc.nextLine();

        if (comando.equalsIgnoreCase("SALIR")) {
            System.out.println("Simulacion pausada. Podra reanudarse despues.");
            return;
        }

        if (!comando.isEmpty()) {
            System.out.println("Comando invalido. Presione ENTER o escriba SALIR.");
            continue;
        }

        if (vehiculo.getActualX() < orden.getDestinoX()) {
            vehiculo.setActualX(vehiculo.getActualX() + 1);
        } else if (vehiculo.getActualX() > orden.getDestinoX()) {
            vehiculo.setActualX(vehiculo.getActualX() - 1);
        } else if (vehiculo.getActualY() < orden.getDestinoY()) {
            vehiculo.setActualY(vehiculo.getActualY() + 1);
        } else if (vehiculo.getActualY() > orden.getDestinoY()) {
            vehiculo.setActualY(vehiculo.getActualY() - 1);
        }
    }

    mostrarMapa(vehiculo.getActualX(), vehiculo.getActualY(), orden.getDestinoX(), orden.getDestinoY());

    System.out.println("Entrega completada correctamente.");

    orden.setEstado("Entregada");
    vehiculo.setEstado("Disponible");
    ordenSeleccionadaSimulacion = null;
    vehiculo.setActualX(0);
    vehiculo.setActualY(0);
}
    
public static void mostrarMapa(int vehiculoX, int vehiculoY, int destinoX, int destinoY) {
    int filas = 10;
    int columnas = 10;

    System.out.println();

    for (int y = 0; y < filas; y++) {
        for (int x = 0; x < columnas; x++) {

            if (x == 0 && y == 0 && x == vehiculoX && y == vehiculoY) {
                System.out.print(" V ");
            } else if (x == vehiculoX && y == vehiculoY) {
                System.out.print(" V ");
            } else if (x == 0 && y == 0) {
                System.out.print(" H ");
            } else if (x == destinoX && y == destinoY) {
                System.out.print(" X ");
            } else {
                System.out.print(" . ");
            }
        }
        System.out.println();
    }

    System.out.println("------------------------------");
}

    public static void menuConsultas() {
    int opcion;

    do {
        System.out.println("\n===== CONSULTAS Y REPORTES =====");
        System.out.println("1. Busqueda detallada de vehiculo por ID");
        System.out.println("2. Vista de flota por defecto");
        System.out.println("3. Vista de flota ordenada por capacidad");
        System.out.println("4. Vista de flota ordenada por marca");
        System.out.println("5. Historial de ordenes agrupado por estado");
        System.out.println("0. Regresar");
        System.out.print("Seleccione una opcion: ");

        try {
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    buscarVehiculoDetallado();
                    break;
                case 2:
                    vistaFlotaDefecto();
                    break;
                case 3:
                    vistaFlotaPorCapacidad();
                    break;
                case 4:
                    vistaFlotaPorMarca();
                    break;
                case 5:
                    historialOrdenesAgrupado();
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
        } finally {
            System.out.println("Operacion de consulta finalizada.");
        }

    } while (opcion != 0);
}
    public static void buscarVehiculoDetallado() {
    try {
        System.out.println("\n===== BUSQUEDA DETALLADA DE VEHICULO =====");
        System.out.print("Ingrese ID del vehiculo: ");
        String id = sc.nextLine();

        int indice = buscarVehiculoPorId(id);

        if (indice == -1) {
            System.out.println("No se encontro vehiculo con ese ID.");
            return;
        }

        Vehiculo v = vehiculos[indice];

        System.out.println("\n===== FICHA TECNICA =====");
        System.out.println("ID              : " + v.getId());
        System.out.println("Marca           : " + v.getMarca());
        System.out.println("Modelo          : " + v.getModelo());
        System.out.println("Capacidad Max   : " + v.getCapacidadMax() + " kg");
        System.out.println("Estado          : " + v.getEstado());
        System.out.println("Imagen          : " + v.getImagen());
        System.out.println("Posicion actual : (" + v.getActualX() + ", " + v.getActualY() + ")");

    } catch (Exception e) {
        System.out.println("Error en busqueda: " + e.getMessage());
    } finally {
        System.out.println("Busqueda finalizada.");
    }
}
    public static void vistaFlotaDefecto() {
    System.out.println("\n===== VISTA DE FLOTA POR DEFECTO =====");
    imprimirTablaVehiculos(vehiculos);
}
    public static void vistaFlotaPorCapacidad() {
    Vehiculo[] copia = copiarVehiculos();

    for (int i = 0; i < copia.length - 1; i++) {
        for (int j = i + 1; j < copia.length; j++) {
            if (copia[i] != null && copia[j] != null &&
                copia[i].getCapacidadMax() > copia[j].getCapacidadMax()) {

                Vehiculo temp = copia[i];
                copia[i] = copia[j];
                copia[j] = temp;
            }
        }
    }

    System.out.println("\n===== FLOTA ORDENADA POR CAPACIDAD =====");
    imprimirTablaVehiculos(copia);
}
    public static void vistaFlotaPorMarca() {
    Vehiculo[] copia = copiarVehiculos();

    for (int i = 0; i < copia.length - 1; i++) {
        for (int j = i + 1; j < copia.length; j++) {
            if (copia[i] != null && copia[j] != null &&
                copia[i].getMarca().compareToIgnoreCase(copia[j].getMarca()) > 0) {

                Vehiculo temp = copia[i];
                copia[i] = copia[j];
                copia[j] = temp;
            }
        }
    }

    System.out.println("\n===== FLOTA ORDENADA POR MARCA =====");
    imprimirTablaVehiculos(copia);
}
    public static Vehiculo[] copiarVehiculos() {
    Vehiculo[] copia = new Vehiculo[vehiculos.length];

    for (int i = 0; i < vehiculos.length; i++) {
        copia[i] = vehiculos[i];
    }

    return copia;
}
    public static void imprimirTablaVehiculos(Vehiculo[] lista) {
    boolean hayVehiculos = false;

    System.out.println("--------------------------------------------------------------------------------");
    System.out.printf("%-10s %-15s %-20s %-15s %-15s\n", "ID", "Marca", "Modelo", "Capacidad", "Estado");
    System.out.println("--------------------------------------------------------------------------------");

    for (int i = 0; i < lista.length; i++) {
        if (lista[i] != null) {
            hayVehiculos = true;

            System.out.printf("%-10s %-15s %-20s %-15.2f %-15s\n",
                    lista[i].getId(),
                    lista[i].getMarca(),
                    lista[i].getModelo(),
                    lista[i].getCapacidadMax(),
                    lista[i].getEstado());
        }
    }

    System.out.println("--------------------------------------------------------------------------------");

    if (!hayVehiculos) {
        System.out.println("No hay vehiculos registrados.");
    }
}
    public static void historialOrdenesAgrupado() {
    System.out.println("\n===== HISTORIAL DE ORDENES AGRUPADO =====");

    imprimirOrdenesPorEstado("Pendiente");
    imprimirOrdenesPorEstado("Asignada");
    imprimirOrdenesPorEstado("En Ruta");
    imprimirOrdenesPorEstado("Entregada");
}
    public static void imprimirOrdenesPorEstado(String estadoBuscado) {
    boolean encontrado = false;

    System.out.println("\n--- ORDENES " + estadoBuscado.toUpperCase() + " ---");
    System.out.println("------------------------------------------------------------------------------------------------");
    System.out.printf("%-12s %-25s %-12s %-15s %-15s\n", "ID Orden", "Descripcion", "Peso", "Destino", "Vehiculo");
    System.out.println("------------------------------------------------------------------------------------------------");

    for (int i = 0; i < ordenes.length; i++) {
        if (ordenes[i] != null && ordenes[i].getEstado().equalsIgnoreCase(estadoBuscado)) {
            encontrado = true;

            String vehiculo = ordenes[i].getIdVehiculoAsignado();

            if (vehiculo == null) {
                vehiculo = "Sin asignar";
            }

            System.out.printf("%-12s %-25s %-12.2f %-15s %-15s\n",
                    ordenes[i].getIdOrden(),
                    ordenes[i].getDescripcion(),
                    ordenes[i].getPesoPaquete(),
                    "(" + ordenes[i].getDestinoX() + "," + ordenes[i].getDestinoY() + ")",
                    vehiculo);
        }
    }

    if (!encontrado) {
        System.out.println("No hay ordenes en estado " + estadoBuscado + ".");
    }

    System.out.println("------------------------------------------------------------------------------------------------");
}

    public static void menuReportesHTML() {
    int opcion;

    do {
        System.out.println("\n===== REPORTES HTML =====");
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
        } finally{
            System.out.println("Operacion de menu finalizada.");
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
    } finally {
        System.out.println("Operacion finalizada.");
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
    } finally {
        System.out.println("Operacion finalizada.");
    }
  } 
}
