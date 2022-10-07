/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.ClsConexion;
import modelo.Persona;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import modelo.Articulo;
import modelo.Venta;

/**
 *
 * @author pipe
 */
public class Controlador {

    ClsConexion conexion = new ClsConexion();

    public Controlador() {

    }

    public boolean guardar(String cedula, String nombre, String apellido, String correo, String contraseña) {
        Persona persona = new Persona(nombre, apellido, cedula, correo, contraseña);
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute("insert into persona(nombre,apellido,cedula,correo,contraseña) "
                    + "values('" + persona.getCedula() + "','"
                    + persona.getNombre() + "','"
                    + persona.getApellido() + "','"
                    + persona.getCorreo() + "',"
                    + persona.getContraseña() + ")");//consulta
            conexion.desconectar();//se desconecta de la base de datos 
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }
    public Persona buscarPersona(String cedula) {
        List<Persona> usuarios = new ArrayList<>();
        conexion.conectar();
        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().executeQuery("select cedula, nombre, apellido, correo, contraseña "
                    + "from persona"));
            while (conexion.getResultadoDB().next()) {
                String cedula2 = conexion.getResultadoDB().getString("cedula");
                String nombre = conexion.getResultadoDB().getString("nombre");
                String apellido = conexion.getResultadoDB().getString("apellido");
                String correo = conexion.getResultadoDB().getString("correo");
                String contraseña = conexion.getResultadoDB().getString("contraseña");
                Persona temp = new Persona( nombre, apellido,cedula2, correo, contraseña);
                usuarios.add(temp);
            }
            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getCedula().equals(cedula)) {                  
                        conexion.desconectar();
                        return usuarios.get(i);
                }
            }
            conexion.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            conexion.desconectar();
            return null;
        }
        return null;
    }

    public List<String> buscar(String cedula) {

        List<String> temp = new ArrayList<String>();

        conexion.conectar();

        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select cedula,nombre,apellido,correo,"
                            + "contraseña from persona where cedula "
                            + "cedula='" + cedula + "'"));//consulta        

            if (conexion.getResultadoDB().next()) {
                temp.add(conexion.getResultadoDB().getString("cedula"));
                temp.add(conexion.getResultadoDB().getString("nombre"));
                temp.add(conexion.getResultadoDB().getString("apellido"));
                temp.add(conexion.getResultadoDB().getString("correo"));
                temp.add(conexion.getResultadoDB().getString("contraseña") + "");
            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {
            Logger.getLogger(Controlador.class.getName())
                    .log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }
        return temp;
    }

    public boolean modificarPersona(String nombre, String apellido, String cedula, String correo,String contraseña) {
        Persona usuario = new Persona(nombre, apellido, cedula, correo, contraseña);
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute
        ("update persona set nombre='" + usuario.getNombre() + 
                "',apellido='" + usuario.getApellido() + "'," + 
                "correo='" + usuario.getCorreo() + "'," + 
                "contraseña=" + usuario.getContraseña()
                + " where cedula='" + usuario.getCedula()+"'");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public boolean eliminar(String cedula) {

        conexion.conectar();

        try {
            conexion.getSentenciaSQL().execute("delete from persona where cedula='" + cedula + "'");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public DefaultTableModel listar() {
        DefaultTableModel temporal;
        String nombreColumnas[] = {"Nombre", "Apellido", "Cedula", "Correo", "Contraseña"};
        temporal = new DefaultTableModel(
                new Object[][]{}, nombreColumnas);
        conexion.conectar();
        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select nombre,apellido,cedula,correo"
                            + "contraseña from persona order by cedula"));//consulta        
            while (conexion.getResultadoDB().next()) {
                temporal.addRow(new Object[]{
                    conexion.getResultadoDB().getString("cedula"),
                    conexion.getResultadoDB().getString("nombre"),
                    conexion.getResultadoDB().getString("apellido"),
                    conexion.getResultadoDB().getString("correo"),
                    conexion.getResultadoDB().getString("contraseña"),
                    conexion.getResultadoDB().getInt("correo")});
            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {
            Logger.getLogger(Controlador.class.getName()).
                    log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }

        return temporal;

    }

    // gestion de articulos 
    public boolean guardarArticulo(String nombre, int precio, int cantidad, String descripcion, String categoria, String codigo) {
        Articulo articulo = new Articulo(nombre, precio, cantidad, descripcion, categoria, codigo);
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute("insert into articulo(codigo,nombre,precio,cantidad,descripcion,categoria) "
                    + "values('" + articulo.getCodigo() + "','"
                    + articulo.getNombre() + "','"
                    + articulo.getPrecio() + "','"
                    + articulo.getCantidad() + "','"
                    + articulo.getDescripcion() + "',"
                    + articulo.getCategoria() + ")");//consulta
            conexion.desconectar();//se desconecta de la base de datos 
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public List<String> buscarArticulo(String codigo) {

        List<String> temp = new ArrayList<String>();

        conexion.conectar();

        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select codigo,nombre,precio,cantidad,descripcion,"
                            + "categoria from articulo where codigo "
                            + "codigo='" + codigo + "'"));//consulta        

            if (conexion.getResultadoDB().next()) {
                temp.add(conexion.getResultadoDB().getString("codigo"));
                temp.add(conexion.getResultadoDB().getString("nombre"));
                temp.add(conexion.getResultadoDB().getString("precio"));
                temp.add(conexion.getResultadoDB().getString("cantidad"));
                temp.add(conexion.getResultadoDB().getString("descripcion"));
                temp.add(conexion.getResultadoDB().getString("categoria") + "");
            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {
            Logger.getLogger(Controlador.class.getName())
                    .log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }
        return temp;
    }

    public boolean modificarArticulo(String codigo, String nombre, int precio, int cantidad, String descripcion, String categoria) {
        Articulo articulo = new Articulo(nombre, precio, cantidad, descripcion, categoria, codigo);
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute
        ("update articulo set nombre='" + articulo.getNombre()
                    + "',precio='" + articulo.getPrecio() + "',"
                    + "cantidad='" + articulo.getCantidad() + "',"
                    + "descripcion='" + articulo.getDescripcion() + "',"
                    + "categoria=" + articulo.getCategoria()
                    + " where codigo='" + articulo.getCodigo() + "'");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }
    
    public Articulo buscarArticulos(String codigo) {
        List<Articulo> articulos = new ArrayList<>();
        conexion.conectar();
        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().executeQuery("select codigo, nombre, precio, cantidad, descripcion,categoria "
                    + "from articulo"));
            while (conexion.getResultadoDB().next()) {
                String codigo2 = conexion.getResultadoDB().getString("codigo");
                String nombre = conexion.getResultadoDB().getString("nombre");
                int precio = Integer.parseInt(conexion.getResultadoDB().getString("precio"));
                int cantidad = Integer.parseInt(conexion.getResultadoDB().getString("cantidad"));
                String descripcion = conexion.getResultadoDB().getString("descripcion");
                String categoria = conexion.getResultadoDB().getString("categoria");
                
                Articulo aux = new Articulo(nombre, precio, cantidad, descripcion, categoria, codigo2);
                articulos.add(aux);
            }
            for (int i = 0; i <articulos.size(); i++) {
                if (articulos.get(i).getCodigo().equals(codigo)) {                  
                        conexion.desconectar();
                        return articulos.get(i);
                }
            }
            conexion.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            conexion.desconectar();
            return null;
        }
        return null;
    }

    public boolean eliminarArticulo(String codigo) {

        conexion.conectar();

        try {
            conexion.getSentenciaSQL().execute("delete from articulo where codigo='" + codigo + "'");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public DefaultTableModel listarArticulos() {
        DefaultTableModel temporal;
        String nombreColumnas[] = {"Nombre", "Precio", "Cantidad", "Descripcion", "Categoria", "Codigo"};
        temporal = new DefaultTableModel(
                new Object[][]{}, nombreColumnas);
        conexion.conectar();
        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select nombre,precio,cantidad,descripcion,categoria"
                            + "codigo from articulo order by codigo"));//consulta        
            while (conexion.getResultadoDB().next()) {
                temporal.addRow(new Object[]{
                    conexion.getResultadoDB().getString("nombre"),
                    conexion.getResultadoDB().getString("precio"),
                    conexion.getResultadoDB().getString("cantidad"),
                    conexion.getResultadoDB().getString("descripcion"),
                    conexion.getResultadoDB().getString("categoria"),
                    conexion.getResultadoDB().getInt("codigo")});
            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {
            Logger.getLogger(Controlador.class.getName()).
                    log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }

        return temporal;

    }

    // ventas
    public boolean guardarVenta(String codigoVenta, String fecha, int unidades, String nombreArticulo, double valorTotal) {
        Venta venta = new Venta(codigoVenta, fecha, unidades, nombreArticulo, valorTotal);
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute
        ("insert into ventas(codigoVenta,fechaVenta,unidadesVendidas,nombreDelArticulo,valorTotal) "
                    + "values('" + venta.getCodigoVenta() + "','"
                    + venta.getFecha()+ "','"
                    + venta.getUnidades() + "','"
                    + venta.getNombreArticulo() + "',"
                    + venta.getValorTotal() + ")");//consulta
            conexion.desconectar();//se desconecta de la base de datos 
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public List<String> buscarVenta(String codigoVenta) {

        List<String> temp = new ArrayList<String>();

        conexion.conectar();

        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select codigoVenta,fecha,unidades,nombreArticulo,"
                            + "valorTotal from ventas where codigo "
                            + "codigoVenta='" + codigoVenta + "'"));//consulta        

            if (conexion.getResultadoDB().next()) {
                temp.add(conexion.getResultadoDB().getString("codigoVenta"));
                temp.add(conexion.getResultadoDB().getString("fecha"));
                temp.add(conexion.getResultadoDB().getString("unidades"));
                temp.add(conexion.getResultadoDB().getString("nombreArticulo"));
                temp.add(conexion.getResultadoDB().getString("valorTotal") + "");
            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {
            Logger.getLogger(Controlador.class.getName())
                    .log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }
        return temp;
    }

    public boolean modificarVenta(String codigoVenta, String fecha, int unidades, String nombreArticulo, int valorTotal) {
        Venta venta = new Venta(codigoVenta, fecha, unidades, nombreArticulo, valorTotal);
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute("update articulo set condigoVenta='" + venta.getCodigoVenta()
                    + "',fecha='" + venta.getFecha() + "',"
                    + "unidades=" + venta.getUnidades() + ","
                    + "nombreArticulo=" + venta.getNombreArticulo() + ","
                    + " where valorTotal='" + venta.getValorTotal() + "'");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public boolean eliminarVenta(String codigoVenta) {

        conexion.conectar();

        try {
            conexion.getSentenciaSQL().execute("delete from venta where codigoVenta='" + codigoVenta + "'");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public DefaultTableModel listarVentas() {
        DefaultTableModel temporal;
        String nombreColumnas[] = {"Codigo Venta", "Fecha", "Unidades", "Nombre Articulo", "Valor total"};
        temporal = new DefaultTableModel(
                new Object[][]{}, nombreColumnas);
        conexion.conectar();
        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select codigoVenta,fechaVenta,nombreDelArticulo,unidadesVendidas,"
                            + "valorTotal from ventas order by codigoVenta"));//consulta        
            while (conexion.getResultadoDB().next()) {
                temporal.addRow(new Object[]{
                    conexion.getResultadoDB().getString("codigoVenta"),
                    conexion.getResultadoDB().getString("fechaVenta"),
                    conexion.getResultadoDB().getString("unidadesVendidas"),
                    conexion.getResultadoDB().getString("nombreDelArticulo"),
                    conexion.getResultadoDB().getInt("valorTotal")});
            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {
            Logger.getLogger(Controlador.class.getName()).
                    log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }

        return temporal;

    }
}
