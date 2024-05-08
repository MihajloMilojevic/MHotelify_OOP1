package views.settings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class EditSettingsDialog extends JDialog {

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the ok
	 */
	public boolean isOk() {
		return ok;
	}

	private static final long serialVersionUID = 1L;
	private String value;
	private boolean ok = false;
	private final JPanel contentPanel = new JPanel();
	private JTextField valueTf;
	private JLabel titleLb;
	private JLabel nameLbl;

	/**
	 * Create the dialog.
	 */
	public EditSettingsDialog(String name, String value) {
		this.value = value;
		setTitle("Edit Setting |" + name);
		setResizable(false);
		setModal(true);
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon("./assets/icons/setting.png").getImage());
		getContentPane().setForeground(new Color(255, 255, 255));
		setForeground(new Color(255, 255, 255));
		getContentPane().setBackground(new Color(73, 73, 73));
		setBackground(new Color(73, 73, 73));
		setBounds(100, 100, 450, 180);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setForeground(new Color(255, 255, 255));
		contentPanel.setBackground(new Color(73, 73, 73));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			titleLb = new JLabel("Edit Setting");
			titleLb.setHorizontalAlignment(SwingConstants.CENTER);
			titleLb.setForeground(Color.WHITE);
			titleLb.setFont(new Font("Times New Roman", Font.PLAIN, 24));
			GridBagConstraints gbc_titleLb = new GridBagConstraints();
			gbc_titleLb.fill = GridBagConstraints.BOTH;
			gbc_titleLb.gridwidth = 2;
			gbc_titleLb.insets = new Insets(0, 0, 5, 0);
			gbc_titleLb.gridx = 0;
			gbc_titleLb.gridy = 0;
			contentPanel.add(titleLb, gbc_titleLb);
		}
		{
			Component verticalStrut = Box.createVerticalStrut(15);
			GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
			gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
			gbc_verticalStrut.gridx = 0;
			gbc_verticalStrut.gridy = 1;
			contentPanel.add(verticalStrut, gbc_verticalStrut);
		}
		{
			nameLbl = new JLabel("");
			nameLbl.setHorizontalAlignment(SwingConstants.LEFT);
			nameLbl.setForeground(Color.WHITE);
			nameLbl.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			GridBagConstraints gbc_nameLbl = new GridBagConstraints();
			gbc_nameLbl.gridwidth = 2;
			gbc_nameLbl.insets = new Insets(0, 0, 5, 0);
			gbc_nameLbl.gridx = 0;
			gbc_nameLbl.gridy = 2;
			contentPanel.add(nameLbl, gbc_nameLbl);
		}
		{
			Component verticalStrut = Box.createVerticalStrut(5);
			GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
			gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
			gbc_verticalStrut.gridx = 0;
			gbc_verticalStrut.gridy = 3;
			contentPanel.add(verticalStrut, gbc_verticalStrut);
		}
		{
			JLabel lblCategoryName = new JLabel("Value:");
			lblCategoryName.setHorizontalAlignment(SwingConstants.LEFT);
			lblCategoryName.setForeground(Color.WHITE);
			lblCategoryName.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			GridBagConstraints gbc_lblCategoryName = new GridBagConstraints();
			gbc_lblCategoryName.insets = new Insets(0, 0, 0, 5);
			gbc_lblCategoryName.anchor = GridBagConstraints.EAST;
			gbc_lblCategoryName.gridx = 0;
			gbc_lblCategoryName.gridy = 4;
			contentPanel.add(lblCategoryName, gbc_lblCategoryName);
		}
		{
			valueTf = new JTextField();
			valueTf.setColumns(10);
			GridBagConstraints gbc_valueTf = new GridBagConstraints();
			gbc_valueTf.fill = GridBagConstraints.HORIZONTAL;
			gbc_valueTf.gridx = 1;
			gbc_valueTf.gridy = 4;
			contentPanel.add(valueTf, gbc_valueTf);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setForeground(new Color(255, 255, 255));
			buttonPane.setBackground(new Color(73, 73, 73));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
				okButton.setActionCommand("OK");
				okButton.addActionListener(e -> {
					if (valueTf.getText().trim().isBlank()) {
						JOptionPane.showMessageDialog(this, "Category name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					ok = true;
					this.value = valueTf.getText().trim();
					dispose();
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(e -> {
					dispose();
				});
				buttonPane.add(cancelButton);
			}
		}

		nameLbl.setText(name);
		valueTf.setText(value);
	}
	
}
