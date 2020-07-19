package com.challenge.desafio;

import com.challenge.annotation.Somar;
import com.challenge.annotation.Subtrair;
import com.challenge.interfaces.Calculavel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;

public class CalculadorDeClasses implements Calculavel {

    @Override
    public BigDecimal somar(Object object) {
        return sumValuesFromClass(object, Somar.class);
    }

    @Override
    public BigDecimal subtrair(Object object) {
        return sumValuesFromClass(object, Subtrair.class);
    }

    @Override
    public BigDecimal totalizar(Object object) {
        return somar(object).subtract(subtrair(object));
    }

    public BigDecimal sumValuesFromClass(Object object, Class<? extends Annotation> annotation) {
        BigDecimal counter = BigDecimal.ZERO;

        for (Field value : object.getClass().getDeclaredFields()) {
            if(value.isAnnotationPresent(annotation) && value.getType() == BigDecimal.class){
                try{
                    value.setAccessible(true);
                    BigDecimal valuefromClass = new BigDecimal(String.valueOf(value.get(object)));
                    counter = counter.add(valuefromClass);
                } catch (IllegalAccessException e){
                    e.printStackTrace();
                    throw new RuntimeException();
                }
            }
        }
        return counter;
    }
}
