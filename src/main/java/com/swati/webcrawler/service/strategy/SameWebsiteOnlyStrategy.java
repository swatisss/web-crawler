package com.swati.webcrawler.service.strategy;


/**
 */
public class SameWebsiteOnlyStrategy implements URLFilterStategy {

    protected String domainUrl = null;

    public SameWebsiteOnlyStrategy(String domainUrl) {
        this.domainUrl = domainUrl;
    }

    public boolean include(String url) {
        return url.startsWith(this.domainUrl);
    }
}
