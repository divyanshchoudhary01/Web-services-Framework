package com.thinking.machines.webrock.Annotations;
import java.lang.annotation.*;
//@Target(ElementType.TYPE)  
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParameter{
String value();
}
