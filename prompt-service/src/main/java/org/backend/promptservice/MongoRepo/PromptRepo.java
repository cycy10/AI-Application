package org.backend.promptservice.MongoRepo;

import org.backend.promptservice.Models.PromptModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromptRepo extends MongoRepository<PromptModel,Integer> {
}
