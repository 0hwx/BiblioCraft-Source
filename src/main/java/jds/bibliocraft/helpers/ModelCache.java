package jds.bibliocraft.helpers;

import net.minecraftforge.client.model.IModelCustom;

import java.util.ArrayList;


public class ModelCache
{
	private ArrayList<ModelCachePackage> models;
	private IModelCustom currentMatch;

	public ModelCache()
	{
		models = new ArrayList<ModelCachePackage>();
		currentMatch = null;
	}

	public void addToCache(IModelCustom model, String name)
	{
		ModelCachePackage pack = new ModelCachePackage(model, name);
		models.add(pack);
	}

	public boolean hasModel(String name)
	{
		boolean output = false;
		for (int i = 0; i < models.size(); i++)
		{
			ModelCachePackage pack = models.get(i);
			if (pack.getTextureName().contentEquals(name))
			{
				output = true;
				currentMatch = pack.getModel();
				break;
			}
		}
		return output;
	}

	public IModelCustom getCurrentMatch()
	{
		return this.currentMatch;
	}



}
