package com.concord.petmily.domain.disease.repository;

import com.concord.petmily.domain.disease.entity.Disease;
import com.concord.petmily.domain.pet.entity.Category;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 질병 저장소 인터페이스
 * 데이터베이스와의 CRUD 작업을 처리
 */
@Repository
public interface DiseaseRepository extends JpaRepository<Disease,Long> {
  Optional<Disease> findByName(String name);

  // 이름과 카테고리로 검색
  Page<Disease> findByNameContainingIgnoreCaseAndAnimalCategoriesIn(String name, Set<Category> categories, Pageable pageable);

  // 이름으로만 검색
  Page<Disease> findByNameContainingIgnoreCase(String name, Pageable pageable);

  // 카테고리로만 검색
  Page<Disease> findByAnimalCategoriesIn(Set<Category> categories, Pageable pageable);
}
