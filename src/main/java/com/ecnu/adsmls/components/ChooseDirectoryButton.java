package com.ecnu.adsmls.components;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

// TODO 设置当前项目目录为初始目录
public class ChooseDirectoryButton {
    private File folder;
    private Pane rootLayout;

    private Label lbDirName;

    public ChooseDirectoryButton(Pane rootLayout) {
        this.rootLayout = rootLayout;
    }

    public Node getNode() {
        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.CENTER_LEFT);
        Button button = new Button("Choose Directory");
        this.lbDirName = new Label();
        hBox.getChildren().addAll(this.lbDirName, button);
        button.setOnMouseClicked(e -> {
            Stage stage = (Stage) rootLayout.getScene().getWindow();
            DirectoryChooser dirChooser = new DirectoryChooser();
            dirChooser.setTitle("Choose Directory");
            folder = dirChooser.showDialog(stage);
            if(folder != null) {
                this.lbDirName.setText(folder.getAbsolutePath());
            }
            stage.sizeToScene();
        });
        hBox.setUserData(this);
        return hBox;
    }

    public File getFolder() {
        return folder;
    }
}
