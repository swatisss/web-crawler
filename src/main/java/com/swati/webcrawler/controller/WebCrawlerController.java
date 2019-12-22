package com.swati.webcrawler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swati.webcrawler.dto.PageTree;
import com.swati.webcrawler.service.IWebCrawlerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/crawler")
@Slf4j
public class WebCrawlerController {
	
	public static final Integer DEFAULT_DEPTH = 10;
	@Autowired
	IWebCrawlerService webCrawlerService;
	
	@GetMapping
    public ResponseEntity<String> crawlURL(
            @RequestParam final String url,
            @RequestParam final Integer depth) {

        log.info("Request for deep crawling received for url: {}, depth: {}", url, depth);
        return new ResponseEntity<>(webCrawlerService.triggerCrawlReturnToken(url, depth),HttpStatus.OK);
    }
	
	@GetMapping("/status")
    public ResponseEntity<String> checkCrawlingStatus(
            @RequestParam final String token) {
        return new ResponseEntity<>(webCrawlerService.getStatus(token),HttpStatus.OK);
    }
	
	@GetMapping("/nodes")
    public ResponseEntity<PageTree> getCrawledNodes(
            @RequestParam final String token) {
		PageTree pageTree = webCrawlerService.getPageTree(token);
		int total = pageTree.getTotalLinks();
		System.out.println(" total is "+total);
        return new ResponseEntity<>(pageTree,HttpStatus.OK);
    }


}
