package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import simulator.view.ControlPanel;

import simulator.control.Controller;


public class MainWindow extends JFrame{
	Controller _ctrl;
	
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI(); 
		setVisible(true);
	}

	private void initGUI() {
		//Main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		initMainPanel(mainPanel);
		
		//control panel
		ControlPanel control_panel = new ControlPanel(_ctrl);
		mainPanel.add(control_panel, BorderLayout.PAGE_START);
		
		//center panel
		JPanel center_panel = new JPanel();
		center_panel.setLayout(new BoxLayout(center_panel, BoxLayout.Y_AXIS));
		center_panel.setBackground(Color.WHITE);
		
		//bodies table
		BodiesTable bodies_table = new BodiesTable(_ctrl);
		center_panel.add(bodies_table);
		
		//viewer
		Viewer viewer = new Viewer(_ctrl);
		center_panel.add(viewer);
		
		mainPanel.add(center_panel, BorderLayout.CENTER);
	
		//status bar
		StatusBar statusBar = new StatusBar(_ctrl);
		mainPanel.add(statusBar, BorderLayout.PAGE_END);
		
	}

	private void initMainPanel(JPanel mainPanel) {
		setContentPane(mainPanel);
		setTitle("PhysicsSimulator");
		setBounds(5,6,1000,500);
		setLocationRelativeTo(null);//aparece en el medio de la pantalla
		setDefaultCloseOperation(EXIT_ON_CLOSE);//acaba el programa al cerrar la ventana
	}
}