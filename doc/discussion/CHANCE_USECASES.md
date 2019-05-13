
* A player lands on Chance, draws "Bank pays you a dividend of $50", your funds are updated appropriately, and the card is returned to the bottom of the deck.
    * ChangeFunds subclass calls a performanAction() method that passes in a Player object and calls a public method on the player object to increase funds (increaseFunds(50))
* A player lands on Chance, draws "Advance to Go, collect $200", your token is moved and your funds are updated appropriately, and the card is returned to the bottom of the deck.
    * MovePlayer subclass calls a performAction() method that passes in a Player object and calls a public method on the player object to move the position to position Go (specified by index). It shall also call a public method to increase funds by 50.
* A player lands on Chance, draws "Get Out of Jail Free", and it is saved with your inventory, and the card is returned to the bottom of the deck.
    * JailTicket subclass calls a performanAction() method that passes in a Player object and calls a public method on the player object to add a “perk” card to the Player’s <List of perks>
* A player lands on Chance, draws "Go to Jail, Go directly to Jail", your token is moved (passing Go, but not collecting any money) and placed in jail, and the card is returned to the bottom of the deck.
    * MovePlayerPlace subclass calls a performAction() method that passes in a Player object and calls a public method on the player to move it to the specified location, Jail.

