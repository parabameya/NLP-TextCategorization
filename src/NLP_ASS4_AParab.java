import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class NLP_ASS4_AParab {
	static HashMap<String,Bigram> Vocab= new HashMap();		//for word given tag
	static ArrayList<String> POS_wordsAndTag = new ArrayList<String>();
	static ArrayList<String> NEG_wordsAndTag = new ArrayList<String>();

	static Double PosDivProb=0.0;
	static Double NegDivProb=0.0;

	public static void main(String args[])
	{
		train();
	}

	private static void train() {
		for(int i=0;i<10;i++)
		{
			if(i==0)
			{
				readFolderPos(100,999);
				readFolderNeg(100,999);
			}
			else if(i==10)
			{
				readFolderPos(0,899);
				readFolderNeg(0,899);
			}
			else
			{
				readFolderPos(0,100*i-1);
				readFolderPos(100*i+100,999);
				readFolderNeg(0,100*i-1);
				readFolderNeg(100*i+100,999);
			}	

			saveDataToBigramWordTagPos();
			saveDataToBigramWordTagNeg();


			//for count of Positive and Negative Divident
			PosDivProb=(double)POS_wordsAndTag.size()+Vocab.size();
			NegDivProb=(double)NEG_wordsAndTag.size()+Vocab.size();

			countProbability();
			writeModelFile("model"+i);
			System.out.println("The LM File has been created " +i);
			Vocab.clear();

			POS_wordsAndTag.clear();
			NEG_wordsAndTag.clear();
		}

	}


	private static void writeModelFile(String fileName) {
		// TODO Auto-generated method stub

		BufferedWriter writer=null;
		try{
			writer=new BufferedWriter(new FileWriter(fileName));

			Set set = Vocab.entrySet();				//HashMap Observation
			Iterator i = set.iterator();
			while(i.hasNext()) {    	//check countHs so that the loop is stopped fast
				Map.Entry me = (Map.Entry)i.next();
				Bigram bgObj=(Bigram) me.getValue();
				String word=(String) me.getKey();

				if((!(word.equalsIgnoreCase("")))&&(word!=null))
				{
					writer.write(word+" ");
					writer.write(bgObj.positive.toString()+" ");
					writer.write(bgObj.negative.toString());
					writer.write("\n");
				}
			}
		}
		catch(Exception e)
		{}
		finally
		{
			if(writer!=null)
				try{
					writer.close();
				}
			catch(Exception e)
			{}
		} 

	}

	private static void countProbability() {
		// TODO Auto-generated method stub
		Set set = Vocab.entrySet();				//HashMap Observation
		Iterator i = set.iterator();
		while(i.hasNext()) {    	//check countHs so that the loop is stopped fast
			Map.Entry me = (Map.Entry)i.next();
			Bigram bgObj=(Bigram) me.getValue();

			//to count the probability
			bgObj.positive/=PosDivProb;
			bgObj.negative/=NegDivProb;

			//to store in log

			bgObj.positive=Math.log(bgObj.positive)/Math.log(2);
			bgObj.negative=Math.log(bgObj.negative)/Math.log(2);
		}
	}

	private static void readFolderNeg(int start,int stop) {
		// TODO Auto-generated method stub
		File folder = new File("neg");
		try {

			File[] listOfFiles = folder.listFiles();
			for (int i = start; i <= stop; i++) {
				getDataFromTrainingFileNegative(listOfFiles[i].toString());
			}
		}
		catch(Exception e)
		{
		}
	}

	private static void readFolderPos(int start,int stop) {
		// TODO Auto-generated method stub
		File folder = new File("pos");
		try {

			File[] listOfFiles = folder.listFiles();
			for (int i = start; i <= stop; i++) {
				getDataFromTrainingFilePositive(listOfFiles[i].toString());
			}
		}
		catch(Exception e)
		{
		}
	}

	static void getDataFromTrainingFilePositive(String fileName)
	{
		String line=null;
		String [] line_temp = null; 
		try {
			FileReader fileReader =  new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((line = bufferedReader.readLine()) != null) {
				line_temp = line.split("\\W+");
				//to add <s> and </s>

				for (int i= 0; i<line_temp.length; i++) 
				{
					POS_wordsAndTag.add(line_temp[i]);
				}

				line_temp=null;
			}	

			line=null;
			fileReader=null;
			bufferedReader=null;
		}
		catch(Exception e)
		{}
	}

	static void getDataFromTrainingFileNegative(String fileName)
	{
		String line=null;
		String [] line_temp = null; 
		try {
			FileReader fileReader =  new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((line = bufferedReader.readLine()) != null) {
				line_temp = line.split("\\W+");
				//to add <s> and </s>

				for (int i= 0; i<line_temp.length; i++) 
				{
					NEG_wordsAndTag.add(line_temp[i]);
				}

				line_temp=null;
			}	

			line=null;
			fileReader=null;
			bufferedReader=null;
		}
		catch(Exception e)
		{}
	}

	static void saveDataToBigramWordTagPos() {
		// TODO Auto-generated method stub
		String tagHistory=null;
		for(int i=0;i<POS_wordsAndTag.size();i++)
		{
			String word=POS_wordsAndTag.get(i);		//Word
			Integer value_temp;
			Bigram bgObj;

			//to save the Observation matrix
			
				if(Vocab.get(word)==null)

				{
					bgObj=new Bigram(word);
					bgObj.positive++;
					Vocab.put(word, bgObj);
				}
				else
				{
					bgObj=Vocab.get(word);
					bgObj.positive++;
				}
				bgObj=null;
				Vocab.remove("");
			
		} //end of for
	}	

	static void saveDataToBigramWordTagNeg() {
		// TODO Auto-generated method stub
		String tagHistory=null;
		for(int i=0;i<NEG_wordsAndTag.size();i++)
		{
			String word=NEG_wordsAndTag.get(i);		//Word
			Bigram bgObj;
			//to save the Observation matrix

			if(Vocab.get(word)==null)
			{
				bgObj=new Bigram(word);
				bgObj.negative++;
				Vocab.put(word, bgObj);
			}
			else
			{
				bgObj=Vocab.get(word);
				bgObj.negative++;
			}

			bgObj=null;
			Vocab.remove("");
		} //end of for
	}	


}
