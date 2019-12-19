package br.com.challenge.uploadservice.util;

import br.com.challenge.uploadservice.domain.Image;
import br.com.challenge.uploadservice.enums.ImageType;
import br.com.challenge.uploadservice.enums.UploadStatus;
import org.bson.types.Binary;

import java.util.Collection;
import java.util.Optional;

import static br.com.challenge.uploadservice.enums.ImageType.PNG;
import static br.com.challenge.uploadservice.enums.UploadStatus.CONCLUDED;
import static java.util.Collections.singleton;
import static java.util.UUID.randomUUID;

public final class TestUtils {

    private TestUtils() throws IllegalAccessException {
        throw new IllegalAccessException("utility class");
    }

    public static Image buildImageWithOutId() {
        return buildImage(null);
    }

    public static Image buildImageWithId() {
        return buildImage(randomUUID().toString());
    }

    public static Optional<Image> buildOptionalImageWithId() {
        return Optional.of(buildImage(randomUUID().toString()));
    }

    public static Collection<Image> buildCollectionsImageWithId() {
        return singleton(buildImage(randomUUID().toString()));
    }

    private static Image buildImage(String id) {
        return buildImage(id, "photo.png",
                PNG, CONCLUDED,
                randomUUID().toString(), new Binary(new byte[0]));
    }

    private static Image buildImage(String id,
                                    String name,
                                    ImageType type,
                                    UploadStatus status,
                                    String customerId,
                                    Binary content) {
        return new Image(id, name, type, status, customerId, content);
    }
}
