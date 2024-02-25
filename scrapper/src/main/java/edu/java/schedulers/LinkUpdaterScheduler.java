package edu.java.schedulers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;

public class LinkUpdaterScheduler {
    private final Logger logger = LogManager.getLogger();

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    void update() {
        logger.info("Starting scheduler...");
    }
}
