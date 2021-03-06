package com.cafe24.jblog.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ TYPE, PARAMETER, METHOD })
public @interface Auth {
	
	// @Auth(value=)
	// public String value() default "user";
	
	// @Auth(test=)
	// public int test() default 1;
	
	public enum Role {ADMIN, USER}
	
	// @Auth(Role.ADMIN) or @Auth(Role.USER)
	public Role role() default Role.USER;
	

}
