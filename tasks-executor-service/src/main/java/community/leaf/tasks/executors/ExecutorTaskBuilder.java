package community.leaf.tasks.executors;

import community.leaf.tasks.AbstractTaskBuilder;
import community.leaf.tasks.ContextualRunnable;
import community.leaf.tasks.Pending;
import community.leaf.tasks.Repeats;
import community.leaf.tasks.Schedule;
import community.leaf.tasks.TaskContext;

import java.util.Objects;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorTaskBuilder
	extends AbstractTaskBuilder<Future<?>, ExecutorTaskBuilder, Pending<ExecutorTaskBuilder>> {
	private final ScheduledExecutorService executor;
	
	public ExecutorTaskBuilder(ScheduledExecutorService executor) {
		super(Pending::milliseconds);
		this.executor = Objects.requireNonNull(executor, "executor");
	}
	
	@Override
	public TaskContext<Future<?>> run(ContextualRunnable<Future<?>> runnable) {
		Objects.requireNonNull(runnable, "runnable");
		
		Schedule schedule = schedule();
		TaskContext<Future<?>> context = new ExecutorTaskContext(schedule);
		Runnable task = runnable.wrapWithContext(context);
		
		if (schedule.isRepeating()) {
			context.task(executor.scheduleAtFixedRate(
				task, schedule.delay(), schedule.period(), TimeUnit.MILLISECONDS
			));
		}
		else if (schedule.isDelayed()) {
			context.task(executor.schedule(
				task, schedule().delay(), TimeUnit.MILLISECONDS
			));
		}
		else {
			context.task(executor.submit(task));
		}
		
		return context;
	}
}
