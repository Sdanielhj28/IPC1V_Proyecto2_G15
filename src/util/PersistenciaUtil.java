package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import modelo.SistemaDatos;

/**
 *
 * @author danie
 */
public class PersistenciaUtil {
    public static void guardarDatos() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("datos.bin"));

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
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("datos.bin"));

            SistemaDatos.investigadores = (java.util.ArrayList) in.readObject();
            SistemaDatos.muestras = (java.util.ArrayList) in.readObject();
            SistemaDatos.patrones = (java.util.ArrayList) in.readObject();

            in.close();
        } catch (Exception e) {
            System.out.println("No hay datos previos cargados");
        }
    }
}
