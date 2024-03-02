package dk.mathiasS.FileSorter.download.ui.define;

import dk.mathiasS.FileSorter.Main;
import dk.mathiasS.FileSorter.configuration.Module;
import dk.mathiasS.FileSorter.download.ui.define.handler.PageManager;
import dk.mathiasS.FileSorter.download.ui.handlers.action.configure.SelectModule;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class DefineFileUI extends JFrame {
    private final ArrayList<Object> components = new ArrayList<>();
    private final File file;
    public int page;

    public final JLabel pageViewer = new JLabel("Side 1");

    public DefineFileUI(File file) throws IOException {

        this.file=file;

        this.setTitle(file.getName());
        Font font = new Font("Verdana", Font.BOLD, 20);

        Image icon = Toolkit.getDefaultToolkit().getImage(new File(Main.class.getResource("/icon.png").getFile()).toURL());
        this.setIconImage(icon);

        this.setResizable(false);
        this.setSize(410, 535);
        this.setLocationRelativeTo(null);

        this.setLayout(null);
        this.setVisible(true);

        //TITLE
        JLabel label = new JLabel("Valg af Moduler");
        label.setFont(font);
        label.setBounds(110,20,250,60);

        pageViewer.setFont(new Font("Verdana", Font.BOLD, 12));
        pageViewer.setBounds(178,400,250,60);

        //classes
        for(Module module : Main.initalizeClasses()) {
            JButton button = button(module.getName());
            new SelectModule(button, this.file);
        }

        this.page=1;
        this.components.add(label); this.components.add(pageViewer);

        initialize();
    }

    public void updatePage(int page) {
        this.page = page;

        int maxHeight = this.size().height - 210; // Maximum available height for buttons
        int buttonHeight = 40; // Height of each button
        int spaceBetweenButtons = buttonHeight / 4; // Space between buttons

        int itemsPerPage = maxHeight / (buttonHeight + spaceBetweenButtons) + 1;
        int startIndex = ((page - 1) * itemsPerPage);
        int endIndex = Math.min(startIndex + itemsPerPage, Main.classes.size());

        Iterator<Object> iterator = components.iterator();
        while (iterator.hasNext()) {
            Object comp = iterator.next();
            if (comp instanceof JButton) {
                this.remove((Component) comp);
                iterator.remove();
            }
        }

        for (int i = startIndex; i < endIndex; i++) {
            button(Main.classes.get(i).getName());
        }

        revalidate();
        repaint();

        pageViewer.setText("Side " + page);

        if (!Main.classes.isEmpty()) {
            createPage();
        }
        initialize();
    }


    public void stop(){
        show();
    }

    public void initialize(){
        for(Object obj : components) this.add((Component) obj);

    }
    public JButton button(String title){
        JButton temp=new JButton(title);

        temp.setFocusable(false);

        temp.setForeground(Color.WHITE);
        temp.setBackground(new Color(3, 60, 93));
        temp.setBorder(BorderFactory.createLineBorder(Color.black, 3));

        int a = 0;
        for(Object comp : this.components){
            if (comp instanceof JButton){
                a++;
            }
        }
        int y = 90;
        int sHeight = 40;
        int sWidth = 200;

        y = (y + (a > 0 ? ((sHeight * a)) + (sHeight / 5 * a) : 0));
        if(90 + y <= this.size().height - 30) {
            temp.setBounds(100, y, sWidth, sHeight);

            this.components.add(temp);
        } else{
            System.out.println("Not enough space for the new button!");
        }

        int maxHeight = this.size().height - 210; // Maximum available height for buttons
        int buttonHeight = 40; // Height of each button
        int spaceBetweenButtons = buttonHeight / 4; // Space between buttons

        int maxButtons = maxHeight / (buttonHeight + spaceBetweenButtons);

        if(maxButtons < a && Main.classes.size() > a)
            {

            createPage();

            }

        return temp;
    }

    private void createPage() {

        JButton[] buttons=new JButton[]{
                new JButton("Sidste side"),
                new JButton("Næste side")
        };


        for (int i = 0; i < buttons.length; i++) {

            buttons[i].addActionListener(new PageManager(this, buttons[i]));

            buttons[i].setFocusable(false);

            buttons[i].setForeground(Color.WHITE);
            buttons[i].setBackground(new Color(4, 108, 168));
            buttons[i].setBorder(BorderFactory.createLineBorder(Color.black, 3));

            buttons[i].setBounds(90 + (i == 1 ? 120 : 0), 440, 100, 30);

            this.components.add(buttons[i]);
        }

    }


}
