package com.shdh.messagequeue;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shdh.jpa.CatalogEntity;
import com.shdh.jpa.CatalogRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaConsumer {
	CatalogRepository repository;
	
	@Autowired
	public KafkaConsumer(CatalogRepository repository) {
		this.repository = repository;
	}
	
	
	@KafkaListener(topics = "example-catalog-topic", groupId = "consumerGroupId")
	public void updateQty(String kafkaMessage) {
		log.info("Kafka Message: -> "+kafkaMessage);
		
		//역직렬화 
		Map<Object, Object> map = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
		}catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		CatalogEntity entity = repository.findByProductId((String)map.get("productId"));
		if(entity != null) {
			entity.setStock(entity.getStock() - (Integer)map.get("qty"));
			repository.save(entity);
		}
		
		
	}
	
	
}
