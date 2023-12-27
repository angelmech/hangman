package com.example.hangmanapp.control.bluetooth;

/**
 * handles input and output
 */
public interface ConnectionHandlerInterface {

    /**
     * Starts game
     */
    void onGameStart();

    /**
     * Sends game data over the Bluetooth connection.
     *
     * @param name
     */
    void sendPlayerData(String name);

    /**
     * Sends game data over the Bluetooth connection.
     *
     * @param currentGuess
     * @param guessedWords
     * @param incorrectGuesses
     */
    void sendGameData(String currentGuess, int guessedWords, int incorrectGuesses);

    /**
     * outcome of game
     *
     * @param isWinner The game data to be sent.
     */
    void onGameOver(boolean isWinner);


    /**
     * Receives player data over the Bluetooth connection.
     *
     * @return playerdata object of PlayerData class containing the data
     */
    //PlayerData receivePlayerData();

    /**
     * Receives game data over the Bluetooth connection.
     *
     * @return gameData object of GameState class containing the data
     */
    //GameState receiveGameData();


}
