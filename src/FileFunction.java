// src/FileFunction.java
import javax.swing.*;
import java.io.*;

public class FileFunction {
    final private GreenHatEditor greenHatEditor;

    public FileFunction(GreenHatEditor greenHatEditor) {
        this.greenHatEditor = greenHatEditor;
    }

    public void newFile() {
        if (greenHatEditor.textArea.getText().isEmpty() || greenHatEditor.confirmSave()) {
            greenHatEditor.textArea.setText("");
            greenHatEditor.setTitle("Untitled - GreenHat Editor");
            greenHatEditor.isModified = false;
        }
    }

    public void openFile() {
        if (greenHatEditor.fileChooser.showOpenDialog(this.greenHatEditor) == JFileChooser.APPROVE_OPTION) {
            File file = greenHatEditor.fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                greenHatEditor.textArea.setText("");
                String line;
                while ((line = reader.readLine()) != null) {
                    greenHatEditor.textArea.append(line + "\n");
                }
                greenHatEditor.setTitle(file.getName() + " - GreenHat Editor");
                greenHatEditor.isModified = false;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveFile(boolean saveAs) {
        if (saveAs || greenHatEditor.fileChooser.getSelectedFile() == null) {
            if (greenHatEditor.fileChooser.showSaveDialog(this.greenHatEditor) == JFileChooser.APPROVE_OPTION) {
                File file = greenHatEditor.fileChooser.getSelectedFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(greenHatEditor.textArea.getText());
                    greenHatEditor.setTitle(file.getName() + " - GreenHat Editor");
                    greenHatEditor.isModified = false;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            // Save to the existing file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(greenHatEditor.fileChooser.getSelectedFile()))) {
                writer.write(greenHatEditor.textArea.getText());
                greenHatEditor.isModified = false;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}