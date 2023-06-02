package com.fundamentosplatzi.springboot.fundamentos.component;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
//@Primary
public class ComponentTwoImplement implements ComponentDependency{

    @Override
    public void doSaludar() {
        System.out.println("Hola Mundo desde mi componente dos");
    }
}
