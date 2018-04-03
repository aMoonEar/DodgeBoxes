package com.mygdx.game.objects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Constants;
import com.mygdx.game.TouchPadControl;

public class RedCircles {

    DelayedRemovalArray<RedCircle> redCircleList;
    Viewport viewport;
    Intersector intersector;

    boolean gameState; //False if the player lost, True if the player is still playing


    public RedCircles(Viewport viewport) {
        gameState = true;
        redCircleList = new DelayedRemovalArray<RedCircle>(false, Constants.RED_CIRCLE_CAPACITY);
        this.viewport = viewport;
        intersector = new Intersector();
    }

    public void update(float delta, Square square, TouchPadControl touchpad, Sound popSound, boolean soundToggle) {

        if (MathUtils.random() < delta * Constants.RED_CIRCLE_SPAWN_RATE && MathUtils.randomBoolean()) {

                Vector2 redCirclePosition = new Vector2( - (Constants.RED_CIRCLE_RADIUS_MAX * 2), MathUtils.random() * viewport.getWorldHeight());
                RedCircle newRedCircle = new RedCircle(redCirclePosition, true, square);
                redCircleList.add(newRedCircle);

            } else if (MathUtils.random() < delta * Constants.RED_CIRCLE_SPAWN_RATE && !MathUtils.randomBoolean()) { //Spawn moving towards the left
                Vector2 redCirclePosition = new Vector2(viewport.getWorldWidth() + (Constants.RED_CIRCLE_RADIUS_MAX * 2),
                        MathUtils.random() * viewport.getWorldHeight());
                RedCircle newRedCircle = new RedCircle(redCirclePosition, false, square);
                redCircleList.add(newRedCircle);
            }


        for (RedCircle redcircle: redCircleList) { //Update each redcircle
            redcircle.update(delta);
        }


        redCircleList.begin();
        for (int i=0; i > redCircleList.size; i++) {

            if (redCircleList.get(i).direction &&
                    redCircleList.get(i).getPosition().x > viewport.getWorldWidth() + redCircleList.get(i).getCircleRadius()) {
                redCircleList.removeIndex(i);
            }

            if (!redCircleList.get(i).direction &&
                    redCircleList.get(i).getPosition().x > - (redCircleList.get(i).getCircleRadius() * 2)) {
                redCircleList.removeIndex(i);
            }
        }

        for (RedCircle redCircle: redCircleList) {

            if (overlaps(square.getSquareBounds(), redCircle.getCircle())) {
                if (redCircle.getCircleRadius()*2 <= square.getSquareWidth()) {
                    redCircleList.removeIndex(redCircleList.indexOf(redCircle, true));
                    if (soundToggle) {
                        popSound.play(Constants.POP_SOUND_LEVEL);
                    }
                    square.squareBigger(redCircle.getCircleRadius());
                    Gdx.app.log("SQUARE SIZE", String.valueOf(square.getSquareWidth()));
                } else {
                    square.dispose();
                    touchpad.dispose();
                    gameState = false;
                }
            }

        }


        redCircleList.end();
    }


    public void render(ShapeRenderer renderer, float squareWidth) {
        for (RedCircle redCircle: redCircleList) {
            if (redCircle.getCircleRadius()*2 > squareWidth ) {
                renderer.setColor(Constants.RED_CIRCLE_NON_EATABLE);
                /*renderer.setColor(Constants.toRGB(Constants.RED_CIRCLE_COLOR_R - Math.round(redCircle.getCircleRadius()*(Constants.RED_CIRCLE_COLOR_DIFFERENCE/2)),
                        Constants.RED_CIRCLE_COLOR_G, Constants.RED_CIRCLE_COLOR_B)); //set color of the circles based on their radius*/
            } else {
                renderer.setColor(Constants.RED_CIRCLE_EATABLE);

            }

            redCircle.render(renderer);
        }
    }

    public static boolean overlaps(Polygon polygon, Circle circle) {
        float []vertices=polygon.getTransformedVertices();
        Vector2 center=new Vector2(circle.x, circle.y);
        float squareRadius=circle.radius*circle.radius;
        for (int i=0;i<vertices.length;i+=2){
            if (i==0){
                if (Intersector.intersectSegmentCircle(new Vector2(vertices[vertices.length - 2], vertices[vertices.length - 1]), new Vector2(vertices[i], vertices[i + 1]), center, squareRadius))
                    return true;
            } else {
                if (Intersector.intersectSegmentCircle(new Vector2(vertices[i-2], vertices[i-1]), new Vector2(vertices[i], vertices[i+1]), center, squareRadius))
                    return true;
            }
        }
        return polygon.contains(circle.x, circle.y);
    }



    public boolean isGameState() {
        return gameState;
    }


}
