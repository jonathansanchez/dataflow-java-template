package com.verix.sam.domain.model;

import java.text.SimpleDateFormat;

public class Sam {
    private final String publisher;
    private final String category;
    private final String product;
    private final String version;
    private final String fullVersion;
    private final String edition;
    private final SimpleDateFormat internalAvailability;
    private final SimpleDateFormat internalEOS;
    private final SimpleDateFormat publisherAvailability;
    //Publisher End Of Support
    private final SimpleDateFormat eos;
    //Publisher End Of Extended Support
    private final SimpleDateFormat eoes;
    //Publisher End Of Life
    private SimpleDateFormat eol;
    private String source;

    public Sam(String publisher, String category, String product, String version, String fullVersion, String edition, SimpleDateFormat internalAvailability, SimpleDateFormat internalEOS, SimpleDateFormat publisherAvailability, SimpleDateFormat eos, SimpleDateFormat eoes, SimpleDateFormat eol, String source) {
        this.publisher = publisher;
        this.category = category;
        this.product = product;
        this.version = version;
        this.fullVersion = fullVersion;
        this.edition = edition;
        this.internalAvailability = internalAvailability;
        this.internalEOS = internalEOS;
        this.publisherAvailability = publisherAvailability;
        this.eos = eos;
        this.eoes = eoes;
        this.eol = eol;
        this.source = source;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getCategory() {
        return category;
    }

    public String getProduct() {
        return product;
    }

    public String getVersion() {
        return version;
    }

    public String getFullVersion() {
        return fullVersion;
    }

    public String getEdition() {
        return edition;
    }

    public SimpleDateFormat getInternalAvailability() {
        return internalAvailability;
    }

    public SimpleDateFormat getInternalEOS() {
        return internalEOS;
    }

    public SimpleDateFormat getPublisherAvailability() {
        return publisherAvailability;
    }

    public SimpleDateFormat getEos() {
        return eos;
    }

    public SimpleDateFormat getEoes() {
        return eoes;
    }

    public SimpleDateFormat getEol() {
        return eol;
    }

    public String getSource() {
        return source;
    }
}
