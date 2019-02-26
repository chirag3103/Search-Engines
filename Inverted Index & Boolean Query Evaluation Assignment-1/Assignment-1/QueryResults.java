import java.io.*;
import java.util.*;
import org.lemurproject.kstem.*;

class Dictionary{
	String word=null;
	int docid=0;
	List<Integer> idlist;



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

class Sortbyword implements Comparator<Dictionary> 
{ 
    // Used for sorting in ascending order of 
    // roll name 
    public int compare(Dictionary a, Dictionary b) 
    { 
        return a.word.compareTo(b.word); 
    } 
} 


public class QueryResults {
	
	static List<String> querylist1;
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
	static QueryResults obj31;
	static KrovetzStemmer obj;
	static Scanner input = null;
	static ArrayList<Dictionary> RepeatedWord;
	static ArrayList<Dictionary> NewRepeatedWord;
	static Map<String, SortedSet<Integer>> newdict;
	static TreeMap<String,SortedSet<Integer>> sorted;
	  
	  //function which performs the part 1 of the assignment
	//summary() is responsible for creating a dictionary FirstDict of words and their docid's
	public void summary (int origj,int documentid) {
		  int count=0;
		  int j=origj;
		  
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
			  object.setDocid(documentid);
			  //adding the word and doc id to the dictionary list
			  FirstDict.add(object);
			  j++;
		    }
		}
		    
	  }
	//function sortbykey is responsible for sorted the inverted index with the help of key in an alphabetical order
	public static void sortbykey() 
    { 
        // TreeMap to store values of HashMap
        sorted = new TreeMap<>(); 
  
        // Copy all data from hashMap into TreeMap 
        sorted.putAll(newdict); 
  
        // Display the TreeMap which is naturally sorted 
        //for (Map.Entry<String,SortedSet<Integer>> entry : sorted.entrySet())  
            //System.out.println("Key: "+entry.getKey()+" -> Value: " + entry.getValue());         
    } 
	
	
	public static void queryresult(String term1,String term2)
	{
		//we accept the query in the main function
		//process the query in the main again
		String word1=obj.stem(term1);
	
		String word2=obj.stem(term2);
		
		SortedSet<Integer> p1 =new TreeSet<Integer>();
		  SortedSet<Integer> p2 =new TreeSet<Integer>();

		  
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
		int p1size=p1.size();
		int p2size=p2.size();
		int arrp1[]=new int[p1size];
		int arrp2[]=new int[p2size];
		int i=0;
			
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
		          System.out.print(arrp2[j++]+" ");
		          i++;
		        }
		      }
		      System.out.println("");	
			
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
		  querylist1 = new ArrayList<>();	//list of strings in query 1
		  querylist2 = new ArrayList<>();	//list of strings in query 2
		  querylist3 = new ArrayList<>();	//list of strings in query 3
		  
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
		 
		  obj31=new QueryResults();
		  
		  for(int docs=1;docs<=10;docs++)
		  {
			  obj31.summary(FirstDict.size(), docs);
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
		  /*
		  System.out.println("The Sorted Inverted Index begins here");
		  System.out.println("------------------------------");

		  for (int k = 0; k < FirstDict.size(); k++) {
		      //For printing from the array list
			  //System.out.println(Firstdoc.get(k));
			  //For printing from the object based array list similar to a dictionary
		      System.out.println("WORD: "+FirstDict.get(k).getWord()+" --> "+"DOC ID: "+FirstDict.get(k).getDocid());
			  //System.out.println("value -> "+FirstDict.get(k).getDocid());
		  }
		  System.out.println("FINISHED PRINTING THE SORTED INVERTED INDEX LIST");
		  System.out.println("------------------------------");
		  */
		  
		  
		  
		  
		   
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
		  /*
		  System.out.println("The Sorted Posting List begins here");
		  System.out.println("------------------------------");
		  */
		  //function which sorts as well as displays the sorted list
		  sortbykey();
		  /*
		  System.out.println("FINISHED PRINTING THE SORTED POSTING LIST");
		  System.out.println("------------------------------");
		  */ 
		  
		  
		  
		  
		  //System.out.println("Enter your query");
		  //users query as string
		  String query1="asus AND google";
		  String query2="screen AND bad";
		  String query3="great AND tablet";
		  String querywords=null;
		  String lowerquerywords=null;
		  
		  System.out.println("------------------------------");
		  //For Query 1
		  input=new Scanner(query1);
	      input.useDelimiter("[\\W\\s]+");
	      
	      while (input.hasNext()) {
		      querywords = input.next();
		      lowerquerywords = querywords.toLowerCase();
		      if(!stopWordsSet.contains(lowerquerywords)) {
		    	  querylist1.add(obj.stem(lowerquerywords));
		      }
		  }
	      System.out.println("The query is: "+query1);
	      queryresult(querylist1.get(0),querylist1.get(1));
		  System.out.println("------------------------------");
		  
	      //For Query 2
	      input=new Scanner(query2);
	      input.useDelimiter("[\\W\\s]+");
	      
	      while (input.hasNext()) {
		      querywords = input.next();
		      lowerquerywords = querywords.toLowerCase();
		      if(!stopWordsSet.contains(lowerquerywords)) {
		    	  querylist2.add(obj.stem(lowerquerywords));
		      }
		  }
	      System.out.println("The query is: "+query2);
	      queryresult(querylist2.get(0),querylist2.get(1));
		  System.out.println("------------------------------");
		  
	      //For Query 3
	      input=new Scanner(query3);
	      input.useDelimiter("[\\W\\s]+");
	      
	      while (input.hasNext()) {
		      querywords = input.next();
		      lowerquerywords = querywords.toLowerCase();
		      if(!stopWordsSet.contains(lowerquerywords)) {
		    	  querylist3.add(obj.stem(lowerquerywords));
		      }
		  }
	      System.out.println("The query is: "+query3);
	      queryresult(querylist3.get(0),querylist3.get(1));
		  System.out.println("------------------------------");
	
		  
		  
		  
		  
		  
		  
		  
		  
	//Main ends here
	}
	



//Class ends here
}


/*
------------------------------
The query is: asus AND google
------------------------------------------
Queried term1 is asu -> [1, 3, 8]
Queried term2 is google-> [1, 7, 8]
1 8 
------------------------------
The query is: screen AND bad
------------------------------------------
Queried term2 is bad-> [3]
Queried term1 is screen -> [1, 2, 3, 6, 8]
3 
------------------------------
The query is: great AND tablet
------------------------------------------
Queried term1 is great -> [1, 2, 3, 6, 10]
Queried term2 is tablet-> [2, 3, 4, 6, 9, 10]
2 3 6 10 
------------------------------
*/

