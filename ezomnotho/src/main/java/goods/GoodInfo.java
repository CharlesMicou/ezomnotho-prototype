package goods;

import com.google.common.collect.ImmutableList;

public class GoodInfo {

    public final GoodId id;
    public final ImmutableList<GoodTag> tags;
    public final ImmutableList<SpecialGoodProperties> properties;


    // todo good "properties"

    private GoodInfo(GoodId id, ImmutableList<GoodTag> tags, ImmutableList<SpecialGoodProperties> properties) {
        this.id = id;
        this.tags = tags;
        this.properties = properties;
    }

    public static GoodInfo create(GoodId id, ImmutableList<GoodTag> tags) {
        return new GoodInfo(id, tags, ImmutableList.of());
    }

    public static GoodInfo createSpecial(GoodId id,
                                         ImmutableList<GoodTag> tags,
                                         ImmutableList<SpecialGoodProperties> properties) {
        return new GoodInfo(id, tags, properties);
    }
}
