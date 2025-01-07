package com.akalin.spring.core.io;


import cn.hutool.core.lang.Assert;

import java.net.MalformedURLException;
import java.net.URL;

public class DefaultResourceLoader implements ResourceLoader {

    @Override
    public Resource getResource(String location) {
        Assert.notNull(location, "Location must not be null");

        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            // classpath
            String path = location.substring(CLASSPATH_URL_PREFIX.length());
            return new ClassPathResource(path);
        }
        try {
            // url
            URL url = new URL(location);
            return new UrlResource(url);
        } catch (MalformedURLException e) {
            // file
            return new FileSystemResource(location);
        }
    }

}
