package server.game.dialogues;

import server.game.players.Client;

public class DialogueHandler {

		public Client c;

		public DialogueHandler(Client client) {
			this.c = client;
		}


		/*
		 * Information Box
		 */

		public void sendNpcChat1(String s, int ChatNpc, String name) {
			c.getPA().sendFrame200(4883, 591);
			c.getPA().sendFrame126(name, 4884);
			c.getPA().sendFrame126(s, 4885);
			c.getPA().sendFrame75(ChatNpc, 4883);
			c.getPA().sendFrame164(4882);
		}
		
		
		public void sendNpcChat2(String s, String s1, int ChatNpc, String name) {
			c.getPA().sendFrame200(4888, 591);
			c.getPA().sendFrame126(name, 4889);
			c.getPA().sendFrame126(s, 4890);
			c.getPA().sendFrame126(s1, 4891);
			c.getPA().sendFrame75(ChatNpc, 4888);
			c.getPA().sendFrame164(4887);
		}
		
			public void sendNpcChat3(String s, String s1, String s2, int ChatNpc, String name) {
			c.getPA().sendFrame200(4894, 591);
			c.getPA().sendFrame126(name, 4895);
			c.getPA().sendFrame126(s, 4896);
			c.getPA().sendFrame126(s1, 4897);
			c.getPA().sendFrame126(s2, 4898);
			c.getPA().sendFrame75(ChatNpc, 4894);
			c.getPA().sendFrame164(4893);
		}

		public void sendStartInfo(String text, String text1, String text2,
				String text3, String title, boolean send) {
			c.getPA().sendFrame126(title, 6180);
			c.getPA().sendFrame126(text, 6181);
			c.getPA().sendFrame126(text1, 6182);
			c.getPA().sendFrame126(text2, 6183);
			c.getPA().sendFrame126(text3, 6184);
			c.getPA().sendFrame164(6179);
		}



		/*
		 * Options
		 */
		
		public void sendPlayerChat1(String s) {
			c.getPA().sendFrame200(969, 591);
			c.getPA().sendFrame126(c.playerName, 970);
			c.getPA().sendFrame126(s, 971);
			c.getPA().sendFrame185(969);
			c.getPA().sendFrame164(968);
		}
		
		public void sendPlayerChat2(String s, String s1) {
			c.getPA().sendFrame200(974, 591);
			c.getPA().sendFrame126(c.playerName, 975);
			c.getPA().sendFrame126(s, 976);
			c.getPA().sendFrame126(s1, 977);
			c.getPA().sendFrame185(974);
			c.getPA().sendFrame164(973);
		}

		 public void sendOption2(String s, String s1) {
		        c.getPA().sendFrame126("Select an Option", 2460);
		        c.getPA().sendFrame126(s, 2461);
		        c.getPA().sendFrame126(s1, 2462);
		        c.getPA().sendFrame164(2459);
		    }
		
		public void sendOption3(String s, String s1, String s2) {
			c.getPA().sendFrame126("Select an Option", 2470);
			c.getPA().sendFrame126(s, 2471);
			c.getPA().sendFrame126(s1, 2472);
			c.getPA().sendFrame126(s2, 2473);
			c.getPA().sendFrame164(2469);
		}

		public void sendOption4(String s, String s1, String s2, String s3) {
			c.getPA().sendFrame126("Select an Option", 2481);
			c.getPA().sendFrame126(s, 2482);
			c.getPA().sendFrame126(s1, 2483);
			c.getPA().sendFrame126(s2, 2484);
			c.getPA().sendFrame126(s3, 2485);
			c.getPA().sendFrame164(2480);
		}

		public void sendOption5(String s, String s1, String s2, String s3, String s4) {
			c.getPA().sendFrame126("Select an Option", 2493);
			c.getPA().sendFrame126(s, 2494);
			c.getPA().sendFrame126(s1, 2495);
			c.getPA().sendFrame126(s2, 2496);
			c.getPA().sendFrame126(s3, 2497);
			c.getPA().sendFrame126(s4, 2498);
			c.getPA().sendFrame164(2492);
		}

		/*
		 * Statements
		 */

		public void sendStatement(String s) { // 1 line click here to continue chat
												// box interface
			c.getPA().sendFrame126(s, 357);
			c.getPA().sendFrame126("Click here to continue", 358);
			c.getPA().sendFrame164(356);
		}
		public void sendStatement4(String s, String s1, String s2, String s3) {
			c.getPA().sendFrame126(s, 369);
			c.getPA().sendFrame126(s1, 370);
			c.getPA().sendFrame126(s2, 371);
			c.getPA().sendFrame126(s3, 372);
			c.getPA().sendFrame126("Click here to continue", 373);
			c.getPA().sendFrame164(368);
		}

		
		public void itemMessage(String title, String message, int itemid, int size){
			c.getPA().sendFrame200(4883, 591);
			c.getPA().sendFrame126(title, 4884);
			c.getPA().sendFrame126(message, 4885);
			c.getPA().sendFrame126("Click here to continue.", 4886);
			c.getPA().sendFrame246(4883, size, itemid);
			c.getPA().sendFrame164(4882);
		}

		/*
		 * Npc Chatting
		 */

		public void sendNpcChat4(String s, String s1, String s2, String s3,
				int ChatNpc, String name) {
			c.getPA().sendFrame200(4901, 591);
			c.getPA().sendFrame126(name, 4902);
			c.getPA().sendFrame126(s, 4903);
			c.getPA().sendFrame126(s1, 4904);
			c.getPA().sendFrame126(s2, 4905);
			c.getPA().sendFrame126(s3, 4906);
			c.getPA().sendFrame75(ChatNpc, 4901);
			c.getPA().sendFrame164(4900);
		}

		public void sendStatement2(Client c,String s, String s1) {
			c.getPA().sendFrame126(s, 360);
			c.getPA().sendFrame126(s1, 361);
			c.getPA().sendFrame126("Click here to continue", 362);
			c.getPA().sendFrame164(359);
		}
		public static void sendStatement(Client c,String s) {
			c.getPA().sendFrame126(s, 357);
			c.getPA().sendFrame126("Click here to continue", 358);
			c.getPA().sendFrame164(356);
		}
		
		/*
		 * 	Tutorial interface
		 */
		
		public void sendStartInfo(String text, String text1, String text2, String text3, String title) {
			c.getPA().sendFrame126(title, 6180);
			c.getPA().sendFrame126(text, 6181);
			c.getPA().sendFrame126(text1, 6182);
			c.getPA().sendFrame126(text2, 6183);
			c.getPA().sendFrame126(text3, 6184);
			c.getPA().sendFrame164(6179);
		}

		
		/*
		 *ItemInformation Box
		 */
		
		public void itemMessage1(String message1, int itemid, int size) {
			c.getPA().sendFrame200(307, 591);
			c.getPA().sendFrame126(message1, 308);
			c.getPA().sendFrame246(307, size, itemid);
			c.getPA().sendFrame164(306);
			c.nextChat = 0;
		}
		
		/*
		 * Give items
		 */
		
		   public void sendGiveItemNpc(String text1, String text2, int item1, int item2) {
			    c.getPA().sendFrame126(text1, 6232);
			    c.getPA().sendFrame126(text2, 6233);
			    c.getPA().sendFrame246(6235, 170, item1);
			    c.getPA().sendFrame246(6236, 170, item2);
			    c.getPA().sendFrame164(6231);
			}
			 
			public void sendGiveItemNpc(String text, int item) {
			    c.getPA().sendFrame126(text, 308);
			    c.getPA().sendFrame246(307, 200, item);
			    c.getPA().sendFrame164(306);
			}
		
		


		/*
		 * Player Chating Back
		 */
	}