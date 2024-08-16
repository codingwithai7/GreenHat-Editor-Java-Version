// src/EditFunction.java
import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditFunction {
    final private GreenHatEditor greenHatEditor;
    final private UndoManager undoManager;

    public EditFunction(GreenHatEditor greenHatEditor, UndoManager undoManager) {
        this.greenHatEditor = greenHatEditor;
        this.undoManager = undoManager;
    }

    public void undo() {
        if (undoManager.canUndo()) {
            undoManager.undo();
        }
    }

    public void timeDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        String dateTime = sdf.format(new Date());
        greenHatEditor.textArea.insert(dateTime, greenHatEditor.textArea.getCaretPosition());
    }
}