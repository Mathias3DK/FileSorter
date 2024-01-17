package dk.mathiasS.FileSorter.model;

import weka.classifiers.Classifier;
import weka.core.Instances;

import java.io.*;

public class ClassifierLoader {

    public Classifier loadModel(String modelFilePath) throws IOException, ClassNotFoundException {
        File modelFile = new File(modelFilePath);
        if (modelFile.exists()) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(modelFile));
            Classifier classifier = (Classifier) ois.readObject();
            ois.close();
            System.out.println("Model loaded: " + modelFilePath);
            return classifier;
        } else {
            System.out.println("Model file not found: " + modelFilePath);
            return null;
        }
    }

    public Instances loadArff(String arffFilePath) throws IOException {
        File arffFile = new File(arffFilePath);
        if (arffFile.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(arffFile));
            Instances trainingData = new Instances(reader);
            reader.close();
            System.out.println("ARFF file loaded: " + arffFilePath);
            return trainingData;
        } else {
            System.out.println("ARFF file not found: " + arffFilePath);
            return null;
        }
    }

    public void saveModel(String modelFilePath, Classifier classifier) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(modelFilePath));
        oos.writeObject(classifier);
        oos.close();
        System.out.println("Model saved: " + modelFilePath);
    }
}
