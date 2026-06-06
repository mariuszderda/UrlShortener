package pl.mariuszderda.urlshortener.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import pl.mariuszderda.urlshortener.model.BannedWord;

@Repository
public interface BannedWordRepository extends CassandraRepository<BannedWord, String> {
}
