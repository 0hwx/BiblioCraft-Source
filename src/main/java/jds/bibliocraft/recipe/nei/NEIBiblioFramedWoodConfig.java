package jds.bibliocraft.recipe.nei;


import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.event.NEIRegisterHandlerInfosEvent;
import codechicken.nei.recipe.HandlerInfo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import jds.bibliocraft.blocks.BlockBookcase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class NEIBiblioFramedWoodConfig  implements IConfigureNEI {


    private static final NEIFramedRecipeHandler HANDLER_INSTANCE = new NEIFramedRecipeHandler();

    @Override
    public void loadConfig() {
        API.registerRecipeHandler(HANDLER_INSTANCE);
        API.registerUsageHandler(HANDLER_INSTANCE);

        API.addRecipeFilter(new BiblioRecipeFilterProvider());

    }

    @Override
    public String getName() {
        return "BiblioCraft NEI Integration";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @SubscribeEvent
    public void registerHandlerInfo(NEIRegisterHandlerInfosEvent event) {
        event.registerHandlerInfo(
            new HandlerInfo.Builder(
                HANDLER_INSTANCE.getOverlayIdentifier(),
                "BiblioCraft",
                "BiblioCraft"
            )
                .setDisplayStack(new ItemStack(Item.getItemFromBlock(BlockBookcase.instance)))
                .setMaxRecipesPerPage(2)
                .build()
        );
    }

}
