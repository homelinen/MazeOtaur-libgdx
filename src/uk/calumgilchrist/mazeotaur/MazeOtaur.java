package uk.calumgilchrist.mazeotaur;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

public class MazeOtaur implements ApplicationListener {
	private OrthographicCamera camera;
	
	private InputController inputCon;
	
	private SpriteBatch batch;
	
	private MazeTemplate maze;
	
	private float cellSize;
	
	private Texture passTex;
	private Texture wallTex;
	
	Player player;
	private Texture playerTex;
	private Cell playerCell;
	
	@Override
	public void create() {
		
		Gdx.app.setLogLevel(Logger.DEBUG);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		camera.update();
		
		inputCon = new InputController();
		
		batch = new SpriteBatch();
		
		int mazeWidth = 9;
		int mazeHeight = 9;
		//Load and draw maze
		maze = new MazeTemplate(mazeWidth, mazeHeight);
		
		cellSize = Gdx.graphics.getHeight() / (float) mazeHeight;
		
		setupMaze();
		
		player = new Player(10, "Larry", new Vector2(0,0));
		setUpPlayerTexture();
		
	}
	
	/**
	 * Go through the given maze and display the passable cells
	 * @param maze
	 */
	public void drawMaze(MazeTemplate maze, SpriteBatch batch) {
		int width = maze.getWidth();
		int height = maze.getHeight();
			
		float yOffset = getScreenOffset();
		
		float xPos = 0;
		float yPos = 0;
		
		for (int x=0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				xPos = x * cellSize;
				yPos = yOffset - (y * cellSize);
				
				if (maze.getCell(x,y).isPassable()) {
					//Draw a white square
					batch.draw(passTex, xPos, yPos, cellSize, cellSize);
				} else {
					//Draw a black square
					batch.draw(wallTex, xPos, yPos, cellSize, cellSize);
				}
			}
		}
	}
	
	/**
	 * Render the player as a circle
	 * @param batch
	 */
	public void drawPlayer(SpriteBatch batch) {
		
		Vector2 playPos = player.getPosition().cpy();
		Vector2 realPos = new Vector2(0, getScreenOffset());
		
		playPos.mul(cellSize);
		realPos.add(playPos.x, - playPos.y);
		
		
		batch.draw(playerTex, realPos.x, realPos.y);
	}
	
	/**
	 * Load the maze and setup the textures for the maze
	 */
	public void setupMaze() {
		FileHandle mazeFile = Gdx.files.internal("openmaze.txt");
		if  (mazeFile.exists()) {
			maze.createMaze(mazeFile);
		} else {
			Gdx.app.error("MazeFile", "Maze file not found!");
		}
		Gdx.app.debug("\nMaze", maze.printMaze());
		
		// Set up maze textures
		passTex = new Texture(Gdx.files.internal("passable.png"));
		wallTex = new Texture(Gdx.files.internal("wall.png"));
	}

	public void setUpPlayerTexture() {
		
		//Should be size of cell
		int circleDiameter = (int) cellSize;
		int circRadius = circleDiameter / 2;
		Pixmap circle = new Pixmap(circleDiameter, circleDiameter, Format.RGBA8888);
		
		circle.setColor(Color.GREEN);
		circle.fillCircle(circRadius, circRadius, circRadius);
		
		playerTex = new Texture(circle);
		circle.dispose();
	}
	
	/**
	 * Get the corresponding offset for the give y co-ord
	 * @param y
	 */
	public float getScreenOffset() {
		return Gdx.graphics.getHeight() - cellSize;
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		passTex.dispose();
		wallTex.dispose();
		playerTex.dispose();
	}

	@Override
	public void render() {		
		
		Gdx.gl.glClearColor(0.5f, 0.1f, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
			//sprite.draw(batch);
			
			drawMaze(maze, batch);
			drawPlayer(batch);
		batch.end();
		//Gdx.app.log("FPS", ""+ Gdx.graphics.getFramesPerSecond());
		
		inputCheck();
		playerMovement();
	}

	/**
	 * Check for input and take actions based on input
	 */
	public void inputCheck() {

		inputCon.poll();
		
		if (inputCon.isKeyPressed(Input.Keys.LEFT)) {
			player.setChangeX(-1);
		} else if (inputCon.isKeyPressed(Input.Keys.RIGHT)) {
			player.setChangeX(1);
		} else if (inputCon.isKeyPressed(Input.Keys.DOWN)) {
			player.setChangeY(1);
		} else if (inputCon.isKeyPressed(Input.Keys.UP)) {
			player.setChangeY(-1);
		}
	}
	
	/**
	 * Controls when the player moves and where the player can move to
	 */
	public void playerMovement() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		playerCell = maze.getCell(player.findNextPos());
		if (playerCell != null && playerCell.isPassable()) {
			player.move(deltaTime);
		} else {
			player.resetMovement();
		}
	}
	
	
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
