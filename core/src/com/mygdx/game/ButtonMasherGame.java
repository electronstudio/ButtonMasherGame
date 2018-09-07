package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import org.jetbrains.annotations.NotNull;
import uk.me.fantastic.retro.Player;
import uk.me.fantastic.retro.SimpleGame;
import uk.me.fantastic.retro.screens.GameSession;


/**
 * A simple RetroWar minigame.  4 players max.  Fastest button masher wins.
 */

public class ButtonMasherGame extends SimpleGame {
    private int gameLength;                             // Lenth of game in seconds
    private float time;                   // Time remaining in game
    private String message = "";                        // We will print this to screen
    private final boolean[] buttonMashed = new boolean[4];   // Remember if button was held down
    private final Texture baloon = new Texture(Gdx.files.internal("mods/ButtonMasherGame/baloon.png"));
    private final Sprite[] sprites = {                         // An array of 4 sprites
            new Sprite(baloon), new Sprite(baloon),
            new Sprite(baloon), new Sprite(baloon)
    };

    private final Texture background = new Texture(Gdx.files.internal("mods/ButtonMasherGame/sky4.png"));

    private static final BitmapFont font =
            new BitmapFont(Gdx.files.internal("mods/ButtonMasherGame/english.fnt"));   // for
    // drawing text

    public ButtonMasherGame(GameSession session) {               // Constructor (required)
      this(session, 10);
    } // Required constructor

    public ButtonMasherGame(GameSession session, int gameLength){   // Not required but lets us set gamelength
        super(session, 256, 320, font, font, false);   // width and height of screen in pixels
        for (int i = 0; i < sprites.length; i++) {                      // Set initial positions for the sprites
            sprites[i].setPosition(36 + i * 56, 33);
        }
        this.gameLength=gameLength;
        time=gameLength+5;              // 5 seconds to get ready before start
    }

    @Override
    public void doLogic(float deltaTime) {  // Called automatically every frame
        doTimer(deltaTime);                 // deltaTime is time passed since last frame
        if (time > gameLength) {  // Game has three phases, current phase depends on how much time is left
            message = "\nPress A or SPACE to join game\n\nGET READY "; // ready phase
        } else if (time > 0) {
            play(deltaTime);                                        // playing phase
        } else {    // time <= 0
            message = "SCORES\n\n" + simpleHighScoreTable();        // end phase
        }
    }

    private void doTimer(float deltaTime) {
        if (getPlayers().size() >= 1) {         // Dont start timing until there are at least one players
            time -= deltaTime;                  // Subtract the time since last frame from the time remaining
        }
        if (time < -7) {
            getSession().findWinners().forEach(winner -> winner.incMetaScore(1));
            gameover();                         // end the game
        }
    }


    private void play(float deltaTime) {
        message = "MASH BUTTON " + Float.valueOf(time).intValue();
        for (int i = 0; i < Math.min(getPlayers().size(), 4); i++) {  // max of 4 players
            Sprite sprite = sprites[i];
            Player player = getPlayers().get(i);
            sprite.setY(sprite.getY() - deltaTime * 20);            // move sprite downwards
            if (player.getInput().getA()) {              // if button is pressed
                if (!buttonMashed[i]) {                             // prevent holding down button to cheat
                    sprite.setY(sprite.getY() + deltaTime * 250);   // move sprite upwards
                    buttonMashed[i] = true;
                }
            } else {
                buttonMashed[i] = false;
            }
            if (sprite.getY() < 33) sprite.setY(33);                 // dont fall through floor
            player.setScore((int) (sprite.getY()-33));  // score = height

        }
    }

    @Override
    public void doDrawing(@NotNull Batch batch) {            // called automatically every frame
        Gdx.gl.glClearColor(0, 0, 0, 1);     // clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.draw(background, 0, 0);
        drawPlayers(batch);
        font.draw(batch, message, 0, 212, 256, Align.center, false);
    }


    private void drawPlayers(Batch batch) {                 // draw one sprite for each Player
        for (int i = 0; i <  Math.min(getPlayers().size(), 4); i++) {  // max of 4 players
            Sprite sprite = sprites[i];
            Player player = getPlayers().get(i);
            sprite.draw(batch);
            font.draw(batch, player.getName(), sprite.getX() - 15, 8);
        }
    }

    @Override
    public void dispose() {
        background.dispose();
        baloon.dispose();
    }

    // On mobile you probably want to override these methods too...

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }


}
