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
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFileChooser fileChooser = new JFileChooser();
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        fileChooser.setDialogTitle("Vælg en fil");

        fileChooser.setMultiSelectionEnabled(true);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Word, Excel, PDF & Onenote", "docx", "xlsx", "one", "pdf");
        fileChooser.setFileFilter(filter);

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();

            int count=0;
            for(File file : files){
                if(!this.instance.cached.containsKey(file.getName())){
                    this.instance.cached.put(file.getName(), file);

                    count++;
                }
            }
            System.out.println("Tilføjede " + count + " file" + (count > 1 ? "r" : "") + " til cachen.");
            return true;
        }
    return false;
    }
}
