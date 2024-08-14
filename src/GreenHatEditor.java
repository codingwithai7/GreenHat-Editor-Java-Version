import javax.swing.*;
import java.awt.event.*;
import javax.swing.undo.*;

public class GreenHatEditor extends JFrame implements ActionListener {
    public JTextArea textArea;
    public JFileChooser fileChooser;
    private UndoManager undoManager = new UndoManager();
    FileFunction fileFunction = new FileFunction(this);
    EditFunction editFunction = new EditFunction(this, undoManager);

    public GreenHatEditor() {
        setTitle("GreenHat Editor");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        textArea = new JTextArea();
        add(new JScrollPane(textArea));

        fileChooser = new JFileChooser();

        MenuBuilder menuBuilder = new MenuBuilder(this);
        setJMenuBar(menuBuilder.createMenuBar());

        textArea.getDocument().addUndoableEditListener(undoManager);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "New":
                fileFunction.newFile();
                break;
            case "Open...":
                fileFunction.openFile();
                break;
            case "Save":
                fileFunction.saveFile(false);
                break;
            case "Save As...":
                fileFunction.saveFile(true);
                break;
            case "Exit":
                System.exit(0);
                break;
            case "Undo":
                editFunction.undo();
                break;
            case "Cut":
                textArea.cut();
                break;
            case "Copy":
                textArea.copy();
                break;
            case "Paste":
                textArea.paste();
                break;
            case "Delete":
                textArea.replaceSelection("");
                break;
            case "Find...":
                // Implement find functionality
                break;
            case "Replace...":
                // Implement replace functionality
                break;
            case "Select All":
                textArea.selectAll();
                break;

            case "About GreenHat":
                JOptionPane.showMessageDialog(this, "About GreenHat Editor");
                break;
            // Add more cases for other menu items
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GreenHatEditor editor = new GreenHatEditor();
            editor.setVisible(true);
        });
    }
}
