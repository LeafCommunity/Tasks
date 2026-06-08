/*
 * Copyright © 2021-2026, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks;

import java.util.Objects;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public abstract class AbstractTaskBuilder<T, B extends ScheduledTaskBuilder<T, B>, P extends Pending<B>>
	implements ScheduledTaskBuilder<T, B> {
	protected final PendingConstructor<B, P> pending;
	
	private long delay;
	private long period;
	
	private RepeatsExpected repeats = RepeatsExpected.NEVER;
	
	protected AbstractTaskBuilder(PendingConstructor<B, P> pending) {
		this.pending = Objects.requireNonNull(pending, "pending");
	}
	
	@SuppressWarnings("unchecked")
	protected final B self() {
		return (B) this;
	}
	
	@Override
	public final Schedule schedule() {
		return Schedule.of(delay, period, repeats);
	}
	
	@Override
	public final B delayByMilliseconds(long milliseconds) {
		delay = milliseconds;
		return self();
	}
	
	@Override
	public P delay(long duration) {
		return pending.construct(this::delayByMilliseconds, duration);
	}
	
	@Override
	public final B everyFewMilliseconds(long milliseconds) {
		if (repeats.until() == Repeats.NEVER) {
			repeats = RepeatsExpected.FOREVER;
		}
		period = milliseconds;
		return self();
	}
	
	@Override
	public P every(long duration) {
		return pending.construct(this::everyFewMilliseconds, duration);
	}
	
	@Override
	public final B repeat(RepeatsExpected repeats) {
		this.repeats = Objects.requireNonNull(repeats, "repeats");
		return self();
	}
}
