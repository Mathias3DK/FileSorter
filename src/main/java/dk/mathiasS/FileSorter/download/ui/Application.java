package dk.mathiasS.FileSorter.download.ui;

import dk.mathiasS.FileSorter.download.manager.UploadManager;
import dk.mathiasS.FileSorter.download.ui.handlers.HandleCached;
import dk.mathiasS.FileSorter.download.ui.handlers.action.AddAction;
import dk.mathiasS.FileSorter.download.ui.handlers.action.ClearAction;
import dk.mathiasS.FileSorter.download.ui.handlers.action.FileAction;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.io.File;
import java.util.HashMap;

public class Application {

    private Font font;
    public HandleCached handleCached;

    public HashMap<String, File> cached = new HashMap<>();
    public Application() {
        this.font=new Font("Verdana", Font.BOLD, 20);

        this.open();
    }
    public void open() {
        JFrame frame = new JFrame("FilSortering af Mathias");
        //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Schje\\Downloads\\Icons8_flat_opened_folder.svg.png");
        frame.setIconImage(icon);

        //TITLE
        JLabel label = new JLabel("FIL SORTERING");
        label.setFont(this.font);
        label.setBounds(110,20,250,60);

        //functional buttons
        JButton configureBox=new JButton("Konfigurer moduler");

        configureBox.setBackground(new Color(38, 87, 246));
        configureBox.setBorder(new BasicBorders.FieldBorder(Color.BLACK,
                Color.BLACK,
                Color.BLACK,
                Color.BLACK));

        JButton startBox=new JButton("Start Fil Listener");
        JButton sortBox=new JButton("Sorter cached filer");
        JButton clearCacheBox=new JButton("Ryd cached filer");
        JButton addFile=new JButton("Tilføj filer til cache");

        //setting background and border for all looped fields
        for(JButton field : new JButton[]{addFile, clearCacheBox, startBox, sortBox}){
            field.setBackground(new Color(38, 165, 246));
            field.setBorder(new BasicBorders.FieldBorder(Color.BLACK,
                    Color.BLACK,
                    Color.BLACK,
                    Color.BLACK));
        }
        //list of cached files
        JTextArea field = new JTextArea("");
        field.setEditable(false);

        this.handleCached=new HandleCached(this,field);

        field.setBorder(new BasicBorders.FieldBorder(Color.BLACK,
                Color.BLACK,
                Color.BLACK,
                Color.BLACK));

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

        startBox.addActionListener(new FileAction(startBox));
        clearCacheBox.addActionListener(new ClearAction(clearCacheBox, handleCached));
        addFile.addActionListener(new AddAction(addFile, new UploadManager(this), this));

        //frame setup

        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(255, 255, 255));
        frame.setSize(410, 535);
        frame.setLocation(450,70);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}

