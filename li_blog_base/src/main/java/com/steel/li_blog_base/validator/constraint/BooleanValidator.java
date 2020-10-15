package com.steel.li_blog_base.validator.constraint;



import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.steel.li_blog_base.validator.annotion.BooleanNotNULL;

/**
 * @author steel
 * @date 2019年12月4日13:16:06
 */
public class BooleanValidator implements ConstraintValidator<BooleanNotNULL, Boolean> {

    @Override
    public void initialize(BooleanNotNULL constraintAnnotation) {

    }

    @Override
    public boolean isValid(Boolean value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return true;
    }
}
