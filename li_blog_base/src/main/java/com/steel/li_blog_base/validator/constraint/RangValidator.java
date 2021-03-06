package com.steel.li_blog_base.validator.constraint;


import com.steel.li_blog_base.validator.annotion.Range;
import com.steel.li_blog_utils.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author steel
 * @date 2019年12月4日13:17:03
 */
public class RangValidator implements ConstraintValidator<Range, String> {
    private long min;
    private long max;
    private String type;

    @Override
    public void initialize(Range constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null == value || StringUtils.isBlank(value)) {
            return false;
        }
        return value.length() >= min && value.length() <= max;
    }
}
