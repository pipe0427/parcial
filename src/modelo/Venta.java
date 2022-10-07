/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author pipe
 */
public class Venta {
    private String codigoVenta;
    private String fecha;
    private int unidades;
    private String nombreArticulo;
    private double valorTotal;

    public Venta(String codigoVenta, String fecha, int unidades, String nombreArticulo, double valorTotal) {
        this.codigoVenta = codigoVenta;
        this.fecha = fecha;
        this.unidades = unidades;
        this.nombreArticulo = nombreArticulo;
        this.valorTotal = valorTotal;
    }

    public String getCodigoVenta() {
        return codigoVenta;
    }

    public void setCodigoVenta(String codigoVenta) {
        this.codigoVenta = codigoVenta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
    
}
