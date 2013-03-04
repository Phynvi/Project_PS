package server.game.npcs;

import server.game.content.randomevents.EventHandler;
import server.game.players.Client;

/**
 *@author Andrew
 */

public class Shops {
	
	public static int[][] firstsecond = {
        {588, 2},
        {550, 3},
        {575, 4},
        {2356, 5},
        {3796, 6},
        {1860, 7},
        {559, 9},
        {562, 10},
        {581, 11},
        {548, 12},
        {554, 13},
        {601, 14},
        {1301, 15},
        {1039, 16},
        {2353, 17},
        {3166, 18},
        {2161, 19},
        {2162, 20},
        {600, 21},
        {603, 22},
        {593, 23},
        {545, 24},
        {585, 25},
        {2305, 26},
        {2307, 27},
        {2304, 28},
        {2306, 29},
        {517, 30},
        {558, 31},
        {576, 32},
        {1369, 33},
        {1038, 35},
        {1433, 36},
        {584, 37},
        {540, 38},
        {2157, 39},
        {538, 40},
        {1303, 41},
        {578, 42},
        {587, 43},
        {1398, 44},
        {556, 45},
        {1865, 46},
        {543, 47},
        {2198, 48},
        {580, 49},
        {1862, 50},
        {583, 51},
        {553, 52},
        {461, 53},
        {903, 54},
        {1435, 56},
        {3800, 57},
        {2623, 58},
        {594, 59},
        {579, 60},
        {2160, 61},
        {2191, 61},
        {589, 62},
        {549, 63},
        {542, 64},
        {3038, 65},
        {544, 66},
        {541, 67},
        {1434, 68},
        {577, 69},
        {539, 70},
        {1980, 71},
        {546, 72},
        {382, 73},
        {3541, 74},
        {520, 75},
        {1436, 76},
        {590, 77},
        {971, 78},
        {1917, 79},
        {1040, 80},
        {563, 81},
        {522, 82},
        {524, 83},
        {526, 84},
        {2154, 85},
        {1334, 86},
        {2552, 87},
        {528, 88},
        {1254, 89},
        {2086, 90},
        {3824, 91},
        {1866, 92},
        {1699, 93},
        {1282, 94},
        {530, 95},
        {516, 96},
        {560, 97},
        {471, 98},
        {1208, 99},
        {532, 100},
        {555, 101},
        {534, 102},
        {551, 104},
        {586, 105},
        {564, 106},
        {747, 107},
        {573, 108},
        {1316, 108},
        {547, 108},
        {1787, 110},
        {568, 111},
        {1526, 112}};
	
	public static int[][] third = {
        {70, 109},
        {1596, 109},
        {1597, 109},
        {1598, 109},
        {1599, 109}};
	
	public static void openShop(Client c, final int npcClickId) {
		 for (int[] data : firstsecond) {
	            final int npcId = data[0];
	            int shopId = data[1];
	            if (npcClickId == npcId) {
	                c.getShops().openShop(shopId);
	                EventHandler.randomEvents(c);
	           }
	        }
		}

	public static void openShop2(Client c, final int npcClickId) {
	 for (int[] data : firstsecond) {
           final int npcId = data[0];
           int shopId = data[1];
           if (npcClickId == npcId) {
               c.getShops().openShop(shopId);
               EventHandler.randomEvents(c);
	       	}
		}
	}

	public static void openShop3(Client c, final int npcClickId) {
	 for (int[] data : third) {
          final int npcId = data[0];
          int shopId = data[1];
          if (npcClickId == npcId) {
              c.getShops().openShop(shopId);
              EventHandler.randomEvents(c);
          	}
      	}
	 }
}