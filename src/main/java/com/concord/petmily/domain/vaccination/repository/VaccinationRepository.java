package com.concord.petmily.domain.vaccination.repository;

import com.concord.petmily.domain.disease.entity.Disease;
import com.concord.petmily.domain.pet.entity.Pet;
import com.concord.petmily.domain.vaccination.entity.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 접종 저장소 인터페이스
 * 데이터베이스와의 CRUD 작업을 처리
 */
@Repository
public interface VaccinationRepository extends JpaRepository<Vaccination, Long> {

    List<Vaccination> findByPetId(Long petId);

    Optional<Vaccination> findByIdAndPetId(Long petId, Long vaccinationId);

    Optional<Vaccination> findByPetIdAndDiseaseId(Long petId, Long diseaseId);
}
