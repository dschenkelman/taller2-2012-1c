package application;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;

import controllers.IDiagramController;

public class ShellWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Bootstrapper bootstrapper = new Bootstrapper();
					bootstrapper.run();
					IDiagramController diagramController = 
						(IDiagramController) bootstrapper.getContainer().getComponent(IDiagramController.class);
					ShellWindow window = new ShellWindow();
					window.frame.setVisible(true);
					window.frame.add((Component) diagramController.getView());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ShellWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
