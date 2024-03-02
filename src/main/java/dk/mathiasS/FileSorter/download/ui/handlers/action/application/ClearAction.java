package dk.mathiasS.FileSorter.download.ui.handlers.action.application;

import dk.mathiasS.FileSorter.download.ui.handlers.HandleCached;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClearAction implements ActionListener {

    private final JButton button;
    private final HandleCached manager;

    public ClearAction(JButton field, HandleCached manager){
        this.button=field;
        this.manager=manager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(this.button==null||!button.isVisible()) return;

        //if button is pressed
        if(button.isEnabled()){
            manager.clear(true);


        }
    }
}
