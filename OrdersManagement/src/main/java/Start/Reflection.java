package Start;

import java.lang.reflect.Field;
/**
 * The Reflection class provides utility methods for retrieving properties of an object using reflection.
 */
public class Reflection
{
    /**
     * Retrieves the properties of the given object using reflection and prints their names and values.
     *
     * @param object the object for which to retrieve properties
     */
    public static void retrieveProperties(Object object) {

        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(object);
                System.out.println(field.getName() + "=" + value);

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }
}

