package fr.sam543381.taskify;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @see fr.sam543381.taskify.Task
 * @author Sam54 (<a href="mailto:sam543381@live.fr">contact</a>)
 */
public abstract class Task {

	protected String name;
	protected int tries;
	protected long interval;
	protected PrintStream logger;

	/**
	 * 
	 * @param name The name of the task to create (printed in logs)
	 * @param tries The number of times the system will try to execute the task if the exit code is not 0
	 * @param interval The time to wait between each time the system tries to execute the task (in milliseconds)
	 * @param logger A PrintStream object used for logging (optionnal)
	 */
	public Task(String name, int tries, long interval, PrintStream logger) {
		this.name = name;
		this.tries = tries;
		this.interval = interval;
		this.logger = logger;
	}

	/**
	 * 
	 * @param name The name of the task to create (printed in logs)
	 * @param tries The number of times the system will try to execute the task if the exit code is not 0
	 * @param interval The time to wait between each time the system tries to execute the task (in milliseconds)
	 */
	public Task(String name, int tries, long interval) {
		this(name, tries, interval, System.out);
	}

	protected List<Object> results = new ArrayList<Object>();

	/**
	 * Executed by the system as the task
	 * @return An exit code (-1 not possible)
	 * @throws Exception
	 */
	public abstract int make() throws Exception;

	/**
	 * Start the task execution
	 * @return The task's last thrown exit code or -1. Can also return -2 to indicate to a loop to stop looping after this task
	 * @see @see {@link fr.sam543381.taskify.Loop#loop()}
	 */
	public int execute() {

		System.out.println(name.toUpperCase());

		for (int i = 0; i < tries; i++) {
			System.out.println("-- " + (i == 0 ? "Trying" : "Retrying") + " to run task " + name);

			int exit = -1;

			try {
				exit = make();
				exit = exit == -1 ? 1 : exit;
			} catch (Exception e) {
				System.out.println("-- Task " + name + " thrown an exception during execution");
			}

			if (exit != -1)
				System.out.println("-- Task " + name + " terminated with exit code " + exit);

			if (exit != 0 && i + 1 >= tries) {
				System.out.println("-- Waiting " + (double) (interval / 1000D) + " seconds before retrying");
				try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
				}
			} else {
				System.out.println("-- Task " + name + " successfully terminated");
			}
		}

		System.out.println("-- Task " + name + " failed\n");

		return 1;
	}

	/**
	 * Asynchronous version of {@link #execute()}
	 * @return The thread the task is executed by
	 */
	public Thread executeAsync() {
		Thread t = new Thread(() -> {
			execute();
		});
		t.start();
		return t;
	}

	/**
	 * Used instead of return statement
	 * @return A list of object representing the objects the tasks produces
	 */
	public List<Object> getResults() {
		return results;
	}
}
