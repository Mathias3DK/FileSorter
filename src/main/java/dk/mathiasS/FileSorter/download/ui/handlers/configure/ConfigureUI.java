package dk.mathiasS.FileSorter.download.ui.handlers.configure;

import dk.mathiasS.FileSorter.Main;
import dk.mathiasS.FileSorter.configuration.Module;
import dk.mathiasS.FileSorter.download.ui.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


//a bit messy will improve in further updates
public class ConfigureUI extends JPanel {

    public ConfigureUI() {

        setLayout(null);
        setSize(410, 535);

        // TITLE
        Font titleFont = new Font("Verdana", Font.BOLD, 20);
        JLabel titleLabel = new JLabel("Konfigurer Moduler");
        titleLabel.setFont(titleFont);
        titleLabel.setBounds(98, 20, 250, 60);

        button_create("Tilføj Fag", new int[]{100, 100, 200, 40}, new Color(51, 102, 236), () -> {
            AddModuleDialog dialog = new AddModuleDialog(this, (JFrame) getParentFrame());
            dialog.setVisible(true);
        });

        button_create("Fjern fag", new int[]{100, 145, 200, 40}, new Color(51, 102, 236), () -> {
            RemoveModuleDialog dialog = new RemoveModuleDialog(this, (JFrame) getParentFrame());
            dialog.setVisible(true);
        });

        JLabel listTitle = new JLabel("Moduler");
        listTitle.setFont(titleFont);
        listTitle.setBounds(98, 180, 250, 60);

        JTextArea field = new JTextArea("");
        field.setEditable(false);
        field.setFocusable(false);
        field.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        field.setBounds(100, 230, 200, 162);

        button_create("Gå tilbage", new int[]{100, 410, 200, 40}, new Color(38, 165, 246), () -> {
            ((JFrame) getParentFrame()).dispose();
            new Application();
        });

        for(Object obj : new Object[]{titleLabel, field, listTitle}){
            add((Component) obj);
        }
        updateModuleList();
    }

    private void button_create(String name, int[] pos, Color color, Runnable action) {

        int x = pos[0];
        int y = pos[1];

        int width = pos[2];
        int height = pos[3];

        JButton temp = new JButton(name);

        temp.setFocusable(false);
        temp.setForeground(Color.WHITE);
        temp.setBackground(color);
        temp.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        temp.setBounds(x, y, width, height);

        temp.addActionListener(ac -> {
            action.run();
        });

        this.add(temp);
    }

    public Container getParentFrame() {
        Container parent = this.getParent();
        while (parent != null && !(parent instanceof JFrame)) {
            parent = parent.getParent();
        }
        return parent;
    }

    private void updateModuleList() {
        JTextArea field = null;
        for (Component component : getComponents()) {
            if (component instanceof JTextArea) {
                field = (JTextArea) component;
                break;
            }
        }

        if (field != null) {
            StringBuilder sb = new StringBuilder();

            for (Module module : Main.classes) {

                try {
                    sb.append(" - [" + module.getId() + "] " + module.getName() + " (" + (module.getAlias() == null ? module.getName() : module.getAlias()) + ")").append("\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
            field.setText(" " + sb.toString().trim());
        }
    }

    static class AddModuleDialog extends JDialog {
        public AddModuleDialog(ConfigureUI instance, JFrame parentFrame) {
            super(parentFrame, "Tilføj fag", true);

            setResizable(false);
            setSize(270, 150);
            setLocationRelativeTo(parentFrame);

            JPanel panel = new JPanel();
            panel.setLayout(null);

            JLabel nameLabel = new JLabel("Fag:");
            nameLabel.setBounds(20, 20, 100, 30);

            JTextField nameField = new JTextField();
            nameField.setBounds(60, 20, 150, 30);

            JButton addButton = new JButton("Bekræft");

            addButton.setBackground(Color.WHITE);
            addButton.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            addButton.setBounds(85, 50, 100, 30);

            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = nameField.getText();

                    if (name.equalsIgnoreCase(" Angiv et fag") || name.equalsIgnoreCase(" Fag allerede tilføjet") || name.equalsIgnoreCase(" Ikke plads til flere fag"))
                        return;

                    if (name.isBlank()) {
                        nameField.setText(" Angiv et fag");
                        return;
                    }

                    if(Main.classes.size()>=10){
                        nameField.setText(" Ikke plads til flere fag");
                        return;
                    }

                    boolean con = false;
                    for (Module module : Main.classes) {
                        if (module.getName().equalsIgnoreCase(name)) {
                            con = true;
                            break;
                        }
                    }
                    if (con) {
                        nameField.setText(" Fag allerede tilføjet");
                        return;
                    }

                    Main.config.addSubject(nameField.getText());

                    try {
                        Main.classes = Main.initalizeClasses();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    instance.updateModuleList();

                    dispose();
                }
            });

            for (Object obj : new Object[]{nameField, nameLabel, addButton}) {
                panel.add((Component) obj);
            }

            add(panel);
        }
    }
    static class RemoveModuleDialog extends JDialog {
        public RemoveModuleDialog(ConfigureUI instance, JFrame parentFrame) {
            super(parentFrame, "Fjern fag", true);

            setResizable(false);
            setSize(270, 150);
            setLocationRelativeTo(parentFrame);

            JPanel panel = new JPanel();
            panel.setLayout(null);

            JLabel nameLabel = new JLabel("Fag:");
            nameLabel.setBounds(20, 20, 100, 30);

            JTextField nameField = new JTextField();
            nameField.setBounds(60, 20, 150, 30);

            JButton removeButton = new JButton("Bekræft");

            removeButton.setBackground(Color.WHITE);
            removeButton.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            removeButton.setBounds(85, 50, 100, 30);

            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = nameField.getText();

                    if (name.equalsIgnoreCase(" Angiv et fag") || name.equalsIgnoreCase(" Fag eksistere ikke"))
                        return;

                    boolean isNumeric = false;
                    int id = 0;

                    try {
                        Integer.parseInt(name);
                        isNumeric = true;
                    } catch (NumberFormatException ignored) {
                    }

                    if (isNumeric) {

                        id = Integer.parseInt(name);

                        //by id; Search
                        boolean con = false;
                        for (Module module : Main.classes) {
                            if (module.getId() == id) {
                                con = true;
                                break;
                            }
                        }
                        if (!con) {
                            nameField.setText(" Intet fag med id: " + id);
                            return;
                        }

                    } else {

                        if (name.isBlank()) {
                            nameField.setText(" Angiv et fag");
                            return;
                        }

                        boolean con = false;
                        for (Module module : Main.classes) {
                            if (module.getName().equalsIgnoreCase(name)) {
                                con = true;
                                break;
                            }
                        }
                        if (!con) {
                            nameField.setText(" Fag eksistere ikke");
                            return;
                        }
                    }

                    if (isNumeric) {
                        Main.config.removeSubject(id);
                    } else {
                        Main.config.removeSubject(name);
                    }

                    try {
                        Main.classes = Main.initalizeClasses();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    instance.updateModuleList();
                    dispose();
                }
            });

            for (Object obj : new Object[]{nameField, nameLabel, removeButton}) {
                panel.add((Component) obj);
            }

            add(panel);
        }
    }

}
