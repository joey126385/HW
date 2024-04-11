package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.CoindeskModel;
import com.example.demo.repository.CoindeskRepository;

@Service
public class CoindeskService {

	@Autowired
	private CoindeskRepository coindeskRepository;
	
	

    
    public CoindeskModel saveUser(CoindeskModel coindeskModel) {
        return coindeskRepository.save(coindeskModel);
    }
    
    public List<CoindeskModel> listCoindesks(){
    	return	(List<CoindeskModel>) coindeskRepository.findAll();
    	// null;
    }
}
