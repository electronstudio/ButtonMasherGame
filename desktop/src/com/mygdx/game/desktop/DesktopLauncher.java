package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.mygdx.game.ButtonMasherGame;
import uk.me.fantastic.retro.App;
import uk.me.fantastic.retro.SimpleApp;
import uk.me.fantastic.retro.desktop.DesktopCallback;
import uk.me.fantastic.retro.desktop.DesktopLogger;
import uk.me.fantastic.retro.games.SimpleGameFactory;


public class DesktopLauncher {
	public static void main (String[] arg) {
		DesktopCallback callback = new DesktopCallback();
		SimpleGameFactory factory = new SimpleGameFactory("Button Masher", ButtonMasherGame.class);
		App app = new SimpleApp(callback, "Button Masher", factory, new DesktopLogger(), null);
		new LwjglApplication(app, callback.getConfig());
	}
}
