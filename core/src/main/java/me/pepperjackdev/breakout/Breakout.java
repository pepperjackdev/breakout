package me.pepperjackdev.breakout;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Breakout extends ApplicationAdapter {
    private ShapeRenderer shape;
    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks = new ArrayList<Brick>();

    @Override
    public void create() {
        shape = new ShapeRenderer();
        ball = new Ball(150, 200, 15, 6, 5, Color.WHITE);
        paddle = new Paddle(10, 10, 120, 10, Color.WHITE);
        bricks = new ArrayList<>();
        bricks = generateBricks(20, 15);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        paddle.update();
        paddle.draw(shape);
        ball.update(paddle, bricks);
        ball.draw(shape);
        bricks.removeIf(brick -> ball.collidesWithBrick(brick));
        bricks.forEach(brick -> brick.draw(shape));
        shape.end();
    }

    public List<Brick> generateBricks(int height, int spacing) {
        List<Brick> bricks = new ArrayList<>();

        int numberOfRows = (Gdx.graphics.getHeight() / 2) / (height + spacing);
        for (int row = 0; row < numberOfRows; row++) {
            for (int xOffset = spacing; xOffset < Gdx.graphics.getWidth(); xOffset += spacing) {
                int width = new Random().nextInt(20, 50);

                if (width > Gdx.graphics.getWidth() - xOffset - spacing) {
                    break;
                }

                bricks.add(new Brick(xOffset, Gdx.graphics.getHeight() - (2 * spacing + (row * (height + spacing))), width, height, Color.WHITE));
                xOffset += width;
            }
        }

        return bricks;
    }
}
