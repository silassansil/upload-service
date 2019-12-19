package br.com.challenge.uploadservice.service.impl;

import br.com.challenge.uploadservice.domain.Image;
import br.com.challenge.uploadservice.exception.ResourceNotFoundException;
import br.com.challenge.uploadservice.repository.ImageRepository;
import br.com.challenge.uploadservice.service.ImageService;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static java.lang.String.format;
import static java.util.Collections.singleton;

@Service
public class ImageServiceImpl implements ImageService {

    private static final String COULD_NOT_FOUND_IMAGE_TO_CUSTOMER = "couldn't found image %s to customer %s";
    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Image uploadImage(final Image image) {
        image.conclude();
        return this.imageRepository.save(image);
    }

    @Override
    public Collection<Image> findByCustomerId(final String customerId) {
        return this.imageRepository.findByCustomerId(customerId);
    }

    @Override
    public Image findByIdAndCustomerId(final String id, final String customerId) {
        return this.imageRepository.findByIdAndCustomerId(id, customerId)
                .orElseThrow(() -> new ResourceNotFoundException(singleton(format(COULD_NOT_FOUND_IMAGE_TO_CUSTOMER, id, customerId))));
    }

    @Override
    public Collection<Image> findByCustomerIdAndName(final String customerId, final String name) {
        return this.imageRepository.findByCustomerIdAndName(customerId, name);
    }

    @Override
    public void deleteByIdAndCustomerId(final String id, final String customerId) {
        this.imageRepository.deleteByIdAndCustomerId(id, customerId);
    }
}
