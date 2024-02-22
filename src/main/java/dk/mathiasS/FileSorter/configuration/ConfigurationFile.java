package dk.mathiasS.FileSorter.configuration;

import dk.mathiasS.FileSorter.Main;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConfigurationFile {

    //root
    private String root="C:\\Users\\Schje\\Downloads\\FileSorter - af Mathias";
    private final Yaml configuration;
    private File file;

    private Map<String, Object> loaded;

    public ConfigurationFile(File file) throws IOException {
        boolean con=false;
        if(!file.exists()){
            con = create();
        }
        this.file=file;
        this.configuration = new Yaml();
        this.loaded = configuration.load(Files.newInputStream(file.toPath()));
    }
    public boolean exist(){
        return this.file.exists();
    }

    public boolean create() {
        File file = new File(Main.class.getResource("/config.yml").getFile());
        new File(String.valueOf(file.toPath())).renameTo(new File(root));

        if (file.exists()) return true;
        return false;
    }
    public Set<String> getKeys(String section){
        // Get the 'classes' section
        Map<String, Object> classesSection = (Map<String, Object>) loaded.get(section);
        return classesSection.keySet();
    }
    public Set<String> getKeys(){
        return loaded.keySet();
    }
    //value you want - the subject
    public Object getInSection(String value, String section){
        Map<String, Object> classesSection = (Map<String, Object>) loaded.get("classes");

        String sectionName = section;

        if (classesSection.containsKey(sectionName)) {
            Map<String, Object> sectionValue = (Map<String, Object>) classesSection.get(sectionName);

            String result = (String) sectionValue.get(value);
            return result;
        }
        return null;
    }

    public Object get(String value) {
        System.out.printf(loaded.toString());
        if(value==null)return loaded.values();
        return loaded.get(value);
    }

}