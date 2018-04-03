package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Constants {


    public static final int DESKTOP_WIDTH = 600; // Constants for configuring the window size of the desktop launcher
    public static final int DESKTOP_HEIGHT = 900;

    public static final int MENU_WORLD_SIZE = 100;

    public static final Color MENU_BACKGROUND = new Color(toRGB(255, 255, 157));

    public static final Color SQUARE_COLOR = new Color(toRGB(0, 163, 136));
    public static final int SQUARE_INITIAL_SIZE = 6;
    public static final Color PLAYSCREEN_BG = new Color(toRGB(255, 255, 157));
    public static final float SQUARE_ACCELERATION = 80f;
    public static final float SQUARE_BOUNCY = 2.0f; // 0 bouncy means velocity is conserved when hitting walls
    public static final float SQUARE_INCREASE_SIZE = 0.01f;
    public static final float SQUARE_INCREASE_SIZE_SPEED = 2f;
    public static final float SQUARE_SPEED = 0.8f;

    public static final int RED_CIRCLE_COLOR_R = 255;
    public static final int RED_CIRCLE_COLOR_G = 114;
    public static final int RED_CIRCLE_COLOR_B = 56;
    public static final int RED_CIRCLE_COLOR_DIFFERENCE = 6;
    public static final Color RED_CIRCLE_EATABLE = new Color(toRGB(56, 176, 85));
    public static final Color RED_CIRCLE_NON_EATABLE = new Color(toRGB(176, 56, 65));

    public static final float RED_CIRCLE_RADIUS_MIN = 2.0f;
    public static final float RED_CIRCLE_RADIUS_MAX = 12.0f;
    public static final int RED_CIRCLE_CAPACITY = 100;
    public static final int RED_CIRCLE_SPAWN_RATE = 2;

    public static final float RED_CIRCLE_MOVEMENT_SPEED_MIN = 10;
    public static final float RED_CIRCLE_MOVEMENT_SPEED_MAX = 50;

    public static final float MOVEMENT_CIRCLE_RADIUS = 12.0f;
    public static final Color MOVEMENT_CIRCLE_COLOR = new Color(0,0,0,0.2f);
    public static final Color INNER_MOVEMENT_CIRCLE_COLOR = new Color(0,0,0,0.5f);
    public static final float INNER_MOVEMENT_CIRCLE_RADIUS = 3.0f;
    public static final float TOUCHPAD_SIZE = 25;
    public static final float KNOB_SIZE = 5f;

    public static final Color SCORE_COLOR = new Color(toRGB(223, 223, 137));
    public static final float SCORE_TEXT_SIZE = 0.4f;
    public static final int SCORE_MULTIPLIER = 10;

    public static final Color TITLE_COLOR = new Color(toRGB(127, 127, 78));
    public static final float TITLE_SIZE = 0.2f;

    public static final float INSTRUCTION_SIZE = 0.06f;


    public static final float PAUSE_BUTTON_SIZE = 0.01f;
    public static final float PAUSE_TEXT_SIZE = 0.064f;
    public static final float GAME_OVER_TEXT_SIZE = 0.09f;

    public static final Color PAUSE_BOXES_COLOR = new Color(toRGB(0, 163, 136));
    public static final float RESUME_BOX_WIDTH = 30;
    public static final float MUSIC_BOX_WIDTH = 14f;

    public static final float RESUME_BOX_POSITION = 3.5f; //larger the number, the farther down the boxes go
    public static final float MUSIC_BOX_POSITION = 9f;

    public static final float RESUME_ICON_SIZE = 0.01f;
    public static final float MUSIC_ICON_SIZE = 0.007f;
    public static final float POP_SOUND_LEVEL = 0.6f;
    public static final float POP_DIE_SOUND_LEVEL = 0.6f;

    public static final Color END_SCORE_COLOR = new Color(toRGB(190,235,159));
    public static final float END_SCORE_FONT_SIZE = 0.08f;
    public static final float CROWN_SIZE = 0.09f;

    public static Color toRGB(int r, int g, int b) {
        float RED = r / 255.0f;
        float GREEN = g / 255.0f;
        float BLUE = b / 255.0f;
        return new Color(RED, GREEN, BLUE, 1);
    }

}
