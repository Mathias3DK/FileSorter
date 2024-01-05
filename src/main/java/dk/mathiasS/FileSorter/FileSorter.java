package dk.mathiasS.FileSorter;

import dk.mathiasS.FileSorter.configuration.ConfigurationFile;
import dk.mathiasS.FileSorter.configuration.Module;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileSorter {

    private java.lang.String toPath;
    //private String type; //concludes if it should be a custom path or just stock
    private boolean debug; //sends info about the moves of files
    private ArrayList<Module> classes;

    private ConfigurationFile config;
    public FileSorter() throws IOException {

        this.config=new ConfigurationFile(new File("C:\\Users\\Schje\\Downloads\\FileSorter - af Mathias\\config.yml"));

        this.toPath = "C:\\Users\\Schje\\Desktop\\Skole\\";
        classes = initalizeClasses();
    }

    public ArrayList<Module> initalizeClasses() {
        for (java.lang.String modules : this.config.getKeys("classes")){
            this.config.getInSection(null, "Informatik");
            //loop them, put all paramters in and then new Module put that in an arraylist
        }
        //return list of modules
        return null;
    }
    public void run(){

        //initalizing dirs
        for (Module clazz : classes) {
            if (!new File(toPath + "\\" + clazz.getName()).exists()) new File(toPath + "\\" + clazz.getName()).mkdir();
        }

        //retrieve files
        File path = new File("C:\\Users\\Schje\\Downloads");
        File[] files = path.listFiles();
        int count = 0;
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) { //this line weeds out other directories/folders
                java.lang.String name = files[i].getName(); //get name of file
                for(int index = 0; index < classes.size(); index++){
                    //sorting
                    java.lang.String sName = name.split("-")[0];
                    if(sName.equals(classes.get(index).getName()) || getFromShort(sName)!=null){

                        String dirTo = classes.get(index).getDir();
                        boolean con = moveFile(files[i].getPath(), "C:\\Users\\Schje\\Desktop\\Skole\\" + dirTo + "\\" + files[i].getName());

                        if (con){
                            count++;
                        }
                        System.out.printf("Du rykkede succesfuldt " + count + "x filer");
                    }
                }

            }
        }
    }
    public boolean moveFile(String sourcePath, String targetPath) {

        File fileToMove = new File(sourcePath);

        return fileToMove.renameTo(new File(targetPath));
    }
    public String getFromShort(String shorted){
        if(shorted.equalsIgnoreCase("EN")) return "Engelsk";
        if(shorted.equalsIgnoreCase("VØ")) return "Virksomhedsøkonomi";
        if(shorted.equalsIgnoreCase("IØ")) return "Internationaløkonomi";
        if(shorted.equalsIgnoreCase("Samf")) return "Samfundsfag";
        if(shorted.equalsIgnoreCase("IT")) return "Informatik";
        if(shorted.equalsIgnoreCase("MAT")) return "Matematik";
        if(shorted.equalsIgnoreCase("AF")) return "Afsætning";
        return null;
    }
}
