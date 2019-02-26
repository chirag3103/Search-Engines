import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lemurproject.kstem.*;

class Score{
	String query=null;
	String doc=null;
	Double score=null;
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getDoc() {
		return doc;
	}
	public void setDoc(String doc) {
		this.doc = doc;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	
}



class Dictionary{
	String word=null;
	int docid=0;
	List<Integer> idlist;
	int termpos=0;



	public int getTermpos() {
		return termpos;
	}

	public void setTermpos(int termpos) {
		this.termpos = termpos;
	}

	public List<Integer> getIdlist() {
		return idlist;
	}

	public void setIdlist(List<Integer> idlist) {
		this.idlist = idlist;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getDocid() {
		return docid;
	}

	public void setDocid(int docid) {
		this.docid = docid;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return ((Dictionary)obj).word.equals(this.word) && ((Dictionary)obj).docid==this.docid;
	}

	
	
}
class Sortbyid implements Comparator<Dictionary>
{
	  public int compare(Dictionary a, Dictionary b)
	  {
		  return a.docid-b.docid;
	  }
	  
}

class SortbyScore implements Comparator<Score>{
	
	public int compare(Score a, Score b)
	  {
		  return (int) (b.score-a.score);
	  } 
	
}

class Sortbyword implements Comparator<Dictionary> 
{ 
    // Used for sorting in ascending order of 
    // roll name 
    public int compare(Dictionary a, Dictionary b) 
    { 
        return a.word.compareTo(b.word); 
    } 
} 


public class PositionalInvertedIndex {
	
	static ArrayList<String> querylist;
	static List<String> querylist2;
	static List<String> querylist3;
	static List<String> list;
	static ArrayList<String> Firstdoc;
	static ArrayList<Dictionary> FirstDict;
	static ArrayList<Dictionary> SecondDict;
	static Set<String> stopWordsSet;
	static String word = null;
	static String lword = null;
	static String endString ="doc";
	static Dictionary object;
	static Dictionary posobject;
	static Score scoreobject;
	static PositionalInvertedIndex obj31;
	static KrovetzStemmer obj;
	static Scanner input = null;
	static ArrayList<Dictionary> RepeatedWord;
	static ArrayList<Dictionary> NewRepeatedWord;
	static Map<String, SortedSet<Integer>> newdict;
	static HashMap<String,HashMap<String,ArrayList<Integer>>> newinvertedindex;
	static TreeMap<String,HashMap<String,ArrayList<Integer>>> sortednewinvertedindex;
	static TreeMap<String,SortedSet<Integer>> sorted;
	static TreeMap<String,SortedSet<Double>> sortedscoreset;

	static ArrayList<Dictionary> Termposdict;
	static ArrayList<Score> Scoredict;
	static SortedSet<Integer> p1;
	static SortedSet<Integer> p2;
	static ArrayList<String> p1doclist;
	static ArrayList<String> p2doclist;
	static ArrayList<Integer> p1poslist;
	static ArrayList<Integer> p2poslist;
	static ArrayList<String> intersectdoc;
	static ArrayList<Integer> intersectdoc1;
	static String lowerquerywords;
	static String querywords;
	static String query;
	static ArrayList<Integer> position;
	static ArrayList<String> documents;
	static ArrayList<ArrayList<String>> computelist;
	static ArrayList<String> splited;
	static ArrayList<String> range;


	  
	  //function which performs the part 1 of the assignment
	//summary() is responsible for creating a dictionary FirstDict of words and their docid's
	public void summary (int origj,int documentid, int position) {
		  int count=0;
		  int j=origj;
		  int docid = documentid;
		  int pos=position;
		  
		  while(j<list.size()&count<2) 
		  {
			  lword=list.get(j);
			  
			  if(lword.equals(endString))
			{
				count++;
			}
			  if(count<=2) 
			{
				  //getting the element from the list of lower case words
			  lword=list.get(j);
			  //adding to the sub list
			  Firstdoc.add(lword);
			  object=new Dictionary();
			  object.setWord(lword);
			  object.setDocid(docid);
			  //adding the word and doc id to the dictionary list
			  FirstDict.add(object);
			  //adding the postion of word in a new dictionary
			  posobject=new Dictionary();
			  posobject.setWord(lword);
			  posobject.setDocid(docid);
			  posobject.setTermpos(pos);
			  Termposdict.add(posobject);
			  pos++;
			  j++;
		    }
		}
		    //System.out.println("positions list count "+pos+" doc id "+docid);
	  }
	//function sortbykey is responsible for sorted the inverted index with the help of key in an alphabetical order
	public static void sortbykey() 
    { 
        // TreeMap to store values of HashMap
        sorted = new TreeMap<>(); 
  
        // Copy all data from hashMap into TreeMap 
        sorted.putAll(newdict); 
  
        // Display the TreeMap which is naturally sorted 
    }
	
	public static void termposlistprint() {
	    for (Map.Entry<String,SortedSet<Integer>> entry : sorted.entrySet())  
            System.out.println("[Word,Docf] --> ["+entry.getKey()+","+entry.getValue().size()+"] --> DOC ID: " + entry.getValue());         
    	
	}
	
	public static void invertedindexcreation() {
		newinvertedindex = new HashMap<String, HashMap<String, ArrayList<Integer>>>();
		  
		  
		  int init=0;
		  String variable=null;
		  String documid=null;
		  int posid=0;
		  int newlength=Termposdict.size();
		  while(init<newlength)
		  {
			  variable=Termposdict.get(init).getWord();
			  //if loop for unique or new elements
			  
			  if(newinvertedindex.get(variable)==null) {
				  newinvertedindex.put(variable, new HashMap<String, ArrayList<Integer>>());
			  }
				  HashMap<String, ArrayList<Integer>> docMap=newinvertedindex.get(variable);
				  
				  documid=Termposdict.get(init).getDocid()+"";
				  if(docMap.get(documid)==null) {
					  docMap.put(documid, new ArrayList<Integer>());
					  
				  }
				  
				  
				  posid=Termposdict.get(init).getTermpos();
				  ArrayList<Integer> poslist =docMap.get(documid);				  
				  poslist.add(posid);
				  
				  //add the docid in a set of docids then add the set of that id's along with it's element into the newdict
				  //poslist.add(documid);
				  init++;
			  }
		
	}
	
	//for printing the positional inverted index on the screen
	public static void invertedindexprint() throws IOException {
		System.out.println("---------------------------");

		System.out.println("Positional Inverted Index");

		sortednewinvertedindex = new TreeMap<>(); 
	
        // Copy all data from hashMap into TreeMap 
        sortednewinvertedindex.putAll(newinvertedindex); 
     int tf=0;
     
	  //for displaying purpose
	  for (String word : sortednewinvertedindex.keySet()) {
		  System.out.print("[WORD: "+word);
			HashMap<String, ArrayList<Integer>> map = sortednewinvertedindex.get(word);
			System.out.println(", DF: "+map.keySet().size()+"] -->");
			for (String docName : map.keySet()) {
				System.out.print("[DOCID: \"" + docName + "\"");
				ArrayList<Integer> occurrences = map.get(docName);
				for (Integer i : occurrences) {
					System.out.print(",pos " + i);
					tf++;
				}System.out.print(" , TF: "+tf+"]");
				tf=0;
				System.out.println();
			}
			System.out.println();
		}
	  System.out.println();
	  System.out.println();
	  System.out.println("---------------------------");

	
	}
	//for writing the positional inverted index on a file
	public static void invertedindexwrite() throws IOException {
		FileWriter stream = new FileWriter("invertedindex.txt");
	     PrintWriter out = new PrintWriter(stream);  
		out.println("---------------------------");
	     out.println("Positional Inverted Index");

		sortednewinvertedindex = new TreeMap<>(); 
	
        // Copy all data from hashMap into TreeMap 
        sortednewinvertedindex.putAll(newinvertedindex); 
     int tf=0;
     
	  //for displaying purpose
	  for (String word : sortednewinvertedindex.keySet()) {
			out.print("[WORD: "+word);
			HashMap<String, ArrayList<Integer>> map = sortednewinvertedindex.get(word);
			out.println(", DF: "+map.keySet().size()+"] -->");
			for (String docName : map.keySet()) {
				out.print("[DOCID: \"" + docName + "\"");
				ArrayList<Integer> occurrences = map.get(docName);
				for (Integer i : occurrences) {
					out.print(",pos " + i);
					tf++;
				}out.print(" , TF: "+tf+"]");
				tf=0;
				out.println();
			}
			out.println();
		}
	  out.println();
	  out.println();
	  out.println("----------------------------------");
	out.close();
	}
	
	//returns the documents list in which the term occurs
	public static ArrayList<String> worddoc(String Term) {
		
		position= new ArrayList<Integer>();
		documents=new ArrayList<String>();
		sortednewinvertedindex = new TreeMap<>(); 
        // Copy all data from hashMap into TreeMap 
        sortednewinvertedindex.putAll(newinvertedindex); 
     //int tf=0;
	  //for displaying purpose
	 /*Set<String> term;
	 term=new TreeSet<String>();
	 term.add("you");
	 */
     
     String term=Term;
	 
	 //String docu="4";
     for (String word : sortednewinvertedindex.keySet()) {
			//System.out.println(word);
			HashMap<String, ArrayList<Integer>> map = sortednewinvertedindex.get(word);
			if(word.equals(term))
			{
				for (String fileName : map.keySet()) {
					//System.out.print("doc \"" + fileName + "\"");
					ArrayList<Integer> occurrences = map.get(fileName);
					documents.add(fileName);
					//if(fileName.equals(docu)) {
						for (Integer i : occurrences) {
							//System.out.print(", " + i);
							
							position.add(i);
							//tf++;
						}	
				//	}
					//System.out.print(" tf "+tf);
					//tf=0;
					//System.out.println();
				}	
			}
			
			//System.out.println();
		}
	  //System.out.println();
	  //System.out.println();
    
     
//		System.out.println(term+" present in "+documents);
//		System.out.println(term+" present at "+position);

     return documents;
     
	
	}
	
	//returns the positions list at which the term occurs
	public static ArrayList<Integer> wordpos(String Term) {
		
		position= new ArrayList<Integer>();
		documents=new ArrayList<String>();
		sortednewinvertedindex = new TreeMap<>(); 
        // Copy all data from hashMap into TreeMap 
        sortednewinvertedindex.putAll(newinvertedindex); 
     //int tf=0;
	  //for displaying purpose
	 /*Set<String> term;
	 term=new TreeSet<String>();
	 term.add("you");
	 */
     
     String term=Term;
	 
	 //String docu="4";
     for (String word : sortednewinvertedindex.keySet()) {
			//System.out.println(word);
			HashMap<String, ArrayList<Integer>> map = sortednewinvertedindex.get(word);
			if(word.equals(term))
			{
				for (String fileName : map.keySet()) {
					//System.out.print("doc \"" + fileName + "\"");
					ArrayList<Integer> occurrences = map.get(fileName);
					documents.add(fileName);
					//if(fileName.equals(docu)) {
						for (Integer i : occurrences) {
							//System.out.print(", " + i);
							
							position.add(i);
							//tf++;
						}	
				//	}
					//System.out.print(" tf "+tf);
					//tf=0;
					//System.out.println();
				}	
			}
			
			//System.out.println();
		}
	  //System.out.println();
	  //System.out.println();
    
     
//		System.out.println(term+" present in "+documents);
//		System.out.println(term+" present at "+position);

     return position;
     
	
	}
	
	//returns the position list matching the document of a term
	public static ArrayList<Integer> worddocpos(String Term,String doc) {
		
		position= new ArrayList<Integer>();
		documents=new ArrayList<String>();
		sortednewinvertedindex = new TreeMap<>(); 
        // Copy all data from hashMap into TreeMap 
        sortednewinvertedindex.putAll(newinvertedindex); 
     //int tf=0;
	  //for displaying purpose
	 /*Set<String> term;
	 term=new TreeSet<String>();
	 term.add("you");
	 */
     
     String term=Term;
	 
	 String docu=doc;
     for (String word : sortednewinvertedindex.keySet()) {
			//System.out.println(word);
			HashMap<String, ArrayList<Integer>> map = sortednewinvertedindex.get(word);
			if(word.equals(term))
			{
				for (String fileName : map.keySet()) {
					//System.out.print("doc \"" + fileName + "\"");
					ArrayList<Integer> occurrences = map.get(fileName);
					documents.add(fileName);
					if(fileName.equals(docu)) {
						for (Integer i : occurrences) {
							//System.out.print(", " + i);
							
							position.add(i);
							//tf++;
						}	
					}
					//System.out.print(" tf "+tf);
					//tf=0;
					//System.out.println();
				}	
			}
			
			//System.out.println();
		}
	  //System.out.println();
	  //System.out.println();
    
     
//		System.out.println(term+" present in "+documents);
//		System.out.println(term+" present at "+position);

     return position;
     
	
	}


	//returns the term frequency of a term with respect to the document
	public static int termfreq(String Term,String doc) {
	
	position= new ArrayList<Integer>();
	documents=new ArrayList<String>();
	sortednewinvertedindex = new TreeMap<>(); 
    // Copy all data from hashMap into TreeMap 
    sortednewinvertedindex.putAll(newinvertedindex); 
 int tf=0;
  
 
 String term=Term;
 
 String docu=doc;
 for (String word : sortednewinvertedindex.keySet()) {
		HashMap<String, ArrayList<Integer>> map = sortednewinvertedindex.get(word);
		if(word.equals(term))
		{
			for (String fileName : map.keySet()) {
				ArrayList<Integer> occurrences = map.get(fileName);
				documents.add(fileName);
				if(fileName.equals(docu)) {
					for (Integer i : occurrences) {
						position.add(i);
						tf++;
					}	
				}
			}	
		}
		
	}
 return tf;
 

}

	public static void querytermdocs(String term1,String term2)
	{
		//basically for retrieving the documents of the words in the query
		//we accept the query in the main function
		//process the query in the main again
		String word1=obj.stem(term1);
	
		String word2=obj.stem(term2);
		
		p1 =new TreeSet<Integer>();
		  p2 =new TreeSet<Integer>();

		  
		//first search the terms in the sorted list
		//and get their posting lists
		  System.out.println("------------------------------------------");
		for (Map.Entry<String,SortedSet<Integer>> entry : sorted.entrySet()) {
		
			if(entry.getKey().equals(word1))
			{
				p1=entry.getValue();
				System.out.println("Queried term1 is "+word1+" -> "+p1);
			}
			if(entry.getKey().equals(word2))
			{
				p2=entry.getValue();
				System.out.println("Queried term2 is "+word2+"-> "+p2);
			}
		}
			
			
		}
	//calculates the intersecting document id's for given two terms
	public static void queryresult() {
		int p1size=p1.size();
		int p2size=p2.size();
		int arrp1[]=new int[p1size];
		int arrp2[]=new int[p2size];
		int i=0;
		int docuid=0;
        intersectdoc1=new ArrayList<Integer>();
			
			for(Integer w : p1) {
				arrp1[i]=w;
				i++;
			}
			
			int j=0;
			for(Integer w : p2) {
				arrp2[j]=w;
				j++;
			}
			
		      i = 0;
		      j = 0;
		      while (i < p1size && j < p2size)
		      {
		        if (arrp1[i] < arrp2[j])
		          i++;
		        else if (arrp2[j] < arrp1[i])
		          j++;
		        else
		        {
		        	docuid=arrp2[j++];
		        	//initial printing of the list
		        	//System.out.print(docuid+" ");
		        	intersectdoc1.add(docuid);
		          
		          i++;
		        }
		      }
		      //spaces while printing
		      //System.out.println("");
		      
	}
     
	//new function
	//do no consider due to string and integer mismatch
	//solved
	public static ArrayList<String> queryresultfrominvertedindex(ArrayList<String> newp12,ArrayList<String> newp22) {
		//p1 and p2 need to replaced
		int p1size=newp12.size();
		int p2size=newp22.size();
		int arrp1[]=new int[p1size];
		int arrp2[]=new int[p2size];
		int i=0;
		int docuid=0;
        intersectdoc=new ArrayList<String>();
			
			for(String w : newp12) {
				arrp1[i]=Integer.parseInt(w);
				i++;
			}
			
			int j=0;
			for(String w : newp22) {
				arrp2[j]=Integer.parseInt(w);
				j++;
			}
			
		      i = 0;
		      j = 0;
		      while (i < p1size && j < p2size)
		      {
		        if (arrp1[i] < arrp2[j])
		          i++;
		        else if (arrp2[j] < arrp1[i])
		          j++;
		        else
		        {
		        	docuid=arrp2[j++];
		        	//initial printing of the list
		        	//System.out.print(docuid+" ");
		        	
		        	intersectdoc.add(Integer.toString(docuid));
		          
		          i++;
		        }
		      }
		      //spaces while printing
		      //System.out.println("");
		      //System.out.println("intersected docs are "+intersectdoc);
		      
		      
		      return intersectdoc;
		      
		      
	}
	
	//creates a HashMap of key value pair for creating the dictionary
	public static void createHashMap()
	{
		//Hashmap
		  newdict=new HashMap<String, SortedSet<Integer>>();
		  //Conditions for HashMap
		  //if hashmap does not contain the word then add the word and the doc id set
		  //else if hashmap contains the word then add the docid to the set and then add the set
		  int start=0;
		  String element=null;
		  int docid=0;
		  //int position;
		  int length31=FirstDict.size();
		  while(start<length31)
		  {
			  element=FirstDict.get(start).getWord();
			  //if loop for unique or new elements
			  
			  if(!newdict.containsKey(element)) {
				  docid=FirstDict.get(start).getDocid();
				  SortedSet<Integer> idlist =new TreeSet<Integer>();
				  //add the docid in a set of docids then add the set of that id's along with it's element into the newdict
				  idlist.add(docid);
				  newdict.put(element, idlist);
				  start++;
			  }else {
				  //else loop for non-unique or repeated elements 
				  docid=FirstDict.get(start).getDocid();
				  newdict.get(element).add(docid);
				  start++;
			  }
		  }
	}
	
	public static void queryresultprint(String query) {
		querywords=null;
		  lowerquerywords=null;
		  querylist=new ArrayList<>();
		  
		input=new Scanner(query);
	      input.useDelimiter("[\\W\\s]+");
	      
	      while (input.hasNext()) {
		      querywords = input.next();
		      lowerquerywords = querywords.toLowerCase();
		      if(!stopWordsSet.contains(lowerquerywords)) {
		    	  querylist.add(obj.stem(lowerquerywords));
		      }
		  }
	      System.out.println("The query is: "+query);
	      //System.out.println(querylist1.get(0));
	      querytermdocs(querylist.get(0),querylist.get(1));
	      queryresult();
		  System.out.println("Intersected docs are "+intersectdoc);
	      System.out.println("------------------------------");
		  
	}
	//extracts the range value from the proximity query
	public static void rangefunc(String query){
	      List<String> rangegroup = new ArrayList<String>();
	      Pattern regexnum = Pattern.compile("([0-9]*)\\(");
	      Matcher regexnumMatcher = regexnum.matcher(query);
	      while (regexnumMatcher.find()) {//Finds Matching Pattern in String
		         rangegroup.add(regexnumMatcher.group(1));//Fetching Group from String
		         
		      }
	      for(String str:rangegroup) {
	    	   range.add(str);
	    	
	      }
		
		
	}
	
	//extracts the proximity terms from the proximity query
	public static void proxytermfunc(String query) {
		
	      List<String> termlist = new ArrayList<String>();

	      Pattern regexterm = Pattern.compile("\\((.*?)\\)");
	      
	      Matcher regextermMatcher = regexterm.matcher(query);

	      while (regextermMatcher.find()) {//Finds Matching Pattern in String
	         termlist.add(regextermMatcher.group(1));//Fetching Group from String
	         
	      }
	      //list of proxy terms
	      //splited=new ArrayList<String>();
	      for(String str:termlist) 
	      	{
	    	  String[] splitarr= str.split("\\s+");
	    	  if(!stopWordsSet.contains(splitarr[0])) {
	    		  splited.add(obj.stem(splitarr[0]).toLowerCase());  
	    	  }
	    		
	    	  if(!stopWordsSet.contains(splitarr[1])) {
	    		  splited.add(obj.stem(splitarr[1]).toLowerCase());  
	    	  }
	    	  }
	      /*
	      for(String proxyterms:splited)
	      {
	    	  System.out.println("These are the proxy terms "+proxyterms);
	    	  
		        
	      }		
		*/
		
		
	}
	//main function to evaluate the proximity query which returns
	//the filtered set of documents
	public static ArrayList<String> proximityquery(String query) {
		  //String query="1(great tablet) 2(tablet fast)";
		  //String query="0(touch screen) fix repair";
	      //String query="1(great tablet)";
	      //String query="2(tablet fast)";

	      rangefunc(query);

	   //if the scanner starts with a integer, then group the next two terms, store the integer as the limit for positions and process
	      //if the scanner starts with strings then group the two terms and process
	      proxytermfunc(query);
	      //need to get the documents of the first two terms
	      
	      
	      ArrayList<String> doclist1=new ArrayList<String>();
	      doclist1=worddoc(splited.get(0));
	      
	      ArrayList<String> doclist2=new ArrayList<String>();
	      doclist2=worddoc(splited.get(1));
	      
	      
	      //System.out.println(""+doclist1+" "+doclist2+"");
	      ArrayList<String> proxyintersectdoc = new ArrayList<String>();
	      
	      proxyintersectdoc=queryresultfrominvertedindex(doclist1, doclist2);
	      
	      
	      ArrayList<Integer> positions=new ArrayList<Integer>();
	      
	      
    	  //System.out.println(splited.get(0));

	      for(String docs:proxyintersectdoc)
	      {
	    	  //System.out.print(docs);
	    	  positions=worddocpos(splited.get(0), docs);
	    	  //System.out.println(positions);
	      }
	      
    	  //System.out.println(splited.get(1));

	      for(String docs:proxyintersectdoc)
	      {
	    	  //System.out.print(docs);
	    	  positions=worddocpos(splited.get(1), docs);
	    	  //System.out.println(positions);
	      }
	      
	      
	      //position of the common documents should be < or equal to range
    	  ArrayList<Integer>pos1=new ArrayList<Integer>();
    	  ArrayList<Integer>pos2=new ArrayList<Integer>();
	      //ArrayList<Integer>diff=new ArrayList<Integer>();
    	  int diff;
    	  ArrayList<String> proxydocs=new ArrayList<String>();
	      for(String doc:proxyintersectdoc)
	      {
	    	  //get position of both terms for the same doc
	    	  pos1=worddocpos(splited.get(0), doc);
	    	  pos2=worddocpos(splited.get(1), doc);
	    	  diff=Integer.parseInt(range.get(0));
	    	  
	    	  
	    	  //condition for same size
	    	  if(pos1.size()==pos2.size()) 
	    	  {
	    		  
	    		  if(pos2.get(0)-pos1.get(0)>0&&pos2.get(0)-pos1.get(0)<=diff+1)
	    		  {
	    			  proxydocs.add(doc);
	    			}
	    	  }
	    	  //condition for different sizes
	    	  else if(pos1.size()<=pos2.size()) 
	    	  {
	    		  int i=0;
	    		  int j=0;
	    		  while(i<pos1.size())
	    		  {
	    			  
	    				  while(j<pos2.size()) {
	    					  if(pos2.get(j)-pos1.get(i)>0&&pos2.get(j)-pos1.get(i)<=diff+1)
	    		    		  {
	    						  proxydocs.add(doc);
	    		    			  j++;
	    		    		  }
	    					  else{j++;}
	    	    		  		  }
	    				  i++;
	    				  j=0;
	    			  
	    			}  
	    	  }  
	      }
	      
	      	      
	      /*
	      //for the fourth query only
	      
    	  ArrayList<String> proxydocs1=new ArrayList<String>();

    	  //System.out.println(splited.get(2));

	      for(String docs:proxydocs)
	      {
	    	  //System.out.print(docs);
	    	  positions=worddocpos(splited.get(2), docs);
	    	  //System.out.println(positions);
	      }
	      
    	  //System.out.println(splited.get(3));

	      for(String docs:proxydocs)
	      {
	    	  //System.out.print(docs);
	    	  positions=worddocpos(splited.get(3), docs);
	    	  //System.out.println(positions);
	      }	      
	      for(String doc:proxydocs)
	      {
	    	  //get position of both terms for the same doc
	    	  pos1=worddocpos(splited.get(2), doc);
	    	  pos2=worddocpos(splited.get(3), doc);
	    	  diff=Integer.parseInt(range.get(1));
	    	  
	    	  //condition for size one
	    	  //condition for same size
	    	  if(pos1.size()==pos2.size()) 
	    	  {
	    		  
	    		  if(pos2.get(0)-pos1.get(0)>0&&pos2.get(0)-pos1.get(0)<=diff+1)
	    		  {
	    			  proxydocs1.add(doc);
	    			}
	    	  }
	    	  //condition for different sizes
	    	  else if(pos1.size()<=pos2.size()) 
	    	  {
	    		  int i=0;
	    		  int j=0;
	    		  while(i<pos1.size())
	    		  {
	    			  
	    				  while(j<pos2.size()) {
	    					  if(pos2.get(j)-pos1.get(i)>0&&pos2.get(j)-pos1.get(i)<=diff+1)
	    		    		  {
	    						  proxydocs1.add(doc);
	    		    			  j++;
	    		    		  }
	    					  else{j++;}
	    	    		  		  }
	    				  i++;
	    				  j=0;
	    			  
	    			}  
	    	  }  
	      }

	      	//for 4th query
	      
	      ArrayList<String> doclist=new ArrayList<String>();
	      doclist=queryresultfrominvertedindex(proxydocs, proxydocs1);
	      
	      if(doclist.isEmpty()) {
	    	  System.out.println("There are no documents to rank");
		        
	      }else {
	    	  System.out.println("Intersecting docs are:");
		      for(String docs:doclist) {
		    	  System.out.println(docs);
		      }  
	      }
	      
	      return doclist;
	      */
	      return proxydocs;
	      
	      
	      
	}
	//function for the non proximity queries
	public static void nonproximityquery() {
		String nonproxyquery="nexus like love happy";
		//String nonproxyquery="asus repair";
		
		
		querywords=null;
		  lowerquerywords=null;
		  querylist=new ArrayList<>();
		  
		input=new Scanner(nonproxyquery);
	      input.useDelimiter("[\\W\\s]+");
	      //input.useDelimiter("\\((.*?)\\)");
	      
	      
	      
	      while (input.hasNext()) {
		      querywords = input.next();
		      lowerquerywords = querywords.toLowerCase();
		      if(!stopWordsSet.contains(lowerquerywords)) {
		    	  querylist.add(obj.stem(lowerquerywords));
		      }
		  }
	      
	      //String answer = proxyquery.substring(proxyquery.indexOf("(")+1,proxyquery.indexOf(")"));
		
	      System.out.println("before the condition");
	      
	      int i=0;
	      ArrayList<String>list1;
	      
	      while(i<querylist.size())
	      {
	    	  
		    	  list1=worddoc(querylist.get(i));
		    	  //pass the first list to the fuction
		    	  intersect(list1);
		    	  
				  i++;
		       
	      }
	      
	      //function
	      //accepts the first list and checks the size of the list if not 2
	      //if less than two then simply adds element
	      //if not less than two, it sends both the list to intersect function
	      //removes both the old lists and saves the new list
	      

	      
	      
	      System.out.println("inside the non function");

	      
	      
	      
	}
	//computes the filtered document list
    public static void intersect(ArrayList<String> list1){
	  //System.out.println("new iteration");
	  ArrayList<String> llist1=list1;
	  ArrayList <String> interlist;   	  
  	  int i=0;
  	  
  	  if(computelist.size()==0)
	      {
  		  //System.out.println("added list");
		      computelist.add(llist1);
	      }
  	  else {
	      computelist.add(llist1);

  		      while(i<computelist.size()) {
  		    	  //System.out.println("call made to index()");
  		    	  //System.out.println("first elemet "+computelist.get(i)+" then "+computelist.get(i++)+" second");
  			      interlist=queryresultfrominvertedindex(computelist.get(0),computelist.get(1));
  			      computelist.removeAll(computelist);
  			      computelist.add(interlist);
  			      i++;
  		      }

		        
  	  }
  	}
    //calculates the union set of documents
    public static Set<String> union(ArrayList<String> querylist) {

    	  Set<String> uniondocs=new HashSet<String>();
	    	
	    	for(String term:querylist) {
	    		uniondocs.addAll(worddoc(term));	
	    	}
	  	  	
	      //System.out.println("union docs from union function are "+uniondocs);
	
    return uniondocs;
    }
    
    //query preprocessing for non-proximity queries
    public static void querypreprocessing(String query)
    {
    		querywords=null;
		  lowerquerywords=null;
		  querylist=new ArrayList<>();
		  
		input=new Scanner(query);
	      input.useDelimiter("[\\W\\s]+");
	      
	      while (input.hasNext()) {
		      querywords = input.next();
		      lowerquerywords = querywords.toLowerCase();
		      if(!stopWordsSet.contains(lowerquerywords)) {
		    	  querylist.add(obj.stem(lowerquerywords));
		      }
		  }
    }
    //query preprocessing for proximity queries
    public static void querypreprocessingforproxy(String query)
    {
    		querywords=null;
		  lowerquerywords=null;
		  querylist=new ArrayList<>();
		  
		input=new Scanner(query);
	      input.useDelimiter("[\\d\\W\\s]+");
	      //String local=null;
	      while (input.hasNext()) {
		      querywords = input.next();
		      lowerquerywords = querywords.toLowerCase();
		      if(!stopWordsSet.contains(lowerquerywords)) {
		    	  //local=(obj.stem(lowerquerywords).replaceAll("[^\\d.]", ""));
		    	  querylist.add(obj.stem(lowerquerywords));
		      }
		  }
    }
    //calculates the scores for non-proximity queries
    public static void score(String query1) throws IOException{
		
		  querypreprocessing(query1);  
		  //for non proxy queries
	    	Set<String> uniondocs=new HashSet<String>();
	      	uniondocs=union(querylist);
	      	
	      	
	      	
	      	
	      double w_td[]= new double [100];
	      double n=10.00;
	      ArrayList<String> documentlist;
	    
		//parsing the doclist
		for(String doc:uniondocs) 
		{
			int i=0;
			for(String term:querylist) {
				//System.out.println(termfreq(term,doc));
				//System.out.println(Math.log(termfreq(term,doc)));
				if(termfreq(term,doc)==0) {
					w_td[i]=0;
					//System.out.println("score for "+term+" in doc "+doc+" is "+w_td[i]);

					i++;
				}
				else {
					//System.out.println(1+(Math.log(termfreq(term,doc)/Math.log(2))));
					//System.out.println(10/worddoc(term).size());
					w_td[i]=((1+(Math.log(termfreq(term,doc)/Math.log(2))))*(Math.log(n/worddoc(term).size())/Math.log(2)));
					//System.out.println("score for "+term+" in doc "+doc+" is "+w_td[i]);
					i++;	
				}
				
			}
			
			double score=0;
			for(int z=0;z<w_td.length;z++) {
				
				score+=w_td[z];
				
			}
			
			scoreobject=new Score();
			scoreobject.setQuery(query1);
			scoreobject.setDoc(doc);
			scoreobject.setScore(score);
			Scoredict.add(scoreobject);
			//once it is out of the query term iteration it needs to calculate the score and
			//store it as a query document pair
		}
	
		
	writescore();
	
	}

    //calculates the score for proximity queries
	public static void scoreforproxy(String query1,ArrayList<String> proxydocs) throws IOException  {
		querypreprocessingforproxy(query1);  
	
	      	
	      	ArrayList<String> proxydoclist=proxydocs;
	      	
	      	
	      double w_td[]= new double [100];
	      ArrayList<String> documentlist;
	    
		//parsing the doclist
		for(String doc:proxydoclist) 
		{
			int i=0;
			for(String term:querylist) {
				//System.out.println(termfreq(term,doc));
				//System.out.println(Math.log(termfreq(term,doc)));
				if(termfreq(term,doc)==0) {
					w_td[i]=0;
					//System.out.println("score for "+term+" in doc "+doc+" is "+w_td[i]);

					i++;
				}
				else {
					w_td[i]=((1+(Math.log(termfreq(term,doc)/Math.log(2))))*(Math.log(10/worddoc(term).size())/Math.log(2)));
					//System.out.println("score for "+term+" in doc "+doc+" is "+w_td[i]);
					i++;	
				}
				
			}
			
			double score=0;
			for(int z=0;z<w_td.length;z++) {
				
				score+=w_td[z];
				
			}
			
			scoreobject=new Score();
			scoreobject.setQuery(query1);
			scoreobject.setDoc(doc);
			scoreobject.setScore(score);
			Scoredict.add(scoreobject);
			//once it is out of the query term iteration it needs to calculate the score and
			//store it as a query document pair
		}
	
		
	writescore();
	
	
	}
    
	
	
	//writes the score of a query onto a file
    public static void writescore() throws IOException{
    	FileWriter stream = new FileWriter("queryresult.txt");
	     PrintWriter out = new PrintWriter(stream); 
		Collections.sort(Scoredict, new SortbyScore());
		out.println("The documents are ranked based on the score (Descending Order)");
    	for(int g=0;g<Scoredict.size();g++)
    	{
    		System.out.println("query: "+Scoredict.get(g).getQuery()+" in doc: "+Scoredict.get(g).getDoc()+" has score: "+Scoredict.get(g).getScore());	
    		out.println("query: "+Scoredict.get(g).getQuery()+" in doc: "+Scoredict.get(g).getDoc()+" has score: "+Scoredict.get(g).getScore());	
    		
    	}
    	out.close();
    
    }	
    
    
    
    
    
	public static void main(String[] args) throws IOException {
		//Object of the Stemmer class
		obj = new KrovetzStemmer();

		  try {
		      //Please paste the path of the file location inside double quotes in the next line
			  input = new Scanner(new File("C:\\Users\\ADMIN\\Desktop\\SE-Assignment 1\\documents.txt"));
			  input.useDelimiter("[\\W\\s]+"); 		      //Tokenization being performed
		  } catch (FileNotFoundException e) {
		      e.printStackTrace();
		  }
		  
		  list = new ArrayList<String>(); //orignal list of words
		  Firstdoc = new ArrayList<String>();	//Sub list of words in the dictionary
		  FirstDict= new ArrayList<>();		//First Dictionary 
		  SecondDict=new ArrayList<>();		//Inverted Index
		  RepeatedWord = new ArrayList<>();	//To store the repeated words so that they can be removed
		  NewRepeatedWord = new ArrayList<>();
		  //querylist = new ArrayList<>();	//list of strings in query 1
		  querylist2 = new ArrayList<>();	//list of strings in query 2
		  querylist3 = new ArrayList<>();	//list of strings in query 3
		  Termposdict = new ArrayList<>();	//list for the new dictionary
	  	  computelist=new ArrayList<ArrayList<String>>();
	  	  Scoredict = new ArrayList<>();
	      splited=new ArrayList<String>();
	      range=  new ArrayList<String>();
		  //HashSet of stop words
		  stopWordsSet = new HashSet<String>();
		  stopWordsSet.add("the");
		  stopWordsSet.add("is");
		  stopWordsSet.add("at");
		  stopWordsSet.add("of");
		  stopWordsSet.add("on");
		  stopWordsSet.add("and");
		  stopWordsSet.add("a");
		  
		  
		  while (input.hasNext()) {
		      word = input.next();
		      lword = word.toLowerCase();
		      if(!stopWordsSet.contains(lword)) {
		    	  list.add(obj.stem(lword));
		      }
		      
		  }
		 
		  obj31=new PositionalInvertedIndex();
		  
		  for(int docs=1;docs<=10;docs++)
		  {
			  obj31.summary(FirstDict.size(), docs,1);
		  }
		    

		  int length=FirstDict.size();

		   int f=0;
		   int fid=0;
		   int gid=0;
		   int g=1;
		   int rid=0;
		   String fword=null;
		   String gword=null;
		   String rword=null;
		   
		  
		   while(f<length)
		  {
			  while(g<length)
			  {
				  fword=FirstDict.get(f).getWord();
				  gword=FirstDict.get(g).getWord();
				  if(fword.equals(gword))
				  	{
					  fid=FirstDict.get(f).getDocid();
					  gid=FirstDict.get(g).getDocid();
					  	if(fid==gid) 
					  		{
					  		rword=FirstDict.get(g).getWord();
					  		rid=FirstDict.get(g).getDocid();					  		
					  		object=new Dictionary();
			  				object.setWord(rword);
			  				object.setDocid(rid);
			  				//adding the word and doc id to the dictionary list
			  				if(RepeatedWord.indexOf(object)==-1) {
			  				RepeatedWord.add(object);}
					  		g++;
					  		}
					  	else{g++;}
				  	}
				  else {g++;}
			  }f++;
			  g=f+1;
		  }
		  //Removed all the occurences of the repeated words 
		  FirstDict.removeAll(RepeatedWord);
		  //Added only one occurence of all the repeated words
		  FirstDict.addAll(RepeatedWord);
		
		  
		  
		  
		  
		  //System.out.println(FirstDict.size());
		  //System.out.println("------------------------------");

		  //System.out.println(FirstDict.size());

		  //parse the list
		  //check for the occurence of a word
		  //if word occurs, append the doc id of that word with the current doc id
		  
		  
		  //Sorted the Index by id
		  Collections.sort(FirstDict, new Sortbyid());
		  //Sorted the Index by alphabets
		  Collections.sort(FirstDict, new Sortbyword());

		  createHashMap();
		  //Positional Inverted Creation Index Logic
		  	invertedindexcreation();
		  //else {//if it contains that element then check the doc id of that element if,
				  //and if same then add the pos to the pos list
				  //if not then create a new list for and add it to that list
				  //else loop for non-unique or repeated elements 
				  //documid=Termposdict.get(init).getDocid()+"";
				  //newdict.get(variable).add(documid);
				  //init++;
			  //}
		  invertedindexwrite();
		  invertedindexprint();
		  sortbykey();
		  //termposlistprint();
		  
		  //#######################################################
		  //for query1 
		  //score("nexus like love happy");
		  //for query2 
		  //score("asus repair");
		  //for query3
		  ArrayList<String> proxydocuments=new ArrayList<String>();
		  proxydocuments=proximityquery("0(touch screen) fix repair");
		  scoreforproxy("0(touch screen) fix repair", proxydocuments);
		  //for query4
		 //ArrayList<String> proxydocuments=new ArrayList<String>();
		 //proxydocuments=proximityquery("1(great tablet) 2(tablet fast)");
		 //scoreforproxy("1(great tablet) 2(tablet fast)", proxydocuments);  
		  //for query5
		  //score("tablet");
		  
	//Main ends here
	}
	



//Class ends here
}
