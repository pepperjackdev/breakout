package me.pepperjackdev.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.List;

public class Ball {
    private int x, y;
    private int radius;
    private int xSpeed, ySpeed;
    private Color color;

    public Ball(int x, int y, int radius, int xSpeed, int ySpeed, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.color = color;
    }

    public void draw(ShapeRenderer shape) {
        shape.setColor(color);
        shape.circle(x, y, radius);
    }

    public void update(Paddle paddle, List<Brick> bricks) {
        x += xSpeed;
        y += ySpeed;

        if (x - radius < 0 || x + radius > Gdx.graphics.getWidth()) {
            xSpeed *= -1;
        }

        if (y + radius > Gdx.graphics.getHeight()) {
            ySpeed *= -1;
        }

        if (collidesWithPaddle(paddle)) {
            ySpeed = Math.abs(ySpeed);
        }

        if (bricks.stream().anyMatch(this::collidesWithBrick)) {
            ySpeed = -Math.abs(ySpeed);
        }
    }

    public boolean collidesWithPaddle(Paddle paddle) {
        return (y - radius < paddle.getY() + paddle.getHeight() && y + radius > paddle.getY()) && (
            (x > paddle.getX() && x < paddle.getX() + paddle.getWidth())
            || (paddle.getX() > x - radius && paddle.getX() < x + radius)
            || (paddle.getX() + paddle.getWidth() > x - radius && paddle.getX() + paddle.getWidth() < x + radius)
        );
    }

    public boolean collidesWithBrick(Brick brick) {
        return (y + radius > brick.getY() - brick.getHeight()) && (
            (x > brick.getX() && x < brick.getX() + brick.getWidth())
                || (brick.getX() > x - radius && brick.getX() < x + radius)
                || (brick.getX() + brick.getWidth() > x - radius && brick.getX() + brick.getWidth() < x + radius)
        );
    }
}
