package dk.mathiasS.FileSorter.download.ui.handlers.action.application;

import dk.mathiasS.FileSorter.download.manager.SortManager;
import dk.mathiasS.FileSorter.download.ui.Application;
import dk.mathiasS.FileSorter.download.ui.handlers.configure.ConfigureUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ConfigureAction implements ActionListener {

    private final JButton button;
    private Application application;

    public ConfigureAction(JButton field, Application application){
        this.button=field;
        this.application=application;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(this.button==null||!button.isVisible()) return;

        //if button is pressed
        if(button.isEnabled()){
             new ConfigureUI();
        }
    }

}
