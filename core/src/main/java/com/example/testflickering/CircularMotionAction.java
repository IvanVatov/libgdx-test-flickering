package com.example.testflickering;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;

public class CircularMotionAction extends Action {
    private float centerX, centerY;
    private float radius;
    private float speed; // in radians per second
    private float angle; // current angle

    public CircularMotionAction(float centerX, float centerY, float radius, float speed, float startAngle) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.speed = speed;
        this.angle = startAngle;
    }

    @Override
    public boolean act(float delta) {
        angle += speed * delta;
        if (angle > MathUtils.PI2) angle -= MathUtils.PI2;

        float x = centerX + radius * MathUtils.cos(angle) - actor.getWidth() / 2f;
        float y = centerY + radius * MathUtils.sin(angle) - actor.getHeight() / 2f;

        actor.setPosition(x, y);

        return false; // false = continue indefinitely
    }
}
