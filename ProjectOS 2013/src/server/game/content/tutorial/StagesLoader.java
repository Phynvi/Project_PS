package server.game.content.tutorial;

import java.util.HashMap;
import server.game.players.Client;
	
public enum StagesLoader {
	
	STAGE_1(1, 1, new int[]{TutorialConstants.LOGOUT_TAB[0]}) {
		@Override
		public void sendInterfaces(Client c, boolean send) {
			c.setSidebarInterface(TutorialConstants.LOGOUT_TAB[0], TutorialConstants.LOGOUT_TAB[1]);
			//c.getPA().createPlayerHints(10, 945);//to fix
			c.getPA().drawHeadicon(1, 945, 0, 0);
			c.getDH().sendStartInfo("To start the tutorial use your left mouse-button to click on the", "'2006Redone Guide' in this room. He is indicated by a flashing", "yellow arrow above his head. If you can't find him, use your", "keyboard's arrow keys to rotate the view.", "Getting Started", send);//to fix
		}
	};
	
	static HashMap<Integer, StagesLoader> stages = new HashMap<Integer, StagesLoader>();

	static {
		for (StagesLoader data : values()) {
			stages.put(data.stageIndex, data);
		}
	}

	public static StagesLoader forId(int stageId) {
		return stages.get(stageId);
	}
	
	int stageIndex, tutItemInvolved, dialogueId;
	int[] sideBarEnabled;
	String tutorName;
	
	public abstract void sendInterfaces(Client c, boolean send);
	
	StagesLoader(int stageIndex, int[] sideBarEnabled, int tutItemInvolved, int dialogueId, String tutorName) {
		this.stageIndex = stageIndex;
		this.sideBarEnabled = sideBarEnabled;
		this.tutItemInvolved = tutItemInvolved;
		this.dialogueId = dialogueId;
		this.tutorName = tutorName;
	}//5options
	
	StagesLoader(int stageIndex, int dialogueId, int[] sideBarEnabled) {
		this.stageIndex = stageIndex;
		this.sideBarEnabled = sideBarEnabled;
		this.dialogueId = dialogueId;
	}//3options

	StagesLoader(int stageIndex, int[] sideBarEnabled) {
		this.stageIndex = stageIndex;
		this.sideBarEnabled = sideBarEnabled;
	}//2options
	
	 public int getStage() {
         return stageIndex;
     }

	 public int[] getSideBar() {
         return sideBarEnabled;
     }

	 public int getTutItems() {
         return tutItemInvolved;
     }

	 public int getDialogueId() {
         return dialogueId;
     }

	 public String getTutorName() {
         return tutorName;
     }
}