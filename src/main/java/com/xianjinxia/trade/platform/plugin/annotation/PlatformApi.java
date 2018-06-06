package com.xianjinxia.trade.platform.plugin.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 不继承spring的Component，在接口上使用，不侵入spring bean
 *
 * 注：此注解不加Inherited，仅在接口上使用
 *
 */
//@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Component
public @interface PlatformApi {

    String value() default "";

}
