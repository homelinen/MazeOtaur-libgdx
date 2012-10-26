package uk.calumgilchrist.mazeotaur;

import java.util.LinkedList;

import uk.calumgilchrist.mazeotaur.ai.AIManager;

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
import com.badlogic.gdx.utils.Logger;

public class MazeOtaur implements ApplicationListener {
	private OrthographicCamera camera;
	
	private InputController inputCon;
	
	private SpriteBatch batch;
	
	private MazeGenerator maze;
	
	private float cellSize;
	
	private Texture passTex;
	private Texture wallTex;
	
	Player player;
	private Texture playerTex;
	private Cell playerCell;
	
	private AIManager aiman;
	private Texture enemyTex;
	
	@Override
	public void create() {
		
		Gdx.app.setLogLevel(Logger.DEBUG);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		camera.update();
		
		inputCon = new InputController();
		
		batch = new SpriteBatch();
		
		cellSize = 32;
		
		int mazeWidth = (int) (Gdx.graphics.getWidth() / cellSize);
		int mazeHeight = (int) (Gdx.graphics.getWidth() / cellSize);
		//Load and draw maze
		maze = new MazeGenerator(mazeWidth, mazeHeight);
		
		setupMaze();
		
		player = new Player(10, "Larry", new Vecter(0,0));
		playerTex = getCircleTexture(Color.GREEN);
		
		aiman = new AIManager();
		setUpAI();
		enemyTex = getCircleTexture(Color.RED);
	}
	
	/**
	 * Go through the given maze and display the passable cells
	 * @param maze
	 */
	public void drawMaze(Maze maze, SpriteBatch batch) {
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
		
		Vecter playPos = player.getPosition().cpy();
		Vecter realPos = getRealPosition(playPos);
		
		batch.draw(playerTex, realPos.x, realPos.y);
	}
	
	/**
	 * Render the player as a circle
	 * @param batch
	 */
	public void drawEnemies(SpriteBatch batch) {
		
		Vecter enemyPos;
		Vecter realPos;
		
		LinkedList<Enemy> enemies = (LinkedList<Enemy>) aiman.getCreatures();
		for (Enemy enemy: enemies) {
			//TODO: This is identical to player drawing
			enemyPos = enemy.getPosition().cpy();

			realPos = getRealPosition(enemyPos);
			batch.draw(enemyTex, realPos.x, realPos.y);
		}
	}
	
	/**
	 * Transform a cell position into a screen position
	 * @param cellPos Position in cell co-ordinates
	 * @return A screen co-ordinate
	 */
	public Vecter getRealPosition(Vecter cellPos) {
		
		Vecter realPos = new Vecter(0, getScreenOffset());
		
		cellPos.mul(cellSize);
		realPos.add(cellPos.x, - cellPos.y);
		
		return realPos;
	}
	
	/**
	 * Load the maze and setup the textures for the maze
	 */
	public void setupMaze() {
		FileHandle mazeFile = Gdx.files.internal("openmaze.txt");
		if  (mazeFile.exists()) {
			maze.createMaze();
		} else {
			Gdx.app.error("MazeFile", "Maze file not found!");
		}
		Gdx.app.debug("\nMaze", maze.printMaze());
		
		// Set up maze textures
		passTex = new Texture(Gdx.files.internal("passable.png"));
		wallTex = new Texture(Gdx.files.internal("wall.png"));
	}

	/**
	 * Create a coloured circle texture
	 * @param color Color of the circle
	 * @return new Texture with a circle
	 */
	public Texture getCircleTexture(Color color) {
		//Should be size of cell
		int circleDiameter = (int) cellSize;
		int circRadius = circleDiameter / 2;
		Pixmap circle = new Pixmap(circleDiameter, circleDiameter, Format.RGBA8888);
		
		circle.setColor(color);
		circle.fillCircle(circRadius, circRadius, circRadius);
		
		Texture blankTex = new Texture(circle);
		circle.dispose();
		
		return blankTex;
	}
	
	/**
	 * Add enemies and initialise their patrol paths
	 */
	public void setUpAI() {
		//TODO: Automate this
		int health = 10;
		String name = "Minny";
		Vecter startPos = maze.findPassableCell(new Vecter(10, 10));
		float speed = 0.5f;
		int lineOfSight = 5;
		
		Vecter goalPoint = new Vecter(5,10);
		
		Minotaur min = new Minotaur(health, name, startPos, speed, lineOfSight);
		aiman.addCreature(min, maze, maze.findPassableCell(goalPoint));
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
			drawEnemies(batch);
		batch.end();
		//Gdx.app.log("FPS", ""+ Gdx.graphics.getFramesPerSecond());
		
		inputCheck();
		playerMovement();
		aiman.update(Gdx.graphics.getDeltaTime(), player.getPosition(), maze);
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
