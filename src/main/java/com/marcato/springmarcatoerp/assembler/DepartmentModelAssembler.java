package com.marcato.springmarcatoerp.assembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.marcato.springmarcatoerp.DTO.Department.DepartmentInputDTO;
import com.marcato.springmarcatoerp.jooq.tables.pojos.DepartmentPojo;
import com.marcato.springmarcatoerp.resources.DepartmentResource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DepartmentModelAssembler implements RepresentationModelAssembler<DepartmentPojo, EntityModel<DepartmentPojo>> {

    @Override
    public EntityModel<DepartmentPojo> toModel(DepartmentPojo entity) {
        EntityModel<DepartmentPojo> departmentModel = EntityModel.of(entity);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Set<String>permissions = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        departmentModel.addIf(permissions.contains("read:department"),()->linkTo(DepartmentResource.class).withSelfRel());
        departmentModel.addIf(permissions.contains("write:department"),()->linkTo(methodOn(DepartmentResource.class).register(new DepartmentInputDTO("Title","Description"))).withRel(IanaLinkRelations.EDIT));
        //departmentModel.addIf(permissions.contains("update:department"),()->linkTo(methodOn(DepartmentResource.class)));
        return departmentModel;
    }
}
