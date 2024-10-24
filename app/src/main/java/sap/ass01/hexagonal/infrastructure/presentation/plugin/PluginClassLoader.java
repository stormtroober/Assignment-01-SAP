package sap.ass01.hexagonal.infrastructure.presentation.plugin;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginClassLoader extends ClassLoader {

    private JarFile jarFile;

    public PluginClassLoader(String jarFilePath) throws Exception {
        this.jarFile = new JarFile(jarFilePath);
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        String classFile = className.replace('.', '/') + ".class";
        JarEntry entry = jarFile.getJarEntry(classFile);

        if (entry == null) {
            throw new ClassNotFoundException(className);
        }

        try (InputStream input = jarFile.getInputStream(entry)) {
            byte[] classData = input.readAllBytes();
            return defineClass(className, classData, 0, classData.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(className, e);
        }
    }
}