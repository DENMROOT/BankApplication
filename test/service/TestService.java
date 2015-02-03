package service;

import com.luxoft.bankapp.service.annotations.NoDB;
import com.luxoft.bankapp.model.BaseClassMarkerInterface;

import java.lang.reflect.Field;
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
        if (o1.getClass() != o2.getClass()) return false;
        Class cls1 = o1.getClass();
        Class cls2 = o2.getClass();

        //Рекурсивно анализируем поля суперкласса объекта если он имплементит мой маркер интерфейс BaseClassMarkerInterface
        Class superClass1 = cls1.getSuperclass();
        Class superClass2 = cls2.getSuperclass();

        if(o1 instanceof BaseClassMarkerInterface) {
            Field[] fieldSuper1 = superClass1.getDeclaredFields();
            Field[] fieldSuper2 = superClass2.getDeclaredFields();

            if(!fieldsIsEqual(o1, o2, fieldSuper1, fieldSuper2)) {
                return false;
            }
        }

        Field[] fields1 = cls1.getDeclaredFields();
        Field[] fields2 = cls2.getDeclaredFields();
        assert(fields1.length == fields2.length);

        if(!fieldsIsEqual(o1, o2, fields1, fields2)) {
            return false;
        }

        return true;
    }

    private static boolean fieldsIsEqual (Object o1, Object o2, Field[] fields1, Field[] fields2){
        Class cls1 = o1.getClass();
        Class cls2 = o2.getClass();
        /*
        System.out.println("Class1 : " + cls1 + " Class2 : " + cls2);
        for (Field fieldIter: fields1){
            if (!fieldIter.isAnnotationPresent(NoDB.class)) {
                System.out.println(fieldIter.getName());
            }
        }
        */

        for (int i=0; i < fields1.length; i++) {
            Field f1 = fields1[i];
            Field f2 = fields2[i];
            f1.setAccessible(true);
            f2.setAccessible(true);

            if (!f1.isAnnotationPresent(NoDB.class)) {
                try {
                    Object value1 = f1.get(o1);
                    Object value2 = f2.get(o2);
                    Class fieldType = f1.getClass();

                    if(value1 == null && value2 == null) {
                        continue;
                    }

                    if(value1 == null || value2 == null) {
                        return false;
                    }

                    if (Collection.class.isAssignableFrom(f1.getType())){
                        if (!collectionIsEqual(value1,value2)) return false;
                    }

                    if (!value1.equals(value2)) {
                        return false;
                    }

                } catch (IllegalAccessException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return true;
    }

    private  static boolean collectionIsEqual (Object o1, Object o2){
        Collection col1 = (Collection) o1;
        Collection col2 = (Collection) o2;

        if(col1.size() != col2.size()) {
            return false;
        }

        Iterator iter1 = col1.iterator();
        Iterator iter2 = col2.iterator();

        while (iter1.hasNext()) {
            Object object1 = iter1.next();
            Object object2 = iter2.next();
            return isEquals(object1, object2);
        }
        return true;
    }

    public static boolean isCollection(Object ob) {
        return ob instanceof Collection || ob instanceof Map;
    }
}