/*******************************************************************************
 * Copyright (C) 2011 Daniel Murphy and Peter Brewer.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     Daniel Murphy
 *     Peter Brewer
 ******************************************************************************/
package org.tellervo.desktop.bulkdataentry.command;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tellervo.desktop.bulkdataentry.control.PopulateFromODKFileEvent;
import org.tellervo.desktop.bulkdataentry.model.BulkImportModel;
import org.tellervo.desktop.bulkdataentry.model.ElementModel;
import org.tellervo.desktop.bulkdataentry.model.ObjectModel;
import org.tellervo.desktop.bulkdataentry.model.SampleModel;
import org.tellervo.desktop.bulkdataentry.model.SingleElementModel;
import org.tellervo.desktop.bulkdataentry.model.SingleObjectModel;
import org.tellervo.desktop.bulkdataentry.model.SingleSampleModel;
import org.tellervo.desktop.bulkdataentry.model.TridasFileList;
import org.tellervo.desktop.bulkdataentry.view.ODKParserLogViewer;
import org.tellervo.desktop.bulkdataentry.view.odkwizard.ODKImportWizard;
import org.tellervo.desktop.core.App;
import org.tellervo.desktop.odk.ODKParser;
import org.tellervo.desktop.odk.fields.ODKFieldInterface;
import org.tellervo.desktop.odk.fields.ODKFields;
import org.tellervo.desktop.prefs.Prefs.PrefKey;
import org.tellervo.desktop.util.DictionaryUtil;
import org.tellervo.desktop.wsi.tellervo.TridasElementTemporaryCacher;
import org.tridas.io.util.ITRDBTaxonConverter;
import org.tridas.io.util.TridasUtils;
import org.tridas.schema.NormalTridasShape;
import org.tridas.schema.TridasElement;
import org.tridas.schema.TridasFile;
import org.tridas.schema.TridasObject;
import org.tridas.schema.TridasSample;
import org.tridas.util.TridasObjectEx;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import au.com.bytecode.opencsv.CSVWriter;

import com.dmurph.mvc.IllegalThreadException;
import com.dmurph.mvc.IncorrectThreadException;
import com.dmurph.mvc.MVC;
import com.dmurph.mvc.MVCEvent;
import com.dmurph.mvc.control.ICommand;


public class PopulateFromODKFileCommand implements ICommand {
	
	TridasElementTemporaryCacher cache = new TridasElementTemporaryCacher();
	String otherErrors = "";
	Integer filesLoadedSuccessfully = 0;
	Integer filesFound = 0;
	private static final Logger log = LoggerFactory.getLogger(PopulateFromODKFileCommand.class);

	private static void debugODKCodes()
	{
		ODKFieldInterface[] fields = ODKFields.getFieldsAsArray(TridasObject.class);
		for(ODKFieldInterface field : fields)
		{
			log.debug(field.getFieldCode());
		}
		
		ODKFieldInterface[] fields2 = ODKFields.getFieldsAsArray(TridasElement.class);
		for(ODKFieldInterface field : fields2)
		{
			log.debug(field.getFieldCode());
		}
		
		ODKFieldInterface[] fields3 = ODKFields.getFieldsAsArray(TridasSample.class);
		for(ODKFieldInterface field : fields3)
		{
			log.debug(field.getFieldCode());
		}
	}
	
	public void execute(MVCEvent argEvent) {

		try {
			MVC.splitOff(); // so other mvc events can execute
		} catch (IllegalThreadException e) {
			// this means that the thread that called splitOff() was not an MVC thread, and the next event's won't be blocked anyways.
			e.printStackTrace();
		} catch (IncorrectThreadException e) {
			// this means that this MVC thread is not the main thread, it was already splitOff() previously
			e.printStackTrace();
		}
		
		PopulateFromODKFileEvent event = (PopulateFromODKFileEvent) argEvent;
		ArrayList<ODKParser> filesProcessed = new ArrayList<ODKParser>();
		ArrayList<ODKParser> filesFailed = new ArrayList<ODKParser>();
		
		
		ODKImportWizard wizard = new ODKImportWizard(BulkImportModel.getInstance().getMainView());
		
		
		String instanceFolder;
		if(wizard.isRemoteAccessSelected())
		{
			instanceFolder = getRemoteODKFiles();
		}
		else
		{
			instanceFolder = wizard.getODKInstancesFolder();
		}
		
		File folder = new File(instanceFolder);
		if(!folder.exists()) {
		
			log.error("Instances folder does not exist");
			return;
		}

		
		// Compile a hash set of all media files in folders
		File file = null;
		File[] mediaFileArr = null;
		if(wizard.isIncludeMediaFilesSelected())
		{
			HashSet<File> mediaFiles = new HashSet<File>();

			// Array of file extensions to consider as media files
			String[] mediaExtensions = {"jpg", "mpg", "snd"};
			for(String ext : mediaExtensions)
			{
				SuffixFileFilter filter = new SuffixFileFilter("."+ext);
				Iterator<File> it = FileUtils.iterateFiles(folder, filter, TrueFileFilter.INSTANCE);
				while(it.hasNext())
				{
					file = it.next();
					mediaFiles.add(file);
				}
			}
			
			
			// Copy files to new folder
			mediaFileArr = mediaFiles.toArray(new File[mediaFiles.size()]);
			String copyToFolder = wizard.getCopyToLocation();
			for(int i=0; i<mediaFileArr.length; i++)
			{
				file = mediaFileArr[i];
				
				File target = new File(copyToFolder+file.getName());
				try {
					FileUtils.copyFile(file, target, true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mediaFileArr[i] = target;
			}
		}
		
		SuffixFileFilter fileFilter = new SuffixFileFilter(".xml");
		Iterator<File> iterator = FileUtils.iterateFiles(folder, fileFilter, TrueFileFilter.INSTANCE);
		while(iterator.hasNext())
		{
			file = iterator.next();
			filesFound++;

			try {

				if(event.model instanceof ObjectModel)
				{
					ODKParser parser = new ODKParser(file, TridasObject.class);
					filesProcessed.add(parser);

					if(!parser.isValidODKFile()) {
						filesFailed.add(parser);
						continue;
					}

					ObjectModel model = (ObjectModel) event.model;
					addObjectToTableFromParser(parser, model, wizard, mediaFileArr);

				}
				else if (event.model instanceof ElementModel)
				{
					ODKParser parser = new ODKParser(file, TridasElement.class);
					filesProcessed.add(parser);
					
					if(!parser.isValidODKFile()) {
						filesFailed.add(parser);
						continue;
					}

					ElementModel model = (ElementModel) event.model;
					addElementFromParser(parser, model, wizard);
				}
				else if (event.model instanceof SampleModel)
				{
					ODKParser parser = new ODKParser(file, TridasSample.class);
					filesProcessed.add(parser);
					
					if(!parser.isValidODKFile()) {
						filesFailed.add(parser);
						continue;
					}

					SampleModel model = (SampleModel) event.model;
					addSampleFromParser(parser, model, wizard);
				}

			} catch (FileNotFoundException e) {
				otherErrors+="<p color=\"red\">Error loading file:</p>\n"+ ODKParser.formatFileNameForReport(file);
				otherErrors+="<br/>  - File not found<br/><br/>";
			} catch (IOException e) {
				otherErrors+="<p color=\"red\">Error loading file:</p>\n"+ ODKParser.formatFileNameForReport(file);
				otherErrors+="<br/>  - IOException - "+e.getLocalizedMessage()+"<br/><br/>";
			}  catch (Exception e) {
				otherErrors+="<p color=\"red\">Error loading file:</p>\n"+ ODKParser.formatFileNameForReport(file);
				otherErrors+="<br/>  - Exception - "+e.getLocalizedMessage()+"<br/><br/>";				
			}    

		}
		
		StringBuilder log = new StringBuilder();
		
		log.append("<html>\n");
		for(ODKParser parser : filesFailed)
		{
			log.append("<p color=\"red\">Error loading file:</p>\n"+ ODKParser.formatFileNameForReport(parser.getFile()));
			log.append("<br/>  - "+parser.getParseErrorMessage()+"<br/><br/>");
		}
		
		for(ODKParser parser : filesProcessed)
		{		
			if(filesFailed.contains(parser)) continue;
			if(parser.getParseErrorMessage()=="") continue;
			
			log.append("<p color=\"orange\">Warning loading file:</p>\n"+ ODKParser.formatFileNameForReport(parser.getFile()));
			log.append("<br/>  - "+parser.getParseErrorMessage()+"<br/><br/>");
		}
		
		
		if(wizard.isCreateCSVFileSelected())
		{
			try {
				createCSVFile(filesProcessed, wizard.getCSVFilename());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		log.append(otherErrors);
		
		log.append("</html>");
		
		ODKParserLogViewer logDialog = new ODKParserLogViewer(BulkImportModel.getInstance().getMainView());
		logDialog.setLog(log.toString());
		logDialog.setFileCount(filesFound, filesLoadedSuccessfully);
		logDialog.setVisible(true);

	}		

	private String getRemoteODKFiles()
	{
		Path tempFolder = null;
		try {
			tempFolder = Files.createTempDirectory("odkdownload", null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return tempFolder.toAbsolutePath().toString();
	}
		
	private void createCSVFile(ArrayList<ODKParser> parsers, String csvfilename ) throws IOException
	{
		File file = new File(csvfilename);
		file.createNewFile();
		if(!file.canWrite()) throw new IOException("Cannot write to file: "+file.getAbsolutePath());
		
		HashSet<String> fieldNames = new HashSet<String>();
		for(ODKParser parser: parsers)
		{
			HashMap<String, String> fields = parser.getAllFields();
		    Iterator it = fields.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        fieldNames.add((String) pair.getKey());
		    }
		}
		
		String[] fieldNamesArr =fieldNames.toArray(new String[fieldNames.size()]);
		
		
		String[][] table = new String[parsers.size()][fieldNames.size()];
		

		for(int r=0; r<parsers.size(); r++)
		{
			ODKParser parser = parsers.get(r);
			
			for(int c=0; c<fieldNamesArr.length; c++)
			{	
				table[r][c] = parser.getFieldValueAsString(fieldNamesArr[c]);
			}
		}
			
		CSVWriter writer = new CSVWriter(new FileWriter(csvfilename), '\t');
		


		String[] header = fieldNames.toArray(new String[fieldNames.size()]);
		
		writer.writeNext(header);
		for(int r=0; r<table.length; r++)
		{
			writer.writeNext(table[r]);
		}
		
		writer.close();


		
	}
	
	/*@Override
	public void execute(MVCEvent argEvent) {

		try {
			MVC.splitOff(); // so other mvc events can execute
		} catch (IllegalThreadException e) {
			// this means that the thread that called splitOff() was not an MVC thread, and the next event's won't be blocked anyways.
			e.printStackTrace();
		} catch (IncorrectThreadException e) {
			// this means that this MVC thread is not the main thread, it was already splitOff() previously
			e.printStackTrace();
		}
		
		//debugODKCodes();
		PopulateFromODKFileEvent event = (PopulateFromODKFileEvent) argEvent;
		ArrayList<ODKParser> filesProcessed = new ArrayList<ODKParser>();
		ArrayList<ODKParser> filesFailed = new ArrayList<ODKParser>();
		
		JFileChooser fc = new JFileChooser(App.prefs.getPref(PrefKey.FOLDER_LAST_READ, null));
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {

			App.prefs.setPref(PrefKey.FOLDER_LAST_READ, fc.getSelectedFile().getAbsolutePath());

			SuffixFileFilter fileFilter = new SuffixFileFilter(".xml");
			@SuppressWarnings("unchecked")
			Iterator<File> iterator = FileUtils.iterateFiles(fc.getSelectedFile(), fileFilter, TrueFileFilter.INSTANCE);	

			File file = null;




			while(iterator.hasNext())
			{
				file = iterator.next();
				filesFound++;

				try {

					if(event.model instanceof ObjectModel)
					{
						ODKParser parser = new ODKParser(file, TridasObject.class);
						filesProcessed.add(parser);

						if(!parser.isValidODKFile()) {
							filesFailed.add(parser);
							continue;
						}

						ObjectModel model = (ObjectModel) event.model;
						addObjectFromParser(parser, model);

					}
					else if (event.model instanceof ElementModel)
					{
						ODKParser parser = new ODKParser(file, TridasElement.class);
						filesProcessed.add(parser);
						
						if(!parser.isValidODKFile()) {
							filesFailed.add(parser);
							continue;
						}

						ElementModel model = (ElementModel) event.model;
						addElementFromParser(parser, model);
					}
					else if (event.model instanceof SampleModel)
					{
						ODKParser parser = new ODKParser(file, TridasSample.class);
						filesProcessed.add(parser);
						
						if(!parser.isValidODKFile()) {
							filesFailed.add(parser);
							continue;
						}

						SampleModel model = (SampleModel) event.model;
						addSampleFromParser(parser, model);
					}

				} catch (FileNotFoundException e) {
					otherErrors+="<p color=\"red\">Error loading file:</p>\n"+ ODKParser.formatFileNameForReport(file);
					otherErrors+="<br/>  - File not found<br/><br/>";
				} catch (IOException e) {
					otherErrors+="<p color=\"red\">Error loading file:</p>\n"+ ODKParser.formatFileNameForReport(file);
					otherErrors+="<br/>  - IOException - "+e.getLocalizedMessage()+"<br/><br/>";
				}  catch (Exception e) {
					otherErrors+="<p color=\"red\">Error loading file:</p>\n"+ ODKParser.formatFileNameForReport(file);
					otherErrors+="<br/>  - Exception - "+e.getLocalizedMessage()+"<br/><br/>";				}    

			}
			
			StringBuilder log = new StringBuilder();
			
			log.append("<html>\n");
			for(ODKParser parser : filesFailed)
			{
				log.append("<p color=\"red\">Error loading file:</p>\n"+ ODKParser.formatFileNameForReport(parser.getFile()));
				log.append("<br/>  - "+parser.getParseErrorMessage()+"<br/><br/>");
			}
			
			for(ODKParser parser : filesProcessed)
			{
				if(filesFailed.contains(parser)) continue;
				if(parser.getParseErrorMessage()=="") continue;
				
				log.append("<p color=\"orange\">Warning loading file:</p>\n"+ ODKParser.formatFileNameForReport(parser.getFile()));
				log.append("<br/>  - "+parser.getParseErrorMessage()+"<br/><br/>");
			}
			
			log.append(otherErrors);
			
			log.append("</html>");
			
			ODKParserLogViewer logDialog = new ODKParserLogViewer(null);
			logDialog.setLog(log.toString());
			logDialog.setFileCount(filesFound, filesLoadedSuccessfully);
			logDialog.setVisible(true);

		}		



	}*/
	
	private void addObjectToTableFromParser(ODKParser parser, ObjectModel model, ODKImportWizard wizard, File[] mediaFileArr)
	{
		SingleObjectModel newrow = (SingleObjectModel) model.createRowInstance();

		String objcode = parser.getFieldValueAsString("tridas_parent_object_code");
		TridasObjectEx obj = null;
		if(objcode!=null) obj = getTridasObjectByCode(objcode);
		if(obj!=null) newrow.setProperty(SingleObjectModel.PARENT_OBJECT, obj);
		
		String objectcode = parser.getFieldValueAsString("tridas_object_code", "PlotSubplotID");
		newrow.setProperty(SingleObjectModel.OBJECT_CODE, objectcode);
		newrow.setProperty(SingleObjectModel.TITLE, parser.getFieldValueAsString("tridas_object_title", "PlotSubplotID"));

		
		try{
			newrow.setProperty(SingleObjectModel.TYPE, DictionaryUtil.getControlledVocForName(parser.getFieldValueAsString("tridas_object_type"), "objectTypeDictionary"));
		} catch (Exception e)
		{
			log.debug("Error getting dictionary value from tag");
			newrow.setProperty(SingleObjectModel.TYPE, DictionaryUtil.getControlledVocForName("Forest", "objectTypeDictionary"));

		}
		String comments = "";
		if(parser.getFieldValueAsString("tridas_object_comments", "Comments")!=null) comments += parser.getFieldValueAsString("tridas_object_comments", "Comments")+"; ";
		comments += "Imported from ODK file: '"+ parser.getFile().getName()+"'. ";
		newrow.setProperty(SingleObjectModel.COMMENTS, comments);
		String description = parser.getFieldValueAsString("tridas_object_description");
		if(parser.getFieldValueAsString("StandType")!=null) description += " "+parser.getFieldValueAsString("StandType");

		newrow.setProperty(SingleObjectModel.DESCRIPTION, description);
		newrow.setProperty(SingleObjectModel.LATITUDE, parser.getLatitude("tridas_object_location", "Location"));
		newrow.setProperty(SingleObjectModel.LONGITUDE, parser.getLongitude("tridas_object_location", "Location"));
		newrow.setProperty(SingleObjectModel.LOCATION_TYPE, DictionaryUtil.getControlledVocForName(parser.getFieldValueAsString("tridas_object_location_type"), "locationTypeDictionary"));
		newrow.setProperty(SingleObjectModel.LOCATION_PRECISION, parser.getError("tridas_object_location", "Location"));
		newrow.setProperty(SingleObjectModel.LOCATION_COMMENT, parser.getFieldValueAsString("tridas_object_location_comments"));
		newrow.setProperty(SingleObjectModel.ADDRESSLINE1, parser.getFieldValueAsString("tridas_object_address_line1"));
		newrow.setProperty(SingleObjectModel.ADDRESSLINE2, parser.getFieldValueAsString("tridas_object_address_line2"));
		newrow.setProperty(SingleObjectModel.CITY_TOWN, parser.getFieldValueAsString("tridas_object_address_cityortown"));
		newrow.setProperty(SingleObjectModel.STATE_PROVINCE_REGION, parser.getFieldValueAsString("tridas_object_address_stateorprovince"));
		newrow.setProperty(SingleObjectModel.POSTCODE, parser.getFieldValueAsString("tridas_object_address_postalcode"));
		newrow.setProperty(SingleObjectModel.COUNTRY, parser.getFieldValueAsString("tridas_object_address_country"));
		newrow.setProperty(SingleObjectModel.VEGETATION_TYPE, parser.getFieldValueAsString("tridas_object_vegetation_type", "VegetationType"));
		

		// Still to handle
		String filephoto = parser.getFieldValueAsString("tridas_object_file_photo");
		
		File f = getFileFromList(mediaFileArr, filephoto);
		
		if(f!=null)
		{
			// Rename file if requested
			if(wizard.isRenameMediaFilesSelected())
			{			
				try {
					Path path = renameFile(f, objectcode+"."+FilenameUtils.getExtension(f.getName()));
					f = path.toFile();
					
										
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			String finalloc = wizard.getFinalLocation();
			if(!finalloc.endsWith("/")) finalloc = finalloc+"/";
			String fullurl = finalloc+f.getName();
			
			TridasFileList tfl = new TridasFileList();
			TridasFile tf = new TridasFile();
			tf.setHref(fullurl);
			tfl.add(tf);
			
			newrow.setProperty(SingleObjectModel.FILES, tfl);
		
		}
		else
		{
			log.warn("Media file '"+filephoto +"' not found. Ignoring.");
		}
		

		
		//tridas_object_file_photo
		//tridas_object_file_sound
		//tridas_object_file_video
		
		model.getRows().add(newrow);
		filesLoadedSuccessfully++;
	}
	
	/**
	 * Search for a file in the File[] based on the filename ignoring the path
	 * 
	 * @param mediaFileArr
	 * @param filename
	 * @return
	 */
	private File getFileFromList(File[] mediaFileArr, String filename)
	{
		for(File f :mediaFileArr)
		{
			if(f.getName().equals(filename))
			{
				return f;
			}
		}
		
		return null;
	}
	
	/**
	 * Rename a file, ensuring the new file is unique.  If a file with the suggested new name already exists then an index is added to the end of the filename.
	 * 
	 * @param fileToRename
	 * @param newname
	 * @return
	 * @throws IOException
	 */
	private Path renameFile(File fileToRename, String newname) throws IOException
	{
		File newFile = getUniqueFilename(new File(fileToRename.getParent(), newname));
		
		
		return Files.move(fileToRename.toPath(), newFile.toPath(), StandardCopyOption.ATOMIC_MOVE);
		
	}
	
	/**
	 * Ensures a filename is unique by adding an index on the end if the file already exists
	 * 
	 * @param file
	 * @return
	 */
	private File getUniqueFilename(File file)
	{
		if(file.exists())
		{
			file = getUniqueFilename(file, 1);
		}

		return file;
		
	}
	
	/**
	 * Ensures a filename is unique by adding an index on the end if the file already exists
	 * 
	 * @param file
	 * @param i
	 * @return
	 */
	private File getUniqueFilename(File file, int i)
	{
		String index = "("+i+").";
		File originalfile = file;
		String filename = file.getAbsolutePath();
		file = new File(FilenameUtils.getPrefix(filename)+
				FilenameUtils.getPath(filename)+
				FilenameUtils.removeExtension(file.getName())+
				index+
				FilenameUtils.getExtension(filename));
		
		if(file.exists())
		{
			return getUniqueFilename(originalfile, i+1);
		}
		
		return file;
		
	}

	private void addElementFromParser(ODKParser parser, ElementModel model, ODKImportWizard wizard)
	{
		SingleElementModel newrow = (SingleElementModel) model.createRowInstance();

		String objcode = parser.getFieldValueAsString("tridas_object_code", "PlotSubplotID").toString();
		TridasObjectEx obj = getTridasObjectByCode(objcode);
		newrow.setProperty(SingleElementModel.OBJECT, obj);
		
		NormalTridasShape shape = null;
		try{
			String shapename = parser.getFieldValueAsString("tridas_element_shape").toString();
			for(NormalTridasShape val : NormalTridasShape.values())
			{
				if(val.name().equals(shapename)) shape = val;
				break;
			}
			if(shape!=null) newrow.setProperty(SingleElementModel.SHAPE, shape);
		} catch (Exception e)
		{
			
		}

		
		newrow.setProperty(SingleElementModel.TITLE, parser.getFieldValueAsString("tridas_element_title", "TreeNO"));
		newrow.setProperty(SingleElementModel.COMMENTS, parser.getFieldValueAsString("tridas_element_comments", "Comments"));
		try{
			newrow.setProperty(SingleElementModel.TYPE, DictionaryUtil.getControlledVocForName(parser.getFieldValueAsString("tridas_element_type"), "elementTypeDictionary"));
		} catch (Exception e)
		{
			log.debug("Failed to get element type from ODK");
		}
		newrow.setProperty(SingleElementModel.DESCRIPTION, parser.getFieldValueAsString("tridas_element_description"));
		
		
		
		newrow.setProperty(SingleElementModel.TAXON, ITRDBTaxonConverter.getControlledVocFromString(parser.getFieldValueAsString("tridas_element_taxon", "Species")));
		newrow.setProperty(SingleElementModel.AUTHENTICITY, parser.getFieldValueAsString("tridas_element_authenticity"));
		newrow.setProperty(SingleElementModel.LATITUDE, parser.getLatitude("tridas_element_location", "TreeLocation"));
		newrow.setProperty(SingleElementModel.LONGITUDE, parser.getLongitude("tridas_element_location", "TreeLocation"));
		newrow.setProperty(SingleElementModel.LOCATION_TYPE, DictionaryUtil.getControlledVocForName(parser.getFieldValueAsString("tridas_element_location_type"), "locationTypeDictionary"));
		newrow.setProperty(SingleElementModel.LOCATION_PRECISION, parser.getError("tridas_element_location", "TreeLocation"));
		newrow.setProperty(SingleElementModel.LOCATION_COMMENT, parser.getFieldValueAsString("tridas_element_location_comments"));
		newrow.setProperty(SingleElementModel.ADDRESSLINE1, parser.getFieldValueAsString("tridas_element_address_line1"));
		newrow.setProperty(SingleElementModel.ADDRESSLINE2, parser.getFieldValueAsString("tridas_element_address_line2"));
		newrow.setProperty(SingleElementModel.CITY_TOWN, parser.getFieldValueAsString("tridas_element_address_cityortown"));
		newrow.setProperty(SingleElementModel.STATE_PROVINCE_REGION, parser.getFieldValueAsString("tridas_element_address_stateorprovince"));
		newrow.setProperty(SingleElementModel.POSTCODE, parser.getFieldValueAsString("tridas_element_address_postalcode"));
		newrow.setProperty(SingleElementModel.COUNTRY, parser.getFieldValueAsString("tridas_element_address_country"));
		newrow.setProperty(SingleElementModel.PROCESSING, parser.getFieldValueAsString("tridas_element_processing"));
		newrow.setProperty(SingleElementModel.MARKS, parser.getFieldValueAsString("tridas_element_marks"));
		newrow.setProperty(SingleElementModel.BEDROCK_DESCRIPTION, parser.getFieldValueAsString("tridas_element_bedrock_description"));
		newrow.setProperty(SingleElementModel.SOIL_DESCRIPTION, parser.getFieldValueAsString("tridas_element_soil_description"));
		newrow.setProperty(SingleElementModel.SLOPE_ANGLE, parser.getFieldValueAsInteger("tridas_element_slope_angle"));
		newrow.setProperty(SingleElementModel.SLOPE_AZIMUTH, parser.getFieldValueAsInteger("tridas_element_slope_azimuth"));
		newrow.setProperty(SingleElementModel.SOIL_DEPTH, parser.getFieldValueAsDouble("tridas_element_soil_depth"));
		newrow.setProperty(SingleElementModel.ALTITUDE, parser.getElevation("tridas_element_location", "TreeLocation"));

		// Still to handle
		//tridas_element_file_photo
		//tridas_element_file_sound
		//tridas_element_file_video
		
		model.getRows().add(newrow);
		filesLoadedSuccessfully++;
	}


	private void addSampleFromParser(ODKParser parser, SampleModel model, ODKImportWizard wizard)
	{
		Boolean loadedSuccessfully = false;
		
		NodeList nList = parser.getNodeListByName("group_sample");

		if(nList==null || nList.getLength()==0) {
			nList = parser.getNodeListByName("SamplesRepeat");
		}
		
		if(nList==null || nList.getLength()==0) {
			return;
		}

		for(int i=0; i<nList.getLength(); i++)
		{
			Node node = nList.item(i);
			SingleSampleModel newrow = (SingleSampleModel) model.createRowInstance();

			String objcode = parser.getFieldValueAsString("tridas_object_code", "PlotSubplotID").toString();
			TridasObjectEx obj = getTridasObjectByCode(objcode);
			newrow.setProperty(SingleSampleModel.OBJECT, obj);
								
			TridasElement element = parser.getChrisTridasElement(cache, "tridas_object_code", "tridas_element_title", "PlotSubplotID", "TreeNO");
			if(element==null) continue;
			
			newrow.setProperty(SingleSampleModel.ELEMENT, element);
			newrow.setProperty(SingleSampleModel.TITLE, parser.getFieldValueAsStringFromNodeList("tridas_sample_title", node.getChildNodes()));
			newrow.setProperty(SingleSampleModel.TYPE, DictionaryUtil.getControlledVocForName(parser.getFieldValueAsString("tridas_sample_type"), "sampleTypeDictionary"));
			newrow.setProperty(SingleSampleModel.COMMENTS, parser.getFieldValueAsStringFromNodeList("tridas_sample_comments", node.getChildNodes()));
			newrow.setProperty(SingleSampleModel.DESCRIPTION, parser.getFieldValueAsStringFromNodeList("tridas_sample_description", node.getChildNodes()));
			newrow.setProperty(SingleSampleModel.SAMPLING_DATE, parser.getDate());
			newrow.setProperty(SingleSampleModel.POSITION, parser.getFieldValueAsStringFromNodeList("tridas_sample_position", node.getChildNodes()));
			newrow.setProperty(SingleSampleModel.STATE, parser.getFieldValueAsStringFromNodeList("tridas_sample_state", node.getChildNodes()));
			newrow.setProperty(SingleSampleModel.EXTERNAL_ID, parser.getFieldValueAsStringFromNodeList("tridas_sample_externalid", node.getChildNodes()));

			String knots = parser.getFieldValueAsStringFromNodeList("tridas_sample_knots", node.getChildNodes());
			Boolean kb = null;
			if(knots.equals("Yes"))
			{
				kb = true;
			}
			else
			{
				kb = false;
			}
			if(kb!=null) newrow.setProperty(SingleSampleModel.KNOTS, kb);

			// STILL TO HANDLE
			//tridas_sample_file_photo
			//?tridas_sample_samplingdate


			model.getRows().add(newrow);
			loadedSuccessfully = true;

		}
		
		if(loadedSuccessfully)
		{
			filesLoadedSuccessfully++;
		}
	}

	/**
	 * Query the object dictionary to find an object based on it's lab code
	 * 
	 * @param code
	 * @return
	 */
	private static TridasObjectEx getTridasObjectByCode(String code)
	{
		if(code==null) return null;

		List<TridasObjectEx> entities = App.tridasObjects.getObjectList();

		for(TridasObjectEx obj : entities)
		{
			if(TridasUtils.getGenericFieldByName(obj, TridasUtils.GENERIC_FIELD_STRING_OBJECTCODE).getValue().equals(code))
			{
				return obj;
			}
		}
		return null;
	}
}
