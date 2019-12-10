package com.swati.webcrawler.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "crawling_status")
@Data
@NoArgsConstructor
public class URLData implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;

	  private String url;

	  private String uuid;

	  private String status;

	public URLData(String url, String uuid, String status) {
		super();
		this.url = url;
		this.uuid = uuid;
		this.status = status;
	}
	  

}
