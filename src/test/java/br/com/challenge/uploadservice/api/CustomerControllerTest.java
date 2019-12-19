package br.com.challenge.uploadservice.api;

import br.com.challenge.uploadservice.domain.Image;
import br.com.challenge.uploadservice.exception.ResourceNotFoundException;
import br.com.challenge.uploadservice.exception.handle.HandlerExceptionResource;
import br.com.challenge.uploadservice.service.ImageService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import static br.com.challenge.uploadservice.util.TestUtils.buildCollectionsImageWithId;
import static br.com.challenge.uploadservice.util.TestUtils.buildImageWithId;
import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@SpringJUnitConfig(classes = {CustomerController.class, HandlerExceptionResource.class})
class CustomerControllerTest {

    private static final String CUSTOMER_ID = "bb12260c-d963-4cb8-9553-7b0b678c1c87";
    private static final String IMAGE_ID = "bb12260c-d963-4cb8-9553-7b0b678c1c87";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @Qualifier("imageService")
    private ImageService imageService;

    @Test
    @DisplayName("Upload new image with success")
    void uploadNewImageWithSuccess() throws Exception {
        final byte[] bytes = new byte[1024 * 1024];
        final MockMultipartFile file = new MockMultipartFile("image", "photo.png", "text/plain", bytes);
        when(this.imageService.uploadImage(any(Image.class))).thenReturn(buildImageWithId());

        this.mockMvc.perform(
                multipart("/customers/" + CUSTOMER_ID + "/image").file(file))

                .andExpect(status().isCreated())

                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.status").value("CONCLUDED"))
                .andExpect(jsonPath("$.type").value("PNG"))
                .andExpect(jsonPath("$.name").value("photo.png"));
    }

    @Test
    @DisplayName("Upload new image with invalid extension")
    void uploadNewImageWithInvalidExtension() throws Exception {
        final byte[] bytes = new byte[1024 * 1024];
        final MockMultipartFile file = new MockMultipartFile("image", "photo.xml", "text/plain", bytes);

        this.mockMvc.perform(
                multipart("/customers/" + CUSTOMER_ID + "/image").file(file))

                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message").value("invalid file type"))
                .andExpect(jsonPath("$.details[0]").value("invalid file type xml"));
    }

    @Test
    @Disabled
    @DisplayName("Upload new image with max size content exceed")
    void uploadNewImageWithMaxSizerExceed() throws Exception {
        final byte[] bytes = new byte[1024 * 1024 * 1024];
        final MockMultipartFile file = new MockMultipartFile("image", "photo.gif", "text/plain", bytes);

        this.mockMvc.perform(
                multipart("/customers/" + CUSTOMER_ID + "/image").file(file))

                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message").value("invalid file type"))
                .andExpect(jsonPath("$.details[0]").value("invalid file type xml"));
    }

    @Test
    @DisplayName("Find all image by customer when exist images")
    void findAllImageByCustomerWhenExistImages() throws Exception {
        when(this.imageService.findByCustomerId(anyString())).thenReturn(buildCollectionsImageWithId());

        this.mockMvc.perform(
                get("/customers/" + CUSTOMER_ID + "/image"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].status").value("CONCLUDED"))
                .andExpect(jsonPath("$[0].type").value("PNG"))
                .andExpect(jsonPath("$[0].name").value("photo.png"));
    }

    @Test
    @DisplayName("Find all image by customer when not exist images")
    void findAllImageByCustomerWhenNotExistImages() throws Exception {
        when(this.imageService.findByCustomerId(anyString())).thenReturn(emptyList());

        this.mockMvc.perform(
                get("/customers/" + CUSTOMER_ID + "/image"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Find all image by customer and name when exists")
    void findAllImageByCustomerAndNameWhenExists() throws Exception {
        when(this.imageService.findByCustomerIdAndName(anyString(), anyString())).thenReturn(buildCollectionsImageWithId());

        this.mockMvc.perform(
                get("/customers/" + CUSTOMER_ID + "/image/by")
                        .param("name", "name"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].status").value("CONCLUDED"))
                .andExpect(jsonPath("$[0].type").value("PNG"))
                .andExpect(jsonPath("$[0].name").value("photo.png"));
    }

    @Test
    @DisplayName("Find all image by customer and name when not exists")
    void findAllImageByCustomerAndNameWhenNOtExists() throws Exception {
        when(this.imageService.findByCustomerIdAndName(anyString(), anyString())).thenReturn(emptyList());

        this.mockMvc.perform(
                get("/customers/" + CUSTOMER_ID + "/image/by")
                        .param("name", "name"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Delete by id and customer id when exist")
    void deleteByIdAndCustomerIdWhenExist() throws Exception {
        this.mockMvc.perform(
                delete("/customers/" + CUSTOMER_ID + "/image/" + IMAGE_ID))

                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Find by id and customer id when exist")
    void findByIdAndCustomerIdWhenExist() throws Exception {
        when(this.imageService.findByIdAndCustomerId(anyString(), anyString())).thenReturn(buildImageWithId());

        this.mockMvc.perform(
                get("/customers/" + CUSTOMER_ID + "/image/" + IMAGE_ID))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG_VALUE));
    }

    @Test
    @DisplayName("Find by id and customer id when not exist")
    void findByIdAndCustomerIdWhenNotExist() throws Exception {
        when(this.imageService.findByIdAndCustomerId(anyString(), anyString()))
                .thenThrow(new ResourceNotFoundException(singleton("couldn't found image %s to customer %s")));

        this.mockMvc.perform(
                get("/customers/" + CUSTOMER_ID + "/image/" + IMAGE_ID))

                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message").value("resource not found"))
                .andExpect(jsonPath("$.details[0]").value("couldn't found image %s to customer %s"));
    }
}