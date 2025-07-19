package com.marcato.springmarcatoerp.DTO.User;

import com.marcato.springmarcatoerp.jooq.tables.pojos.UsererpPojo;
import jakarta.validation.constraints.NotBlank;

public record UserRegistrationDTO(
        @NotBlank
        String userName,
        @NotBlank
        String fullName) {
        public UserRegistrationDTO fromPojo(UsererpPojo pojo) {
                return new UserRegistrationDTO(pojo.getUsername(), pojo.getFullName());
        }

}
