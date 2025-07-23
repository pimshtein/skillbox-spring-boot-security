package ru.skillbox.spring.boot.demospringprofiles.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("local")
public class TestOnlyService {

    // Этот бин будет создан только если активен профиль test
    public TestOnlyService() {
        Logger log = LoggerFactory.getLogger(this.getClass());
        log.warn("TestOnlyService created! Profile 'test' is active.");
    }
}
