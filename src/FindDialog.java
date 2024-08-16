// src/FindDialog.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FindDialog extends JDialog {

    private GreenHatEditor greenHatEditor;
    private JTextField findTextField;
    private JCheckBox matchCaseCheckBox;
    private JRadioButton upRadioButton;
    private JRadioButton downRadioButton;
    private JButton findNextButton;
    private JButton cancelButton;

    public FindDialog(JFrame parent, GreenHatEditor greenHatEditor) {
        super(parent, "Find", true);
        this.greenHatEditor = greenHatEditor;
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Find what label and text field
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Find what:"), gbc);

        findTextField = new JTextField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(findTextField, gbc);

        // Match case checkbox
        matchCaseCheckBox = new JCheckBox("Match case");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(matchCaseCheckBox, gbc);

        // Direction radio buttons
        JPanel directionPanel = new JPanel();
        directionPanel.setBorder(BorderFactory.createTitledBorder("Direction"));
        upRadioButton = new JRadioButton("Up");
        downRadioButton = new JRadioButton("Down", true);

        ButtonGroup directionGroup = new ButtonGroup();
        directionGroup.add(upRadioButton);
        directionGroup.add(downRadioButton);

        directionPanel.add(upRadioButton);
        directionPanel.add(downRadioButton);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(directionPanel, gbc);

        // Find Next and Cancel buttons
        findNextButton = new JButton("Find Next");
        cancelButton = new JButton("Cancel");

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(findNextButton, gbc);

        gbc.gridy = 1;
        add(cancelButton, gbc);

        // Add action listeners
        findNextButton.addActionListener(new FindNextAction());
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        pack();
        setLocationRelativeTo(parent);
    }

    public void findNext() {
        new FindNextAction().actionPerformed(null);
    }

    private class FindNextAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchText = findTextField.getText();
            if (searchText.isEmpty()) {
                searchText = greenHatEditor.lastSearchText;
            } else {
                greenHatEditor.lastSearchText = searchText;
            }
            String content = greenHatEditor.textArea.getText();
            boolean matchCase = matchCaseCheckBox.isSelected();
            boolean searchUp = upRadioButton.isSelected();
            int startIndex = greenHatEditor.textArea.getCaretPosition();
            int index;

            if (!matchCase) {
                content = content.toLowerCase();
                searchText = searchText.toLowerCase();
            }

            if (searchUp) {
                index = content.lastIndexOf(searchText, startIndex - 1);
            } else {
                index = content.indexOf(searchText, startIndex);
            }

            if (index != -1) {
                greenHatEditor.textArea.select(index, index + searchText.length());
                greenHatEditor.textArea.setCaretPosition(index + (searchUp ? 0 : searchText.length()));
                greenHatEditor.textArea.requestFocusInWindow();
            } else {
                JOptionPane.showMessageDialog(FindDialog.this, "Text not found.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GreenHatEditor editor = new GreenHatEditor();
            FindDialog dialog = new FindDialog(editor, editor);
            dialog.setVisible(true);
        });
    }
}