package rbadia.voidspace.graphics;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.Ship;

/**
 * Manages and draws game graphics and images.
 */
public class GraphicsManager {
	private BufferedImage shipImg;
	private BufferedImage enemyImg;
	private BufferedImage bulletImg;
	private BufferedImage asteroidImg;
	private BufferedImage asteroidImg2;
	private BufferedImage asteroidImg3;
	private BufferedImage enemybulletImg;

	private BufferedImage asteroidExplosionImg;
	private BufferedImage asteroidExplosionImg2;
	private BufferedImage asteroidExplosionImg3;
	private BufferedImage shipExplosionImg;
	private BufferedImage enemyExplosionImg;

	/**
	 * Creates a new graphics manager and loads the game images.
	 */
	public GraphicsManager(){
		// load images
		try {
			this.shipImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/ship.png"));
			this.enemyImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/enemyShip.png"));
			this.asteroidImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroid.png"));
			this.asteroidImg2 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroid.png"));
			this.asteroidImg3 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/BigAsteroid.png"));
			this.enemyExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroidExplosion.png"));

			this.asteroidExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroidExplosion.png"));
			this.asteroidExplosionImg2 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroidExplosion.png"));
			this.asteroidExplosionImg3 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/BigAsteroidExplosion.png"));
			this.shipExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/shipExplosion.png"));
			this.bulletImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bullet.png"));
			this.enemybulletImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/EnemyBullet.png"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "The graphic files are either corrupt or missing.",
					"VoidSpace - Fatal Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Draws a ship image to the specified graphics canvas.
	 * @param ship the ship to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawShip(Ship ship, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(shipImg, ship.x, ship.y, observer);
	}
	public void drawEnemy(EnemyShip enemy, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(enemyImg, enemy.x, enemy.y, observer);
	}

	/**
	 * Draws a bullet image to the specified graphics canvas.
	 * @param bullet the bullet to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBullet(Bullet bullet, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bulletImg, bullet.x, bullet.y, observer);
	}

	/**
	 * Draws an asteroid image to the specified graphics canvas.
	 * @param asteroid the asteroid to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawAsteroid(Asteroid asteroid, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidImg, asteroid.x, asteroid.y, observer);
	}
	public void drawAsteroid2(Asteroid asteroid2, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidImg2, asteroid2.x, asteroid2.y, observer);
	}
	public void drawAsteroid3(Asteroid asteroid2, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidImg3, asteroid2.x, asteroid2.y, observer);
	}
	public void drawAsteroid4(Asteroid asteroid2, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidImg2, asteroid2.x, asteroid2.y, observer);
	}
	public void drawAsteroid5(Asteroid asteroid2, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidImg3, asteroid2.x, asteroid2.y, observer);
	}
	public void drawEnemyBullet(Asteroid asteroid2, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(enemybulletImg, asteroid2.x, asteroid2.y, observer);
	}


	/**
	 * Draws a ship explosion image to the specified graphics canvas.
	 * @param shipExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawShipExplosion(Rectangle shipExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(shipExplosionImg, shipExplosion.x, shipExplosion.y, observer);
	}

	/**
	 * Draws an asteroid explosion image to the specified graphics canvas.
	 * @param asteroidExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawAsteroidExplosion(Rectangle asteroidExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidExplosionImg, asteroidExplosion.x, asteroidExplosion.y, observer);
	}
	public void drawAsteroidExplosion2(Rectangle asteroidExplosion2, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidExplosionImg2, asteroidExplosion2.x, asteroidExplosion2.y, observer);
	}
	public void drawAsteroidExplosion3(Rectangle asteroidExplosion2, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidExplosionImg3, asteroidExplosion2.x, asteroidExplosion2.y, observer);
	}
	public void drawAsteroidExplosion4(Rectangle asteroidExplosion4, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidExplosionImg2, asteroidExplosion4.x, asteroidExplosion4.y, observer);
	}
	public void drawAsteroidExplosion5(Rectangle asteroidExplosion5, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidExplosionImg2, asteroidExplosion5.x, asteroidExplosion5.y, observer);
	}

	public void drawEnemyExplosion(Rectangle enemyExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(enemyExplosionImg, enemyExplosion.x, enemyExplosion.y, observer);
	}

}
