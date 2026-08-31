package com.vedant.kakade.push.up.challenges.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetAllEntriesLog {
    long totalRep;

    int streak;

    long todayTotalRep;

    long needPerDay;

    List<GetEntryLog> entryLogList;
}
