package cubex2.cs4.util;

import org.apache.commons.io.IOUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class IOHelper
{
    @Nullable
    public static String readStringFromModFile(File modDirectoryOrZip, String path)
    {
        if (modDirectoryOrZip.isDirectory())
        {
            return readStringFromFile(modDirectoryOrZip, path);
        } else
        {
            return readStringFromZip(modDirectoryOrZip, path);
        }
    }

    private static String readStringFromFile(File modDirectory, String path)
    {
        String result = null;
        InputStream stream = null;
        try
        {
            File file = new File(modDirectory, path);
            stream = new FileInputStream(file);
            result = IOUtils.toString(stream, StandardCharsets.UTF_8);
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (stream != null)
                IOUtils.closeQuietly(stream);
        }
        return result;
    }

    private static String readStringFromZip(File modZip, String path)
    {
        ZipFile zip = null;
        String result = null;
        try
        {
            zip = new ZipFile(modZip);
            ZipEntry entry = zip.getEntry(path);
            if (entry != null)
            {
                InputStream stream = zip.getInputStream(entry);
                result = IOUtils.toString(stream, StandardCharsets.UTF_8);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (zip != null)
                IOUtils.closeQuietly(zip);
        }

        return result;
    }
}
