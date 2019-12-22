package com.swati.webcrawler.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.swati.webcrawler.dto.PageTree;
import com.swati.webcrawler.service.strategy.SameWebsiteOnlyStrategy;
import com.swati.webcrawler.service.util.WebCrawlerUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WebCrawlerServiceImpl implements IWebCrawlerService {

	private static final Map<String, PageTree> PAGE_TREE = new ConcurrentHashMap<>();

	@Autowired
	URLDataService urlDataService;

	public String triggerCrawlReturnToken(final String url, final int depth) {
		String uuid = UUID.randomUUID().toString();
		CompletableFuture.supplyAsync(() -> urlDataService.markInProgressURLCrawling(url, uuid));
		CompletableFuture<PageTree> startCrawling = CompletableFuture
				.supplyAsync(() -> this.deepCrawl(url, depth, uuid, null));
		startCrawling.thenAccept(pageTree -> this.markComplete(pageTree, uuid));
		return uuid;
	}

	@Override
	public PageTree deepCrawl(final String url, final int depth, final String uuid, final List<String> processedUrls) {
		log.debug("Starting crawler for url {} for depth {}", url, depth);
		if (depth < 0) {
			log.debug("Depth crossed for url {}", url);
			return null;
		}
		if (!WebCrawlerUtil.isValidURL(url, processedUrls, new SameWebsiteOnlyStrategy(url))) {
			log.info("Invalid url {}", url);
			return null;
		}
		AtomicInteger linkCount = new AtomicInteger();
		linkCount.set(0);
		final List<String> updatedProcessedUrls = Optional.ofNullable(processedUrls).orElse(new ArrayList<>());
		updatedProcessedUrls.add(url);
		final PageTree pageTree = new PageTree(url);
		WebCrawlerUtil.crawl(url).ifPresent(pageData -> {
			linkCount.getAndAdd(pageData.getLinks().size());
			//pageTree.setTotalLinks(pageData.getLinks().size());
			log.info(" setting total links to {} ",pageData.getLinks().size());
			pageTree.setTitle(pageData.getTitle());
			pageTree.setImageCount(pageData.getImageCount());
			log.info("Found {} links on the web page: {}", pageData.getLinks().size(), url);
			pageData.getLinks().parallelStream().forEach(link -> {
				pageTree.addNodesItem(deepCrawl(link.attr("abs:href"), depth - 1, uuid, updatedProcessedUrls));
			});
		});
		pageTree.setTotalLinks(linkCount.get());
		return pageTree;

	}


	@Override
	public String getStatus(String uuid) {
		return urlDataService.getStatus(uuid);
	}

	@Override
	public PageTree getPageTree(String uuid) {
		PageTree pageTree =  PAGE_TREE.get(uuid);
		PAGE_TREE.remove(uuid);
		return pageTree;
	}

	@Override
	public void markComplete(PageTree pageTree, String uuid) {
		System.out.println(pageTree);
		PAGE_TREE.put(uuid, pageTree);
		urlDataService.markURLCrawlingCompleted(uuid);
		
	}

}
