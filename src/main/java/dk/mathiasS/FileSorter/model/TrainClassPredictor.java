package dk.mathiasS.FileSorter.model;

import dk.mathiasS.FileSorter.model.data.DataRetriever;
import weka.classifiers.Classifier;
import weka.core.Instances;

import java.io.File;
import java.util.Random;

public class TrainClassPredictor {

    private static final String path = "C:/Users/Schje/Downloads/FileSorter - af Mathias/";

    public static void main(String[] args) {
        try {
            // Load or create an instance of ClassPredictor
            ClassPredictor predictor = new ClassPredictor();

            // Load model if available, otherwise train a new classifier
            ClassifierLoader loader = new ClassifierLoader();
            Classifier classifier = loader.loadModel(path + "/" + "SubjectPredictor_model.model");

            if (classifier == null) {
                // Train a new classifier
                DataRetriever data = new DataRetriever(new File(path + "/" + "config.yml"));
                predictor.addTrainingInstance(data.getName(), data.getOwner(), data.getSize(), data.getContent(), data.getType());

                // Build classifier
                Instances trainingData = loader.loadArff(path + "/" + "data.arff");
                predictor.buildClassifier(trainingData);

                // Save the trained model
                loader.saveModel("C:/Users/Schje/Downloads/FileSorter - af Mathias/SubjectPredictor_model.model", predictor.getClassifier());
            } else {
                // Use the loaded model for predictions
                predictor.setClassifier(classifier);
            }
            // Perform prediction
            String prediction = predictor.predict();

            // Print prediction
            System.out.println("Subject for a random file " + prediction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
