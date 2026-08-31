package com.vedant.kakade.push.up.challenges.repository;

import com.vedant.kakade.push.up.challenges.model.DBEntity.DBEntryLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<DBEntryLog, String> {

}
