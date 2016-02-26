import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class NLP_ASS4_Test_AParab {
	static HashMap<String,Bigram> Vocab = new HashMap();
	static Double mainCounter=0.0;
	static Double PosCounter=0.0;
	static Double NegCounter=0.0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Double total=0.0;
		System.out.println("Accuracy:");
		for(int i=0;i<10;i++)
		{
			//read model and save to HashMap
			readModel("model"+i);
			if(i==0)
			{
				readFolderPos(0,99);
				readFolderNeg(0,99);
			}
			else if(i==10)
			{
				readFolderPos(900,999);
				readFolderNeg(900,999);
			}
			else
			{
				readFolderPos(100*i,100*i+99);
				readFolderNeg(100*i,100*i+99);
			}	
			System.out.println("model"+i +" : "+(mainCounter));
			Vocab.clear();
			total+=mainCounter;
			mainCounter=0.0;
		}
		total/=2;
		System.out.println("\n"+"Percentage:"+(total/10)+"%");
	}
	private static void readModel(String fileName) {
		// TODO Auto-generated method stub
		String line=null;
		String [] line_temp = null; 
		try {
			FileReader fileReader =  new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((line = bufferedReader.readLine()) != null) {
				line_temp = line.split(" ");
				String word=line_temp[0];
				Double pos=Double.parseDouble(line_temp[1]);
				Double neg=Double.parseDouble(line_temp[2]);
				Bigram bgObj=new Bigram(word);
				bgObj.positive=pos;
				bgObj.negative=neg;
				Vocab.put(word, bgObj);
				bgObj=null;	
				line_temp=null;
			}	
			line=null;
			fileReader=null;
			bufferedReader=null;
		}
		catch(Exception e)
		{}
	}
	private static void readFolderNeg(int start,int stop) {
		// TODO Auto-generated method stub
		File folder = new File("neg");
		try {
			File[] listOfFiles = folder.listFiles();
			for (int i = start; i <= stop; i++) {
				getDataFromTestingFileNegative(listOfFiles[i].toString());
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
				getDataFromTestingFilePositive(listOfFiles[i].toString());
			}
		}
		catch(Exception e)
		{
		}
	}

	static void getDataFromTestingFilePositive(String fileName)
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
					Bigram bgObj=Vocab.get(line_temp[i]);
					if(bgObj!=null)
					{
						Double pos=bgObj.positive;
						Double neg=bgObj.negative;
						PosCounter+=pos;
						NegCounter+=neg;
					}
				}
				line_temp=null;
			}	
			line=null;
			fileReader=null;
			bufferedReader=null;
		}
		catch(Exception e)
		{}

		if(PosCounter>NegCounter)
			mainCounter++;
		NegCounter=PosCounter=0.0;
	}

	static void getDataFromTestingFileNegative(String fileName)
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
					Bigram bgObj=Vocab.get(line_temp[i]);
					if(bgObj!=null)
					{
						Double pos=bgObj.positive;
						Double neg=bgObj.negative;
						PosCounter+=pos;
						NegCounter+=neg;
					}
				}
				line_temp=null;
			}	
			line=null;
			fileReader=null;
			bufferedReader=null;
		}
		catch(Exception e)
		{}

		if(NegCounter>PosCounter)
			mainCounter++;
		NegCounter=PosCounter=0.0;
	}
}