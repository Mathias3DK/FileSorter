package dk.mathiasS.FileSorter.model.data;

public class DataRetriever {
    private String name;
    private String owner;
    private double size;
    private String content;
    private String type;

    // Constructor
    public DataRetriever(String name, String owner, double size, String content, String type) {
        this.name = name;
        this.owner = owner;
        this.size = size;
        this.content = content;
        this.type = type;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public double getSize() {
        return size;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }
}
