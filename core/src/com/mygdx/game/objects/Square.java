package com.mygdx.game.objects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Constants;

import static com.badlogic.gdx.Input.Keys.B;


public class Square extends InputAdapter {

    Vector2 velocity;
    Viewport viewport;


    Rectangle SquareRect;


    Polygon SquareBounds;
    float SquareWidth;

    float SquareHeight;

    float SquareScaleLimit;
    float SquareScale;

    public Square (Viewport viewport) {
        System.out.println("Square Made");
        this.viewport = viewport;
        formSquare();
     }

    public void formSquare() {
        SquareRect = new Rectangle(viewport.getWorldWidth()/2 - Constants.SQUARE_INITIAL_SIZE/2,
                viewport.getWorldHeight()/2 - Constants.SQUARE_INITIAL_SIZE/2,
                Constants.SQUARE_INITIAL_SIZE,Constants.SQUARE_INITIAL_SIZE
                );

        SquareBounds = new Polygon(new float[]{viewport.getWorldWidth()/2 - Constants.SQUARE_INITIAL_SIZE/2, //bottom left x
                viewport.getWorldHeight()/2 - Constants.SQUARE_INITIAL_SIZE/2, //bottom left y
                viewport.getWorldWidth()/2 - Constants.SQUARE_INITIAL_SIZE/2, //top left x
                viewport.getWorldHeight()/2 + Constants.SQUARE_INITIAL_SIZE/2, //top left y
                viewport.getWorldWidth()/2 + Constants.SQUARE_INITIAL_SIZE/2, //top right X
                viewport.getWorldHeight()/2 + Constants.SQUARE_INITIAL_SIZE/2, //top right y
                viewport.getWorldWidth()/2 + Constants.SQUARE_INITIAL_SIZE/2, //bottom right x
                viewport.getWorldHeight()/2 - Constants.SQUARE_INITIAL_SIZE/2, //bottom right x
        });

        SquareScaleLimit = 1;
        SquareScale = 1;

        SquareWidth = Constants.SQUARE_INITIAL_SIZE;
        SquareHeight = Constants.SQUARE_INITIAL_SIZE;
        velocity = new Vector2(0, 0);
    }

    public void update(float delta) {
        ControlComputer(delta);
        Bounds();
        UpdateProperties(delta);


    }

    public void draw(ShapeRenderer sr, float delta) {
        sr.setColor(Constants.SQUARE_COLOR);


        sr.rect(SquareRect.getX(), SquareRect.getY(),
                Constants.SQUARE_INITIAL_SIZE/2,
                Constants.SQUARE_INITIAL_SIZE/2,
                Constants.SQUARE_INITIAL_SIZE,
                Constants.SQUARE_INITIAL_SIZE,
                SquareScale, SquareScale, 0);

    }


    public void ControlComputer(float delta) {

       if (Gdx.input.isKeyPressed(21)) { //Go left
            velocity.x -= delta * Constants.SQUARE_ACCELERATION;
        }

       if (Gdx.input.isKeyPressed(22)) { //Go right
            velocity.x += delta * Constants.SQUARE_ACCELERATION;
        }

        if (Gdx.input.isKeyPressed(19)) { //Go up
            velocity.y += delta * Constants.SQUARE_ACCELERATION;
        }

        if (Gdx.input.isKeyPressed(20)) { //Go down
            velocity.y -= delta * Constants.SQUARE_ACCELERATION;
        }
        /*
        float xAxis = -Gdx.input.getAccelerometerY();
        float yAxis = Gdx.input.getAccelerometerX();

        float accelerationX = -Constants.SQUARE_ACCELERATION * xAxis / (ACCELEROMETER_SENSITIVITY * ACCELERATION_OF_GRAVITY);
        float accelerationY = - Constants.SQUARE_ACCELERATION * yAxis / (ACCELEROMETER_SENSITIVITY * ACCELERATION_OF_GRAVITY);

        velocity.x += delta * accelerationX;
        velocity.y += delta * accelerationY;

        velocity.clamp(0, Constants.SQUARE_MAX_SPEED);
        */
        SquareRect.setX(SquareRect.getX() + (delta * velocity.x));
        SquareRect.setY(SquareRect.getY() + (delta * velocity.y));

    }

    public void Bounds() {

        if (SquareRect.getX() + (Constants.SQUARE_INITIAL_SIZE/2) - (SquareWidth/2) < 0 ) {
            SquareRect.setX((SquareWidth/2) - (Constants.SQUARE_INITIAL_SIZE/2));
            velocity.x = -velocity.x / Constants.SQUARE_BOUNCY;
        }

        if ( SquareRect.getX() + (Constants.SQUARE_INITIAL_SIZE/2)  + (SquareWidth/2) > viewport.getWorldWidth()) { //Restrict going past right boundary
            SquareRect.setX(viewport.getWorldWidth() - (SquareWidth/2) - (Constants.SQUARE_INITIAL_SIZE/2));
            velocity.x = -velocity.x / Constants.SQUARE_BOUNCY;
        }

        if(SquareRect.getY() + (Constants.SQUARE_INITIAL_SIZE/2) + (SquareHeight/2) > viewport.getWorldHeight()) {
            SquareRect.setY(viewport.getWorldHeight() - (SquareHeight/2) - (Constants.SQUARE_INITIAL_SIZE/2));
            velocity.y = -velocity.y / Constants.SQUARE_BOUNCY;
        }

        if(SquareRect.getY() + (Constants.SQUARE_INITIAL_SIZE/2) - (SquareHeight/2) < 0) {
            SquareRect.setY((SquareHeight/2) - (Constants.SQUARE_INITIAL_SIZE/2));
            velocity.y = -velocity.y / Constants.SQUARE_BOUNCY;
        }

    }

    public void UpdateProperties(float delta) {

        if (Gdx.input.isKeyJustPressed(B)){
            SquareScaleLimit = SquareScaleLimit + Constants.SQUARE_INCREASE_SIZE;
        }

        if (SquareScale < SquareScaleLimit) {
            SquareScale = SquareScale + (Constants.SQUARE_INCREASE_SIZE_SPEED * delta);
            SquareWidth = Constants.SQUARE_INITIAL_SIZE * SquareScale;
            SquareHeight = Constants.SQUARE_INITIAL_SIZE * SquareScale;
        }

        SquareBounds.setVertices(new float[]{
                SquareRect.getX() + (Constants.SQUARE_INITIAL_SIZE/2) - (SquareWidth/2),//bottom left x
                SquareRect.getY() + (Constants.SQUARE_INITIAL_SIZE/2) - (SquareWidth/2),//bottom left y
                SquareRect.getX() + (Constants.SQUARE_INITIAL_SIZE/2) - (SquareWidth/2),//top left x
                SquareRect.getY() + (Constants.SQUARE_INITIAL_SIZE/2) + (SquareWidth/2),//top left y
                SquareRect.getX() + (Constants.SQUARE_INITIAL_SIZE/2) + (SquareWidth/2),//top right x
                SquareRect.getY() + (Constants.SQUARE_INITIAL_SIZE/2) + (SquareWidth/2),//top right y
                SquareRect.getX() + (Constants.SQUARE_INITIAL_SIZE/2) + (SquareWidth/2),//bottom right x
                SquareRect.getY() + (Constants.SQUARE_INITIAL_SIZE/2) - (SquareWidth/2),//bottom right x
        });

    }

    public void squareBigger(float circleRadius) {
        SquareScaleLimit = SquareScaleLimit + (Constants.SQUARE_INCREASE_SIZE*circleRadius);
    }

    public Polygon getSquareBounds() {
        return SquareBounds;
    }

    public float getSquareWidth() {
        return SquareWidth;
    }

    public Rectangle getSquareRect() {
        return SquareRect;
    }

    public void setSquareSize(float squareSize) {
        SquareHeight = squareSize;
        SquareWidth = squareSize;
    }

    public void dispose() {
        SquareScale = 0;
        SquareScaleLimit = 0;
    }


    }

