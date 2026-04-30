package com.example.example.processor;

import com.example.example.model.Quest;

/**
 * Strategy interface for processing quest-specific logic.
 * Each quest type (KILL, FETCH, ESCORT, etc.) should have its own implementation.
 * This follows the Open-Closed Principle: adding a new quest type only requires
 * a new implementation, without modifying existing code.
 */
public interface QuestProcessor {

    /**
     * Returns true if this processor handles the given quest type.
     */
    boolean supports(String type);

    /**
     * Calculates the reward for a completed quest.
     */
    int calculateReward(Quest quest);

    /**
     * Checks whether the quest's progress meets the completion threshold.
     */
    boolean isCompleted(Quest quest);
}
