# CS102-Card-Game
Number of Players: 2
Description:
- game starts with 10 cards dealt to the cardpool, and 4 cards to each player
- players take turns forming captures to score points
- first player to accumulate a total score greater than the winning score wins

Rules:
- To form a valid capture, players can only select exactly one handcard, and up to two cards from the cardpool
- Players can choose to discard one card from their hand to obtain a new card from the deck,
  but they pass their turn in the process
- Scoring system: number of cards captured * multiplier 

Types of captures:
    1. Pair: one handcard, one poolcard, both same rank (multiplier = 1.0)
    2. Triple: one handcard, two poolcards, all same rank (multiplier = 1.5)
    3. Combo: one handcard, two poolcards, sum of rank value of poolcards equals rank value of handcard (multiplier 1.3)
    4. Straight: one handcard, two poolcards, rank value in sequence (multiplier = 1.8)
    5. Run: Straight but suit of all cards are the same (multiplier = 2.0)