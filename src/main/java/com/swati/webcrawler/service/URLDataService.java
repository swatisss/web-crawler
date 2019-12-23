package com.swati.webcrawler.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swati.webcrawler.dao.repository.URLDataRepository;
import com.swati.webcrawler.dto.URLData;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class URLDataService {
	
	@Autowired
	URLDataRepository urlDataRepository;
	
	public URLData markInProgressURLCrawling(String url, String uuid){
		log.info(" mark crwaling inprogress for {} ",uuid);
		URLData urlData = new URLData(url,uuid, "INPROGRESS");
		try {
			return urlDataRepository.save(urlData);
		}
		catch(Exception e) {
			log.error(" Exception occured while saving data");
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	public void markURLCrawlingCompleted( String uuid){
		log.info(" mark crwaling complete for {} ",uuid);
		try {
			 urlDataRepository.markCompleted(uuid, "COMPLETED");
		}
		catch(Exception e) {
			log.error(" Exception occured while saving data");
			e.printStackTrace();
		}
	}
	public String getStatus(String uuid) {
		try {
			Optional<URLData> urlData =  urlDataRepository.getByUuid(uuid);
			return urlData.isPresent()? urlData.get().getStatus():null;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
