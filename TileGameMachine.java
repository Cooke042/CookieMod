package CookieMod;

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

public class TileGameMachine extends TileEntity {

	// general params
	private int modelid;
	int facingDir;

	// drawFlags
	boolean drawWire = false;

	// screen data
	private Screen gamescreen;

	public TileGameMachine() {
		setModel(modelid);
	}
    
	
	
	public Boolean setModel(int modelid) {

		if (modelid < Model.models.size())
		{
			this.modelid = modelid;
			Model model = Model.models.get(modelid);
			if (model != null && model.hasScreen()) {
				gamescreen = new Screen(40, 32);
				gamescreen.setPixelAspect(model.screenwidth, model.screenheight);
			}

			return true;
		}
		System.out.println("Model Id out of bounds!");
		return false;
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

	public Screen getGamescreen() {
		return gamescreen;
	}

	public int getModelid() {
		return modelid;
	}
}