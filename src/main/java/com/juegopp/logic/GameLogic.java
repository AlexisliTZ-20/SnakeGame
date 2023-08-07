package com.juegopp.logic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.juegopp.object.Circle;
import com.juegopp.view.GameView;

public class GameLogic extends JPanel implements Runnable, KeyListener {
    private static final int SIZE = 25;
    private static final int WIDTH = 1550;
    private static final int HEIGHT = 890;
    private static final int INITIAL_SPEED = 200;
    private static final int FOOD_TIMEOUT = 7000; // 7 segundos en milisegundos

    private List<Circle> snake;
    private Circle food;
    private int direction;
    private int score;
    private int speed;
    private int counter;
    private long lastFoodTime;

    public GameLogic() {
        snake = new ArrayList<>();
        direction = KeyEvent.VK_RIGHT;
        score = 0;
        speed = INITIAL_SPEED;
        counter = 0;
        lastFoodTime = System.currentTimeMillis();
        setFocusable(true);
        setPreferredSize(new java.awt.Dimension(WIDTH, HEIGHT));
        addKeyListener(this);
        startGame();
    }

    public void startGame() {
        snake.clear();
        snake.add(new Circle(10, 10, SIZE, Color.GREEN));
        generateFood();
        new Thread(this).start();
    }

    private void generateFood() {
        Random random = new Random();
        int x = random.nextInt(WIDTH / SIZE);
        int y = random.nextInt(HEIGHT / SIZE);
        food = new Circle(x, y, SIZE, Color.RED);
        lastFoodTime = System.currentTimeMillis();
    }

    private void moveSnake() {
        int headX = snake.get(0).getX();
        int headY = snake.get(0).getY();

        switch (direction) {
            case KeyEvent.VK_UP:
                headY--;
                break;
            case KeyEvent.VK_DOWN:
                headY++;
                break;
            case KeyEvent.VK_LEFT:
                headX--;
                break;
            case KeyEvent.VK_RIGHT:
                headX++;
                break;
        }

        Circle newHead = new Circle(headX, headY, SIZE, Color.GREEN);
        snake.add(0, newHead);

        if (headX == food.getX() && headY == food.getY()) {
            score++;
            speed -= 5;
            counter++;
            generateFood();
        } else {
            snake.remove(snake.size() - 1);
        }

        if (headX >= WIDTH / SIZE) {
            newHead.setX(0);
        } else if (headX < 0) {
            newHead.setX(WIDTH / SIZE - 1);
        }

        if (headY >= HEIGHT / SIZE) {
            newHead.setY(0);
        } else if (headY < 0) {
            newHead.setY(HEIGHT / SIZE - 1);
        }

        if (checkCollision()) {
            gameOver();
        }

        if (System.currentTimeMillis() - lastFoodTime >= FOOD_TIMEOUT) {
            generateFood();
            counter--;
            if (counter < 0) {
                counter = 0;
            }
        }
    }

    private boolean checkCollision() {
        int headX = snake.get(0).getX();
        int headY = snake.get(0).getY();

        for (int i = 1; i < snake.size(); i++) {
            if (headX == snake.get(i).getX() && headY == snake.get(i).getY()) {
                return true;
            }
        }

        return false;
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(null, "Game Over\nPuntuacion: " + score);
        startGame();
    }

    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        for (Circle c : snake) {
            c.draw(g);
        }
        food.draw(g);
        drawCounter(g);
    }

    private void drawCounter(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Contador: " + counter, 10, 30);
    }
    
    

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(speed);
                moveSnake();
                repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN)
                && Math.abs(direction - key) != 2) {
            direction = key;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No se utiliza
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No se utiliza
    }

    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
