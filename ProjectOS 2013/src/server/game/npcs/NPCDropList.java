package server.game.npcs;
 
import server.util.Misc;
 
public enum NPCDropList {
	//npcid, constantdrop, amount, drop, amount, rarirty
    GOBLIN(new int[] {101}, new int[] {526}, new int[] {1}, new int[] {995, 882, 1237, 1139, 1173}, new int[] {Misc.random(24), 16, 1, 1, 1}, new int[] {3, 7, 5, 5, 5}),
    MAN(new int[] {1}, new int[] {526}, new int [] {1}, new int[] {995, 1041}, new int[] {7, 10}, new int[] {2, 0}),
    MAN1(new int[] {2}, new int[] {526}, new int[] {1}, new int[] {995}, new int[] {7}, new int[] {2}),
    MAN2(new int[] {3}, new int[] {526}, new int[] {1}, new int[] {995}, new int[] {7}, new int[] {2}),
    GUARD(new int[] {9}, new int[] {526}, new int[] {1}, new int[] {995, 555}, new int[] {200, 100}, new int[] {5, 5});
 
    private int[] id; 
    private int[] constantDrops; 
    private int[] amount; 
    public int[] dropTables;
    public int[] dropTablesAmounts;
    public int[] dropTablesRarity;
     
    private NPCDropList(int[] id, int[] constantDrops, int[] amount, int[] dropTables, int[] dropTablesAmounts, int[] dropTablesRarity) {
        this.id = id;
        this.constantDrops = constantDrops;
        this.amount = amount;
        this.dropTables = dropTables;
        this.dropTablesAmounts = dropTablesAmounts;
        this.dropTablesRarity = dropTablesRarity;
    }
     
    public int[] getId() {
        return id;
    }
 
    public int[] getConstantDrop() {
        return constantDrops;
    }
     
    public int[] getAmount() {
        return amount;
    }
     
    public int getDropTables(int i) {
        return dropTables[i];
    }
     
    public int getDropTablesAmounts(int i) {
        return dropTablesAmounts[i];
    }
     
    public int getDropTablesRarity(int i) {
        return dropTablesRarity[i];
    }
}