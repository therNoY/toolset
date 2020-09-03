package pers.mihao.toolset.annotation;

import pers.mihao.toolset.annotation.handle.InEnumValidator;
import pers.mihao.toolset.enums.BaseEnum;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = InEnumValidator.class)
public @interface InEnum {

    String message() default "{参数不在可选范围内}";

    Class<? extends BaseEnum> in();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
