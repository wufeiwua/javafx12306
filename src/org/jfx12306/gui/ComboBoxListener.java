package org.jfx12306.gui;

import org.jfx12306.entity.Station;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.List;
import java.util.stream.Collectors;

public class ComboBoxListener implements EventHandler<KeyEvent> {
    private ComboBox comboBox;
    private ObservableList<Station> data;
    private boolean moveCaretToPos = false;
    private int caretPos;
    private Station selected;

    public ComboBoxListener(ComboBox comboBox) {
        this.comboBox = comboBox;
        data = comboBox.getItems();

        this.comboBox.setEditable(true);
        this.comboBox.setOnKeyPressed(t -> comboBox.hide());
        this.comboBox.setOnKeyReleased(ComboBoxListener.this);

        this.comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                selected = (Station) newValue;
            }
        });
    }

    public Station getSelected() {
        return selected;
    }

    @Override
    public void handle(KeyEvent event) {

        if(event.getCode() == KeyCode.UP) {
            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;
        } else if(event.getCode() == KeyCode.DOWN) {
            if(!comboBox.isShowing()) {
                comboBox.show();
            }
            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;
        } else if(event.getCode() == KeyCode.BACK_SPACE) {
            moveCaretToPos = true;
            caretPos = comboBox.getEditor().getCaretPosition();
        } else if(event.getCode() == KeyCode.DELETE) {
            moveCaretToPos = true;
            caretPos = comboBox.getEditor().getCaretPosition();
        }

        if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                || event.isControlDown() || event.getCode() == KeyCode.HOME
                || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
            return;
        }

        ObservableList list = FXCollections.observableArrayList();
        String text = ComboBoxListener.this.comboBox
                .getEditor().getText();
        List<Station> filter = data.stream().filter(i ->
                i.getShortPinYin().contains(text)
                        || i.getFullPinYin().contains(text)
                        || i.getName().contains(text)
        ).collect(Collectors.toList());
        list.addAll(filter);
        String t = comboBox.getEditor().getText();

        comboBox.setItems(list);

        comboBox.getEditor().setText(t);

        if(!moveCaretToPos) {
            caretPos = -1;
        }
        moveCaret(t.length());
        if(!list.isEmpty()) {
            comboBox.show();
        }
    }

    private void moveCaret(int textLength) {
        if(caretPos == -1) {
            comboBox.getEditor().positionCaret(textLength);
        } else {
            comboBox.getEditor().positionCaret(caretPos);
        }
        moveCaretToPos = false;
    }
}
