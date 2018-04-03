package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.objects.RedCircles;
import com.mygdx.game.objects.Square;


public class PlayScreen extends InputAdapter implements Screen {

    public static final String TAG = PlayScreen.class.getName();

    private Preferences prefs;
    private int highscore;
    private boolean soundToggle;

    private float min = 0; //Used to animate the transparency of the screen


    MyGdxGame game;
    private ExtendViewport viewport;
    private ShapeRenderer sr;
    private Square square;
    private RedCircles redCircles;
    private TouchPadControl touchpad;


    private InputEvent fakeTouchDownEvent;
    private Vector2 stagePos;
    private Vector2 screenPos;
    private Vector2 localPos;
    private Texture pauseTexture;

    private BitmapFont score; //text to keep track of score
    private GlyphLayout scoreText; //String of score
    private GlyphLayout highscoreText; //String of highscore

    private BitmapFont pause; //text that says PAUSED
    private GlyphLayout pauseText; //String of pause

    private BitmapFont gameOver; //text that says GAMEOVER
    private GlyphLayout gameOverText; //String of gameover

    private BitmapFont touchToRestart;
    private GlyphLayout touchToRestartText;

    private Texture highScoreCrownTexture;
    private BitmapFont endScore; //text for the end score numbers and text

    private SpriteBatch batch; //to draw the text
    private SpriteBatch batch2;//to draw the pause button

    private Rectangle pauseOutline; // to detect touch detection of the pause button
    private boolean pauseBoolean; //detect if game is paused

    private Rectangle resumeOutline; //Rectangles used in the pause screen
    private Rectangle musicOutline;

    private Texture resumeTexture; //textures used in the pause screen
    private Texture musicOnTexture; //texture present music is off
    private Texture musicOffTexture; //texture present when music is on

    private Sound popSound; //Sound played when box hits green
    private Sound popDieSound; //Sound played when box dies


    public PlayScreen(MyGdxGame game) {
        Gdx.app.log(TAG, "PLAYSCREEN MADE");
        this.game = game;

        viewport = new ExtendViewport(Constants.MENU_WORLD_SIZE, Constants.MENU_WORLD_SIZE);
        sr = new ShapeRenderer();
        square = new Square(viewport);
        redCircles = new RedCircles(viewport);

        stagePos = new Vector2();
        screenPos = new Vector2();
        localPos = new Vector2();

        fakeTouchDownEvent = new InputEvent();
        fakeTouchDownEvent.setType(InputEvent.Type.touchDown);

        prefs = Gdx.app.getPreferences("highscore");
        highscore = prefs.getInteger("score", 0);
        soundToggle = prefs.getBoolean("sound", false);

        touchpad = new TouchPadControl(viewport, game.getManager());

        batch = new SpriteBatch();
        batch2 = new SpriteBatch();

        score = game.getManager().get("score.ttf");
        pause = game.getManager().get("pause.ttf");
        gameOver = game.getManager().get("gameover.ttf");
        touchToRestart = game.getManager().get("pause.ttf");
        endScore = game.getManager().get("score2.ttf");
        popSound = game.getManager().get("pop1.mp3");
        popDieSound = game.getManager().get("popdie.mp3");

        pauseText = new GlyphLayout();
        scoreText = new GlyphLayout();
        touchToRestartText = new GlyphLayout();
        touchToRestartText.setText(pause, "TAP TO RETRY", Color.WHITE, 0,0, true);
        gameOverText = new GlyphLayout();
        gameOverText.setText(gameOver, "GAME OVER", Color.WHITE,0,0,true);
        highscoreText = new GlyphLayout();
        highscoreText.setText(endScore, String.valueOf(prefs.getInteger("score")),Constants.END_SCORE_COLOR,20,0,true);


        highScoreCrownTexture = game.getManager().get("high.png", Texture.class);

        pauseTexture = game.getManager().get("pause.png", Texture.class);
        pauseOutline = new Rectangle(viewport.getWorldWidth() - 2 * (pauseTexture.getWidth() * 0.07f), viewport.getWorldHeight() - 2 * (pauseTexture.getWidth() * 0.07f), 100, 100);

        resumeOutline = new Rectangle(viewport.getWorldWidth()/2 - Constants.RESUME_BOX_WIDTH/2 , viewport.getWorldHeight()/Constants.RESUME_BOX_POSITION - Constants.RESUME_BOX_WIDTH/2,
                Constants.RESUME_BOX_WIDTH,
                Constants.RESUME_BOX_WIDTH); //position the resume box in the pause screen
        musicOutline = new Rectangle(viewport.getWorldWidth()/2 - Constants.MUSIC_BOX_WIDTH/2, viewport.getWorldHeight()/Constants.MUSIC_BOX_POSITION - Constants.MUSIC_BOX_WIDTH/2,
                Constants.MUSIC_BOX_WIDTH,
                Constants.MUSIC_BOX_WIDTH);


        resumeTexture = game.getManager().get("resume.png", Texture.class);
        musicOnTexture = game.getManager().get("music_on.png", Texture.class);
        musicOffTexture = game.getManager().get("music_off.png", Texture.class);

        Gdx.app.log("HIGH SCORE", String.valueOf(prefs.getInteger("score")));

        pauseBoolean = true;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if (pauseBoolean) {

            runGame(delta);

        } else if (redCircles.isGameState()) {
            Gdx.gl.glClearColor(Constants.PLAYSCREEN_BG.r, Constants.PLAYSCREEN_BG.g, Constants.PLAYSCREEN_BG.b, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


            batch.begin();
            scoreText.setText(score, String.valueOf(Math.round((square.getSquareWidth() - Constants.SQUARE_INITIAL_SIZE)*Constants.SCORE_MULTIPLIER)), Constants.SCORE_COLOR, 0, 0, true);//Center glyphlayout

            score.draw(batch, scoreText, Gdx.graphics.getWidth() / 2 + scoreText.width / 2, Gdx.graphics.getHeight() / 2 + scoreText.height / 2);
            batch.end();

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);


            //Draw the last frames of the redcircles and squares
            sr.begin(ShapeRenderer.ShapeType.Filled);
            square.draw(sr, delta);
            redCircles.render(sr, square.getSquareWidth());
            sr.setColor(new Color(0, 0, 0, 0.5f));
            sr.rect(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
            sr.end();

            //Draw the pause text in the middle of the screen
            batch.begin();
            pauseText.setText(pause, "PAUSED", Color.WHITE, 0, 0, true);
            pause.draw(batch, pauseText, Gdx.graphics.getWidth() / 2 + pauseText.width / 2, Gdx.graphics.getHeight() / 1.5f + pauseText.height / 2 );
            batch.end();

            //Draw the exit and music button shape
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(Constants.PAUSE_BOXES_COLOR);
            sr.rect(resumeOutline.x, resumeOutline.y, resumeOutline.getWidth(), resumeOutline.getHeight()); //draw the 3 pause boxes
            sr.rect(musicOutline.x, musicOutline.y, musicOutline.getWidth(), musicOutline.getHeight());
            sr.end();

            //Draw the resume icon
            batch2.begin();
            batch2.setProjectionMatrix(viewport.getCamera().combined);
            batch2.draw(resumeTexture,(resumeOutline.x + Constants.RESUME_BOX_WIDTH/2) - ((viewport.getScreenWidth() * Constants.RESUME_ICON_SIZE))/2, //I have no idea why this centers it in the x, it's just a guess
                    (resumeOutline.y + Constants.RESUME_BOX_WIDTH/2) - ((viewport.getScreenWidth() * Constants.RESUME_ICON_SIZE))/2,
                    viewport.getScreenWidth() * Constants.RESUME_ICON_SIZE,
                    viewport.getScreenWidth() * Constants.RESUME_ICON_SIZE);
            if (soundToggle) {

                batch2.draw(musicOffTexture, (musicOutline.x + Constants.MUSIC_BOX_WIDTH/2) - (viewport.getScreenWidth() * Constants.RESUME_ICON_SIZE)/2,
                        (musicOutline.y + Constants.MUSIC_BOX_WIDTH/2) - (viewport.getScreenWidth() * Constants.MUSIC_ICON_SIZE)/2,
                        viewport.getScreenWidth() * Constants.MUSIC_ICON_SIZE,
                        viewport.getScreenWidth() * Constants.MUSIC_ICON_SIZE);
                batch2.end();
            } else {
                batch2.draw(musicOnTexture, (musicOutline.x + Constants.MUSIC_BOX_WIDTH/2) - (viewport.getScreenWidth() * Constants.RESUME_ICON_SIZE)/2,
                        (musicOutline.y + Constants.MUSIC_BOX_WIDTH/2) - (viewport.getScreenWidth() * Constants.MUSIC_ICON_SIZE)/2,
                        viewport.getScreenWidth() * Constants.MUSIC_ICON_SIZE,
                        viewport.getScreenWidth() * Constants.MUSIC_ICON_SIZE);
                batch2.end();
            }

            //If the resume button is touched, resume the game
            if (Gdx.input.isTouched() && resumeOutline.contains(viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY())))) {
                pauseBoolean = true;
            }

            if (Gdx.input.isTouched() && musicOutline.contains(viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY())))) {
                if (soundToggle) {
                    soundToggle = false;
                    prefs.putBoolean("sound", false);
                } else {
                    soundToggle = true;
                    prefs.putBoolean("sound", true);
                }
                prefs.flush();
            }
        }

        if (!redCircles.isGameState()) {
            pauseBoolean = false;
            Gdx.gl.glClearColor(Constants.PLAYSCREEN_BG.r, Constants.PLAYSCREEN_BG.g, Constants.PLAYSCREEN_BG.b, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);


            //Draw the redcircles
            sr.begin(ShapeRenderer.ShapeType.Filled);
            redCircles.render(sr, square.getSquareWidth());
            redCircles.update(delta,square,touchpad, popSound, soundToggle);

            float max = 0.5f;

            if (min <= max) { //Used to animate the transparency of the screen
                min = min + delta;
            }
            sr.setColor(new Color(0, 0, 0, min));
            sr.rect(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
            sr.end();

            //Replace the highscore if the current score is bigger
            if(Math.round((square.getSquareWidth() - Constants.SQUARE_INITIAL_SIZE)*Constants.SCORE_MULTIPLIER) > highscore) {
                prefs.putInteger("score",Math.round((square.getSquareWidth() - Constants.SQUARE_INITIAL_SIZE)*Constants.SCORE_MULTIPLIER));
                prefs.flush();
                highscoreText.setText(endScore, String.valueOf(prefs.getInteger("score")),Constants.END_SCORE_COLOR,20,0,true);
            }

            batch.begin();
            scoreText.setText(score, String.valueOf(Math.round((square.getSquareWidth() - Constants.SQUARE_INITIAL_SIZE)*Constants.SCORE_MULTIPLIER)), Constants.SCORE_COLOR, 0, 0, true);//Center glyphlayout
            score.draw(batch, scoreText, Gdx.graphics.getWidth() / 2 + scoreText.width / 2, Gdx.graphics.getHeight() / 2 + scoreText.height / 2);
            endScore.draw(batch, highscoreText, Gdx.graphics.getWidth()/2 + highscoreText.width, Gdx.graphics.getHeight()*(1/2.9f));
            gameOver.draw(batch, gameOverText, Gdx.graphics.getWidth()/2 + gameOverText.width /2, Gdx.graphics.getHeight()*(0.75f) + gameOverText.height /2 );
            touchToRestart.draw(batch, touchToRestartText, Gdx.graphics.getWidth()/2 + touchToRestartText.width/2, Gdx.graphics.getHeight()*(0.15f) + touchToRestartText.height/2);
            batch.draw(highScoreCrownTexture, Gdx.graphics.getWidth()*(1/3.5f), Gdx.graphics.getHeight()*(1/3.3f), Gdx.graphics.getHeight()*Constants.CROWN_SIZE, Gdx.graphics.getWidth()*Constants.CROWN_SIZE);
            batch.end();

            if (soundToggle) {
                popDieSound.play(Constants.POP_DIE_SOUND_LEVEL);
            }

            if (Gdx.input.justTouched()) {
                game.showMenuScreen();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        square.formSquare(); //send viewport dimensions to the square
    }

    public void touchpadCommand() { //controls the touchpad, aswell as detects if the pause button was touched
        if (Gdx.input.isTouched() && pauseOutline.contains(viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY())))) {
            System.out.println("PAUSED");
            pauseBoolean = false;

        } else if (Gdx.input.justTouched() && touchpad.isExist()) { //enable the touchpad when the screen is touched
            touchpad.getTouchpad().setVisible(false);
            screenPos.set(Gdx.input.getX(), Gdx.input.getY());
            localPos.set(screenPos);
            localPos = touchpad.getTouchpad().getParent().screenToLocalCoordinates(localPos);
            touchpad.getTouchpad().setBounds(localPos.x - touchpad.getTouchpad().getWidth() / 2,
                    localPos.y - touchpad.getTouchpad().getHeight() / 2,
                    Constants.TOUCHPAD_SIZE,
                    Constants.TOUCHPAD_SIZE);

            square.getSquareRect().setX(square.getSquareRect().getX() + touchpad.getTouchpad().getKnobPercentX() * Constants.SQUARE_SPEED);
            square.getSquareRect().setY(square.getSquareRect().getY() + touchpad.getTouchpad().getKnobPercentY() * Constants.SQUARE_SPEED);

            Vector2 stagePos = touchpad.getTouchpad().getStage().screenToStageCoordinates(screenPos);
            fakeTouchDownEvent.setStageX(stagePos.x);
            fakeTouchDownEvent.setStageY(stagePos.y);
            touchpad.getTouchpad().fire(fakeTouchDownEvent);

            touchpad.draw();

        } else if (Gdx.input.isTouched()) {
            square.getSquareRect().setX(square.getSquareRect().getX() + touchpad.getTouchpad().getKnobPercentX() * Constants.SQUARE_SPEED);
            square.getSquareRect().setY(square.getSquareRect().getY() + touchpad.getTouchpad().getKnobPercentY() * Constants.SQUARE_SPEED);
            touchpad.draw();
        }
    }

    public void runGame(float delta) {
        viewport.apply();

        Gdx.gl.glClearColor(Constants.PLAYSCREEN_BG.r, Constants.PLAYSCREEN_BG.g, Constants.PLAYSCREEN_BG.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        batch.begin();
        scoreText.setText(score, String.valueOf(Math.round((square.getSquareWidth() - Constants.SQUARE_INITIAL_SIZE)*Constants.SCORE_MULTIPLIER)), Constants.SCORE_COLOR, 0, 0, true);//Center glyphlayout
        score.draw(batch, scoreText, Gdx.graphics.getWidth() / 2 + scoreText.width / 2, Gdx.graphics.getHeight() / 2 + scoreText.height / 2);
        batch.end();

        batch2.begin();
        batch2.setProjectionMatrix(viewport.getCamera().combined);
        batch2.draw(pauseTexture, viewport.getWorldWidth() - 2 * (pauseTexture.getWidth() * 0.07f),//draw the pause button
                viewport.getWorldHeight() - 2 * (pauseTexture.getHeight() * 0.07f),
                viewport.getWorldHeight() * 0.07f,
                viewport.getWorldHeight() * 0.07f);
        batch2.end();

        //draw the square and the redcircles
        sr.setProjectionMatrix(viewport.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        square.draw(sr, delta);
        square.update(delta);
        redCircles.update(delta, square, touchpad, popSound, soundToggle);
        redCircles.render(sr, square.getSquareWidth());
        touchpadCommand();
        sr.end();
    }

    @Override
    public void pause() {
        pauseBoolean = false;
    }

    @Override
    public void resume() {
        pauseBoolean = false;
    }

    @Override
    public void hide() {
        pauseBoolean = false;
    }

    @Override
    public void dispose() {
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        /*
        System.out.println("screen coords: " + screenX + " Y: " + screenY);
        System.out.println("Square coords X : " + pauseOutline.x + " Y: " + pauseOutline.y);

        if (pauseOutline.contains(viewport.unproject(new Vector2(screenX, screenY)))) {
            System.out.println("PAUSED");
        }
        */
        return true;

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchpad.dispose();
        return true;
    }
}

