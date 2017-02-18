package goods;

import com.google.common.collect.ImmutableList;

public class GoodInfo {

    public final int id;
    public final String name;
    public final ImmutableList<GoodTag> tags;

    // todo good "properties"

    GoodInfo(int id, String name, ImmutableList<GoodTag> tags) {
        this.id = id;
        this.name = name;
        this.tags = tags;
    }

}
