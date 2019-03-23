package com.flappyago.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.flappyago.game.FlappyAgo;

import java.util.Random;

public class Ago {
    public boolean newStart;
    private static final int GRAVITY = -15;
    private float movement;
    private int score;

    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;

    private Texture texture;
    private Animation agoAnimation;

    private Sound fly;

    public Ago(int x, int y) {
        newStart = true;
        position = new Vector3(x, y, 0);  // Ago's starting point
        velocity = new Vector3(0, 0, 0);  // before starting speed is 0
        movement = 100;
        score = 0;
        texture = new Texture("ago_animation.png");
        agoAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);

        bounds = new Rectangle(x, y, texture.getWidth() / 3, texture.getHeight());

        fly = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    public void update(float dt) {
        if (newStart) {
            Random random = new Random();
            String number = String.valueOf(random.nextInt(6));
            FlappyAgo.playMusic = Gdx.audio.newMusic(Gdx.files.internal("music" + number + ".mp3"));
            newStart = false;
        }
        FlappyAgo.playMusic.play();
        agoAnimation.update(dt);
        movement += 0.03;

        if (0 < position.y) {
            velocity.add(0, GRAVITY, 0);
        }

        velocity.scl(dt);
        position.add(movement * dt, velocity.y, 0);

        if (position.y < 0) {
            position.y = 0;
        }

        velocity.scl(1 / dt);
        bounds.setPosition(position.x, position.y);
    }

    public void jump() {
        velocity.y = 250;
        fly.play(FlappyAgo.masterVolume);
    }

    public void dispose() {
        texture.dispose();
        fly.dispose();
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return agoAnimation.getFrame();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getScore() {
        return score;
    }
}
