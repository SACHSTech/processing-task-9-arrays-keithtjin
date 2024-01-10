import processing.core.PApplet;

public class Sketch extends PApplet {

  int numSnowflakes = 100;
  float[] snowflakeX = new float[numSnowflakes];
  float[] snowflakeY = new float[numSnowflakes];
  float[] snowflakeSpeed = new float[numSnowflakes];
  int playerX, playerY;
  int lives = 3;
  boolean[] ballHideStatus = new boolean[numSnowflakes];

  /**
   * Called once at the beginning of execution, put your size call here
   */
  public void settings() {
    size(400, 400);
  }

  /**
   * Called once at the beginning of execution. Add initial set up
   * values here i.e background, stroke, fill, etc.
   */
  public void setup() {
    background(210, 255, 173);
    initializeSnowflakes();
    playerX = width / 2;
    playerY = height - 20;
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
    background(210, 255, 173);

    moveSnowflakes();
    drawSnowflakes();

    movePlayer();
    drawPlayer();

    drawLives();

    checkCollisions();
    checkGameEnd();
  }

  /**
   * Initializes the snowflake arrays with random positions and speeds
   */
  private void initializeSnowflakes() {
    for (int i = 0; i < numSnowflakes; i++) {
      snowflakeX[i] = random(width);
      snowflakeY[i] = random(height);
      snowflakeSpeed[i] = random(1, 5);
      ballHideStatus[i] = false;
    }
  }

  /**
   * Moves the snowflakes down the screen
   */
  private void moveSnowflakes() {
    for (int i = 0; i < numSnowflakes; i++) {
      snowflakeY[i] += snowflakeSpeed[i];
      if (snowflakeY[i] > height) {
        snowflakeY[i] = 0;
      }
    }
  }

  /**
   * Draws the snowflakes on the screen
   */
  private void drawSnowflakes() {
    fill(255);
    noStroke();
    for (int i = 0; i < numSnowflakes; i++) {
      if (!ballHideStatus[i]) {
        ellipse(snowflakeX[i], snowflakeY[i], 10, 10);
      }
    }
  }

  /**
   * Moves the player circle based on keyboard input
   */
  private void movePlayer() {
    if (keyPressed) {
      if (keyCode == LEFT) {
        playerX -= 5;
      } else if (keyCode == RIGHT) {
        playerX += 5;
      } else if (keyCode == DOWN) {
        playerY += 5;
      } else if (keyCode == UP) {
        playerY -= 5;
      }
    }
  }

  /**
   * Draws the player circle on the screen
   */
  private void drawPlayer() {
    fill(0, 0, 255);
    noStroke();
    ellipse(playerX, playerY, 20, 20);
  }

  /**
   * Draws the remaining lives on the screen
   */
  private void drawLives() {
    fill(255);
    noStroke();
    for (int i = 0; i < lives; i++) {
      rect(width - 20 - i * 25, 10, 20, 20);
    }
  }

  /**
   * Checks collisions between snowflakes and the player
   */
  private void checkCollisions() {
    for (int i = 0; i < numSnowflakes; i++) {
      if (!ballHideStatus[i] && dist(playerX, playerY, snowflakeX[i], snowflakeY[i]) < 15) {
        ballHideStatus[i] = true;
        lives--;
      }
    }
  }

  /**
   * Checks if the game has ended
   */
  private void checkGameEnd() {
    if (lives <= 0) {
      background(255);
      fill(0);
      textSize(32);
      text("Game Over", width / 2 - 80, height / 2);
    }
  }

  /**
   * Called when a mouse click occurs
   */
  public void mouseClicked() {
    for (int i = 0; i < numSnowflakes; i++) {
      if (dist(mouseX, mouseY, snowflakeX[i], snowflakeY[i]) < 15) {
        ballHideStatus[i] = true;
      }
    }
  }
}
