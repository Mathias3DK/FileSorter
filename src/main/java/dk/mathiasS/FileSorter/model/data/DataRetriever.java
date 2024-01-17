package dk.mathiasS.FileSorter.model.data;

import org.apache.poi.hpsf.PropertySet;
import org.apache.poi.hpsf.PropertySetFactory;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class DataRetriever {

    private final File file;
    private String name;
    private String content;
    private String owner;
    private String type;

    private long size;

    public DataRetriever(File file){

        this.file=file;

        file_retrieveData(); //initalizing all the data
    }

    private void file_retrieveData() {

        if (this.file == null) return;

        this.name = this.file.getName();
        this.type = this.file.getName().split("\\.")[1];
        this.owner = (type.equals("docx") ? getWordFileOwner(this.file.getPath()) : getExcelFileOwner(this.file.getPath()));//file attributeViewer class#getOwner
        this.content = "Matematik|ligningen";

        this.size = this.file.length();

    }


    public static String getExcelFileOwner(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             POIFSFileSystem poifs = new POIFSFileSystem(fis)) {

            DirectoryNode root = poifs.getRoot();
            DirectoryEntry dir = (DirectoryEntry) root.getEntry(SummaryInformation.DEFAULT_STREAM_NAME);
            PropertySet ps = PropertySetFactory.create((InputStream) dir);

            SummaryInformation summaryInformation = (SummaryInformation) ps;

            return summaryInformation.getAuthor();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getWordFileOwner(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             XWPFDocument document = new XWPFDocument(fis)) {

            return document.getProperties().getCoreProperties().getCreator();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public long getSize() {
        return size;
    }

    public String getOwner() {
        return owner;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
