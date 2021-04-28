package community.leaf.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class UnlessBuilderImpl implements Unless.Builder
{
    private final List<Unless> caveats = new ArrayList<>();
    
    @Override
    public UnlessBuilderImpl unless(Unless criteria)
    {
        caveats.add(Objects.requireNonNull(criteria, "criteria"));
        return this;
    }
    
    @Override
    public Unless unless()
    {
        if (caveats.isEmpty()) { return Unless::never; }
        List<Unless> copy = List.copyOf(caveats);
        return Unless.any(copy);
    }
}
