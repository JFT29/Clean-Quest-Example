# Clean Quest Example

This project provides an example/case study that was deliberately written poorly by AI to showcase some software maintainability issues.

## Case Study

The case study is about a software component for managing quests (or missions) in an MMORPG game.
The previous developer left in a hurry, leaving behind a "Quest Manager" that is working but will be challenging to maintain in further development.
There are already some complaints from the admin who manages the quests provided to the player.
Some of the complaints are:

1.  Escort quests are paying out 10x more gold than intended due to a typo, breaking the in-game economy.
2.  Every time the admin wants to add a new quest type (like "DUNGEON_CRAWL"), the code breaks.
3.  Quest results are saved directly to a hardcoded text file, making it impossible to switch to a proper database later.

Your task is to improve this project using proper OO design principles, especially **SOLID Principles**.
You also need to improve the code quality by refactoring the current code.

## Your Task

1.  Run the tests: `QuestServiceTest` currently passes, but notice it *expects* the bug in the Escort quest (10000 gold instead of 1000).
2.  Identify Smells:
    - Unit Size: Large method in `QuestServiceImpl` handling both logic and I/O.
    - Complexity: Nested `if-else` chains for quest types (OCP violation).
    - Persistence: Hardcoded file writing inside the service layer.
    - DIP Violation: Service depends on concrete `FileWriter` instead of abstraction.
    - Controller SRP Violation: Controller performing business logic checks.
3.  Improve the codebase:
    - Update the test to reflect the correct requirement: Fixed `QuestServiceTest` to expect 1000 gold.
    - Fix the code: Corrected multiplier in `EscortQuestProcessor`.
    - Refactor: Applied Strategy (Processors) and Repository patterns to follow SOLID principles.

## Generative AI Use Disclosure

The codebase and instruction were drafted using Gemini 3 via `gemini` CLI command.
Then, it was reviewd and edited manually.