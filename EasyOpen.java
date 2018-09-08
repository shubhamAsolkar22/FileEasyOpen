package home;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;

public class EasyOpen {
	//now will work if two file exist with same names in hierarchical manner.....collision handled using linked list
	private Map< String , LinkedList<File> > allFilesinDirectory;
	
	public EasyOpen(String directoryPath) throws  DirectoryDoesntExistException{
		File dir = new File(directoryPath);
		Map < String , LinkedList<File> > afid = new HashMap<String, LinkedList<File>>();
		if(dir.exists()){
			createMap(dir,afid);
			allFilesinDirectory = afid;
		}
		else throw new DirectoryDoesntExistException("Directory: " +directoryPath+" doesn't exist");
	}
	
	/*public Map<String,File> getAllFilesInDirectory(){
		return allFilesinDirectory;
	}*/

	private void createMap(File dir, Map<String, LinkedList<File>> afid) {
		File[] fArr = dir.listFiles();

		for(File f: fArr) {
			
			if(f.isFile())
			{	//if file name already exists in map add current file to linked list
				if(afid.get(f.getName()) != null) {
					afid.get(f.getName()).add(f);
				}
				else {	//otherwise create a new Linked list and add it to the map
					LinkedList<File> ll = new LinkedList<>();
					ll.add(f);
					afid.put(f.getName(), ll);
				}
				
				
			}
			
			if(f.isDirectory()) {
				createMap(f,afid);
			}				
		}
	}
	
	public Map<String, Set<String>> getFileNamesAndAbsolutePaths(){
		Map<String,Set<String>> tempMap = new HashMap<>();
		for(Entry<String, LinkedList<File>> e : allFilesinDirectory.entrySet()) {

			Set<String> ll = new HashSet<>();
			for(File file : e.getValue()) {
				ll.add(file.getAbsolutePath());
			}
			tempMap.put(e.getKey(), ll);
		}		
		return tempMap;
	}

	public static void main (String args[]) throws DirectoryDoesntExistException {
		EasyOpen eo  = new EasyOpen("E:\\nptel");
		System.out.println(eo.getFileNamesAndAbsolutePaths());
	}
}
