package dk.mathiasS.FileSorter.model;

import dk.mathiasS.FileSorter.Main;
import dk.mathiasS.FileSorter.configuration.Module;
import dk.mathiasS.FileSorter.model.data.DataRetriever;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.*;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToNominal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ClassPredictor {
    private Instances trainingData;
    private Classifier classifier;

    // Constructor
    public ClassPredictor() {
        Attribute nameAttr = new Attribute("name", true);
        Attribute ownerAttr = new Attribute("owner", true);
        Attribute sizeAttr = new Attribute("size");
        Attribute contentAttr = new Attribute("content", true);
        Attribute typeAttr = new Attribute("type", true);

        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(nameAttr);
        attributes.add(ownerAttr);
        attributes.add(sizeAttr);
        attributes.add(contentAttr);
        attributes.add(typeAttr);

        FastVector subjectValues = new FastVector();

        try {
            for (Module module : Main.initalizeClasses()) {
                subjectValues.addElement(module.getName());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Attribute subjectAttribute = new Attribute("subject", subjectValues);
        attributes.add(subjectAttribute);

        trainingData = new Instances("PredictorData", attributes, 0);
        trainingData.setClassIndex(attributes.size() - 1);

        classifier=new J48();
    }

    // Load ARFF file

    public void loadARFF(String filePath) throws IOException {

        if(!new File(filePath).exists())
            return;

        ArffLoader loader = new ArffLoader();
        loader.setFile(new File(filePath));
        Instances data = loader.getDataSet();
        data.setClassIndex(data.numAttributes() - 1);
    }

    // Load model file
    public void loadModel(String modelFilePath) {
        File modelFile = new File(modelFilePath);

        if (modelFile.exists()) {
            try {
                classifier = (Classifier) SerializationHelper.read(modelFilePath);
                // Print statement for debugging
                System.out.println("Model file loaded.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Handle the case when the model file doesn't exist
            System.out.println("Model file does not exist. Initializing classifier...");
            // Perform any initialization steps here if needed
            classifier = new J48(); // Replace with your actual initialization logic
        }
    }

    // Save ARFF file
    public void saveARFF(String arffFilePath) {
        // Save ARFF logic
        ArffSaver arffSaver = new ArffSaver();
        arffSaver.setInstances(trainingData);
        try {
            arffSaver.setFile(new File(arffFilePath));
            arffSaver.writeBatch();

            // Print statement for debugging
            System.out.println("ARFF file saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save model file
    public void saveModel(String modelFilePath) {
        // Save model logic
        try {
            SerializationHelper.write(modelFilePath, classifier);

            // Print statement for debugging
            System.out.println("Model file saved.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Train the classifier
    public void trainClassifier() {
        try {
            StringToNominal filter = new StringToNominal();
            String[] options = {"-R", "first-last"};
            filter.setOptions(options);
            filter.setInputFormat(trainingData);
            Instances filteredData = Filter.useFilter(trainingData, filter);
            classifier.buildClassifier(filteredData);
            // Print statement for debugging
            System.out.println("Classifier trained.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Predict the class for a given file
    public String predictClass(DataRetriever dataRetriever) {
        try {
            Instances instance = createInstance(dataRetriever);
            double prediction = classifier.classifyInstance(instance.firstInstance());
            return trainingData.classAttribute().value((int) prediction);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    // Add a training instance to the dataset
// Add a training instance to the dataset
    public void addTrainingInstance(DataRetriever dataRetriever, String subjectValue) {
        Instances instance = createInstance(dataRetriever);

        // Set class value
        instance.setClassIndex(instance.numAttributes() - 1);
        instance.instance(0).setClassValue(subjectValue);

        // Add the instance to the training data
        trainingData.add(instance.instance(0));

        // Print statement for debugging
        System.out.println("Training instance added to the dataset. Subject: " + subjectValue);
    }


    public Instances getTrainingData() {
        return trainingData;
    }


    public Classifier getClassifier() {
        return classifier;
    }

    // Create an instance from DataRetriever
    private Instances createInstance(DataRetriever dataRetriever) {
        Instances dataset = trainingData;  // assuming trainingData is initialized

        Instance instance = new DenseInstance(dataset.numAttributes());

        for (int i = 0; i < dataset.numAttributes(); i++) {
            Attribute attribute = dataset.attribute(i);

            switch (attribute.name()) {
                case "name":
                    instance.setValue(attribute, dataRetriever.getName());
                    break;
                case "owner":
                    instance.setValue(attribute, dataRetriever.getOwner());
                    break;
                case "size":
                    instance.setValue(attribute, dataRetriever.getSize());
                    break;
                case "content":
                    instance.setValue(attribute, dataRetriever.getContent());
                    break;
                case "type":
                    instance.setValue(attribute, dataRetriever.getType());
                    break;
                default:
                    break;
            }
        }

        // Print statement for debugging
        System.out.println("Instance created from DataRetriever: " + instance);

        // Create a new Instances object and add the instance
        Instances newInstances = new Instances(dataset, 0);
        newInstances.add(instance);


        return newInstances;
    }

}
