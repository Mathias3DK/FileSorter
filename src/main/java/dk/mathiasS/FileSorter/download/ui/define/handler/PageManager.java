package dk.mathiasS.FileSorter.download.ui.define.handler;

import dk.mathiasS.FileSorter.Main;
import dk.mathiasS.FileSorter.download.ui.define.DefineFileUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PageManager implements ActionListener {

    private final JButton button;
    private final DefineFileUI instance;

    public PageManager(DefineFileUI instance, JButton field){
        this.button=field;
        this.instance=instance;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(this.button==null||!button.isVisible()) return;

        //if button is pressed
        if(button.isEnabled()){
            if(button.getText().equalsIgnoreCase("Næste side")) {
                if(Main.classes.size()<this.instance.page * 6) return;
                this.instance.updatePage((this.instance.page + 1));
            }
            else if(button.getText().equalsIgnoreCase("Sidste side")){
                if(this.instance.page<=1) return;
                this.instance.updatePage((this.instance.page - 1));
            }

        }
    }
}