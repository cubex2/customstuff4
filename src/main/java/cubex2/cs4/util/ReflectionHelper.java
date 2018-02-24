package cubex2.cs4.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReflectionHelper
{
    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... paramTypes)
    {
        try
        {
            return clazz.getConstructor(paramTypes);
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> T newInstance(Constructor<T> constructor, Object... initArgs)
    {
        try
        {
            return constructor.newInstance(initArgs);
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        } catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> T newInstance(Class<T> clazz)
    {
        try
        {
            return clazz.newInstance();
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
