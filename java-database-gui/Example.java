import java.awt.*;

public class Example extends Frame {


	Example() {
		
		Frame f = new Frame();
		Button b = new Button("Click Me!!");

		b.setBounds(30,100,80,30);

		f.add(b);
		TextArea t = new TextArea("Hello", 5, 40);
		t.setBounds(30, 140, 80,80);
		f.add(t);
		f.setSize(300,300);
		f.setTitle("This is basic example");
		f.setLayout(null);
		f.setVisible(true);

	}
	public static void main(String args[]){
		Example f = new Example();
	}
}
