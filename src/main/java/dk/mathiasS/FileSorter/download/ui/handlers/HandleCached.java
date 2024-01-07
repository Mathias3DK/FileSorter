package dk.mathiasS.FileSorter.download.ui.handlers;

import dk.mathiasS.FileSorter.download.ui.Application;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class HandleCached {

    private final JTextArea field;
    private final Application main;

    public HandleCached(Application main, JTextArea field){
        this.field=field;
        this.main=main;
    }
    public void format(){

    }
    public void define() {
        clear(false);
        this.field.setBorder(null);

        StringBuilder add = new StringBuilder();

        for (File files : this.main.cached.values()) {
            add.append(files.getName()).append("<<");
        }

        String[] parts = (add.toString()).split("<<");

        int remainingFiles = this.main.cached.size(); // Initial value

        //rows of 3
        for (int i = 0; i < parts.length; i += 3) {
            //get last if there is less than 3
            int end = Math.min(i + 3, parts.length);

            if (canFitText()) {

                //add columns
                for (int j = i; j < end; j++) {

                    parts[j] = parts[j].replaceAll("\\s", "");
                    for (String replace : new String[]{".docx", ".xlsx", ".one"}) {
                        parts[j] = parts[j].replaceAll(replace, "");
                    }

                    int charLimit = ((j + 1) % 3 == 0 || j == end - 1) ? 7 : 10;
                    if (parts[j].length() > charLimit) {

                        parts[j] = parts[j].substring(0, charLimit);
                    }
                    this.field.append(parts[j]);

                    if (j < end - 1) {
                        this.field.append("   ");
                    }
                }

                remainingFiles -= (end - i);
                this.field.append(System.lineSeparator());
            } else {
                break;
            }
        }
        if (remainingFiles > 0) {
            String currentText = this.field.getText();

            // Split the current text by spaces
            String[] textArray = currentText.split("   ");

            // Check if there are enough elements to consider
            if (textArray.length > 0) {
                // Get the last element
                String lastElement = textArray[textArray.length - 1];
                System.out.println(lastElement);

                // Replace the last element with "Og x flere"
                String newText = currentText.substring(0, currentText.length() - (lastElement.length() + 3)) + "...Og " + remainingFiles + " flere.!";
                this.field.setText(newText);
            }
        }
    }
    private boolean canFitText() {
        int caretPosition = field.getCaretPosition();
        int documentLength = field.getDocument().getLength();

        // Get the visible rectangle in the JTextArea
        Rectangle visibleRect = field.getVisibleRect();

        // Get the font metrics to determine the line height
        int lineHeight = field.getFontMetrics(field.getFont()).getHeight();

        // Calculate the number of visible lines
        int visibleLines = (int) (visibleRect.getHeight() / lineHeight);

        // Check if the caret is at the end of the document and the new text fits in the visible area
        return caretPosition == documentLength &&
                (visibleLines >= field.getLineCount() || visibleRect.getHeight() == 0);
    }
    public void clear(boolean cache){
        if(cache) this.main.cached.clear();
        this.field.setText("");
    }
}