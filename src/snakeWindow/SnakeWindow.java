package snakeWindow;

import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.Point;
import javax.swing.JPanel;

public class SnakeWindow extends JFrame {

    int widht = 640;
    int height = 480;

    Point snake;
    int widhtPoint = 10;
    int heightPoint = 10;

    int direccion = KeyEvent.VK_LEFT;

    long frecuencia = 20;

    Imagen imagenSnake = new Imagen();

    public SnakeWindow() {

        setTitle("Snake");

        setSize(widht, height);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - widht / 2, dim.height / 2 - height / 2);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeKeys teclas = new SnakeKeys();
        this.addKeyListener(teclas);

        snake = new Point(widht / 2, height / 2);

        imagenSnake = new Imagen();
        this.getContentPane().add(imagenSnake);
        
        setVisible(true);
        
        Momento momento = new Momento();
        Thread trid = new Thread(momento);
        trid.start();
    }

    public void actualizar() {
        imagenSnake.repaint();
    }

    public class Imagen extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(new Color(0, 0, 225));
            g.fillRect(snake.x, snake.y, widhtPoint, heightPoint);
        }

    }

    public class SnakeKeys extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (direccion != KeyEvent.VK_DOWN) {
                    direccion = KeyEvent.VK_UP;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (direccion != KeyEvent.VK_UP) {
                    direccion = KeyEvent.VK_DOWN;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (direccion != KeyEvent.VK_RIGHT) {
                    direccion = KeyEvent.VK_LEFT;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (direccion != KeyEvent.VK_LEFT) {
                    direccion = KeyEvent.VK_RIGHT;
                }
            }
        }
    }

    public class Momento extends Thread {

        long last = 0;
        public void run() {
            while (true) {
                if ((java.lang.System.currentTimeMillis() - last) > frecuencia) {

                    if (direccion == KeyEvent.VK_UP) {
                        snake.y = snake.y - heightPoint;
                        if (snake.y < 0) {
                            snake.y = height - heightPoint;
                        }
                        if (snake.y > height) {
                            snake.y = 0;
                        }
                    }
                    
                    actualizar();
                    last = java.lang.System.currentTimeMillis();
                }
            }
        }
    }
}
