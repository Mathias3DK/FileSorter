package dk.mathiasS.FileSorter.model;

import dk.mathiasS.FileSorter.model.data.DataRetriever;

// TrainClassPredictor.java
public class TrainClassPredictor {

    public static void main(String[] args) {
        // Test data
        DataRetriever testData = new DataRetriever("Ligninger", "Kate", 60.0, "Ligninger", "pdf");

        // Create instances of classes
        ClassPredictor predictor = new ClassPredictor();
        ClassifierLoader classifierLoader = new ClassifierLoader("C:/Users/Schje/Downloads/FileSorter - af Mathias/" + "modelFile.model", "C:/Users/Schje/Downloads/FileSorter - af Mathias/" + "trainingData.arff");

        // Load classifier
        classifierLoader.loadClassifier(predictor);

        // Train classifier (Assuming you have labeled data to add)
        predictor.addTrainingInstance(testData, "Matematik");
        predictor.trainClassifier();

        // Save ARFF and model files
        classifierLoader.saveARFF("C:/Users/Schje/Downloads/FileSorter - af Mathias/" + "trainingData.arff", predictor.getTrainingData());
        classifierLoader.saveModel("C:/Users/Schje/Downloads/FileSorter - af Mathias/" + "modelFile.model", predictor.getClassifier());

        // Predict the class for the test data
        String predictedClass = predictor.predictClass(testData);

        // Print the result
        System.out.println("Predicted Class: " + predictedClass);
    }
}
