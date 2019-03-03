package GameLogic.Movement;

import GameLogic.Movement.MovementHelper.playerCollisionChecker;
import GameLogic.Movement.MovementHelper.playerFrameChecker;
import GameObjects.Field;
import GameObjects.GameObjectEnums.PlayerPosition;
import GameObjects.Player;
import GameObjects.Target;
import Helper.consolePrinter;
import Rendering.View;

import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class PlayerMovement {

	private View view;
	private consolePrinter consolePrinter;
	private int yDimension;
	private int xDimension;
	private Field[][] fields;
	private Player player;
	private int playerPosY;
	private int playerPosX;
	private playerFrameChecker playerFrameChecker;

	public PlayerMovement(consolePrinter consolePrinter , int xDimension, int yDimension, View view, Field[][] fields, int playerPosY, int playerPosX, Player player){
		this.consolePrinter = consolePrinter;
		this.xDimension = xDimension;
		this.yDimension = yDimension;
		this.view = view;
		this.fields = fields;
		this.playerPosY = playerPosY;
		this.playerPosX = playerPosX;
		this.player = player;
		this.playerFrameChecker = new playerFrameChecker();
	}

	private void setPlayerPosY(int playerPosY) {
		this.playerPosY = playerPosY;
	}

	private void setPlayerPosX(int playerPosX) {
		this.playerPosX = playerPosX;
	}

	// checks if next playerPos is target and when it isn´t creates new Player
	private void controllPlayer(){
		if(this.fields[this.playerPosX][this.playerPosY] instanceof Target){
			this.view.setDialog(
					"You won your moves :" + " "
							+ this.player.getMoves()
							+ " Score : "  + " "
							+ this.player.getScore()
							+ " Lives : " + this.player.getLives()
			);
			this.view.dispatchEvent(new WindowEvent(this.view, WindowEvent.WINDOW_CLOSING));
			this.player.setMoves(0);
		}

		this.player.setY(this.playerPosY * 30);
		this.player.setX(this.playerPosX * 30);

		this.fields[this.playerPosX][this.playerPosY] = this.player;
	}

	//keylistener for player-movement
	public void changePlayerPos(KeyEvent event){
		int keyCode = event.getKeyCode();

		switch ( keyCode ){
			case KeyEvent.VK_DOWN:
				this.movePlayer(true, 1);
				this.player.setPosition(PlayerPosition.PLAYER_DOWN);
				this.playerFrameChecker.checkWalkFrameDown(this.player);
				break;
			case KeyEvent.VK_S:
				this.movePlayer(true, 1);
				this.player.setPosition(PlayerPosition.PLAYER_DOWN);
				this.playerFrameChecker.checkWalkFrameDown(this.player);
				break;
			case KeyEvent.VK_UP:
				this.movePlayer(true, -1);
				this.player.setPosition(PlayerPosition.PLAYER_UP);
				this.playerFrameChecker.checkWalkFrameUp(this.player);
				break;
			case KeyEvent.VK_W:
				this.movePlayer(true, -1);
				this.player.setPosition(PlayerPosition.PLAYER_UP);
				this.playerFrameChecker.checkWalkFrameUp(this.player);
				break;
			case KeyEvent.VK_RIGHT:
				this.movePlayer(false, 1);
				this.player.setPosition(PlayerPosition.PLAYER_RIGHT);
				this.playerFrameChecker.checkWalkFrameRight(this.player);
				break;
			case KeyEvent.VK_D:
				this.movePlayer(false, 1);
				this.player.setPosition(PlayerPosition.PLAYER_RIGHT);
				this.playerFrameChecker.checkWalkFrameRight(this.player);
				break;
			case KeyEvent.VK_LEFT:
				this.movePlayer(false, -1);
				this.player.setPosition(PlayerPosition.PLAYER_LEFT);
				this.playerFrameChecker.checkWalkFrameLeft(this.player);
				break;
			case KeyEvent.VK_A:
				this.movePlayer(false, -1);
				this.player.setPosition(PlayerPosition.PLAYER_LEFT);
				this.playerFrameChecker.checkWalkFrameLeft(this.player);
				break;
			default:
				this.view.setDialog("You have to press the arrow keys or WASD!");
				break;

		}

	}

	//move player pos
	private void movePlayerPos() {
		this.player.setMoves(1);
		this.controllPlayer();
	}

	//method that checks if down/up is pressed and if player reached the end of field
	private void movePlayer(boolean upDown,int newPos) {
		GameLogic.Movement.MovementHelper.playerCollisionChecker playerCollisionChecker;
		if(upDown) {
			if (this.playerPosY + newPos >= 0 && this.playerPosY + newPos < yDimension) {
				playerCollisionChecker = new playerCollisionChecker(this.playerPosX, this.playerPosY + newPos, fields, player);
				if(playerCollisionChecker.checkNextGameObject()) {
					this.fields[this.playerPosX][this.playerPosY] = new Field(this.playerPosX * 30, this.playerPosY * 30, "GameObjects.Field");
					this.setPlayerPosY(this.playerPosY + newPos);
					this.movePlayerPos();
				}
			}
		}else {
			if (this.playerPosX + newPos >= 0 && this.playerPosX + newPos < xDimension) {
				playerCollisionChecker = new playerCollisionChecker(this.playerPosX + newPos, this.playerPosY, fields, player);
				if(playerCollisionChecker.checkNextGameObject()) {
					this.fields[this.playerPosX][this.playerPosY] = new Field(this.playerPosX * 30, this.playerPosY * 30, "GameObjects.Field");
					this.setPlayerPosX(this.playerPosX + newPos);
					this.movePlayerPos();
				}
			}
		}
//		consolePrinter.printAllFields(yDimension, xDimension, fields);
	}

}
