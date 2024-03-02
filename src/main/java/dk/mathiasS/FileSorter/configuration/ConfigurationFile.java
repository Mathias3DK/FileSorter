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
    private String root=System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "FileSorter - af Mathias";
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
        try {
            InputStream inputStream = getClass().getResourceAsStream("/config.yml");
            OutputStream outputStream = new FileOutputStream(root + File.separator + "config.yml");

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outputStream.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
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
        if(value==null)return loaded.values();
        return loaded.get(value);
    }
    public void addSubject(String subjectName) {
        Map<String, Object> classesSection = (Map<String, Object>) loaded.get("classes");
        if (classesSection == null) {
            classesSection = new HashMap<>();
            loaded.put("classes", classesSection);
        }

        classesSection.put(subjectName, new HashMap<>()); // Empty map for subject data
        saveConfiguration();
    }

    private void saveConfiguration() {
        try (Writer writer = new FileWriter(file)) {
            configuration.dump(loaded, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeSubject(String subjectName) {
        Map<String, Object> classesSection = (Map<String, Object>) loaded.get("classes");
        if (classesSection != null) {
            classesSection.remove(subjectName);
            saveConfiguration();
        }
    }
    public void removeSubject(int subjectId) {
        Map<String, Object> classesSection = (Map<String, Object>) loaded.get("classes");
        if (classesSection != null) {
            int i = 1;
            for (Map.Entry<String, Object> entry : classesSection.entrySet()) {
                if (i == subjectId) {
                    classesSection.remove(entry.getKey());
                    saveConfiguration();
                    return;
                }
                i++;
            }
        }
    }


}