package pl.mariuszderda.urlshortener.util;

public interface BaseEncoder {
    String encode(long number);

    long decode (String number);
}
