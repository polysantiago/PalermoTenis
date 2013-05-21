package com.palermotenis.support;

public interface TestService<T> {

    T refresh(T entity);

    T create();

    T getAny();
}
