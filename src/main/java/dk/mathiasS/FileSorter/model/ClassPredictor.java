package dk.mathiasS.FileSorter.model;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.*;

import java.io.*;
import java.util.ArrayList;


public class ClassPredictor {

    private final Instances trainingData;
    private Classifier classifier;

    private String[] attributes;

    private String[] classes;


    public ClassPredictor() {

        // Opret attributter
        ArrayList<Attribute> attributes = new ArrayList<>();

        // recognizable data attributes
        Attribute fileName = new Attribute("name");
        Attribute fileSize = new Attribute("size");
        Attribute fileContent = new Attribute("content");
        Attribute fileOwner = new Attribute("owner");
        Attribute fileType = new Attribute("type");

        // Class Attribute with all classes as return values
        FastVector classValues = new FastVector();

        //define classes from getClasses()
        for (String subject : new String[]{"Engelsk", "Matematik", "Samfundsfag", "Dansk", "Informatik", "Virksomhedsøkonomi", "Internationaløkonomi", "Afsætning"}) {
            classValues.addElement(subject);
        }

        Attribute subject = new Attribute("subject", classValues);

        // Tilføj attributter til liste
        attributes.add(fileName);
        attributes.add(fileType);
        attributes.add(fileSize);
        attributes.add(fileOwner);
        attributes.add(fileContent);

        attributes.add(subject);

        // Opret Instances objekt
        trainingData = new Instances("PredictorData", attributes, 0);
        trainingData.setClass(subject);

        // Opret og konfigurer J48 klassifikator
        classifier = new J48();
    }

    public void addTrainingInstance(String name, String owner, double size, String content, String type) {
        // Oprette instans
        Instance instance = new DenseInstance(5);

        instance.setValue(trainingData.attribute("name"), name);
        instance.setValue(trainingData.attribute("owner"), owner);
        instance.setValue(trainingData.attribute("size"), size);
        instance.setValue(trainingData.attribute("content"), content);
        instance.setValue(trainingData.attribute("type"), type);

        // Tilføj instans til træningsdata
        trainingData.add(instance);
    }

    public void buildClassifier(Instances trainingData) throws Exception {
        // build classifier
        classifier.buildClassifier(trainingData);
    }
    public String predict() throws Exception {
        // Opret instans for forudsigelse
        Instance instance = new DenseInstance(5);
        instance.setDataset(trainingData);

        System.out.println(trainingData);

        // Sæt attributværdier


        // Udføre forudsigelse
        double prediction = classifier.classifyInstance(instance);

        // Returnere den forudsagte klasseværdi
        return trainingData.classAttribute().value((int) prediction);
    }

    public void setClassifier(Classifier newClassifier) {
        // Set the classifier to the provided one
        classifier = newClassifier;
    }

    public Classifier getClassifier() {
        return classifier;
    }
}