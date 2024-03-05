package principal;

import javax.swing.JFrame;

import tablas.Tablas;

public class Main {

	public Main() {
		new Tablas();

	}

	public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new Tablas());
        f.setSize(400,400);
        f.setLocation(200,200);
        f.setVisible(true);

	}

}
