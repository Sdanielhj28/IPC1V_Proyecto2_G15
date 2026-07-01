package modelo;

import java.io.Serializable;

/**
 *
 * @author danie
 */

public class Resultado implements Serializable {
    private static final long serialVersionUID = 1L;

    private int numeroAnalisis;
    private String codigoMuestra;
    private String codigoPatron;
    private String fecha;
    private String hora;
    private String resultado;
    private String codigoInvestigador;

    public Resultado(int numeroAnalisis, String codigoMuestra, String codigoPatron,
                     String fecha, String hora, String resultado, String codigoInvestigador) {
        this.numeroAnalisis = numeroAnalisis;
        this.codigoMuestra = codigoMuestra;
        this.codigoPatron = codigoPatron;
        this.fecha = fecha;
        this.hora = hora;
        this.resultado = resultado;
        this.codigoInvestigador = codigoInvestigador;
    }

    public int getNumeroAnalisis() {
        return numeroAnalisis;
    }

    public String getCodigoMuestra() {
        return codigoMuestra;
    }

    public String getCodigoPatron() {
        return codigoPatron;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getResultado() {
        return resultado;
    }

    public String getCodigoInvestigador() {
        return codigoInvestigador;
    }
}