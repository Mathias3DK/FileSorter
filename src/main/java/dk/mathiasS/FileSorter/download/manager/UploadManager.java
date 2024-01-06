package dk.mathiasS.FileSorter.download.manager;

import dk.mathiasS.FileSorter.download.ui.Application;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.HashMap;

public class UploadManager {

    private final Application instance;

    public UploadManager(Application instance){
        this.instance=instance;
    }

    public boolean openChooser() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //set Look and Feel to Windows
        JFileChooser fileChooser = new JFileChooser(); //Create a new GUI that will use the current(windows) Look and Feel
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); //revert the Look and Feel back to the ugly Swing
        fileChooser.setDialogTitle("Vælg en fil");

        fileChooser.setMultiSelectionEnabled(true);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Word, Excel & Onenote", "docx", "xlsx", "one");
        fileChooser.setFileFilter(filter);

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();

            System.out.println(this.instance.cached);
            for(File file : files){
                if(!this.instance.cached.containsKey(file.getName()))
                    this.instance.cached.put(file.getName(), file);

            }
            System.out.println("Tilføjede " + files.length + " filer til cachen.");
            return true;
        }
    return false;
    }
}
