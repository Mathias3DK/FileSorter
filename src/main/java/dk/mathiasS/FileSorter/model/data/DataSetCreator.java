package dk.mathiasS.FileSorter.model.data;

import dk.mathiasS.FileSorter.Main;
import dk.mathiasS.FileSorter.configuration.Module;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.*;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToNominal;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class DataSetCreator {

    private Instances dataset;
    private Classifier classifier;

    public void createDataset() {
        // Create attributes
        FastVector attributes = new FastVector();
        attributes.addElement(new Attribute("name", (FastVector) null));
        attributes.addElement(new Attribute("owner", (FastVector) null));
        attributes.addElement(new Attribute("size"));
        attributes.addElement(new Attribute("content", (FastVector) null));
        attributes.addElement(new Attribute("type", (FastVector) null));
        FastVector subjectValues = new FastVector();

        try {
            for (Module module : Main.initalizeClasses()) {
                subjectValues.addElement(module.getName());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Attribute subjectAttribute = new Attribute("subject", subjectValues);
        attributes.addElement(subjectAttribute);

        // Create empty dataset
        dataset = new Instances("PredictorData", attributes, 0);

        ArffSaver arffSaver = new ArffSaver();
        arffSaver.setInstances(dataset);
        try {
            arffSaver.setFile(new File(System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "FileSorter - af Mathias" + File.separator + "file_sorter_data.arff"));
            arffSaver.writeBatch();
            System.out.println("ARFF file saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Generate fictive data for 10 files
        /*Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            Instance instance = new DenseInstance(6);
            instance.setDataset(dataset);
            instance.setValue(0, "File" + (i + 1)); // Name
            instance.setValue(1, "Owner" + (i + 1)); // Owner
            instance.setValue(2, rand.nextDouble() * 500); // Size (assuming maximum size of 500)
            instance.setValue(3, "Content" + (i + 1)); // Content
            // Randomly select a file type
            String[] fileTypes = {"pdf", "docx", "xlsx", "ppt", "csv"};
            instance.setValue(4, fileTypes[rand.nextInt(fileTypes.length)]); // Type
            // Randomly select a subject
            instance.setValue(5, String.valueOf(subjectValues.elementAt(rand.nextInt(subjectValues.size())))); // Subject
            dataset.add(instance);
        }*/
    }

    /*public void buildClassifier() throws Exception {
        // Set class attribute
        dataset.setClassIndex(dataset.numAttributes() - 1);

        // Apply StringToNominal filter
        StringToNominal filter = new StringToNominal();
        String[] options = {"-R", "first-last"};
        filter.setOptions(options);
        filter.setInputFormat(dataset);
        Instances filteredData = Filter.useFilter(dataset, filter);

        // Build classifier
        classifier = new J48();
        classifier.buildClassifier(filteredData);
    }*/

 /*   public String predict(String name, String owner, double size, String content, String type) throws Exception {
        // Create instance for prediction
        Instance instance = new DenseInstance(6);
        instance.setDataset(dataset);

        // Set attribute values
        instance.setValue(0, name);
        instance.setValue(1, owner);
        instance.setValue(2, size);
        instance.setValue(3, content);
        instance.setValue(4, type);

        // Perform prediction
        double prediction = classifier.classifyInstance(instance);

        // Return the predicted class value
        return dataset.classAttribute().value((int) prediction);
    }
*/
    public static void main(String[] args) {
        try {
            DataSetCreator dataSetCreator = new DataSetCreator();
            dataSetCreator.createDataset();
/*            dataSetCreator.buildClassifier();

            // Example prediction
            String prediction = dataSetCreator.predict("File1", "Owner1", 150, "Content1", "pdf");
            System.out.println("Prediction: " + prediction);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
