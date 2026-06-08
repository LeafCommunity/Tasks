package community.leaf.tasks;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public interface ScheduleBuilder<B extends ScheduleBuilder<B>> extends ScheduleSource {
	B delayByMilliseconds(long milliseconds);
	
	default B delay(long duration, TimeUnit unit) {
		return delayByMilliseconds(TimeUnit.MILLISECONDS.convert(duration, unit));
	}
	
	default B delay(Duration duration) {
		return delayByMilliseconds(duration.toMillis());
	}
	
	Pending<B> delay(long duration);
	
	B everyFewMilliseconds(long milliseconds);
	
	default B every(long duration, TimeUnit unit) {
		return everyFewMilliseconds(TimeUnit.MILLISECONDS.convert(duration, unit));
	}
	
	default B every(Duration duration) {
		return delayByMilliseconds(duration.toMillis());
	}
	
	Pending<B> every(long duration);
	
	B repeat(RepeatsExpected repeats);
	
	default B repeat(long repetitions) {
		return repeat(RepeatsExpected.of(repetitions));
	}
	
	default B forever() {
		return repeat(RepeatsExpected.FOREVER);
	}
	
	default B once() {
		return repeat(RepeatsExpected.NEVER);
	}
}
