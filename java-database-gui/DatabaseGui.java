import java.awt.*;
import java.awt.event.*;

public class DatabaseGui {

	DatabaseGui() {

		Frame f = new Frame();

		f.setSize(500,550);
		//f.setBounds(1000,2000);
		f.addWindowListener( new WindowAdapter() { public void windowClosing(WindowEvent w){ System.exit(0);}});
		
		// Create the buttons for the tables
		Button t1 = new Button("Table1");
		Button t2 = new Button("Table2");
		Button t3 = new Button("Table3");
		Button t4 = new Button("Table4");
		Button t5 = new Button("Table5");
		Button t6 = new Button("Table6");
		
		List l = new List(6, false);
		l.add("book");
		l.add("customer");
		l.add("employee");
		l.add("order1");
		l.add("order_detail");
		l.add("shipper");
		l.add("subject");
		l.add("supplier");


		l.setBounds(30, 100, 100, 200);
		
		TextArea t = new TextArea(5, 40);
		t.setBounds(150, 100, 200, 160);
			
		Button submit = new Button("Submit");
		submit.setBounds(150, 265, 200, 30);

		TextArea results = new TextArea();
		results.setBounds(30, 310, 300, 200);
		results.setEditable(false);

		f.add(l);
		f.add(t);
		f.add(submit);	
		f.add(results);

		
		f.setTitle("Database");
		f.setLayout(null);
		f.setVisible(true);
		
		l.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) 
			{ String result = TouchDatabase.runCommand("SELECT * FROM " + l.getSelectedItem());
			  results.setText(result);
			}
		});

	}
	public static void main(String args[]){
		DatabaseGui g = new DatabaseGui();
	}
}
