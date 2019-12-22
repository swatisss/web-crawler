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

    public PageTree addNodesItem(final PageTree nodesItem) {
        if (nodes == null) {
        	//this.totalLinks =0;
            nodes = new ArrayList<>();
        }
        if (nodesItem != null) {
            nodes.add(nodesItem);  
            //totalLinks = totalLinks + nodesItem.getTotalLinks();
            //System.out.println("Setting total links to "+totalLinks);
        } 
        
        return this;
    }
	public int getTotalImageCount() {
		return totalImageCount + nodes.stream().collect(Collectors.summingInt(node -> node.getImageCount()));
	}
    

}
