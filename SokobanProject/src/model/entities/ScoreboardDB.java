package model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="Scoreboard")
public class ScoreboardDB implements Serializable, EntityDB {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private Integer id;
	@Column(name="LevelName")
	private String levelName;
	@Column(name="Username")
	private String username;
	@Column(name="TotalSteps")
	private Integer totalSteps;
	@Column(name="FinishTime")
	private Integer finishTime;
	
	public ScoreboardDB(){}
	
	public ScoreboardDB(String levelName, String username, int totalSteps, int finishTime) {
		this.levelName = levelName;
		this.username = username;
		this.totalSteps = totalSteps;
		this.finishTime = finishTime;
	}
	@Override
	public int getId() {
		return id;
	}
	@Override
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String getLevelName() {
		return levelName;
	}
	@Override
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public Integer getTotalSteps() {
		return totalSteps;
	}
	@Override
	public void setTotalSteps(int totalSteps) {
		this.totalSteps = totalSteps;
	}
	@Override
	public Integer getFinishTime() {
		return finishTime;
	}
	@Override
	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}
	
}
