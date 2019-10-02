package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;

public class BodiesTable extends JPanel {

	BodiesTable(Controller ctrl) {
		
		setLayout(new BorderLayout()); 
		setBorder(BorderFactory.createTitledBorder (
				BorderFactory.createLineBorder(Color.black, 2), "Bodies",
				TitledBorder.LEFT, TitledBorder.TOP));
		setPreferredSize(new Dimension(300,180));
		BodiesTableModel btm = new BodiesTableModel(ctrl);
		JTable table = new JTable(btm);
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false); 
		JScrollPane scroll = new JScrollPane(table);
		add(scroll, BorderLayout.CENTER);
		
	}
}
