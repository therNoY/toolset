package pers.mihao.toolset.common.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.SOURCE;


/**
 * 知识点注解 方便找知识点 或者容易出错的地方
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(SOURCE)
@Documented
public @interface KnowledgePoint {

    String value();
}
