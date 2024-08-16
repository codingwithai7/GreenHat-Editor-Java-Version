// src/FontDialog.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class FontDialog extends JDialog {

    private JList<String> fontList;
    private JList<String> styleList;
    private JList<Integer> sizeList;
    private JLabel sampleLabel;
    private JButton okButton;
    private JButton cancelButton;
    private GreenHatEditor greenHatEditor;

    public FontDialog(JFrame parent, GreenHatEditor greenHatEditor) {
        super(parent, "Font", true);
        this.greenHatEditor = greenHatEditor;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Font list
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontList = new JList<>(fonts);
        JScrollPane fontScrollPane = new JScrollPane(fontList);
        fontScrollPane.setPreferredSize(new Dimension(150, 150));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.BOTH;
        add(fontScrollPane, gbc);

        // Style list
        String[] styles = {"Regular", "Italic", "Bold", "Bold Italic"};
        styleList = new JList<>(styles);
        JScrollPane styleScrollPane = new JScrollPane(styleList);
        styleScrollPane.setPreferredSize(new Dimension(100, 80));
        gbc.gridx = 1;
        gbc.gridheight = 2;
        add(styleScrollPane, gbc);

        // Size list
        Integer[] sizes = {8, 10, 12, 14, 16, 18, 20, 22, 24, 28, 36, 48, 72};
        sizeList = new JList<>(sizes);
        JScrollPane sizeScrollPane = new JScrollPane(sizeList);
        sizeScrollPane.setPreferredSize(new Dimension(50, 80));
        gbc.gridx = 2;
        add(sizeScrollPane, gbc);

        // Sample label
        sampleLabel = new JLabel("AaBbYyZz");
        sampleLabel.setPreferredSize(new Dimension(200, 50));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        add(sampleLabel, gbc);

        // OK and Cancel buttons
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(buttonPanel, gbc);

        // Action listeners
        fontList.addListSelectionListener(e -> updateSample());
        styleList.addListSelectionListener(e -> updateSample());
        sizeList.addListSelectionListener(e -> updateSample());

        okButton.addActionListener(e -> {
            // Apply the selected font settings
            String selectedFont = fontList.getSelectedValue();
            int selectedStyle = styleList.getSelectedIndex();
            int selectedSize = sizeList.getSelectedValue();
            Font font = new Font(selectedFont, selectedStyle, selectedSize);
            greenHatEditor.textArea.setFont(font);
            setVisible(false);
        });

        cancelButton.addActionListener(e -> setVisible(false));

        pack();
        setLocationRelativeTo(parent);
    }

    private void updateSample() {
        String selectedFont = fontList.getSelectedValue();
        int selectedStyle = styleList.getSelectedIndex();
        Integer selectedSize = sizeList.getSelectedValue();

        if (selectedFont != null && selectedSize != null) {
            Font font = new Font(selectedFont, selectedStyle, selectedSize);
            sampleLabel.setFont(font);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            FontDialog dialog = new FontDialog(frame, null);
            dialog.setVisible(true);
        });
    }
}