package modelo;

/**
 *
 * @author danie
 */
public class Muestra {
    private String codigo;
    private String descripcion;
    private String estado;
    
    public Muestra(String codigo, String descripcion, String estado){
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.estado = estado;
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
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
