package com.fundamentosplatzi.springboot.fundamentos.component;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
//@Primary
public class ComponentImplement implements ComponentDependency{

    @Override
    public void doSaludar() {
        System.out.println("Hola mundo desde mi componente");
    }
}
