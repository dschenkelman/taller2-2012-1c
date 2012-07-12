/*
 * Created by JFormDesigner on Sat Jun 23 18:45:29 ART 2012
 */

package views;

import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;

/**
 * @author santiago storti
 */
public abstract class RelationshipViewAbstract extends JFrame {
	public RelationshipViewAbstract(RelationshipEntityViewImpl relEntView, AttributeView attView) {
		initComponents(relEntView, attView);
	}

	protected abstract  void setUpCompontenents (RelationshipEntityViewImpl relEntView, AttributeView attView) ;
	
	
	private void initComponents(RelationshipEntityViewImpl relEntView, AttributeView attView) {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - santiago storti
		DefaultComponentFactory compFactory = DefaultComponentFactory.getInstance();
		labelName = new JLabel();
		textFieldName = new JTextField();
		separator1 = compFactory.createSeparator("");
		tabbedPane3 = new JTabbedPane();
		panelAttributes = new JPanel();
		panelEntities = new JPanel();
		Title = new JLabel();
		checkBox1 = new JCheckBox();
		button1 = new JButton();

		setUpCompontenents (relEntView, attView); 
		
		//======== this ========
		setTitle("Relationship Editor");
		Container contentPane = getContentPane();

		//---- labelName ----
		labelName.setText("Name:");

		//======== tabbedPane3 ========
		{

			//======== panelAttributes ========
			{

				// JFormDesigner evaluation mark
				panelAttributes.setBorder(new javax.swing.border.CompoundBorder(
					new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
						"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
						javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
						java.awt.Color.red), panelAttributes.getBorder())); panelAttributes.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});


				GroupLayout panelAttributesLayout = new GroupLayout(panelAttributes);
				panelAttributes.setLayout(panelAttributesLayout);
				panelAttributesLayout.setHorizontalGroup(
					panelAttributesLayout.createParallelGroup()
						.addGap(0, 538, Short.MAX_VALUE)
				);
				panelAttributesLayout.setVerticalGroup(
					panelAttributesLayout.createParallelGroup()
						.addGap(0, 315, Short.MAX_VALUE)
				);
			}
			tabbedPane3.addTab("Attributes", panelAttributes);


			//======== panelEntities ========
			{

				GroupLayout panelEntitiesLayout = new GroupLayout(panelEntities);
				panelEntities.setLayout(panelEntitiesLayout);
				panelEntitiesLayout.setHorizontalGroup(
					panelEntitiesLayout.createParallelGroup()
						.addGap(0, 538, Short.MAX_VALUE)
				);
				panelEntitiesLayout.setVerticalGroup(
					panelEntitiesLayout.createParallelGroup()
						.addGap(0, 315, Short.MAX_VALUE)
				);
			}
			tabbedPane3.addTab("Entities", panelEntities);

		}

		//---- Title ----
		Title.setText("Edit your Relationship");

		//---- checkBox1 ----
		checkBox1.setText("Composition");

		//---- button1 ----
		button1.setText("Add");

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGroup(contentPaneLayout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(separator1, GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE))
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(button1, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
								.addGroup(contentPaneLayout.createParallelGroup()
									.addGroup(contentPaneLayout.createSequentialGroup()
										.addGap(172, 172, 172)
										.addComponent(labelName, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(contentPaneLayout.createParallelGroup()
											.addComponent(textFieldName, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
											.addComponent(Title)))
									.addGroup(contentPaneLayout.createSequentialGroup()
										.addGap(239, 239, 239)
										.addComponent(checkBox1))
									.addGroup(contentPaneLayout.createSequentialGroup()
										.addGap(30, 30, 30)
										.addComponent(tabbedPane3, GroupLayout.PREFERRED_SIZE, 543, GroupLayout.PREFERRED_SIZE))))
							.addGap(0, 16, Short.MAX_VALUE)))
					.addContainerGap())
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addComponent(Title, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addGap(18, 18, 18)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(labelName)
						.addComponent(textFieldName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26, 26, 26)
					.addComponent(checkBox1)
					.addGap(18, 18, 18)
					.addComponent(separator1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18, 18, 18)
					.addComponent(tabbedPane3, GroupLayout.PREFERRED_SIZE, 343, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
					.addComponent(button1)
					.addContainerGap())
		);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - santiago storti
	protected JLabel labelName;
	protected JTextField textFieldName;
	protected JComponent separator1;
	protected JTabbedPane tabbedPane3;
	protected JPanel panelAttributes;
	protected JPanel panelEntities;
	protected JLabel Title;
	protected JCheckBox checkBox1;
	protected JButton button1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
