package service;

import com.luxoft.bankapp.annotations.NoDB;
import com.luxoft.bankapp.model.BaseClassMarkerInterface;
import com.luxoft.bankapp.model.Client;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by Makarov Denis on 02.02.2015.
 */
public class TestService {
    /**
     * Данный метод должен анализировать поля классов o1 и o2
     * Он должен сравнивать все поля с помощью equals,
     * за исключением тех полей, которые помечены аннотацией
     * @NoDB
     * и возвращать true, если все поля совпали.
     * также он должен уметь сравнивать коллекции.
     */
    public static boolean isEquals(Object o1, Object o2) {

        if (o1==null || o2==null) return false;

        if (o1.getClass() != o2.getClass()) return false;
        Class cls = o1.getClass();

        for (Field fieldIterator:cls.getDeclaredFields()) {
            fieldIterator.setAccessible(true);
            if (!fieldIterator.isAnnotationPresent(NoDB.class)) {
                try {
                    Object value1 = fieldIterator.get(o1);
                    Object value2 = fieldIterator.get(o2);
                    Class fieldType = fieldIterator.getClass();

                    //System.out.println("Field name: " + fieldIterator.getName());
                    //System.out.println("Values: " + value1 + " -- "+ value2);
                    //System.out.println(value1.getClass() +  " -- " + value1.getClass().getSuperclass());

                    if (!value1.equals(value2)) return false;

                    System.out.println(value1 + " -- " + value2);

                    System.out.println("Field is : " + fieldIterator.getName() +" type: "
                            + fieldType +" value: " + value1.getClass());

                    if (isCollection(value1)){
                        Collection col1 = (Collection) value1;
                        Collection col2 = (Collection) value2;
                        for (Object iterator1 : col1) {
                            for (Object iterator2 : col2){
                                if (!iterator1.equals(iterator2)) return false;
                                isEquals(iterator1, iterator2);
                            }
                        }
                    }


                    /*
                    if (isCollection(value1) ) {
                        System.out.println("Collection!");

                        ArrayList <Client> array1 = new ArrayList<Client>((Collection<? extends Client>) value1);
                        ArrayList <Client> array2 = new ArrayList<Client>((Collection<? extends Client>) value2);
                    }
                    */
                    /*
                    if (value1.getClass().getSuperclass() != java.lang.Object.class) {
                        isEquals(value1,value2);
                    }
                    */
                } catch (IllegalAccessException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return true;
    }

    public static boolean isCollection(Object ob) {
        return ob instanceof Collection || ob instanceof Map;
    }
}