package modelo;

public class Turno {
    private String id;
    private Cliente cliente;
    private String fecha;
    private String hora;
    private String servicio;
    private String estado;

    public Turno(String id, Cliente cliente, String fecha, String hora, String servicio, String estado) {
        this.id = id;
        this.cliente = cliente;
        this.fecha = fecha;
        this.hora = hora;
        this.servicio = servicio;
        this.estado = estado;
    }

    public String getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }
    public String getServicio() { return servicio; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}