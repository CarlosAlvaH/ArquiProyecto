/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import java.util.List;
import prototipobrazo.clase.Estante;

/**
 *
 * @author solra
 */
public interface EstanteDaoInt {
    public List<Estante> Listar();
    public List<Estante> BuscarEstado(String est);
    public Estante Buscar(int cod);
    public int insertar(Estante obj);
    public int eliminar(int cod);
    public int actualizar(Estante obj);
    public int vaciar(Estante obj);
}
