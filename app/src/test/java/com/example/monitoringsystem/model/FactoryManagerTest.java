package com.example.monitoringsystem.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class FactoryManagerTest {

    String mail = "mail";
    String username = "user";
    String password = "pass";

    FactoryManager factoryManager = new FactoryManager(mail, username, password);

    @Test
    public void setEmail() {
        factoryManager.setEmail("MaIl");
        assertEquals("MaIl", factoryManager.getEmail());
    }

    @Test
    public void setUsername() {
        factoryManager.setUsername("User");
        assertEquals("User", factoryManager.getUsername());
    }

    @Test
    public void setPassword() {
        factoryManager.setPassword("PA$$");
        assertEquals("PA$$", factoryManager.getPassword());
    }

    @Test
    public void getEmail() {
        assertEquals("mail", factoryManager.getEmail());
    }

    @Test
    public void getUsername() {
        assertEquals("user", factoryManager.getUsername());
    }

    @Test
    public void getPassword() {
        assertEquals("pass", factoryManager.getPassword());
    }
}