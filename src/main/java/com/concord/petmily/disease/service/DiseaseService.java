package com.concord.petmily.disease.service;

import com.concord.petmily.disease.repository.DiseaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *  질병 로직
 *
 */
@Service
@RequiredArgsConstructor
public class DiseaseService {

  private final DiseaseRepository diseaseRepository;


}
