package com.verix.sam.domain.model;

import com.verix.sam.domain.model.exception.InvalidPropertyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SamTest {

    private static final String EMPTY_STRING = "";
    private static final String INVALID_CHAR = "!¡'¿+ç´%()*";

    @Test
    void Given_a_valid_data_When_try_to_create_Then_create_a_valid_sam() {
        //Arrange
        String publisher = "Microsoft";
        String category = "Data Integration";
        String product = "SQL Server Integration Services";
        String productVersion = "SQL Server Integration Services 2014 Standard 12.0.2456.0";
        String version = "2014";
        String fullVersion = "12.0.2456.0";
        String edition = "Standard";
        String internalAvailability = "1/25/2022 12:00:00 AM";
        String expectedInternalAvailability = "2022-01-25";
        String internalEOS = "7/9/2022 12:00:00 AM";
        String expectedInternalEOS = "2022-07-09";
        String publisherAvailability = "12/17/2014 12:00:00 AM";
        String expectedPublisherAvailability = "2014-12-17";
        String eos = "7/9/2022 12:00:00 AM";
        String expectedEos = "2022-07-09";
        String eoes = "7/9/2024 12:00:00 AM";
        String expectedEoes = "2024-07-09";
        String eol = "7/9/2024 12:00:00 AM";
        String expectedEol = "2024-07-09";
        String source = "samp_sw_product";

        //Act
        Sam sam = new Sam(publisher,
                category,
                product,
                productVersion,
                version,
                fullVersion,
                edition,
                LifeDate.create(internalAvailability),
                LifeDate.create(internalEOS),
                LifeDate.create(publisherAvailability),
                LifeDate.create(eos),
                LifeDate.create(eoes),
                LifeDate.create(eol),
                source);

        //Assert
        assertNotNull(sam.getPublisher());
        assertNotEquals(EMPTY_STRING, sam.getPublisher());
        assertEquals(publisher, sam.getPublisher());

        assertNotNull(sam.getCategory());
        assertNotEquals(EMPTY_STRING, sam.getCategory());
        assertEquals(category, sam.getCategory());

        assertNotNull(sam.getProduct());
        assertNotEquals(EMPTY_STRING, sam.getProduct());
        assertEquals(product, sam.getProduct());

        assertNotNull(sam.getProductVersion());
        assertNotEquals(EMPTY_STRING, sam.getProductVersion());
        assertEquals(productVersion, sam.getProductVersion());

        assertNotNull(sam.getVersion());
        assertNotEquals(EMPTY_STRING, sam.getVersion());
        assertEquals(version, sam.getVersion());

        assertNotNull(sam.getFullVersion());
        assertNotEquals(EMPTY_STRING, sam.getFullVersion());
        assertEquals(fullVersion, sam.getFullVersion());

        assertNotNull(sam.getEdition());
        assertNotEquals(EMPTY_STRING, sam.getEdition());
        assertEquals(edition, sam.getEdition());

        assertNotNull(sam.getInternalAvailability().getValue());
        assertNotEquals(EMPTY_STRING, sam.getInternalAvailability().getValue());
        assertEquals(expectedInternalAvailability, sam.getInternalAvailability().getValue());

        assertNotNull(sam.getInternalEOS().getValue());
        assertNotEquals(EMPTY_STRING, sam.getInternalEOS().getValue());
        assertEquals(expectedInternalEOS, sam.getInternalEOS().getValue());

        assertNotNull(sam.getPublisherAvailability().getValue());
        assertNotEquals(EMPTY_STRING, sam.getPublisherAvailability().getValue());
        assertEquals(expectedPublisherAvailability, sam.getPublisherAvailability().getValue());

        assertNotNull(sam.getEos().getValue());
        assertNotEquals(EMPTY_STRING, sam.getEos().getValue());
        assertEquals(expectedEos, sam.getEos().getValue());

        assertNotNull(sam.getEoes().getValue());
        assertNotEquals(EMPTY_STRING, sam.getEoes().getValue());
        assertEquals(expectedEoes, sam.getEoes().getValue());

        assertNotNull(sam.getEol().getValue());
        assertNotEquals(EMPTY_STRING, sam.getEol().getValue());
        assertEquals(expectedEol, sam.getEol().getValue());

        assertNotNull(sam.getSource());
        assertNotEquals(EMPTY_STRING, sam.getSource());
        assertEquals(source, sam.getSource());
    }

    @Test
    void Given_data_with_optional_values_When_try_to_create_Then_create_a_valid_sam() {
        //Arrange
        String publisher = "Microsoft";
        String category = "Data Integration";
        String product = "SQL Server Integration Services";
        String productVersion = "SQL Server Integration Services 2014 Standard 12.0.2456.0";
        String version = EMPTY_STRING;
        String fullVersion = EMPTY_STRING;
        String edition = EMPTY_STRING;
        String internalAvailability = EMPTY_STRING;
        String internalEOS = EMPTY_STRING;
        String publisherAvailability = EMPTY_STRING;
        String eos = EMPTY_STRING;
        String eoes = EMPTY_STRING;
        String eol = EMPTY_STRING;
        String source = "samp_sw_product";

        //Act
        Sam sam = new Sam(publisher,
                category,
                product,
                productVersion,
                version,
                fullVersion,
                edition,
                LifeDate.create(internalAvailability),
                LifeDate.create(internalEOS),
                LifeDate.create(publisherAvailability),
                LifeDate.create(eos),
                LifeDate.create(eoes),
                LifeDate.create(eol),
                source);

        //Assert
        assertNotNull(sam.getPublisher());
        assertNotEquals(EMPTY_STRING, sam.getPublisher());
        assertEquals(publisher, sam.getPublisher());

        assertNotNull(sam.getCategory());
        assertNotEquals(EMPTY_STRING, sam.getCategory());
        assertEquals(category, sam.getCategory());

        assertNotNull(sam.getProduct());
        assertNotEquals(EMPTY_STRING, sam.getProduct());
        assertEquals(product, sam.getProduct());

        assertNotNull(sam.getProductVersion());
        assertNotEquals(EMPTY_STRING, sam.getProductVersion());
        assertEquals(productVersion, sam.getProductVersion());

        assertNull(sam.getVersion());

        assertNull(sam.getFullVersion());

        assertNull(sam.getEdition());

        assertNull(sam.getInternalAvailability().getValue());

        assertNull(sam.getInternalEOS().getValue());

        assertNull(sam.getPublisherAvailability().getValue());

        assertNull(sam.getEos().getValue());

        assertNull(sam.getEoes().getValue());

        assertNull(sam.getEol().getValue());

        assertNotNull(sam.getSource());
        assertEquals(source, sam.getSource());
    }

    @Test
    void Given_data_with_optional_and_invalid_values_When_try_to_create_Then_create_a_valid_sam() {
        //Arrange
        String publisher = "Microsoft";
        String category = "Data Integration";
        String product = "SQL Server Integration Services";
        String productVersion = "SQL Server Integration Services 2014 Standard 12.0.2456.0";
        String version = INVALID_CHAR;
        String fullVersion = INVALID_CHAR;
        String edition = INVALID_CHAR;
        String internalAvailability = INVALID_CHAR;
        String internalEOS = INVALID_CHAR;
        String publisherAvailability = INVALID_CHAR;
        String eos = INVALID_CHAR;
        String eoes = INVALID_CHAR;
        String eol = INVALID_CHAR;
        String source = "samp_sw_product";

        //Act
        Sam sam = new Sam(publisher,
                category,
                product,
                productVersion,
                version,
                fullVersion,
                edition,
                LifeDate.create(internalAvailability),
                LifeDate.create(internalEOS),
                LifeDate.create(publisherAvailability),
                LifeDate.create(eos),
                LifeDate.create(eoes),
                LifeDate.create(eol),
                source);

        //Assert
        assertNotNull(sam.getPublisher());
        assertNotEquals(EMPTY_STRING, sam.getPublisher());
        assertEquals(publisher, sam.getPublisher());

        assertNotNull(sam.getCategory());
        assertNotEquals(EMPTY_STRING, sam.getCategory());
        assertEquals(category, sam.getCategory());

        assertNotNull(sam.getProduct());
        assertNotEquals(EMPTY_STRING, sam.getProduct());
        assertEquals(product, sam.getProduct());

        assertNotNull(sam.getProductVersion());
        assertNotEquals(EMPTY_STRING, sam.getProductVersion());
        assertEquals(productVersion, sam.getProductVersion());

        assertNull(sam.getVersion());

        assertNull(sam.getFullVersion());

        assertNull(sam.getEdition());

        assertNull(sam.getInternalAvailability().getValue());

        assertNull(sam.getInternalEOS().getValue());

        assertNull(sam.getPublisherAvailability().getValue());

        assertNull(sam.getEos().getValue());

        assertNull(sam.getEoes().getValue());

        assertNull(sam.getEol().getValue());

        assertNotNull(sam.getSource());
        assertEquals(source, sam.getSource());

        System.out.println(sam.toString());
    }

    @Test
    void Given_data_with_required_values_When_try_to_create_Then_throw_an_exception() {
        //Arrange
        String publisher = EMPTY_STRING;
        String category = EMPTY_STRING;
        String product = EMPTY_STRING;
        String productVersion = EMPTY_STRING;
        String version = "2014";
        String fullVersion = "12.0.2456.0";
        String edition = "Standard";
        String internalAvailability = "1/25/2022 12:00:00 AM";
        String internalEOS = "7/9/2022 12:00:00 AM";
        String publisherAvailability = "12/17/2014 12:00:00 AM";
        String eos = "7/9/2022 12:00:00 AM";
        String eoes = "7/9/2024 12:00:00 AM";
        String eol = "7/9/2024 12:00:00 AM";
        String source = EMPTY_STRING;

        //Act and Assert
        assertThrows(InvalidPropertyException.class, () -> new Sam(publisher,
                category,
                product,
                productVersion,
                version,
                fullVersion,
                edition,
                LifeDate.create(internalAvailability),
                LifeDate.create(internalEOS),
                LifeDate.create(publisherAvailability),
                LifeDate.create(eos),
                LifeDate.create(eoes),
                LifeDate.create(eol),
                source)
        );
    }

    @Test
    void Given_data_with_required_and_invalid_values_When_try_to_parse_invalid_to_empty_Then_throw_an_exception() {
        //Arrange
        String publisher = INVALID_CHAR;
        String category = INVALID_CHAR;
        String product = INVALID_CHAR;
        String productVersion = INVALID_CHAR;
        String version = EMPTY_STRING;
        String fullVersion = EMPTY_STRING;
        String edition = EMPTY_STRING;
        String internalAvailability = EMPTY_STRING;
        String internalEOS = EMPTY_STRING;
        String publisherAvailability = EMPTY_STRING;
        String eos = EMPTY_STRING;
        String eoes = EMPTY_STRING;
        String eol = EMPTY_STRING;
        String source = INVALID_CHAR;

        //Act and Assert
        assertThrows(InvalidPropertyException.class, () -> new Sam(publisher,
                category,
                product,
                productVersion,
                version,
                fullVersion,
                edition,
                LifeDate.create(internalAvailability),
                LifeDate.create(internalEOS),
                LifeDate.create(publisherAvailability),
                LifeDate.create(eos),
                LifeDate.create(eoes),
                LifeDate.create(eol),
                source)
        );
    }

    @Test
    void Given_data_with_required_and_null_values_When_try_to_parse_null_Then_throw_an_exception() {
        //Arrange
        String publisher = null;
        String category = null;
        String product = null;
        String productVersion = null;
        String version = EMPTY_STRING;
        String fullVersion = EMPTY_STRING;
        String edition = EMPTY_STRING;
        String internalAvailability = EMPTY_STRING;
        String internalEOS = EMPTY_STRING;
        String publisherAvailability = EMPTY_STRING;
        String eos = EMPTY_STRING;
        String eoes = EMPTY_STRING;
        String eol = EMPTY_STRING;
        String source = null;

        //Act and Assert
        assertThrows(InvalidPropertyException.class, () -> new Sam(publisher,
                category,
                product,
                productVersion,
                version,
                fullVersion,
                edition,
                LifeDate.create(internalAvailability),
                LifeDate.create(internalEOS),
                LifeDate.create(publisherAvailability),
                LifeDate.create(eos),
                LifeDate.create(eoes),
                LifeDate.create(eol),
                source)
        );
    }
}