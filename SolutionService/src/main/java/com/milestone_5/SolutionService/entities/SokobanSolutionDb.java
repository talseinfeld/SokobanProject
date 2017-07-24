package com.milestone_5.SolutionService.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Solutions")
public class SokobanSolutionDb {
	
	@Id
	@Column(name="LevelName")
	private String levelName;
	@Column(name="Solution")
	private String solution;
	
	public SokobanSolutionDb() {}
	
	public SokobanSolutionDb(String levelName, String solution) {
		super();
		this.levelName = levelName;
		this.solution = solution;
	}
	
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	
	
	
}
