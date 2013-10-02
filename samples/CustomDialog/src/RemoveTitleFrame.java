import javax.swing.*;

public class RemoveTitleFrame{
	public static void main(String[] args) {
		JFrame frame = new JFrame("Removing the Title Bar of a Frame");
		JLabel label = new JLabel("This is the Frame without Title Bar.");
		frame.add(label);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200,200);
		frame.setVisible(true);
	}
}