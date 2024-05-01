package dk.mathiasS.FileSorter.download.listener;

import dk.mathiasS.FileSorter.download.ui.define.DefineFileUI;
import dk.mathiasS.FileSorter.model.ClassPredictor;
import dk.mathiasS.FileSorter.model.TrainClassPredictor;
import dk.mathiasS.FileSorter.model.data.DataRetriever;
import org.apache.commons.math3.analysis.function.Exp;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class DownloadListener {

    private static boolean enabled;
    private WatchService watchService;
    private Thread thread;
    private Map<String, File> fileList = new HashMap<>();

    public DownloadListener() {
        enabled = false;
        this.watchService = null;
        this.thread = null;
    }

    public static boolean inAction() {
        return enabled;
    }

    public boolean toggle() {
        if (!enabled) {
            start();
        }
        enabled = !enabled;
        System.out.println("Togglede fil eventet: " + enabled);

        return enabled;
    }


    private void start() {
        Path dir = Paths.get(System.getProperty("user.home") + File.separator + "Downloads");

        try {
            watchService = FileSystems.getDefault().newWatchService();

            dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.OVERFLOW);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thread = new Thread(() -> {
            while (enabled) {
                try {
                    WatchKey key = watchService.take();

                    if (!enabled) {
                        return;
                    }

                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                            Path newPath = (Path) event.context();
                            File newFile = newPath.toFile();
                            // Check if file is not an intermediate file or temporary file
                            if (!isIntermediateFile(newFile) && !isTemporaryFile(newFile) && isValidFileExtension(newFile)) {
                                String fileName = newFile.getName();
                                if (!fileList.containsKey(fileName)) {
                                    System.out.println("Processing file: " + fileName);
                                    fileList.put(fileName, newFile);

                                    DataRetriever data = new DataRetriever(newFile);

                                    new DefineFileUI(new File(dir + "/" + newFile));
                                }
                            }
                        }
                    }
                    key.reset();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                watchService.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        thread.start();

    }


    private boolean isIntermediateFile(File file) {
        // Define patterns or extensions for intermediate files
        String[] intermediateFileExtensions = {"crdownload"};
        String fileName = file.getName();

        for (String extension : intermediateFileExtensions) {
            if (fileName.endsWith("." + extension)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidFileExtension(File file) {
        List<String> allowedExtensions = Arrays.asList("pdf", "docx", "xlsx", "mw", "ppt", "csv");
        String fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return allowedExtensions.contains(extension);
    }

    private boolean isTemporaryFile(File file) {
        // Define patterns or identifiers for temporary files
        String fileName = file.getName();

        // Check if file name contains a pattern for temporary files
        return fileName.matches("[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}\\.tmp");
    }
}
