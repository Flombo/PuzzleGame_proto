package GameObjects.Enemies;

import GameObjects.Field_like_Objects.Field;
import GameObjects.GameObjectEnums.PositionEnums.SkullPosition;
import GameObjects.GameObjectEnums.Frames.SkullWalkFrames;
import Rendering.Animations.SkullAnimations.skullAttackAnimation;
import Rendering.Animations.SkullAnimations.skullWalkAnimation;
import Sound.EnemieSounds.SkullSound;
import javafx.scene.image.Image;

public class Skull extends Field {
	private SkullPosition position;
	private SkullWalkFrames walkFrame;
	private Image currentImage;
	private Image imageRight;
	private Image imageLeft;
	private skullWalkAnimation skullWalkAnimation;
	private SkullSound skullSound;

	public Skull() {
		super("GameObjects.Enemies.Skull");
		this.imageRight = this.loadImage("/textures/skullTextureRight.png");
		this.imageLeft = this.loadImage("/textures/skullTextureLeft.png");
		this.currentImage = imageRight;
		this.walkFrame = SkullWalkFrames.Skull_Right_Default;
		this.position = SkullPosition.SKULL_RIGHT;
		this.skullWalkAnimation = new skullWalkAnimation();
		this.skullSound = new SkullSound();
	}

	public SkullWalkFrames getWalkFrame() {
		return walkFrame;
	}

	public void setImageToDefault(Skull skull){
		switch (skull.getPosition()) {
			case SKULL_LEFT:
				this.currentImage = imageRight;
				this.walkFrame = SkullWalkFrames.Skull_Right_Default;
				break;
			case SKULL_RIGHT:
				this.currentImage = imageLeft;
				this.walkFrame = SkullWalkFrames.Skull_Left_Default;
				break;
		}
	}

	public SkullPosition getPosition() {
		return position;
	}

	public void changePosition() {
		if(this.getPosition().equals(SkullPosition.SKULL_RIGHT)){
			this.setPosition(SkullPosition.SKULL_LEFT);
			this.walkFrame = SkullWalkFrames.Skull_Left_Default;
		} else {
			this.setPosition(SkullPosition.SKULL_RIGHT);
			this.walkFrame = SkullWalkFrames.Skull_Right_Default;
		}
	}

	private void setPosition(SkullPosition position) {
		this.position = position;
		this.setImageToDefault(this);
	}

	@Override
	public Image getCurrentImage() {
		return currentImage;
	}

	@Override
	public void setCurrentImage(Image currentImage) {
		this.currentImage = currentImage;
	}

	@Override
	public void setImageToDefault() {
		if(this.position.equals(SkullPosition.SKULL_RIGHT)){
			this.currentImage = this.imageRight;
		} else {
			this.currentImage = this.imageLeft;
		}
	}

	public void playAttackSound(){
		this.skullSound.playAttackSound();
	}

	//plays walk animiation
	public void walk(){
		this.skullWalkAnimation.walk(this);
	}

	public void setImage(Image image){
		this.currentImage = image;
	}

	public void setWalkFrame(SkullWalkFrames skullWalkFrame) {
		this.walkFrame = skullWalkFrame;
	}

	// plays attack animation
	public void attack(){
		this.playAttackSound();
		skullAttackAnimation skullAttackAnimation = new skullAttackAnimation(this);
		skullAttackAnimation.animateAttack();
	}
}
