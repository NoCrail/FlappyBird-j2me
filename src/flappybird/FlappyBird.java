/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flappybird;


import java.util.Random;

public class FlappyBird implements Game {

    private int tick = 0;
    private int horizontalSpeedTick = 0;

    private int verticalSpeed = 1;
    private int horizontalSpeed = 0;
    private int horizontalSpeedUp = 20;
    private int horizontalSpeedDown = 1;
    private int posX = 72;
    private int posY = 80;

    private int window = 45;

    private int pipeInterval = 96;

    private Pos[] pos = new Pos[4];
    private boolean pressed = false;

    private boolean started = false;

    private boolean gameOver = false;

    private int score = 0;
    private int maxScore = 0;

    private int textScale = 2;

    private FontRenderer fontRenderer = new FontRenderer(textScale);

    private Point[] clouds = new Point[4];

    Game.Engine engine;

    FlappyBird(Game.Engine engine) {
        Random r = new Random();
        for (int i = 3; i != 0; i--) {
            clouds[i] = new Point(
                    pipeInterval * (i + 1) + r.nextInt(30),
                    r.nextInt(130)
            );
        }
        engine.isClick(this::onKeyClicked);
        restart();
    }

    private Void onKeyClicked(Integer key) {
        tick = tick + 1;
        pressed = true;
        if (gameOver)
            restart();
        else {
            if (!started) {
                started = true;
                restart();
            }
        }
        return null;
    }

    public void onDraw(Game.Canvas canvas) {

        int w = canvas.getWidth();
        int h = canvas.getHeight();

        canvas.setColor(0, 200, 255, 210);
        canvas.drawRect(0, 0, 288, 160);

        canvas.setColor(255, 255, 255, 50);
        for (int i = 1; i < 4; i++) {
            canvas.drawRect(clouds[i].x, clouds[i].y, (int) (pipeInterval * 1.5), 30);
            canvas.drawRect(clouds[i].x + 5, clouds[i].y + 5, (int) (pipeInterval * 1.5 - 10), 20);
        }

        if (started || gameOver) {
            for (int i = 1; i < 4; i++) {
                drawPipeTop(canvas, pos[i].x, pos[i].height.top);
                drawPipeBottom(canvas, pos[i].x, pos[i].height.bottom);
            }
        }
        drawBird(canvas, posX, posY);

        canvas.setColor(0, 0, 0, 255);

        if (gameOver) {
            canvas.setColor(255, 255, 255, 255);
            canvas.drawRect(w / 2 - (6 << 2), h / 2 - 4, 15 << 2, 10);
            canvas.setColor(0, 0, 0, 255);
            canvas.drawText(w / 2 - (4 << 2), h / 2 - 2, "Game Over");
        }

        drawScore(canvas);

        canvas.setColor(139, 69, 19, 255);
        canvas.drawRect(0, 157, 288, 3);
        canvas.setColor(34, 139, 34, 255);
        canvas.drawRect(0, 155, 288, 2);
    }


    public void onTick() {
        if (horizontalSpeedTick > 5) {
            horizontalSpeedTick = 0;
        }

        if (horizontalSpeedTick != 0) {
            horizontalSpeedTick = horizontalSpeedTick + 1;
        }

//        output.setBool(1, false)
//        output.setBool(2, false)

//        verticalSpeed = property.getNumber("Vertical Speed")
//        horizontalSpeedUp = property.getNumber("Horizontal Speed Up")
//        horizontalSpeedDown = property.getNumber("Horizontal Speed Down")
//        window = property.getNumber("Pipe Interval")

        boolean clicked = engine.isClick();
        if (clicked) {
            tick = tick + 1;
            pressed = true;
            if (gameOver)
                restart();
            else {
                if (!started) {
                    started = true;
                    restart();
                }
            }
        } else {
            tick = 0;
            pressed = false;
        }
        if (tick == 2 && pressed)
            horizontalSpeedTick = 1;
        else
            horizontalSpeed = horizontalSpeedDown;
        if (horizontalSpeedTick > 0) {
            horizontalSpeed = -horizontalSpeedUp / 5;
        }

        Random r = new Random();

        for (int i = 3; i != 0; i--) {
            clouds[i].x = clouds[i].x - verticalSpeed / 2;
            if (clouds[i].x < -pipeInterval * 1.5) {
                int max = 0;
                for (int j = 4; i != 1; i--) {
                    if (clouds[j].x > max) {
                        max = clouds[j].x;
                    }
                }
                clouds[i].x = 288 + r.nextInt(50);
                clouds[i].y = r.nextInt(100);
            }
        }

        if (started) {
            for (int i = 1; i < 4; i++) {

                pos[i].x = pos[i].x - verticalSpeed;

                if (((posX + 6 > pos[i].x - 13 && posX + 6 < pos[i].x + 13) || (posX - 6 > pos[i].x - 13 && posX - 6 < pos[i].x + 13)) &&
                        ((posY + 4 > 0 && posY + 4 < pos[i].height.top + 8) || (posY - 4 < 160 && posY - 4 > 160 - pos[i].height.bottom - 16))
                ) {
                    gameOver = true;
                }

                if (posX == pos[i].x && !gameOver) {
                    score = score + 1;
                    engine.soundJump();
                }

                if (pos[i].x < -30) {
                    int max = 0;
                    for (int j = 1; i < 4; i++) {
                        if (pos[j].x > max) {
                            max = pos[j].x;
                        }
                    }
                    pos[i].x = max + pipeInterval - verticalSpeed;
                    Height h = randomHeight();
                    pos[i].height.top = h.top;
                    pos[i].height.bottom = h.bottom;
                }
            }
            posY = posY + horizontalSpeed;
        }

        if (posY > 150 || posY < 4) {
            gameOver = true;
            if (posY > 150) {
                started = false;
            }
        }
        if (gameOver) {
            engine.soundGameOver();
        }
    }

    private void drawScore(Game.Canvas canvas) {
//        String ss = String.valueOf(score);
//        int x = 144 - textScale * 4 * (ss.length() - 1) - 3 * (ss.length() - 1);
//        int y = 20;
//        int i = 0;
//        for (String c : ss.split(".")) {
//            int cx = x + textScale * 4 * i + 3 * i;
//            switch (c) {
//                case "0":
//                    fontRenderer.draw0(canvas, cx, y);
//                    break;
//                case "1":
//                    fontRenderer.draw1(canvas, cx, y);
//                    break;
//                case "2":
//                    fontRenderer.draw2(canvas, cx, y);
//                    break;
//                case "3":
//                    fontRenderer.draw3(canvas, cx, y);
//                    break;
//                case "4":
//                    fontRenderer.draw4(canvas, cx, y);
//                    break;
//                case "5":
//                    fontRenderer.draw5(canvas, cx, y);
//                    break;
//                case "6":
//                    fontRenderer.draw6(canvas, cx, y);
//                    break;
//                case "7":
//                    fontRenderer.draw7(canvas, cx, y);
//                    break;
//                case "8":
//                    fontRenderer.draw8(canvas, cx, y);
//                    break;
//                case "9":
//                    fontRenderer.draw9(canvas, cx, y);
//                    break;
//            }
//            i = i + 1;
//        }
//        String mss = String.valueOf(maxScore);
//        canvas.drawText(288 - ((mss.length() + 11) << 2) - 15, 5, "Max Score: " + mss);
    }

    private void drawBird(Game.Canvas canvas, int x, int _y) {
        int y = _y - 4;
        canvas.setColor(0, 0, 0, 255);
        canvas.drawRect(x, y, 12, 8);
        canvas.drawRect(x, y + 5, 13, 3);
        canvas.setColor(255, 255, 0, 255);
        canvas.drawRect(x + 1, y + 1, 6, 6);
        canvas.setColor(255, 255, 255, 255);
        canvas.drawRect(x + 7, y + 1, 4, 4);
        canvas.setColor(255, 0, 0, 200);
        canvas.drawRect(x + 8, y + 6, 4, 1);
        canvas.setColor(0, 0, 0, 255);
        canvas.drawRect(x + 9, y + 2, 1, 2);
        canvas.drawRect(x + 6, y, 1, 5);
        canvas.drawRect(x - 2, y + 2, 5, 4);
        canvas.setColor(255, 255, 255, 255);
        canvas.drawRect(x - 1, y + 3, 3, 2);
    }

    private void drawPipeTop(Game.Canvas canvas, int x, int h) {
        //рамка
        canvas.setColor(0, 0, 0, 255);
        canvas.drawRect(x, 0, 20, h);
        canvas.drawRect(x - 2, h - 1, 24, 8);
        //заливка
        canvas.setColor(0, 255, 0, 210);
        canvas.drawRect(x + 1, 0, 18, h - 1);
        canvas.drawRect(x - 1, h, 22, 6);
        //свет
        canvas.setColor(255, 255, 255, 100);
        canvas.drawRect(x + 1, 0, 5, h - 1);
        canvas.drawRect(x - 1, h, 5, 6);
    }

    private void drawPipeBottom(Game.Canvas canvas, int x, int h) {
        //рамка
        canvas.setColor(0, 0, 0, 255);
        canvas.drawRect(x, 160 - h, 20, h - 5);
        canvas.drawRect(x - 2, 160 - h + 1 - 8, 24, 8);
        //заливка
        canvas.setColor(0, 255, 0, 210);
        canvas.drawRect(x + 1, 160 - h + 1, 18, h - 6);
        canvas.drawRect(x - 1, 160 - h - 6, 22, 6);
        //свет
        canvas.setColor(255, 255, 255, 100);
        canvas.drawRect(x + 1, 160 - h + 1, 5, h - 6);
        canvas.drawRect(x - 1, 160 - h - 6, 5, 6);
    }

    private void restart() {
        gameOver = false;
        posX = 72;
        posY = 80;
        verticalSpeed = 1;
        horizontalSpeed = 0;
        if (score > maxScore) {
            maxScore = score;
        }
        score = 0;
        for (int i = 3; i != 0; i--) {
            pos[i] = new Pos(
                    pipeInterval * (i + 1),
                    randomHeight()
            );
        }
    }

    private Height randomHeight() {
        int h = Math.max(new Random().nextInt(160) - window / 2, window / 2 + 2);
        return new Height(h - window / 2, 160 - h - window / 2 + 2);
    }

    static class Height {
        int top;
        int bottom;

        Height(int top, int bottom) {
            this.bottom = bottom;
            this.top = top;
        }
    }

    static class Pos {
        int x;
        Height height;

        public Pos(int x, Height height) {
            this.x = x;
            this.height = height;
        }
    }

    static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
