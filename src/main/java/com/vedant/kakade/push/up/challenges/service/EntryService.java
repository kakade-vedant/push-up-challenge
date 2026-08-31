package com.vedant.kakade.push.up.challenges.service;

import com.vedant.kakade.push.up.challenges.exception.IdModificationException;
import com.vedant.kakade.push.up.challenges.exception.ItemNotFoundException;
import com.vedant.kakade.push.up.challenges.model.DBEntity.DBEntryLog;
import com.vedant.kakade.push.up.challenges.model.request.AddEntryLog;
import com.vedant.kakade.push.up.challenges.model.request.DeleteEntryLog;
import com.vedant.kakade.push.up.challenges.model.response.GetAllEntriesLog;
import com.vedant.kakade.push.up.challenges.model.response.GetEntryLog;
import com.vedant.kakade.push.up.challenges.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class EntryService {
    @Autowired
    EntryRepository entryRepository;

    @Autowired
    UtilityService utilityService;

    public GetEntryLog addEntry(AddEntryLog addEntryLog) throws IdModificationException {
        DBEntryLog DBEntryLog = new DBEntryLog();

        DBEntryLog.setId(utilityService.generateUUID().toString());
        DBEntryLog.setCount(addEntryLog.getCount());
        DBEntryLog.setDate(LocalDate.now());

        entryRepository.save(DBEntryLog);

        return generateSavedEntryResponse(DBEntryLog);
    }

    private GetEntryLog generateSavedEntryResponse(DBEntryLog dbentryLog) throws IdModificationException {
        GetEntryLog entryResponse = new GetEntryLog();

        entryResponse.setId(dbentryLog.getId());
        entryResponse.setCount(dbentryLog.getCount());
        entryResponse.setDate(dbentryLog.getDate());

        return entryResponse;
    }

    public GetAllEntriesLog getEntries() throws IdModificationException{
        List<GetEntryLog> entries = new ArrayList<>();

        List<DBEntryLog> dbEntries = entryRepository.findAll();

        long totalReps = 0L;
        long todayTotalRep = 0L;
        for (DBEntryLog dbEntryLog: dbEntries) {
            GetEntryLog getEntryLog = new GetEntryLog();

            getEntryLog.setId(dbEntryLog.getId());
            getEntryLog.setCount(dbEntryLog.getCount());
            getEntryLog.setDate(dbEntryLog.getDate());

            entries.add(getEntryLog);

            totalReps += getEntryLog.getCount();

            if (ChronoUnit.DAYS.between(getEntryLog.getDate(), LocalDate.now()) == 0) {
                todayTotalRep += getEntryLog.getCount();
            }
        }

        entries.sort(Comparator.comparing(GetEntryLog::getDate).reversed());

        int streak = entries.isEmpty() ? 0 : 1; // count the most recent entry itself
        for (int i = 1; i < entries.size(); i++) {
            GetEntryLog latestLog = entries.get(i - 1);
            GetEntryLog oldLog = entries.get(i);

            long daysBetween = ChronoUnit.DAYS.between(latestLog.getDate(), oldLog.getDate());
            if (daysBetween == 1L) {
                streak++;
            } else if (daysBetween > 1L) {
                break;
            }
        }

        GetAllEntriesLog getAllEntriesLog = new GetAllEntriesLog();

        getAllEntriesLog.setEntryLogList(entries);
        getAllEntriesLog.setTotalRep(totalReps);
        getAllEntriesLog.setStreak(streak);
        getAllEntriesLog.setTodayTotalRep(todayTotalRep);

        long daysRemaining = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.of(2026, Month.DECEMBER, 31));
        long needPerDay = (10000 - totalReps ) / daysRemaining;
        needPerDay += (10000 - totalReps ) % daysRemaining != 0 ? 1 : 0;
        getAllEntriesLog.setNeedPerDay(needPerDay);

        return getAllEntriesLog;
    }

    private DBEntryLog findEntry(String id) throws ItemNotFoundException {
        DBEntryLog itemToBeFind = entryRepository.findById(id).orElse(null);

        if (itemToBeFind == null) {
            throw new ItemNotFoundException("Delete Entry ID's DB Entry Log");
        }

        return itemToBeFind;
    }

    public void deleteEntry(DeleteEntryLog deleteEntryLog) throws ItemNotFoundException {
        DBEntryLog entry = findEntry(deleteEntryLog.getId());

        entryRepository.deleteById(deleteEntryLog.getId());
    }

    public URI generateURI(GetEntryLog entryLog) {
        String location = "entry/" + entryLog.getId();

        return URI.create(location);
    }
}
