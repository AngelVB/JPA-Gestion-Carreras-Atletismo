/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import Modelo.Carrera;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author Angel Valera
 * Clase que gestiona los modelos de las listas de Carreras.
 * Utilizada para poder alamcenar Objetos Carrera en un JList
 */
public class ListModelCarrera extends AbstractListModel{

    private ArrayList<Carrera> Carreras = new ArrayList<>();
    
    @Override
    public int getSize() {
       return Carreras.size();
     }

    @Override
    public Object getElementAt(int index) {
     Carrera c = Carreras.get(index);
     String cadenafecha = new SimpleDateFormat("dd-MM-yyyy").format(c.getFecha());
     return cadenafecha;   
    
    }
    
    public void addCarrera(Carrera c){
  Carreras.add(c);
  this.fireIntervalAdded(this, getSize(), getSize()+1);
 }
    
    public void eliminarCarrera(int index0){
   Carreras.remove(index0);
   this.fireIntervalRemoved(index0, getSize(), getSize()+1);
 }
    public Carrera getCarrera(int index){
  return Carreras.get(index);
}

}