import javax.swing.*;

public class MenuBuilder {
    private GreenHatEditor greenHatEditor;

    public JMenuItem newMenuItem;
    public JMenuItem openMenuItem;
    public JMenuItem saveMenuItem;
    public JMenuItem saveAsMenuItem;
    public JMenuItem pageSetupMenuItem;
    public JMenuItem printMenuItem;
    public JMenuItem exitMenuItem;
    public JMenuItem undoMenuItem;
    public JMenuItem cutMenuItem;
    public JMenuItem copyMenuItem;
    public JMenuItem pasteMenuItem;
    public JMenuItem deleteMenuItem;
    public JMenuItem findMenuItem;
    public JMenuItem findNextMenuItem;
    public JMenuItem replaceMenuItem;
    public JMenuItem goToMenuItem;
    public JMenuItem selectAllMenuItem;
    public JMenuItem timeDateMenuItem;
    public JMenuItem wordWrapMenuItem;
    public JMenuItem fontMenuItem;
    public JMenuItem statusBarMenuItem;
    public JMenuItem viewHelpMenuItem;
    public JMenuItem aboutGreenHatMenuItem;

    public MenuBuilder(GreenHatEditor greenHatEditor) {
        this.greenHatEditor = greenHatEditor;
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu fileMenu = new JMenu("File");
        newMenuItem = addItem(fileMenu, "New");
        openMenuItem = addItem(fileMenu, "Open...");
        saveMenuItem = addItem(fileMenu, "Save");
        saveAsMenuItem = addItem(fileMenu, "Save As...");
        fileMenu.addSeparator();
        pageSetupMenuItem = addItem(fileMenu, "Page Setup...");
        printMenuItem = addItem(fileMenu, "Print");
        fileMenu.addSeparator();
        exitMenuItem = addItem(fileMenu, "Exit");
        menuBar.add(fileMenu);

        // Edit Menu
        JMenu editMenu = new JMenu("Edit");
        undoMenuItem = addItem(editMenu, "Undo");
        editMenu.addSeparator();
        cutMenuItem = addItem(editMenu, "Cut");
        copyMenuItem = addItem(editMenu, "Copy");
        pasteMenuItem = addItem(editMenu, "Paste");
        deleteMenuItem = addItem(editMenu, "Delete");
        editMenu.addSeparator();
        findMenuItem = addItem(editMenu, "Find...");
        findNextMenuItem = addItem(editMenu, "Find Next");
        replaceMenuItem = addItem(editMenu, "Replace...");
        goToMenuItem = addItem(editMenu, "Go To...");
        editMenu.addSeparator();
        selectAllMenuItem = addItem(editMenu, "Select All");
        timeDateMenuItem = addItem(editMenu, "Time/Date");
        menuBar.add(editMenu);

        // Format Menu
        JMenu formatMenu = new JMenu("Format");
        wordWrapMenuItem = addItem(formatMenu, "Word Wrap");
        fontMenuItem = addItem(formatMenu, "Font...");
        menuBar.add(formatMenu);

        // View Menu
        JMenu viewMenu = new JMenu("View");
        statusBarMenuItem = addItem(viewMenu, "Status Bar");
        menuBar.add(viewMenu);

        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        viewHelpMenuItem = addItem(helpMenu, "View Help");
        aboutGreenHatMenuItem = addItem(helpMenu, "About GreenHat");
        menuBar.add(helpMenu);

        return menuBar;
    }

    private JMenuItem addItem(JMenu menu, String name) {
        JMenuItem menuItem = new JMenuItem(name);
        menuItem.addActionListener(greenHatEditor);
        menuItem.setActionCommand(name);
        menu.add(menuItem);
        return menuItem;
    }
}