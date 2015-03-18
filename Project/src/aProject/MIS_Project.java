/*
 * Copyright (C) 2012 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package aProject;

import gbl.Anchor;
import gbl.Fill;
import gbl.GBConstraints;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.event.*;
import gov.nasa.worldwind.formats.gpx.GpxReader;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.MarkerLayer;
import gov.nasa.worldwind.pick.PickedObject;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.markers.*;
import gov.nasa.worldwind.util.WWIO;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.xml.sax.SAXException;

import javax.media.nativewindow.util.Insets;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.DateFormatter;
import javax.xml.parsers.ParserConfigurationException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * Demonstrates displaying GPS tracks using markers. When the view is far away from the data, only a few markers are
 * displayed. As the view zooms in, more markers appear to fill in the track data. When the mouse hovers over a marker
 * the coordinates of that point will be printed to stdout.
 *
 * @author tag
 * @version $Id$
 */
public class MIS_Project extends ApplicationTemplate
{
	protected static final String TRACK_PATH = "gov/nasa/worldwindx/examples/data/tuolumne.gpx";

	protected static class AppFrame extends ApplicationTemplate.AppFrame implements ActionListener, ItemListener
	{

		private JDatePickerImpl endDatePicker;
		private JDatePickerImpl beginDatePicker;
		private JCheckBox pedestrians;
		private JCheckBox cyclists;

		/*
		 * Overloads the no argument AppFrame() constructor
		 */
		public AppFrame()
		{
			//int userRole;

			super(true, false, false);

			//			login dah = new login();
			//			
			//			while(dah.getStatus){
			//				
			//			}

			///sadasdf
		
			//userRole = dah.getrole();

			MarkerLayer layer = this.buildTracksLayer();
			insertBeforeCompass(this.getWwd(), layer);

			//case 
			createMenuBar();
			this.getContentPane().add(createfilterPanel(),BorderLayout.EAST);
			this.getContentPane().add(createDetailsPanel(), BorderLayout.WEST);
			//this.getDatabasePane();

			this.getWwd().addSelectListener(new SelectListener()
			{
				public void selected(SelectEvent event)
				{
					if (event.getTopObject() != null)
					{
						if (event.getTopPickedObject().getParentLayer() instanceof MarkerLayer)
						{
							PickedObject po = event.getTopPickedObject();
							//noinspection RedundantCast
							System.out.printf("Track position %s, %s, size = %f\n",
									po.getValue(AVKey.PICKED_OBJECT_ID).toString(),
									po.getPosition(), (Double) po.getValue(AVKey.PICKED_OBJECT_SIZE));
						}
					}
				}
			});//end addSelectListener



		}//end AppFrame



		/*
		 * This method builds the Details Panel
		 * 
		 * @returns the details panel
		 */
		protected void createMenuBar(){			
			JMenu fileMenu = new JMenu("File");
			fileMenu.setMnemonic('F');

			JMenuItem openItem = new JMenuItem("Open");
			fileMenu.setMnemonic('O');
			fileMenu.add(openItem);
			openItem.addActionListener(this);

			fileMenu.addSeparator();

			JMenuItem exitItem = new JMenuItem("Exit");
			fileMenu.setMnemonic('E');
			fileMenu.add(exitItem);
			exitItem.addActionListener(this);

			JMenuBar bar = new JMenuBar();
			setJMenuBar(bar);
			bar.add(fileMenu); 

		}//end createMenuBar
		/*
		 * This method builds the Details Panel
		 * 
		 * @returns the details panel
		 */
		protected JPanel createDetailsPanel(){			

			JTextField caseNumber  = new JTextField(10);
			JTextField caseType   = new JTextField(10);
			JTextField caseDate = new JTextField(10);
			JTextField caseLong   = new JTextField(10);
			JTextField caseLat  = new JTextField(10);
			JTextField elevation    = new JTextField(10);

			caseNumber.setEditable(false);
			caseType.setEditable(false);
			caseDate.setEditable(false);
			caseLong.setEditable(false);
			caseLat.setEditable(false);
			elevation.setEditable(false);

			JPanel details = new JPanel();
			GridBagLayout gbl = new GridBagLayout();
			details.setLayout(gbl);

			details.add(new JLabel("Details"), new GBConstraints(0,0).anchor(Anchor.CENTER).spanX(2));

			details.add(new JLabel("Case Number: "), new GBConstraints(0,1).anchor(Anchor.WEST));
			details.add(caseNumber ,new GBConstraints(1,1).anchor(Anchor.WEST));

			details.add(new JLabel("Case Type: "),new GBConstraints(0,2).anchor(Anchor.WEST));
			details.add(caseType ,new GBConstraints(1,2).anchor(Anchor.WEST));

			details.add(new JLabel("Case Date: "),new GBConstraints(0,3).anchor(Anchor.WEST));
			details.add(caseDate ,new GBConstraints(1,3).anchor(Anchor.WEST));

			details.add(new JLabel("Longitude: "),new GBConstraints(0,4).anchor(Anchor.WEST));
			details.add(caseLong ,new GBConstraints(1,4).anchor(Anchor.WEST));

			details.add(new JLabel("Latitude: "),new GBConstraints(0,5).anchor(Anchor.WEST));
			details.add(caseLat ,new GBConstraints(1,5).anchor(Anchor.WEST));

			details.add(new JLabel("Elevation: "),new GBConstraints(0,6).anchor(Anchor.WEST));
			details.add(elevation ,new GBConstraints(1,6).anchor(Anchor.WEST));

			details.add(new JPanel(), new GBConstraints(10,10).fill(Fill.BOTH));  //this pushes all components to the top left corner 
			return details; 

		}//end createDetailsPanel

		/*
		 * This method builds the filter Panel
		 * 
		 * @returns the details panel
		 */
		protected JPanel createfilterPanel(){

			JPanel filter = new JPanel();
			GridBagLayout gbl = new GridBagLayout();
			filter.setLayout(gbl);

			LocalDate now = LocalDate.now();
			Properties p = new Properties();
			p.put("text.today", "Today");
			p.put("text.month", "Month");
			p.put("text.year", "Year");

			filter.add(new JLabel("Filter by Date"), new GBConstraints(0,0).anchor(Anchor.CENTER).spanX(2));

			filter.add(new JLabel("End date: "), new GBConstraints(0,1).anchor(Anchor.WEST));

			UtilDateModel eModel = new UtilDateModel();
			eModel.setDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
			eModel.setSelected(true);
			JDatePanelImpl eDatePanel = new JDatePanelImpl(eModel, p);
			endDatePicker = new JDatePickerImpl(eDatePanel, new DateLabelFormatter());
			endDatePicker.addActionListener(this);

			filter.add(endDatePicker, new GBConstraints(1,1));

			filter.add(new JLabel("Start date: "), new GBConstraints(0,2).anchor(Anchor.WEST));

			UtilDateModel bModel = new UtilDateModel();
			bModel.setDate(2000, 0, 1);
			bModel.setSelected(true);
			JDatePanelImpl bDatePanel = new JDatePanelImpl(bModel, p);
			beginDatePicker = new JDatePickerImpl(bDatePanel, new DateLabelFormatter());
			beginDatePicker.addActionListener(this);

			filter.add(beginDatePicker, new GBConstraints(1,2).ipad(1,10));

			filter.add(new JSeparator(JSeparator.HORIZONTAL), new GBConstraints(0,3).fill(Fill.HORIZONTAL).spanX(2));

			filter.add(new JLabel("Filter by Type"), new GBConstraints(0,4).anchor(Anchor.CENTER).spanX(2));

			pedestrians = new JCheckBox("Pedestrians",true);
			pedestrians.addItemListener(this);
			cyclists = new JCheckBox("Cyclists",true);
			cyclists.addItemListener(this);			 

			filter.add(pedestrians, new GBConstraints(0,5).anchor(Anchor.WEST));
			filter.add(cyclists, new GBConstraints(0,6).anchor(Anchor.WEST));

			filter.add(new JPanel(), new GBConstraints(10,10).fill(Fill.BOTH));  //this pushes all components to the top left corner 
			return filter;

		}//end createDetailsPanel

		/*
		 * Inner class to format the date for the datepicker
		 * 
		 * @returns a DataLabelFormatter for the datepicker. see method above
		 */
		public class DateLabelFormatter extends AbstractFormatter {

			private String datePattern = "yyyy-MM-dd";
			private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

			@Override
			public Object stringToValue(String text) throws ParseException {
				return dateFormatter.parseObject(text);
			}//end stringToValue

			@Override
			public String valueToString(Object value) throws ParseException {
				if (value != null) {
					Calendar cal = (Calendar) value;
					return dateFormatter.format(cal.getTime());
				}

				return "";
			}//end stringToValue

		}//end inner class DateLabelFormatter

		/*
		 * This method builds a layer with tracks on it.
		 * 
		 * @returns a layer with marks on it.
		 */
		protected MarkerLayer buildTracksLayer()
		{
			try
			{
				GpxReader reader = new GpxReader();
				reader.readStream(WWIO.openFileOrResourceStream(TRACK_PATH, this.getClass()));
				Iterator<Position> positions = reader.getTrackPositionIterator();

				BasicMarkerAttributes attrs =
						new BasicMarkerAttributes(Material.RED, BasicMarkerShape.SPHERE, 1d);

				ArrayList<Marker> markers = new ArrayList<Marker>();
				while (positions.hasNext())
				{
					markers.add(new BasicMarker(positions.next(), attrs));
				}

				MarkerLayer layer = new MarkerLayer(markers);
				layer.setOverrideMarkerElevation(true);
				layer.setElevation(0);
				layer.setEnablePickSizeReturn(true);

				return layer;
			}
			catch (ParserConfigurationException e)
			{
				e.printStackTrace();
			}
			catch (SAXException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			return null;
		}//end buildTracksLayer

		/*
		 * This method responds to users making changes to the checkbox filters
		 * 
		 * @Override actionPerformed (date picker)
		 */
		public void actionPerformed(ActionEvent event) {

			System.out.println("command: " + event.getActionCommand());

			if(event.getActionCommand().equals("Open")){
				JFileChooser fc = new JFileChooser();
				
				int returnVal = fc.showOpenDialog(this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					//This is where a real application would open the file.
					System.out.println("Opening: " + file.getName() + ".");
				} else {
					System.out.println("Open command cancelled by user.");
				}
			}else if(event.getActionCommand().equals("Exit")){
				//shutdown() save stuff
				System.exit(0);
			}else if(event.getActionCommand().equals("Date selected")){
				Date endDate = (Date) endDatePicker.getModel().getValue();
				Date beginDate = (Date) beginDatePicker.getModel().getValue();

				System.out.println(endDate.toString());
				System.out.println(beginDate.toString());
			}//if either datepicker changed


		}//end actionPerformed


		/*
		 * This method responds to users making changes to the checkbox filters
		 * 
		 * @Override itemStateChanged (checkboxes)
		 */
		public void itemStateChanged(ItemEvent event) {
			if(pedestrians.isSelected()){
				System.out.println("Pedestrians selected");
				//add pedestrians
			}else{
				System.out.println("Pedestrians deselected");
				//filter out pedestrians
			}

			if(cyclists.isSelected()){
				System.out.println("Cyclists selected");
				//add Cyclists
			}else{
				System.out.println("Cyclists deselected");
				//filter out Cyclists
			}
		}//end itemStateChanged

	}//end class AppFrame 


	public static void main(String[] args)
	{
		ApplicationTemplate.start("", AppFrame.class);
	}//end main

}//end MIS_Project
