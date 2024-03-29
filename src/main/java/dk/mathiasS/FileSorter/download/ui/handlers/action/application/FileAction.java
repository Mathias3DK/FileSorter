package dk.mathiasS.FileSorter.download.ui.handlers.action.application;

import dk.mathiasS.FileSorter.download.listener.DownloadListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileAction implements ActionListener {

    private final JButton button;
    private DownloadListener event;

    public FileAction(JButton field){
        this.button=field;

        this.event=new DownloadListener();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(this.button==null||!button.isVisible()) return;

        //if button is pressed
        if(button.isEnabled()){
            boolean enabled = event.toggle();
            button.setText((enabled ? "Stop" : "Start") + " Auto sortering");
        }
    }
}