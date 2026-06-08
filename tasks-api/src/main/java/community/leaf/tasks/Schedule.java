/*
 * Copyright © 2021-2026, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks;

@SuppressWarnings("unused")
public interface Schedule {
	static Schedule of(long delay, long period, RepeatsExpected repeats) {
		return new ScheduleImpl(delay, period, repeats);
	}
	
	static Schedule immutable(Schedule schedule) {
		if (schedule instanceof ScheduleImpl) {
			return schedule;
		}
		return of(schedule.delay(), schedule.period(), schedule.repeats());
	}
	
	long delay();
	
	long period();
	
	RepeatsExpected repeats();
	
	default boolean isDelayed() {
		return delay() > 0;
	}
	
	default boolean isRepeating() {
		return repeats().until() != Repeats.NEVER;
	}
}
