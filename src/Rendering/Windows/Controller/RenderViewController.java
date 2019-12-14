package Rendering.Windows.Controller;

import GameObjects.Field_like_Objects.Field;
import GameObjects.Player.Player;
import Rendering.View;
import Rendering.Windows.ControllerHelper.HoverHelper;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;

public class RenderViewController extends HoverHelper {

    public Label coinLabel;
    @FXML
    private Label moveLabel;
    @FXML
    private Label liveLabel;
    @FXML
    private Button menuButton;
    @FXML
    private Button punchSkillButton;
    @FXML
    private Button doubleCoinSkillButton;
    @FXML
    private Button rageSkillButton;
    @FXML
    private Canvas renderCanvas;
    private Field[][] fields;
    private View view;

    public void menuButtonClicked(MouseEvent mouseEvent) {
        if(view != null){
            this.view.showIngameMenu();
        }
    }

    //render Gamefield and Counterpanel
    public void initRendering(Field[][] fields, View view){
        this.view = view;
        this.fields = fields;
        this.renderGamefield(this.renderCanvas);
    }

    public Runnable setLabelBindings(Player player){
        return (() -> {
            moveLabel.textProperty().bind(player.getMoves());
            liveLabel.textProperty().bind(player.getLives());
            coinLabel.textProperty().bind(player.getCoins());
        });
    }

    //render Gamefield
    private void renderGamefield(Canvas renderCanvas){
        GraphicsContext g = renderCanvas.getGraphicsContext2D();
        for (Field[] fields : this.fields) {
            for(Field field : fields){
                g.drawImage(field.getCurrentImage(), field.getX() + 75, field.getY() + 90, field.getHeight(), field.getWidth());
            }
        }
    }

}