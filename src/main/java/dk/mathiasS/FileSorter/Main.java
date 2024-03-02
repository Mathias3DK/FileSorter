package dk.mathiasS.FileSorter;

//file sorter
//sort files by name and class
//


import dk.mathiasS.FileSorter.configuration.ConfigurationFile;
import dk.mathiasS.FileSorter.configuration.Module;
import dk.mathiasS.FileSorter.download.ui.Application;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static ArrayList<Module> classes = new ArrayList<>();
    public static ConfigurationFile config;

    public static ArrayList<Module> initalizeClasses() throws IOException {
        ArrayList<Module> modules = new ArrayList<>();

        ConfigurationFile config = new ConfigurationFile(new File(System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "FileSorter - af Mathias" + File.separator + "config.yml"));
        int i = 1;
        for (java.lang.String module : config.getKeys("classes")) {

            Module subject = new Module(module);

            subject.setId(i);
            subject.create();
            i++;

            modules.add(subject);
        }
        return modules;
    }
    public static void main(String[] args) throws IOException {

        config = new ConfigurationFile(new File(System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "FileSorter - af Mathias" + File.separator + "config.yml"));
        classes = initalizeClasses();

        //for(Module clazz : classes) System.out.println(clazz.getName());

        new Application();

        //ConfigurationFile config = new ConfigurationFile(new File(Main.class.getResource("/config.yml").getFile()));
        //for (String modules : config.getKeys("classes")) {
        //    System.out.println(config.getInSection("alias", modules));
        //}
    }
}




