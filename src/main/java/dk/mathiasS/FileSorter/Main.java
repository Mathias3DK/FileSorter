package dk.mathiasS.FileSorter;

//file sorter
//sort files by name and class
//


import dk.mathiasS.FileSorter.configuration.ConfigurationFile;
import dk.mathiasS.FileSorter.configuration.Module;
import dk.mathiasS.FileSorter.download.ui.Application;
import dk.mathiasS.FileSorter.download.ui.DefineFileUI;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Main {

    public static ArrayList<Module> classes = new ArrayList<>();

    public static ArrayList<Module> initalizeClasses() throws IOException {
        ArrayList<Module> modules = new ArrayList<>();

        ConfigurationFile config = new ConfigurationFile(new File(Main.class.getResource("/config.yml").getFile()));
        for (java.lang.String module : config.getKeys("classes")) {

            Module subject = new Module(module);
            subject.create();

            modules.add(subject);
        }
        return modules;
    }

        public static void main(String[] args) throws IOException {

        classes = initalizeClasses();

        for(Module clazz : classes) System.out.println(clazz.getName());


        new DefineFileUI(null);
        ConfigurationFile config = new ConfigurationFile(new File(Main.class.getResource("/config.yml").getFile()));
        for (java.lang.String modules : config.getKeys("classes")) {
            System.out.println(config.getInSection("alias", modules));
        }
    }
}




