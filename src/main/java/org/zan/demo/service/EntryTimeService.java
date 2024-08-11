package org.zan.demo.service;

import org.zan.demo.entity.EntryTime;

import java.util.Optional;

public interface EntryTimeService {
    Optional<EntryTime> getCurrentEffectiveDate();
}
