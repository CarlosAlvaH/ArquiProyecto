
package prototipobrazo.clase;

public class Caja {
    private int idCaja;
    private String nombre;
    private String fec_ing;
    private String fec_sal;
    private String peso;
    private String tipo;
    private int cantidad;
    
    public Caja(){
        
    }

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFec_ing() {
        return fec_ing;
    }

    public void setFec_ing(String fec_ing) {
        this.fec_ing = fec_ing;
    }

    public String getFec_sal() {
        return fec_sal;
    }

    public void setFec_sal(String fec_sal) {
        this.fec_sal = fec_sal;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
}
