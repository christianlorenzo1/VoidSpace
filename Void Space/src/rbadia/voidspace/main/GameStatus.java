package rbadia.voidspace.main;

/**
 * Container for game flags and/or status variables.
 */
public class GameStatus {
	// game flags
	private boolean gameStarted = false;
	private boolean gameStarting = false;
	private boolean gameOver = false;
	private boolean nextlevel = false;

	// status variables
	private boolean newShip;
	private boolean newAsteroid; 
	private boolean newAsteroid2;  
	private boolean newAsteroid3;
	private boolean newAsteroid4;  
	private boolean newAsteroid5;
	private boolean EnemyBullet;
	

	private long asteroidsDestroyed = 0;
	private int shipsLeft;
	private boolean newEnemy;
	private int shieldLeft=5;
	
	public GameStatus(){
		
	}
	
	/**
	 * Indicates if the game has already started or not.
	 * @return if the game has already started or not
	 */
	public synchronized boolean isGameStarted() {
		return gameStarted;
	}
	
	public synchronized void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}
	
	/**
	 * Indicates if the game is starting ("Get Ready" message is displaying) or not.
	 * @return if the game is starting or not.
	 */
	public synchronized boolean isGameStarting() {
		return gameStarting;
	}
	
	public synchronized void setGameStarting(boolean gameStarting) {
		this.gameStarting = gameStarting;
	}
	
	/**
	 * Indicates if the game has ended and the "Game Over" message is displaying.
	 * @return if the game has ended and the "Game Over" message is displaying.
	 */
	public synchronized boolean isGameOver() {
		return gameOver;
	}
	
	public synchronized void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
/*
 * Crea el proximo nivel del juego.	
 */
	public synchronized boolean getnextlevel() {
		return nextlevel;
	}
	
	public synchronized void setNextLevel(boolean nextlevel) {
		this.nextlevel = nextlevel;
	}
	
	/**
	 * Indicates if a new ship should be created/drawn.
	 * @return if a new ship should be created/drawn
	 */
	public synchronized boolean isNewShip() {
		return newShip;
	}

	public synchronized void setNewShip(boolean newShip) {
		this.newShip = newShip;
	}

	/**
	 * Indicates if a new asteroid should be created/drawn.
	 * @return if a new asteroid should be created/drawn
	 */
	public synchronized boolean isNewAsteroid() {
		return newAsteroid;
	}
	public synchronized boolean isNewAsteroid2() {
		return newAsteroid2;
	}
	public synchronized boolean isNewAsteroid3() {
		return newAsteroid3;
	}
	public synchronized boolean isNewAsteroid4() {
		return newAsteroid4;
	}
	public synchronized boolean isNewAsteroid5() {
		return newAsteroid5;
	}
	public synchronized boolean isNewEnemyBullet() {
		return EnemyBullet;
	}
	public synchronized boolean isNewEnemy() {
		return newEnemy;
	}

	public synchronized void setNewAsteroid(boolean newAsteroid) {
		this.newAsteroid = newAsteroid;
	}

	public synchronized void setNewAsteroid2(boolean newAsteroid) {
		this.newAsteroid2 = newAsteroid;
	}
	public synchronized void setNewAsteroid3(boolean newAsteroid) {
		this.newAsteroid3 = newAsteroid;
	}
	public synchronized void setNewAsteroid4(boolean newAsteroid) {
		this.newAsteroid4 = newAsteroid;
	}
	public synchronized void setNewAsteroid5(boolean newAsteroid) {
		this.newAsteroid5 = newAsteroid;
	}
	public synchronized void setNewEnemyBullet(boolean newAsteroid) {
		this.EnemyBullet = newAsteroid;
	}
	/**
	 * Returns the number of asteroid destroyed. 
	 * @return the number of asteroid destroyed
	 */
	public synchronized long getAsteroidsDestroyed() {
		return asteroidsDestroyed;
	}

	public synchronized void setAsteroidsDestroyed(long asteroidsDestroyed) {
		this.asteroidsDestroyed = asteroidsDestroyed;
	}

	/**
	 * Returns the number ships/lives left.
	 * @return the number ships left
	 */
	public synchronized int getShipsLeft() {
		return shipsLeft;
	}
	

	public synchronized void setShipsLeft(int shipsLeft) {
		this.shipsLeft = shipsLeft;
	}

	public synchronized void setNewEnemy(boolean enemy) {
		this.newEnemy = enemy;
	}

	public int getShieldLeft() {
		return shieldLeft;
	}

	public void setShieldLeft(int shieldLeft) {
		this.shieldLeft = shieldLeft;
	}


}
