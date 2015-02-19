package Modelo;

import Modelo.Marca;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-02-19T21:02:35")
@StaticMetamodel(Carrera.class)
public class Carrera_ { 

    public static volatile SingularAttribute<Carrera, Integer> idCarrera;
    public static volatile SingularAttribute<Carrera, Date> fecha;
    public static volatile SingularAttribute<Carrera, String> modalidad;
    public static volatile SingularAttribute<Carrera, BigDecimal> distancia;
    public static volatile SingularAttribute<Carrera, String> organiza;
    public static volatile SingularAttribute<Carrera, String> poblacion;
    public static volatile ListAttribute<Carrera, Marca> marcaList;
    public static volatile SingularAttribute<Carrera, String> lugarSalida;

}