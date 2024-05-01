package dk.mathiasS.FileSorter.model.data;

import java.io.File;

public class DataRetriever {
    private final File file;
    private final MetadataExtractor extractor;
    private String name;
    private String owner;
    private double size;
    private String content;
    private String type;

    // Constructor
    public DataRetriever(File file) {

        this.file=file;
        this.extractor=new MetadataExtractor(this.file);

    }

    public void setName(){
        String fileName = this.file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1) {
            this.name = fileName.substring(0, dotIndex);
        } else {
            this.name = fileName;
        }
    }

    public void setSize(){
        this.size=(this.file.length() > 0 ? (double) file.length() / (1024 * 1024) : 0);

    }

    public void setType(){
        String extension = "";
        String filename = this.file.getName();

        int i = filename.lastIndexOf('.');
        int p = Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));

        if (i > p) {
            extension = filename.substring(i+1);
            this.type=extension;
        }
    }

    public void setOwner(){
        this.owner=extractor.file_getCreator();
    }
    // Getters
    public String getName() {
        return name;
    }

    public String getOwner() {
        return (owner != null ? owner : "none");
    }

    public double getSize() {
        return size;
    }

    public String getContent() {
        return (content != null ? content : " ");
    }

    public String getType() {
        return type;
    }
    public void setContent() {
        this.content=null;
    }
}
