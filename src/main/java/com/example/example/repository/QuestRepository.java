package com.example.example.repository;

/**
 * Abstraction for persisting quest completion logs.
 * This follows the Dependency Inversion Principle: the service depends on this
 * abstraction rather than a concrete file writer, making it easy to swap
 * implementations (e.g., file, database, cloud storage).
 */
public interface QuestRepository {

    /**
     * Saves a quest completion log entry.
     *
     * @param questName the name of the completed quest
     * @param reward    the reward that was earned
     */
    void saveQuestLog(String questName, int reward);
}
