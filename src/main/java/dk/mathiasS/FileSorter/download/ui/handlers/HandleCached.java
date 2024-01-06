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
        clear(false);
        this.field.setBorder(null);

        StringBuilder add = new StringBuilder();

        for (File files : this.main.cached.values()) {
            add.append(files.getName()).append("<<");
        }
        String[] parts = (add.toString()).split("<<");
        this.components = parts.length;

        // Opdel teksten i grupper af tre og tilføj til tekstområdet
        int columns = calculateOptimalColumns(parts);

        for (int i = 0; i < parts.length; i += columns) {
            int end = Math.min(i + columns, parts.length);

            // Kontroller om der er mere plads i JTextArea
            if (canFitText()) {

                this.components++;

                // Tilføj teksten i kolonner
                for (int j = i; j < end; j++) {
                    // Fjern alle mellemrum i teksten
                    parts[j] = parts[j].replaceAll("\\s", "");
                    for (String replace : new String[]{".docx", ".xlsx", ".one"}) {
                        parts[j] = parts[j].replaceAll(replace, "");
                    }

                    // Tjek om teksten overskrider karaktergrænsen
                    if (parts[j].length() > 10) {
                        // Hvis det gør, forkort teksten
                        parts[j] = parts[j].substring(0, 10);
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

        // Add a message for the remaining files
        int remainingFiles = this.main.cached.size() - components;
        if (remainingFiles > 0 && canFitText()) {
            this.field.append("Og " + remainingFiles + " flere filer..!");
        }
    }

    private int calculateOptimalColumns(String[] parts) {
        int caretPosition = field.getCaretPosition();
        int documentLength = field.getDocument().getLength();

        // Get the visible rectangle in the JTextArea
        Rectangle visibleRect = field.getVisibleRect();

        // Calculate the maximum number of visible columns based on the visible rectangle width
        int maxVisibleColumns = (int) (visibleRect.getWidth() / (10 * 4)); // Assuming each part has a length of 10 characters

        // Calculate the optimal number of columns based on the actual character lengths of the parts
        int columns = calculateColumnsBasedOnLength(parts, maxVisibleColumns);

        // Check if the caret is at the end of the document and there's enough space for the calculated columns
        return caretPosition == documentLength ? Math.max(1, columns) : 1;
    }

    private int calculateColumnsBasedOnLength(String[] parts, int maxColumns) {
        int totalLength = 0;
        int maxPartLength = 0;

        // Find den faktiske karakterlængde af det længste element
        for (String part : parts) {
            // Fjern alle mellemrum i teksten
            part = part.replaceAll("\\s", "");

            for (String replace : new String[]{".docx", ".xlsx", ".one"}) {
                part = part.replaceAll(replace, "");
            }

            // Beregn den faktiske karakterlængde for hver del
            int partLength = Math.min(part.length(), 10); // Brug højst de første 10 tegn til beregning
            maxPartLength = Math.max(maxPartLength, partLength);
        }

        // Beregn det optimale antal kolonner baseret på den faktiske karakterlængde af det længste element
        int adjustedColumns = Math.min(maxColumns, Math.max(1, (int) Math.ceil((double) maxPartLength / 10)));
        return adjustedColumns;
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
        this.components=0;
    }
}
