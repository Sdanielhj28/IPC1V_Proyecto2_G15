package proyecto_1;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

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
            System.out.println("\n=================================");
            System.out.println("      PONCHITRUCK CORE");
            System.out.println("=================================");
            System.out.println("1. CRUD de Vehículos");
            System.out.println("2. Órdenes de Envío");
            System.out.println("3. Simulación en el Mapa");
            System.out.println("4. Consultas y Reportes");
            System.out.println("5. Reportes HTML");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

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
                        System.out.println("Opción inválida.");
                }

            } catch (Exception e) {
                System.out.println("Debe ingresar un número.");
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
            System.out.println("\n===== CRUD DE VEHÍCULOS =====");
            System.out.println("1. Carga masiva (vehiculos.csv)");
            System.out.println("2. Ingreso manual");
            System.out.println("3. Modificar vehículo");
            System.out.println("4. Eliminar vehículo");
            System.out.println("5. Mostrar vehículos");
            System.out.println("0. Regresar");
            System.out.print("Seleccione una opción: ");

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
                        System.out.println("Regresando al menú principal...");
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }

            } catch (Exception e) {
                System.out.println("Debe ingresar un número.");
                opcion = -1;
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
                    System.out.println("Línea con formato incorrecto, omitida: " + linea);
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
                    System.out.println("Arreglo lleno. No se pudieron registrar más vehículos.");
                    omitidos++;
                    break;
                }
            }

            br.close();
            System.out.println("----------------------------------------");
            System.out.println("Carga finalizada.");
            System.out.println("Vehículos registrados : " + registrados);
            System.out.println("Registros omitidos    : " + omitidos);
            System.out.println("----------------------------------------");

        } catch (java.io.FileNotFoundException e) {
            System.out.println("Error: no se encontró el archivo en la ruta indicada.");
        } catch (NumberFormatException e) {
            System.out.println("Error: la capacidad máxima debe ser un número válido.");
        } catch (Exception e) {
            System.out.println("Error inesperado al leer el archivo: " + e.getMessage());
        }
    }

    // =========================================================
    //              2. INGRESO MANUAL (ya existía)
    // =========================================================
    public static void ingresarVehiculoManual() {
        try {
            System.out.println("\n===== INGRESO MANUAL DE VEHÍCULO =====");

            System.out.print("ID: ");
            String id = sc.nextLine();

            if (buscarVehiculoPorId(id) != -1) {
                System.out.println("Error: ya existe un vehículo con ese ID.");
                return;
            }

            System.out.print("Marca: ");
            String marca = sc.nextLine();

            System.out.print("Modelo: ");
            String modelo = sc.nextLine();

            System.out.print("Capacidad máxima en kg: ");
            double capacidad = Double.parseDouble(sc.nextLine());

            System.out.print("Estado (Disponible / En Ruta / Mantenimiento): ");
            String estado = sc.nextLine();

            System.out.print("Ruta de imagen: ");
            String imagen = sc.nextLine();

            for (int i = 0; i < vehiculos.length; i++) {
                if (vehiculos[i] == null) {
                    vehiculos[i] = new Vehiculo(id, marca, modelo, capacidad, estado, imagen);
                    System.out.println("Vehículo registrado correctamente.");
                    return;
                }
            }

            System.out.println("No hay espacio disponible para más vehículos.");

        } catch (Exception e) {
            System.out.println("Error: dato inválido.");
        }
    }

    // =========================================================
    //              3. MODIFICAR VEHÍCULO
    // =========================================================
    public static void modificarVehiculo() {
        try {
            System.out.println("\n===== MODIFICAR VEHÍCULO =====");
            System.out.print("Ingrese el ID del vehículo a modificar: ");
            String id = sc.nextLine().trim();

            int indice = buscarVehiculoPorId(id);

            if (indice == -1) {
                System.out.println("Error: no se encontró ningún vehículo con ese ID.");
                return;
            }

            Vehiculo v = vehiculos[indice];

            // Bloqueo si está En Ruta
            if (v.getEstado().equalsIgnoreCase("En Ruta")) {
                System.out.println("Operación denegada: El vehículo y su carga están en tránsito operativo.");
                return;
            }

            System.out.println("Vehículo encontrado: " + v.getMarca() + " " + v.getModelo());
            System.out.println("Deje en blanco los campos que NO desea modificar.");

            System.out.print("Nueva marca [" + v.getMarca() + "]: ");
            String marca = sc.nextLine().trim();
            if (!marca.isEmpty()) v.setMarca(marca);

            System.out.print("Nuevo modelo [" + v.getModelo() + "]: ");
            String modelo = sc.nextLine().trim();
            if (!modelo.isEmpty()) v.setModelo(modelo);

            System.out.print("Nueva capacidad máxima kg [" + v.getCapacidadMax() + "]: ");
            String capStr = sc.nextLine().trim();
            if (!capStr.isEmpty()) {
                double nuevaCap = Double.parseDouble(capStr);
                v.setCapacidadMax(nuevaCap);
            }

            System.out.print("Nuevo estado (Disponible / En Ruta / Mantenimiento) [" + v.getEstado() + "]: ");
            String estado = sc.nextLine().trim();
            if (!estado.isEmpty()) v.setEstado(estado);

            System.out.println("Vehículo modificado correctamente.");

        } catch (NumberFormatException e) {
            System.out.println("Error: la capacidad debe ser un número válido.");
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    // =========================================================
    //              4. ELIMINAR VEHÍCULO (con desplazamiento)
    // =========================================================
    public static void eliminarVehiculo() {
        try {
            System.out.println("\n===== ELIMINAR VEHÍCULO =====");
            System.out.print("Ingrese el ID del vehículo a eliminar: ");
            String id = sc.nextLine().trim();

            int indice = buscarVehiculoPorId(id);

            if (indice == -1) {
                System.out.println("Error: no se encontró ningún vehículo con ese ID.");
                return;
            }

            // Bloqueo si está En Ruta
            if (vehiculos[indice].getEstado().equalsIgnoreCase("En Ruta")) {
                System.out.println("Operación denegada: El vehículo y su carga están en tránsito operativo.");
                return;
            }

            String nombreVehiculo = vehiculos[indice].getMarca() + " " + vehiculos[indice].getModelo();

            // Desplazar elementos hacia la izquierda para no dejar huecos
            for (int i = indice; i < vehiculos.length - 1; i++) {
                vehiculos[i] = vehiculos[i + 1];
            }
            vehiculos[vehiculos.length - 1] = null; // Última posición queda libre

            System.out.println("Vehículo '" + nombreVehiculo + "' eliminado correctamente.");

        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
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
        System.out.println("\n===== LISTADO DE VEHÍCULOS =====");
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
            System.out.println("No hay vehículos registrados.");
        }
    }

    // =========================================================
    //         MÓDULOS PENDIENTES (mañana)
    // =========================================================
    public static void menuOrdenes() {
        System.out.println("MÓDULO ÓRDENES - Próximamente");
    }

    public static void menuSimulacion() {
        System.out.println("MÓDULO SIMULACIÓN - Próximamente");
    }

    public static void menuConsultas() {
        System.out.println("MÓDULO CONSULTAS - Próximamente");
    }

    public static void menuReportesHTML() {
        System.out.println("MÓDULO REPORTES HTML - Próximamente");
    }
}
