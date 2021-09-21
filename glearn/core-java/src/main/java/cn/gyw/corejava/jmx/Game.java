package cn.gyw.corejava.jmx;

public class Game implements GameMBean {

	private String playerName;
	
	@Override
	public void playFootball(String clubName) {
		System.out.println(this.playerName + " play for " + clubName);
	}

	@Override
	public String getPlayerName() {
		return playerName;
	}

	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

}
