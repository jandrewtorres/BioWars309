package client.model;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import server.model.Player;

public class PlayerStatusPane {
	public AnchorPane pane;
	public Label nameLabel;
	public Label populationLabel;
	private Player associatedPlayer;
	
	public PlayerStatusPane(AnchorPane pane, Label nameLabel, Label populationLabel) {
		this.pane = pane;
		this.nameLabel = nameLabel;
		this.populationLabel = populationLabel;
	}
	
	public void bindValues(Player p) {
		associatedPlayer = p;
		nameLabel.textProperty().bind(p.nameProperty());
		populationLabel.textProperty().bind(Bindings.createStringBinding(() ->
			Integer.toString(p.populationProperty().asObject().get()), p.populationProperty()));
	}
	
	public void hide() {
		pane.setVisible(false);
	}
	
	public Player getAssociatedPlayer() {
		return associatedPlayer;
	}
}
