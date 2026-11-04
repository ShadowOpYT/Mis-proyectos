package gestion;

import modelo.Cliente;
import modelo.Turno;
import java.util.ArrayList;

public class GestorTurnos {
    private ArrayList<Cliente> listaClientes;
    private ArrayList<Turno> listaTurnos;

    public GestorTurnos() {
        this.listaClientes = new ArrayList<>();
        this.listaTurnos = new ArrayList<>();
    }

    public void registrarCliente(Cliente cliente) {
        listaClientes.add(cliente);
    }

    public ArrayList<Cliente> obtenerClientes() {
        return listaClientes;
    }

    public void registrarTurno(Turno turno) {
        listaTurnos.add(turno);
    }

    public ArrayList<Turno> obtenerTurnos() {
        return listaTurnos;
    }
}