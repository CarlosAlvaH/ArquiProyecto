package DAO;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import prototipobrazo.clase.Caja;
import util.SqlDbConexion;

public class CajaDAO implements CajaDAOInt {

    @Override
    public List<Caja> Listar() {
        ArrayList<Caja> data = new ArrayList<>();
        Caja obj = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = SqlDbConexion.getConnection("arquitectura");
            String sql = "Select * from caja";
            pst = (PreparedStatement) cn.prepareStatement(sql);

            rs = pst.executeQuery();
            while (rs.next()) {
                obj = new Caja();
                obj.setIdCaja(rs.getInt(1));
                obj.setNombre(rs.getString(2));
                obj.setFec_ing(rs.getString(3));
                obj.setFec_sal(rs.getString(4));
                obj.setPeso(rs.getString(5));
                obj.setTipo(rs.getString(6));
                obj.setCantidad(rs.getInt(7));
                Blob img=(Blob) rs.getBlob(8);
                
                byte[] imgBytes = img.getBytes(1, (int) img.length());
                ImageIcon image = new ImageIcon(imgBytes);
                obj.setImagen(image);
                
                data.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public Caja Buscar(int cod) {
        Caja obj = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = SqlDbConexion.getConnection("arquitectura");
            String sql = "Select * from caja where idCaja=?";
            pst = (PreparedStatement) cn.prepareStatement(sql);
            pst.setInt(1, cod);
            rs = pst.executeQuery();
            if (rs.next()) {
                obj = new Caja();
                obj.setIdCaja(rs.getInt(1));
                obj.setNombre(rs.getString(2));
                obj.setFec_ing(rs.getString(3));
                obj.setFec_sal(rs.getString(4));
                obj.setPeso(rs.getString(5));
                obj.setTipo(rs.getString(6));
                obj.setCantidad(rs.getInt(7));
                Blob img=(Blob) rs.getBlob(8);
                
                byte[] imgBytes = img.getBytes(1, (int) img.length());
                ImageIcon image = new ImageIcon(imgBytes);
                obj.setImagen(image);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public int insertar(Caja obj) {
        int estado = -1;
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = SqlDbConexion.getConnection("arquitectura");
            String sql = "";

            sql = "insert into seguro values(?,?,?,?,?,?,?)";

            pst = (PreparedStatement) cn.prepareStatement(sql);
            pst.setInt(1, obj.getIdCaja());
            pst.setString(2, obj.getNombre());
            pst.setString(3, obj.getFec_ing());
            pst.setString(4, obj.getFec_sal());
            pst.setString(5, obj.getPeso());
            pst.setString(6, obj.getTipo());
            pst.setInt(7, obj.getCantidad());
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

        return estado;
    }

    @Override
    public int eliminar(int cod) {
        int estado = -1;
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = SqlDbConexion.getConnection("arquitectura");
            String sql = "";

            sql = "delete from caja where idCaja=? ";

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

        return estado;
    }

    @Override
    public int actualizar(Caja obj) {
        int estado = -1;
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = SqlDbConexion.getConnection("arquitectura");
            String sql = "";

            sql = "update caja set nombre=?, fecIng=?, fec_sal=?, peso=?, tipo=?,cantidad=? where idCaja=? ";

            pst = (PreparedStatement) cn.prepareStatement(sql);
            pst.setInt(7, obj.getIdCaja());
            pst.setString(1, obj.getNombre());
            pst.setString(2, obj.getFec_ing());
            pst.setString(3, obj.getFec_sal());
            pst.setString(4, obj.getPeso());
            pst.setString(5, obj.getTipo());
            pst.setInt(6, obj.getCantidad());
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

        return estado;
    }

}
