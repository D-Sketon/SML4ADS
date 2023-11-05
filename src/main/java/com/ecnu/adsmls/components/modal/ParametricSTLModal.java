package com.ecnu.adsmls.components.modal;

import com.ecnu.adsmls.model.MModel;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class ParametricSTLModal extends Modal {

  private final MModel mModel;

  private final ListView<String> listView = new ListView<>();
  private List<String> parametricStls = new ArrayList<>();

  public ParametricSTLModal(MModel mModel) {
    this.mModel = mModel;
    this.loadData();
  }

  public List<String> getParametricStls() {
    return parametricStls;
  }

  private void loadData() {
    this.parametricStls = this.mModel.getParametricStls();
    this.listView.setItems(FXCollections.observableList(this.parametricStls));
  }

  @Override
  protected void createWindow() {
    super.createWindow();
    this.window.setTitle("PSTL");

    Label lbRequirements = new Label("pstls");

    this.listView.setCellFactory(TextFieldListCell.forListView());
    this.listView.setEditable(true);

    VBox btWrapper = new VBox();
    btWrapper.setAlignment(Pos.CENTER);
    btWrapper.setSpacing(5.0);
    Button btAdd = new Button("Add");
    btAdd.setOnAction(e -> {
      this.listView.getItems().add("");
    });

    Button btDelete = new Button("Delete");
    btDelete.setOnAction(e -> {
      String selected = this.listView.getSelectionModel().getSelectedItem();
      this.listView.getItems().remove(selected);
    });

    btWrapper.getChildren().addAll(btAdd, btDelete);

    this.slot.addRow(0, lbRequirements);
    this.slot.addRow(1, this.listView, btWrapper);
  }

  @Override
  protected void update() {
    this.parametricStls = this.listView.getItems();
  }

  @Override
  protected void check() {

  }

  @Override
  protected void then() {

  }
}
