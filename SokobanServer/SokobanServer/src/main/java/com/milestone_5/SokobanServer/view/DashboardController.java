package com.milestone_5.SokobanServer.view;



import com.milestone_5.SokobanServer.view_model.DashboardViewModel;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class DashboardController {

    @FXML
    private Button button;
    
    @FXML
    private Label label;

    @FXML
    private ListView<String> myListView;
   
    private DashboardViewModel vm;
    
    public void setViewModel(DashboardViewModel vm) {
    	this.setVm(vm);
    	myListView.itemsProperty().bind(vm.clientsList);
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
				myListView.getItems().remove(myListView.getSelectionModel().getSelectedItem());
			}
		});
    }

	public DashboardViewModel getVm() {
		return vm;
	}

	public void setVm(DashboardViewModel vm) {
		this.vm = vm;
	}  
}