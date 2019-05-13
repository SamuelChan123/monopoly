To represent your design at a reasonably high level and provide an organization to the plan document, break it into APIs rather than all the classes you can think of. In everything, focus on how to build a variety of games. Explain the key features in the concept that can vary, how you will support those features, and how you expect new variations to be written (i.e., what needs to be sub-classed, what accepts lambdas, what goes into resource files, and how communication will occur between your separate programs).

Note, the API agreed to between the sub-groups should not be changed after this document because it affects how the other's part might work. If it must be changed, the change and its reasons must be clearly described in a separate document, doc/API_CHANGES.md, included in the team's Gitlab repository.

Structure the plan document in the following sections:

#Introduction

This section describes the problem your team is trying to solve by writing this program, the primary design goals of the project (i.e., where is it most flexible), and the primary architecture of the design (i.e., what is closed and what is open). Discuss the design at a high-level (i.e., without referencing specific classes, data structures, or code).

###Problem Statement:

Our team is tackling the task of implementing the board game Monopoly with JavaFX. At a minimum, we are expecting to create a program that allows supporting multiple players (one of which can be a CPU) on the same computer to play a basic game of Monopoly, in which the official rules are enforced. There will be a win/lose condition (based on which type of game?s rules are implemented), probably related to bankruptcy of all other players.

###Primary Design Goals/Architecture:

We wanted our design to be as flexible as possible, so that adding new features would not require much modification to existing code; instead, new rules/objects would be created as subclasses to existing abstract classes or extend existing concrete frameworks. Thus, we have a lot of abstraction and polymorphism to make this possible and allow extensibility in both model and view. We tried to adhere to the Open/Closed principle: open for extension but closed for modification; while we know that we may have to modify our public methods (hence, our API) during the actual sprints, we hope to stick as close as possible to the design we created to ensure uniformity, flexibility, and mvc separation.

###High-Level Design:

The model will manage the backend data, including loading/parsing config files, and the classes that will store data relevant to each object on the Monopoly game board, such as player information, board/tile information, etc. This data will be passed to the front end and ultimately displayed in the view via the controller, which will contain the engine that creates instances of each object in the model and places them together, either by using an animation or some turn-based function to determine how to update each iteration of the game.

Finally, the view will handle things such as displaying the type of board, the tiles on the board, which pieces will appear and when, styling and dimensions, etc.


#Overview

This section serves as a map of your design for other programmers to gain a general understanding of how and why the program was divided up, and how the individual parts work together to provide the desired functionality. Describe specific modules you intend to create, their purpose with regards to the program's functionality, and how they collaborate with each other, focusing specifically on each one's API. Include a picture of how the modules are related (these pictures can be hand drawn and scanned in, created with a standard drawing program, or screen shots from a UML design program). Discuss specific classes, methods, and data structures, but not individual lines of code.

Main Ideas
Our program was divided up into model, view and controller packages in order to separate the back-end development from the front-end
Controller keeps track of what events have taken place (button clicks, Player moved, upload files) and updates the Model and View classes accordingly

#Model Features

###Board Set-Up

The information of which tiles go where will be given in the configuration file
Create an instance of the configuration file in the Board class, then extract information
Board holds the tiles
Controller class will check what tile the Player is on and call the appropriate methods
Depending on the type of tile you land on and location of your position, you can search the tile map in order to see what functionalities each tile has
Each tile type differs only in instance variables and all share a performAction() method that will alter the Player?s funds and status accordingly
Not sure yet how the Tiles will interact with the Player, are we passing through the Player?
If Card is appropriate, tile holds Card
If there requires an object that needs to be appended to an ArrayList or recorded in some way, then there is a Card associated with the tile
Card / non-Card Objects in the Tiles

###PropertyCard: 

all of the the Cards that require possession and collect rent, distinction between sub-classes is calculation of rent

Separated from Chance Cards because you need to keep all of the information about which Properties / Property groups you own in one place and append to Player?s propertyMap
Classes in PropertyCard have a buying and selling price predetermined in the configuration file
calculateRent() 
Regular and Railroad: We can check to see how many houses / hotels are owned by searching Map and using if statements
Utilities: Check dice number by using rollDice() method
Perk usually changes the state of the Player (except for Jail) and distinction is how it changes the Player
When a player lands on a PerkTile, depending on the Perk, it can return a value to the Player and the Player can update funds / move respectively
Other option is to pass through Player into the Tile and then alter the Players attributes
Player

###PropertiesMap

Easy to index see which Cards the Player has and how many of each card the Player has, particularly when you possess all of one color group (by using map.getValues() or map.containsKey())
When another Player lands on Property, we can check if other Players own that Property easily with the Map

###PerkList
To store GetOutOfJail cards and use them when necessary
Configuration Reader

Information given in each file
Property options:
Group number
Rent prices depending on how many houses are built
Build cost
Selling cose
Mortgage

Tiles:
Tile number, type, property group, group number

When Player uploads new file:
Controller will call model: configuration reader and parse data and then return that data
Create new PropertyCards and then add to unsoldPropertyMap
Create new ChanceCards
Create new Board of appropriate Grid size
Create new Tiles
Have Tiles hold PropertyCards, ChanceCards, 
Controller calls view: update the Board
Fill in BoardView with appropriate Properties and their information
Each TileView will display information about rent, name, etc.

###Controller

Main/Engine

Player

-When a Player lands on a specific Property

Call Model to: check if property is in unsoldPropertyMap
Else, check other Player?s propertyMaps and see how many hotels / houses are built

Call View to: update the Player?s ImageView to proper Tile
When a player clicks a certain button (roll dice, end turn, buy property), the Controller will update the View and Model appropriatel
When player draws a go to Jail card

Call Model: check if the Player has anything in ChanceCardList
If not, update Player location to the Jail tile location

Call View: If Player has get out of Jail card, then display button to use the card
If not, update PlayerView to the got to Jail location

###Dice
When ?Roll Dice? button clicked

Call Model to: Generate random values in the Dice class (rollDice() which returns an ArrayList)
Depending on the outcome of the Dice, choose to re-run the round

Call View to: Update the Dice ImageView
Buy Houses/Hotels
When player lands on own property

Call Model to: Check whether the Player has the option to buy houses/hotels
If yes, then call View to: present Button to buy
When Button clicked to buy

Call Model to: Check which hotel/house options there are

Call View to: Present options of houses/hotels, rent costs

When Property is bought

Call Model to: Update PropertyMap

Call View to: Add a house (colored square) to the Tile that the Player is on (using Player?s location)

View

Tiles

When a Player buys a property, the Controller will call to update the outline color of the tile to correspond to the Player

When a Player's location changes, update the View to put the Player ImageView onto the proper Tile

When a Player lands on a Property, depending on the condition of that Property (bought by another Player), give Button options to buy, build houses, etc.

![UML Diagram](https://drive.google.com/file/d/16cPl7SsTHgNgywhHGHmbcGJ_kPck17mH/view?usp=sharing)

#User Interface

This section describes what components the user will interact with in your program (keep it simple to start) and how a game is represented to the designer and what support is provided to make it easy to create a game. Include design goals for the implementation of the UI that shows the kind of abstractions you plan to build on top of OpenJFX's standard components and how they can be flexibly exchanged within the GUI itself. Finally, describe any erroneous situations that are reported to the user (i.e., bad input data, empty data, etc.).

#UI interactions

###Splash Screen
The first scene of the application. The user can click PLAY to create a new game or INSTRUCTIONS to pull up the instruction manual for the game.
New Game Screen + Player configurations
Load File: User will load a PropertiesSetup.properties from the local directory to set up the configurations for a new game. The property files will all be pre set up by the designer to make it easy for the user to load the file- however, the game will be slightly less customizable.
Number of Players: User will select the number of players for the new game (under numerical constraints). The next window will prompt the user to enter the names and colors/images of the players. User will be able to designate part of the player list as CPU players.
Future ideas: allow the user to load a file/game that they previously saved
###Game Screen
The main screen for gameplay
Player chips (identified by the chosen color/image) all start on the GO tile. During each round, each player will be able to click Roll Dice to roll the dice - the player chip will automatically move forward on the board the sum rolled.
Depending on the tile that the player landed on, the player will be promted to Buy, Pay Rent, or draw a Chance or Community Chest Card. The buttons are only clickable when the action is valid as per the game.
The player cannot click Next Turn for the next player?s turn before the player completes his/her requirements.
The monopoly man will give hints and prompts in the quote box to guide the players along.
On the ?table,? the user will be able to toggle between the players (buttons) to see each respective player?s stats in the white info box. User can see the player?s current balance and the properties they own. The properties on the list will be clickable and open a pop up that allows the player to trade/sell the properties. Clicking on the property will also display a property information card.
The Property Ownership board is there to give the user an easy visual of the distribution of property ownership between the players (color coded)
Clicking the Home will take the player to the splash screen
Clicking the Settings icon will pop up a settings menu where the user can set the sound volume and disable/enable the monopoly man?s quote box.

#Design goals for the UI

View package
Contains all the JavaFX components for the application
###Monopolyview (Main view class)

###Boardview (Abstract class)
Provides the display for the monopoly game board; the class is abstract so that designer can easily extend the Boardview class to incorporate circular boards, 3D boards and more. 
Will fill each grid cell with a Tileview
###Tileview (Abstract)
Provides the display for the game tiles; the Tileview can be easily extended with subclasses for different looks of the tile (PropertyTileview, JailTileview, PerkTile view etc)
Sprite (Superclass)- tentative design
Superclass for all interactive game image entities (Playerview, Diceview, etc). The class will have an ImageView field variable, x and y location variables, and getters and setters for the dimensions of the image element. 
###Playerview
Provides the player chip display-  configurable from the GUI (user will be able to select an image/color for the player chip)
###Diceview
Class for the dice- will have a method that toggles through images of the dice to create a dice rolling ?animation?
###GameScene (interface)
Must be implemented by all the different scenes of the application

#Controller package
Sets the action of the buttons and View elements when pressed by the player
Served to transfer information from the Model to the View for display 
Erroneous situations
Bad file selection (for Load File)
The back end model will always check the validity of the file selected by the user. 
If the file is not .properties type, a pop up will alert the player to please choose a .properties file. 
If the .properties file is invalid/not a setup-game type, a message will also be returned to advise the user to select a different. 
If a set-up .properties file is corrupted/tampered with, the user will also need to choose a different file.


#Design Details 

This section describes each module introduced in the Overview in detail (as well as any other sub-modules that may be needed but are not significant to include in a high-level description of the program). Describe how each module handles specific features given in the assignment specification, what resources it might use, how it collaborates with other modules, and how each could be extended to include additional requirements (from the assignment specification or discussed by your team). Note, each sub-team should have its own API(s). Finally, justify the decision to create each module with respect to the design's key goals, principles, and abstractions.


##Model Features
###Board Set-Up

A configuration file will contain information that corresponds to values of each tile instance variable within the board object (type of tile, position of tile, properties held by the tile,  actions performed when a player lands on a tile)
Board is a HashMap<Integer : Tile> that indicates which tile is at which position in the layout
Create an instance of the configuration file in the Board class, then extract information from it through a readConfigurationFile method in the model?s ConfigurationReader class that uses Scanner within the class that populates the board HashMaps and assigns a positional integer value to the correct tile in accordance to the configuration file (this would be made easier if tile information is already presented in order of which integer they appear in in the HashMap of the Board, and tile information itself contains values for  Tile instance variables in a consistent manner)
Board will hold the tiles
Controller class will checks what tile the Player is on and call the appropriate methods, such as getPosition() form the Player class
Depending on the type of tile you land on and location of your position, you can search the tile map in order to see what functionalities each tile has, each tile will undergo a different set of rules for when a player lands on it
Each tile type differs only in instance variables and all share a performAction() method (implemented differently, performAction() is an abstract method in the Tiles abstract class) that will alter the Player?s funds, location, and status accordingly.
The current player which is tracked by the controller then that will be passed as an argument to the performAction() method.
If Card is appropriate, tile holds Card
If cards are associated with a tile, cards will be removed from the Bank ArrayList unsoldProperties and the tile-specific ArrayList cardsHeld associated with that tile and added to the corresponding ArrayList/Map of cards owned by the player (perkList or propertiesMap), this will all be coded into method performAction()
If tiles are associated with the same group of cards (such as  ChanceCardTile and CommunityCardTile they will pull cards from the same ArrayList cardsHeld so that there are no duplicates pulled from the same deck, this should be solved by initializing cardsHeld for each tile to point to the same ArrayList of ChanceCardTile/CommunityCardTile
Card / non-Card Objects in the Tiles

###PropertyCard: Extends Card type

An object type to add to Player?s propertyMap int sellingPrice (normally a ratio), String name, int buyingPrice and Int propertyGroup.
calculateRent(Player player) calculates the rent based on player?s other information.
Perk usually changes the state of the Player (except for Jail) and distinction is how it changes the Player
Based on subclass updates player?s parameters. Can pass player through updatePlayer().
Player

###PropertiesMap

Easy to index see which Cards the Player has and how many of each card the Player has, particularly when you possess all of one color group (by using map.getValues() or map.containsKey()), the key will be the color and the map will be the the cards corresponding to the color
The situation when a player controls all cards of a certain color will be known by comparing the number of Tiles with that color (represented by the variable tileColor) in Tile to the length of the ArrayList  containing the tiles of that color owned by the player
When another Player lands on Property, we can check if other Players own that Property easily with the Map, as we can call getTileColor() on that PropertyTile object and then search the propertiesMap of each player for the value associated with the key of that tileColor

###PerkList
Will just be an ArrayList containing cards that are of the Jail subclass (under the abstract class ChanceCard), which will be ?get out of jail free cards,? such cards will be stored in perkList until they are used or traded, in which the remove() method will be called on perkList and the card taken out
Configuration Reader

##Information given in each file
###Property options:
Group number 

Will be attained through the PropertiesLayout.csv file, as the group number will be one of the values read in through the readConfigurationFile method
Rent prices depending on how many houses are built
Information will be passed through the PropertiesCards.properties file which will create the cards corresponding to each property, in this case there will be the corresponding rent prices, which will then be set as the rent integer variables (e.g. rent1house, rent2house, ? rent1hotel)
Those values will then be run through the method calculateRent() which should be the same for every property tile, and then the rent prices will be returned and the player will lose that amount of money from instance variable myBalance and added to the owner?s myBalance variable

Build cost

Build cost is represented by the integer variable buyingPrice and will be set via the PropertiesCards.properties file when the readConfigurationFile method is run

Selling cost

Selling cost is represented by the integer variable sellingPrice and will be calculated as a ratio/proportion of the original buying price via the method getSellingPrice (house rules state one-half original price)

Mortgage

Mortgage is represented by the integer variable mortgage and will be set via the PropertiesCards.properties file when the readConfigurationFile method is run
###Tiles:
Tile number, type, property group, group number

All of this information will be gathered when the PropertiesLayout.csv file is read so the values can be used to populate these attributes for the Tile object and then the attributes of the tile and the proper PropertiesCard can be matched in order for the correct actions to be performed upon landing on the tile 

When Player uploads new file:

Controller will call model: configurationReader and parse data and then return that data
Create new PropertyCards and then add to unsoldPropertyMap as well as cardsHeld for every PropertyTile
Create new ChanceCards and then add to a deck of cards represented by the ArrayList<Card>, used by both the ChanceCardTiles
Create new Board of appropriate Grid size
PropertyCards are created in configurationReader and then are added in order to the HashMap describing board through the setBoard() method
Create new Tiles
New Tiles are created in the Board class as tile info is read-in from the configurationReader, will most likely be in helper method within setBoard() called makeTile()
Have Tiles hold PropertyCards, ChanceCards
PropertyCards and ChanceCards will be stored in the cardsHeld ArrayList within each corresponding Tile object
Controller calls view: update the Board
Fill in BoardView with appropriate Properties and their information
Each TileView will display information about rent, name, etc.

##Controller
###Main/Engine
Player
When a Player lands on a specific Property
Call Model to: we can call Player getPropertyMap() to check if player owns. If true then see how many hotels / houses are built and call addtoPerks() based on player?s choice.
Call View to: update the Player?s ImageView to proper Tile
When a player clicks a certain button (roll dice, end turn, buy property), the Controller will update the View and Model appropriately by calling different methods in Player for example: setBalance(), setPosition().
When player draws a go to Jail card
Call Model: check if the Player has anything in ChanceCardList
If not, update Player location to the Jail tile location by calling updatePosition() method on Player.
Call View: If Player has get out of Jail card, then display button to use the card
If not, update PlayerView to the got to Jail location
###Dice
When ?Roll Dice? button clicked
Call Model to: Generate random values in the Dice class
Store the rolled values in an ArrayList which we get from rollDice() method
Depending on the outcome of the Dice, choose to re-run the round and call rollDice() again
Call View to: Update the Dice ImageView including pop-up showing that we are re-rolling (if rule allows).
###Buy Houses/Hotels
####When player lands on own property
Call Model to: Check whether the Player has the option to buy houses/hotels using getBalance() method or a for loop calling all other Player?s getPropertyMap() method to look for their properties and see if already bought.
If yes, then call View to: present Button to buy
When Button clicked to buy
Call Model to: Check which hotel/house options there are. 
Call View to: Present options of houses/hotels, rent costs
####When Property is bought
Call Model to: Update PropertyMap. This can be achieved by passing the property object associated to the tile to the player?s addProperty() method.
Call View to: Add a house (colored square) to the Tile that the Player is on (using Player?s location)
View
###Tiles
When a Player buys a property, the Controller will call to update the outline color of the tile to correspond to the Player
When a Player?s location changes, update the View to put the Player ImageView onto the proper Tile
When a Player lands on a Property, depending on the condition of that Property (bought by another Player), give Button options to buy, build houses, etc.


#Example games

Describe three example games in detail that differ significantly. Clearly identify how the functional differences in these games is supported by your design. Use these examples to help clarify the abstractions in your design.

###Monopoly Millionaire

Square Board

Instead of having to bankrupt opponents, win condition is to reach 1 million dollars (thus, bankruptcy is still present in the game, but is not needed to win)
The functionality for this game can be fully supported by our game. There is probably not a whole lot of difference needed in the View or the Model, but the bulk of the rule changes will be in Controller, as the engine of the game will probably be there to determine when the win condition is achieved, and the end/win screen is displayed

###Monopoly Junior

Square Board

In this implementation of the game, there are less squares, no utilities, and 4 chance cards. More importantly, if you land on a property square, you must buy it (you can?t refuse/auction it off), this affects both the player and the CPU as it disallows other actions if one lands on the property square (shouldn?t affect model too much)

###Monopoly Star Wars

Circular board (easy to implement, merely View specific functionality)


Assigned to one of two teams (either random or deterministic assignment), pay less rent (half) if the property is owned by someone of the same team

Team could be a private instance variable stored by Player

Hyperdrive square that allows one to move to any one of many places between the current hyperdrive square and the next one (can be a simple for loop through the tiles to get the index of the next hyperdrive tile in the engine/rules)

Game ends not on bankruptcy but when all properties are bought (whoever has the most of one kind of a team?s bases wins)

This is specified more in the engine/controller than in the model

Model should not be too affected except for storing the team, perhaps rent for members of opposite team vs same team.

No trading of properties (affects controller)


#Design Considerations 

This section describes any issues which need to be addressed or resolved before attempting to devise a complete design solution. Include any design decisions that each sub-team discussed at length (include pros and cons from all sides of the discussion) as well as any ambiguities, assumptions, or dependencies regarding the program that impact the overall design.

MVC and the use of the Controller:

Maintaining a Model View Controller (MVC) design pattern that supports architectural separation of back-end, front-end, and mediator elements.
Deciding what code and what elements to delegate to the Controller element.

Design ideas:

Using the Controller to manage Player activity such as key/button-input and also manage CPU activity such as decisions and relevant if/conditional statements
Using the Controller to set up View elements based on input data from Model counterparts
Using the Controller to also manage the animation and timeline
Major data structures to organize data
Choosing a easily maintainable and accessible data structure to organize game data such as property, ownership, and board layout information.

Design ideas:

Organize board by assigning tiles with integers ranging from 1 to N for an N-tile board.
Map<Integer, Tile> to assign each tile of the board with a Tile object that indicates the tile?s nature (property, chance/cc, go... etc) and contains methods regarding special functions (ex. acquire 200 for passing go).
Map<Integer, List<PropertyCard>> to keep track of property ownership where Integer keys represent the ?propertygroup? number of the group the property belongs to (1-8 for a standard 40-tile board) and a List of the PropertyCard objects representing the owned properties of said ?propertygroup.?
PropertyCards are read in initially to the Bank with all relevant information, which will hold a List<PropertyCard> of unsold properties that will be transferred to players layer.
List<PerkCard> of ?perk? cards that represent Chance/CC cards and potential powerups (such as get-out-of-jail-free pass) 

Potential problems:

The numbered system of tiles can pose issues when extended to games such as Triopoly where there are multiple ?systems? of tiles that are not simply linear
This design implies that configuration files must be in-depth and contain significant structural information
Properties of Board/Tiles
Deciding what information would be appropriate and most accessible to be contained in certain classes.

Design ideas:

Property/PerkCards do not contain information about the owner, rather, the Player contains relevant Lists/Map to contain Card objects.
There would be a for-loop system needed to search through all Player?s cards to find out who owns what (in addition to the Bank if applicable), unless the Tile contained information regarding owner.
Tiles are of property-type contain information regarding owner
This can be used to display a colored tile-outline that can be used to visually indicate who owns the tile (and who will rent be paid to).
Tiles contain methods regarding action methods of the respective tile type (ex. Passing go and collecting 200, free parking attributes)

Existence of a Bank Class:

Bank class can be used to contain PropertyCard objects that aren?t sold/owned by anyone.
This can make sure all relevant information regarding the properties are already read in and accessible to either the player or the viewer (ex. Information on propertycard pops up when a player can potentially purchase it)
Bank class can also potentially contain a limit regarding the available money in the economy (usable for certain/variations of game modes - similar to actual board game). Could also contain other relevant information regarding the ?economy? of the game.
Setup of configuration files
Organize data and all necessary configuration data into initial configuration files - need to decide how to label/organize/distinguish certain elements from others (especially properties) as well as information regarding board layout/setup

Design ideas:

See sample files under data/GameFiles/...
Properties can be grouped by ?propertygroup? integers (ex. Ranges 1-8 for a standard 40-block setup) that represent the adjacent, colored groups of properties. Properties will also be subgrouped into integers 1-4 called ?groupnums? (representing the order of which they appear on the board in their respective ?propertygroups?)
These can be organized into XML files that are read-in and processed into PropertyCard objects that are contained by the Bank
This poses potential issues regarding special properties such as railroads and utilities and how to organize groups and subgroups
CSV file contains layout data regarding set-up of board with integers 1-40 representing the standard 40-tile layout and information regarding tile-type (property, go, cc/chance... etc) and relevant ?propertygroup? and ?groupnum? information as well as name information.
This implies a significant chunks of hard coded information that will be interchangeable but limited to the amount of hardcoded variations.
