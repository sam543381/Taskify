package fr.sam543381.taskify;

/**
 * @see fr.sam543381.taskify.Task
 * @author Sam54 (<a href="mailto:sam543381@live.fr">contact</a>)
 */
public class Loop {

	protected Task task;
	protected long interval;
	protected int maxCount;
	protected String name;

	protected int loops = 0;

	/**
	 * @param task The task to loop on
	 * @param interval The time (in milliseconds) beetwen each execution
	 * @param count The number of time the task must be executed (overriden if -2 is returned by the task)
	 * @param name The loop name used by the logger (optionnal)
	 */
	public Loop(Task task, long interval, int count, String name) {
		this.task = task;
		this.interval = interval;
		this.maxCount = count;
		this.name = name;
	}

	/**
	 * @param task The task to loop on
	 * @param interval The time (in milliseconds) beetwen each execution
	 * @param count The number of time the task must be executed (overriden if -2 is returned by the task)
	 */
	public Loop(Task task, long interval, int count) {
		this(task, interval, count, null);
	}

	/**
	 * Star the loop
	 */
	public void loop() {
		while (loops < maxCount) {
			
			if (task.execute() == -2)
				return;
			
			loops = maxCount == -1 ? loops : loops + 1;

			if (maxCount == -1 && name != null)
				System.out.println("Loop " + name + " made on loop more : " + (maxCount - loops) + " loops left");
		}
	}

	/**
	 * Asynchronous version of {@link #loop()}
	 * @return The thread the loop is executed by
	 */
	public Thread loopAsync() {

		Thread t = new Thread(() -> {
			loop();
		});

		return t;
	}
}
