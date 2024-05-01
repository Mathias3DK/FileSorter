package dk.mathiasS.FileSorter.download.ui.handlers.action.configure;

import dk.mathiasS.FileSorter.download.manager.SortManager;
import dk.mathiasS.FileSorter.model.TrainClassPredictor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

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

            ((JFrame) getParentFrame()).dispose();

            TrainClassPredictor trainer = new TrainClassPredictor(this.file, button.getText());
            try {
                trainer.classPredictor_predict();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            new SortManager(this.file).sort();


        }
    }

    public Container getParentFrame() {
        Container parent = button.getParent();
        while (parent != null && !(parent instanceof JFrame)) {
            parent = parent.getParent();
        }
        return parent;
    }

}


