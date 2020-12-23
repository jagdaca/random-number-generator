package com.example.randomNumberGenerator.service;


import java.util.ArrayList;
import java.util.Collections;

import java.util.List;

import java.util.concurrent.ExecutionException;


import org.springframework.stereotype.Service;

import com.example.randomNumberGenerator.model.RandomNumber;
import com.google.api.core.ApiFuture;


import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import com.google.firebase.cloud.FirestoreClient;

@Service
public class RandomNumberService {
	 public static final String COL_NAME="numbers";  
	 
	 public void storeNumberDetails(RandomNumber randomNumber) throws InterruptedException, ExecutionException {  
		 Firestore dbFirestore = FirestoreClient.getFirestore();
		 dbFirestore.collection(COL_NAME).document(Long.toString(randomNumber.getId())).set(randomNumber);
        //return getRandomNumbers();   
	 }
	 
	 public void deleteNumber(String id) throws InterruptedException, ExecutionException {
		 Firestore dbFirestore = FirestoreClient.getFirestore();
		 dbFirestore.collection(COL_NAME).document(id).delete();
		 //return getRandomNumbers();
	 }
	 
	 public List<RandomNumber> getRandomNumbers() throws InterruptedException, ExecutionException {
		 Firestore dbFirestore = FirestoreClient.getFirestore();
		 List<RandomNumber> queriedNumbers = new ArrayList<RandomNumber>();
		 
		 ApiFuture<QuerySnapshot> future = dbFirestore.collection(COL_NAME).get();
		 List<QueryDocumentSnapshot> documents = future.get().getDocuments();

		 if(documents.size() > 0) {
			 for (QueryDocumentSnapshot document : documents) {
				 
				 //can't make this work for some reason
				 //RandomNumber randomNumber = document.toObject(RandomNumber.class);
				 
				 RandomNumber randomNumber = new RandomNumber();

				 randomNumber.setId(Long.valueOf(document.getId().toString()));
				 randomNumber.setNumber(Integer.valueOf(document.get("number").toString()));
				 randomNumber.setTimestamp(document.get("timestamp").toString());
				 queriedNumbers.add(randomNumber);
			 }
			 Collections.reverse(queriedNumbers);
		 }
		 else {
			 System.out.println("no data to pull");
		 }
		 return queriedNumbers;
	 }
	 

}
