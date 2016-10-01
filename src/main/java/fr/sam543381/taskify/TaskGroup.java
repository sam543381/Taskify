package fr.sam543381.taskify;

import java.io.PrintStream;

/**
 * @see fr.sam543381.taskify.Task
 * @author Sam54 (<a href="mailto:sam543381@live.fr">contact</a>)
 */
public class TaskGroup extends Task {

	protected Task[] tasks;
	protected PrintStream logger;

	/**
	 * @param name
	 *            The name of the task to create (printed in logs)
	 * @param tries
	 *            The number of times the system will try to execute the task if
	 *            the exit code is not 0
	 * @param interval
	 *            The time to wait between each time the system tries to execute
	 *            the task (in milliseconds)
	 * @param logger
	 *            A PrintStream object used for logging
	 * @param tasks
	 *            An array of tasks
	 */
	public TaskGroup(String name, int tries, long interval, PrintStream logger, final Task[] tasks) {
		super("(TaskGroup)" + name, tries, interval, logger);
		this.logger = logger;
		this.tasks = tasks;
	}

	/**
	 * @param name
	 *            The name of the task to create (printed in logs)
	 * @param tries
	 *            The number of times the system will try to execute the task if
	 *            the exit code is not 0
	 * @param interval
	 *            The time to wait between each time the system tries to execute
	 *            the task (in milliseconds)
	 * @param tasks
	 *            An array of tasks
	 */
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
