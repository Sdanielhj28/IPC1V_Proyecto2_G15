package proyecto_1;

/**
 *
 * @author daniel
 */
public class Vehiculo {
     private String id;
    private String marca;
    private String modelo;
    private double capacidadMax;
    private String estado;
    private String imagen;

    public Vehiculo(String id, String marca, String modelo, double capacidadMax, String estado, String imagen) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.capacidadMax = capacidadMax;
        this.estado = estado;
        this.imagen = imagen;
    }

    public String getId() { return id; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public double getCapacidadMax() { return capacidadMax; }
    public String getEstado() { return estado; }
    public String getImagen() { return imagen; }

    public void setMarca(String marca) { this.marca = marca; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setCapacidadMax(double capacidadMax) { this.capacidadMax = capacidadMax; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setImagen(String imagen) { this.imagen = imagen; }
}
