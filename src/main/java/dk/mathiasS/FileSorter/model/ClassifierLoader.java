package dk.mathiasS.FileSorter.model;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.SerializationHelper;

import java.io.File;
import java.io.IOException;

public class ClassifierLoader {
    private String modelFilePath;
    private String arffFilePath;

    // Constructor
    public ClassifierLoader(String modelFilePath, String arffFilePath) {
        this.modelFilePath = modelFilePath;
        this.arffFilePath = arffFilePath;
    }

    // Load model or ARFF file based on training progress
    public void loadClassifier(ClassPredictor predictor) {
        if (trainingInProgress()) {
            // Load ARFF file
            predictor.loadARFF(arffFilePath);
        } else {
            // Load model file
            predictor.loadModel(modelFilePath);
        }
    }

    // Save ARFF file
    public void saveARFF(String arffFilePath, Instances dataset) {
        // Save ARFF logic
        ArffSaver arffSaver = new ArffSaver();
        arffSaver.setInstances(dataset);
        try {
            arffSaver.setFile(new File(arffFilePath));
            arffSaver.writeBatch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save model file
    public void saveModel(String modelFilePath, Classifier classifier) {
        // Save model logic
        try {
            SerializationHelper.write(modelFilePath, classifier);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Check if training is in progress
    private boolean trainingInProgress() {
        File arffFile = new File(arffFilePath);
        File modelFile = new File(modelFilePath);
        return arffFile.exists() && !modelFile.exists();
    }
}
