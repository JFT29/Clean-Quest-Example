package com.example.example.controller;

import com.example.example.model.Quest;
import com.example.example.service.QuestService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuestController.class)
class QuestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private QuestService questService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testProcessQuestSuccess() throws Exception {
        Quest quest = new Quest("Dragon Slayer", "KILL", 5, 100);

        when(questService.processQuest(any(Quest.class)))
            .thenReturn(500);

        mockMvc.perform(post("/api/quests/process")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(quest)))
                .andExpect(status().isOk())
                .andExpect(content().string("SUCCESS: Reward is 500"));
    }

    @Test
    void testProcessQuestFailure() throws Exception {
        Quest quest = new Quest("Find Cat", "FETCH", 1, 0);

        when(questService.processQuest(any(Quest.class)))
            .thenReturn(0);

        mockMvc.perform(post("/api/quests/process")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(quest)))
                .andExpect(status().isOk())
                .andExpect(content().string("FAILED: Not done yet."));
    }

    @Test
    void testProcessQuestMissingType() throws Exception {
        Quest quest = new Quest("No Type", null, 1, 0);

        mockMvc.perform(post("/api/quests/process")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(quest)))
                .andExpect(status().isOk())
                .andExpect(content().string("ERROR: Quest type is missing!"));
    }
}
