package br.com.challenge.uploadservice.repository;

import br.com.challenge.uploadservice.domain.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ImageRepository extends MongoRepository<Image, String> {

    Collection<Image> findByCustomerId(String customerId);

    Optional<Image> findByIdAndCustomerId(String id, String customerId);

    Collection<Image> findByCustomerIdAndName(String customerId, String name);

    void deleteByIdAndCustomerId(String id, String customerId);
}
