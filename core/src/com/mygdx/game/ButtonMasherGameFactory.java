package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;
import uk.me.fantastic.retro.AbstractGameFactory;
import uk.me.fantastic.retro.Game;
import uk.me.fantastic.retro.menu.MultiChoiceMenuItem;
import uk.me.fantastic.retro.screens.GameSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** To run your game standalone you won't need to define a Factory, just
 *  use an instance of SimpleGameFactory.
 *  However if you want to make a plugin to RetroWar, you need
 *  something like this...
 */

public class ButtonMasherGameFactory extends AbstractGameFactory {

    /** If you're making a plugin you NEED a no-arg constructor
     *
     */
    public ButtonMasherGameFactory(){
        this("Button Masher!", null);
    }

    /** Java requires this constructor too, not sure why because Kotlin does not!
     */
    public ButtonMasherGameFactory(String name, List<String> levels) {
        super(name, levels);
    }


    /** description
     *
     * @return description to show on game select screen
     */
    @NotNull
    @Override
    public String getDescription() {
        return "A game about mashing Buttons";
    }

    /** logo for your game
     *
     * @return image to show on game select screen
     */
    @NotNull
    @Override
    public Texture getImage() {
        return new Texture(Gdx.files.internal("mods/ButtonMasherGame/baloon.png"));
    }


    /** create
     * @return an instance of your game
     */
    @NotNull
    @Override
    public Game create(GameSession gameSession) {
        return new ButtonMasherGame(gameSession, gameLength);
    }


    //////////////////////////////////////////////////////////////////////////////////////////
    // the rest of this file is only required if you want Retrowar to display a unique settings screen
    //////////////////////////////////////////////////////////////////////////////////////////

    /** This is optional (hohoho), only needed if you want to display a unique settings screen */
    @NotNull
    @Override
    public List<MultiChoiceMenuItem> getOptions() {
        return  options;
    }

    /** A menu that will be displayed on the settings screen by
     *  out getOptions(). If you don't have getOptions() you
     *  dont need this.
     */
    private List<MultiChoiceMenuItem> options = Arrays.asList(
            new MultiChoiceMenuItem("Game length: ",
                    this::menuSelect,
                    Arrays.asList("SHORT","LONG"),
                    Arrays.asList(10, 25))
    );

    /** Used to store what option is currently chosen for how long game should be.
     *  If you dont have options or implement them differently you dont need this
     */
    private int gameLength = 10;

    /** Used by MultiChoiceMenuItem to be called when menu is selected to update
     *  selecttion.
     *  If you dont have options or implement them differently you dont need this
     */
    private Unit menuSelect(String name, Integer number) {
        System.out.println("Selected "+name+" gamelength which is "+number+" seconds");
        gameLength = number;
        return Unit.INSTANCE;
    }

    /** This is optional, only needed if you want to provide default settings
     * for when your game is run as part of a tournament and so wont get a
     * chance to display its settings screen */
    @NotNull
    @Override
    public Game createWithDefaultSettings(GameSession session) {
        return new ButtonMasherGame(session, 10);
    }


}
