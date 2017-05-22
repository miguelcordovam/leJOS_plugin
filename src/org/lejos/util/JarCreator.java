package org.lejos.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class JarCreator {

    private String inputDirectory;
    private String outputFile;
    private String mainClass;
    private boolean debug = false;
    private ArrayList<String> libs;
    private String classPath = "/home/root/lejos/lib/ev3classes.jar /home/root/lejos/lib/opencv-2411.jar /home/root/lejos/lib/dbusjava.jar /home/root/lejos/libjna/usr/share/java/jna.jar";

    public JarCreator(String inputDirectory, String outputFile, String mainClass, ArrayList<String> libs) {
        this.inputDirectory = inputDirectory.replace("\\", "/");
        this.outputFile = outputFile;
        this.mainClass = mainClass;
        this.libs = libs;
        if(this.debug) {
//            LeJOSEV3Util.message("Input Directory is " + this.inputDirectory);
        }

    }

    public void run() throws IOException {
        Manifest manifest = new Manifest();
        Attributes attributes = manifest.getMainAttributes();
        attributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
        attributes.put(Attributes.Name.MAIN_CLASS, this.mainClass);

        File file;
        Iterator libs = this.libs.iterator();
        while (libs.hasNext()) {
            String lib = (String)libs.next();
            file = new File(lib);
            this.classPath = this.classPath + " /home/lejos/lib/" + file.getName();
        }

        if(this.debug) {
//            LeJOSEV3Util.message("Classpath is " + this.classPath);
        }

        attributes.put(Attributes.Name.CLASS_PATH, this.classPath);
        JarOutputStream outputStream = new JarOutputStream(new FileOutputStream(this.outputFile), manifest);
        this.add(new File(this.inputDirectory), outputStream);
        outputStream.close();
    }

    private void add(File source, JarOutputStream outputStream) throws IOException {
        BufferedInputStream in = null;

        try {

            if(source.isDirectory()) {
                String sourcePath = source.getPath().replace("\\", "/").replace(this.inputDirectory, "");
                if(!sourcePath.isEmpty()) {
                    if(!sourcePath.endsWith("/")) {
                        sourcePath = sourcePath + "/";
                    }

                    if(this.debug) {
//                        LeJOSEV3Util.message("Adding directory " + var12);
                    }

                    JarEntry jarEntry = new JarEntry(sourcePath);
                    jarEntry.setTime(source.lastModified());
                    outputStream.putNextEntry(jarEntry);
                    outputStream.closeEntry();
                }

                File[] files = source.listFiles();
                int numberOfFiles = files.length;

                for(int i = 0; i < numberOfFiles; ++i) {
                    File file = files[i];
                    if(this.debug) {
//                        LeJOSEV3Util.message("Adding " + file.getAbsolutePath());
                    }

                    this.add(file, outputStream);
                }

            } else {
                JarEntry entry = new JarEntry(source.getPath().replace("\\", "/").replace(this.inputDirectory + "/", ""));
                entry.setTime(source.lastModified());
                if(this.debug) {
//                    LeJOSEV3Util.message("Putting entry " + entry.getName());
                }

                outputStream.putNextEntry(entry);
                in = new BufferedInputStream(new FileInputStream(source));
                byte[] buffer = new byte[1024];

                while(true) {
                    int read = in.read(buffer);
                    if(read == -1) {
                        outputStream.closeEntry();
                        return;
                    }

                    outputStream.write(buffer, 0, read);
                }
            }
        } finally {
            if(in != null) {
                in.close();
            }
        }
    }
}