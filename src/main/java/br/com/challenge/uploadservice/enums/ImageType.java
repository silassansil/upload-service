package br.com.challenge.uploadservice.enums;

import br.com.challenge.uploadservice.exception.InvalidImageTypeException;

import static java.util.Arrays.stream;
import static org.apache.commons.io.FilenameUtils.getExtension;

public enum ImageType {
    JPEG,
    JFIF,
    GIF,
    BMP,
    PNG;

    public static ImageType byExtension(final String filename) {
        final String extension = getExtension(filename);

        if (stream(values()).noneMatch(type -> type.toString().equals(extension.toUpperCase())))
            throw new InvalidImageTypeException(extension);

        return valueOf(extension.toUpperCase());
    }
}
