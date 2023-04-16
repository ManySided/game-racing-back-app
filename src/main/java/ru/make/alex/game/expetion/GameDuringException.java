package ru.make.alex.game.expetion;

public class GameDuringException extends RuntimeException{
    public GameDuringException(String message) {
        super(message);
    }
}
