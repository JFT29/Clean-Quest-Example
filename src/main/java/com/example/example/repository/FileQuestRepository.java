package com.example.example.repository;

import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * File-based implementation of {@link QuestRepository}.
 * Saves quest completion logs to a text file.
 *
 * This extracts the persistence concern that was previously scattered
 * throughout the service's if-else branches.
 */
@Repository
public class FileQuestRepository implements QuestRepository {

    private static final Logger LOGGER = Logger.getLogger(FileQuestRepository.class.getName());
    private static final String LOG_FILE = "quest_log.txt";

    @Override
    public void saveQuestLog(String questName, int reward) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write("Quest: " + questName + " | Reward: " + reward);
            writer.newLine();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving quest log for: " + questName, e);
        }
    }
}
