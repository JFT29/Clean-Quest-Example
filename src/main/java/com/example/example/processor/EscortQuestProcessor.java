package com.example.example.processor;

import com.example.example.model.Quest;
import org.springframework.stereotype.Component;

/**
 * Processor for ESCORT-type quests.
 * Completion requires progress >= 1 (e.g., escort Pak Burhan).
 * Reward is difficulty * 500 gold.
 *
 * BUG FIX: The original code used difficulty * 5000, which was a typo
 * causing Escort quests to pay 10x more gold than intended,
 * breaking the in-game economy.
 */
@Component
public class EscortQuestProcessor implements QuestProcessor {

    private static final String QUEST_TYPE = "ESCORT";
    private static final int COMPLETION_THRESHOLD = 1;
    private static final int REWARD_MULTIPLIER = 500;

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
