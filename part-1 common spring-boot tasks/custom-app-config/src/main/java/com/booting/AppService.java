package com.booting;

import com.booting.configurationproperties.AppProperties;
import org.springframework.stereotype.Service;

@Service
public class AppService {

    private final AppProperties appProperties;

    public AppService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public AppProperties getAppProperties() {
        return this.appProperties;
    }
}

