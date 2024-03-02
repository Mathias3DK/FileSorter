package dk.mathiasS.FileSorter.model;

import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.*;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class WeatherPredictor {
    private Instances trainingData;
    private Classifier classifier;

    public WeatherPredictor(String arffFilePath) throws Exception {
        // Load data from ARFF file
        DataSource source = new DataSource(arffFilePath);
        trainingData = source.getDataSet();
        trainingData.setClassIndex(trainingData.numAttributes() - 1);

        classifier = new J48();
    }

    public void buildClassifier() throws Exception {
        // Build classifier
        classifier.buildClassifier(trainingData);
    }

    public void saveArff(String arffFilePath) throws IOException {
        // Save training data as ARFF file
        BufferedWriter writer = new BufferedWriter(new FileWriter(arffFilePath));
        writer.write(trainingData.toString());
        writer.close();
        System.out.println("ARFF file created: " + arffFilePath);
    }

    public String predict(String outlook, double temperatureValue, double humidityValue, boolean windy) throws Exception {
        // Create instance for prediction
        Instance instance = new DenseInstance(5);
        instance.setDataset(trainingData);

        // Set attribute values
        instance.setValue(trainingData.attribute("outlook"), outlook);
        instance.setValue(trainingData.attribute("temperature"), temperatureValue);
        instance.setValue(trainingData.attribute("humidity"), humidityValue);
        instance.setValue(trainingData.attribute("windy"), windy ? "TRUE" : "FALSE");

        // Perform prediction
        double prediction = classifier.classifyInstance(instance);

        // Return the predicted class value
        return trainingData.classAttribute().value((int) prediction);
    }

    public static void main(String[] args) throws Exception {
        try {
            WeatherPredictor weatherPredictor = new WeatherPredictor("weather_data.arff");

            // Build classifier
            weatherPredictor.buildClassifier();

            String[] possibilities = new String[]{"sunny", "rainy", "overcast"};
            // Perform random predictions

            int pred_yes=0;
            int pred_no=0;

            Random rand = new Random();
            for (int i = 0; i < 50; i++) {
                String outlook = possibilities[rand.nextInt(2)+1]; // Change accordingly
                double temp = rand.nextDouble() * 100;
                double humidity = rand.nextDouble() * 100;
                boolean windy = rand.nextBoolean();
                String prediction = weatherPredictor.predict(outlook, temp, humidity, windy);
                System.out.println("Prediction " + (i + 1) + ": " + prediction + " (Outlook: " + outlook +
                        ", Temperature: " + (temp - 32)*5/9 + ", Humidity: " + humidity + ", Windy: " + windy + ")");
                if(prediction.equals("yes")) pred_yes++;
                if(prediction.equals("no")) pred_no++;
            }
            System.out.println("summary:");
            System.out.println("Yes: " + (pred_yes));
            System.out.println("No: " + (pred_no));
            System.out.println("Instances: " + (pred_no + pred_yes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        WeatherPredictor weatherPredictor = new WeatherPredictor("weather_data.arff");

        weatherPredictor.buildClassifier();
        System.out.println(weatherPredictor.classifier);

        J48 classifier = (J48) weatherPredictor.classifier;

        FileWriter writer = new FileWriter("decision_tree.dot");
        writer.write(classifier.graph());
        writer.close();

        System.out.println("Decision tree exported to 'decision_tree.dot'");


        try {

            Evaluation eval = new Evaluation(weatherPredictor.trainingData);
            eval.crossValidateModel(weatherPredictor.classifier, weatherPredictor.trainingData, 10, new Random(1));

            System.out.println(eval.toSummaryString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
