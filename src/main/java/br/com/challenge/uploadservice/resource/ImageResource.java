package br.com.challenge.uploadservice.resource;

import br.com.challenge.uploadservice.domain.Image;
import br.com.challenge.uploadservice.enums.ImageType;
import br.com.challenge.uploadservice.enums.UploadStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;

import static br.com.challenge.uploadservice.enums.ImageType.byExtension;
import static br.com.challenge.uploadservice.enums.UploadStatus.PROCESSING;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static org.bson.BsonBinarySubType.BINARY;

@JsonInclude(NON_NULL)
public class ImageResource implements Serializable {

    public ImageResource(String customerId, MultipartFile file) throws IOException {
        this.customerId = customerId;
        this.status = PROCESSING;
        this.name = file.getOriginalFilename();
        this.type = byExtension(file.getOriginalFilename());
        this.content = new Binary(BINARY, file.getBytes());
    }

    public ImageResource(String id, String name, ImageType type, UploadStatus status) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
    }

    @ApiModelProperty("image id")
    private String id;

    @ApiModelProperty("image file name")
    private String name;

    @ApiModelProperty(value = "image type", dataType = "br.com.challenge.uploadservice.enums.ImageType")
    private ImageType type;

    @ApiModelProperty(value = "status upload file", dataType = "br.com.challenge.uploadservice.enums.UploadStatus")
    private UploadStatus status;

    @ApiModelProperty("owner customer id")
    private String customerId;

    @ApiModelProperty("content from image")
    private Binary content;

    public Image toImage() {
        return new Image(this.id, this.name, this.type, this.status, this.customerId, this.content);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ImageType getType() {
        return type;
    }

    public UploadStatus getStatus() {
        return status;
    }
}
