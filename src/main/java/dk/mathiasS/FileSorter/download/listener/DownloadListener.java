package dk.mathiasS.FileSorter.download.listener;

import dk.mathiasS.FileSorter.download.ui.define.DefineFileUI;
import org.apache.commons.math3.analysis.function.Exp;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DownloadListener {

    private boolean enabled;
    private WatchService watchService;
    private Thread thread;
    private Map<String, File> fileList = new HashMap<>();

    public DownloadListener() {
        this.enabled = false;
        this.watchService = null;
        this.thread = null;
    }

    public boolean inAction() {
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
        // Sti til mappen, du vil overvåge
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
                            if (!isIntermediateFile(newFile) && !isTemporaryFile(newFile)) {
                                String fileName = newFile.getName();
                                if (!fileList.containsKey(fileName)) {
                                    System.out.println("Processing file: " + fileName);
                                    fileList.put(fileName, newFile);
                                    new DefineFileUI(newFile);
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

    private boolean isTemporaryFile(File file) {
        // Define patterns or identifiers for temporary files
        String fileName = file.getName();

        // Check if file name contains a pattern for temporary files
        return fileName.matches("[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}\\.tmp");
    }
}
