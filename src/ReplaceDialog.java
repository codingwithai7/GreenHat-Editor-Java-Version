import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReplaceDialog extends JFrame {
    public JTextField findField;
    public JTextField replaceField;
    public JCheckBox matchCaseCheckBox;
    public JTextArea textArea;

    public ReplaceDialog(JTextArea textArea) {
        this.textArea = textArea;
        setTitle("Replace");
        setSize(400, 200);
        setLayout(new BorderLayout());

        // Create components
        JLabel findLabel = new JLabel("Find what:");
        findField = new JTextField();
        findField.setPreferredSize(new Dimension(200, 20)); // Set preferred size
        JLabel replaceLabel = new JLabel("Replace with:");
        replaceField = new JTextField();
        matchCaseCheckBox = new JCheckBox("Match case");

        JButton findNextButton = new JButton("Find Next");
        JButton replaceButton = new JButton("Replace");
        JButton replaceAllButton = new JButton("Replace All");
        JButton cancelButton = new JButton("Cancel");

        // Add action listeners
        findNextButton.addActionListener(new FindNextAction());
        replaceButton.addActionListener(new ReplaceAction());
        replaceAllButton.addActionListener(new ReplaceAllAction());
        cancelButton.addActionListener(e -> dispose());

        // Create a panel for the text fields and labels
        JPanel fieldsPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        fieldsPanel.add(findLabel);
        fieldsPanel.add(findField);
        fieldsPanel.add(replaceLabel);
        fieldsPanel.add(replaceField);
        fieldsPanel.add(matchCaseCheckBox);

        // Create a panel for the buttons
        JPanel buttonsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonsPanel.add(findNextButton);
        buttonsPanel.add(replaceButton);
        buttonsPanel.add(replaceAllButton);
        buttonsPanel.add(cancelButton);

        // Add panels to the frame
        add(fieldsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.EAST);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private class FindNextAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String text = findField.getText();
            String content = textArea.getText();
            int index = matchCaseCheckBox.isSelected() ? content.indexOf(text) : content.toLowerCase().indexOf(text.toLowerCase());

            if (index != -1) {
                textArea.select(index, index + text.length());
            } else {
                JOptionPane.showMessageDialog(ReplaceDialog.this, "Text not found.");
            }
        }
    }

    private class ReplaceAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String textToFind = findField.getText();
            String replacementText = replaceField.getText();
            String content = textArea.getText();
            int index = matchCaseCheckBox.isSelected() ? content.indexOf(textToFind) : content.toLowerCase().indexOf(textToFind.toLowerCase());

            if (index != -1) {
                textArea.replaceRange(replacementText, index, index + textToFind.length());
            } else {
                JOptionPane.showMessageDialog(ReplaceDialog.this, "Text not found.");
            }
        }
    }

    private class ReplaceAllAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String textToFind = findField.getText();
            String replacementText = replaceField.getText();
            String content = textArea.getText();
            String newContent = matchCaseCheckBox.isSelected() ? content.replace(textToFind, replacementText)
                    : content.toLowerCase().replace(textToFind.toLowerCase(), replacementText);
            textArea.setText(newContent);
        }
    }

    public static void main(String[] args) {
        JTextArea textArea = new JTextArea();
        JFrame mainFrame = new JFrame("Text Editor");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 300);
        mainFrame.setLayout(new BorderLayout());

        mainFrame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        JButton replaceButton = new JButton("Replace");
        replaceButton.addActionListener(e -> new ReplaceDialog(textArea).setVisible(true));
        mainFrame.add(replaceButton, BorderLayout.SOUTH);

        mainFrame.setVisible(true);
    }
}