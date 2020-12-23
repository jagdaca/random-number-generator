package com.example.randomNumberGenerator.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.randomNumberGenerator.model.RandomNumber;
import com.example.randomNumberGenerator.service.RandomNumberService;

@CrossOrigin(origins = "*")
@RequestMapping("/api")
@RestController
public class RandomNumberController {
	
	@Autowired
	RandomNumberService randomNumberService;

	@PostMapping("/store")
	public ResponseEntity<?> storeNumber(@RequestBody RandomNumber randomNumber) throws InterruptedException, ExecutionException {
		Map<String,Object> response = new HashMap<String,Object>();
        HttpStatus status = HttpStatus.OK;
        
        randomNumberService.storeNumberDetails(randomNumber);
        
        response.put("message", "Success");
		return new ResponseEntity<>(response, status);
	}
	
	@GetMapping("/numbers")
	public List<RandomNumber> getStoredNumbers() throws InterruptedException, ExecutionException {
		return randomNumberService.getRandomNumbers();
	}
	
	@DeleteMapping("/numbers/{id}")
	public ResponseEntity<?> deleteNumber(@PathVariable String id) throws InterruptedException, ExecutionException {
		Map<String,Object> response = new HashMap<String,Object>();
        HttpStatus status = HttpStatus.OK;
        
		randomNumberService.deleteNumber(id);
		
		response.put("message", "Success");
		return new ResponseEntity<>(response, status);
	}
}
