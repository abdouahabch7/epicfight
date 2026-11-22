package yesman.epicfight.registry.entries;

import java.util.EnumMap;
import java.util.List;

import net.minecraft.util.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import yesman.epicfight.main.EpicFightMod;

public final class EpicFightArmorMaterials {
	private EpicFightArmorMaterials() {}
	
	public static final DeferredRegister<ArmorMaterial> REGISTRY = DeferredRegister.create(Registries.ARMOR_MATERIAL, EpicFightMod.MODID);
	
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> STRAY_CLOTH =
		REGISTRY.register(
			  "stray_cloth"
			, () ->
				new ArmorMaterial(
					Util.make(
						new EnumMap<>(net.minecraft.world.item.equipment.ArmorType.class),
						enumMap -> {
					        enumMap.put(net.minecraft.world.item.equipment.ArmorType.BOOTS, 1);
					        enumMap.put(net.minecraft.world.item.equipment.ArmorType.LEGGINGS, 2);
					        enumMap.put(net.minecraft.world.item.equipment.ArmorType.CHESTPLATE, 3);
					        enumMap.put(net.minecraft.world.item.equipment.ArmorType.HELMET, 1);
					        enumMap.put(net.minecraft.world.item.equipment.ArmorType.BODY, 2);
						}
					)
					, 15
					, SoundEvents.ARMOR_EQUIP_LEATHER
					, () -> Ingredient.of(Items.STRING)
					, List.of(new ArmorMaterial.Layer(EpicFightMod.identifier("stray_cloth")))
					, 0.0F
					, 0.0F
				)
		);
}