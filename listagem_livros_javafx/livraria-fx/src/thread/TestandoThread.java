package thread;

public class TestandoThread {

	public static void main(String[] args) {
		
		new Thread(() -> System.out.println("Rodando em paralelo!")).start();
		
		System.out.println("Terminei de rodar o main");
	}

}
