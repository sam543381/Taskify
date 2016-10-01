package fr.sam543381.taskify;

public class Loop {

	protected Task task;
	protected long interval;
	protected int maxCount;
	protected String name;

	protected int loops = 0;

	public Loop(Task task, long interval, int count, String name) {
		this.task = task;
		this.interval = interval;
		this.maxCount = count;
		this.name = name;
	}

	public Loop(Task task, long interval, int count) {
		this(task, interval, count, null);
	}

	public void loop() {
		while (loops < maxCount) {
			loops = maxCount == -1 && task.execute() != -2 ? loops : loops + 1;

			if (maxCount == -1 && name != null)
				System.out.println("Loop " + name + " made on loop more : " + (maxCount - loops) + " loops left");
		}
	}

	public Thread loopAsync() {

		Thread t = new Thread(() -> {
			loop();
		});

		return t;
	}
}
