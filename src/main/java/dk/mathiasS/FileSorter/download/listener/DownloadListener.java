package dk.mathiasS.FileSorter.download.listener;

import dk.mathiasS.FileSorter.download.ui.DefineFileUI;

import java.io.IOException;
import java.nio.file.*;

public class DownloadListener {

    private boolean enabled;
    private WatchService watchService;
    private Thread thread;

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
        Path dir = Paths.get("C:\\Users\\Schje\\Downloads");

        try {
            // Opret WatchService
            watchService = FileSystems.getDefault().newWatchService();

            // Registrer WatchService til OVERFLOW og ENTRY_CREATE hændelser
            dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.OVERFLOW);

            // Start tråd til at behandle WatchService-hændelser
            thread = new Thread(() -> {
                while (enabled) {
                    try {
                        WatchKey key = watchService.take();

                        if (!enabled) {
                            return; // Afslut tråden, hvis tråden skal stoppe
                        }

                        for (WatchEvent<?> event : key.pollEvents()) {
                            if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                                Path nyFil = (Path) event.context();
                                System.out.println("Ny fil oprettet: " + nyFil);

                                new DefineFileUI(nyFil.toFile());
                            }
                        }

                        key.reset();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    // Luk WatchService, når tråden stopper
                    watchService.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            thread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
