package GameObjects.Obstacles;

import GameObjects.Field_like_Objects.Field;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Obstacle extends Field {
	private BufferedImage image;
	public Obstacle(){
		super(0, 0, "GameObjects.Obstacles.Obstacle");
		try {
			this.image = ImageIO.read(getClass().getResource("/textures/obstacleTexture.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public BufferedImage getCurrentImage() {
		return image;
	}
}