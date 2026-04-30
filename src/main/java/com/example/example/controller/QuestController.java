package com.example.example.controller;

import com.example.example.model.Quest;
import com.example.example.service.QuestService;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

/**
 * REST controller for quest processing.
 *
 * Refactored to follow SRP: the controller only handles HTTP concerns
 * (request validation, response formatting). All business logic
 * is delegated to {@link QuestService}.
 */
@RestController
@RequestMapping("/api/quests")
public class QuestController {

    private static final Logger LOGGER = Logger.getLogger(QuestController.class.getName());

    private final QuestService questService;

    public QuestController(QuestService questService) {
        this.questService = questService;
    }

    @PostMapping("/process")
    public String process(@RequestBody Quest quest) {
        LOGGER.info("Received quest processing request for: " + quest.getName());

        if (quest.getType() == null || quest.getType().isEmpty()) {
            return "ERROR: Quest type is missing!";
        }

        int reward = questService.processQuest(quest);

        if (reward > 0) {
            return "SUCCESS: Reward is " + reward;
        } else {
            return "FAILED: Not done yet.";
        }
    }
}
