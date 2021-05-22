package com.softserve.app.constant;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public enum Period {
    Day(LocalDateTime.now().minusDays(1)),
    Week(LocalDateTime.now().minusWeeks(1)),
    Month(LocalDateTime.now().minusMonths(1)),
    Year(LocalDateTime.now().minusYears(1));

    private LocalDateTime period;

    Period(LocalDateTime period){
        this.period = period;
    }

    public static Period getPeriodDate(String period) {
        Map<String, Period> map = new HashMap<>();
        map.put("Day", Period.Day);
        map.put("Week", Period.Week);
        map.put("Month", Period.Month);
        map.put("Year", Period.Year);

        return map.get(period);
    }

    public LocalDateTime getPeriodDate() {
        return period;
    }

    @Override
    public String toString() {
        return period.toString();
    }
}
