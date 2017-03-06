package guru.springframework.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import guru.springframework.domain.User;

public interface UserMongoRepository extends MongoRepository<User, Integer> {
    User findByName(String name);
}
