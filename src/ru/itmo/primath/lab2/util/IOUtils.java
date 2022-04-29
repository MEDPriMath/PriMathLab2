package ru.itmo.primath.lab2.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtils {
    public static String readResourceFile(String name) {
        InputStream is = IOUtils.class.getClassLoader().getResourceAsStream(name);
        if (is == null) {
            throw new RuntimeException(String.format("Could not find resource file '%s'", name));
        }
        return readFile(is);
    }

    public static String readFile(InputStream is) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] cache = new byte[8192];
            int length;
            while ((length = is.read(cache)) > 0) {
                os.write(cache, 0, length);
            }
            is.close();
            os.close();
            return os.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
