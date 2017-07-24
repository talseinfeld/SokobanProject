package com.milestone_5.SolutionService;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.milestone_5.SolutionService.db.DbHandler;
import com.milestone_5.SolutionService.entities.SokobanSolutionDb;


@Path("solutions")
public class SolutionService {
	
	private DbHandler dbHandler = new DbHandler();
	
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	@Path("{name}")
    public String getSolution(@PathParam("name") String name) {
        return dbHandler.getSolution(name);
    }
	
	@POST  
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addSolution(@FormParam("name") String name, @FormParam("solution") String solution) {
		SokobanSolutionDb sol = new SokobanSolutionDb(name, solution);
        dbHandler.addSolution(sol);
    }
}
