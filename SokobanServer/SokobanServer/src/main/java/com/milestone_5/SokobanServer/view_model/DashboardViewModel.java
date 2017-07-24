package com.milestone_5.SokobanServer.view_model;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.milestone_5.SokobanServer.MyServer;
import com.milestone_5.SokobanServer.model.AdminModel;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class DashboardViewModel implements Observer {

	private AdminModel adminModel;
	private ObservableList<String> observableList;
	public ListProperty<String> clientsList;
	
	public DashboardViewModel(AdminModel adminModel) {
		
		this.adminModel = adminModel;
		observableList = FXCollections.observableArrayList();
		clientsList = new SimpleListProperty<String>();
		clientsList.set(observableList);
		
		observableList.addListener(new ListChangeListener<String>() {

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
				if (c.getRemovedSize() > 0) {
					for (String client: c.getRemoved()) {
						adminModel.removeClient(client);
					}
				}
				
			}
		});
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (o == adminModel) {
			@SuppressWarnings("unchecked")
			List<String> params = (LinkedList<String>)arg;
			String op = params.get(0);
			String clientName = params.get(1);
			if (op.equals("Add"))
				observableList.add(clientName);
			else
				observableList.remove(clientName);
		}		
	}

}
