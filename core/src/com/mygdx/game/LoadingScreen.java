package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class LoadingScreen extends InputAdapter implements Screen{


    MyGdxGame game;
    ShapeRenderer renderer;
    Texture logo;
    SpriteBatch batch;

    public LoadingScreen(MyGdxGame game) {
        this.game = game;
        logo = new Texture(Gdx.files.internal("logo.png"));
        batch = new SpriteBatch();
        init();
    }

    public void init() {
        game.getManager().load("logo.png", Texture.class);
        game.getManager().load("touchBackground.png", Texture.class);
        game.getManager().load("touchKnob.png", Texture.class);
        game.getManager().load("pause.png", Texture.class);
        game.getManager().load("resume.png", Texture.class);
        game.getManager().load("exit.png", Texture.class);
        game.getManager().load("music_on.png", Texture.class);
        game.getManager().load("music_off.png", Texture.class);
        game.getManager().load("high.png", Texture.class);
        game.getManager().load("pop1.mp3", Sound.class);
        game.getManager().load("popdie.mp3", Sound.class);
        loadFonts(game.getManager());
        renderer = new ShapeRenderer();
    }

    public void loadFonts(AssetManager manager) {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter title = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        title.fontFileName = "code.ttf";
        title.fontParameters.size = Math.round(Gdx.graphics.getWidth() * Constants.TITLE_SIZE);
        //title.fontParameters.color = Constants.TITLE_COLOR;
        manager.load("code.ttf", BitmapFont.class, title);

        FreetypeFontLoader.FreeTypeFontLoaderParameter instruction = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        instruction.fontFileName = "arial.ttf";
        instruction.fontParameters.size = Math.round(Gdx.graphics.getWidth() * Constants.INSTRUCTION_SIZE);
        //instruction.fontParameters.color = Constants.MENU_BACKGROUND;
        manager.load("instruction.ttf", BitmapFont.class, instruction);

        FreetypeFontLoader.FreeTypeFontLoaderParameter score = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        score.fontFileName = "arial.ttf";
        score.fontParameters.size = (Math.round(Gdx.graphics.getWidth()*Constants.SCORE_TEXT_SIZE));
        //score.fontParameters.color = Constants.SCORE_COLOR;
        manager.load("score.ttf", BitmapFont.class, score);

        FreetypeFontLoader.FreeTypeFontLoaderParameter score2 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        score2.fontFileName = "arial.ttf";
        score2.fontParameters.size = (Math.round(Gdx.graphics.getWidth()*Constants.END_SCORE_FONT_SIZE));
        //score.fontParameters.color = Constants.SCORE_COLOR;
        manager.load("score2.ttf", BitmapFont.class, score2);

        FreetypeFontLoader.FreeTypeFontLoaderParameter pause = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        pause.fontFileName = "arial.ttf";
        pause.fontParameters.size = (Math.round(Gdx.graphics.getWidth()*Constants.PAUSE_TEXT_SIZE));
        //score.fontParameters.color = Constants.SCORE_COLOR;
        manager.load("pause.ttf", BitmapFont.class, pause);

        FreetypeFontLoader.FreeTypeFontLoaderParameter gameover = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        gameover.fontFileName = "arial.ttf";
        gameover.fontParameters.size = (Math.round(Gdx.graphics.getWidth()*Constants.GAME_OVER_TEXT_SIZE));
        //score.fontParameters.color = Constants.SCORE_COLOR;
        manager.load("gameover.ttf", BitmapFont.class, gameover);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (game.getManager().update()) {
            game.showMenuScreen();
        } else {
            batch.begin();
            batch.draw(logo, (Gdx.graphics.getWidth() - logo.getWidth())/2, (Gdx.graphics.getHeight() - logo.getHeight())/2);
            batch.end();
        }
    }

    @Override
    public void resize(int width, int height) {

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

    }

}
