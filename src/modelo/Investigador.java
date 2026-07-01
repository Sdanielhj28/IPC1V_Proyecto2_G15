package modelo;

import java.io.Serializable;

/**
 *
 * @author danie
 */
public class Investigador implements Serializable {
    private String codigo;
    private String nombre;
    private String genero;
    private String password;
    private int experimentos;

    public Investigador(String codigo, String nombre, String genero, String password) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.genero = genero;
        this.password = password;
        this.experimentos = 0;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getGenero() {
        return genero;
    }

    public String getPassword() {
        return password;
    }

    public int getExperimentos() {
        return experimentos;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setExperimentos(int experimentos) {
        this.experimentos = experimentos;
    }
}