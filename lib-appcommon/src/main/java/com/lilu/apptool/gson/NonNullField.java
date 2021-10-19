package com.lilu.apptool.gson;

import com.google.gson.InstanceCreator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:
 *
 * @author lilu0916 on 2021/7/1
 * No one knows this better than me
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NonNullField {

    Class<? extends InstanceCreator> value() default NonNullFieldConstructor.class;

}
