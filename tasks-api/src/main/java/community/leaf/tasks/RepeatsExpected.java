package community.leaf.tasks;

public record RepeatsExpected(Repeats until, long repetitions) {
	public static final RepeatsExpected NEVER =
		new RepeatsExpected(Repeats.NEVER, 0);
	
	public static final RepeatsExpected FOREVER =
		new RepeatsExpected(Repeats.FOREVER, -1);
	
	public static RepeatsExpected of(long repetitions) {
		if (Repeats.NEVER.accepts(repetitions)) {
			return NEVER;
		}
		if (Repeats.FOREVER.accepts(repetitions)) {
			return FOREVER;
		}
		return new RepeatsExpected(Repeats.FINITE, repetitions);
	}
	
	public RepeatsExpected {
		if (!until.accepts(repetitions)) {
			throw new IllegalArgumentException(
				"%s does not accept %d repetitions".formatted(until, repetitions)
			);
		}
	}
}
