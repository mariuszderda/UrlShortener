package pl.mariuszderda.urlshortener.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class Base62EncoderTest {

    private final Base62Encoder base62Encoder = new Base62Encoder();

    @Test
    void encoderShouldReturnZero() {
        String encodeValue = base62Encoder.encode(0);

        Assertions.assertEquals("0", encodeValue);
    }

    @Test
    void encodeShouldReturnOne() {
        String encodeValue = base62Encoder.encode(1);

        Assertions.assertEquals("1", encodeValue);
    }

    @Test
    void encodeShouldReturnTen() {
        String encodeValue = base62Encoder.encode(62);

        Assertions.assertEquals("10", encodeValue);
    }

    @Test
    void encodeShouldReturnOneHundred() {
        String encodeValue = base62Encoder.encode(3844);

        Assertions.assertEquals("100", encodeValue);
    }


}