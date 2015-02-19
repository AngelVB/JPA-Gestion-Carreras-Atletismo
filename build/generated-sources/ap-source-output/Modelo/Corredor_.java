package Modelo;

import Modelo.Marca;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-02-19T21:02:35")
@StaticMetamodel(Corredor.class)
public class Corredor_ { 

    public static volatile SingularAttribute<Corredor, String> apellidos;
    public static volatile SingularAttribute<Corredor, Date> fechaNac;
    public static volatile SingularAttribute<Corredor, Integer> idCorredor;
    public static volatile SingularAttribute<Corredor, String> club;
    public static volatile ListAttribute<Corredor, Marca> marcaList;
    public static volatile SingularAttribute<Corredor, String> nombre;
    public static volatile SingularAttribute<Corredor, String> dni;

}