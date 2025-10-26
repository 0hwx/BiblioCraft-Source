package jds.bibliocraft.models;

import net.minecraftforge.client.model.IModelCustom;

public class ModelSeat extends BiblioModelSimple
{

    public ModelSeat() {
        super("seat");
    }

    public void Stool()
    {
        this.model.renderPart("stool_center");
        this.model.renderPart("stool_west");
        this.model.renderPart("stool_east");
        this.model.renderPart("stool_north");
        this.model.renderPart("stool_south");
        this.model.renderPart("stool_northeast");
        this.model.renderPart("stool_southeast");
        this.model.renderPart("stool_southwest");
        this.model.renderPart("stool_northwest");
    }


    public enum ModelPart
    {
        stool("stool", "Wood"),
        ;

        private String name;
        private String texture;
        private static IModelCustom model;

        ModelPart(String name , String texture)
        {
            this.name = name;
            this.texture = texture;
        }
        public String getTexture()
        {
            return this.texture;
        }

        public String getName()
        {
            return this.name;
        }

        public void render() {
            this.model.renderPart(this.name);
        }

        public static void setModel(IModelCustom setmodel)
        {
            model = setmodel;
        }


    }
}
