package com.concord.petmily.domain.disease.dto;

import com.concord.petmily.domain.pet.entity.PetType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.util.List;

public class DiseaseDto {

    @Getter
    @Setter
    public static class Create {
        @NotNull
        @NotEmpty
        @Size(min = 2, max = 100)
        private String name;

        @NotNull
        @NotEmpty
        @Size(max = 500)
        private String description;

        @Min(0)
        private Integer vaccinationDeadline;

        @NotEmpty
        private PetType petType;
    }

    @Getter
    @Setter
    public static class Modifier {
        @Size(min = 2, max = 100)
        private String name;

        @Size(max = 500)
        private String description;

        private Integer vaccinationDeadline;

        private PetType petType;
    }
}