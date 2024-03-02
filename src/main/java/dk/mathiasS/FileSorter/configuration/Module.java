package dk.mathiasS.FileSorter.configuration;

import dk.mathiasS.FileSorter.Main;
import org.apache.xmlbeans.impl.xb.xmlconfig.ConfigDocument;

import java.io.File;
import java.io.IOException;

public class Module {

    private String name;
    private String file_root="C:/Users/Schje/Desktop/Skole/";

    private String dir;
    private int id;

    public Module(String name){
        this.name=name; //setting the name
    }

    //make the instance from the file
    public Module(File file){
        //this.name=new File(file_root )
    }

    public void create(){
        if(!new File(file_root + "\\" + name).exists())
            new File(file_root + "\\" + name).mkdir();
        this.dir = file_root + "\\" + name;
    }
    public String getName(){
        return name;
    }
    public String getDir(){
        return dir;
    }

    public String getAlias() throws IOException {
        return (String) Main.config.getInSection("alias", getName());
    }

    public void setDir(String path){
        dir = path;
    }

    public void estimate(){
        //estimate all the files
    }
    public int lastUsted(){
        return 0;//unix of last time the direcoty was used
    }
    public void delete(){
    }
    public void autosort(){
        //enable autodelete if not used in a long period of time
    }


    public void setId(int i) {
        this.id=i;
    }

    public int getId() {
        return id;
    }
}
