package org.javafx12306;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import org.javafx12306.entity.Station;
import org.javafx12306.entity.Ticket;
import org.javafx12306.gui.ComboBoxListener;
import org.javafx12306.util.APIUtil;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controller implements Initializable {

    private List<Station> stationList = null;

    private Map<String, String> codeNameMap = null;

    @FXML
    public ComboBox from;

    @FXML
    public ComboBox to;

    @FXML
    public DatePicker date;

    @FXML
    public Button search;

    @FXML
    public TableView tableView;

    @FXML
    public HBox top;

    private ComboBoxListener fromStation;

    private ComboBoxListener toStation;

    @FXML
    public void handleButton() {
        Station fromSelected = fromStation.getSelected();
        Station toSelected = toStation.getSelected();
        String dateStr = date.getEditor().getText();
        DateTime selectedDate = DateUtil.parse(dateStr);

        // 日期不能小于今天
        if (selectedDate.isBefore(DateUtil.parseDate(DateUtil.today()))) {
            return;
        }

        if (null == fromSelected
                || null == toSelected
                || StrUtil.isEmpty(fromSelected.getCode())
                || StrUtil.isEmpty(toSelected.getCode())
                || StrUtil.isEmpty(dateStr)) {
            return;
        }
        List<Ticket> ticketList = APIUtil.getTicketList(fromSelected.getCode(), toSelected.getCode(), dateStr);
        for (Ticket ticket : ticketList) {
            ticket.setSfmz(this.codeNameMap.get(ticket.getSfmz()));
            ticket.setDdmz(this.codeNameMap.get(ticket.getDdmz()));
        }
        ObservableList<Ticket> strList = getTableViewList(ticketList);
        tableView.setItems(strList);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.stationList = APIUtil.getStationList();
        this.codeNameMap = this.stationList.stream().collect(Collectors.toMap(Station::getCode, Station::getName, (o, n) -> o));
        ObservableList<Station> stations = FXCollections.observableArrayList(this.stationList);
        this.fromStation = createComboBox(from,stations);
        this.toStation = createComboBox(to, stations);
    }


    public ComboBoxListener createComboBox(ComboBox comboBox, ObservableList<Station> list){
        comboBox.getItems().addAll(list);
        comboBox.converterProperty().set(new StringConverter<Station>() {
            Station temp = new Station();
            @Override
            public String toString(Station object) {
                temp = object;
                if (null == temp) {
                    return null;
                }
                return String.valueOf(object.getName());
            }
            @Override
            public Station fromString(String string) {
                return temp;
            }
        });
       return new ComboBoxListener(comboBox);
    }

    private ObservableList<Ticket> getTableViewList(List<Ticket> ticketList) {
        ObservableList<Ticket> strList = FXCollections.observableArrayList(ticketList);
        ObservableList<TableColumn> observableList = tableView.getColumns();
        observableList.get(0).setCellValueFactory(new PropertyValueFactory("cc"));
        observableList.get(1).setCellValueFactory(new PropertyValueFactory("sfmz"));
        observableList.get(2).setCellValueFactory(new PropertyValueFactory("ddmz"));
        observableList.get(3).setCellValueFactory(new PropertyValueFactory("cfsj"));
        observableList.get(4).setCellValueFactory(new PropertyValueFactory("ddsj"));
        observableList.get(5).setCellValueFactory(new PropertyValueFactory("lishi"));
        observableList.get(6).setCellValueFactory(new PropertyValueFactory("gjrw"));
        observableList.get(7).setCellValueFactory(new PropertyValueFactory("rw"));
        observableList.get(8).setCellValueFactory(new PropertyValueFactory("wz"));
        observableList.get(9).setCellValueFactory(new PropertyValueFactory("yw"));
        observableList.get(10).setCellValueFactory(new PropertyValueFactory("yz"));
        observableList.get(11).setCellValueFactory(new PropertyValueFactory("ed"));
        observableList.get(12).setCellValueFactory(new PropertyValueFactory("yd"));
        observableList.get(13).setCellValueFactory(new PropertyValueFactory("td"));
        return strList;
    }


}
