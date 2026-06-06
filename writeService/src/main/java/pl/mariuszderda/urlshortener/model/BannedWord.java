package pl.mariuszderda.urlshortener.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("banned_words")
public class BannedWord {

    @PrimaryKey
    private String word;

    public String getWord() {
        return word;
    }
}
