import javax.swing.*;
import java.awt.*;


public class Window extends JFrame{

	public static int width , height;

	
	public Window(int w, int h){
		super("Smart Snakes");
		width =w;
		height = h;
		setup();
	}
	
	public void setup(){
	
		this.setLayout(new FlowLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setBackground(Color.BLACK);
		this.setVisible(true);
		
	}
}
