Monopoly (and its Variations)
====

#### Authors
- Sam Chan
- Jen Li
- Kiori Tanaka
- Caleb Chiang
- Eugene Wang
- Amik Mandal


#### Project Details
- Date Started: 4/1/19 (Start of plan)
- Date Finished: 4/27/19 (End of sprint 3)
- Hours Spent: 20+ hours, per person, per week (approximately 3.5 weeks)


#### Roles
- Each person's role in developing the project:
- Model:
    - Caleb Chiang: Implementing MVC design (writing and refactoring across all Controller classes and ModelEngine classes, hierarchy/interactions), button actions, CPU, PropertyCard, PropertyConfig, EventLog, AudioEffects, CheatDialogs, set up configuration/setup files
    - Eugene Wang: Tile package in Model (and config files), PerkCard package in Model (and config files), Board class, Backend tests, Auction/Trade special feature, Rule Change special feature
    - Sam Chan: Implementing MVC design (primarily worked with MonopolyView, Controller, ModelEngine, as well as appropriate classes such as Board, Dice, Player, Tiles, Cards), primary responsibilities involve facilitating the transfer of data between the front end and the backend via the controller (button clicks, etc.)
- View:
    - Jen Li: Worked primarily with implementing the front-end of the project. Helped set up MonopolyView (with the Controller team) as the view engine class that organizes the different program scenes, maintains pointers to model objects for view objects to reference, and creates buttons for the Controller to access. Implemented the starter scenes (theme set up, language chooser, player configuration settings) and worked with view components such the PlayerChipView, PlayerInformationBox, the screen package, and BoardViewAbstract.
    - Kiori Tanaka: Worked with the View team to implement the front-end of the game. Personal roles include working on the hover pop-up feature of the property tiles (PopUp and ModifyPopUp class), creating the end screen, set up and implementation the BoardView method to fill the board with TileView given Tile map and information, Button class, Language choicebox and methods related, created DiceView, helped with language property files.
    - Amik Mandal: Worked with view team to implement the front-end design of the game. Largely responsible for the TileView package and the setup for the junior and regular boards (layout of the tiles and placement of the dialogue box). Worked extensively to refactor the TileView package and the board package.


#### Resources
Any books, papers, online, or human resources that you used in developing the project
- Javadocs (especially for javafx, resourcebundle, reflection)
- Office hours with TA, instructors for git, overall plan, assistance with certain functionalities
- Stackoverflow for help with implementing audio, various misc. functions
- Code.makery.ch for help with JavaFX implementations such as dialog boxes/alerts

#### Starting the Project
- Files used to start the project (the class(es) containing main): Main class is located under View, which creates instances of MonopolyView, ModelEngine, Controller, which create instances of other classes to run the game.


#### Testing
- Files used to test the project and errors you expect your program to handle without crashing: the /tests directory is located under the /model directory, uses Junit tests for the backend


#### Data/Resources
- Any data or resource files required by the project (including format of non-standard files): All files are located under the /data directory.
- /data/gamefiles/setupfiles/: These setup files contain all needed base information (relative file paths to other needed classes and configuration files.
 - /data/gamefiles/<gametype>/: These files include game-specific configurationfiles:
	- <gametype>Colors: Contains initial colors of the different tile groups to be displayed on the board
	- <gametype>PropertiesInfo: Contains all property information of each property on the board (including titles, prices, rents, and identifier information)
	- <gametype>PerksInfo: Contains the a combined ?deck? of Chance and Community chest cards (including their name, text, and action-information)
	- <gametype>TilesLayout: Contains the index-based location of each ?tile? on the board to arrange the tiles
- /data/audio/: Contains all audio files (button-effects, game music... etc)
- /data/gamefiles/languagefiles: These files include property files of all the different languages available so that altering labels on buttons is seamless and easy


#### Program Usage
- Any information about using the program (i.e., command-line/applet arguments, key inputs, interesting example data files, or easter eggs): Type 1234 to enable cheats, 4321 to disable cheats (only when playing the game). Or, type m to activate move cheat, c to activate money cheat. ?Yeet? to win the game automatically Hotkeys: R to roll dice, S to sell property, B to buy property, E to end turn, F to forfeit (and pay respects)
- Upon boot, the user is greeted with a welcome screen and the option to change the language to one of the six currently supported languages. After clicking ?Play,? the user is presented with gametype options that currently include the Standard 40-tile game, the Junior 24-tile game, and the Millionaire adaptation. There are also options to change the theme of the UI that will provide immersive videos and images to add variation to the visuals. The user will also select the number of players (both human and CPU) currently up to nine. After clicking ?Next,? the user is now prompted to enter player information that includes player name, token color (using color picker), and type, which can set up CPU players. After clicking ?Start,? the user now has entered the main UI, the game is all set up, and players can now play the game. 
- Available player choices are simplified by greying out unavailable options, simplifying user interaction.
- Moving the mouse pointer over any of the properties on the board will display a temporary popup that displays the property?s information that is normally displayed on the card. 
- Clicking the player name buttons immediately to the right top of the board will display a player info box that displays the player?s balance, properties owned, and any usable chance/community chest cards in possession.
- The white box in the middle of the screen, next to our old fella, will update upon game movement and events to allow easy understanding of the board action. To allow other players and/or inattentive players to understand past game action, the button ?Event Log? will display all past events, with the most recent at the top.
- The ?Home? button leaves the current game and returns to the initial greeting menu.
- The ?Settings? button opens a scene that displays the option to change the theme from a drop-down of available choices and the versatility to change either music volume (the redbone sax and bankaccount music) or FX volume (the sounds that play after specific button clicks).
- Cheats can be enabled by pressing the digits 1, 2, 3, 4 in order and disabled by pressing 4, 3, 2, 1 in order.
	- The move cheat allows users to move the current player any number of spaces, both backwards and forwards)
	- The money cheat allows users to add or subtract money from the current player, also indicated by no signs or a negative sign.


#### Decisions/Assumptions
- Any decisions, assumptions, or simplifications you made to handle vague, ambiguous, or conflicting requirements
- One main decision was to use getters/setters for buttons and user input instead of handlers/listeners. This way, the button input was handled in the View and then sent to Model for interpretation. This was not only conducive to straightforward, readable code, but also eliminated the need for separate handler and listener classes and code. This obviously necessitates more getters/setters, but the tradeoff is in our favor.
- When reading config files (for tiles, cards, etc.), the use of reflection to create new instances of objects assumes specific naming patterns for subclasses of abstract classes
- Another main decision was to create classes in the Model under the package ?modelengine? to facilitate interactions between the different classes in the Model. The code is intuitively organized mostly by button input methods and then separated into classes that handle methods that checked game conditions (ModelCheck), handle the bulk of action implementations (ModelAction), and organize the two former while also setting up files and reading in configuration data (ModelEngine). These are under the package src/model/modelengine/.
- Another main decision was to use a group of Controller classes that also delegated responsibilities similar to the Model structure. The Controller serves to take in user input from the View and then execute the associated methods within Model, which is then sent to View to update visuals. This follows the MVC design concept and mainly focuses on isolating the Model from the View and keeping raw data hidden as much as possible. The View is then responsible for interpreting this raw data and displaying it to the user. Under src/controller/, the code is organized into classes that handle raw button input and enlists the help other other controller/model classes (ButtonController), handle CPU actions and decisions as well as time-based action (to allow the console box to update the message and the user to understand the CPU?s actions), handle the enabling and disabling of UI buttons (StatusController), handle all audio-related files and sounds (AudioEffects), and organize all the former and contain initial setup implementations and associated UI (Controller).
- Some classes contain dependencies regarding method order of execution. The most prominent examples are in ModelEngine where certain elements need to be created or set before other methods are executed (setup of files need to be executed in the order of button clicks... etc). In MonopolyView the getters for the game type, number of players, and game theme can only be called after the user sets them in the SetUpScreen.


#### Bugs/Issues
- Any known bugs, crashes, or problems with the project's functionality: Pressing buttons fast can lead to null pointer exception or other kinds of exception some times due to asynchronous tasks that don?t initialize certain variables on time. Sometimes misalignment of buttons depending of screen resolution of the user
-The End screen music sometimes continues playing after the user clicks ?Start Over?
- Loading the game screen?s video background may be lagging during the initial scene set up.
- Player stats do not update automatically- user needs to double click the player toggle button to refresh the player?s stats.
- The UI may change slightly across different computers due to the variations in screen resolution.


#### Extra Features
- Auctions/Trading functionality
- Rules change after voting functionality
- Cheat Keys, Hotkeys
- Language functionality for Chinese, English, Japanese, Spanish, Bengali
- Sound effects on button clicks as well as in-game and end-game music
- Settings that allows users to change visual themes and manipulate music and FX volumes
- Event Log that logs and displays all past events 
- Mouse-scrolling effect that displays all contained property information
- CPU player that emulates player action with time-based execution (to allow user understanding of CPU?s actions)
- Animated dice (as well as dice that displays the number rolled for each die)
- Intuitive UI (data-driven) that closely resembles a monopoly board
- Restart game functionality
- Use of videos as background for the game screen, and they can be easily changed after set up in settings
