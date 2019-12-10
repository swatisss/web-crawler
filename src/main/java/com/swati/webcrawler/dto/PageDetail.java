package com.swati.webcrawler.dto;

import java.util.List;

import org.jsoup.select.Elements;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.net.URL;
@Data
@AllArgsConstructor
public class PageDetail {

	private String title;

	private String url;

	private int imageCount;
	
	private Elements links;
}
