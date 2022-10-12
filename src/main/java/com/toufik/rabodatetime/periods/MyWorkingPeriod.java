package com.toufik.rabodatetime.periods;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
public class MyWorkingPeriod {
    public static final LocalTime AM_START_TIME = LocalTime.of(9, 30, 00);
    public static final LocalDate START_DATE = LocalDate.of(2022, 10, 12);
    public static final Duration WORK_DURATION = Duration.ofMinutes(210);
    private static final LocalTime  PM_START_TIME = LocalTime.of(13, 00, 00);;

    public static WorkPeriod createFirstWorkPeriod(){
        LocalDateTime startLocalDatetime;
        startLocalDatetime = LocalDateTime.of(2022, 10, 12, 13, 30, 30);
        LocalDate localDate = LocalDate.of(2022, 10, 12);
        LocalTime localTime = LocalTime.of(13, 30, 30);
        startLocalDatetime = LocalDateTime.of(localDate, localTime);

        Duration morningLength;
        morningLength = Duration.ofHours(3).plusMinutes(30);
        morningLength = Duration.ofMinutes(210);

        LocalDateTime endDateTime;
        endDateTime = startLocalDatetime.with(LocalTime.of(3, 30));
        endDateTime = startLocalDatetime.plus(morningLength);

        WorkPeriod wp = WorkPeriod.of(startLocalDatetime, endDateTime);
        return  wp;
    }
    public static WorkPeriod createFirstWorkPeriodRefactored(){
        LocalDateTime mystartLocalDatetime = LocalDateTime.of(START_DATE, AM_START_TIME);
        LocalDateTime endDateTime = mystartLocalDatetime.plus(WORK_DURATION);
        return WorkPeriod.of(mystartLocalDatetime, endDateTime);
    }

    public static WorkPeriod createMorningPeriod(LocalDate date){
        LocalDateTime mystartLocalDatetime = LocalDateTime.of(date, AM_START_TIME);
        LocalDateTime endDateTime = mystartLocalDatetime.plus(WORK_DURATION);
        return WorkPeriod.of(mystartLocalDatetime, endDateTime);
    }

    public static WorkPeriod createAfternoonPeriod(LocalDate date){
        LocalDateTime mystartLocalDatetime = LocalDateTime.of(date, PM_START_TIME);
        LocalDateTime endDateTime = mystartLocalDatetime.plus(WORK_DURATION);
        return WorkPeriod.of(mystartLocalDatetime, endDateTime);
    }

    public static List<WorkPeriod> generateWorkPeriods(LocalDate startDate, int dayCount){
        List<LocalDate> workingDays = generateWorkingDays(startDate, dayCount);
        return generateWorkingDays(workingDays);
    }

    public static List<WorkPeriod> generateWorkingDays(List<LocalDate> workingDays){
        return workingDays.stream()
                .flatMap(d -> Stream.of(createMorningPeriod(d), createAfternoonPeriod(d)))
                .collect(Collectors.toList());
    }
    private static List<LocalDate> generateWorkingDays(LocalDate startDate, int dayCount) {
        return Stream.iterate(startDate, d -> d.plusDays( 1))
                .filter(d -> isWorkingDay(d))
                .limit(dayCount)
                .collect(Collectors.toList());

    }
    private static boolean isWorkingDay(LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return !(dayOfWeek == DayOfWeek.SUNDAY || dayOfWeek == DayOfWeek.SATURDAY);
    }

    @Test
    public void test_working_days_periods(){
        List<WorkPeriod> workPeriods = generateWorkPeriods(LocalDate.now(), 5);
        workPeriods.forEach(System.out::println);

    }
}
