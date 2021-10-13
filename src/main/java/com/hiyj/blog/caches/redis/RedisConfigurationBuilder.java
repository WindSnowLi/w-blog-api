/*
 *    Copyright 2015-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.hiyj.blog.caches.redis;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Converter from the Config to a proper {@link RedisConfig}.
 *
 * @author Eduardo Macarron
 */
@Getter
@Setter
@Component
public final class RedisConfigurationBuilder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * Hidden constructor, this class can't be instantiated.
     */
    private RedisConfigurationBuilder() {
    }

    public static RedisConfig getRedisConfig() {
        return applicationContext.getBean(RedisConfig.class);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        RedisConfigurationBuilder.applicationContext = applicationContext;
    }
}
