package com.marcato.springmarcatoerp.utils;

import com.marcato.springmarcatoerp.jooq.tables.pojos.DepartmentPojo;

import java.util.Arrays;
import java.util.List;

public class DepartmentTestData {
    public static DepartmentPojo createHumanResources() {
        DepartmentPojo hr = new DepartmentPojo();
        hr.setId(null);
        hr.setTitle("Recursos Humanos");
        hr.setDescription("Departamento responsável pela gestão de pessoas, contratação, treinamento e folha de pagamento.");
        return hr;
    }

    public static DepartmentPojo createTechnology() {
        DepartmentPojo ti = new DepartmentPojo();
        ti.setId(2);
        ti.setTitle("Tecnologia da Informação");
        ti.setDescription("Responsável pela infraestrutura de TI, suporte técnico, desenvolvimento e segurança de sistemas.");
        return ti;
    }

    public static DepartmentPojo createFinance() {
        DepartmentPojo finance = new DepartmentPojo();
        finance.setId(3);
        finance.setTitle("Financeiro");
        finance.setDescription("Gerencia as finanças da empresa, incluindo contabilidade, tesouraria e planejamento financeiro.");
        return finance;
    }

    public static DepartmentPojo createMarketing() {
        DepartmentPojo marketing = new DepartmentPojo();
        marketing.setId(4);
        marketing.setTitle("Marketing");
        marketing.setDescription("Cuida da promoção da marca, campanhas publicitárias e estratégias de comunicação com o cliente.");
        return marketing;
    }

    public static List<DepartmentPojo> getAllDepartments() {
        return Arrays.asList(
                createHumanResources(),
                createTechnology(),
                createFinance(),
                createMarketing()
        );
    }
}
