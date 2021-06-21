/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inicio;

import java.io.Serializable;

/**
 *
 * @author David
 */
public class Producto implements Serializable {
    
    private int codigo;
    private String nombre;
    private int Cantidad;
    private Object descripcion;
    private String fechas;
    private byte[] foto;

    public Producto(){}
    
    public Producto(int codigo, String nombre, int Cantidad, Object descripcion , String fechas,byte[]foto){
        this.codigo = codigo;
        this.nombre = nombre;
        this.Cantidad = Cantidad;
        this.descripcion = descripcion;
        this.fechas=fechas;
        this.foto=foto;
    }
    /**
     * @return the codigo
     */
    public int getCodigo() {
        return codigo;
    }
/**
     * @return the Fechas
     */
    public String getFechas() {
        return fechas;
    }
/**
     * @param fechas the codigo to set
     */
    public void setFechas(String fechas) {
        this.fechas = fechas;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the precio
     */
    public double getCantidad() {
        return Cantidad;
    }

    /**
     * @param Cantidad the precio to set
     */
    public void setPrecio(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    /**
     * @return the descripcion
     */
    public Object getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(Object descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the foto
     */
    public byte[] getFoto() {
        return foto;
    }

    /**
     * @param foto the foto to set
     */
    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
    
}
