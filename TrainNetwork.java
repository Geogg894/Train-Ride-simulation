public class TrainNetwork {
	final int swapFreq = 2;
	TrainLine[] networkLines;

    public TrainNetwork(int nLines) {
    	this.networkLines = new TrainLine[nLines];
    }
    
    public void addLines(TrainLine[] lines) {
    	this.networkLines = lines;
    }
    
    public TrainLine[] getLines() {
    	return this.networkLines;
    }
    
    public void dance() {
    	
    	for(int i=0;i<networkLines.length;i++) {
    		  
    	networkLines[i].shuffleLine();
    	
    	}
    	
    System.out.println("The tracks are moving!");
    	
    }
    
    public void undance() {
    	for(int i=0;i<networkLines.length;i++) {
  		  
        	networkLines[i].sortLine();
        	
        	}
        
    }
    
    public int travel(String startStation, String startLine, String endStation, String endLine) {
    	
    	TrainLine curLine=null;
    	TrainStation curStation=null;
    	
    	try {
    		
    		 curLine=getLineByName(startLine);
    		 curStation=curLine.findStation(startStation);
    	}
    	
    	catch(LineNotFoundException e) {
    		
    		e.printStackTrace();
    	}
    	
    	catch(StationNotFoundException e) {
    		
    		e.printStackTrace();
    		return 168;
    	}
    	
  
    	int hoursCount = 0;
    	System.out.println("Departing from "+startStation);
    	
    	TrainStation prevStation=null;
   
	
    	while(true) {
    		
    		TrainStation s= curStation;
    				         
    			if (hoursCount!=0&&hoursCount%2==0) {
    				
    				dance();
    			}
    			
    			if(curStation.getName().equals(endStation)) {
    	    		
    	    		System.out.println("Arrived at destination after "+hoursCount+" hours!");
    		         break;
    		    	
    	    	}
    					
    			if(hoursCount == 168) {
    			System.out.println("Jumped off after spending a full week on the train. Might as well walk.");
    			return hoursCount;
    		}
    			
    			    curLine = curStation.getLine();
	    	        curStation = curLine.travelOneStation(curStation, prevStation);
	    			prevStation = s;
	    			
	   	
    		
    		//prints an update on your current location in the network.
	    	System.out.println("Traveling on line "+curLine.getName()+":"+curLine.toString());
	    	System.out.println("Hour "+hoursCount+". Current station: "+curStation.getName()+" on line "+curLine.getName());
	    	System.out.println("=============================================");
	    	
	    	
	    	hoursCount++; 
	    
    			   }
    			   
    			   return hoursCount;
	    	
    }
      
    //you can extend the method header if needed to include an exception. You cannot make any other change to the header.
   TrainLine getLineByName(String lineName){
    	
    for(int i=0;i<networkLines.length;i++) {
   
       if(networkLines[i].getName().equals(lineName)) {
    		
   		return networkLines[i];
    	}
    	
       }
    	
    	throw new LineNotFoundException("The following line could not be found");
    	
    }
   
  //prints a plan of the network for you.
    public void printPlan() {
    	System.out.println("CURRENT TRAIN NETWORK PLAN");
    	System.out.println("----------------------------");
    	for(int i=0;i<this.networkLines.length;i++) {
    		System.out.println(this.networkLines[i].getName()+":"+this.networkLines[i].toString());
    		}
    	System.out.println("----------------------------");
    }
}

//exception when searching a network for a LineName and not finding any matching Line object.
class LineNotFoundException extends RuntimeException {
	   String name;

	   public LineNotFoundException(String n) {
	      name = n;
	   }

	   public String toString() {
	      return "LineNotFoundException[" + name + "]";
	   }
	}
