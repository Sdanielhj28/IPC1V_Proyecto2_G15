package modelo;

import java.io.Serializable;

public class Patron implements Serializable {
    private static final long serialVersionUID = 1L;

    private String codigo;
    private String nombre;
    private int[][] matriz;

    public Patron(String codigo, String nombre, int[][] matriz) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.matriz = matriz;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public int[][] getMatriz() { return matriz; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setMatriz(int[][] matriz) { this.matriz = matriz; }
}
