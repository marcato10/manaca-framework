package com.marcato.springmarcatoerp.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department")
public class DepartmentResource {
    Logger logger = LoggerFactory.getLogger(DepartmentResource.class);

}
