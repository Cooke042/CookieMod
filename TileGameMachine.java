package CookieMod;

import java.util.EnumSet;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
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
import net.minecraftforge.client.IItemRenderer;

public class TileGameMachine extends TileEntity implements ITickHandler{

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

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		float time = (Float)tickData[0];
		System.out.println(time);
		
		
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {	
		//float time = (Float)tickData[0];
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.RENDER);
	}

	@Override
	public String getLabel() {
		return null;
	}

	public void renderUpdate(float time) {
		gamescreen.borderPixel.color.x += 
	}
}