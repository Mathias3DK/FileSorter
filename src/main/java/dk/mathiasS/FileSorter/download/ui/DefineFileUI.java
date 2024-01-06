package dk.mathiasS.FileSorter.download.ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

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
        JLabel label = new JLabel("FIL SORTERING");
        label.setOpaque(false);
        label.setFont(font);
        label.setBounds(110,20,250,60);

        JButton test = button("Hej");
        button("hejsa");

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

        temp.setBackground(new Color(38, 165, 246));
        temp.setBorder(new BasicBorders.FieldBorder(Color.BLACK,
                Color.BLACK,
                Color.BLACK,
                Color.BLACK));

        int a = 0;
        for(Object comp : this.components){
            if (comp instanceof JButton){
                a++;
            }
        }

        int y = 90;
        int sHeight = 40;
        int sWidth = 200;

        y = (y + (a > 0 ? ((sHeight * a)) + (sHeight / 4 * a) : 0));
        if(90 + y <= this.size().height) {
            temp.setBounds(100, y, sWidth, sHeight);
        } else{
            System.out.println("Not enough space for the new button!");
        }

        this.components.add(temp);

        return temp;
    }
}
