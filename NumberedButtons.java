import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
@SuppressWarnings("FieldMayBeFinal")

public class NumberedButtons extends JFrame {
    JFrame numWind = new JFrame();

    public NumberedButtons() {
        numWind.setTitle("Numbered Buttons");
        numWind.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        numWind.setSize(400, 300);
        numWind.setLocationRelativeTo(null);
        numWind.setLayout(new GridLayout(1, 7, 5, 5));

        for (int i = 1; i <= 7; i++) {
            JButton button = new JButton(String.valueOf(i));
            button.addActionListener(new ButtonListener(i));
            numWind.add(button);
        }

        numWind.setVisible(true);
    }
    private class ButtonListener implements ActionListener {
        private int value;
        public ButtonListener(int value) {
            this.value = value;
        }
        public void actionPerformed(ActionEvent e) {
            CSVData.randomPairings(value);
            numWind.dispose();
            ButtonCSVRemover.main(null);
        }
    }
    public static void main(String[] args) {
        FileSelector.main(null);
        SwingUtilities.invokeLater(NumberedButtons::new);
    }
}
