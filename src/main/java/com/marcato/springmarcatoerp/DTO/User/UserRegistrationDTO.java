package com.marcato.springmarcatoerp.DTO.User;

import com.marcato.springmarcatoerp.jooq.tables.pojos.UserErpPojo;
import jakarta.validation.constraints.NotBlank;

public record UserRegistrationDTO(
        @NotBlank
        String userName,
        @NotBlank
        String fullName){
        public UserRegistrationDTO fromPojo(UserErpPojo pojo) {
                return new UserRegistrationDTO(pojo.getUsername(), pojo.getFullName());
        }

}
