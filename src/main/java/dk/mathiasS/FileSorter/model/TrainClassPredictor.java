package dk.mathiasS.FileSorter.model;

import dk.mathiasS.FileSorter.model.data.DataRetriever;

import java.io.File;
import java.io.IOException;

// TrainClassPredictor.java
public class TrainClassPredictor {

    public static void main(String[] args) throws IOException {
        DataRetriever testData = new DataRetriever("Opgaveløsning", "Kate", 56.0, "Løs opgaverne", "pdf");

        ClassPredictor predictor = new ClassPredictor();
        ClassifierLoader classifierLoader = new ClassifierLoader("C:/Users/Schje/Downloads/FileSorter - af Mathias/" + "modelFile.model", "C:/Users/Schje/Downloads/FileSorter - af Mathias/" + "trainingData.arff");

        classifierLoader.loadClassifier(predictor);

        // Train classifier
        predictor.addTrainingInstance(testData, "Matematik");
        predictor.trainClassifier();

        // Save ARFF and model files
        classifierLoader.saveARFF("C:/Users/Schje/Downloads/FileSorter - af Mathias/" + "trainingData.arff", predictor.getTrainingData());
        //classifierLoader.saveModel("C:/Users/Schje/Downloads/FileSorter - af Mathias/" + "modelFile.model", predictor.getClassifier());

        String predictedClass = predictor.predictClass(testData);

        System.out.println("Predicted Class: " + predictedClass);
    }
}
