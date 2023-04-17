package com.notifyme.application.repository;

import com.notifyme.application.model.User;

import javax.sound.midi.MidiDeviceTransmitter;

public interface CustomUserRepository {
    void insertAll(Iterable<User> users);
}
