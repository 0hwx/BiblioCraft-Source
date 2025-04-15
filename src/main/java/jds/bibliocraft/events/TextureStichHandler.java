//package jds.bibliocraft.events;
//
//import cpw.mods.fml.common.eventhandler.SubscribeEvent;
//import jds.bibliocraft.helpers.PaintingUtil;
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.client.event.TextureStitchEvent;
//
//public class TextureStichHandler
//{
//	public static final TextureStichHandler instance = new TextureStichHandler();
//
//	public TextureStichHandler() {}
//
//	 @SubscribeEvent
//	 public void onTextureStichEvent(TextureStitchEvent event)
//	 {
//		 // any custom textures I need to use in my models should be registered here
//		 event.map.registerIcon("bibliocraft:blocks/frame");
//		 event.map.registerIcon("bibliocraft:models/bookcase_books");
//		 event.map.registerIcon("bibliocraft:models/waypointcompass");
//		 event.map.registerIcon("bibliocraft:models/markerpole");
//		 event.map.registerIcon("bibliocraft:models/clipboard");
//		 event.map.registerIcon("bibliocraft:models/lamp");
//		 event.map.registerIcon("bibliocraft:models/lamp_iron");
//		 event.map.registerIcon("bibliocraft:models/lantern");
//		 event.map.registerIcon("bibliocraft:models/lantern_iron");
//		 event.map.registerIcon("bibliocraft:models/lamplight0");
//		 event.map.registerIcon("bibliocraft:models/lamplight1");
//		 event.map.registerIcon("bibliocraft:models/lamplight2");
//		 event.map.registerIcon("bibliocraft:models/lamplight3");
//		 event.map.registerIcon("bibliocraft:models/lamplight4");
//		 event.map.registerIcon("bibliocraft:models/lamplight5");
//		 event.map.registerIcon("bibliocraft:models/lamplight6");
//		 event.map.registerIcon("bibliocraft:models/lamplight7");
//		 event.map.registerIcon("bibliocraft:models/lamplight8");
//		 event.map.registerIcon("bibliocraft:models/lamplight9");
//		 event.map.registerIcon("bibliocraft:models/lamplight10");
//		 event.map.registerIcon("bibliocraft:models/lamplight11");
//		 event.map.registerIcon("bibliocraft:models/lamplight12");
//		 event.map.registerIcon("bibliocraft:models/lamplight13");
//		 event.map.registerIcon("bibliocraft:models/lamplight14");
//		 event.map.registerIcon("bibliocraft:models/lamplight15");
//		 event.map.registerIcon("bibliocraft:models/lanterncandle0");
//		 event.map.registerIcon("bibliocraft:models/lanterncandle1");
//		 event.map.registerIcon("bibliocraft:models/lanterncandle2");
//		 event.map.registerIcon("bibliocraft:models/lanterncandle3");
//		 event.map.registerIcon("bibliocraft:models/lanterncandle4");
//		 event.map.registerIcon("bibliocraft:models/lanterncandle5");
//		 event.map.registerIcon("bibliocraft:models/lanterncandle6");
//		 event.map.registerIcon("bibliocraft:models/lanterncandle7");
//		 event.map.registerIcon("bibliocraft:models/lanterncandle8");
//		 event.map.registerIcon("bibliocraft:models/lanterncandle9");
//		 event.map.registerIcon("bibliocraft:models/lanterncandle10");
//		 event.map.registerIcon("bibliocraft:models/lanterncandle11");
//		 event.map.registerIcon("bibliocraft:models/lanterncandle12");
//		 event.map.registerIcon("bibliocraft:models/lanterncandle13");
//		 event.map.registerIcon("bibliocraft:models/lanterncandle14");
//		 event.map.registerIcon("bibliocraft:models/lanterncandle15");
//		 event.map.registerIcon("bibliocraft:models/paneler");
//		 event.map.registerIcon("bibliocraft:models/typewriter_paper_blank");
//		 event.map.registerIcon("bibliocraft:models/benchsides");
//		 event.map.registerIcon("bibliocraft:models/sign_front");
//		 event.map.registerIcon("bibliocraft:models/desk_books");
//		 event.map.registerIcon("bibliocraft:models/clock");
//		 event.map.registerIcon("bibliocraft:models/maptool");
//		 event.map.registerIcon("bibliocraft:models/maptool");
//		 event.map.registerIcon("bibliocraft:models/typewriter0");
//		 event.map.registerIcon("bibliocraft:models/typewriter1");
//		 event.map.registerIcon("bibliocraft:models/typewriter2");
//		 event.map.registerIcon("bibliocraft:models/typewriter3");
//		 event.map.registerIcon("bibliocraft:models/typewriter4");
//		 event.map.registerIcon("bibliocraft:models/typewriter5");
//		 event.map.registerIcon("bibliocraft:models/typewriter6");
//		 event.map.registerIcon("bibliocraft:models/typewriter7");
//		 event.map.registerIcon("bibliocraft:models/typewriter8");
//		 event.map.registerIcon("bibliocraft:models/typewriter9");
//		 event.map.registerIcon("bibliocraft:models/typewriter10");
//		 event.map.registerIcon("bibliocraft:models/typewriter11");
//		 event.map.registerIcon("bibliocraft:models/typewriter12");
//		 event.map.registerIcon("bibliocraft:models/typewriter13");
//		 event.map.registerIcon("bibliocraft:models/typewriter14");
//		 event.map.registerIcon("bibliocraft:models/typewriter15");
//		 event.map.registerIcon("bibliocraft:models/typewriter_paper_blank");
//		 event.map.registerIcon("bibliocraft:models/typewriter_paper_1");
//		 event.map.registerIcon("bibliocraft:models/typewriter_paper_2");
//		 event.map.registerIcon("bibliocraft:models/typewriter_paper_3");
//		 event.map.registerIcon("bibliocraft:models/typewriter_paper_4");
//		 event.map.registerIcon("bibliocraft:models/typewriter_paper_5");
//		 event.map.registerIcon("bibliocraft:models/typewriter_paper_6");
//		 event.map.registerIcon("bibliocraft:models/typewriter_paper_7");
//		 event.map.registerIcon("bibliocraft:models/typewriter_paper_8");
//		 event.map.registerIcon("bibliocraft:models/typewriter_paper_9");
//		 event.map.registerIcon("bibliocraft:models/typewriter_paper_10");
//		 event.map.registerIcon("bibliocraft:models/typewriter_paper_11");
//		 event.map.registerIcon("bibliocraft:models/typewriter_paper_12");
//		 event.map.registerIcon("bibliocraft:models/typewriter_paper_13");
//		 event.map.registerIcon("bibliocraft:models/typewriter_paper_14");
//		 event.map.registerIcon("bibliocraft:models/typewriter_paper_15");
//		 event.map.registerIcon("bibliocraft:models/bell");
//		 event.map.registerIcon("bibliocraft:models/dinnerplate");
//		 event.map.registerIcon("bibliocraft:models/discrack");
//		 event.map.registerIcon("bibliocraft:models/painting_press");
//		 event.map.registerIcon("bibliocraft:models/sword_pedestal");
//		 event.map.registerIcon("bibliocraft:models/armorstand");
//		 event.map.registerIcon("bibliocraft:paintings/canvas");
//		 event.map.registerIcon("bibliocraft:models/typesettingtable");
//		 event.map.registerIcon("bibliocraft:models/printpress");
//		 event.map.registerIcon("bibliocraft:models/inkplate0");
//		 event.map.registerIcon("bibliocraft:models/inkplate1");
//		 event.map.registerIcon("bibliocraft:models/inkplate2");
//		 event.map.registerIcon("bibliocraft:models/inkplate3");
//		 event.map.registerIcon("bibliocraft:models/inkplate4");
//		 event.map.registerIcon("bibliocraft:models/inkplate5");
//		 event.map.registerIcon("bibliocraft:models/inkplate6");
//		 event.map.registerIcon("bibliocraft:models/inkplate7");
//		 event.map.registerIcon("bibliocraft:models/inkplate8");
//		 event.map.registerIcon(getTextureName("items/atlas"));
//		 event.map.registerIcon("bibliocraft:gui/atlas_cover");
//		 event.map.registerIcon("bibliocraft:items/clipboardsimple");
//
//		 event.map.registerIcon("bibliocraft:paintings/64painting01z");
//		 event.map.registerIcon("bibliocraft:paintings/32painting02z");
//		 event.map.registerIcon("bibliocraft:paintings/32painting03z");
//		 event.map.registerIcon("bibliocraft:paintings/64painting04z");
//		 event.map.registerIcon("bibliocraft:paintings/64painting05z");
//		 event.map.registerIcon("bibliocraft:paintings/32collage");
//		 event.map.registerIcon("bibliocraft:paintings/32pie");
//		 event.map.registerIcon("bibliocraft:paintings/boathouse_64");
//		 event.map.registerIcon("bibliocraft:paintings/jimi_32");
//		 event.map.registerIcon("bibliocraft:paintings/raven_32");
//		 event.map.registerIcon("bibliocraft:paintings/vanilla");
//		 event.map.registerIcon("bibliocraft:paintings/custom");
//		 event.map.registerIcon("bibliocraft:models/deathcompass");
//
//
//		 PaintingUtil.updateCustomArtDatas();
//		 if (PaintingUtil.customArtResources != null)
//		 {
//			 for (int i = 0; i < PaintingUtil.customArtResources.length; i++)
//			 {
//				 if (PaintingUtil.customArtHeights[i] == PaintingUtil.customArtWidths[i])
//				 {
//					 // only registers textures with a 1:1 aspect ratio for use with the canvas item model.
//					 event.map.registerIcon(PaintingUtil.customArtResourceStrings[i]);
//				 }
//			 }
//		 }
//	 }
//     private String getTextureName(String name) {
//         final ResourceLocation sprite = new ResourceLocation("bibliocraft", name);
//         return sprite.toString();
//     }
//}
