package snakeWindow;

import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

public class SnakeWindow extends JFrame {

    int widht = 1360;
    int height = 768;

    Point snake;
    Point comida;

    boolean gameOver = false;

    ArrayList<Point> lista;

    int widhtPoint = 10;
    int heightPoint = 10;

    int direccion = KeyEvent.VK_LEFT;

    long frecuencia = 30;

    Imagen imagenSnake = new Imagen();

    public SnakeWindow() {

        setTitle("Snake Game");

        setSize(widht, height);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - widht / 2, dim.height / 2 - height / 2);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeKeys teclas = new SnakeKeys();
        this.addKeyListener(teclas);

        pointSnake();

        imagenSnake = new Imagen();
        this.getContentPane().add(imagenSnake);

        setVisible(true);

        Momento momento = new Momento();
        Thread trid = new Thread(momento);
        trid.start();
    }

    public void pointSnake() {
        comida = new Point(200, 200);
        snake = new Point(widht / 2, height / 2);

        lista = new ArrayList<Point>();
        lista.add(snake);
        crearComida();
    }

    public void crearComida() {
        Random r1 = new Random();

        comida.x = r1.nextInt(widht);
        comida.y = r1.nextInt(widht);

        if ((comida.x % 5) > 0) {
            comida.x = comida.x - (comida.x % 5);
        }

        if (comida.x < 5) {
            comida.x = comida.x + 10;
        }

        if ((comida.y % 5) > 0) {
            comida.y = comida.y - (comida.y % 5);
        }

        if (comida.y < 5) {
            comida.y = comida.y + 10;
        }
    }

    public void actualizar() {

        imagenSnake.repaint();

        lista.add(0, new Point(snake.x, snake.y));
        lista.remove((lista.size() - 1));

        for (int i = 1; i < lista.size(); i++) {
            Point punto = lista.get(i);
            if (snake.x == punto.x && snake.y == punto.y) {
                gameOver = true;
            }
        }
        if ((snake.x > (comida.x - 10)) && (snake.x < (comida.x + 10)) && (snake.y > (comida.y - 10)) && (snake.y < (comida.y + 10))) {
            crearComida();
            lista.add(0, new Point(snake.x, snake.y));
        }
    }

    public class Imagen extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(new Color(0, 0, 225));

            for (int i = 0; i < lista.size(); i++) {
                Point p = (Point) lista.get(i);
                g.fillRect(p.x, p.y, widhtPoint, heightPoint);
            }

            g.setColor(new Color(255, 0, 0));
            g.fillRect(comida.x, comida.y, widhtPoint, heightPoint);
            
            if(gameOver){
                g.drawString("Game Over!!", 200, 320);
            }
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
                    if (!gameOver) {
                        if (direccion == KeyEvent.VK_UP) {
                            snake.y = snake.y - heightPoint;
                            if (snake.y < 0) {
                                snake.y = height - heightPoint;
                            }
                            if (snake.y > height) {
                                snake.y = 0;
                            }
                        } else if (direccion == KeyEvent.VK_DOWN) {
                            snake.y = snake.y + heightPoint;
                            if (snake.y < 0) {
                                snake.y = height - heightPoint;
                            }
                            if (snake.y > height) {
                                snake.y = 0;
                            }
                        } else if (direccion == KeyEvent.VK_LEFT) {
                            snake.x = snake.x - widhtPoint;
                            if (snake.x < 0) {
                                snake.x = widht - widhtPoint;
                            }
                            if (snake.x > widht) {
                                snake.x = 0;
                            }
                        } else if (direccion == KeyEvent.VK_RIGHT) {
                            snake.x = snake.x + widhtPoint;
                            if (snake.x < 0) {
                                snake.x = widht - widhtPoint;
                            }
                            if (snake.x > widht) {
                                snake.x = 0;
                            }
                        }
                    }

                    actualizar();
                    last = java.lang.System.currentTimeMillis();
                }
            }
        }
    }
}
