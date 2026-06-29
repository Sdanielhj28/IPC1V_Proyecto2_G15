package modelo;

import java.io.Serializable;

/**
 *
 * @author danie
 */
public class Muestra implements Serializable {
    private String codigo;
    private String descripcion;
    private String estado;
    private int[][] matriz;
    private String codigoInvestigadorAsignado;

    public Muestra(String codigo, String descripcion, String estado, int[][] matriz) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.matriz = matriz;
        this.codigoInvestigadorAsignado = "";
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public int[][] getMatriz() {
        return matriz;
    }

    public String getCodigoInvestigadorAsignado() {
        return codigoInvestigadorAsignado;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setMatriz(int[][] matriz) {
        this.matriz = matriz;
    }

    public void setCodigoInvestigadorAsignado(String codigoInvestigadorAsignado) {
        this.codigoInvestigadorAsignado = codigoInvestigadorAsignado;
    }
}