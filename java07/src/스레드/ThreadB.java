package 스레드;

public class ThreadB extends Thread{
	@Override
	public void run() {
		for (int i = 0; i < 1000; i++) {
			System.out.println("@" + i);
		}
	}
}
