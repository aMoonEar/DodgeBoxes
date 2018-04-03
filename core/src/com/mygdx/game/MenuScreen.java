package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class MenuScreen extends InputAdapter implements Screen {

    MyGdxGame game;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    ExtendViewport viewport;

    BitmapFont codeFont;
    BitmapFont arialFont;
    GlyphLayout title;
    GlyphLayout instructions;


    public MenuScreen(MyGdxGame game) {
        this.game = game;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        viewport = new ExtendViewport(Constants.MENU_WORLD_SIZE, Constants.MENU_WORLD_SIZE);
        Gdx.input.setInputProcessor(this);
        codeFont = game.getManager().get("code.ttf");
        arialFont = game.getManager().get("instruction.ttf", BitmapFont.class);
        title = new GlyphLayout();
        instructions = new GlyphLayout();
        System.out.println("MenuScreen Opened!");

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        viewport.apply();

        Gdx.gl.glClearColor(Constants.MENU_BACKGROUND.r, Constants.MENU_BACKGROUND.g, Constants.MENU_BACKGROUND.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.rect(viewport.getWorldWidth()/2 - Constants.SQUARE_INITIAL_SIZE/2, //Draw the square in the middle
                viewport.getWorldHeight()/2 - Constants.SQUARE_INITIAL_SIZE/2,
                Constants.SQUARE_INITIAL_SIZE, Constants.SQUARE_INITIAL_SIZE,
                Constants.SQUARE_COLOR, Constants.SQUARE_COLOR ,Constants.SQUARE_COLOR, Constants.SQUARE_COLOR);

        shapeRenderer.setColor(Constants.TITLE_COLOR);
        shapeRenderer.rect(0,viewport.getWorldHeight()/5, viewport.getWorldWidth(), viewport.getWorldHeight()/6);
        //shapeRenderer.setColor(Color.BLUE);
        //shapeRenderer.rect(0,Gdx.graphics.getHeight()/30, viewport.getWorldWidth(), Gdx.graphics.getHeight()/30);

        shapeRenderer.end();

        batch.begin();
        float howDown = (float)1.2;
        title.setText(codeFont, "DODGE\nBOX", Constants.TITLE_COLOR, 0 , 1, true );
        codeFont.draw(batch, title, Gdx.app.getGraphics().getWidth()/2, Gdx.app.getGraphics().getHeight()/howDown);

        instructions.setText(arialFont, "TOUCH AND DRAG\nTO PLAY", Constants.MENU_BACKGROUND, 0, 1, true);
        arialFont.draw(batch, instructions, Gdx.app.getGraphics().getWidth()/2, Gdx.app.getGraphics().getHeight()*0.32f);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.getManager().dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));
        /*Rectangle playBtnBounds = new Rectangle(viewport.getWorldWidth()/2 - (playBtnDimensions.x / 2), viewport.getWorldHeight()/2 - (playBtnDimensions.y /2),
                playBtnDimensions.x, playBtnDimensions.y);
        if(playBtnBounds.contains(worldTouch)) {
            game.showPlayScreen();
        }
        */
        game.showPlayScreen();
        return true;
    }

}
