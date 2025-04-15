package jds.bibliocraft.helpers;

public enum EnumCustomDataType
{
	NONE(0, "None"),
	FANCY_SIGN(1, "FancySign");

	private int ID;
	private String name;
	private static final EnumCustomDataType[] META_LOOKUP = new EnumCustomDataType[values().length];

	private EnumCustomDataType(int id, String name)
	{
		this.ID = id;
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	public int getID()
	{
		return this.ID;
	}

	public static EnumCustomDataType getTypeFromID(int id)
	{
		EnumCustomDataType datatype = META_LOOKUP[id];
		return datatype;
	}

	static
	{
		for (EnumCustomDataType dt : values())
		{
			META_LOOKUP[dt.getID()] = dt;
		}
	}
}
