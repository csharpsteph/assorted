package component;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class ColorPanel extends JPanel {
	
	private static final long serialVersionUID = 558564360823234767L;
	
	private ControlPanel ctrlPanel;
	private JPanel mainPanel;
	
	private ColorPanel() {
		setLayout(new BorderLayout());
		mainPanel = new JPanel();
		add(mainPanel, BorderLayout.CENTER);
		ctrlPanel = new ControlPanel(this);
		add(ctrlPanel, BorderLayout.SOUTH);
		recolor(ctrlPanel.getColor());
	}
	
	public void recolor(Color color) {
		mainPanel.setBackground(color);
	}
	
	private class ControlPanel extends JPanel {
		
		private static final long serialVersionUID = -3146928004155433457L;
		private JTextField jtfRed, jtfGreen, jtfBlue;
		private int r, g, b;
		private ColorPanel parent;
		
		private FocusListener jtfFocusListener = new FocusListener() {
			
			public void focusGained(FocusEvent e) {
				JTextField source = (JTextField)e.getSource();
				source.selectAll();
			}

			public void focusLost(FocusEvent e) {
				return;
			}
		};
		
		private ActionListener jtfListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jtfRed.getText().length() == 0) {
					jtfRed.setText("0");
				}
				if (jtfGreen.getText().length() == 0) {
					jtfGreen.setText("0");
				}
				if (jtfBlue.getText().length() == 0) {
					jtfBlue.setText("0");
				}
				
				int[] newColor = new int[3];
				try {
					newColor[0] = Integer.parseInt(jtfRed.getText());
					newColor[1] = Integer.parseInt(jtfGreen.getText());
					newColor[2] = Integer.parseInt(jtfBlue.getText());
					for (int v: newColor) {
						if (v < 0 || v > 255) {
							throw new IllegalArgumentException(
									String.format("Color component not between 0 and 255: %d", v));
						}
					}
				} catch (Exception ex) {
					jtfRed.setText(String.valueOf(r));
					jtfGreen.setText(String.valueOf(g));
					jtfBlue.setText(String.valueOf(b));
					return;
				}
				
				r = newColor[0];
				g = newColor[1];
				b = newColor[2];
				
				parent.recolor(getColor());
				transferFocusUpCycle();
			}
		};
		
		ControlPanel(ColorPanel parent) {
			this.parent = parent;
			setLayout(new GridLayout(0,3,5,5));
			JPanel[] panels = new JPanel[3];
			for (int i = 0; i < 3; i++) {
				panels[i] = new JPanel(new FlowLayout(FlowLayout.LEFT));
			}
			
			panels[0].add(new JLabel("Red:"));
			panels[1].add(new JLabel("Green:"));
			panels[2].add(new JLabel("Blue:"));
			
			jtfRed = new JTextField("0", 3);
			jtfGreen = new JTextField("0", 3);
			jtfBlue = new JTextField("0", 3);
			
			
			jtfRed.addActionListener(jtfListener);
			jtfRed.addFocusListener(jtfFocusListener);
			jtfGreen.addActionListener(jtfListener);
			jtfGreen.addFocusListener(jtfFocusListener);
			jtfBlue.addActionListener(jtfListener);
			jtfBlue.addFocusListener(jtfFocusListener);
			
			panels[0].add(jtfRed);
			panels[1].add(jtfGreen);
			panels[2].add(jtfBlue);
			
			for (int i = 0; i < 3; i++) {
				add(panels[i]);
			}
			
			this.setBorder(BorderFactory.createMatteBorder(2,0,0,0,Color.BLACK));
		}
		
		public Color getColor() {
			return new Color(r, g, b);
		}
	}
	
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(800, 800);
	}
	
	public static void createAndShowGUI() {
		JFrame frame = new JFrame("Color Panel");
		frame.add(new ColorPanel());
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
}
