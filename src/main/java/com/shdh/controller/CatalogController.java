package com.shdh.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.env.Environment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shdh.jpa.CatalogEntity;
import com.shdh.service.CatalogService;
import com.shdh.vo.ResponseCatalog;

@RestController
@RequestMapping(name = "/catalog-service")
public class CatalogController {
	Environment env;
	CatalogService catalogService;
	
	@Autowired
	public CatalogController(Environment env, CatalogService catalogService) {
		this.env = env;
		this.catalogService = catalogService;
	}
	
	@GetMapping("/health_check")
	public String healthCheck() {
		return String.format("It's Working in Catalog Servie on PORT %s",env.getProperty("local.server.port"));
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<ResponseCatalog>> getCatalogs(){
		Iterable<CatalogEntity> userList = catalogService.getAllCatalogs();
		
		List<ResponseCatalog> result = new ArrayList<>();
		userList.forEach(v -> {
			result.add(new ModelMapper().map(v, ResponseCatalog.class));
		});
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
}
