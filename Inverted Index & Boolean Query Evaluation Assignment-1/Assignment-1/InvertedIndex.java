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


public class InvertedIndex {
	
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
	static InvertedIndex obj31;
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
        for (Map.Entry<String,SortedSet<Integer>> entry : sorted.entrySet())  
            System.out.println("Key: "+entry.getKey()+" -> Value: " + entry.getValue());         
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
		 
		  obj31=new InvertedIndex();
		  
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
		  System.out.println("------------------------------");

		  //System.out.println(FirstDict.size());

		  //parse the list
		  //check for the occurence of a word
		  //if word occurs, append the doc id of that word with the current doc id
		  
		  
		  //Sorted the Index by id
		  Collections.sort(FirstDict, new Sortbyid());
		  //Sorted the Index by alphabets
		  Collections.sort(FirstDict, new Sortbyword());

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
		  
		  
		  
		  /*
		  
		   
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

		  System.out.println("The Sorted Posting List begins here");
		  System.out.println("------------------------------");
		  
		  //function which sorts as well as displays the sorted list
		  sortbykey();
		  System.out.println("FINISHED PRINTING THE SORTED POSTING LIST");
		  
		  
		  
		  
		  
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
	
		  
		  
		  */
		  
		  
		  
		  
		  
	//Main ends here
	}
	



//Class ends here
}


/*
 ------------------------------
The Sorted Inverted Index begins here
------------------------------
WORD: 1 --> DOC ID: 1
WORD: 10 --> DOC ID: 10
WORD: 100 --> DOC ID: 8
WORD: 2 --> DOC ID: 2
WORD: 2 --> DOC ID: 8
WORD: 200 --> DOC ID: 8
WORD: 2013 --> DOC ID: 1
WORD: 2015 --> DOC ID: 1
WORD: 3 --> DOC ID: 3
WORD: 4 --> DOC ID: 4
WORD: 5 --> DOC ID: 1
WORD: 5 --> DOC ID: 5
WORD: 6 --> DOC ID: 6
WORD: 6mos --> DOC ID: 4
WORD: 7 --> DOC ID: 6
WORD: 7 --> DOC ID: 7
WORD: 720p --> DOC ID: 4
WORD: 8 --> DOC ID: 8
WORD: 9 --> DOC ID: 9
WORD: able --> DOC ID: 2
WORD: about --> DOC ID: 2
WORD: about --> DOC ID: 6
WORD: acknowledge --> DOC ID: 8
WORD: actually --> DOC ID: 3
WORD: add --> DOC ID: 8
WORD: addition --> DOC ID: 6
WORD: after --> DOC ID: 6
WORD: again --> DOC ID: 2
WORD: again --> DOC ID: 3
WORD: again --> DOC ID: 8
WORD: ago --> DOC ID: 2
WORD: air --> DOC ID: 8
WORD: all --> DOC ID: 2
WORD: all --> DOC ID: 3
WORD: allow --> DOC ID: 7
WORD: also --> DOC ID: 3
WORD: although --> DOC ID: 8
WORD: am --> DOC ID: 4
WORD: amazing --> DOC ID: 8
WORD: america --> DOC ID: 8
WORD: amount --> DOC ID: 8
WORD: an --> DOC ID: 2
WORD: an --> DOC ID: 8
WORD: an --> DOC ID: 10
WORD: android --> DOC ID: 2
WORD: android --> DOC ID: 6
WORD: another --> DOC ID: 8
WORD: appreciate --> DOC ID: 6
WORD: are --> DOC ID: 6
WORD: are --> DOC ID: 8
WORD: armor --> DOC ID: 2
WORD: as --> DOC ID: 4
WORD: as --> DOC ID: 8
WORD: as --> DOC ID: 9
WORD: as --> DOC ID: 10
WORD: ask --> DOC ID: 9
WORD: asu --> DOC ID: 1
WORD: asu --> DOC ID: 3
WORD: asu --> DOC ID: 8
WORD: automatic --> DOC ID: 8
WORD: avoid --> DOC ID: 8
WORD: awesome --> DOC ID: 1
WORD: awful --> DOC ID: 1
WORD: back --> DOC ID: 1
WORD: back --> DOC ID: 2
WORD: bad --> DOC ID: 3
WORD: battery --> DOC ID: 5
WORD: be --> DOC ID: 3
WORD: became --> DOC ID: 8
WORD: because --> DOC ID: 8
WORD: been --> DOC ID: 4
WORD: been --> DOC ID: 8
WORD: before --> DOC ID: 2
WORD: being --> DOC ID: 2
WORD: big --> DOC ID: 2
WORD: big --> DOC ID: 4
WORD: birthday --> DOC ID: 2
WORD: books --> DOC ID: 4
WORD: bought --> DOC ID: 4
WORD: bought --> DOC ID: 5
WORD: bought --> DOC ID: 8
WORD: brick --> DOC ID: 8
WORD: browse --> DOC ID: 4
WORD: but --> DOC ID: 1
WORD: but --> DOC ID: 2
WORD: but --> DOC ID: 3
WORD: but --> DOC ID: 4
WORD: but --> DOC ID: 9
WORD: but --> DOC ID: 10
WORD: button --> DOC ID: 2
WORD: buy --> DOC ID: 8
WORD: by --> DOC ID: 8
WORD: by --> DOC ID: 9
WORD: camera --> DOC ID: 2
WORD: camera --> DOC ID: 6
WORD: can --> DOC ID: 6
WORD: can --> DOC ID: 9
WORD: case --> DOC ID: 2
WORD: charge --> DOC ID: 3
WORD: child --> DOC ID: 9
WORD: claim --> DOC ID: 8
WORD: common --> DOC ID: 8
WORD: company --> DOC ID: 8
WORD: compare --> DOC ID: 1
WORD: compare --> DOC ID: 6
WORD: complete --> DOC ID: 8
WORD: completely --> DOC ID: 8
WORD: confidence --> DOC ID: 8
WORD: control --> DOC ID: 7
WORD: convenience --> DOC ID: 4
WORD: convince --> DOC ID: 9
WORD: cooler --> DOC ID: 3
WORD: cost --> DOC ID: 8
WORD: could --> DOC ID: 10
WORD: count --> DOC ID: 4
WORD: counter --> DOC ID: 1
WORD: crappy --> DOC ID: 9
WORD: cursor --> DOC ID: 2
WORD: customer --> DOC ID: 8
WORD: d --> DOC ID: 2
WORD: days --> DOC ID: 1
WORD: dealings --> DOC ID: 8
WORD: dedicated --> DOC ID: 4
WORD: defective --> DOC ID: 8
WORD: delete --> DOC ID: 7
WORD: deserve --> DOC ID: 9
WORD: device --> DOC ID: 2
WORD: device --> DOC ID: 5
WORD: device --> DOC ID: 10
WORD: devicewear --> DOC ID: 2
WORD: dice --> DOC ID: 8
WORD: difference --> DOC ID: 6
WORD: disaster --> DOC ID: 9
WORD: do --> DOC ID: 1
WORD: do --> DOC ID: 7
WORD: do --> DOC ID: 8
WORD: do --> DOC ID: 9
WORD: do --> DOC ID: 10
WORD: doc --> DOC ID: 1
WORD: doc --> DOC ID: 2
WORD: doc --> DOC ID: 3
WORD: doc --> DOC ID: 4
WORD: doc --> DOC ID: 5
WORD: doc --> DOC ID: 6
WORD: doc --> DOC ID: 7
WORD: doc --> DOC ID: 8
WORD: doc --> DOC ID: 9
WORD: doc --> DOC ID: 10
WORD: drop --> DOC ID: 5
WORD: e --> DOC ID: 10
WORD: ease --> DOC ID: 4
WORD: easily --> DOC ID: 6
WORD: ebay --> DOC ID: 8
WORD: ebook --> DOC ID: 2
WORD: edit --> DOC ID: 4
WORD: elective --> DOC ID: 7
WORD: emulate --> DOC ID: 4
WORD: end --> DOC ID: 8
WORD: end --> DOC ID: 9
WORD: environment --> DOC ID: 3
WORD: estimate --> DOC ID: 8
WORD: etc --> DOC ID: 5
WORD: even --> DOC ID: 1
WORD: even --> DOC ID: 8
WORD: everything --> DOC ID: 10
WORD: expensive --> DOC ID: 8
WORD: experienced --> DOC ID: 8
WORD: express --> DOC ID: 8
WORD: fail --> DOC ID: 8
WORD: fall --> DOC ID: 9
WORD: fast --> DOC ID: 2
WORD: fast --> DOC ID: 6
WORD: feel --> DOC ID: 6
WORD: feeling --> DOC ID: 6
WORD: few --> DOC ID: 3
WORD: few --> DOC ID: 4
WORD: finally --> DOC ID: 4
WORD: fine --> DOC ID: 2
WORD: first --> DOC ID: 3
WORD: first --> DOC ID: 8
WORD: five --> DOC ID: 3
WORD: fix --> DOC ID: 8
WORD: fixed --> DOC ID: 3
WORD: fixed --> DOC ID: 8
WORD: flaw --> DOC ID: 9
WORD: flawless --> DOC ID: 1
WORD: for --> DOC ID: 2
WORD: for --> DOC ID: 3
WORD: for --> DOC ID: 4
WORD: for --> DOC ID: 8
WORD: for --> DOC ID: 9
WORD: from --> DOC ID: 1
WORD: from --> DOC ID: 4
WORD: from --> DOC ID: 6
WORD: frustrate --> DOC ID: 9
WORD: function --> DOC ID: 3
WORD: games --> DOC ID: 4
WORD: games --> DOC ID: 10
WORD: garbage --> DOC ID: 9
WORD: gear --> DOC ID: 1
WORD: get --> DOC ID: 2
WORD: get --> DOC ID: 3
WORD: get --> DOC ID: 4
WORD: get --> DOC ID: 8
WORD: get --> DOC ID: 9
WORD: give --> DOC ID: 9
WORD: go --> DOC ID: 3
WORD: good --> DOC ID: 2
WORD: good --> DOC ID: 4
WORD: good --> DOC ID: 5
WORD: good --> DOC ID: 8
WORD: google --> DOC ID: 1
WORD: google --> DOC ID: 7
WORD: google --> DOC ID: 8
WORD: gopro --> DOC ID: 4
WORD: got --> DOC ID: 3
WORD: got --> DOC ID: 8
WORD: great --> DOC ID: 1
WORD: great --> DOC ID: 2
WORD: great --> DOC ID: 3
WORD: great --> DOC ID: 6
WORD: great --> DOC ID: 10
WORD: guess --> DOC ID: 6
WORD: had --> DOC ID: 3
WORD: had --> DOC ID: 4
WORD: had --> DOC ID: 8
WORD: had --> DOC ID: 9
WORD: happen --> DOC ID: 8
WORD: happy --> DOC ID: 4
WORD: happy --> DOC ID: 6
WORD: hard --> DOC ID: 9
WORD: hardware --> DOC ID: 1
WORD: hardware --> DOC ID: 9
WORD: has --> DOC ID: 1
WORD: has --> DOC ID: 5
WORD: hate --> DOC ID: 9
WORD: have --> DOC ID: 2
WORD: have --> DOC ID: 3
WORD: have --> DOC ID: 6
WORD: have --> DOC ID: 8
WORD: have --> DOC ID: 10
WORD: he --> DOC ID: 5
WORD: hero4 --> DOC ID: 4
WORD: his --> DOC ID: 5
WORD: hold --> DOC ID: 7
WORD: hundred --> DOC ID: 8
WORD: husband --> DOC ID: 2
WORD: i --> DOC ID: 2
WORD: i --> DOC ID: 3
WORD: i --> DOC ID: 4
WORD: i --> DOC ID: 6
WORD: i --> DOC ID: 8
WORD: i --> DOC ID: 9
WORD: i --> DOC ID: 10
WORD: if --> DOC ID: 1
WORD: if --> DOC ID: 2
WORD: if --> DOC ID: 3
WORD: if --> DOC ID: 8
WORD: immediately --> DOC ID: 2
WORD: in --> DOC ID: 3
WORD: in --> DOC ID: 8
WORD: in --> DOC ID: 9
WORD: instead --> DOC ID: 5
WORD: instead --> DOC ID: 8
WORD: instead --> DOC ID: 9
WORD: internet --> DOC ID: 10
WORD: ipad --> DOC ID: 2
WORD: iphone --> DOC ID: 2
WORD: ipod --> DOC ID: 2
WORD: issue --> DOC ID: 3
WORD: issue --> DOC ID: 8
WORD: it --> DOC ID: 2
WORD: it --> DOC ID: 3
WORD: it --> DOC ID: 4
WORD: it --> DOC ID: 5
WORD: it --> DOC ID: 6
WORD: it --> DOC ID: 8
WORD: it --> DOC ID: 9
WORD: it --> DOC ID: 10
WORD: its --> DOC ID: 2
WORD: its --> DOC ID: 9
WORD: jumpy --> DOC ID: 8
WORD: junk --> DOC ID: 8
WORD: just --> DOC ID: 4
WORD: just --> DOC ID: 8
WORD: justify --> DOC ID: 9
WORD: kinemaster --> DOC ID: 4
WORD: know --> DOC ID: 1
WORD: know --> DOC ID: 4
WORD: l --> DOC ID: 6
WORD: laptop --> DOC ID: 5
WORD: later --> DOC ID: 8
WORD: least --> DOC ID: 7
WORD: less --> DOC ID: 8
WORD: lg --> DOC ID: 1
WORD: life --> DOC ID: 5
WORD: lighter --> DOC ID: 6
WORD: like --> DOC ID: 1
WORD: like --> DOC ID: 2
WORD: like --> DOC ID: 8
WORD: little --> DOC ID: 4
WORD: loss --> DOC ID: 8
WORD: lot --> DOC ID: 2
WORD: lot --> DOC ID: 5
WORD: love --> DOC ID: 2
WORD: love --> DOC ID: 8
WORD: lower --> DOC ID: 1
WORD: m --> DOC ID: 4
WORD: m --> DOC ID: 6
WORD: m --> DOC ID: 8
WORD: m --> DOC ID: 9
WORD: machine --> DOC ID: 4
WORD: mainly --> DOC ID: 10
WORD: me --> DOC ID: 2
WORD: model --> DOC ID: 6
WORD: modern --> DOC ID: 1
WORD: money --> DOC ID: 5
WORD: money --> DOC ID: 8
WORD: month --> DOC ID: 3
WORD: month --> DOC ID: 8
WORD: more --> DOC ID: 1
WORD: more --> DOC ID: 8
WORD: most --> DOC ID: 1
WORD: move --> DOC ID: 2
WORD: movies --> DOC ID: 4
WORD: much --> DOC ID: 2
WORD: much --> DOC ID: 8
WORD: much --> DOC ID: 9
WORD: my --> DOC ID: 2
WORD: my --> DOC ID: 4
WORD: my --> DOC ID: 5
WORD: my --> DOC ID: 6
WORD: my --> DOC ID: 8
WORD: myself --> DOC ID: 9
WORD: net --> DOC ID: 4
WORD: new --> DOC ID: 8
WORD: next --> DOC ID: 1
WORD: nexus --> DOC ID: 1
WORD: nexus --> DOC ID: 2
WORD: nexus --> DOC ID: 6
WORD: nice --> DOC ID: 10
WORD: no --> DOC ID: 9
WORD: not --> DOC ID: 2
WORD: not --> DOC ID: 3
WORD: not --> DOC ID: 4
WORD: not --> DOC ID: 6
WORD: not --> DOC ID: 7
WORD: not --> DOC ID: 8
WORD: not --> DOC ID: 9
WORD: notice --> DOC ID: 6
WORD: now --> DOC ID: 1
WORD: now --> DOC ID: 2
WORD: now --> DOC ID: 4
WORD: now --> DOC ID: 8
WORD: off --> DOC ID: 4
WORD: off --> DOC ID: 8
WORD: old --> DOC ID: 6
WORD: one --> DOC ID: 1
WORD: one --> DOC ID: 6
WORD: one --> DOC ID: 8
WORD: only --> DOC ID: 1
WORD: only --> DOC ID: 2
WORD: only --> DOC ID: 6
WORD: onto --> DOC ID: 4
WORD: operate --> DOC ID: 2
WORD: option --> DOC ID: 1
WORD: or --> DOC ID: 1
WORD: or --> DOC ID: 2
WORD: or --> DOC ID: 4
WORD: or --> DOC ID: 7
WORD: os --> DOC ID: 1
WORD: out --> DOC ID: 8
WORD: over --> DOC ID: 4
WORD: over --> DOC ID: 8
WORD: overall --> DOC ID: 6
WORD: own --> DOC ID: 1
WORD: parts --> DOC ID: 8
WORD: pc --> DOC ID: 4
WORD: picky --> DOC ID: 9
WORD: piece --> DOC ID: 8
WORD: piece --> DOC ID: 9
WORD: plus --> DOC ID: 1
WORD: point --> DOC ID: 9
WORD: port --> DOC ID: 3
WORD: powerful --> DOC ID: 1
WORD: pre --> DOC ID: 5
WORD: prefer --> DOC ID: 2
WORD: present --> DOC ID: 9
WORD: price --> DOC ID: 1
WORD: price --> DOC ID: 2
WORD: problem --> DOC ID: 3
WORD: problem --> DOC ID: 4
WORD: problem --> DOC ID: 8
WORD: product --> DOC ID: 8
WORD: profile --> DOC ID: 1
WORD: properly --> DOC ID: 3
WORD: protect --> DOC ID: 2
WORD: protector --> DOC ID: 2
WORD: purchase --> DOC ID: 6
WORD: pushed --> DOC ID: 8
WORD: put --> DOC ID: 2
WORD: put --> DOC ID: 7
WORD: quite --> DOC ID: 5
WORD: quite --> DOC ID: 6
WORD: react --> DOC ID: 6
WORD: read --> DOC ID: 2
WORD: reader --> DOC ID: 10
WORD: realize --> DOC ID: 9
WORD: really --> DOC ID: 2
WORD: really --> DOC ID: 6
WORD: really --> DOC ID: 8
WORD: rear --> DOC ID: 6
WORD: recommend --> DOC ID: 5
WORD: release --> DOC ID: 8
WORD: repair --> DOC ID: 3
WORD: repair --> DOC ID: 8
WORD: replace --> DOC ID: 3
WORD: responsive --> DOC ID: 5
WORD: responsive --> DOC ID: 6
WORD: restart --> DOC ID: 2
WORD: result --> DOC ID: 8
WORD: return --> DOC ID: 9
WORD: review --> DOC ID: 8
WORD: revision --> DOC ID: 8
WORD: ridiculous --> DOC ID: 1
WORD: right --> DOC ID: 8
WORD: rocks --> DOC ID: 1
WORD: roll --> DOC ID: 8
WORD: s --> DOC ID: 3
WORD: s --> DOC ID: 4
WORD: s --> DOC ID: 5
WORD: s --> DOC ID: 10
WORD: same --> DOC ID: 10
WORD: save --> DOC ID: 5
WORD: screen --> DOC ID: 1
WORD: screen --> DOC ID: 2
WORD: screen --> DOC ID: 3
WORD: screen --> DOC ID: 6
WORD: screen --> DOC ID: 8
WORD: see --> DOC ID: 8
WORD: seem --> DOC ID: 3
WORD: send --> DOC ID: 3
WORD: send --> DOC ID: 8
WORD: sensitive --> DOC ID: 3
WORD: several --> DOC ID: 5
WORD: shame --> DOC ID: 8
WORD: shop --> DOC ID: 4
WORD: should --> DOC ID: 7
WORD: size --> DOC ID: 2
WORD: slimport --> DOC ID: 1
WORD: slow --> DOC ID: 9
WORD: sluggy --> DOC ID: 9
WORD: small --> DOC ID: 2
WORD: so --> DOC ID: 1
WORD: so --> DOC ID: 2
WORD: so --> DOC ID: 9
WORD: software --> DOC ID: 1
WORD: some --> DOC ID: 1
WORD: some --> DOC ID: 8
WORD: sometimes --> DOC ID: 2
WORD: son --> DOC ID: 5
WORD: sony --> DOC ID: 1
WORD: soon --> DOC ID: 9
WORD: speaker --> DOC ID: 6
WORD: specially --> DOC ID: 6
WORD: star --> DOC ID: 1
WORD: stars --> DOC ID: 1
WORD: stars --> DOC ID: 3
WORD: start --> DOC ID: 3
WORD: start --> DOC ID: 4
WORD: stero --> DOC ID: 6
WORD: still --> DOC ID: 1
WORD: stock --> DOC ID: 8
WORD: storage --> DOC ID: 2
WORD: submit --> DOC ID: 8
WORD: super --> DOC ID: 9
WORD: sure --> DOC ID: 2
WORD: surprise --> DOC ID: 2
WORD: surprise --> DOC ID: 4
WORD: suspension --> DOC ID: 7
WORD: system --> DOC ID: 1
WORD: system --> DOC ID: 2
WORD: t --> DOC ID: 2
WORD: t --> DOC ID: 3
WORD: t --> DOC ID: 8
WORD: tablet --> DOC ID: 2
WORD: tablet --> DOC ID: 3
WORD: tablet --> DOC ID: 4
WORD: tablet --> DOC ID: 6
WORD: tablet --> DOC ID: 9
WORD: tablet --> DOC ID: 10
WORD: tech --> DOC ID: 2
WORD: teen --> DOC ID: 5
WORD: temperature --> DOC ID: 3
WORD: than --> DOC ID: 2
WORD: than --> DOC ID: 8
WORD: that --> DOC ID: 1
WORD: that --> DOC ID: 2
WORD: that --> DOC ID: 3
WORD: that --> DOC ID: 6
WORD: that --> DOC ID: 8
WORD: that --> DOC ID: 9
WORD: their --> DOC ID: 1
WORD: their --> DOC ID: 8
WORD: them --> DOC ID: 7
WORD: them --> DOC ID: 8
WORD: then --> DOC ID: 3
WORD: then --> DOC ID: 8
WORD: they --> DOC ID: 8
WORD: thing --> DOC ID: 6
WORD: thing --> DOC ID: 8
WORD: things --> DOC ID: 1
WORD: things --> DOC ID: 7
WORD: this --> DOC ID: 1
WORD: this --> DOC ID: 2
WORD: this --> DOC ID: 3
WORD: this --> DOC ID: 4
WORD: this --> DOC ID: 5
WORD: this --> DOC ID: 6
WORD: this --> DOC ID: 8
WORD: this --> DOC ID: 9
WORD: this --> DOC ID: 10
WORD: thought --> DOC ID: 9
WORD: thrill --> DOC ID: 6
WORD: throw --> DOC ID: 9
WORD: time --> DOC ID: 8
WORD: times --> DOC ID: 5
WORD: to --> DOC ID: 1
WORD: to --> DOC ID: 2
WORD: to --> DOC ID: 3
WORD: to --> DOC ID: 4
WORD: to --> DOC ID: 6
WORD: to --> DOC ID: 7
WORD: to --> DOC ID: 8
WORD: to --> DOC ID: 9
WORD: to --> DOC ID: 10
WORD: too --> DOC ID: 1
WORD: too --> DOC ID: 2
WORD: too --> DOC ID: 7
WORD: too --> DOC ID: 8
WORD: too --> DOC ID: 9
WORD: topnotch --> DOC ID: 1
WORD: touch --> DOC ID: 2
WORD: touch --> DOC ID: 3
WORD: touch --> DOC ID: 6
WORD: touch --> DOC ID: 8
WORD: tough --> DOC ID: 5
WORD: trivial --> DOC ID: 8
WORD: try --> DOC ID: 9
WORD: two --> DOC ID: 9
WORD: ugly --> DOC ID: 9
WORD: unable --> DOC ID: 7
WORD: unit --> DOC ID: 8
WORD: unlike --> DOC ID: 6
WORD: unresponsive --> DOC ID: 8
WORD: up --> DOC ID: 8
WORD: up --> DOC ID: 9
WORD: update --> DOC ID: 1
WORD: update --> DOC ID: 8
WORD: upgrade --> DOC ID: 6
WORD: use --> DOC ID: 1
WORD: use --> DOC ID: 2
WORD: use --> DOC ID: 4
WORD: use --> DOC ID: 5
WORD: use --> DOC ID: 7
WORD: use --> DOC ID: 8
WORD: use --> DOC ID: 10
WORD: ve --> DOC ID: 4
WORD: ve --> DOC ID: 8
WORD: vein --> DOC ID: 9
WORD: very --> DOC ID: 4
WORD: very --> DOC ID: 9
WORD: video --> DOC ID: 4
WORD: vimeo --> DOC ID: 4
WORD: want --> DOC ID: 2
WORD: want --> DOC ID: 7
WORD: want --> DOC ID: 8
WORD: want --> DOC ID: 10
WORD: warm --> DOC ID: 3
WORD: warranty --> DOC ID: 8
WORD: was --> DOC ID: 1
WORD: was --> DOC ID: 2
WORD: was --> DOC ID: 3
WORD: was --> DOC ID: 8
WORD: wasn --> DOC ID: 2
WORD: waste --> DOC ID: 8
WORD: watch --> DOC ID: 4
WORD: we --> DOC ID: 1
WORD: web --> DOC ID: 4
WORD: week --> DOC ID: 9
WORD: weight --> DOC ID: 2
WORD: well --> DOC ID: 2
WORD: went --> DOC ID: 3
WORD: were --> DOC ID: 3
WORD: what --> DOC ID: 4
WORD: whatsoever --> DOC ID: 4
WORD: when --> DOC ID: 1
WORD: when --> DOC ID: 3
WORD: when --> DOC ID: 8
WORD: where --> DOC ID: 2
WORD: which --> DOC ID: 8
WORD: widespread --> DOC ID: 8
WORD: will --> DOC ID: 8
WORD: willing --> DOC ID: 8
WORD: with --> DOC ID: 1
WORD: with --> DOC ID: 2
WORD: with --> DOC ID: 3
WORD: with --> DOC ID: 4
WORD: with --> DOC ID: 5
WORD: with --> DOC ID: 6
WORD: with --> DOC ID: 8
WORD: won --> DOC ID: 2
WORD: won --> DOC ID: 3
WORD: won --> DOC ID: 8
WORD: work --> DOC ID: 2
WORD: work --> DOC ID: 3
WORD: working --> DOC ID: 8
WORD: works --> DOC ID: 2
WORD: worst --> DOC ID: 1
WORD: would --> DOC ID: 3
WORD: would --> DOC ID: 5
WORD: would --> DOC ID: 8
WORD: writing --> DOC ID: 8
WORD: year --> DOC ID: 1
WORD: year --> DOC ID: 2
WORD: year --> DOC ID: 8
WORD: years --> DOC ID: 4
WORD: you --> DOC ID: 6
WORD: you --> DOC ID: 7
WORD: you --> DOC ID: 8
FINISHED PRINTING THE SORTED INVERTED INDEX LIST
------------------------------

 */

