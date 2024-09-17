package controller;

import com.alibaba.fastjson.JSON;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import po.AppInfo;
import utils.AppUtil;
import utils.FileUtil;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Controller {
    @FXML
    public TextArea dragArea;

    @FXML
    private TableView<AppInfo> tableViewToAdd;

    @FXML
    private TableView<AppInfo> tableViewAdded;

    @FXML
    private TableColumn<AppInfo, String> nameToAdd;

    @FXML
    private TableColumn<AppInfo, String> nameAdded;

    @FXML
    Button openButton;

    @FXML
    Button addButton;

    private List<AppInfo> addedAppInfos;

    private List<AppInfo> toAddAppInfos = new ArrayList<>();

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
    }

    public void initialize() {
        String s1 = FileUtil.readFile("./data.json");
        this.addedAppInfos = JSON.parseArray(s1, AppInfo.class);
        ObservableList<AppInfo> cellDate = FXCollections.observableArrayList();
        nameAdded.setCellValueFactory(new PropertyValueFactory<AppInfo, String>("name"));
        assert addedAppInfos != null;
        cellDate.addAll(addedAppInfos);
        tableViewAdded.setItems(cellDate);
    }

    public void handleOpenButtonAction() {
        addedAppInfos.forEach(appInfo -> {
            AppUtil.startProgram(appInfo.getAbsolutePath());
        });
    }

    public void handleAddButtonAction() {
        addedAppInfos.addAll(toAddAppInfos);
        Map<String, AppInfo> collect = addedAppInfos.stream().collect(Collectors.toMap(AppInfo::getAbsolutePath, Function.identity(), (v1, v2) -> v1));
        addedAppInfos = new ArrayList(collect.values());
        addedAppInfos.sort((a, b) -> {
            LocalDateTime aTime = LocalDateTime.parse(a.getId(), formatter);
            LocalDateTime bTime = LocalDateTime.parse(b.getId(), formatter);
            return aTime.isAfter(bTime) ? 1 : -1;
        });
        toAddAppInfos = new ArrayList<>();
        // 刷新表单
        ObservableList<AppInfo> cellDate = FXCollections.observableArrayList();
        cellDate.addAll(addedAppInfos);
        tableViewAdded.setItems(cellDate);

        ObservableList<AppInfo> cellDate2 = FXCollections.observableArrayList();
        cellDate2.addAll(toAddAppInfos);
        tableViewToAdd.setItems(cellDate2);
        // TODO: 2024/9/7  存文件
        String s = JSON.toJSONString(addedAppInfos);
        FileUtil.saveFile(s, "./data.json");
    }

    public void onDragOver(DragEvent event) {
        event.acceptTransferModes(TransferMode.ANY);
    }

    public void onDragDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasFiles()) {
            List<File> files = dragboard.getFiles();
            files.forEach(file -> {
                AppInfo appInfo = new AppInfo(file.getName(), file.getAbsolutePath(), LocalDateTime.now().format(formatter));
                toAddAppInfos.add(appInfo);
            });
            ObservableList<AppInfo> cellDate2Add = FXCollections.observableArrayList();
            nameToAdd.setCellValueFactory(new PropertyValueFactory<AppInfo, String>("name"));
            cellDate2Add.addAll(toAddAppInfos);
            tableViewToAdd.setItems(cellDate2Add);
        }
    }

    public void handleDeleteButtonAction(ActionEvent actionEvent) {
        AppInfo selected = tableViewAdded.getSelectionModel().getSelectedItem();
        if (selected != null) {
            tableViewAdded.getItems().remove(selected);
            // TODO: 2024/9/7  存文件
            addedAppInfos.remove(selected);
            String s = JSON.toJSONString(addedAppInfos);
            FileUtil.saveFile(s, "./data.json");
        }
    }
}
