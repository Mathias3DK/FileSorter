package dk.mathiasS.FileSorter.model.data;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MetadataExtractor {

    private final File file;

    public MetadataExtractor(File file){
        this.file=file;
    }

    public String file_getContent(){

        if(file.getName().endsWith(".pdf")){
            try (PDDocument document = PDDocument.load(new FileInputStream(String.valueOf(file.toPath())))) {

                PDFTextStripper pdfStripper = new PDFTextStripper();
                String text = pdfStripper.getText(document);

                return text;

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else if(file.getName().endsWith(".docx")){
            try (InputStream fis = new FileInputStream(file.toPath().toString())) {
                XWPFDocument doc = new XWPFDocument(fis);

                XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
                String text = extractor.getText();

                return text;

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public String file_getCreator(){

        if(file.getName().endsWith(".pdf")){
            try (PDDocument document = PDDocument.load(new FileInputStream(String.valueOf(file.toPath())))) {
                PDDocumentInformation info = document.getDocumentInformation();

                if(info.getAuthor() != null){
                    return info.getAuthor();
                }
                return info.getCreator();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else if(file.getName().endsWith(".docx")){
            try (InputStream fis = new FileInputStream(file.toPath().toString())) {
                XWPFDocument doc = new XWPFDocument(fis);

                if(doc.getProperties().getCoreProperties().getCreator() != null){
                    doc.close();
                    return (doc.getProperties().getCoreProperties().getCreator());
                }
                doc.close();
                return doc.getProperties().getCoreProperties().getLastModifiedByUser();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "none";
    }


    public void extractDocxMetadata() throws IOException, InvalidFormatException {
        try (InputStream fis = new FileInputStream(String.valueOf(file.toPath()))) {
            XWPFDocument doc = new XWPFDocument(fis);

            System.out.println("Title: " + doc.getProperties().getCoreProperties().getTitle());
            System.out.println("Creator: " + doc.getProperties().getCoreProperties().getCreator());
            System.out.println("last edit: " + doc.getProperties().getCoreProperties().getLastModifiedByUser());
            System.out.println("Author: " + doc.getProperties().getThumbnailImage());


            doc.close();
        }
    }

    public static void extractPdfMetadata(String filePath) throws IOException {
        try (PDDocument document = PDDocument.load(new FileInputStream(filePath))) {
            PDDocumentInformation info = document.getDocumentInformation();

            // Extract document properties
            System.out.println("Title: " + info.getTitle());
            System.out.println("Author: " + info.getAuthor());
            System.out.println("Subject: " + info.getSubject());
            System.out.println("Keywords: " + info.getKeywords());
            System.out.println("Creator: " + info.getCreator());
            System.out.println("Producer: " + info.getProducer());
            System.out.println("Creation Date: " + info.getCreationDate());
            System.out.println("Modification Date: " + info.getModificationDate());
            // Add more properties as needed
        }
    }

    public static void main(String[] args) {

        File dir = new File("C:/Users/Schje/Downloads/");
        ArrayList<File> files = new ArrayList<>(List.of(dir.listFiles()));

        HashMap<String, Integer> creatorCounts = new HashMap<>();

        System.out.println(files);
        for(int i = 1; i < 200; i++) {

            if(files.get(i).isFile() && (files.get(i).getName().endsWith(".docx") || files.get(i).getName().endsWith(".pdf"))) {
                String docxFilePath = files.get(i).getPath();

                System.out.println("Extracting metadata from DOCX file:" + files.get(i).getName());
                String creator = new MetadataExtractor(new File(docxFilePath)).file_getCreator();
                System.out.println(creator);

                creatorCounts.put(creator, (creatorCounts.getOrDefault(creator, 0)) + 1);
            };
        }
        for (String creator : creatorCounts.keySet()) {
            System.out.println("Creator: " + creator + ", Count: " + creatorCounts.get(creator));
        }

    }
}

