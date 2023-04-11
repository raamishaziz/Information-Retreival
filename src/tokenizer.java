import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.lang.Object;

public class tokenizer {
	
	public static void main(String[]args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		
	
			
		
		File dir = new File("C:\\Users\\Raamish\\eclipse-workspace\\Assignment\\corpus\\corpus2\\");
		File stopList = new File("C:\\Users\\Raamish\\eclipse-workspace\\Assignment\\stoplist.txt"); 
		BufferedReader buff = new BufferedReader(new FileReader(stopList));
		String st1;
		Map<String,Integer> TermIndex = new HashMap<String,Integer>();
		
		ArrayList<String> stopWords = new ArrayList<String>();
		while((st1 = buff.readLine()) !=null) {
			stopWords.add(st1);
		}
//		System.out.println(stopWords);
		
		int docIds = 1;
		int termIds = 1;
		
		  String tokens[] = null;
		for (File file : dir.listFiles()) {
			
		
			
//			FileInputStream fis = new FileInputStream(file);
//			byte[] data = new byte[(int) file.length()];
//			fis.read(data);
//			
//			
//			String str = new String(data, "UTF-8");
//			Document doc = Jsoup.parse(str);
//			String text = doc.body().text();
//			System.out.println(text);
//			
//			fis.close();
		    BufferedReader br = new BufferedReader(new FileReader(file));
		    boolean scriptCheck = false;
		    String st;
		    String check = "";
		    while((st = br.readLine()) != null) {
		    	if(st.contains("<") && !(st.contains("WARC")) && !(st.contains("script")) && !(st.contains("var")) && !((st.indexOf("{")==0))) {
		    		if(st.contains("script")) {
		    			scriptCheck = true;
		    			
		    		}
		    		if(st.contains("/script")) {
		    			scriptCheck = false;
		    		}
		    	
		    		Document doc = Jsoup.parse(st);
		    		String text = doc.body().text();
//		    		System.out.println(text);
		    		if(!(text.isEmpty()) && !(text.contains("src")) && !(scriptCheck)) {
		    			check+=" ";
		    			check+=text;
//		    			System.out.println(text);
		    		}
		    		
		    	}
		    	else {
		    		// ignoring headers
		    	}
		    		
		    		
		    }
		    

		     tokens = check.split("\\s+");
//		     tokens = check.split("\\s|,|\\/|-|\\&");
		     check="";
		     ArrayList<String> tokensList = new ArrayList<String>();
		     
		 	for(int i=0;i<tokens.length;i++) {
				tokens[i] = tokens[i].toLowerCase();     //turn to lower case
				
		 	}
		 	
		 	for(int i=0;i<tokens.length;i++) {
		 		tokensList.add(tokens[i]);
		 	}
		 	
		 	
		 	
		 	tokensList.removeAll(stopWords);       // removing stopwords
		
		 
		   
		   	 File wfile2 = new File("C:\\Users\\Raamish\\eclipse-workspace\\Assignment\\src\\termIds.txt");
		        if (!wfile2.exists()) {
		            wfile2.createNewFile();
		        }

		        FileWriter fw2 = new FileWriter(wfile2.getAbsoluteFile(), true);
		        BufferedWriter bw2 = new BufferedWriter(fw2);
		       
	            

		 	
		 PorterStemmer stem = new PorterStemmer();
		 ArrayList<String> StemWords = new ArrayList<String>();
		 for(int i=0;i<tokensList.size();i++) {                           //Stemming (PortersStemmer)
			 String word = tokensList.get(i);
//			 System.out.println(word.length());
			 if(word.length()>50) {
				 tokensList.remove(i);
				 
			 }
			 if(word.length()>2 && word.length()<50) {
				 String StemmedWord = stem.stem(word);
				 
				 StemWords.add(StemmedWord);
				 if(TermIndex.containsKey(StemmedWord)) {
					 TermIndex.put(StemmedWord, TermIndex.get(StemmedWord)+1);
				 }
				 else {
					 TermIndex.put(StemmedWord, 1);
				 }
				 bw2.write(termIds + "\\" + StemmedWord);
		         bw2.newLine();
			     termIds+=1;
				 
			 }
			
		 }
		 
		 System.out.println("-------------------------------");
		 bw2.close();
		 
		 //Writing docIds file
		 
		 File wfile = new File("C:\\Users\\Raamish\\eclipse-workspace\\Assignment\\src\\docids.txt");
		 String currentFile = file.getName();
		 System.out.println(currentFile);
	        
	        if (!wfile.exists()) {
	            wfile.createNewFile();
	        }

	        FileWriter fw = new FileWriter(wfile.getAbsoluteFile(), true);
	        BufferedWriter bw = new BufferedWriter(fw);
	       
            bw.write(docIds + "\\" + currentFile );
            bw.newLine();
	        bw.close();
	        docIds+=1;
	        
	  
		 
		 	
		}
		
		System.out.print(TermIndex);
		
		
		 File wfile3 = new File("C:\\Users\\Raamish\\eclipse-workspace\\Assignment\\src\\term_index.txt");
		 
		
	        
	        if (!wfile3.exists()) {
	            wfile3.createNewFile();
	        }

	        FileWriter fw = new FileWriter(wfile3.getAbsoluteFile(), true);
	        BufferedWriter bw = new BufferedWriter(fw);
	        
	        for ( Map.Entry<String, Integer> entry : TermIndex.entrySet()) {
	            String key = entry.getKey();
	            Integer value = entry.getValue();
	            bw.write(key + "  Occured " +  value + " times. " );
	             bw.newLine();
	 	        
	            // do something with key and/or tab
	        }
	        bw.close();
	      
		
	}

}
