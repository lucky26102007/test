import java.io.*;
import java.util.Properties;
import javax.swing.*;
@SuppressWarnings("UnnecessaryReturnStatement")

public class FileSelector {
    public static String input;
    public static String output;
    public static Properties properties = new Properties();
    public static File configFile = new File("config.properties");



    public static void main(String[] args) {

        FileSelector fileSelector = new FileSelector();
        fileSelector.selectFilesAndSave();
        fileSelector.checkFileLocationsExistence();
    }

    public void selectFilesAndSave() {
        if (configFile.exists() && configFile.length() > 0) {
            readFromFile("input");
            readFromFile("output");
        } else {
            selectFilesAndSaveWithDialogs();
        }
    }

    public void checkFileLocationsExistence() {
        File inputFile = new File(input);
        File outputFile = new File(output);

        if (!inputFile.exists() || !outputFile.exists()) {


            input = null;
            output = null;

            selectFilesAndSaveWithDialogs();
        }
    }

    private void selectFilesAndSaveWithDialogs() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);

        JOptionPane.showMessageDialog(null, "Please select the input file.");
        int inputSelection = fileChooser.showOpenDialog(null);
        if (inputSelection == JFileChooser.APPROVE_OPTION) {
            File inputFile = fileChooser.getSelectedFile();
            input = inputFile.getAbsolutePath();
            saveToFile("input", input);
        } else {
            return;
        }

        JOptionPane.showMessageDialog(null, "Please select the output file.");
        int outputSelection = fileChooser.showOpenDialog(null);
        if (outputSelection == JFileChooser.APPROVE_OPTION) {
            File outputFile = fileChooser.getSelectedFile();
            output = outputFile.getAbsolutePath();
            saveToFile("output", output);
        } else {
            return;
        }

    }

    private void saveToFile(String property, String address) {
        try {
            properties.load(new FileInputStream("config.properties"));
            FileWriter writer = new FileWriter("config.properties", true);
            properties.setProperty(property, address);
            properties.store(writer, "File addresses");
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String readFromFile(String option) {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("config.properties"));
            input = properties.getProperty("input");
            output = properties.getProperty("output");



        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if(option.equals("input")){
            return input;
        } else if(option.equals("output")){
            return output;
        } else {
            return null;
        }
    }
}
