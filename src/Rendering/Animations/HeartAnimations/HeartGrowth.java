package Rendering.Animations.HeartAnimations;

import GameLogic.ThreadHelper.ThreadWaitManager;
import GameObjects.Collectibles.Heart;
import GameObjects.GameObjectEnums.Frames.HeartGrowthFrames;
import javafx.scene.image.Image;

public class HeartGrowth implements Runnable {

	private Image growth1;
	private Image growth2;
	private Heart heart;
	private Thread thread;
	private boolean isRunning = false;
	private ThreadWaitManager threadWaitManager;

	public HeartGrowth(Heart heart){
		this.growth1 = heart.loadImage("/textures/HeartAnimation/Heart1.png");
		this.growth2 = heart.loadImage("/textures/HeartAnimation/Heart2.png");
		this.heart = heart;
		this.threadWaitManager = new ThreadWaitManager();
	}

	public void setIsRunning(boolean isRunning){
		this.isRunning = isRunning;
	}

	private synchronized void start(){
		this.isRunning = true;
		this.thread = new Thread(this, "HeartGrowth");
		this.thread.setDaemon(true);
		this.thread.start();
	}

	private synchronized void stop(){
		isRunning = false;
		this.thread.interrupt();
	}

	@Override
	public void run(){
		long timer = System.currentTimeMillis();
		while (isRunning){
			if (System.currentTimeMillis() - timer > 10000 / 60) {
				this.changeCurrentHeartImage(this.heart);
				this.threadWaitManager.pauseThread(10000 / 60);
				timer += 3500 / 60;
			}
			this.heart.setCurrentImageToDefault();
		}
		this.stop();
	}

	//changes currentCoinImage to next frame
	private void changeCurrentHeartImage(Heart heart){
		switch (heart.getGrowthFrame()){
			case Heart_Default:
				heart.setCurrentImage(this.growth1);
				heart.setGrowthFrame(HeartGrowthFrames.Heart_1);
				break;
			case Heart_1:
				heart.setCurrentImage(this.growth2);
				heart.setGrowthFrame(HeartGrowthFrames.Heart_2);
				break;
			case Heart_2:
				heart.setImageToDefault();
				heart.setGrowthFrame(HeartGrowthFrames.Heart_Default);
				break;
		}
	}

	//inits thread
	public void initGrowth(){
		this.start();
	}
}
