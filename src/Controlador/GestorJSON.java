/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import Modelo.Carrera;
import Modelo.Corredor;
import Modelo.Marca;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * 
 * Gestión de JSON
 */
public class GestorJSON {
    
    //IMPORTAR FICHERO JSON A LISTA DE OBJETOS.
    public List<Carrera> importar(String ficheroJSON) throws IOException, ParseException
    {
        List<Carrera> carreras = new ArrayList<Carrera>();
        JSONParser parser = new JSONParser();       
        Object obj = parser.parse(new FileReader(ficheroJSON));       
        JSONArray array = (JSONArray) obj;
        
        Iterator<JSONObject> iterator = array.iterator();
        while (iterator.hasNext()) {
            JSONObject carrera = iterator.next();
            int id = Integer.parseInt(carrera.get("id_carrera").toString());  
            Date fecha = new Date(Date.parse(carrera.get("fecha").toString())); 
            String poblacion = carrera.get("poblacion").toString();
            String organiza=carrera.get("organiza").toString();
            String lugarsalida = carrera.get("lugar_salida").toString();
            String modalidad = carrera.get("modalidad").toString();
            double distancia1 = Double.parseDouble(carrera.get("distancia").toString());
            BigDecimal distancia = BigDecimal.valueOf(distancia1);
            
          
            Carrera nuevo = new Carrera(id, fecha,poblacion, organiza, lugarsalida,distancia,modalidad);
            carreras.add(nuevo);
        }
        return carreras;
    }
    
    
    //EXPORTAR OBJETOS A FICHERO JSON.
    public boolean exportar(List<Carrera>lista, String ficheroJSON) throws IOException
    {
        //CREO FICHERO
        FileWriter file = new FileWriter(ficheroJSON);
        
        //CREO ARRAY JSON
        JSONArray array = new JSONArray();
        
        //RECORRO LA LISTA Y VOY CREANDO OBJETOS JSON Y METIÉNDOLOS EN EL ARRAY
        for(Carrera c : lista)
        {
            JSONObject obj = new JSONObject(); 
            obj.put("id_carrera", c.getIdCarrera());
            
            String cadenafecha = new SimpleDateFormat("dd-MM-yyyy").format(c.getFecha());
           
            
            obj.put("fecha", cadenafecha);
            obj.put("poblacion", c.getPoblacion());
            obj.put("organiza", c.getOrganiza());
            obj.put("lugar_salida", c.getLugarSalida());
            obj.put("distancia", c.getDistancia());
            obj.put("organiza", c.getOrganiza());
            obj.put("modalidad", c.getModalidad());
             
            
            array.add(obj);
        }
        
        //ESCRIBO EL ARRAY DE JSON EN EL FICHERO.
        file.write(array.toJSONString());
        file.flush();
        file.close();
        
        return true;
    }
    
}
