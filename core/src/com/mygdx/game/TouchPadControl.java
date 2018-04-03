package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TouchPadControl extends InputAdapter {


    private Stage stage;
    private SpriteBatch batch;



    private boolean exist; //This is a boolean that is true if the touchpad exists, and false if the touchpad does not exist


    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;
    public Viewport viewport;
    AssetManager manager;



    public TouchPadControl(Viewport viewport, AssetManager manager) {
        this.viewport = viewport;
        this.manager = manager;
        batch = new SpriteBatch();
        exist = true; //We set this to true because we instantiated the touchpad

        touchpadSkin = new Skin();         //Create a touchpad skin
        Texture background = manager.get("touchBackground.png", Texture.class);
        touchpadSkin.add("touchBackground", background);//Set background image
        //Set knob image
        Texture knob = manager.get("touchKnob.png", Texture.class);
        touchpadSkin.add("touchKnob", knob);
        //Create TouchPad Style
        touchpadStyle = new Touchpad.TouchpadStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        touchKnob.setMinHeight(Constants.KNOB_SIZE);
        touchKnob.setMinWidth(Constants.KNOB_SIZE);
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(0, touchpadStyle);


        //Create a Stage and add TouchPad
        stage = new Stage(viewport, batch);
        stage.addActor(touchpad);
        Gdx.input.setInputProcessor(stage);
        stage.getViewport().update(viewport.getScreenWidth(), viewport.getScreenHeight());
    }

    public void draw() {
            touchpad.setVisible(true);
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
    }

    public void dispose() {
        touchpad.remove();
        stage.dispose();
        exist = false;
    }

    public Touchpad getTouchpad() {
        return touchpad;
    }

    public boolean isExist() {
        return exist;
    }

}

