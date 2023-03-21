package org.scada_lts.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.scada_lts.report.PrintLog.p;
import static org.scada_lts.report.PrintLog.warn;

public class FileUtil {

    public static Optional<File> getFileFromJar(String fileName) {
        try {
            p("getFileFromJar: " + fileName);
            Path path = Paths.get(fileName.trim());
            if(Files.exists(path)) {
                p("exists: " + path);
                return Optional.of(path.toFile());
            }
            return _createNewFileInFileSystem(fileName.replace("\\", "/").trim());
        } catch (Throwable e) {
            warn(e.getMessage());
            return Optional.empty();
        }
    }

    public static Optional<File> getFileFromFileSystem(Path path) {
        try {
            p("getFileFromFileSystem: " + path);
            if(Files.exists(path)) {
                p( "exists: " + path);
                return Optional.of(path.toFile());
            }
            if(!Files.notExists(path)) {
                throw new IllegalArgumentException("File access denied: " + path);
            }
            File file = path.toFile();
            boolean created = file.createNewFile();
            p("file: " + file +  ", created: " + created);
            return Optional.of(file);
        } catch (Throwable e) {
            warn(e.getMessage());
            return Optional.empty();
        }
    }

    public static Optional<File> getFileFromFileSystem(String fileName) {
        Path path = Paths.get(fileName.trim());
        return getFileFromFileSystem(path);
    }

    public static Optional<InputStream> getResourceAsStream(String fileName) {
        try {
            return Optional.ofNullable(FileUtil.class.getClassLoader().getResourceAsStream(fileName.trim()));
        } catch (Exception e) {
            warn(e.getMessage());
            return Optional.empty();
        }
    }

    public static File zip(File file) {
        String zipFileName = file.getName() + ".zip";
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFileName))) {
            zipOut.putNextEntry(new ZipEntry(file.getName()));
            Files.copy(file.toPath(), zipOut);
        } catch (IOException e) {
            warn(e.getMessage());
        }
        return new File(zipFileName);
    }

    public static List<String> readLines(Path path) {
        try {
            return Files.lines(path).collect(Collectors.toList());
        } catch (IOException e) {
            warn(e.getMessage());
            return Collections.emptyList();
        }
    }

    private static Optional<File> _createNewFileInFileSystem(String fileName) throws IOException {
        Path path = Paths.get(fileName.trim());
        boolean dirsCreated = _createDirs(path);
        Optional<InputStream> inputStream = getResourceAsStream(fileName.trim());
        if(inputStream.isPresent()) {
            Files.copy(inputStream.get(), path);
            p( "created: " + path);
            return Optional.of(path.toFile());
        }
        return Optional.empty();
    }

    private static boolean _createDirs(Path path) {
        Path parent = path.getParent();
        if(Objects.nonNull(parent)) {
            File dir = path.getParent().toFile();
            return dir.mkdirs();
        }
        return false;
    }
}
