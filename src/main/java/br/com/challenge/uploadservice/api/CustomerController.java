package br.com.challenge.uploadservice.api;

import br.com.challenge.uploadservice.domain.Image;
import br.com.challenge.uploadservice.resource.ImageResource;
import br.com.challenge.uploadservice.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.*;

@Api("customers")
@RestController
@RequestMapping("/customers/{customerId}")
public class CustomerController {

    private final ImageService imageService;

    public CustomerController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/image")
    @ApiOperation(value = "Upload images to a customer", response = ImageResource.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful upload image to customer", response = ImageResource.class),
            @ApiResponse(code = 422, message = "invalid file type"),
            @ApiResponse(code = 400, message = "Max Upload Size Exceeded Exception")
    })
    public ResponseEntity<ImageResource> uploadFile(@RequestParam("image") MultipartFile image,
                                                    @PathVariable("customerId") String customerId) throws IOException {

        final ImageResource resource = new ImageResource(customerId, image);
        final Image saved = this.imageService.uploadImage(resource.toImage());

        return ResponseEntity
                .status(CREATED)
                .body(saved.toImageResource());
    }

    @GetMapping("/image")
    @ApiOperation(value = "Returns all images from customer Id", response = ImageResource.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of image resource",
                    response = ImageResource.class, responseContainer = "List"),
    })
    public ResponseEntity<List<ImageResource>> findAllImageByCustomer(@PathVariable("customerId") String customerId) {
        final List<ImageResource> byCustomerId = this.imageService.findByCustomerId(customerId)
                .stream()
                .map(Image::toImageResource)
                .collect(toList());

        return ResponseEntity.ok()
                .body(byCustomerId);
    }

    @GetMapping("/image/by")
    @ApiOperation(value = "Returns all images from customer name", response = ImageResource.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of image resource",
                    response = ImageResource.class, responseContainer = "List"),
    })
    public ResponseEntity<List<ImageResource>> findAllImageByCustomerAndName(@PathVariable("customerId") String customerId,
                                                                             @RequestParam("name") String name) {
        final List<ImageResource> byCustomerId = this.imageService.findByCustomerIdAndName(customerId, name)
                .stream()
                .map(Image::toImageResource)
                .collect(toList());

        return ResponseEntity.ok()
                .body(byCustomerId);
    }

    @DeleteMapping("/image/{imageId}")
    @ApiOperation(value = "Delete one image by imageId and CustomerId")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successful delete one image by imageId and CustomerId")
    })
    public ResponseEntity<Void> deleteByIdAndCustomerId(@PathVariable("imageId") String imageId,
                                                        @PathVariable("customerId") String customerId) {
        this.imageService.deleteByIdAndCustomerId(imageId, customerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/image/{imageId}", produces = {IMAGE_JPEG_VALUE, IMAGE_GIF_VALUE, IMAGE_PNG_VALUE})
    @ApiOperation(value = "Download image by imageId and customerid", response = Byte[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of image resource",
                    response = ImageResource.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "resource not found"),
    })
    public ResponseEntity<byte[]> findByIdAndCustomerId(@PathVariable("customerId") String customerId,
                                                        @PathVariable("imageId") String imageId) {
        return ResponseEntity.ok()
                .body(this.imageService.findByIdAndCustomerId(imageId, customerId).toByte());
    }
}
