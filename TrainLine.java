import java.util.Arrays;
import java.util.Random;

public class TrainLine {

	private TrainStation leftTerminus;
	private TrainStation rightTerminus;
	private String lineName;
	private boolean goingRight;
	public TrainStation[] lineMap;
	public static Random rand;

	public TrainLine(TrainStation leftTerminus, TrainStation rightTerminus, String name, boolean goingRight) {
		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		this.lineMap = this.getLineArray();
	}

	public TrainLine(TrainStation[] stationList, String name, boolean goingRight)
	/*
	 * Constructor for TrainStation input: stationList - An array of TrainStation
	 * containing the stations to be placed in the line name - Name of the line
	 * goingRight - boolean indicating the direction of travel
	 */
	{
		TrainStation leftT = stationList[0];
		TrainStation rightT = stationList[stationList.length - 1];

		stationList[0].setRight(stationList[stationList.length - 1]);
		stationList[stationList.length - 1].setLeft(stationList[0]);

		this.leftTerminus = stationList[0];
		this.rightTerminus = stationList[stationList.length - 1];
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		for (int i = 1; i < stationList.length - 1; i++) {
			this.addStation(stationList[i]);
		}

		this.lineMap = this.getLineArray();
	}

	public TrainLine(String[] stationNames, String name,
			boolean goingRight) {/*
									 * Constructor for TrainStation. input: stationNames - An array of String
									 * containing the name of the stations to be placed in the line name - Name of
									 * the line goingRight - boolean indicating the direction of travel
									 */
		TrainStation leftTerminus = new TrainStation(stationNames[0]);
		TrainStation rightTerminus = new TrainStation(stationNames[stationNames.length - 1]);

		leftTerminus.setRight(rightTerminus);
		rightTerminus.setLeft(leftTerminus);

		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;
		for (int i = 1; i < stationNames.length - 1; i++) {
			this.addStation(new TrainStation(stationNames[i]));
		}

		this.lineMap = this.getLineArray();

	}

	// adds a station at the last position before the right terminus
	public void addStation(TrainStation stationToAdd) {
		TrainStation rTer = this.rightTerminus;
		TrainStation beforeTer = rTer.getLeft();
		rTer.setLeft(stationToAdd);
		stationToAdd.setRight(rTer);
		beforeTer.setRight(stationToAdd);
		stationToAdd.setLeft(beforeTer);

		stationToAdd.setTrainLine(this);

		this.lineMap = this.getLineArray();
	}
	
	

	public String getName() {
		return this.lineName;
	}


	public int getSize() {
		
	int number=0;
	TrainStation s = leftTerminus;

   while(s!=null) {
	         s=s.getRight();
		     number++;
		}
   return number;
	}


	public void reverseDirection() {
		this.goingRight = !this.goingRight;
	}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation travelOneStation(TrainStation current, TrainStation previous) {

		if(current.getTransferStation()!=null&&current.getTransferStation()!=previous){
			
			try {
			current=current.getTransferStation();
			return current;
			
			}
			
			catch(StationNotFoundException e) {
				
				e.printStackTrace();
			}
				
		}
		
		if(current.getTransferStation()==null||current.getTransferStation()==previous){
			
			return current.getLine().getNext(current);
		}
		
		throw new StationNotFoundException("No such station is on this train line");
	}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation getNext (TrainStation station) throws NullPointerException {
		
		if (station.getName()!=null) {

		if(goingRight==true) {
			
			if(station.equals(rightTerminus)) {
				
				try {
				reverseDirection();
			    return station.getLeft();
				}
				
				catch(StationNotFoundException e) {
					
					e.printStackTrace();
				}
				
			    
			}
			    
			else {
				
				try {
			
			station=station.getRight();
			return station;
				}
				
				catch(StationNotFoundException e) {
					
					e.printStackTrace();
				}
			
			    }
		}
		
		if(goingRight==false) { 
			
           if(station.equals(leftTerminus)) {
        	   
        	   try {
				
				reverseDirection();
				return station.getRight();
        	   }
        	   
        	   catch(StationNotFoundException e) {
   				
   				e.printStackTrace();
   			}
        	   
			}
           else
           {
        	   try {
			
		station=station.getLeft();
		return station;
        	   }
        	   
        	 catch(StationNotFoundException e) {
   				
   				e.printStackTrace();
   			  }
           }
		
		}
		
		  }
		
		throw new StationNotFoundException("The next station cannot be found");
					
	}
			
		
		
	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	
	public TrainStation findStation(String name) throws NullPointerException {
		
		TrainStation s = leftTerminus; 
		
		try {
		   while(s!=null) {
			   
			   if(s.getName().equals(name)) {
			        	
			return s;	
		}
			   
			   s=s.getRight();
			
		   }
		}
		
		catch(NullPointerException e){
			
			e.printStackTrace();
		}
		
		throw new StationNotFoundException("No such station exists");
			
		}


	 

public void swap(TrainStation station1,TrainStation station2) {
		
		TrainStation s=station1.getLeft();
		TrainStation r=station2.getRight();
		
		station2.setRight(station1);
		station2.setLeft(s);
	
		station1.setLeft(station2);
		station1.setRight(r);
	
		
		if(s!=null) {
			
			s.setRight(station2);
			
		}
		
		if(r!=null) {
			
			r.setLeft(station1);;
				
		}
	}

	
	public void sortLine() {
		
		boolean variable=false;
		
		while(variable)
		{
			TrainStation station= leftTerminus;
			TrainStation station1= station.getRight();
			variable = false;
			for(int i=0; i < this.getSize(); i++)
			{
				
				if(station.getName().compareTo(station1.getName()) > 0)
				{
					swap(station,station1);
					
					if(station.isLeftTerminal())
					{
						station.setNonTerminal();
						station1.setLeft(null);
						station1.setLeftTerminal();
						leftTerminus = station1;
					}
					
					if(station1.isRightTerminal())
					{
						station1.setNonTerminal();
						station.setRight(null);
						station.setRightTerminal();
						this.rightTerminus = station;
					}
					variable=true;
				}
				station=station1;
				station1=station.getRight();
				
			}

			
		}
		
		lineMap=getLineArray();
	}


		
	public TrainStation[] getLineArray() {
		
		TrainStation[] array=new TrainStation[getSize()]; 
	    TrainStation s = leftTerminus;
	    
	    
	   int number=0;
	   
	   try {
		
	    while(s!=null) {
	    		
	    	     array[number]=s;
	 	         s=s.getRight();
	 	    	number++;
	 		 
	 	}
	    
	   }
	   
	   catch(NullPointerException e) {
		   
		   e.printStackTrace();
	   }
	    
	   lineMap=array;
		return array;
		
	}
	

	private TrainStation[] shuffleArray(TrainStation[] array) { 
		Random rand = new Random();
		
		rand.setSeed(11);

		for (int i = 0; i < array.length; i++) {
			int randomIndexToSwap = rand.nextInt(array.length);
			TrainStation temp = array[randomIndexToSwap];
			array[randomIndexToSwap] = array[i];
			array[i] = temp;
		}
		this.lineMap = array;
		return array;
	}

	public void shuffleLine() {

		// you are given a shuffled array of trainStations to start with
				TrainStation[] lineArray = this.getLineArray();
				TrainStation[] shuffledArray = shuffleArray(lineArray);
				
				leftTerminus = shuffledArray[0];
				rightTerminus = shuffledArray[shuffledArray.length-1];
				
				shuffledArray[shuffledArray.length-1].setRightTerminal();
				shuffledArray[shuffledArray.length-1].setRight(null);
				shuffledArray[shuffledArray.length-1].setLeft(shuffledArray[shuffledArray.length-2]);
				
				shuffledArray[0].setLeftTerminal();
				shuffledArray[0].setLeft(null);
				shuffledArray[0].setRight(shuffledArray[0].getRight());
				
				
				for(int i=1; i < shuffledArray.length-1; i++ )
				{
					shuffledArray[i].setNonTerminal();
					shuffledArray[i].setRight(shuffledArray[i+1]);
					shuffledArray[i].setLeft(shuffledArray[i-1]);
				}
				
				
		}

	public String toString() {
		TrainStation[] lineArr = this.getLineArray();
		String[] nameArr = new String[lineArr.length];
		for (int i = 0; i < lineArr.length; i++) {
			nameArr[i] = lineArr[i].getName();
		}
		return Arrays.deepToString(nameArr);
	}

	public boolean equals(TrainLine line2) {

		// check for equality of each station
		TrainStation current = this.leftTerminus;
		TrainStation curr2 = line2.leftTerminus;

		try {
			while (current != null) {
				if (!current.equals(curr2))
					return false;
				else {
					current = current.getRight();
					curr2 = curr2.getRight();
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public TrainStation getLeftTerminus() {
		return this.leftTerminus;
	}

	public TrainStation getRightTerminus() {
		return this.rightTerminus;
	}
}

//Exception for when searching a line for a station and not finding any station of the right name.
class StationNotFoundException extends RuntimeException {
	String name;

	public StationNotFoundException(String n) {
		name = n;
	}

	public String toString() {
		return "StationNotFoundException[" + name + "]";
	}
}
