
package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

public class LatexExample extends JFrame implements ActionListener
	{

	private JTextArea latexSource;
	private JButton btnRender;
	private JPanel drawingArea;

	public LatexExample()
		{
		this.setTitle("JLatexMath Example");
		this.setSize(500, 500);
		Container content = this.getContentPane();
		content.setLayout(new GridLayout(2, 1));
		this.latexSource = new JTextArea();

		JPanel editorArea = new JPanel();
		editorArea.setLayout(new BorderLayout());
		editorArea.add(new JScrollPane(this.latexSource), BorderLayout.CENTER);
		editorArea.add(btnRender = new JButton("Render"), BorderLayout.SOUTH);

		content.add(editorArea);
		content.add(this.drawingArea = new JPanel());
		this.btnRender.addActionListener(this);

		this.latexSource.setText("x=\\frac{-b \\pm \\sqrt {b^2-4ac}}{2a}");
		}

	public void render()
		{
		try
			{
			String latex = this.latexSource.getText();
			TeXFormula formula = new TeXFormula(latex);
			TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);

			BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

			Graphics2D g2 = image.createGraphics();

			JLabel jl = new JLabel();
			jl.setForeground(new Color(0, 0, 0));

			icon.paintIcon(jl, g2, 0, 0);

			Graphics g = this.drawingArea.getGraphics();
			g.drawImage(image, 0, 0, null);

			}
		catch (Exception ex)
			{

			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);

			}

		}

	public static void main(String[] args)
		{
		LatexExample frame = new LatexExample();
		frame.setVisible(true);
		}

	@Override
	public void actionPerformed(ActionEvent e)
		{
		if (e.getSource() == this.btnRender)
			{
			render();
			}

		}
	}
