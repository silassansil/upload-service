package br.com.challenge.uploadservice.service.impl;

import br.com.challenge.uploadservice.domain.Image;
import br.com.challenge.uploadservice.exception.ResourceNotFoundException;
import br.com.challenge.uploadservice.repository.ImageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collection;

import static br.com.challenge.uploadservice.util.TestUtils.*;
import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
class ImageServiceImplTest {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageServiceImpl imageService;


    @Test
    @DisplayName("Upload New Image")
    void uploadNewImage() {
        when(this.imageRepository.save(any(Image.class))).thenReturn(buildImageWithId());

        final Image image = this.imageService.uploadImage(buildImageWithOutId());

        assertNotNull(image);
        verify(this.imageRepository).save(any(Image.class));
    }

    @Test
    @DisplayName("Find by customer id when exists image to customer")
    void findByCustomerIdWhenExistsImageToCustomer() {
        when(this.imageRepository.findByCustomerId(anyString())).thenReturn(buildCollectionsImageWithId());

        final Collection<Image> byCustomerId = this.imageService.findByCustomerId(randomUUID().toString());

        assertFalse(byCustomerId.isEmpty());
        verify(this.imageRepository).findByCustomerId(anyString());
    }

    @Test
    @DisplayName("Find by customer id when not exists image to customer")
    void findByCustomerIdWhenNotExistsImageToCustomer() {
        when(this.imageRepository.findByCustomerId(anyString())).thenReturn(emptyList());

        final Collection<Image> byCustomerId = this.imageService.findByCustomerId(randomUUID().toString());

        assertTrue(byCustomerId.isEmpty());
        verify(this.imageRepository).findByCustomerId(anyString());
    }

    @Test
    @DisplayName("Find by id and customer id when exists image to customer")
    void findByIdAndCustomerIdWhenExistsImageToCustomer() {
        when(this.imageRepository.findByIdAndCustomerId(anyString(), anyString()))
                .thenReturn(buildOptionalImageWithId());

        final Image byIdAndCustomerId = this.imageService.findByIdAndCustomerId(randomUUID().toString(), randomUUID().toString());

        verify(this.imageRepository).findByIdAndCustomerId(anyString(), anyString());
        assertNotNull(byIdAndCustomerId);
    }

    @Test
    @DisplayName("Find by id and customer id when not exists image to customer")
    void findByIdAndCustomerIdWhenNotExistsImageToCustomer() {
        final String id = randomUUID().toString();
        final String customerId = randomUUID().toString();

        when(this.imageRepository.findByIdAndCustomerId(anyString(), anyString()))
                .thenReturn(empty());

        assertThrows(ResourceNotFoundException.class, () ->
                        this.imageService.findByIdAndCustomerId(id, customerId),
                format("couldn't found image %s to customer %s", id, customerId));

        verify(this.imageRepository).findByIdAndCustomerId(anyString(), anyString());
    }

    @Test
    @DisplayName("Find by customer id and name when exist image to customer")
    void findByCustomerIdAndNameWhenExistImageToCustomer() {
        when(this.imageRepository.findByCustomerIdAndName(anyString(), anyString()))
                .thenReturn(buildCollectionsImageWithId());

        final Collection<Image> byCustomerIdAndName = this.imageService.findByCustomerIdAndName(randomUUID().toString(), "name");

        assertFalse(byCustomerIdAndName.isEmpty());
        verify(this.imageRepository).findByCustomerIdAndName(anyString(), anyString());
    }

    @Test
    @DisplayName("Find by customer id and name when not exist image to customer")
    void findByCustomerIdAndNameWhenNotExistImageToCustomer() {
        when(this.imageRepository.findByCustomerIdAndName(anyString(), anyString()))
                .thenReturn(emptyList());

        final Collection<Image> byCustomerIdAndName = this.imageService.findByCustomerIdAndName(randomUUID().toString(), "name");

        assertTrue(byCustomerIdAndName.isEmpty());
        verify(this.imageRepository).findByCustomerIdAndName(anyString(), anyString());
    }

    @Test
    @DisplayName("Delete by id and customer id with success")
    void deleteByIdAndCustomerIdWithSuccess() {
        this.imageService.deleteByIdAndCustomerId(randomUUID().toString(), randomUUID().toString());
        verify(this.imageRepository).deleteByIdAndCustomerId(anyString(), anyString());
    }
}