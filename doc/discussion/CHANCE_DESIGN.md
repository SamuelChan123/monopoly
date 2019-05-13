There must be a class made in the Model package called Card that is an abstract base class. 
It can then be extended by classes that determine the algorithms behind the Chance or Community Chest space 
called ChanceCard or CommunityChestCard, for example. In this case, ChanceCard and CommunityChestCard will have 
methods that create a brand new instance of a Chance/Community Chest object (within a Chance/Community Chest card 
class) whenever a game piece lands on a Chance/Community Chest space. When a new Chance/Community Chest card object 
is created, the “deck” (really just an indexed data structure holding the cards) pulls out a random card and 
performs the action specified by each card’s unique “action” method (which is an abstract method in Card)that 
affect other classes such as Player (which handles the status of each player in the game e.g. money held, 
properties owned, and their location). In order to determine the Chance/Community Chest cards included in a 
game, we can read a CSV file containing information such as the name of the card as well as containing text 
that determines the variables of the player affected by the card.  

* Configuration would contain two groups (separated by two Headers?) of Chance/Community cards (contain Title of Card, Type of Card method, and any necessary integers/variables for specific values (funds, location... etc))
* Methods could be either in a controller class or in the Abstract Card classes (available to all Card subclasses and only call necessary ones)

* Abstract Card Class
  * public Card(Player player)
  * protected Player getPlayer();
  * Abstract void performAction();
  * ChangeFunds (extends Card) - passes in positive/negative integer (funds)
* MovePlayerNumber (extends Card) - passes in positive/negative integer (position-change)
* MovePlayerPlace (extends Card) - passes in integer (specific location)
* ChangeFunds&Move (extends Card) - passes in two integers (funds/position-change)
* JailTicket (extends Card) - adds a “perk” to a Player’s <list of cards>

