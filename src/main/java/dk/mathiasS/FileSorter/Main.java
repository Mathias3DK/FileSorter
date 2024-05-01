package dk.mathiasS.FileSorter;

//file sorter
//sort files by name and class
//


import dk.mathiasS.FileSorter.configuration.ConfigurationFile;
import dk.mathiasS.FileSorter.configuration.Module;
import dk.mathiasS.FileSorter.database.DatabaseHandler;
import dk.mathiasS.FileSorter.download.ui.Application;
import dk.mathiasS.FileSorter.model.data.DataSetCreator;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static ArrayList<Module> classes = new ArrayList<>();
    public static ConfigurationFile config;

    public static ArrayList<Module> initalizeClasses() throws IOException {
        ArrayList<Module> modules = new ArrayList<>();

        ConfigurationFile config = new ConfigurationFile(new File(System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "FileSorter - af Mathias" + File.separator + "config.yml"));
        List<String> keyList = new ArrayList<>(config.getKeys("classes")); // Convert Set to List
        for(int i = 1;i<keyList.size()+1;i++)
        {
            String subj=keyList.get(i-1);

            if(subj==null)
                return null;

            Module subject = new Module(subj);

            subject.setId(i);
            subject.create();

            modules.add(subject);
        }
        return modules;
    }

    //TODO MAKE SURE ARFF FILE EXISTS AND DO SO IT DOESNT REUPLOAD THE SAME DATAs
    public static void main(String[] args) throws IOException, SQLException {

        config = new ConfigurationFile(new File(System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "FileSorter - af Mathias" + File.separator + "config.yml"));
        classes = initalizeClasses();

        if(!new File(System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "FileSorter - af Mathias" + File.separator + "file_sorter_data.arff").exists())

        {

            DataSetCreator dataSetCreator = new DataSetCreator();
            dataSetCreator.createDataset();

        }

        new Application();

        //ConfigurationFile config = new ConfigurationFile(new File(Main.class.getResource("/config.yml").getFile()));
        //for (String modules : config.getKeys("classes")) {
        //    System.out.println(config.getInSection("alias", modules));
        //}
    }


}




