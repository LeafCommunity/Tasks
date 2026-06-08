package community.leaf.tasks;

import java.util.function.LongFunction;

public interface PendingConstructor<B, P extends Pending<B>> {
	P construct(LongFunction<B> function, long units);
}
