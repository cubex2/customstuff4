package cubex2.cs4.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializer;
import cubex2.cs4.api.*;
import cubex2.cs4.plugins.vanilla.BlockTintRegistry;
import cubex2.cs4.plugins.vanilla.ColorRegistry;
import cubex2.cs4.plugins.vanilla.ItemModuleRegistry;
import cubex2.cs4.plugins.vanilla.TileEntityModuleRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

public class ContentRegistryImpl implements ContentRegistry, DeserializationRegistry, LoaderPredicateRegistry,
        TileEntityModuleRegistry, BlockTintRegistry, ColorRegistry, ItemModuleRegistry
{
    private final Map<String, Class<? extends Content>> types = Maps.newHashMap();
    private final Map<String, Side> typeSides = Maps.newHashMap();
    private final Map<String, LoaderPredicate> predicates = Maps.newHashMap();
    private final List<Pair<Type, JsonDeserializer<?>>> deserializers = Lists.newArrayList();
    private final Map<String, Class<? extends TileEntityModuleSupplier>> tileEntityModules = Maps.newHashMap();
    private final Map<String, Class<? extends ItemModuleSupplier>> itemModules = Maps.newHashMap();
    private final Map<String, BlockTint> blockTintFunctions = Maps.newHashMap();
    private final Map<String, Integer> colors = Maps.newHashMap();

    @Override
    public <T extends Content> void registerContentType(String typeName, Class<T> clazz)
    {
        checkArgument(!types.containsKey(typeName), "Duplicate typeName: %s", typeName);

        types.put(typeName, clazz);
    }

    @Override
    public <T extends Content> void registerContentType(String typeName, Class<T> clazz, Side side)
    {
        registerContentType(typeName, clazz);

        typeSides.put(typeName, side);
    }

    @Nullable
    Class<? extends Content> getContentClass(String typeName)
    {
        if (typeSides.containsKey(typeName)
            && FMLCommonHandler.instance().getSide() != typeSides.get(typeName))
            return null;

        return types.get(typeName);
    }

    @Override
    public <T> void registerDeserializer(Type type, JsonDeserializer<T> deserializer)
    {
        deserializers.add(Pair.of(type, deserializer));
    }

    @Override
    public List<Pair<Type, JsonDeserializer<?>>> getDeserializers()
    {
        return deserializers;
    }

    @Override
    public void registerLoaderPredicate(String name, LoaderPredicate predicate)
    {
        checkArgument(!predicates.containsKey(name), "Duplicate predicate name: %s", name);

        predicates.put(name, predicate);
    }

    @Nullable
    @Override
    public LoaderPredicate getPredicate(String name)
    {
        return predicates.get(name);
    }

    @Override
    public <T extends TileEntityModuleSupplier> void registerTileEntityModule(String typeName, Class<T> clazz)
    {
        checkArgument(!tileEntityModules.containsKey("name"), "Duplicate tile entity module name: %s", typeName);

        tileEntityModules.put(typeName, clazz);
    }

    @Override
    public Class<? extends TileEntityModuleSupplier> getTileEntityModuleClass(String typeName)
    {
        return tileEntityModules.get(typeName);
    }

    @Override
    public <T extends ItemModuleSupplier> void registerItemModule(String typeName, Class<T> clazz)
    {
        checkArgument(!itemModules.containsKey("name"), "Duplicate item module name: %s", typeName);

        itemModules.put(typeName, clazz);
    }

    @Override
    public Class<? extends ItemModuleSupplier> getItemModuleClass(String typeName)
    {
        return itemModules.get(typeName);
    }

    @Override
    public void registerBlockTint(String name, BlockTint tint)
    {
        checkArgument(!blockTintFunctions.containsKey(name), "Duplicate block tint name: %s", name);

        blockTintFunctions.put(name, tint);
    }

    @Nullable
    @Override
    public BlockTint getBlockTint(String name)
    {
        return blockTintFunctions.get(name);
    }

    @Override
    public void registerColor(String name, int rgba)
    {
        checkArgument(!colors.containsKey(name), "Duplicate color name: %s", name);

        colors.put(name, rgba);
    }

    @Override
    public int getColor(String name)
    {
        return colors.getOrDefault(name, -1);
    }
}
