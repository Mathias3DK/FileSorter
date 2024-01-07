package dk.mathiasS.FileSorter.download.ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.util.ArrayList;

public class DefineFileUI extends JFrame {
    private final ArrayList<Object> components = new ArrayList<>();

    public DefineFileUI(){
        JFrame frame = new JFrame("Definer fil");
        Font font = new Font("Verdana", Font.BOLD, 20);

        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Schje\\Downloads\\Icons8_flat_opened_folder.svg.png");
        this.setIconImage(icon);

        this.setResizable(false);
        this.setSize(410, 535);
        this.setLocation(450,70);

        this.setLayout(null);
        this.setVisible(true);

        //TITLE
        JLabel label = new JLabel("Valg af moduler");
        label.setOpaque(false);
        label.setFont(font);
        label.setBounds(110,20,250,60);

        button("Matematik");
        button("Samfundsfag");
        button("Engelsk");
        button("Hej");
        button("Hej");
        button("Hej");
        button("Hej");


        this.components.add(label);

        initialize();
    }

    public void stop(){
        show();
    }

    public void initialize(){
        for(Object obj : components) this.add((Component) obj);

        //loop all classes and add them to list

    }
    public JButton button(String title){
        JButton temp=new JButton(title);

        temp.setFocusable(false);

        temp.setForeground(Color.WHITE);
        temp.setBackground(new Color(3, 60, 93));
        temp.setBorder(BorderFactory.createLineBorder(Color.black, 5));

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

        if(maxButtons <= a)
            {

            createPage();

        }

        return temp;
    }

    private void createPage() {

        JButton[] buttons=new JButton[]{
                new JButton("Næste side"),
                new JButton("Sidste side")
        };

        //safe check, if there isn't already existing buttons
        if(!this.components.contains(buttons[0])||!this.components.contains(buttons[1])){

            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setFocusable(false);

                buttons[i].setForeground(Color.WHITE);
                buttons[i].setBackground(new Color(4, 108, 168));
                buttons[i].setBorder(BorderFactory.createLineBorder(Color.black, 3));

                buttons[i].setBounds(90 + (i == 1 ? 120 : 0), 440, 100, 30);

                this.components.add(buttons[i]);
            }
        } else{
            //define buttons as already existing buttons
        }
    }


}
