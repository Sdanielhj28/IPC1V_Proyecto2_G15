package proyecto_1;

/**
 *
 * @author daniel
 */
public class Orden {
    private String idOrden;
    private String descripcion;
    private double pesoPaquete;
    private int destinoX;
    private int destinoY;
    private String estado;
    private String idVehiculoAsignado;

    public Orden(String idOrden, String descripcion, double pesoPaquete, int destinoX, int destinoY) {
        this.idOrden = idOrden;
        this.descripcion = descripcion;
        this.pesoPaquete = pesoPaquete;
        this.destinoX = destinoX;
        this.destinoY = destinoY;
        this.estado = "Pendiente";
        this.idVehiculoAsignado = null;
    }

    public String getIdOrden() { return idOrden; }
    public String getDescripcion() { return descripcion; }
    public double getPesoPaquete() { return pesoPaquete; }
    public int getDestinoX() { return destinoX; }
    public int getDestinoY() { return destinoY; }
    public String getEstado() { return estado; }
    public String getIdVehiculoAsignado() { return idVehiculoAsignado; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setPesoPaquete(double pesoPaquete) { this.pesoPaquete = pesoPaquete; }
    public void setDestinoX(int destinoX) { this.destinoX = destinoX; }
    public void setDestinoY(int destinoY) { this.destinoY = destinoY; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setIdVehiculoAsignado(String idVehiculoAsignado) { this.idVehiculoAsignado = idVehiculoAsignado; }
}
