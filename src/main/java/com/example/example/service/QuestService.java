package com.example.example.service;

import com.example.example.model.Quest;

/**
 * Service interface for quest processing.
 * Provides a clean API that accepts a Quest object directly,
 * following proper OO design instead of passing primitive parameters.
 */
public interface QuestService {

    /**
     * Processes a quest: checks completion, calculates reward, and logs the result.
     *
     * @param quest the quest to process
     * @return the reward earned, or 0 if the quest is not completed
     */
    int processQuest(Quest quest);

    /**
     * Calculates the reward for a given quest without side effects.
     *
     * @param quest the quest to calculate the reward for
     * @return the calculated reward
     */
    int calculateReward(Quest quest);

    /**
     * Checks whether a quest meets its completion criteria.
     *
     * @param quest the quest to check
     * @return true if the quest is completed
     */
    boolean isCompleted(Quest quest);
}
