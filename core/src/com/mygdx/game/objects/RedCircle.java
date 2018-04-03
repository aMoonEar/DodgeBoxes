package com.mygdx.game.objects;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Constants;

public class RedCircle {

    Vector2 position;


    Circle circle;
    boolean direction; //true = going right, false = going left
    float circleRadius;
    float circleMovementSpeed;
    Square square;



    public RedCircle(Vector2 position, boolean direction, Square square) {
        this.position = position;
        this.direction = direction;
        this.square = square;
        makeCircle();
    }

    public void makeCircle() {
        setCircleRadius(MathUtils.random(Constants.RED_CIRCLE_RADIUS_MIN, Constants.RED_CIRCLE_RADIUS_MAX));
        setCircleMovementSpeed(MathUtils.random(Constants.RED_CIRCLE_MOVEMENT_SPEED_MIN, Constants.RED_CIRCLE_MOVEMENT_SPEED_MAX));
        circle = new Circle(position, circleRadius);
//        circle = new Circle(position, MathUtils.random(Constants.RED_CIRCLE_RADIUS_MIN, Constants.RED_CIRCLE_RADIUS_MAX));
    }

    public void render(ShapeRenderer sr) {
        sr.circle(circle.x, circle.y, circle.radius, 80);
    }

    public void update(float delta) {
        if (direction) { //automate the direction of the circle
            circle.setX(circle.x + (delta * circleMovementSpeed));
        } else if (direction == false) {
            circle.setX(circle.x - (delta * circleMovementSpeed));
        }
    }


    public boolean isDirection() {
        return direction;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Circle getCircle() {
        return circle;
    }

    public float getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(float circleRadius) {
        this.circleRadius = circleRadius;
    }

    public void setCircleMovementSpeed(float circleMovementSpeed) {
        this.circleMovementSpeed = circleMovementSpeed;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

}
