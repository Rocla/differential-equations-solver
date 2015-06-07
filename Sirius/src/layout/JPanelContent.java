
package layout;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import layout.tank.JPanelTank;
import tank.Tank;
import differentialEquationSolving.SimulationSingleton;

public class JPanelContent extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelContent()
		{

		listPanelTank = new LinkedList<JPanelTank>();

		h = w = 0;
		scrolX = 0;
		scrolY = 0;
		startPosX = 0;
		startPosY = 0;
		t=null;
		geometry();
		control();
		appearance();

		}

	//
	//		class MovingAdapter extends MouseAdapter {
	//
	//			private int x;
	//			private int y;
	//
	//			@Override
	//			public void mousePressed(MouseEvent e) {
	//				x = e.getX();
	//				y = e.getY();
	//			}
	//
	//			@Override
	//			public void mouseDragged(MouseEvent e) {
	//
	////				Tank maintank = SimulationSingleton.getInstance().getMainTank();
	//
	//				int dx = e.getX() - x;
	//				int dy = e.getY() - y;
	//
	//				if (panelTank.contains(x, y)) {
	//					dx = panelTank.getX() + dx;
	//					dy = panelTank.getY() + dy;
	//					panelTank.setLocation(dx, dy);
	//					repaint();
	//				}
	//				x += dx;
	//				y += dy;
	//			}
	//		}

	//	class ScaleHandler implements MouseWheelListener {
	//		public void mouseWheelMoved(MouseWheelEvent e) {
	//
	//			int x = e.getX();
	//			int y = e.getY();
	//
	//			if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
	//
	//				if (tank1.contains(x, y)) {
	//
	//					float amount = e.getWheelRotation() * 5f;
	//					int width = (int) (tank1.getWidth() + amount);
	//					int height = (int) (tank1.getHeight() + amount);
	//					tank1.setSize(width, height);
	//				}
	//			}
	//		}
	//	}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	//	@Override
	//	protected void paintComponent(Graphics g)
	//		{
	//		super.paintComponent(g);
	//		constructPannelsTank();
	//		}

	// public Tank getMainContainer()
	// {
	// Tank mainContainer = panelParametresContainer.getConteneur();
	// mainContainer.addTankParent(panelParametresSource.getConteneur());
	// return mainContainer;
	// }

	//	@Override
	//	public void paint(Graphics g) {
	//		super.paint(g);
	//
	//		// Layout : Specification
	//		setLayout(null);
	//		add(tank1);
	//		int tx = tank1.getX() + tank1.getWidth() - tank1.getHeight() / 6;
	//		int ty = tank1.getY() + tank1.getHeight() / 2;
	//		toggle.setLocation(tx, ty);
	//		collapsibelPane.setLocation(tx + toggle.getWidth() + 10, ty
	//				- collapsibelPane.getHeight() / 2);
	//	}

	public List<JPanelTank> constructPannelsTank()
		{
		Tank mainTank = SimulationSingleton.getInstance().getMainTank();

		List<Tank> pileTank = new LinkedList<Tank>();
		pileTank.add(mainTank);

		//tout les tanks a afficher et leur panels
		HashMap<Tank, JPanelTank> mapAffTank = new HashMap<Tank, JPanelTank>();
		HashMap<Tank, Integer> mapIParentTank = new HashMap<Tank, Integer>();

		//Construction de l'arbre
		int xPosChild = 0;
		int yPosChild = 0;
		int iParent = 0;

		while(!pileTank.isEmpty())
			{
			int i = pileTank.size() - 1;

			Tank tank = pileTank.get(i);
			pileTank.remove(i);

			if (tank != null)
				{

				pileTank.addAll(tank.getlistTankParent());
				panelTank = new JPanelTank(tank, false);

				int xPos = 0;
				int yPos = 0;

				if (xPosChild == 0 && yPosChild == 0)
					{
					//main
					System.out.println("---maintank---");
					xPos = xPosChild = this.getWidth() / 2 - (panelTank.getWidth() / 2);
					yPos = yPosChild = this.getHeight() - panelTank.getHeight();
					}
				else
					{
					System.out.println("---childtank---");
					//he is a child
					if (mapAffTank.keySet().contains(tank.getTankChild()))
						{
						JPanelTank panelTankChild = mapAffTank.get(tank.getTankChild());
						int xChild = panelTankChild.getX();
						int yChild = panelTankChild.getY();

						iParent = 0;
						if (mapIParentTank.keySet().contains(tank.getTankChild()))
							{
							iParent = mapIParentTank.get(tank.getTankChild());
							}

						int widthtotaltank = tank.getlistTankParent().size() * panelTank.getWidth();
						int decalage = widthtotaltank;

						if (tank.getlistTankParent().size() == 0)
							{
							decalage += panelTank.getWidth() / 2;
							}
						else
							{
							decalage -= panelTank.getWidth() / 2;
							}

						System.out.println("nb parent : " + tank.getlistTankParent().size() + " , total width : " + widthtotaltank);
						System.out.println("pos X parent : " + xChild);

						xPos = xChild + iParent * panelTank.getWidth() - decalage + 20;
						yPos = yChild - panelTank.getHeight() - 20;

						if (xPos > xChild)
							{
							panelTank = new JPanelTank(tank, true);
							}

						iParent++;
						}
					else
						{
						System.out.println("error");
						}
					}

				panelTank.setLocation(xPos, yPos);

				mapAffTank.put(tank, panelTank);
				mapIParentTank.put(tank.getTankChild(), iParent);

				System.out.println("Tank => pos : x: " + xPos + " , y: " + yPos);
				}
			}

		mainTank = null;

		//centrage de la simulation
		int minPosX = xPosChild;
		int maxPosX = xPosChild;
		int minPosY = yPosChild;
		int maxPosY = yPosChild;
		for(JPanelTank panelTank:mapAffTank.values())
			{
			if (panelTank.getX() + panelTank.getWidth() > maxPosX)
				{
				maxPosX = panelTank.getX() + panelTank.getWidth();
				}
			else if (panelTank.getX() < minPosX)
				{
				minPosX = panelTank.getX();
				}

			if (panelTank.getY() + panelTank.getHeight() > maxPosY)
				{
				maxPosY = panelTank.getY() + panelTank.getHeight();
				}
			else if (panelTank.getY() < minPosY)
				{
				minPosY = panelTank.getY();
				}
			}
		int centerSimulationX = minPosX + (maxPosX - minPosX) / 2;
		int centerWindowX = this.getWidth() / 2;
		int decalageX = (centerWindowX - centerSimulationX);

		int centerSimulationY = minPosY + (maxPosY - minPosY) / 2;
		int centerWindowY = this.getHeight() / 2;
		int decalageY = (centerWindowY - centerSimulationY);

		System.out.println("centre Fenetre "+ centerWindowX);
		System.out.println("min : "+ minPosX + "  max : " + maxPosX+ "  centre simulation :"+centerSimulationX+ " centre ecran:"+centerWindowX);
		System.out.println("-----declage :  " + decalageX);

		//affichage sur la scene
		for(JPanelTank panelTank:mapAffTank.values())
			{
			panelTank.setLocation(panelTank.getX() + decalageX + scrolX, panelTank.getY() + decalageY + scrolY);
			add(panelTank);
			listPanelTank.add(panelTank);
			}

		return listPanelTank;
		}

	public void affTime(double time)
		{
		for(JPanelTank paneltank:listPanelTank)
			{
			paneltank.affTime(time);
			}
		repaint();
		this.t = time;
		}

	public void refresh()
		{
		removeAll();

		listPanelTank = constructPannelsTank();

		if(t!=null)
			{
			affTime(t);
			}

		repaint();
		updateUI();
		}

	public void resetTime()
		{
		this.t = null;
		}

	public void centerSimulation()
		{
		scrolX = scrolY = 0;
		refresh();
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		// Layout : Specification
		setLayout(null);

		// JComponent : Instanciation
		listPanelTank = constructPannelsTank();
		}

	private void control()
		{
		//addParameters();
		//		addMouseMotionListener(ma);
		//		addMouseListener(ma);
		//addMouseWheelListener(new ScaleHandler());

		addMouseListener(new MouseAdapter()
			{

				@Override
				public void mouseReleased(MouseEvent e)
					{
					startPosX = 0;
					startPosY = 0;
					getParent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}

				@Override
				public void mousePressed(MouseEvent e)
					{
					startPosX = e.getX();
					startPosY = e.getY();
					refresh();
					}
			});

		addMouseMotionListener(new MouseMotionListener()
			{

				@Override
				public void mouseDragged(MouseEvent e)
					{
					scrolX += e.getX() - startPosX;
					scrolY += e.getY() - startPosY;
					startPosX = e.getX();
					startPosY = e.getY();

					getParent().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
					refresh();
					}

				@Override
				public void mouseMoved(MouseEvent e)
					{
					//rien
					}

			});

		}

	private void appearance()
		{
		// rien
		}

	//	private void addBoutonsAddParentAndChild() {
	//
	//		// JXCollapsiblePane
	//		box.setBorder(new LineBorder(Color.black));
	//
	//		Box sub_box1 = new Box(BoxLayout.X_AXIS);
	//		Box sub_box2 = new Box(BoxLayout.X_AXIS);
	//		Box sub_box3 = new Box(BoxLayout.X_AXIS);
	//		Box sub_box4 = new Box(BoxLayout.X_AXIS);
	//		Box sub_box5 = new Box(BoxLayout.X_AXIS);
	//
	//		sub_box1.add(new JXLabel("Infini: "));
	//		sub_box1.add(new JCheckBox());
	//
	//		sub_box2.add(new JXLabel("Contenance: "));
	//		sub_box2.add(new JXTextField("100"));
	//
	//		sub_box3.add(new JXLabel("Eau: "));
	//		sub_box3.add(new JXTextField("55"));
	//
	//		sub_box4.add(new JXLabel("Element: "));
	//		sub_box4.add(new JXTextField("45"));
	//
	//		sub_box5.add(new JXLabel("Debit: "));
	//		sub_box5.add(new JXTextField("50"));
	//
	//		box.add(sub_box1);
	//		box.add(sub_box2);
	//		box.add(new JXLabel("Configuration"));
	//		box.add(sub_box3);
	//		box.add(sub_box4);
	//		box.add(new JXButton("Ajouter"));
	//		box.add(sub_box5);
	//
	//		collapsibelPane.add(box);
	//
	//		toggle.setText("More");
	//
	//		this.add(toggle);
	//		this.add(collapsibelPane);
	//
	//	}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private JPanelTank panelTank;
	private List<JPanelTank> listPanelTank;

	private int w;
	private int h;

	private Double t;

	private int scrolX;
	private int scrolY;
	private int startPosX;
	private int startPosY;

	//private JPanelTank tank1;
	//
	//	JXCollapsiblePane collapsibelPane = new JXCollapsiblePane(
	//			Direction.TRAILING);
	//	JXButton toggle = new JXButton(collapsibelPane.getActionMap().get(
	//			JXCollapsiblePane.TOGGLE_ACTION));
	//	Box box = new Box(BoxLayout.Y_AXIS);
	//		MovingAdapter ma = new MovingAdapter();

	}
