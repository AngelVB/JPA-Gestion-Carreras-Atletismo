/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import Modelo.Corredor;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author Angel Valera
 * Clase que gestiona los modelos de las listas de Corredores.
 * Utilizada para poder alamcenar Objetos Corredor en un JList
 */
public class ListModelCorredor extends AbstractListModel{

    private ArrayList<Corredor> Corredores = new ArrayList<>();
    
    @Override
    public int getSize() {
       return Corredores.size();
     }

    @Override
    public Object getElementAt(int index) {
     Corredor c = Corredores.get(index);
   return c.getNombre();   
    
    }
    
    public void addCorredor(Corredor c){
  Corredores.add(c);
  this.fireIntervalAdded(this, getSize(), getSize()+1);
 }
    
    public void eliminarCorredor(int index0){
   Corredores.remove(index0);
   this.fireIntervalRemoved(index0, getSize(), getSize()+1);
 }
    public Corredor getCorredor(int index){
  return Corredores.get(index);
}

}