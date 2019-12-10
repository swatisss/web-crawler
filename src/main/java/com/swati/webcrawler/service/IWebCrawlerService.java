package com.swati.webcrawler.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.swati.webcrawler.dto.PageTree;


public interface IWebCrawlerService {

	 Set<String> crawledURLs = new HashSet<String>();
	 
	 String triggerCrawlReturnToken(final String url, final int depth);
	 
	 String getStatus(final String uuid);
	 
	 PageTree getPageTree(String uuid);
	 
	 PageTree deepCrawl(final String url, final int depth, final String uuid, final List<String> processedUrls);
}
