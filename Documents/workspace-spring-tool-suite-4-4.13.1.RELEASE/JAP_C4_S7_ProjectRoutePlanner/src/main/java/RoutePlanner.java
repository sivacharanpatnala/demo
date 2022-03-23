import java.io.*;
import java.util.*;
public class RoutePlanner {
	public static void main(String[] args) throws IOException {
		RoutePlanner routeplanner=new RoutePlanner();
		Scanner scan=new Scanner(System.in);
		String [] routeInfo= {};
		//-----------------
		//Task-1
		//-----------------
		String file="src\\main\\java\\routes.csv";
		BufferedReader reader=null;
		String line="";
		try {
			reader=new BufferedReader(new FileReader(file));
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(",");
		        String uniqueRoute=String.join(",",values);
		        routeInfo=Arrays.copyOf(routeInfo,routeInfo.length+1);
		        routeInfo[routeInfo.length-1]=uniqueRoute;
		        for(String index:values) {
		        	System.out.printf("%-20s",index);
		        }
		        System.out.println();
		    }
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			reader.close();
		}
		//-----------------
		//Task-2
		//-----------------
		String fromCity=scan.nextLine();
		routeplanner.showDirectFlights(routeInfo, fromCity);
		//-----------------
		//Task-4
		//-----------------
		String sourceCity=scan.nextLine();
		String destinationCity=scan.nextLine();
		routeplanner.showAllConnections(routeInfo,sourceCity,destinationCity);
		
	}
	//Use the name of source City and present the list of the other cities to which it has direct flights.
	void showDirectFlights(String []  routeInfo,String fromCity) {
		int n=fromCity.length();
		int m=routeInfo.length;
		String [] directFlights= {};
		int count=0;
		for(int i=0;i<m;i++) {
			String str= routeInfo[i].substring(0,n);
			if(fromCity.equals(str)){
				directFlights=Arrays.copyOf(directFlights,directFlights.length+1);
				directFlights[directFlights.length-1]=routeInfo[i];
				count+=1;
			}
		}
		
		if(count==0) {
			System.out.println("We are sorry. At this point of time, we do not have any information on flights originating from "+fromCity+".");
		}
		else {
			/*for(int i=0;i<1;i++) {
				if(i==0) {
					String [] firstElement=routeInfo[0].split(",");
					for(String var:firstElement) {
						System.out.printf("%-20s",var);
					}
					System.out.println();
				}
			}
			for(String var:directFlights) {
				String [] finalValue=var.split(",");
				for(String value:finalValue) {
					System.out.printf("%-20s",value);
				}
				System.out.println();
			}*/
			
			//-----------------
			//Task-3
			//-----------------
			sortDirectFlights(directFlights);
			
		}
	}
	//Function to sort Direct Flights
	void sortDirectFlights(String [] directFlights) {
		int count=0;
		int [] start = {};
		int [] end = {};
		char s=',';
		//Finding starting and ending index of destination
		for(int i=0;i<directFlights.length;i++) {
			for(int j=0;j<directFlights[i].length();j++) {
				if(s==directFlights[i].charAt(j)) {
					count+=1;
					if(count==1) {
						int startValue=directFlights[i].indexOf(directFlights[i].charAt(j))+1;
						start=Arrays.copyOf(start,start.length+1);
						start[start.length-1]=startValue;
					}
					else if (count==2) {
						int endValue=directFlights[i].indexOf(directFlights[i].charAt(j),directFlights[i].indexOf(directFlights[i].charAt(j))+1);
						end=Arrays.copyOf(end,end.length+1);
						end[end.length-1]=endValue;
					}
				}

			}
			count=0;
		}
		
		//Sorting Logic
		int k=0;
		while(k<directFlights.length-1) {
			String firstValue=directFlights[k].substring(start[k],end[k]);
			for(int j=k+1;j<directFlights.length;j++) {
				String secondValue=directFlights[j].substring(start[k+1],end[k+1]);
				if(firstValue.compareToIgnoreCase(secondValue)>0) {
					String temp=directFlights[k];
					directFlights[k]=directFlights[j];
					directFlights[j]=temp;
				}
			}
			k+=1;
		}
		
		//Inserting Default printing column in array
		int n1=directFlights.length;
		String defaultString="From,To,Distance,Travel Time,Airfare";
		String [] sorteddirectFlights=Arrays.copyOf(directFlights,directFlights.length+1);
		int pos=1;
		//Inserting logic at index 0.
		for(int i=0;i<n1+1;i++) {
			if(i<pos-1) {
				sorteddirectFlights[i]=directFlights[i];
			}
			else if(i==pos-1) {
				sorteddirectFlights[i]=defaultString;
			}
			else {
				sorteddirectFlights[i]=directFlights[i-1];
			}
		}
		//Printing of the sorted array
		for(String var:sorteddirectFlights) {
			String [] finalValue=var.split(",");
			for(String value:finalValue) {
				System.out.printf("%-20s",value);
			}
			System.out.println();
		}
	}
	
	void showAllConnections(String [] routesInfo,String sourceCity,String destinationCity) {
		//case1 Direct flights from source to destination
		String [] directInfo= {};
		int flag=0;
		for(int i=0;i<routesInfo.length;i++) {
			String [] arrofStr=routesInfo[i].split(",",3);
			if(arrofStr[0].equals(sourceCity) && arrofStr[1].equals(destinationCity)){
				flag+=1;
				directInfo=Arrays.copyOf(directInfo,directInfo.length+1);
				directInfo[directInfo.length-1]=routesInfo[i];
			}
		}

		//case2 Connecting flights from source to destination
		String [] arrofStr= {};
		String [] indirectInfo= {};
		int n=sourceCity.length();
		int index1=-1;
		int index2=-1;
		for(int i=1;i<routesInfo.length;i++) {
			char [] arr=routesInfo[i].toCharArray();
			for(int j=0;j<arr.length;j++) {
				if(Character.isDigit(arr[j])) {
					index1=j;
					break;
				}
			}
			for(int j=0;j<arr.length;j++) {
				if(arr[j]==',') {
					index2=j;
					break;
				}
			}
			String str1= routesInfo[i].substring(0,n);
			String str2=routesInfo[i].substring(n+1,index1-1);
			if(sourceCity.equals(str1) && !destinationCity.equals(str2)){
				flag+=1;
				arrofStr=Arrays.copyOf(arrofStr,arrofStr.length+1);
				arrofStr[arrofStr.length-1]=routesInfo[i];
			}
			

		}
		for(int i=1;i<routesInfo.length;i++) {
			char [] arr=routesInfo[i].toCharArray();
			for(int j=0;j<arr.length;j++) {
				if(Character.isDigit(arr[j])) {
					index1=j;
					break;
				}
			}
			for(int j=0;j<arr.length;j++) {
				if(arr[j]==',') {
					index2=j;
					break;
				}
			}
			String str1= routesInfo[i].substring(0,n);
			String str3=routesInfo[i].substring(index2+1,index1-1);
			if(destinationCity.equals(str3) && ! sourceCity.equals(str1)) {
				flag+=1;
				arrofStr=Arrays.copyOf(arrofStr,arrofStr.length+1);
				arrofStr[arrofStr.length-1]=routesInfo[i];
			}
			
		}
		//for(String index : arrofStr) {
			//System.out.println(index);
		//}

		//----------------------------------------
		//Sorting of values
		int count=0;
		int [] start = {};
		int [] end = {};
		char s=',';
		for(int i=0;i<arrofStr.length/2;i++) {
			for(int j=0;j<arrofStr[i].length();j++) {
				if(s==arrofStr[i].charAt(j)) {
					count+=1;
					if(count==1) {
						int startValue=arrofStr[i].indexOf(arrofStr[i].charAt(j))+1;
						start=Arrays.copyOf(start,start.length+1);
						start[start.length-1]=startValue;
					}
					else if (count==2) {
						int endValue=arrofStr[i].indexOf(arrofStr[i].charAt(j),arrofStr[i].indexOf(arrofStr[i].charAt(j))+1);
						end=Arrays.copyOf(end,end.length+1);
						end[end.length-1]=endValue;
					}
				}

			}

			count=0;
		}
		int k=0;
		while(k<arrofStr.length/2) {
			String firstValue=arrofStr[k].substring(start[k],end[k]);
			for(int j=k+1;j<arrofStr.length/2;j++) {
				String secondValue=arrofStr[j].substring(start[k+1],end[k+1]);
				if(firstValue.compareToIgnoreCase(secondValue)>0) {
					String temp=arrofStr[k];
					arrofStr[k]=arrofStr[j];
					arrofStr[j]=temp;
				}
			}
			k+=1;
		}
		//for(String index:arrofStr) {
			//System.out.println(index);
		//}
		//------------------------------
		int counter=0;
		int startindex=0;
		int endindex=0;
		for(int i=0;i<arrofStr.length/2;i++) {
			indirectInfo=Arrays.copyOf(indirectInfo,indirectInfo.length+1);
			indirectInfo[indirectInfo.length-1]=arrofStr[i];
			char [] arr=arrofStr[i].toCharArray();
			for(int j=0;j<arr.length;j++) {
				if(arr[j]==',') {
					counter+=1;
					if (counter==1) {
						startindex=j;
					}
					if(counter==2) {
						endindex=j;
						break;
					}
				}
			}
			counter=0;
			String destination=arrofStr[i].substring(startindex+1,endindex);
			for(int t=arrofStr.length/2;t<arrofStr.length;t++) {
				if(arrofStr[t].contains(destination)) {
					indirectInfo=Arrays.copyOf(indirectInfo,indirectInfo.length+1);
					indirectInfo[indirectInfo.length-1]=arrofStr[t];
				}
			}
			
		}
		if (flag==0) {
			System.out.println("We are sorry. At this point of time, we do not have any information on flights originating from "+sourceCity+".");
		}
		else {
			for(int i=0;i<1;i++) {
				if(i==0) {
					String [] firstElement=routesInfo[0].split(",");
					for(String var:firstElement) {
						System.out.printf("%-20s",var);
					}
					System.out.println();
				}
			}
			for(String var:directInfo) {
				String [] finalValue=var.split(",");
				for(String value:finalValue) {
					System.out.printf("%-20s",value);
				}
				System.out.println();
			}
		}
	}
}