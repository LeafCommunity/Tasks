/*
 * Copyright © 2021-2026, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks.executors;

@FunctionalInterface
public interface ExecutorTaskSource extends ExecutorServiceSource {
	default ExecutorTaskBuilder async() {
		return new ExecutorTaskBuilder(executor());
	}
}
