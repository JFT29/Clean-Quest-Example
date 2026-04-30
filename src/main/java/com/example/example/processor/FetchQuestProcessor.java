package com.example.example.processor;

import com.example.example.model.Quest;
import org.springframework.stereotype.Component;

/**
 * Processor for FETCH-type quests.
 * Completion requires progress >= 10 (e.g., gather 10 palm oils).
 * Reward is difficulty * 50 gold.
 */
@Component
public class FetchQuestProcessor implements QuestProcessor {

    private static final String QUEST_TYPE = "FETCH";
    private static final int COMPLETION_THRESHOLD = 10;
    private static final int REWARD_MULTIPLIER = 50;

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
