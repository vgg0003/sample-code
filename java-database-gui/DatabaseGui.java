import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class DatabaseGui {

	DatabaseGui() {
		
		String connectionStr = UserInteractable.infoCollection();
		String connTables = TouchDatabase.runCommand("SHOW TABLES", connectionStr);

		Frame f = new Frame();

		f.setSize(850,550);
		//f.setBounds(1000,2000);
		f.addWindowListener( new WindowAdapter() { public void windowClosing(WindowEvent w){ System.exit(0);}});
		
		String[] tables = connTables.split("\n");
			
		List l = new List(6, false);
		int i = 1;
		while (i < tables.length){
			l.add(tables[i].trim());
			i++;
		}
		
		//l.add(tables[1].trim());
		//l.add("customer");
		//l.add("employee");
		//l.add("order1");
		//l.add("order_detail");
		//l.add("shipper");
		//l.add("subject");
		//l.add("supplier");


		l.setBounds(30, 100, 100, 200);
		
		TextArea t = new TextArea(100,100);
		t.setBounds(150, 100, 600, 160);
			
		Button submit = new Button("Submit");
		submit.setBounds(150, 265, 600, 30);

		TextArea results = new TextArea();
		results.setBounds(30, 310, 750, 200);
		results.setEditable(false);
		results.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

		f.add(l);
		f.add(t);
		f.add(submit);	
		f.add(results);

		
		f.setTitle("Database");
		f.setLayout(null);
		f.setVisible(true);
		
		// Listener for the list
		l.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) 
			{ String result = TouchDatabase.runCommand("SELECT * FROM " + l.getSelectedItem(), connectionStr);
			  	//System.out.println(result);
				results.setText(result);
				
			}
		});
		
		//Listener for the submit button
		submit.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e)
			{ String query = t.getText();
				String result = TouchDatabase.runCommand(query, connectionStr);
				//System.out.println(query);
				results.setText(result);
			}
		});


	}
	public static void main(String args[]){
		DatabaseGui g = new DatabaseGui();
	}
}
