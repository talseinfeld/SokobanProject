package model.entities;

public interface EntityDB {

	public int getId();
	public void setId(int id);
	public String getLevelName();
	public void setLevelName(String levelName);
	public String getUsername();
	public void setUsername(String username);
	public Integer getTotalSteps();
	public void setTotalSteps(int totalSteps);
	public Integer getFinishTime();
	public void setFinishTime(int finishTime);
}
