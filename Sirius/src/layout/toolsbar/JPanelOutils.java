/*********************************************************************************
# Copyright © Haute-Ecole ARC - All Rights Reserved
# Copyright © Banana Rocket - All Rights Reserved
#
# This file is part of <P2 Java Project: GlouGlou - Problèmes de mélanges>.
#
# Unauthorized copying of this file, via any medium is strictly prohibited
# Proprietary and confidential
# Written by Claret-Yakovenko Roman <romain.claret@rocla.ch>
# Written by Divernois Margaux <margaux.divernois@gmail.com>
# Written by Visinand Steve <visinandst@gmail.com>
#
# Date : June 2015
**********************************************************************************/

package layout.toolsbar;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import substance.Substance;
import differentialEquationSolving.SimulationSingleton;

public class JPanelOutils extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelOutils()
		{
		int size = SimulationSingleton.getInstance().getSubstanceList().size();
		tabSubstance = new JPanelOutilSubstance[size];

		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

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
			// JComponent : Instanciation
			for(int i = 0; i < tabSubstance.length; i++)
			{
			Substance substance = SimulationSingleton.getInstance().getSubstanceAt(i);
			tabSubstance[i] = new JPanelOutilSubstance(substance);
			}

			FlowLayout flowlayout = new FlowLayout(FlowLayout.CENTER);
			setLayout(flowlayout);

			Box boxV = Box.createVerticalBox();

			for(int i = 0; i < tabSubstance.length; i++)
			{
				boxV.add(tabSubstance[i]);
			}

			add(boxV);

			setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Substance"));
		}

	private void control()
		{
		// rien
		}

	private void appearance()
		{
		int height = this.getHeight();
		setPreferredSize(new Dimension(160,height));
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private JPanelOutilSubstance[] tabSubstance;

	}
