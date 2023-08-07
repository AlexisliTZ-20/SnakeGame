package com.juegopp.process;

import com.juegopp.logic.GameLogic;
import com.juegopp.view.GameView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameProcess implements ActionListener {
    private GameLogic gameLogic;
    private GameView gameView;
    private Timer timer;

    public GameProcess(GameLogic gameLogic, GameView gameView) {
        this.gameLogic = gameLogic;
        this.gameView = gameView;

        timer = new Timer(100, this);
    }

    public void startGame() {
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        gameLogic.update();
        gameView.repaint();
    }
}
