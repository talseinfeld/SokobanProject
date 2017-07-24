package com.milestone_5.SokobanServer;

import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.milestone_5.SokobanServer.model.AdminModel;

import domain.SokobanHeuristics;
import model.data.Level;
import model.data.Square;
import plan.SokobanPlannable;
import plan.SokobanSolver;
import search.searcher.BFS;
import strips.Action;
import strips.Strips;

public class SokobanClientHandler implements ClientHandler {

	private List<char[]> grid;
	private ObjectInputStream in = null;
	private PrintWriter out = null;
	private final String URL = "http://localhost:8080/SokobanService/webapi/solutions/";

	public SokobanClientHandler() {}

	@Override
	public void handleClient(Socket clientSocket, InputStream inFromClient, OutputStream outToClient) {
		try {
			in = new ObjectInputStream(inFromClient);
			out = new PrintWriter(outToClient);

			Level level = (Level)in.readObject();
			String levelName = level.getLevelName();	
			AdminModel.getInstance().addClient(clientSocket);
			String solution = getSolutionFromService(levelName);
			if (solution==null) {
				squaresToGrid(level);
				solution = solveLevel(grid);
				addSolutionToDb(levelName, solution);
			}
			out.println(solution);
			out.flush();

		} catch (IOException e) {
			
		} catch (ClassNotFoundException e) {
			
		} finally {
			try {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
				if (clientSocket != null)
					clientSocket.close();
			} catch (IOException e) {}
		}
	}

	private String getSolutionFromService(String levelName) {
		String url = URL + levelName;    	
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(url);	
		String solution = null;
		Response response = webTarget.request(MediaType.TEXT_PLAIN)  
				.get(Response.class);        
		if (response.getStatus() == 200) {
			solution = response.readEntity(new GenericType<String>() {});       
			System.out.println("solution: " + solution);
		} 
		else {        	
			System.out.println(response.getHeaderString("errorResponse"));
		}
		return solution;
	}

	private void addSolutionToDb(String levelName, String solution) {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(URL);

		Form form = new Form();
		form.param("name", levelName);
		form.param("solution", solution);

		Response response = webTarget.request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

		if (response.getStatus() == 204) {
			System.out.println("Solutions database updated successfully.");
		} else {
			System.out.println(response.getHeaderString("errorResponse"));
		}
	}

	private String solveLevel(List<char[]> grid) {

		SokobanPlannable sp = new SokobanPlannable(grid, new BFS<Point>(), new BFS<Point>());
		SokobanSolver solver = new SokobanSolver(sp, new Strips());

		// Setting heuristic method to the solver
		solver.setHeuristic(new SokobanHeuristics());
		// Getting the solution
		List<Action> plan = solver.solve(); 

		StringBuilder solutionBuilder = new StringBuilder();
		for (Action a : plan) {
			if (a.toString().contains("up")) {
				solutionBuilder.append("u");
			} else if(a.toString().contains("down")) {
				solutionBuilder.append("d");
			} else if(a.toString().contains("left")) {
				solutionBuilder.append("l");
			} else if(a.toString().contains("right")) {
				solutionBuilder.append("r");
			}
		}
		return solutionBuilder.toString();
	}

	private void squaresToGrid(Level level) {
		List<ArrayList<Square>> squares = level.getSquares();
		grid = new ArrayList<>();
		for (int y=0; y<squares.size();y++) {
			grid.add(new char[squares.get(y).size()]);
			for (int x=0; x<squares.get(y).size();x++) {
				char[] squareType = squares.get(y).get(x).toString().toCharArray();
				grid.get(y)[x] = squareType[0];
			}
		}
	}
}
