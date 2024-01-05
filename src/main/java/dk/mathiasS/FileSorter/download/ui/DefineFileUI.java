package dk.mathiasS.FileSorter.download.ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.util.Arrays;

public class DefineFileUI extends JFrame {

    private final JFrame frame;
    private final Font font;

    private Object[] components;

    public DefineFileUI(){
        this.frame=new JFrame("Definer fil");
        this.font=new Font("Verdana", Font.BOLD, 20);

        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Schje\\Downloads\\Icons8_flat_opened_folder.svg.png");
        this.frame.setIconImage(icon);

        this.frame.setResizable(false);
        this.frame.setSize(410, 535);
        this.frame.setLocation(450,70);

        this.frame.setLayout(null);
        this.frame.setVisible(true);

        //TITLE
        JLabel label = new JLabel("FIL SORTERING");
        label.setFont(this.font);
        label.setBounds(110,20,250,60);

        JButton test = button("Hej");

        this.components=new Object[]{label, test};

        initialize();
    }

    public void initialize(){
        for(Object obj : components) this.frame.add((Component) obj);

        //loop all classes and add them to list

    }
    public JButton button(String title){
        JButton temp=new JButton(title);
        temp.setBackground(new Color(38, 165, 246));
        temp.setBorder(new BasicBorders.FieldBorder(Color.BLACK,
                Color.BLACK,
                Color.BLACK,
                Color.BLACK));

        temp.setBounds(100,210,200,40);


        return temp;
    }
}
