package dk.mathiasS.FileSorter.download.ui.handlers;

import dk.mathiasS.FileSorter.download.ui.Application;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class HandleCached {

    private final JTextArea field;
    private final Application main;
    private int components;

    public HandleCached(Application main, JTextArea field){
        this.field=field;
        this.components=0;

        this.main=main;

    }
    public void format(){

    }
    public void define() {
        StringBuilder add = new StringBuilder();

        for(File files: this.main.cached.values()){
            add.append(files.getName()).append("<<");
        }
        String[] parts = (add.toString()).split("<<");
        this.components = parts.length;

        // Opdel teksten i grupper af tre og tilføj til tekstområdet
        for (int i = 0; i < parts.length; i += 3) {
            // Hvis der er mindre end tre elementer tilbage, brug resten
            int end = Math.min(i + 3, parts.length);

            // Kontroller om der er mere plads i JTextArea
            if (canFitText()) {

                // Tilføj teksten i kolonner
                for (int j = i; j < end; j++) {
                    // Fjern alle mellemrum i teksten
                    parts[j] = parts[j].replaceAll("\\s", "");

                    // Tjek om teksten overskrider karaktergrænsen
                    if (parts[j].length() > 16) {
                        // Hvis det gør, forkort teksten
                        parts[j] = parts[j].substring(0, 16);
                    }
                    this.field.append(parts[j]);

                    // Tjek om det er sidste element i rækken
                    if (j < end - 1) {
                        // Tilføj mellemrum mellem elementer i samme række
                        this.field.append("   ");
                    } else {
                        // Tilføj linjeskift efter sidste element i rækken
                        this.field.append(System.lineSeparator());
                    }
                }
            } else {
                // Stop loopet, hvis der ikke er mere plads
                break;
            }
        }
    }
    private boolean canFitText() {
        // Få det synlige rektangel i JTextArea
        Rectangle visibleRect = field.getVisibleRect();

        // Tjek om der er plads til mere tekst baseret på det synlige rektangel
        return visibleRect.getHeight() > 0 && visibleRect.getWidth() > 0;
    }


    public void clear(){
        this.main.cached.clear();
        this.field.setText("");
        this.components=0;
    }
}
