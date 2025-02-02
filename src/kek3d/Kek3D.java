package kek3d;

import game.Game;
import game.Game.Canvas;

/**
 * @author User
 */
public class Kek3D implements Game {

    public static final int JOYSTICK_UP = -1;
    public static final int JOYSTICK_LEFT = -3;
    public static final int JOYSTICK_RIGHT = -4;
    public static final int JOYSTICK_DOWN = -2;
    public static final int JOYSTICK_UP_LEFT = -6;
    public static final int JOYSTICK_UP_RIGHT = -7;
    public static final int KEY_NUM1 = 49;
    public static final int KEY_NUM2 = 50;
    public static final int KEY_NUM3 = 51;
    public static final int KEY_NUM4 = 52;
    public static final int KEY_NUM5 = 53;
    public static final int KEY_NUM6 = 54;
    public static final int KEY_NUM7 = 55;
    public static final int KEY_NUM8 = 56;
    public static final int KEY_NUM9 = 57;

    int[] blocks = new int[]{
        0xFFFFFF,
        0xFFFFFF,
        0xFF0000,
        0x00FF00,
        0xFF55FF,
        0xFFEE60
    };

    // Параметры игрока
    class Player {

        float x = 5;
        float y = 5;
        float rotation = 0;
        int fov = 60;
        int moveSpeed = 3;
        int rotationSpeed = 2;
    }

    class Point {

        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    class Color {

        int r;
        int g;
        int b;

        Color(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }
    }

    class Raycast {

        Float distanceToTile;
        int tileID;

        Raycast(Float distanceToTile, int tileID) {
            this.distanceToTile = distanceToTile;
            this.tileID = tileID;
        }
    }

    Player player = new Player();

    int inputX = 0;
    int inputY = 0;
    int inputYaw = 0;

    int screenWidth = 32;
    int screenHeight = 32;

    int rayLength = 2000;
    float raycastStep = 0.1f;
    float raycastQuality = 4.8953488372093f;

    int horizonPosition = 0;
    int tileWidth = 64;
    float distanceToProjectionPlane = 1;

    boolean started = false;

    void init(Game.Canvas canvas) {
        if (started) {
            return;
        }
        started = true;
        screenWidth = canvas.getWidth();
        screenHeight = canvas.getHeight();
        horizonPosition = canvas.getHeight() / 2;
        raycastStep = (float) (player.fov) / screenWidth;
        distanceToProjectionPlane = (float) (((float) (canvas.getWidth()) / 2) / Math.tan(Math.toRadians(((float) (player.fov) / 2))));

        player.x = tileWidth * player.x - tileWidth / 2;
        player.y = tileWidth * player.y - tileWidth / 2;
    }

    private Point convertWorldCoordsToMapCoords(float x, float y) {
        return new Point(round(x / tileWidth), round(y / tileWidth));
    }

    private int round(float num) {
        if (num >= 0) {
            return (int) Math.floor(num + 0.5);
        } else {
            return (int) Math.ceil(num - 0.5);
        }
    }

    private int floor(float num) {
        return (int) Math.floor(num);
    }

    float constrainAngle(float value) {
        value = value % 360;
        return value;
    }

    void move(float distanceForward, float distanceRight) {

        float forwardRotation = (float) Math.toRadians(player.rotation);
        float rightRotation = (float) Math.toRadians(player.rotation + 90);
        float xNew = (float) (player.x + distanceForward * Math.sin(forwardRotation) + distanceRight * Math.sin(rightRotation));
        float yNew = (float) (player.y + distanceForward * Math.cos(forwardRotation) + distanceRight * Math.cos(rightRotation));

        Point world = convertWorldCoordsToMapCoords(xNew, yNew);
        if (Kek3dMap.map[world.y][world.x] == 0) {
            player.x = xNew;
            player.y = yNew;
        }
    }

    void rotate(float angle) {
        player.rotation = constrainAngle(player.rotation + angle);
    }

    Raycast raycast(float angle) {

        angle = (float) Math.toRadians(angle);
        int raySteps = 0;
        float angleSinDistance = (float) (Math.sin(angle) * raycastQuality);
        float angleCosDistance = (float) (Math.cos(angle) * raycastQuality);
        float currentDistance = 0;
        float xWorld = player.x;
        float yWorld = player.y;

        while (true) {
            if (currentDistance <= rayLength) {
                int xMap = (int) floor(xWorld / tileWidth);
                int yMap = (int) floor(yWorld / tileWidth);
                if (Kek3dMap.map[yMap] != null && Kek3dMap.map[yMap][xMap] != 0) {
                    return new Raycast(new Float(currentDistance), Kek3dMap.map[yMap][xMap]);
                }

                xWorld = xWorld + angleSinDistance;
                yWorld = yWorld + angleCosDistance;
                currentDistance = currentDistance + raycastQuality;
                raySteps = raySteps + 1;

            } else {
                return new Raycast(null, 0);
            }
        }
    }

    void drawWorld(Game.Canvas canvas) {
        //Земля
        canvas.setColor(100, 80, 50, 255);
        canvas.drawRect(0, 0, screenWidth, screenHeight);
        // Небо
        canvas.setColor(100, 255, 255, 255);
        canvas.drawRect(0, 0, screenWidth, horizonPosition);
        // Сцена
        float startAngle = player.rotation - player.fov / 2;
        float endAngle = player.rotation + player.fov / 2;
        float startX = 0;
        for (float angle = startAngle; angle < endAngle;) {
            Raycast raycast = raycast(angle);

            if (raycast.distanceToTile != null) {
                float distanceToTile = raycast.distanceToTile.floatValue();
                //  Получаем цвет  стенки
                Color tileColor = getTileColor(blocks[raycast.tileID], distanceToTile);
                canvas.setColor(tileColor.r, tileColor.g, tileColor.b, 255);

                float height = tileWidth / distanceToTile * distanceToProjectionPlane;
                float startY = horizonPosition - height / 2 + 1;
                canvas.drawRect(floor(startX), floor(startY), 1, floor(height));
            }
            startX = startX + 1;
            angle += raycastStep;
        }
    }

    Color getTileColor(int block, float distance) {
        float t = (float) Math.max(0.1, 1 - (distance / ((float) (rayLength) / 2)) - 0.2);
        int r = (int) (((block >> 16) & 0xff) * t);
        int g = (int) (((block >> 8) & 0xff) * t);
        int b = (int) (((block) & 0xff) * t);
        return new Color(r, g, b);
    }

    public void keyRepeated(int keyCode) {
    }

    int pressed = 0;

    public void keyPressed(int keyCode) {
        pressed = keyCode;
        if (keyCode == KEY_NUM2 || keyCode == JOYSTICK_UP) {
            inputY = 1;
        }
        if (keyCode == KEY_NUM8 || keyCode == JOYSTICK_DOWN) {
            inputY = -1;
        }
        if (keyCode == KEY_NUM6) {
            inputX = 1;
        }
        if (keyCode == KEY_NUM4) {
            inputX = -1;
        }
        if (keyCode == KEY_NUM3 || keyCode == JOYSTICK_UP_RIGHT || keyCode == JOYSTICK_RIGHT) {
            inputYaw = 1;
        }
        if (keyCode == KEY_NUM1 || keyCode == JOYSTICK_UP_LEFT || keyCode == JOYSTICK_LEFT) {
            inputYaw = -1;
        }
        System.out.println("___ keyCode=" + keyCode);
    }

    public void keyReleased(int keyCode) {
        if (keyCode == KEY_NUM2 || keyCode == KEY_NUM8 || keyCode == JOYSTICK_UP || keyCode == JOYSTICK_DOWN) {
            inputY = 0;
        }
        if (keyCode == KEY_NUM6 || keyCode == KEY_NUM4) {
            inputX = 0;
        }
        if (keyCode == KEY_NUM3 || keyCode == KEY_NUM1 || keyCode == JOYSTICK_UP_RIGHT || keyCode == JOYSTICK_RIGHT || keyCode == JOYSTICK_UP_LEFT || keyCode == JOYSTICK_LEFT) {
            inputYaw = 0;
        }
    }

    public void onTick() {
        if (!started) {
            return;
        }

        rotate(player.rotationSpeed * inputYaw);
        move(player.moveSpeed * inputY, player.moveSpeed * inputX);
    }

    public void onDraw(Game.Canvas canvas) {

        init(canvas);

        drawWorld(canvas);

        canvas.setColor(0, 255, 0, 255);
        Point world = convertWorldCoordsToMapCoords(player.x, player.y);
        canvas.drawText(1, screenHeight - 60, "pressed=" + pressed);
        canvas.drawText(1, screenHeight - 40, "[" + world.x + "; " + world.y + "]");
        canvas.drawText(1, screenHeight - 20, "(" + player.rotation + ")");

        for (int i = 0; i < Kek3dMap.map.length; i++) {
            for (int j = 0; j < Kek3dMap.map[0].length; j++) {
                canvas.setColor(200, 200, 200, 127);
                if (Kek3dMap.map[i][j] != 0) {
                    canvas.setColor(0, 0, 200, 127);
                }
                canvas.drawRect(j * 2, i * 2, 2, 2);
            }
        }
        canvas.setColor(0, 255, 0, 255);
        canvas.drawRect(world.x * 2, world.y * 2, 2, 2);
    }

}
