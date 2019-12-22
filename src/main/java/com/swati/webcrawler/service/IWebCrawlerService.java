package com.swati.webcrawler.service;

import java.util.List;

import com.swati.webcrawler.dto.PageTree;


public interface IWebCrawlerService {
	 
	 String triggerCrawlReturnToken(final String url, final int depth);
	 
	 String getStatus(final String uuid);
	 
	 PageTree getPageTree(String uuid);
	 
	 void markComplete(PageTree pageTree, String uuid);
	 
	 PageTree deepCrawl(final String url, final int depth, final String uuid, final List<String> processedUrls);
}
