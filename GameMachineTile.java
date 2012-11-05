package cookieMod;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet132TileEntityData;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntitySpecialRenderer;

public class GameMachineTile extends TileEntity {
	
	// general params
	int modelid;
	int facingDir;
	
	// drawFlags
	boolean drawWire = false;
	
	// screen data
	private Screen gamescreen;
		
	public GameMachineTile() {
		gamescreen = new Screen(20, 16, 16);
	}
	
	public Screen getGamescreen() {
		return gamescreen;
	}
	
	@Override
		public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.modelid = nbt.getInteger("modelid");
		this.facingDir = nbt.getInteger("facing");
		this.drawWire = nbt.getBoolean("wire");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("facing", this.facingDir);
		nbt.setInteger("modelid", this.modelid);
		nbt.setBoolean("wire", this.drawWire);
		
        NBTTagList itemList = new NBTTagList();
        nbt.setTag("Inventory", itemList);
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	
	float speed = .05f;
	@Override
	public void updateEntity() {
	}
	
    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
        this.readFromNBT(pkt.customParam1);
    }
    
    
   
    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
    }
}