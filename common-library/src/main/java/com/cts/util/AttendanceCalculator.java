package com.cts.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class AttendanceCalculator {
    public static double calculateAttendance(Long totalAttendedDays,Long totalDaysToBeAttend){
        if (totalDaysToBeAttend <= 0) {
            return 0.0;
        }
        double rawPercentage = ((double) totalAttendedDays / totalDaysToBeAttend) * 100;
        double roundedPercentage = new BigDecimal(rawPercentage).setScale(2, RoundingMode.HALF_UP).doubleValue();

        if (roundedPercentage > 100.0) {
            roundedPercentage = 100.00;
        }
        return roundedPercentage;
    }
    public static long getCalendarDaysBetween(LocalDateTime start, LocalDateTime end) {
        return java.time.temporal.ChronoUnit.DAYS.between(start.toLocalDate(), end.toLocalDate());
    }
}
