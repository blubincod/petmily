package com.concord.petmily.domain.disease.dto;

import com.concord.petmily.domain.pet.entity.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.util.List;

public class DiseaseDto {

    @Getter
    public static class Create {

        @NotNull(message = "질병 이름은 필수입니다.")
        @NotEmpty(message = "질병 이름은 빈 값일 수 없습니다.")
        @Size(min = 2, max = 100, message = "질병 이름은 2자 이상 100자 이하로 입력해야 합니다.")
        private String name;

        @NotNull(message = "질병 설명은 필수입니다.")
        @NotEmpty(message = "질병 설명은 빈 값일 수 없습니다.")
        @Size(max = 500, message = "질병 설명은 500자 이하로 입력해야 합니다.")
        private String description;

        @Min(value = 0, message = "접종 기한은 최소 0 이상이어야 합니다.")
        private int vaccinationDeadline;

        @NotEmpty(message = "적용 대상 동물 카테고리를 하나 이상 선택해야 합니다.")
        private List<Category> animalCategories;

    }

    @Getter
    public static class Modifier {
        @Size(min = 2, max = 100, message = "질병 이름은 2자 이상 100자 이하로 입력해야 합니다.")
        private String name;

        @Size(max = 500, message = "질병 설명은 500자 이하로 입력해야 합니다.")
        private String description;

        private Integer vaccinationDeadline;  // null이면 수정 없음

        private String animalCategory; // 카테고리도 nullable로 허용
    }
}
