package info;

import java.io.Serializable;
import java.util.ArrayList;

public class Info implements Serializable{
	private static final long serialVersionUID = 4089220122187181512L;
	
	private ArrayList<PlayerInfo> playerList = new ArrayList<PlayerInfo>();
	private ArrayList<VesselInfo> vesselList = new ArrayList<VesselInfo>();
	
	public Info(ArrayList<PlayerInfo> playerList, ArrayList<VesselInfo> vesselList){
		this.playerList = playerList;
		this.vesselList = vesselList;
	}

	public ArrayList<PlayerInfo> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(ArrayList<PlayerInfo> playerList) {
		this.playerList = playerList;
	}

	public ArrayList<VesselInfo> getVesselList() {
		return vesselList;
	}

	public void setVesselList(ArrayList<VesselInfo> vesselList) {
		this.vesselList = vesselList;
	}
	
}
