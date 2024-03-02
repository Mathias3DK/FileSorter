package dk.mathiasS.FileSorter.download.ui;

import dk.mathiasS.FileSorter.download.manager.UploadManager;
import dk.mathiasS.FileSorter.download.ui.handlers.HandleCached;
import dk.mathiasS.FileSorter.download.ui.handlers.action.application.AddAction;
import dk.mathiasS.FileSorter.download.ui.handlers.action.application.ClearAction;
import dk.mathiasS.FileSorter.download.ui.handlers.action.application.FileAction;
import dk.mathiasS.FileSorter.download.ui.handlers.action.configure.ConfigureAction;
import dk.mathiasS.FileSorter.download.ui.handlers.configure.ConfigureUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;

public class Application {

    private final JFrame frame;
    private final Font font;
    public HandleCached handleCached;
    public HashMap<String, File> cached = new HashMap<>();

    public Application() {
        this.font=new Font("Verdana", Font.BOLD, 20);
        this.frame = new JFrame("Fil Sortering af M.O.S");

        this.open();
    }
    public void open() {
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Schje\\Downloads\\Icons8_flat_opened_folder.svg.png");
        frame.setIconImage(icon);

        //TITLE
        JLabel label = new JLabel("FIL SORTERING");
        label.setFont(this.font);
        label.setBounds(110,20,250,60);

        //functional buttons
        JButton configureBox=new JButton("Konfigurer moduler");
        configureBox.setFocusable(false);

        configureBox.setBackground(new Color(35, 80, 196));
        configureBox.setForeground(new Color(255, 255, 255));
        configureBox.setBorder(BorderFactory.createLineBorder(Color.black, 3));

        JButton startBox=new JButton("Start Auto sortering");
        JButton sortBox=new JButton("Sorter cached filer");
        JButton clearCacheBox=new JButton("Ryd cached filer");
        JButton addFile=new JButton("Tilføj filer til cache");

        //setting background and border for all looped fields
        for(JButton field : new JButton[]{addFile, clearCacheBox, startBox, sortBox}){
            field.setFocusable(false);
            field.setBackground(new Color(38, 165, 246));
            field.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        }
        //list of cached files
        JTextArea field = new JTextArea("");
        field.setEditable(false);
        field.setFocusable(false);

        this.handleCached=new HandleCached(this,field);
        this.handleCached.define();

        field.setBorder(BorderFactory.createLineBorder(Color.black, 5));

        //field bounds
        configureBox.setBounds(100,275,200,40);
        addFile.setBounds(100,210,200,40);
        clearCacheBox.setBounds(100,170,200,40);
        startBox.setBounds(100,130,200,40);
        sortBox.setBounds(100,90,200,40);

        JLabel cacheTitle = new JLabel("CACHED FILER");
        cacheTitle.setFont(this.font);
        cacheTitle.setBounds(110,310,250,60);

        field.setBounds(100, 360, 200, 90);

        //adding all objects to frame
        for(Object obj : new Object[]{configureBox, cacheTitle, label,field, addFile, clearCacheBox, startBox, sortBox}){
            frame.add((Component) obj);
        }

        SwingUtilities.invokeLater(() -> startBox.addActionListener(new FileAction(startBox)));
        SwingUtilities.invokeLater(() -> clearCacheBox.addActionListener(new ClearAction(clearCacheBox, handleCached)));
        SwingUtilities.invokeLater(() -> addFile.addActionListener(new AddAction(addFile, new UploadManager(this), this)));

        configureBox.addActionListener(e -> showConfigureUI());

        //frame setup
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(255, 255, 255, 255));
        frame.setSize(410, 535);
        frame.setLocationRelativeTo(null);
        //frame.setLocation(450,70);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private void showConfigureUI() {
        ConfigureUI configureUI = new ConfigureUI();
        frame.getContentPane().removeAll();
        frame.getContentPane().add(configureUI);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }


}

