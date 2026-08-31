package com.vedant.kakade.push.up.challenges.model.response;

import com.vedant.kakade.push.up.challenges.exception.IdModificationException;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GetEntryLog {
    String id;

    LocalDate date;

    long count;

    public void setId(String id) throws IdModificationException {
        if (this.id == null) {
            this.id = id;
        } else {
            throw new IdModificationException("GetEntryLog");
        }
    }
}
