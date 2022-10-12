package com.toufik.rabodatetime.overview;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Slf4j
public class PlayingAroundWithClock {

    private Clock clock;
    private final static Instant INSTANT = Instant.now();
    private final static ZoneId ZONE_ID = ZoneId.systemDefault();
    @Test
    public void instance(){
        System.out.println("instance :");
       clock = Clock.systemDefaultZone();
       Instant instant = clock.instant();
       log.info("The instance time : {}", instant);
    }
    @Test
    public void systemUct(){
        System.out.println("systemUct :");
        clock = Clock.systemUTC();
        log.info("UTC time using systemUct :: {}", clock.instant());
    }
    @Test
    public void fixedClock() {
        System.out.println("fixedClock using Instant.EPOCH :");
        clock = Clock.fixed(Instant.EPOCH, ZoneOffset.UTC);
        log.info("The time zone using Instant.EPOCH:: {}", clock);
        System.out.println("fixedClock using now() :");
        log.info("the time instance right now : {}", INSTANT);
        log.info("the timezoneId : {}", ZONE_ID);
        clock = Clock.fixed(INSTANT, ZONE_ID);
        log.info("The time zone using Instant.EPOCH:: {}", clock);
    }

}
