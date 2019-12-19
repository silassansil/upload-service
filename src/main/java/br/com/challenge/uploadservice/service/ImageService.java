package br.com.challenge.uploadservice.service;

import br.com.challenge.uploadservice.domain.Image;

import java.util.Collection;

public interface ImageService {

    Image uploadImage(final Image image);

    Collection<Image> findByCustomerId(final String customerId);

    Image findByIdAndCustomerId(final String customerId, final String id);

    Collection<Image> findByCustomerIdAndName(final String customerId, final String name);

    void deleteByIdAndCustomerId(final String customerId, final String id);
}
