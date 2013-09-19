package org.tellervo.desktop.io.view;

import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.tellervo.desktop.editor.EditorFactory;
import org.tellervo.desktop.io.AbstractDendroReaderFileFilter;
import org.tellervo.desktop.io.DendroReaderFileFilter;
import org.tellervo.desktop.ui.Alert;
import org.tridas.interfaces.ITridasSeries;
import org.tridas.io.AbstractDendroFileReader;
import org.tridas.io.TridasIO;
import org.tridas.io.exceptions.InvalidDendroFileException;
import org.tridas.io.util.TridasUtils;
import org.tridas.schema.NormalTridasUnit;
import org.tridas.schema.NormalTridasVariable;
import org.tridas.schema.TridasDerivedSeries;
import org.tridas.schema.TridasMeasurementSeries;
import org.tridas.schema.TridasValues;

public class ImportDataOnly extends Object {

	private final Window parent;
	private Boolean warnedAboutUnspecifiedVar = false;
	private ArrayList<ITridasSeries> seriesList = new ArrayList<ITridasSeries>();
	private NormalTridasUnit unitsIfNotSpecified = NormalTridasUnit.MICROMETRES;
	private AbstractDendroFileReader reader;
	
	public ImportDataOnly(Window parent, File file, String fileType)
	{
		this.parent = parent;
		parseFile(file, fileType);
	}
	
	public ImportDataOnly(Window parent, File file, DendroReaderFileFilter filetypefilter) throws Exception
	{
		this.parent = parent;
		
		for (String readername : TridasIO.getSupportedReadingFormats())
		{
			AbstractDendroReaderFileFilter filter = new DendroReaderFileFilter(readername);
			if(filter.getDescription().equals(filetypefilter.getDescription()))
			{
				parseFile(file, filter.getDescription());
				return;
			}
		}
		
		throw new Exception("Unsupported dendro data file type.  Do not know "+filetypefilter.toString());
		
	}
	
	public ArrayList<ITridasSeries> getSeries()
	{
		return seriesList;
	}
	
	private void parseFile(File file, String fileType)
	{
		
		
		// Create a reader based on the file type supplied
		
		reader = TridasIO.getFileReader(fileType);
		if(reader==null) 
		{
			Alert.error(parent, "Error", "Unknown file type");
			return;
		}
		
		// Try and load the file
		try {
			reader.loadFile(file.getAbsolutePath());
		} catch (IOException e) {
			Alert.errorLoading(file.getAbsolutePath(), e);
			return;
		} catch (InvalidDendroFileException e) {
			Alert.error(parent, "Error", "The selected file is not a valid "+fileType+ " file.\nPlease check and try again");
			return;
		}
		catch(NullPointerException e)
		{
			Alert.error(parent, "Invalid File", e.getLocalizedMessage());
		}
					
		ArrayList<TridasMeasurementSeries> mseriesList = TridasUtils.getMeasurementSeriesFromTridasContainer(reader.getTridasContainer());
		ArrayList<TridasDerivedSeries> dseriesList = TridasUtils.getDerivedSeriesFromTridasContainer(reader.getTridasContainer());

		seriesList.addAll(mseriesList);
		seriesList.addAll(dseriesList);
		
		Integer count = reader.getProjects()[0].getDerivedSeries().size() + seriesList.size();
		
		if(count>3)
		{
			int n = JOptionPane.showConfirmDialog(
				    parent,
				    "This file contains "+count+" series.\nAre you sure you want to open all of these?",
				    "Multiple series",
				    JOptionPane.YES_NO_OPTION);
			if(n != JOptionPane.YES_OPTION)
			{
				return;
			}
			
		}
		
		Boolean unitsSet = false;
		for(ITridasSeries ser : seriesList)
		{
			for(TridasValues  tv : ser.getValues())
			{	
				if(tv.isSetUnit())
				{
					if(tv.getUnit().isSetNormalTridas())
					{
						unitsSet = true;
					}
				}	
			}
		}
		
		
		
		for(TridasDerivedSeries ser : reader.getProjects()[0].getDerivedSeries())
		{
			for(TridasValues  tv : ser.getValues())
			{	
				if(tv.isSetUnit())
				{
					if(tv.getUnit().isSetNormalTridas())
					{
						unitsSet = true;
					}
				}	
			}
		}
		

		if(unitsSet==false && seriesList.size()>0)
		{
			Object[] possibilities = {"1/1000th mm", 
					"1/100th mm",
					"1/50th mm",
					"1/20th mm",
					"1/10th mm"};
			Object s = JOptionPane.showInputDialog(
			                    parent,
			                    "One or more series has no units defined.\n"
			                    + "Please specify units below:",
			                    "Set Units",
			                    JOptionPane.PLAIN_MESSAGE,
			                    null,
			                    possibilities,
			                    "1/1000th mm");

			if (s.equals("1/1000th mm")) 
			{
			   unitsIfNotSpecified = NormalTridasUnit.MICROMETRES;
			}
			else if (s.equals("1/100th mm")) 
			{
				   unitsIfNotSpecified = NormalTridasUnit.HUNDREDTH_MM;
			}
			else if (s.equals("1/50th mm")) 
			{
				   unitsIfNotSpecified = NormalTridasUnit.FIFTIETH_MM;
			}	
			else if (s.equals("1/20th mm")) 
			{
				   unitsIfNotSpecified = NormalTridasUnit.TWENTIETH_MM;
			}	
			else if (s.equals("1/10th mm")) 
			{
				   unitsIfNotSpecified = NormalTridasUnit.TENTH_MM;
			}		
			else
			{
				Alert.error(parent, "Error", "Invalid measurement units specified");
				return;
			}
		}
	}
	
	public void openEditors()
	{
		openEditors(false);
	}
	
	public void openEditorLites()
	{
		openEditors(true);
	}
	
	private void openEditors(Boolean useEditorLite)
	{
		if(seriesList==null || seriesList.size()==0) return;
		
		// Warn if project contains derivedSeries
		if(reader.getProjects()[0].isSetDerivedSeries())
		{
			for(TridasDerivedSeries series : reader.getProjects()[0].getDerivedSeries())	
			{
				EditorFactory.newSeriesFromDerivedSeries(parent, series, unitsIfNotSpecified, useEditorLite);
			}
		}

		for(ITridasSeries series : seriesList)
		{		
			if(series instanceof TridasDerivedSeries) continue;
			
			// Check if series contain data of unknown or unsupported variables
			for(TridasValues grp : series.getValues())
			{
				if(!grp.getVariable().isSetNormalTridas())
				{
					if(!grp.getVariable().isSetNormalStd() || !grp.getVariable().getNormalStd().equals("Tellervo"))
					{
						if(!this.warnedAboutUnspecifiedVar)
						{
							Alert.error(parent, "Error", "The measurement variable was not specified in input file. \nAssuming data are standard ring widths.");
						}
						this.warnedAboutUnspecifiedVar = true;
						grp.getVariable().setNormalTridas(NormalTridasVariable.RING_WIDTH);
					}
				}
			}	
			
			EditorFactory.newSeriesFromMeasurementSeries(null, (TridasMeasurementSeries)series, unitsIfNotSpecified, useEditorLite);
		}
	}
}
