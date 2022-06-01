package com.goortis.cpfsearch.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goortis.cpfsearch.model.CpfModel;

@Repository
public interface CpfRepository extends JpaRepository<CpfModel, Long>{

	boolean existsByCpf(String cpf);

	
}
