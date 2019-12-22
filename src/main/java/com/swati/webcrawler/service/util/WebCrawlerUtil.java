package com.swati.webcrawler.service.util;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.swati.webcrawler.constant.InvalidWebCrawlerFormats;
import com.swati.webcrawler.dto.PageDetail;
import com.swati.webcrawler.service.strategy.URLFilterStategy;


import lombok.extern.slf4j.Slf4j;
@Slf4j
public class WebCrawlerUtil {

	
	public static Optional<PageDetail> crawl(final String url) {
        log.info("Crawling url: {}", url);
        try {
            final Document doc = Jsoup.connect(url).timeout(5000)
                    .followRedirects(true).get();
            final Elements links = doc.select("a[href]");
            final Elements images = doc.select("img");
            final String title = doc.title();
            log.debug("Fetched title: {}, links[{}] for url: {}", title, links, url);
            return Optional.of(new PageDetail(title, url, images.size(), links));
        } catch (final IOException | IllegalArgumentException e) {
            log.error(String.format("Error getting contents of url %s", url), e);
            return Optional.empty();
        }

    }
	
	
	public static boolean isValidURL(String nextUrl, List<String> crawledUrls, URLFilterStategy strategy) {
		if(strategy != null && !strategy.include(nextUrl)){
			return false;
		}
		if(crawledUrls != null && crawledUrls.contains(nextUrl)) { return false; }
		if(nextUrl.startsWith("javascript:"))  { return false; }
		if(nextUrl.startsWith("#"))            { return false; }
		for(InvalidWebCrawlerFormats format: InvalidWebCrawlerFormats.values()) {
			if(nextUrl.endsWith(format.toString())){
				return false;
			}
		}
		return true;
	}
}
