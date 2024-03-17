import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal","unused"})
public class ButtonCSVRemover extends JFrame {

    private ArrayList<String> buttonValues;
    private int leftColumnEndIndex;

    public ButtonCSVRemover() {
        setTitle("Buttons from CSV");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(0, 3, 5, 5));
        buttonValues = new ArrayList<>();

        String csvFile = FileSelector.readFromFile("output");
        try (BufferedReader br = new BufferedReader(new FileReader(Objects.requireNonNull(csvFile)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                buttonValues.addAll(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        leftColumnEndIndex = buttonValues.size();

        for (String value : buttonValues) {
            JButton button = new JButton(value);
            button.addActionListener(new ButtonListener(value));
            add(button);
        }

        setVisible(true);
    }

    private class ButtonListener implements ActionListener {
        private String value;

        public ButtonListener(String value) {
            this.value = value;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            replaceValueInCSV(value);
            ((JButton) e.getSource()).setText("");
        }
    }

    private void replaceValueInCSV(String valueToReplace) {
        String csvFile = FileSelector.readFromFile("output");
        try {
            List<String> lines = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(Objects.requireNonNull(csvFile)));
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (int i = 0; i < values.length; i++) {
                    if (values[i].equals(valueToReplace)) {
                        values[i] = "";
                    }
                }
                lines.add(String.join(",", values));
            }
            br.close();

            FileWriter writer = new FileWriter(csvFile);
            for (String updatedLine : lines) {
                writer.write(updatedLine + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ButtonCSVRemover::new);
    }
}
