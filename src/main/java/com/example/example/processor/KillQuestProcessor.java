package com.example.example.processor;

import com.example.example.model.Quest;
import org.springframework.stereotype.Component;

/**
 * Processor for KILL-type quests.
 * Completion requires progress >= 100 (e.g., kill 100 goblins).
 * Reward is difficulty * 100 gold.
 */
@Component
public class KillQuestProcessor implements QuestProcessor {

    private static final String QUEST_TYPE = "KILL";
    private static final int COMPLETION_THRESHOLD = 100;
    private static final int REWARD_MULTIPLIER = 100;

    @Override
    public boolean supports(String type) {
        return QUEST_TYPE.equals(type);
    }

    @Override
    public int calculateReward(Quest quest) {
        return quest.getDifficulty() * REWARD_MULTIPLIER;
    }

    @Override
    public boolean isCompleted(Quest quest) {
        return quest.getProgress() >= COMPLETION_THRESHOLD;
    }
}
