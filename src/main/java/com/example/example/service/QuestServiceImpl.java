package com.example.example.service;

import com.example.example.model.Quest;
import com.example.example.processor.QuestProcessor;
import com.example.example.repository.QuestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Refactored implementation of {@link QuestService}.
 *
 * Instead of a monolithic if-else chain handling all quest types,
 * this service delegates to the appropriate {@link QuestProcessor}
 * via the Strategy pattern, and persists results through
 * the {@link QuestRepository} abstraction.
 *
 * This design follows:
 * - SRP: The service only coordinates; it doesn't calculate rewards or write files.
 * - OCP: New quest types are added by creating a new QuestProcessor, not modifying this class.
 * - DIP: Depends on abstractions (QuestProcessor, QuestRepository), not concrete implementations.
 */
@Service
public class QuestServiceImpl implements QuestService {

    private final List<QuestProcessor> processors;
    private final QuestRepository questRepository;

    public QuestServiceImpl(List<QuestProcessor> processors, QuestRepository questRepository) {
        this.processors = processors;
        this.questRepository = questRepository;
    }

    @Override
    public int processQuest(Quest quest) {
        QuestProcessor processor = findProcessor(quest.getType());

        if (processor != null && processor.isCompleted(quest)) {
            int reward = processor.calculateReward(quest);
            questRepository.saveQuestLog(quest.getName(), reward);
            return reward;
        }

        return 0;
    }

    @Override
    public int calculateReward(Quest quest) {
        QuestProcessor processor = findProcessor(quest.getType());
        if (processor != null) {
            return processor.calculateReward(quest);
        }
        return 0;
    }

    @Override
    public boolean isCompleted(Quest quest) {
        QuestProcessor processor = findProcessor(quest.getType());
        if (processor != null) {
            return processor.isCompleted(quest);
        }
        return false;
    }

    private QuestProcessor findProcessor(String type) {
        return processors.stream()
                .filter(p -> p.supports(type))
                .findFirst()
                .orElse(null);
    }
}
