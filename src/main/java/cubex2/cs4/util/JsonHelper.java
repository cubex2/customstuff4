package cubex2.cs4.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;

public class JsonHelper
{
    private static final Gson gson = new GsonBuilder().create();

    public static <T> T deserialize(String input, Class<T> clazz)
    {
        return gson.fromJson(input, clazz);
    }

    public static <T> T deserialize(String input, Type clazz)
    {
        return gson.fromJson(input, clazz);
    }

    public static <T> T deserialize(Reader input, Class<T> clazz)
    {
        return gson.fromJson(input, clazz);
    }

    @Nullable
    public static <T> T deserialize(File input, Class<T> clazz)
    {
        FileReader reader = null;
        T result = null;
        try
        {
            reader = new FileReader(input);
            result = deserialize(reader, clazz);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } finally
        {
            if (reader != null)
                IOUtils.closeQuietly(reader);
        }

        return result;
    }
}
