package dk.mathiasS.FileSorter.model;

import dk.mathiasS.FileSorter.model.data.DataRetriever;

import java.io.File;
import java.io.IOException;

// TrainClassPredictor.java
public class TrainClassPredictor {

    private final File file;
    private final String subject;

    public TrainClassPredictor(File file, String subject){

        this.file=file;
        this.subject=subject;

    }
    //return == predicted subject
    public String classPredictor_predict() throws Exception {

        DataRetriever testData = new DataRetriever(this.file);
//      DataRetriever testData = new DataRetriever("Opgaveløsning", "Kate", 56.0, "Løs opgaverne", "pdf");

        testData.setName();
        testData.setType();
        testData.setSize();
        testData.setOwner();
        testData.setContent();

        System.out.println("Owner " + testData.getOwner());
        System.out.println("Type " + testData.getType());
        System.out.println("Size " + testData.getSize());
        System.out.println("content " + testData.getContent());

        ClassPredictor predictor = new ClassPredictor();
        ClassifierLoader classifierLoader = new ClassifierLoader(System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "FileSorter - af Mathias" + File.separator + "modelFile.model", System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "FileSorter - af Mathias" + File.separator + "file_sorter_data.arff");

        classifierLoader.loadClassifier(predictor);

        // Train classifier
        predictor.addTrainingInstance(testData, this.subject);
        predictor.trainClassifier();

        // Save ARFF and model files
        classifierLoader.saveARFF(System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "FileSorter - af Mathias" + File.separator + "file_sorter_data.arff", predictor.getTrainingData());

        //classifierLoader.saveModel("C:/Users/Schje/Downloads/FileSorter - af Mathias/" + "modelFile.model", predictor.getClassifier());

        String predictedClass = predictor.predictClass(testData);
        System.out.println("Predicted Class: " + predictedClass);

        return predictedClass;
    }

}
