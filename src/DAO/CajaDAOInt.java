/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import java.util.List;
import prototipobrazo.clase.Caja;


public interface CajaDAOInt {
    public List<Caja> Listar();
    public Caja Buscar(int cod);
    public int insertar(Caja obj);
    public int eliminar(int cod);
    public int actualizar(Caja obj);
}
