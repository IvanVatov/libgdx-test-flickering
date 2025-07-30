package com.example.testflickering;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;
    private Table window;
    private Label fpsLabel;
    private Label frameLabel;
    private Label deltaLabel;
    private Label milisLabel;

    private long lastFrameId = 0;

    private long skippedFrames = 0;

    @Override
    public void create() {
        stage = new Stage(new FitViewport(640, 480));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        fpsLabel = new Label("", skin);
        frameLabel = new Label("", skin);
        deltaLabel = new Label("", skin);
        milisLabel = new Label("", skin);

        window = new Window("", skin);
        window.defaults().pad(4f);
        window.add(fpsLabel).pad(4f).row();
        window.add(deltaLabel).pad(4f);
        window.pack();

        window.setPosition(MathUtils.roundPositive(stage.getWidth() / 2f - window.getWidth() / 2f),
            MathUtils.roundPositive(stage.getHeight() / 2f - window.getHeight() / 2f));
        window.addAction(Actions.sequence(Actions.alpha(0f), Actions.fadeIn(1f)));

        float centerX = 320;
        float centerY = 240;
        float radius = 100;
        float speed = 2f;         // radians per second
        float startAngle = 0f;

        CircularMotionAction circularAction = new CircularMotionAction(centerX, centerY, radius, speed, startAngle);
        window.addAction(circularAction);


        Table table = new Table();
        table.pad(16.0f);
        table.align(Align.left);
        table.setFillParent(true);

        table.add(fpsLabel).prefWidth(192.0f);
        table.row();

        table.add(frameLabel).prefWidth(192.0f);
        table.row();

        table.add(deltaLabel).prefWidth(192.0f);
        table.row();

        table.add(milisLabel).prefWidth(192.0f);
        table.row();

        window.add(table);

        stage.addActor(window);
        window.pack();
    }

    @Override
    public void render() {

        float delta = Gdx.graphics.getDeltaTime();
        long frameId = Gdx.graphics.getFrameId();


        if (frameId > lastFrameId + 1) {
            skippedFrames += frameId - lastFrameId + 1;
            System.out.println("Total skipped frames: " + skippedFrames);
        }

        lastFrameId = frameId;

        fpsLabel.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
        frameLabel.setText("Frame: " + frameId);
        deltaLabel.setText("Delta: " + delta);
        milisLabel.setText("Millis: " + System.currentTimeMillis());
        stage.act(delta);

        ScreenUtils.clear(0f, 192f, 128f, 1f);

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
