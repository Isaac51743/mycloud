package com.isaac.gatewayserver;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GatewayServerApplicationTests {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    void logTest() {
        logger.trace("this is a trace log");
        logger.debug("this is a debug log");
        logger.info("this is a info log");
        logger.warn("this is a warn log");
        logger.error("this is a error log");
    }

}
