package br.com.challenge.uploadservice.domain;

import br.com.challenge.uploadservice.enums.ImageType;
import br.com.challenge.uploadservice.enums.UploadStatus;
import br.com.challenge.uploadservice.resource.ImageResource;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

import static br.com.challenge.uploadservice.enums.UploadStatus.CONCLUDED;

@Document
public class Image implements Serializable {

    public Image(String id, String name, ImageType type, UploadStatus status, String customerId, Binary content) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.customerId = customerId;
        this.content = content;
    }

    @Id
    private String id;

    private String name;

    private ImageType type;

    private UploadStatus status;

    private String customerId;

    private Binary content;

    public void conclude() {
        this.status = CONCLUDED;
    }

    public ImageResource toImageResource() {
        return new ImageResource(this.id, this.name, this.type, this.status);
    }

    public byte[] toByte() {
        return this.content.getData();
    }
}
