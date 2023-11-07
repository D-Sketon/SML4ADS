package com.ecnu.adsmls.components.modal;

import com.ecnu.adsmls.model.MModel;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class ParametricSTLModal extends Modal {

  private final MModel mModel;

  private final ListView<String> listView = new ListView<>();
  private final ListView<String> parametricsView = new ListView<>();
  private List<String> parametricStls = new ArrayList<>();
  private List<String> parametrics = new ArrayList<>();

  private int caretPosition;

  public ParametricSTLModal(MModel mModel) {
    this.mModel = mModel;
    this.loadData();
    ColumnConstraints col1 = new ColumnConstraints();
    col1.setPercentWidth(30);
    ColumnConstraints col2 = new ColumnConstraints();
    col2.setPercentWidth(10);
    ColumnConstraints col3 = new ColumnConstraints();
    col3.setPercentWidth(50);
    ColumnConstraints col4 = new ColumnConstraints();
    col4.setPercentWidth(10);
    this.slot.getColumnConstraints().addAll(col1, col2, col3, col4);
  }

  public List<String> getParametricStls() {
    return parametricStls;
  }

  public List<String> getParametrics() {
      return parametrics;
  }

  private void loadData() {
    this.parametricStls = this.mModel.getParametricStls();
    this.listView.setItems(FXCollections.observableList(this.parametricStls));
    this.parametrics = this.mModel.getParametrics();
    this.parametricsView.setItems(FXCollections.observableList(this.parametrics));
  }

  @Override
  protected void createWindow() {
    super.createWindow();
    this.window.setTitle("PSTL");

    Label lbPstl = new Label("pstls");
    Label lbDummy = new Label("");
    Label lbParametric = new Label("parametrics");

    this.listView.setCellFactory(TextFieldListCell.forListView());
//    this.listView.setEditable(true);

    this.parametricsView.setCellFactory(TextFieldListCell.forListView());
    this.parametricsView.setEditable(true);

    VBox btStlWrapper = new VBox();
    btStlWrapper.setAlignment(Pos.CENTER);
    btStlWrapper.setSpacing(5.0);
    Button btAddStl = new Button("Add");
    btAddStl.setOnAction(e -> {
      this.listView.getItems().add("");
    });

    Button btDeleteStl = new Button("Delete");
    btDeleteStl.setOnAction(e -> {
      String selected = this.listView.getSelectionModel().getSelectedItem();
      this.listView.getItems().remove(selected);
    });

    btStlWrapper.getChildren().addAll(btAddStl, btDeleteStl);
    TextField textField = new TextField();

    textField.setOnMouseClicked(event -> {
      if (event.getButton().equals(javafx.scene.input.MouseButton.PRIMARY)) {
        caretPosition = textField.getCaretPosition();
      }
    });

    textField.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
      int newCaretPosition = textField.getCaretPosition();
      if (newCaretPosition != caretPosition) {
        caretPosition = newCaretPosition;
      }
    });

    VBox btParametricWrapper = new VBox();
    btParametricWrapper.setAlignment(Pos.CENTER);
    btParametricWrapper.setSpacing(5.0);
    Button btAddParametric = new Button("Add");
    btAddParametric.setOnAction(e -> {
      this.parametricsView.getItems().add("");
    });

    Button btDeleteParametric = new Button("Delete");
    btDeleteParametric.setOnAction(e -> {
      String selected = this.parametricsView.getSelectionModel().getSelectedItem();
      this.parametricsView.getItems().remove(selected);
    });

    btParametricWrapper.getChildren().addAll(btAddParametric, btDeleteParametric);

    this.slot.addRow(0, lbParametric, lbDummy, lbPstl);
    this.slot.addRow(1,  this.parametricsView, btParametricWrapper, this.listView, btStlWrapper);
    GridPane.setConstraints(textField, 0, 2, 3, 1);
    this.slot.getChildren().add(textField);

    Button btSubmit = new Button("Submit Edit");
    GridPane.setConstraints(btSubmit, 3, 2);
    this.slot.getChildren().add(btSubmit);

    this.listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        textField.setText(newValue);
      }
    });

    btSubmit.setOnAction(e -> {
      int selectedIndex = listView.getSelectionModel().getSelectedIndex();
      if (selectedIndex >= 0) {
        String newText = textField.getText();
        listView.getItems().set(selectedIndex, newText);
      }
    });

    Button btAnd = buttonFactory("/\\", "/\\", textField);
    Button btOr = buttonFactory("\\/", "\\/", textField);
    Button btImply = buttonFactory("->", "->", textField);
    Button btG = buttonFactory("G", "G[,]", textField);
    Button btF = buttonFactory("F", "F[,]", textField);
    Button btU = buttonFactory("U", "U[,]", textField);

    HBox buttonsInRow4 = new HBox(10);
    buttonsInRow4.getChildren().addAll(btAnd, btOr, btImply, btG, btF, btU);

    // 将多个按钮并排放在第四行
    GridPane.setConstraints(buttonsInRow4, 0, 3, 3, 1);
    this.slot.getChildren().add(buttonsInRow4);
  }

  private Button buttonFactory(String title, String inner, TextField textField) {
    Button bt = new Button(title);
    bt.setMinWidth(70);
    bt.setOnAction(e -> {
      String currentText = textField.getText();
      String updatedText = currentText.substring(0, caretPosition) + inner + currentText.substring(caretPosition);
      textField.setText(updatedText);
      textField.requestFocus();
      textField.positionCaret(caretPosition + inner.length());
    });
    return bt;
  }

  @Override
  protected void update() {
    this.parametricStls = this.listView.getItems();
    this.parametrics = this.parametricsView.getItems();
  }

  @Override
  protected void check() {

  }

  @Override
  protected void then() {

  }
}
