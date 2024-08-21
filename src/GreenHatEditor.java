// src/GreenHatEditor.java
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import javax.swing.undo.*;

public class GreenHatEditor extends JFrame implements ActionListener {
    public JTextArea textArea;
    public JFileChooser fileChooser;
    final private UndoManager undoManager = new UndoManager();
    FileFunction fileFunction = new FileFunction(this);
    EditFunction editFunction = new EditFunction(this, undoManager);
    ReplaceDialog replaceDialog;
    FindDialog findDialog;
    FontDialog fontDialog;
    MenuBuilder menuBuilder;
    public JFrame window;
    private boolean hasSearched = false;
    public String lastSearchText = "";
    private boolean wordWrapEnabled = false;
    public boolean isModified = false;

    public GreenHatEditor() {
        window = new JFrame("GreenHat Editor");
        window.setSize(600, 400);
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);

        textArea = new JTextArea();
        window.add(new JScrollPane(textArea));

        fileChooser = new JFileChooser();

        menuBuilder = new MenuBuilder(this);
        window.setJMenuBar(menuBuilder.createMenuBar());

        textArea.getDocument().addUndoableEditListener(undoManager);
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isModified = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isModified = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isModified = true;
            }
        });

        replaceDialog = new ReplaceDialog(this.textArea);
        findDialog = new FindDialog(window, this);
        fontDialog = new FontDialog(window, this);
    }

    public void find() {
        findDialog.setVisible(true);
        hasSearched = true;
    }

    public void findNext() {
        if (hasSearched) {
            findDialog.findNext();
        } else {
            find();
        }
    }

    public void replace() {
        replaceDialog.setVisible(true);
    }

    public void showFontDialog() {
        fontDialog.setVisible(true);
    }

    public void gotoLine() {
        String line = JOptionPane.showInputDialog(window, "Enter line number:");
        try {
            int pos = textArea.getLineStartOffset(Integer.parseInt(line) - 1);
            textArea.setCaretPosition(pos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(window, "Invalid line number");
        }
    }

    public void toggleWordWrap() {
        wordWrapEnabled = !wordWrapEnabled;
        textArea.setLineWrap(wordWrapEnabled);
        textArea.setWrapStyleWord(wordWrapEnabled);
        JMenuItem wordWrapMenuItem = this.menuBuilder.wordWrapMenuItem;
        wordWrapMenuItem.setSelected(wordWrapEnabled);
    }

    public boolean confirmSave() {
        if (!isModified) {
            return true;
        }
        int option = JOptionPane.showConfirmDialog(this, "Do you want to save changes?", "Save", JOptionPane.YES_NO_CANCEL_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            fileFunction.saveFile(false);
            return true;
        } else if (option == JOptionPane.NO_OPTION) {
            return true;
        }
        return false;
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
                find();
                break;
            case "Find Next":
                findNext();
                break;
            case "Replace...":
                replace();
                break;
            case "Select All":
                textArea.selectAll();
                break;
            case "Go To...":
                gotoLine();
                break;
            case "Word Wrap":
                toggleWordWrap();
                break;
            case "Font...":
                showFontDialog();
                break;
            case "Time/Date":
                editFunction.timeDate();
                break;
            case "About GreenHat":
                JOptionPane.showMessageDialog(window, "About GreenHat Editor");
                break;
            // Add more cases for other menu items
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GreenHatEditor editor = new GreenHatEditor();
            editor.window.setVisible(true);
        });
    }
}