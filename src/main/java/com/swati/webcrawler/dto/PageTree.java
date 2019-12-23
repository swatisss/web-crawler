package com.swati.webcrawler.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class PageTree {
	
	private String url;

    private String title;
   
    private int totalLinks;
    
    private int imageCount;
    
    private int totalImageCount;
    
    private List<PageTree> nodes;


    public PageTree(final String url) {
        this.url = url;
    }

    public PageTree url(final String url) {
        this.url = url;
        return this;
    }

    public PageTree title(final String title) {
        this.title = title;
        return this;
    }

    public PageTree nodes(final List<PageTree> nodes) {
        this.nodes = nodes;
        return this;
    }
    public int getTotalLinks() {
    	if(nodes == null) {
    		return 0;
    	}
    	return nodes.stream().collect(Collectors.summingInt(PageTree::getTotalLinks))+nodes.size();
    }
    
    public int getTotalImageCount() {
    	if(nodes == null) {
    		return 0;
    	}
    	return nodes.stream().collect(Collectors.summingInt(PageTree::getTotalImageCount)) + this.imageCount;
    }
    public PageTree addNodesItem(final PageTree nodesItem) {
        if (nodes == null) {
            nodes = new ArrayList<>();
        }
        if (nodesItem != null) {
            nodes.add(nodesItem);  
        } 
        
        return this;
    }

	@Override
	public String toString() {
		return "PageTree [url=" + url + ", title=" + title + ", imageCount=" + imageCount + ", nodes=" + nodes + "]";
	}
	
    
    

}
