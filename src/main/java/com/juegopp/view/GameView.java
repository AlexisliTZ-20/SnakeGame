package com.juegopp.view;

import javax.swing.JFrame;
import com.juegopp.logic.GameLogic;

public class GameView extends JFrame {
    public GameView() {
        setTitle("Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        add(new GameLogic());
        setVisible(true);
    }

    public static void main(String[] args) {
        new GameView();
    }
}
