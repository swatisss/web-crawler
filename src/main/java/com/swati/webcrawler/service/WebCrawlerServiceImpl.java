package com.swati.webcrawler.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.swati.webcrawler.dto.PageTree;
import com.swati.webcrawler.dto.URLData;
import com.swati.webcrawler.service.strategy.SameWebsiteOnlyStrategy;
import com.swati.webcrawler.service.util.WebCrawlerUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WebCrawlerServiceImpl implements IWebCrawlerService{
	
	private static final Map<String,PageTree> PAGE_TREE = new HashMap<>();
	
	@Autowired
	URLDataService URLDataService;
	
	public String triggerCrawlReturnToken(final String url, final int depth) {
		String uuid = UUID.randomUUID().toString();
		CompletableFuture<URLData> markInprogress
		  = CompletableFuture.supplyAsync(() -> URLDataService.markInProgressURLCrawling(url, uuid));
		CompletableFuture<PageTree> startCrawling
		  = CompletableFuture.supplyAsync(() -> this.deepCrawl(url, depth, uuid,null));
		startCrawling.thenApply((pageTree) -> PAGE_TREE.put(uuid, pageTree));
		return uuid;
	}

	@Override
	public PageTree deepCrawl(final String url, final int depth, final String uuid, final List<String> processedUrls) {
        log.debug("Starting crawler for url {} for depth {}", url, depth);
        if (depth < 0) {
            log.debug("Depth crossed returning for url {}", url);
            return null;
        } else {
        	if(!WebCrawlerUtil.isValidURL(url, crawledURLs, new SameWebsiteOnlyStrategy(url))) {
        		 log.debug("Invalid url {}", url);
                 return null;
			}
        	crawledURLs.add(url);
            final List<String> updatedProcessedUrls = Optional.ofNullable(processedUrls).orElse(new ArrayList<>());
            if (!updatedProcessedUrls.contains(url)) {
                updatedProcessedUrls.add(url);
                final PageTree pageTree = new PageTree(url);
                WebCrawlerUtil.crawl(url).ifPresent(pageData -> {
                    pageTree.setTitle(pageData.getTitle());
                    pageTree.setImageCount(pageData.getImageCount());
                    log.info("Found {} links on the web page: {}", pageData.getLinks().size(), url);
                    pageData.getLinks().parallelStream().forEach(link -> {
                        pageTree.addNodesItem(deepCrawl(link.attr("abs:href"), depth - 1, uuid,updatedProcessedUrls));
                    });
                });
                return pageTree;
            } else {
                return null;
            }
        }

    }

	@Override
	public String getStatus(String uuid) {
		return URLDataService.getStatus(uuid);
	}
 
	@Override
	public PageTree getPageTree(String uuid) {
		return PAGE_TREE.get(uuid);
	}

}
