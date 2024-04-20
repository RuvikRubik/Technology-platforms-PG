package org.example;


import org.apache.commons.lang3.tuple.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Zła ilość argumentów");
            System.exit(1);
        }

        String inputDirectory = args[0];
        String outputDirectory = args[1];

        List<Path> files;
        Path source = Path.of(inputDirectory);
        try (var stream = Files.list(source)) {
            files = stream.collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            ForkJoinPool customthread = new ForkJoinPool(2);

            long startTime = System.currentTimeMillis();
            try{
                customthread.submit(()->
                        files.parallelStream()
                                .map(path -> {
                                    BufferedImage originalImage = null;
                                    try {
                                        originalImage = ImageIO.read(path.toFile());
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    return Pair.of(path.getFileName().toString(), originalImage);
                                })
                                .map(result -> {

                                    BufferedImage transformedImage = transformImage(result.getRight());
                                    return Pair.of(result.getLeft(), transformedImage);
                                })
                                .forEach(result -> {
                                    try {
                                        saveImage(result.getRight(), outputDirectory, result.getLeft());
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                                })).get();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                customthread.shutdown();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Czas wykonania dla puli wątków o rozmiarze 4: " + (endTime - startTime) + " ms");
    }

    private static BufferedImage transformImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage transformedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);
                int gray = (int)(color.getRed()*0.3 +  color.getGreen()*0.59 + color.getBlue()*0.11);
                Color outColor = new Color(gray, gray, gray);
                int outRgb = outColor.getRGB();
                transformedImage.setRGB(x, y, outRgb);
            }
        }
        return transformedImage; // Na razie zwracamy oryginalny obraz
    }

    private static void saveImage(BufferedImage image, String outputDirectory, String filename) throws IOException {
        File outputFolder = new File(outputDirectory);
        if (!outputFolder.exists()) {
            outputFolder.mkdirs(); // Tworzymy katalog wyjściowy, jeśli nie istnieje
        }
        File outputFile = new File(outputDirectory, filename);
        ImageIO.write(image, "jpg", outputFile);
    }

}