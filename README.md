TicTacToe
================================================
----------------------------------------------------------------
Table of content
[TOC]
----------------------------------------------------------------

# 1. Product architecture
- **oxo-api** (API)
  **Java Spring** application that handles all the game's logic. the source code is compiled using **JDK 16**
  - Unit tests.
    Following the Test Driven Development (**TDD**) practice, those ones helped out mainly to validate the algorithm that rules over the TicTacToe game.
- **oxo-view** (UI Client)
  **React js** application that gives possibilities to interact with the API.

# 2. Functionalities
- Create a new game at any moment
- Join / fetch a created game and load its current state
- Play the game
- Take a revange after a game ended either on a victory/loss or either on a tie/draw.
- Reset scores and start with 0 loss and 0 victory for both players.

# 3. Features
- The UI adapts to the game's actual state
- The game displays the scores at the end of a game.


# 4. Getting started

1. Run the maven project:
   navigate to the root location of the maven project (in oxo folder) and run this command 
   ```cmd 
   $ mvn spring-boot:run
   ```
   The application will run on `localhost:8080`.

2. Setup the UI project:
   navigate to the root location of the UI client (in oxo-view folder) and run this command
   ```cmd
   $ npm install
   ```
   it will pull the dependencies from the node package manager. If you do not have node installed, you can find the official download platform [here (https://nodejs.org/en/download/)](https://nodejs.org/en/download/). Choose the latest Long Term Support (**LTS**) version.

3. Run the UI application:
   run this command at the root location of the UI client (in oxo-view folder)
   ```cmd
   $ npm run start
   ```
   The application will run on `localhost:3000`.
----------------------------------------------------------------
[WIKIPEDIA Tic-tac-toe](https://en.wikipedia.org/wiki/Tic-tac-toe) | V 0.2.1 | [MIT](./LICENSE)