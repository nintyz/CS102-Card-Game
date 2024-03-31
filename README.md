# CS102-Card-Game
Number of Players: 2
Description:
- Game starts with 8 cards dealt to the Cardpool, and 4 cards to each player
- Players take turns forming captures to score points
- First player to accumulate a total score greater than the winning score wins
- Winning Score can be configured in the app.config file

Rules:
- To form a valid Capture, players can only select exactly one Handcard, and up to two cards from the Cardpool
- Players can choose to discard one card from their hand to obtain a new card from the deck,
  but they skip their turn in the process
- Scoring system: number of cards captured * multiplier 

Types of Captures:
    1. Pair: one Handcard, one Poolcard, both same rank (multiplier = 1.0)
    2. Combo: one Handcard, two Poolcards, sum of rank value of Poolcards equals rank value of Handcard (multiplier = 1.3)
    3. Triple: one Handcard, two Poolcards, all same rank (multiplier = 1.5)
    4. Straight: one Handcard, two Poolcards, rank value in sequence (multiplier = 1.8)
    5. Run: Straight but suit of all cards are the same (multiplier = 2.0)