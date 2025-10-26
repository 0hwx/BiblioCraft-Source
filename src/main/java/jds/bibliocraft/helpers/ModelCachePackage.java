package jds.bibliocraft.helpers;


import net.minecraftforge.client.model.IModelCustom;

public class ModelCachePackage
{
	private IModelCustom model;
	private String name;

	public ModelCachePackage(IModelCustom modelIn, String nameIn)
	{
		this.model = modelIn;
		this.name = nameIn;
	}

	public IModelCustom getModel()
	{
		return this.model;
	}

	public String getTextureName()
	{
		return this.name;
	}
}
