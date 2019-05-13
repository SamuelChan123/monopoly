Use cases: In another file, USE_CASES.md, write at least 24 use cases for your project that describe specific features you expect to complete. Focus on those features you plan to complete during the first two sprints (i.e., the next two weeks ? the project's highest priority features). Note, they can be similar to those given in the assignment specification, but should include more details based on your project's goals.

1. The user(s) will be able to select the number of players in a new Monopoly game- each ?player? will start on tile GO and have X amount of money in their bank account. 

2. The game is composed of rounds in which each player in their turn rolls two 6-sided dice and moves forward [on the game board] the sum of the numbers rolled.

3. When a player lands on a tile, the tile must register that the player is on the tile

When player needs to move to special tile due to instructions, there must be a way to identify the special tile so that player position can be changed accordingly

4. When a player lands on an unowned RealEstateTile, the player can choose to buy the property. In this case, the player?s funds will be reduced but the player will gain the tiles? property card

5. When a player lands on an owned RealEstateTile (but the player itself is not the owner), the player pays rent to the player who ones the property- funds will be adjusted on both sides. 

6. Rules: User should be able to modify rules (e.x. What to do when you land on a special space or rules for getting out of jail/dice roll situations)

User should be able to change these commonly modified rules (look at general variations in rules) via a properties file (this is in addition to what other things are included in the properties file such as board layout)

Different instruction files should be presented to new users (see following use case) depending on which game rules are being played

7. User has never played Monopoly before

Have instructions button on the Splash screen
When you land on Chance / Community cards, you have information in the information box that tells you how it works
Each property should have information about the price, building another house/other properties on the real estate, ownership (?), what property group it belongs to.

8. Player runs out of money
Option I: The player loses and retires from the game, can divy up the property among the other users or just reset the properties
If there are only 2 users, then the other user wins
Else, then the other players keep playing
Option 2: The player can sell is assets (un-mortgaged properties, buildings, get out of jail free cards)

9. Player lands on Parking square, collects all the money from fines/etc. that players paid due to chance/community cards
Create variable to track total collected fees and taxes from all players and take taxes/fees from players when they receive cards with such instructions or land on income tax/luxury tax spaces
Accumulated funds go to player who lands on Free Parking and the fee/tax balance returns to zero 

10. Player lands on Go to Jail square
If the player has a Get Out of Jail card, then they should be able to use it
Check the Player?s ArrayList
Else, they will be moved directly to the Jail square

11. Player owns all properties of a property color group
Give the option to build houses or hotels when it is your turn
You must build evenly, you have to build one house on every property of that group before you build two on one property
Pop-up window that explains prices and rent costs of additional property when player clicks on property they own that can be developed 
Create visuals to easily indicate which spaces on board contain which properties (preferably on main board) 
Make sure upgrade levels for property are correct (e.g. can only build hotel on top of four houses)

12. Trading Properties: a player can offer a trade to another user, if the recipient accepts, the two players exchange real estate properties (previous property removed from each player?s respective property arraylist, appends the new one)

13. If a player throws doubles (the same numbers on each die) then the player gets a second turn 

An extra button appears to ?reroll dice,? effectively ending their first turn to begin their second turn.

14. If player owns all properties of a group, the rent will double for other players landing on any of the properties

15. Selling a property with buildings already built on one or more of the properties

Must sell the buildings back to the bank before you sell the individual properties

16. Utilities: If a player lands on a utility that is owned by another player then they must roll a dice to determine how much they owe
If utility is not yet owned, player gets option to purchase utility
Start a dice roll when player lands on utility they do not own (but is in someone else?s utility card arraylist), the amount they owe is 4 times the roll if the utility owner does not own the other utility, otherwise it is 10 times the roll

17. Railroads: If a player lands on a railroad, they have to pay rent according to how many railroads the owner of the railroads owns (1- $25, 2- $50, 3- $100, 4- $200)

18. CPU player:  CPU player that makes moves based on a pre-determined set of algorithms/specifications must be made (contained in a class CPUController that encapsulates Model and View)

19. Possibly could implement different CPU strategies based on what difficulty player sets

20. ust be able to handle all situations that a human player would be presented with 

21. If a player has insufficient funds to pay a required fine/rent, then they have the option to either mortgage/sell existing properties and/or buildings (as well as assets such as get out of jail free card) to acquire the necessary fines - otherwise, they forfeit the game and are considered bankrupt, and effectively out of the game.

22. If a player lands on the Jail square in a regular turn based movement, the player?s sprite will be placed on the outside of the Jail square to indicate that the player is ?just visiting? and is not actually in ?Jail?

23. If a player completes one loop of the board, then they gets Y amount of money.

24. If a player does not like to buy a tile then the tile can be auctioned. Players will bid for the tile and a timer will keep track of the bidding. If a bidding holds for T time then the tile belongs to the player with the bid price.

25. After a player buys a tile, then the outer rim shall display the player?s color to highlight that it is his property.

A player can save the state of the game so that later on they can load that game and continue playing.
