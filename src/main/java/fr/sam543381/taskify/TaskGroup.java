package fr.sam543381.taskify;

import java.io.PrintStream;

public class TaskGroup extends Task {

	protected Task[] tasks;
	protected PrintStream logger;

	public TaskGroup(String name, int tries, long interval, PrintStream logger, final Task[] tasks) {
		super("(TaskGroup)" + name, tries, interval, logger);
		this.logger = logger;
		this.tasks = tasks;
	}

	public TaskGroup(String name, int tries, long interval, final Task[] tasks) {
		this("(TaskGroup)" + name, tries, interval, System.out, tasks);
	}

	@Override
	public int make() throws Exception {

		for (int i = 0; i < tasks.length; i++) {
			Task t = tasks[i];
			int exit = t.execute();

			if (exit == 0)
				for (Object o : t.getResults())
					results.add(o);
			else
				return i + 1; /* Error code is based on the task's id */
		}

		return 0;
	}
}
