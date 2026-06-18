package proyecto_1;

import java.util.Scanner;

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

    public static void menuVehiculos() {
        int opcion;

        do {
            System.out.println("\n===== CRUD DE VEHÍCULOS =====");
            System.out.println("1. Carga masiva");
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
                    System.out.println("Carga masiva pendiente");
                    break;
                case 2:
                    ingresarVehiculoManual();
                    break;
                case 3:
                    System.out.println("Modificar pendiente");
                    break;
                case 4:
                    System.out.println("Eliminar pendiente");
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

    for (int i = 0; i < vehiculos.length; i++) {
        if (vehiculos[i] != null) {
            System.out.println("--------------------------------");
            System.out.println("ID: " + vehiculos[i].getId());
            System.out.println("Marca: " + vehiculos[i].getMarca());
            System.out.println("Modelo: " + vehiculos[i].getModelo());
            System.out.println("Capacidad: " + vehiculos[i].getCapacidadMax() + " kg");
            System.out.println("Estado: " + vehiculos[i].getEstado());
            System.out.println("Imagen: " + vehiculos[i].getImagen());
        }
    }
}
    
    public static void menuOrdenes() {
        System.out.println("MÓDULO ÓRDENES");
    }

    public static void menuSimulacion() {
        System.out.println("MÓDULO SIMULACIÓN");
    }

    public static void menuConsultas() {
        System.out.println("MÓDULO CONSULTAS");
    }

    public static void menuReportesHTML() {
        System.out.println("MÓDULO REPORTES HTML");
    }
}
