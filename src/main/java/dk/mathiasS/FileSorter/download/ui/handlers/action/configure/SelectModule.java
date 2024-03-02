package dk.mathiasS.FileSorter.download.ui.handlers.action.configure;

import dk.mathiasS.FileSorter.download.manager.SortManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SelectModule implements ActionListener {

    private final JButton button;
    private final File file;

    public SelectModule(JButton field, File file){
        this.button=field;
        this.file = file;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(this.button==null||!button.isVisible()) return;

        //if button is pressed
        if(button.isEnabled()){
            new SortManager(this.file).sort();

        }
    }
}
