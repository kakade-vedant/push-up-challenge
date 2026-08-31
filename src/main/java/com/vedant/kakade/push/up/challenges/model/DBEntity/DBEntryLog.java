package com.vedant.kakade.push.up.challenges.model.DBEntity;

import com.vedant.kakade.push.up.challenges.exception.IdModificationException;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class DBEntryLog {
    @Id
    String id;

    LocalDate date;

    long count;

    public void setId(String id) throws IdModificationException{
        if (this.id == null) {
            this.id = id;
        } else {
            throw new IdModificationException("DBEntryLog");
        }
    }
}
