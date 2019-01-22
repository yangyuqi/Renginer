package com.qxmagic.railwayuserterminal.injector;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Christian on 2017/3/9.
 * 单例标识  标识生命周期activity一样
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
