package dk.mathiasS.FileSorter.model;

import dk.mathiasS.FileSorter.model.data.DataSetCreator;
import weka.classifiers.Classifier;
import weka.core.*;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToNominal;

import java.io.*;

public class ClassifierLoader {
    private String modelFilePath;
    private String arffFilePath;

    public ClassifierLoader(String modelFilePath, String arffFilePath) {
        this.modelFilePath = modelFilePath;
        this.arffFilePath = arffFilePath;
    }

    // Load model or ARFF file based on training progress
    public void loadClassifier(ClassPredictor predictor) throws IOException {
        System.out.println("Training: " + trainingInProgress());
        System.out.printf(arffFilePath);
        predictor.loadARFF(arffFilePath);
        //if (trainingInProgress()) {
        //    // Load ARFF file
        //    System.out.println("Training: " + trainingInProgress  ());
        //    predictor.loadARFF(arffFilePath);
        //} else {
        //    // Load model file
        //    predictor.loadModel(modelFilePath);
        //}
    }

    // Save ARFF file

    public void saveArff(String arffFilePath, Instances trainingData) throws IOException {

        // Open file writer in append mode
        BufferedWriter writer = new BufferedWriter(new FileWriter(arffFilePath, true));

        // Append new data to the ARFF file
        writer.write(trainingData.get(trainingData.size()-1).toString());

        // Close the file writer
        writer.close();

        System.out.println("New data appended to ARFF file: " + arffFilePath);
    }




    public void saveARFF(String arffFilePath, Instances newData) throws Exception {

       /* // Load existing ARFF file
        ArffLoader loader = new ArffLoader();
        Instances existingData = null;
        try {
            loader.setFile(new File(arffFilePath));
            existingData = loader.getDataSet();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Check if existingData is not null and empty
        if (existingData != null && existingData.size() > 0) {
            // Check for duplicate instances and add only unique instances
            for (int i = 0; i < newData.numInstances(); i++) {
                boolean duplicate = false;
                for (int j = 0; j < existingData.numInstances(); j++) {
                    if (newData.instance(i).equals(existingData.instance(j))) {
                        duplicate = true;
                        break;
                    }
                }
                if (!duplicate) {
                    existingData.add(newData.instance(i));
                }
            }
        } else {
            // If existingData is null or empty, simply add newData to it
            existingData = newData;
        }
*/
        // Save the combined data to ARFF file


     /*   ArffLoader loader = new ArffLoader();
        Instances existingData = null;
        try {
            loader.setFile(new File(arffFilePath));
            existingData = loader.getDataSet();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (existingData == null) {
            System.out.println("error eccoured");
            return;
        }

        addInstances(existingData, newData);

        ArffSaver arffSaver = new ArffSaver();
        arffSaver.setInstances(existingData);
        try {
            arffSaver.setFile(new File(arffFilePath));
            arffSaver.writeBatch();

            // Print statement for debugging
            System.out.println("ARFF file saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        try {
            // Step 1: Load the existing ARFF file
            BufferedReader reader = new BufferedReader(new FileReader(arffFilePath));
            Instances data = new Instances(reader);
            reader.close();

            // Step 2: Read values for the new instance from a file
            BufferedReader instanceReader = new BufferedReader(new FileReader(arffFilePath));
            for(int i = 0; i<10; i++){
                instanceReader.readLine();
            }
            String line = instanceReader.readLine();
            String[] values = line.split(",");
            instanceReader.close();

            // Step 2: Create a new instance with the same attributes
            DenseInstance newInstance = new DenseInstance(data.numAttributes());

            // Step 3: Set attribute values for the new instance
            for (int i = 0; i < data.numAttributes(); i++) {
                String cleanedValue = values[i].replaceAll("\\\\", "").replaceAll("'", "");
                Attribute attr = data.attribute(i);
                if (attr.isNominal()) {

                    StringToNominal filter = new StringToNominal();
                    String[] options = {"-R", String.valueOf(i + 1)}; // Attribute indices are 1-based
                    filter.setOptions(options);
                    filter.setInputFormat(data);

                    Instances filteredData = Filter.useFilter(data, filter);
                    newInstance.setValue(attr, filteredData.attribute(i).indexOfValue(cleanedValue));
                } else if (attr.isString()) {
                    newInstance.setValue(attr, cleanedValue);
                } else {
                    double numericValue = Double.parseDouble(cleanedValue);
                    newInstance.setValue(attr, numericValue);
                }
            }

            // Step 4: Add the new instance to the existing Instances object
            data.add(newInstance);

            // Step 5: Write the modified Instances object back to a new ARFF file
            BufferedWriter writer = new BufferedWriter(new FileWriter(arffFilePath + "hej" + ".arff"));
            writer.write(data.toString());
            writer.flush();
            writer.close();

            System.out.println("New instance added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addInstances(Instances existingData, Instances newData) {
        for (int i = 0; i < newData.numInstances(); i++) {
            Instance newInstance = newData.instance(i);
            existingData.add(newInstance);
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
