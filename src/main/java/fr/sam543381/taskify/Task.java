package fr.sam543381.taskify;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public abstract class Task {

	protected String name;
	protected int tries;
	protected long interval;
	protected PrintStream logger;

	public Task(String name, int tries, long interval, PrintStream logger) {
		this.name = name;
		this.tries = tries;
		this.interval = interval;
		this.logger = logger;
	}

	public Task(String name, int tries, long interval) {
		this(name, tries, interval, System.out);
	}

	public void setLogger(PrintStream stream) {
		this.logger = stream;
	}

	protected List<Object> results = new ArrayList<Object>();

	public abstract int make() throws Exception;

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

	public Thread executeAsync() {
		Thread t = new Thread(() -> {
			execute();
		});
		t.start();
		return t;
	}

	public List<Object> getResults() {
		return results;
	}
}
