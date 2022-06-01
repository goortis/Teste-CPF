package com.goortis.cpfsearch.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goortis.cpfsearch.model.CpfModel;
import com.goortis.cpfsearch.repository.CpfRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/cpf")
public class CpfController {

	
	@Autowired
	private CpfRepository cpfRepository;
	
	@GetMapping("/list")
	public List<CpfModel> getAllCpf(){
		return cpfRepository.findAll();
	}
	
	@GetMapping("/list/{id}")
	public ResponseEntity<Object> getCpfById(@PathVariable(value = "id") Long id) {
		Optional<CpfModel> cpfModelOptional = cpfRepository.findById(id);
		if(!cpfModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CPF not found.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(cpfRepository.findById(id));
	}
	
	@PostMapping("/list")
	public ResponseEntity<Object> createCpf(@RequestBody CpfModel cpfModel) {
		if(cpfModel.getCpf().length() != 11) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("CPF needs to contain 11 digits");
		}
		if(cpfRepository.existsByCpf(cpfModel.getCpf())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: CPF is already in use!");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(cpfRepository.save(cpfModel));
	}
	
	@PutMapping("/list/{id}")
	public ResponseEntity<Object> updateCpf(@PathVariable(value = "id") Long id, @RequestBody CpfModel cpfModel) {
		Optional<CpfModel> cpfModelOptional = cpfRepository.findById(id);
		if(!cpfModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CPF not found.");
		}
		var cpf = cpfModelOptional.get();
		cpf.setCpf(cpfModel.getCpf());
		
		return ResponseEntity.status(HttpStatus.OK).body(cpfRepository.save(cpf));
	}
	
	@DeleteMapping("/list/{id}")
	public void deleteCpf(@PathVariable(value = "id") Long id) {
		cpfRepository.deleteById(id);
	}
	
	
}
