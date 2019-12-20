package br.com.challenge.uploadservice.service;

import br.com.challenge.uploadservice.domain.Image;

import java.util.Collection;

public interface ImageService {

    /**
     * Upload image to customer id
     *
     * @param image image data
     * @return image saved
     */
    Image uploadImage(final Image image);

    /**
     * Find by customer id
     *
     * @param customerId customer id
     * @return collection with all images owned customers
     */
    Collection<Image> findByCustomerId(final String customerId);

    /**
     * Find by id and customer id
     *
     * @param customerId customer id
     * @param id         image id
     * @return images owned by customer id
     */
    Image findByIdAndCustomerId(final String customerId, final String id);

    /**
     * Find by customer id and name
     *
     * @param customerId customer id
     * @param name       name
     * @return collection with all images owned customers and with name
     */
    Collection<Image> findByCustomerIdAndName(final String customerId, final String name);

    /**
     * Delete by id and customer id
     *
     * @param customerId customer id
     * @param id         image id
     */
    void deleteByIdAndCustomerId(final String customerId, final String id);
}
