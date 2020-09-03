package pers.mihao.toolset.annotation.handle;

import pers.mihao.toolset.annotation.InEnum;
import pers.mihao.toolset.enums.BaseEnum;
import pers.mihao.toolset.enums.HttpMethod;
import pers.mihao.toolset.vo.MyException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InEnumValidator implements ConstraintValidator<InEnum, Object> {

    Class clazz;

    @Override
    public void initialize(InEnum constraintAnnotation) {
        this.clazz = constraintAnnotation.in();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (!Arrays.asList(clazz.getInterfaces()).contains(BaseEnum.class)) {
            throw new MyException("验证类型错误");
        }

        List<String> enums = new ArrayList<>();
        if (value == null) {
            return false;
        }
        if (HttpMethod.class.equals(clazz)) {
            for (HttpMethod httpMethod : HttpMethod.values()) {
                enums.add(httpMethod.type());
            }
            if (!enums.contains(value.toString())) {
                return false;
            } else {
                return true;
            }
        }

        throw new MyException("验证的枚举没有加入");
    }

}
