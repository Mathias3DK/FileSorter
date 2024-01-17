package dk.mathiasS.FileSorter.download.ui.handlers.action;

import dk.mathiasS.FileSorter.Main;
import dk.mathiasS.FileSorter.download.listener.DownloadListener;
import dk.mathiasS.FileSorter.download.manager.UploadManager;
import dk.mathiasS.FileSorter.download.ui.Application;
import dk.mathiasS.FileSorter.download.ui.handlers.HandleCached;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAction implements ActionListener {

    private final JButton button;
    private UploadManager manager;
    private Application instance;

    public AddAction(JButton field, UploadManager manager, Application instance){
        this.button=field;
        this.manager=manager;

        this.instance=instance;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(this.button==null||!button.isVisible()) return;

        //if button is pressed
        if(button.isEnabled()){
            try {
                boolean con=manager.openChooser();

                if(con)
                {
                    this.instance.handleCached.define();
                    }
            } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                     IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
