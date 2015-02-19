package Modelo;

import Modelo.Carrera;
import Modelo.Corredor;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-02-19T21:02:35")
@StaticMetamodel(Marca.class)
public class Marca_ { 

    public static volatile SingularAttribute<Marca, Integer> dorsal;
    public static volatile SingularAttribute<Marca, Carrera> idCarrera;
    public static volatile SingularAttribute<Marca, Corredor> idCorredor;
    public static volatile SingularAttribute<Marca, BigDecimal> tiempo;
    public static volatile SingularAttribute<Marca, Integer> idMarca;

}