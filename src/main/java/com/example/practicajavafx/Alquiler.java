package com.example.practicajavafx;

import java.time.LocalDate;

public class Alquiler {

    private String nommbreProducto;

    private LocalDate fechaAlquiler;

    private String observaciones;

    private Cliente cliente;

    public Alquiler(String nommbreProducto, LocalDate fechaAlquiler, String observaciones, Cliente cliente) {
        this.nommbreProducto = nommbreProducto;
        this.fechaAlquiler = fechaAlquiler;
        this.observaciones = observaciones;
        this.cliente = cliente;
    }

    public Alquiler() {

    }

    public String getNommbreProducto() {
        return nommbreProducto;
    }

    public void setNommbreProducto(String nommbreProducto) {
        this.nommbreProducto = nommbreProducto;
    }

    public LocalDate getFechaAlquiler(String string) {
        return fechaAlquiler;
    }

    public void setFechaAlquiler(LocalDate fechaAlquiler) {
        this.fechaAlquiler = fechaAlquiler;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
