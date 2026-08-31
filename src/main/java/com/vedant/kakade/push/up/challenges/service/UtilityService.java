package com.vedant.kakade.push.up.challenges.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilityService {
    public UUID generateUUID() {
        return UUID.randomUUID();
    }
}
