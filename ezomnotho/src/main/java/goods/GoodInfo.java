package goods;

import com.google.common.collect.ImmutableList;

public class GoodInfo {

    public final int id;
    public final GoodName name;
    public final ImmutableList<GoodTag> tags;
    public final ImmutableList<SpecialGoodProperties> properties;


    // todo good "properties"

    private GoodInfo(int id, GoodName name, ImmutableList<GoodTag> tags, ImmutableList<SpecialGoodProperties> properties) {
        this.id = id;
        this.name = name;
        this.tags = tags;
        this.properties = properties;
    }

    public static GoodInfo create(int id, GoodName name, ImmutableList<GoodTag> tags) {
        return new GoodInfo(id, name, tags, ImmutableList.of());
    }

    public static GoodInfo createSpecial(int id,
                                         GoodName name,
                                         ImmutableList<GoodTag> tags,
                                         ImmutableList<SpecialGoodProperties> properties) {
        return new GoodInfo(id, name, tags, properties);
    }
}
