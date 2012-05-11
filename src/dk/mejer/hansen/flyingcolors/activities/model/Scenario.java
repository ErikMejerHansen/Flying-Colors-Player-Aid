package dk.mejer.hansen.flyingcolors.activities.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public class Scenario implements Serializable {
	private Map<Nationalities, Integer> audacities = new HashMap<Nationalities, Integer>();
	private int year;
	private String scenarioHint;
	private String scenarioName;

	public Scenario(String scenarioName) {
		this.scenarioName = scenarioName;
	}

	public void addNationality(Nationalities nation, int audacity) {
		audacities.put(nation, audacity);
	}

	public Period getPeriod() {
		return Period.fromYear(this.year);
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Set<Nationalities> getParticipatingNationalities() {
		return audacities.keySet();
	}

	public void setScenarioHint(String scenarioHint) {
		this.scenarioHint = scenarioHint;
	}
	
	public String getScenarioHint() {
		return scenarioHint;
	}
	
	public String getName() {
		return scenarioName;
	}

	public void setName(String name) {
		this.scenarioName = name;
	}

	public int getAudacity(Nationalities nationality) {
		return audacities.get(nationality);
	}
}
