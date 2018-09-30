package rbadia.voidspace.main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.Ship;
import rbadia.voidspace.sounds.SoundManager;

/**
 * Main game screen. Handles all game graphics updates and some of the game logic.
 */
public class GameScreen extends JPanel {
	private static final long serialVersionUID = 1L;

	private BufferedImage backBuffer;
	private Graphics2D g2d;

	private static final int NEW_SHIP_DELAY = 500;
	private static final int NEW_ASTEROID_DELAY = 500;

	private long lastShipTime;
	private long lastAsteroidTime;

	private Rectangle asteroidExplosion;
	private Rectangle asteroidExplosion2;
	private Rectangle asteroidExplosion3;
	//	private Rectangle asteroidExplosion4;
	//	private Rectangle asteroidExplosion5;
	//	private Rectangle EnemyBulletExplosion;

	private Rectangle shipExplosion;
	private Rectangle enemyExplosion;

	private JLabel shipsValueLabel;
	private JLabel destroyedValueLabel;
	private JProgressBar shieldprogress;

	private Random rand;

	private Font originalFont;
	private Font bigFont;
	private Font biggestFont;

	private GameStatus status;
	private SoundManager soundMan;
	private GraphicsManager graphicsMan;
	private GameLogic gameLogic;


	/**
	 * This method initializes 
	 * 
	 */
	public GameScreen() {
		super();
		// initialize random number generator
		rand = new Random();

		initialize();

		// init graphics manager
		graphicsMan = new GraphicsManager();

		// init back buffer image
		backBuffer = new BufferedImage(500, 400, BufferedImage.TYPE_INT_RGB);
		g2d = backBuffer.createGraphics();
	}

	/**
	 * Initialization method (for VE compatibility).
	 */
	private void initialize() {
		// set panel properties
		this.setSize(new Dimension(500, 300));
		this.setPreferredSize(new Dimension(500, 400));
		this.setBackground(Color.BLACK);
	}

	/**
	 * Update the game screen.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// draw current backbuffer to the actual game screen
		g.drawImage(backBuffer, 0, 0, this);
	}

	/**
	 * Update the game screen's backbuffer image.
	 */
	public void updateScreen(){
		Ship ship = gameLogic.getShip();
		Asteroid asteroid = gameLogic.getAsteroid();
		Asteroid asteroid2 = gameLogic.getAsteroid2();
		Asteroid asteroid3 = gameLogic.getAsteroid3();
		//		Asteroid asteroid4 = gameLogic.getAsteroid4();
		//		Asteroid asteroid5 = gameLogic.getAsteroid5();
		Asteroid enemybullet = gameLogic.getEnemyBullet();
		EnemyShip enemy=gameLogic.getEnemy();
		List<Bullet> bullets = gameLogic.getBullets();

		// set orignal font - for later use
		if(this.originalFont == null){
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		// erase screen
		g2d.setPaint(Color.BLACK);
		g2d.fillRect(0, 0, getSize().width, getSize().height);

		// draw 50 random stars
		drawStars(50);

		// if the game is starting, draw "Get Ready" message
		if(status.isGameStarting()){
			drawGetReady();
			return;
		}
		//Empieza next Level
		if(status.getAsteroidsDestroyed() == 5){
			drawnextLevel();
		}
		else if (status.getAsteroidsDestroyed() >=6 && status.getAsteroidsDestroyed() <10){
			if(!status.isNewAsteroid()){
				// draw the asteroid until it reaches the bottom of the screen
				if(asteroid.getY() + asteroid.getSpeed() < this.getHeight()){
					asteroid2.setSpeed(4);
					asteroid2.translate(1, asteroid2.getSpeed());
				}


				else{
					asteroid.setLocation(rand.nextInt(getWidth() - asteroid.width), 0);
					asteroid2.setLocation(rand.nextInt(getWidth() - asteroid2.width), 0);
					enemy.setLocation(rand.nextInt(getWidth() - enemy.width), 0);

				}

			}

		}
		else if(status.getAsteroidsDestroyed() ==10) {
			drawnextLevel2();
		}
		else if(status.getAsteroidsDestroyed() >=11) {
			if(!status.isNewAsteroid()){
				// draw the asteroid until it reaches the bottom of the screen
				if(asteroid.getY() + asteroid.getSpeed() < this.getHeight()){
					asteroid2.setSpeed(4);
					asteroid2.translate(1, asteroid2.getSpeed());
					enemybullet.translate(0, 5);				



				}
				else{
					asteroid.setLocation(rand.nextInt(getWidth() - asteroid.width), 0);
					asteroid2.setLocation(rand.nextInt(getWidth() - asteroid2.width), 0);
					enemy.setLocation(rand.nextInt(getWidth() - enemy.width), 0);

				}

			} }

		// if the game is over, draw the "Game Over" message
		if(status.isGameOver()){
			// draw the message
			drawGameOver();

			long currentTime = System.currentTimeMillis();
			// draw the explosions until their time passes
			if((currentTime - lastAsteroidTime) < NEW_ASTEROID_DELAY){
				//No se que hace este if
			}
			if((currentTime - lastShipTime) < NEW_SHIP_DELAY){
				graphicsMan.drawShipExplosion(shipExplosion, g2d, this);
			}
			return;
		}

		// the game has not started yet
		if(!status.isGameStarted()){
			// draw game title screen
			initialMessage();
			return;
		}

		//Set SHIELD Value
		///////////////////////
		if(status.getShieldLeft()==5){
			shieldprogress.setForeground(Color.GREEN);
			shieldprogress.setValue(100);
		}
		else if(status.getShieldLeft()==4){
			shieldprogress.setForeground(Color.ORANGE);
			shieldprogress.setValue(50);
		}
		else if (status.getShieldLeft()==3){
			shieldprogress.setForeground(Color.RED);
			shieldprogress.setValue(10);
		}
		else if(status.getShieldLeft()==2) {
			shieldprogress.setValue(0);
			status.setShipsLeft(2);
			}
		else if(status.getShieldLeft()==1){
				status.setShipsLeft(1);
			}
		else if(status.getShieldLeft()==0){
			status.setShipsLeft(0);
		}
	
		//////////////////////

		//EnemyBullets
		if(!status.isNewEnemyBullet()){
			// draw the asteroid until it reaches the bottom of the screen
			enemybullet.setSize(8, 8); 
			if(enemybullet.getY() + enemybullet.getSpeed() < this.getHeight()){
				enemybullet.translate(0, 2);				
				graphicsMan.drawEnemyBullet(enemybullet, g2d, this);
			}

			else{
				enemybullet.setLocation(enemy.x+5,enemy.y);
			}
		}
		else{
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
				// draw a new asteroid
				lastAsteroidTime = currentTime;
				status.setNewEnemyBullet(false);
				enemybullet.setLocation(enemy.x+5, enemy.y);
			}
			else{
				// draw explosion
				enemybullet.setLocation(enemy.x+5, enemy.y);

			}
		}       
		// draw asteroid
		if(!status.isNewAsteroid()){
			// draw the asteroid until it reaches the bottom of the screen
			if(asteroid.getY() + asteroid.getSpeed() < this.getHeight()){
				asteroid.translate(0, asteroid.getSpeed());
				graphicsMan.drawAsteroid(asteroid, g2d, this);
			}

			else{
				asteroid.setLocation(rand.nextInt(getWidth() - asteroid.width), 0);
			}
		}
		else{
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
				// draw a new asteroid
				lastAsteroidTime = currentTime;
				status.setNewAsteroid(false);
				asteroid.setLocation(rand.nextInt(getWidth() - asteroid.width), 0);
			}
			else{
				// draw explosion
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
		}                      

		//draw asteroid 2
		if(!status.isNewAsteroid2()){
			// draw the asteroid until it reaches the bottom of the screen
			if(asteroid2.getY() + asteroid2.getSpeed() < this.getHeight()){
				asteroid2.translate(0, asteroid2.getSpeed());
				graphicsMan.drawAsteroid2(asteroid2, g2d, this);
			}

			else{
				asteroid2.setLocation(rand.nextInt(getWidth() - asteroid2.width), 0);
			}
		}
		else{
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
				// draw a new asteroid
				lastAsteroidTime = currentTime;
				status.setNewAsteroid2(false);
				asteroid2.setLocation(rand.nextInt(getWidth() - asteroid2.width), 0);
			}
			else{
				// draw explosion
				graphicsMan.drawAsteroidExplosion2(asteroidExplosion2, g2d, this);
			}
		}
		//Draw Asteroid 3
		if(!status.isNewAsteroid3()){				asteroid3.setSize(60,60);

		// draw the asteroid until it reaches the bottom of the screen
		if(asteroid3.getY() + asteroid3.getSpeed() < this.getHeight()){
			asteroid3.translate(0, asteroid3.getSpeed());
			graphicsMan.drawAsteroid3(asteroid3, g2d, this);
		}

		else{
			asteroid3.setLocation(rand.nextInt(getWidth() - asteroid3.width), 0);
		}
		}
		else{
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
				// draw a new asteroid
				lastAsteroidTime = currentTime;
				status.setNewAsteroid3(false);
				asteroid3.setLocation(rand.nextInt(getWidth() - asteroid3.width), 0);

			}
			else{
				// draw explosion
				graphicsMan.drawAsteroidExplosion3(asteroidExplosion3, g2d, this);

			}
		}
		////Draw asteroid 4
		//		if(!status.isNewAsteroid4()){
		//			// draw the asteroid until it reaches the bottom of the screen
		//			if(asteroid4.getY() + asteroid4.getSpeed() < this.getHeight()){
		//				asteroid4.translate(0, asteroid4.getSpeed());
		//				graphicsMan.drawAsteroid4(asteroid4, g2d, this);
		//			}
		//
		//			else{
		//				asteroid4.setLocation(rand.nextInt(getWidth() - asteroid3.width), 0);
		//			}
		//		}
		//		else{
		//			long currentTime = System.currentTimeMillis();
		//			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
		//				// draw a new asteroid
		//				lastAsteroidTime = currentTime;
		//				status.setNewAsteroid4(false);
		//				asteroid4.setLocation(rand.nextInt(getWidth() - asteroid3.width), 0);
		//			}
		//			else{
		//				// draw explosion
		//				graphicsMan.drawAsteroidExplosion4(asteroidExplosion4, g2d, this);
		//			}
		//		}
		////Draw asteroid 5
		//		if(!status.isNewAsteroid5()){
		//			// draw the asteroid until it reaches the bottom of the screen
		//			if(asteroid5.getY() + asteroid5.getSpeed() < this.getHeight()){
		//				asteroid5.translate(0, asteroid5.getSpeed());
		//				graphicsMan.drawAsteroid2(asteroid5, g2d, this);
		//			}
		//
		//			else{
		//				asteroid5.setLocation(rand.nextInt(getWidth() - asteroid5.width), 0);
		//			}
		//		}
		//		else{
		//			long currentTime = System.currentTimeMillis();
		//			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
		//				// draw a new asteroid
		//				lastAsteroidTime = currentTime;
		//				status.setNewAsteroid5(false);
		//				asteroid5.setLocation(rand.nextInt(getWidth() - asteroid5.width), 0);
		//			}
		//			else{
		//				// draw explosion
		//				graphicsMan.drawAsteroidExplosion5(asteroidExplosion5, g2d, this);
		//			}
		//		}



		//draw enemy
		if(!status.isNewEnemy()){
			// draw the asteroid until it reaches the bottom of the screen
			if(enemy.getY() + enemy.getSpeed() < this.getHeight()){
				enemy.translate(0, enemy.getSpeed());
				graphicsMan.drawEnemy(enemy, g2d, this);

			}

			else{
				enemy.setLocation(rand.nextInt(getWidth() - enemy.width), 0);
			}
		}
		else{
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
				// draw a new Enemy
				lastAsteroidTime = currentTime;
				status.setNewEnemy(false);
				enemy.setLocation(rand.nextInt(getWidth() - enemy.width), 0);
			}
			else{
				// draw explosion
				graphicsMan.drawEnemyExplosion(enemyExplosion, g2d, this);
			}
		}


		// draw bullets
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			graphicsMan.drawBullet(bullet, g2d, this);

			boolean remove = gameLogic.moveBullet(bullet);
			if(remove){
				bullets.remove(i);
				i--;
			}
		}


		//Asteroids Conditions

		// check bullet-asteroid collisions
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if(asteroid.intersects(bullet)){
				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

				// "remove" asteroid
				asteroidExplosion = new Rectangle(
						asteroid.x,
						asteroid.y,
						asteroid.width,
						asteroid.height);
				asteroid.setLocation(-asteroid.width, -asteroid.height);
				status.setNewAsteroid(true);
				lastAsteroidTime = System.currentTimeMillis();

				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();

				// remove bullet
				bullets.remove(i);
				break;
			}
		}
		// check bullet-asteroid2 collisions
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if(asteroid2.intersects(bullet)){
				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

				// "remove" asteroid
				asteroidExplosion2 = new Rectangle(
						asteroid2.x,
						asteroid2.y,
						asteroid2.width,
						asteroid2.height);
				asteroid2.setLocation(-asteroid2.width, -asteroid2.height);
				status.setNewAsteroid2(true);
				lastAsteroidTime = System.currentTimeMillis();

				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();

				// remove bullet
				bullets.remove(i);
				break;
			}
		}
		// check bullet-asteroid3 collisions
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if(asteroid3.intersects(bullet)){
				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

				// "remove" asteroid
				asteroidExplosion3 = new Rectangle(
						asteroid3.x,
						asteroid3.y,
						asteroid3.width,
						asteroid3.height);
				graphicsMan.drawAsteroid3(asteroid2, g2d, this);	
				asteroid3.setLocation(-asteroid3.width, -asteroid3.height);
				status.setNewAsteroid3(true);
				lastAsteroidTime = System.currentTimeMillis();

				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();

				// remove bullet
				bullets.remove(i);
				break;
			}
		}
		//Bullet- Enemy Collisions
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if(enemy.intersects(bullet)){
				// increase asteroids destroyed count

				// "remove" asteroid
				enemyExplosion = new Rectangle(
						enemy.x,
						enemy.y,
						enemy.width,
						enemy.height);
				enemy.setLocation(-enemy.width, -enemy.height);
				status.setNewEnemy(true);
				lastAsteroidTime = System.currentTimeMillis();
				soundMan.playAsteroidExplosionSound();

				// remove bullet
				bullets.remove(i);
				break;
			}
		}




		//Draw Ship & Conditions

		// draw ship
		if(!status.isNewShip()){
			// draw it in its current location
			graphicsMan.drawShip(ship, g2d, this);
		}
		else{
			// draw a new one
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastShipTime) > NEW_SHIP_DELAY){
				lastShipTime = currentTime;
				status.setNewShip(false);
				ship = gameLogic.newShip(this);
			}
			else{
				// draw explosion
				graphicsMan.drawShipExplosion(shipExplosion, g2d, this);
			}
		}

		// check ship-asteroid collisions
		if(asteroid.intersects(ship)){
			// decrease number of ships left
			status.setShipsLeft(status.getShipsLeft() - 1);
			status.setShieldLeft(status.getShieldLeft() - 1);
			status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

			// "remove" asteroid
			asteroidExplosion = new Rectangle(
					asteroid.x,
					asteroid.y,
					asteroid.width,
					asteroid.height);
			asteroid.setLocation(-asteroid.width, -asteroid.height);
			status.setNewAsteroid(true);
			lastAsteroidTime = System.currentTimeMillis();

			// "remove" ship
			shipExplosion = new Rectangle(
					ship.x,
					ship.y,
					ship.width,
					ship.height);
			ship.setLocation(this.getWidth() + ship.width, -ship.height);
			status.setNewShip(true);
			lastShipTime = System.currentTimeMillis();

			// play ship explosion sound
			soundMan.playShipExplosionSound();
			// play asteroid explosion sound
			soundMan.playAsteroidExplosionSound();
		}

		// check ship-asteroid2 collisions
		if(asteroid2.intersects(ship)){
			// decrease number of ships left
			status.setShipsLeft(status.getShipsLeft() - 1);
			status.setShieldLeft(status.getShieldLeft() - 1);
			status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

			// "remove" asteroid
			asteroidExplosion2 = new Rectangle(
					asteroid2.x,
					asteroid2.y,
					asteroid2.width,
					asteroid2.height);
			asteroid2.setLocation(-asteroid2.width, -asteroid2.height);
			status.setNewAsteroid2(true);
			lastAsteroidTime = System.currentTimeMillis();

			// "remove" ship
			shipExplosion = new Rectangle(
					ship.x,
					ship.y,
					ship.width,
					ship.height);
			ship.setLocation(this.getWidth() + ship.width, -ship.height);
			status.setNewShip(true);
			lastShipTime = System.currentTimeMillis();

			// play ship explosion sound
			soundMan.playShipExplosionSound();
			// play asteroid explosion sound
			soundMan.playAsteroidExplosionSound();
		}

		// check ship-asteroid3 collisions
		if(asteroid3.intersects(ship)){
			// decrease number of ships left
			status.setShipsLeft(status.getShipsLeft() - 1);
			status.setShieldLeft(status.getShieldLeft() - 1);
			status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

			// "remove" asteroid
			asteroidExplosion3 = new Rectangle(
					asteroid3.x,
					asteroid3.y,
					asteroid3.width,
					asteroid3.height);
			asteroid3.setLocation(-asteroid3.width, -asteroid3.height);
			status.setNewAsteroid3(true);
			lastAsteroidTime = System.currentTimeMillis();

			// "remove" ship
			shipExplosion = new Rectangle(
					ship.x,
					ship.y,
					ship.width,
					ship.height);
			ship.setLocation(this.getWidth() + ship.width, -ship.height);
			status.setNewShip(true);
			lastShipTime = System.currentTimeMillis();

			// play ship explosion sound
			soundMan.playShipExplosionSound();
			// play asteroid explosion sound
			soundMan.playAsteroidExplosionSound();
		}
		// check ship-enemybullets collisions
		if(enemybullet.intersects(ship)){
			// decrease number of ships left
			status.setShieldLeft(status.getShieldLeft() - 1);

			shipExplosion = new Rectangle(
					ship.x,
					ship.y,
					ship.width,
					ship.height);
			ship.setLocation(this.getWidth() + ship.width, -ship.height);
			status.setNewShip(true);
			lastShipTime = System.currentTimeMillis();

			// play ship explosion sound
			soundMan.playShipExplosionSound();
			// play asteroid explosion sound
			soundMan.playAsteroidExplosionSound();
		}

		//Check Ship-Enemy Collisions
		if(enemy.intersects(ship)){
			// decrease number of ships left
			status.setShipsLeft(status.getShipsLeft() - 1);
			status.setShieldLeft(status.getShieldLeft() - 1);

			// "remove" asteroid
			enemyExplosion = new Rectangle(
					enemy.x,
					enemy.y,
					enemy.width,
					enemy.height);
			enemy.setLocation(-enemy.width, -enemy.height);
			status.setNewEnemy(true);
			lastAsteroidTime = System.currentTimeMillis();

			// "remove" ship
			shipExplosion = new Rectangle(
					ship.x,
					ship.y,
					ship.width,
					ship.height);
			ship.setLocation(this.getWidth() + ship.width, -ship.height);
			status.setNewShip(true);
			lastShipTime = System.currentTimeMillis();

			// play ship explosion sound
			soundMan.playShipExplosionSound();
			// play asteroid explosion sound
			soundMan.playAsteroidExplosionSound();
		}

		// update asteroids destroyed label
		destroyedValueLabel.setText(Long.toString(status.getAsteroidsDestroyed()));

		// update ships left label
		shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));

	}

	/**
	 * Draws the "Game Over" message.
	 */
	private void drawGameOver() {
		String gameOverStr = "GAME OVER";
		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameOverStr);
		if(strWidth > this.getWidth() - 10){
			biggestFont = currentFont;
			bigFont = biggestFont;
			fm = g2d.getFontMetrics(bigFont);
			strWidth = fm.stringWidth(gameOverStr);
		}
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setFont(bigFont);
		g2d.setPaint(Color.WHITE);
		g2d.drawString(gameOverStr, strX, strY);
	}

	/**
	 * Draws the initial "Get Ready!" message.
	 */
	private void drawGetReady() {
		String readyStr = "Get Ready!";
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(readyStr, strX, strY);
	}
	public void drawnextLevel(){
		String readyStr = "Level 2";
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setPaint(Color.RED);
		g2d.drawString(readyStr, strX, strY);

	}
	public void drawnextLevel2(){
		String readyStr = "Level 3";
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setPaint(Color.RED);
		g2d.drawString(readyStr, strX, strY);

	}

	/**
	 * Draws the specified number of stars randomly on the game screen.
	 * @param numberOfStars the number of stars to draw
	 */
	private void drawStars(int numberOfStars) {
		g2d.setColor(Color.WHITE);
		for(int i=0; i<numberOfStars; i++){
			int x = (int)(Math.random() * this.getWidth());
			int y = (int)(Math.random() * this.getHeight());
			g2d.drawLine(x, y, x, y);
		}
	}

	/**
	 * Display initial game title screen.
	 */
	private void initialMessage() {
		String gameTitleStr = "Void Space";

		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD).deriveFont(Font.ITALIC);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameTitleStr);
		if(strWidth > this.getWidth() - 10){
			bigFont = currentFont;
			biggestFont = currentFont;
			fm = g2d.getFontMetrics(currentFont);
			strWidth = fm.stringWidth(gameTitleStr);
		}
		g2d.setFont(bigFont);
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2 - ascent;
		g2d.setPaint(Color.BLUE );
		g2d.drawString(gameTitleStr, strX, strY);

		g2d.setFont(originalFont);
		fm = g2d.getFontMetrics();
		String newGameStr = "Press <Space> to Start a New Game.";
		strWidth = fm.stringWidth(newGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = (this.getHeight() + fm.getAscent())/2 + ascent + 16;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(newGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String exitGameStr = "Press <Esc> to Exit the Game.";
		strWidth = fm.stringWidth(exitGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = strY + 16;
		g2d.drawString(exitGameStr, strX, strY);
	}

	/**
	 * Prepare screen for game over.
	 */
	public void doGameOver(){
		shipsValueLabel.setForeground(new Color(128, 0, 0));
	}

	/**
	 * Prepare screen for a new game.
	 */
	public void doNewGame(){		
		lastAsteroidTime = -NEW_ASTEROID_DELAY;
		lastShipTime = -NEW_SHIP_DELAY;

		bigFont = originalFont;
		biggestFont = null;

		// set labels' text
		shipsValueLabel.setForeground(Color.BLACK);
		shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));
		destroyedValueLabel.setText(Long.toString(status.getAsteroidsDestroyed()));

	}

	/**
	 * Sets the game graphics manager.
	 * @param graphicsMan the graphics manager
	 */
	public void setGraphicsMan(GraphicsManager graphicsMan) {
		this.graphicsMan = graphicsMan;
	}

	/**
	 * Sets the game logic handler
	 * @param gameLogic the game logic handler
	 */
	public void setGameLogic(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
		this.status = gameLogic.getStatus();
		this.soundMan = gameLogic.getSoundMan();
	}

	/**
	 * Sets the label that displays the value for asteroids destroyed.
	 * @param destroyedValueLabel the label to set
	 */
	public void setDestroyedValueLabel(JLabel destroyedValueLabel) {
		this.destroyedValueLabel = destroyedValueLabel;
	}

	/**
	 * Sets the label that displays the value for ship (lives) left
	 * @param shipsValueLabel the label to set
	 */
	public void setShipsValueLabel(JLabel shipsValueLabel) {
		this.shipsValueLabel = shipsValueLabel;
	}

	public JProgressBar getShieldprogress() {
		return shieldprogress;
	}

	public void setShieldprogress(JProgressBar shieldprogress) {
		this.shieldprogress = shieldprogress;
	}
}
