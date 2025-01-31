package com.verix.sam.domain.model;

import com.verix.sam.domain.model.exception.InvalidPropertyException;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Predicate;

public class Sam implements Serializable {
    private static final String REGEX_SPECIAL_CHARS = "[^a-zA-Z0-9\\s._,-@#$/]";
    private static final String REPLACEMENT = "";

    private final String publisher;
    private final String category;
    private final String product;
    private final String productVersion;
    private final String version;
    private final String fullVersion;
    private final String edition;
    private final LifeDate internalAvailability;
    private final LifeDate internalEOS;
    private final LifeDate publisherAvailability;
    private final LifeDate eos; //Publisher End Of Support
    private final LifeDate eoes; //Publisher End Of Extended Support
    private final LifeDate eol; //Publisher End Of Life
    private final String source;

    public Sam(String publisher, String category, String product, String productVersion, String version, String fullVersion, String edition, LifeDate internalAvailability, LifeDate internalEOS, LifeDate publisherAvailability, LifeDate eos, LifeDate eoes, LifeDate eol, String source) {
        this.publisher = setPublisher(publisher);
        this.category = setCategory(category);
        this.product = setProduct(product);
        this.productVersion = setProductVersion(productVersion);
        this.version = setVersion(version);
        this.fullVersion = setFullVersion(fullVersion);
        this.edition = setEdition(edition);
        this.internalAvailability = internalAvailability;
        this.internalEOS = internalEOS;
        this.publisherAvailability = publisherAvailability;
        this.eos = eos;
        this.eoes = eoes;
        this.eol = eol;
        this.source = setSource(source);
    }

    public String getPublisher() {
        return publisher;
    }

    public String setPublisher(String publisher) {
        return removeSpecialCharsForRequired(publisher);
    }

    public String getCategory() {
        return category;
    }

    public String setCategory(String category) {
        return removeSpecialCharsForRequired(category);
    }

    public String getProduct() {
        return product;
    }

    public String setProduct(String product) {
        return removeSpecialCharsForRequired(product);
    }

    public String getProductVersion() {
        return productVersion;
    }

    public String setProductVersion(String productVersion) {
        return removeSpecialCharsForRequired(productVersion);
    }

    public String getVersion() {
        return version;
    }

    public String setVersion(String version) {
        return removeSpecialCharsForOptional(version);
    }

    public String getFullVersion() {
        return fullVersion;
    }

    public String setFullVersion(String fullVersion) {
        return removeSpecialCharsForOptional(fullVersion);
    }

    public String getEdition() {
        return edition;
    }

    public String setEdition(String edition) {
        return removeSpecialCharsForOptional(edition);
    }

    public LifeDate getInternalAvailability() {
        return internalAvailability;
    }

    public LifeDate getInternalEOS() {
        return internalEOS;
    }

    public LifeDate getPublisherAvailability() {
        return publisherAvailability;
    }

    public LifeDate getEos() {
        return eos;
    }

    public LifeDate getEoes() {
        return eoes;
    }

    public LifeDate getEol() {
        return eol;
    }

    public String getSource() {
        return source;
    }

    public String setSource(String source) {
        return removeSpecialCharsForRequired(source);
    }

    private String removeSpecialCharsForRequired(String value) {
        String optionalValue = Optional
                .ofNullable(value)
                .filter(Predicate.not(String::isEmpty))
                .orElseThrow(InvalidPropertyException::thrown)
                .trim()
                .replaceAll(REGEX_SPECIAL_CHARS, REPLACEMENT);

        if (optionalValue.isEmpty()) {
            throw InvalidPropertyException.thrown();
        }

        return optionalValue;
    }

    private String removeSpecialCharsForOptional(String value) {
        return Optional
                .ofNullable(value)
                .filter(Predicate.not(String::isEmpty))
                .map(s ->
                        s
                                .trim()
                                .replaceAll(REGEX_SPECIAL_CHARS, REPLACEMENT))
                .filter(Predicate.not(String::isEmpty))
                .orElse(null);

    }

    @Override
    public String toString() {
        return "Sam{" +
                "publisher='" + publisher + '\'' +
                ", category='" + category + '\'' +
                ", product='" + product + '\'' +
                ", productVersion='" + productVersion + '\'' +
                ", version='" + version + '\'' +
                ", fullVersion='" + fullVersion + '\'' +
                ", edition='" + edition + '\'' +
                ", internalAvailability=" + internalAvailability +
                ", internalEOS=" + internalEOS +
                ", publisherAvailability=" + publisherAvailability +
                ", eos=" + eos +
                ", eoes=" + eoes +
                ", eol=" + eol +
                ", source='" + source + '\'' +
                '}';
    }
}
