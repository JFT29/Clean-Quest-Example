package com.example.example.service;

import com.example.example.model.Quest;
import com.example.example.processor.EscortQuestProcessor;
import com.example.example.processor.FetchQuestProcessor;
import com.example.example.processor.KillQuestProcessor;
import com.example.example.processor.QuestProcessor;
import com.example.example.repository.QuestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestServiceTest {

    private QuestServiceImpl questService;
    private QuestRepository mockRepository;

    @BeforeEach
    void setUp() {
        mockRepository = mock(QuestRepository.class);
        List<QuestProcessor> processors = List.of(
                new KillQuestProcessor(),
                new FetchQuestProcessor(),
                new EscortQuestProcessor());
        questService = new QuestServiceImpl(processors, mockRepository);
    }

    @Test
    void testKillQuestCompleted() {
        // KILL quest: 100 gold per difficulty when progress >= 100
        Quest quest = new Quest("Slime Slayer", "KILL", 1, 100);
        assertEquals(100, questService.processQuest(quest));
        verify(mockRepository).saveQuestLog("Slime Slayer", 100);
    }

    @Test
    void testKillQuestNotCompleted() {
        // KILL quest: progress < 100 should return 0
        Quest quest = new Quest("Slime Slayer", "KILL", 1, 50);
        assertEquals(0, questService.processQuest(quest));
        verify(mockRepository, never()).saveQuestLog(anyString(), anyInt());
    }

    @Test
    void testFetchQuestCompleted() {
        // FETCH quest: 50 gold per difficulty when progress >= 10
        Quest quest = new Quest("Gather Herbs", "FETCH", 3, 10);
        assertEquals(150, questService.processQuest(quest));
        verify(mockRepository).saveQuestLog("Gather Herbs", 150);
    }

    @Test
    void testFetchQuestNotCompleted() {
        // FETCH quest: progress < 10 should return 0
        Quest quest = new Quest("Gather Herbs", "FETCH", 1, 5);
        assertEquals(0, questService.processQuest(quest));
        verify(mockRepository, never()).saveQuestLog(anyString(), anyInt());
    }

    @Test
    void testEscortQuestCompleted() {
        // BUG FIX: ESCORT quest should pay 500 gold per difficulty (not 5000).
        // For difficulty 2, the correct reward is 2 * 500 = 1000.
        Quest quest = new Quest("VIP Escort", "ESCORT", 2, 1);
        assertEquals(1000, questService.processQuest(quest));
        verify(mockRepository).saveQuestLog("VIP Escort", 1000);
    }

    @Test
    void testEscortQuestNotCompleted() {
        // ESCORT quest: progress < 1 should return 0
        Quest quest = new Quest("VIP Escort", "ESCORT", 2, 0);
        assertEquals(0, questService.processQuest(quest));
        verify(mockRepository, never()).saveQuestLog(anyString(), anyInt());
    }

    @Test
    void testUnknownQuestType() {
        // Unknown quest type should return 0
        Quest quest = new Quest("Mystery", "DUNGEON_CRAWL", 5, 100);
        assertEquals(0, questService.processQuest(quest));
        verify(mockRepository, never()).saveQuestLog(anyString(), anyInt());
    }

    @Test
    void testCalculateReward() {
        Quest quest = new Quest("Dragon Slayer", "KILL", 5, 100);
        assertEquals(500, questService.calculateReward(quest));
    }

    @Test
    void testIsCompleted() {
        Quest completedQuest = new Quest("Dragon Slayer", "KILL", 5, 100);
        Quest incompleteQuest = new Quest("Dragon Slayer", "KILL", 5, 50);
        assertTrue(questService.isCompleted(completedQuest));
        assertFalse(questService.isCompleted(incompleteQuest));
    }
}
