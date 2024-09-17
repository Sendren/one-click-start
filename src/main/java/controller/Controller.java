package controller;

import com.alibaba.fastjson.JSON;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import po.AppInfo;
import po.Data;
import utils.AppUtil;
import utils.FileUtil;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Controller {

    @FXML
    private TableView<AppInfo> tableViewStartAll;

    @FXML
    private TableView<AppInfo> tableViewStartFavor;

    @FXML
    private TableColumn<AppInfo, String> nameStartAll;

    @FXML
    private TableColumn<AppInfo, String> nameStartFavor;

    @FXML
    Button openAllButton;

    @FXML
    Button openFavorButton;

    @FXML
    Button deleteButton;

    /**
     * 数据类 包含 startAllList 和 startFavorList
     */
    private Data data;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private String dataPath = "F:\\code\\one-click-start\\data\\data.json";

    public void initialize() {
        String s1 = FileUtil.readFile(dataPath);
        data = JSON.parseObject(s1, Data.class);
        if (Objects.isNull(data)) {
            data = Data.init();
        }
        data.setStartAllList(Objects.isNull(data.getStartAllList()) ? Collections.emptyList() : data.getStartAllList());
        data.setStartFavorList(Objects.isNull(data.getStartFavorList()) ? Collections.emptyList() : data.getStartFavorList());


        ObservableList<AppInfo> startAllObservable = FXCollections.observableArrayList();
        ObservableList<AppInfo> startFavorObservable = FXCollections.observableArrayList();
        nameStartAll.setCellValueFactory(new PropertyValueFactory<AppInfo, String>("name"));
        nameStartFavor.setCellValueFactory(new PropertyValueFactory<AppInfo, String>("name"));
        startAllObservable.addAll(data.getStartAllList());
        startFavorObservable.addAll(data.getStartFavorList());
        tableViewStartAll.setItems(startAllObservable);
        tableViewStartFavor.setItems(startFavorObservable);
    }

    public void handleOpenAllButtonAction() {
        data.getStartAllList().forEach(appInfo -> {
            AppUtil.startProgram(appInfo.getAbsolutePath());
        });
    }

    public void handleOpenFavorButtonAction() {
        AppInfo selectedStartFavor = tableViewStartFavor.getSelectionModel().getSelectedItem();
        if (selectedStartFavor != null) {
            AppUtil.startProgram(selectedStartFavor.getAbsolutePath());
        }
    }

    public void onDragOver(DragEvent event) {
        event.acceptTransferModes(TransferMode.ANY);
    }

    public void onDragDroppedForStartAll(DragEvent event) {
        handleDragSaveAndView(event, data.getStartAllList(), tableViewStartAll);
    }

    public void onDragDroppedForStartFavor(DragEvent event) {
        handleDragSaveAndView(event, data.getStartFavorList(), tableViewStartFavor);
    }

    private void handleDragSaveAndView(DragEvent event, List<AppInfo> startAllList, TableView<AppInfo> tableView) {
        Dragboard dragboard = event.getDragboard();
        List<AppInfo> thisTimeAdd = new ArrayList<>();
        if (dragboard.hasFiles()) {
            List<File> files = dragboard.getFiles();
            files.forEach(file -> {
                AppInfo appInfo = new AppInfo(file.getName(), file.getAbsolutePath(), LocalDateTime.now().format(formatter));
                thisTimeAdd.add(appInfo);
            });
            // 新旧合并、去重、排序
            startAllList.addAll(thisTimeAdd);
            Map<String, AppInfo> collect = startAllList.stream().collect(Collectors.toMap(AppInfo::getAbsolutePath, Function.identity(), (v1, v2) -> v1));
            startAllList.clear();
            startAllList.addAll(collect.values());
            startAllList.sort((a, b) -> {
                LocalDateTime aTime = LocalDateTime.parse(a.getId(), formatter);
                LocalDateTime bTime = LocalDateTime.parse(b.getId(), formatter);
                return aTime.isAfter(bTime) ? 1 : -1;
            });

            // 刷新表单
            ObservableList<AppInfo> cellDate = FXCollections.observableArrayList();
            cellDate.addAll(startAllList);
            tableView.setItems(cellDate);
            // 保存数据
            String s = JSON.toJSONString(data);
            FileUtil.saveFile(s, dataPath);
        }
    }

    public void handleDeleteButtonAction(ActionEvent actionEvent) {
        AppInfo selectedStartAll = tableViewStartAll.getSelectionModel().getSelectedItem();
        AppInfo selectedStartFavor = tableViewStartFavor.getSelectionModel().getSelectedItem();
        handleDelete(selectedStartAll, data.getStartAllList(), tableViewStartAll);
        handleDelete(selectedStartFavor, data.getStartFavorList(), tableViewStartFavor);
        String s = JSON.toJSONString(data);
        FileUtil.saveFile(s, dataPath);
    }

    private void handleDelete(AppInfo selectObject, List<AppInfo> dbList, TableView<AppInfo> tableView) {
        if (selectObject != null) {
            tableView.getItems().remove(selectObject);
            dbList.remove(selectObject);
        }

    }
}
