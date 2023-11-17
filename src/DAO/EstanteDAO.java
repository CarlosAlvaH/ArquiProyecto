/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import prototipobrazo.clase.Estante;
import util.SqlDbConexion;


public class EstanteDAO implements EstanteDaoInt{

    @Override
    public List<Estante> Listar() {
        ArrayList<Estante> data = new ArrayList<>();
        Estante obj = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = SqlDbConexion.getConnection("arquitectura");
            String sql = "Select * from estante";
            pst = (PreparedStatement) cn.prepareStatement(sql);

            rs = pst.executeQuery();
            while (rs.next()) {
                obj = new Estante();
                obj.setIdEstante(rs.getString(1));
                obj.setIdCaja(rs.getInt(2));
                obj.setEstado(rs.getString(3));
                data.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;}

    @Override
    public Estante Buscar(int cod) {
        Estante obj = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = SqlDbConexion.getConnection("arquitectura");
            String sql = "Select * from estante where idEstante=?";
            pst = (PreparedStatement) cn.prepareStatement(sql);
            pst.setInt(1, cod);
            rs = pst.executeQuery();
            if (rs.next()) {
                obj = new Estante();
                obj.setIdEstante(rs.getString(1));
                obj.setIdCaja(rs.getInt(2));
                obj.setEstado(rs.getString(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;}

    @Override
    public int insertar(Estante obj) {
        int estado = -1;
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = SqlDbConexion.getConnection("arquitectura");
            String sql = "";

            sql = "insert into estante values(?,?,?)";

            pst = (PreparedStatement) cn.prepareStatement(sql);
            pst.setString(1, obj.getIdEstante());
            pst.setInt(2, obj.getIdCaja());
            pst.setString(3, obj.getEstado());
            estado = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return estado;}

    @Override
    public int eliminar(int cod) {
        int estado = -1;
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = SqlDbConexion.getConnection("arquitectura");
            String sql = "";

            sql = "delete from estante where idEstante=? ";

            pst = (PreparedStatement) cn.prepareStatement(sql);
            pst.setInt(1, cod);
            estado = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return estado;}

    @Override
    public int actualizar(Estante obj) {
        int estado = -1;
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = SqlDbConexion.getConnection("arquitectura");
            String sql = "";

            sql = "update estante set idCaja=?, estado=? where idEstante=? ";

            pst = (PreparedStatement) cn.prepareStatement(sql);
            pst.setString(3, obj.getIdEstante());
            pst.setInt(1, obj.getIdCaja());
            pst.setString(2, obj.getEstado());
            estado = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return estado;}

    @Override
    public List<Estante> BuscarEstado(String est) {
        ArrayList<Estante> data = new ArrayList<>();
        Estante obj = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = SqlDbConexion.getConnection("arquitectura");
            String sql = "Select * from estante where estado=?";
            pst = (PreparedStatement) cn.prepareStatement(sql);
            pst.setString(1, est);
            rs = pst.executeQuery();
            while (rs.next()) {
                obj = new Estante();
                obj.setIdEstante(rs.getString(1));
                obj.setIdCaja(rs.getInt(2));
                obj.setEstado(rs.getString(3));
                data.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;}

    @Override
    public int vaciar(Estante obj) {
        int estado = -1;
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = SqlDbConexion.getConnection("arquitectura");
            String sql = "";

            sql = "update estante set idCaja=NULL, estado=? where idEstante=? ";

            pst = (PreparedStatement) cn.prepareStatement(sql);
            pst.setString(2, obj.getIdEstante());
            pst.setString(1, obj.getEstado());
            estado = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return estado;}
}
    

